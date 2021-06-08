package com.nminin.izibookexample.ui.category.model

data class ExpandableItem<T>(
    var isExpanded: Boolean = false,
    var item: T,
)