package com.nminin.izibookexample.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nminin.izibookexample.BuildConfig
import com.nminin.izibookexample.data.model.Certificate
import com.nminin.izibookexample.data.networking.Api
import com.nminin.izibookexample.data.networking.AuthApi
import com.nminin.izibookexample.data.repository.CategoryRepository
import com.nminin.izibookexample.data.repository.CertificateRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.Provider
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

val dataModule = module {

    single<Gson> {
        GsonBuilder()
            .setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .enableComplexMapKeySerialization()
            .setVersion(1.0)
            .create()
    }

    factory<AuthApi> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_AUTH_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(getOkHttpClient())
            .build()
            .create(AuthApi::class.java)
    }

    single { CertificateRepository(get()) }

    factory<Api> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(getOkHttpClient(get()))
            .build()
            .create(Api::class.java)
    }

    single<CategoryRepository> { CategoryRepository(get()) }
}

private fun getOkHttpClient(certificate: CertificateRepository? = null): OkHttpClient {
    val logsInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }
    val headerInterceptor = Interceptor { chain ->
        val builder = chain
            .request()
            .newBuilder()
        chain.proceed(builder.build())
    }

    return OkHttpClient.Builder()
        .apply {
            val sslContext = getSslContext()
            certificate?.let { certificate ->
                initTrustManagerFactory(certificate.get().blockingFirst()).trustManagers
                    .firstOrNull {
                        it is X509TrustManager
                    }?.let { trustManager ->
                        sslContext.init(null, arrayOf(trustManager), null)
                        this.sslSocketFactory(
                            sslContext.socketFactory,
                            trustManager as X509TrustManager
                        )
                    }
            } ?: kotlin.run {
                val trustAllCerts: Array<TrustManager> = arrayOf(object : X509TrustManager {
                    override fun checkClientTrusted(
                        chain: Array<out X509Certificate>?,
                        authType: String?
                    ) {
                    }

                    override fun checkServerTrusted(
                        chain: Array<out X509Certificate>?,
                        authType: String?
                    ) {
                    }
                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                })
                sslContext.init(null ,trustAllCerts, null)
                this.hostnameVerifier(object : HostnameVerifier {
                    override fun verify(hostname: String?, session: SSLSession?): Boolean = true
                })
                this.sslSocketFactory(
                    sslContext.socketFactory,
                    trustAllCerts.first() as X509TrustManager
                )
            }
        }
        .addInterceptor(headerInterceptor)
        .addInterceptor(logsInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()
}

private fun getSslContext() = SSLContext.getInstance("SSL")

private fun initTrustManagerFactory(certificate: Certificate) =
    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
        init(KeyStore.getInstance("pkcs12").apply {
            load(
                ByteArrayInputStream(certificate.pkcs12.toByteArray()),
                certificate.public.toCharArray()
            )
        })
    }