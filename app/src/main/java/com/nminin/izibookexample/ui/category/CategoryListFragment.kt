package com.nminin.izibookexample.ui.category

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nminin.bindingbuilder.bind
import com.nminin.izibookexample.R
import com.nminin.izibookexample.ui.base.Fragment
import com.nminin.izibookexample.ui.category.recycler.CategoryViewHolderFactory
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryListFragment : Fragment(R.layout.fragment_category_list) {
    private val viewModel by viewModel<CategoryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeErrors(viewModel)
        view.findViewById<RecyclerView>(R.id.recycler_view_categories)
            .bind(CategoryViewHolderFactory())
            .layoutManager(
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            )
            .observeItems(viewModel.getCategories())

    }
}