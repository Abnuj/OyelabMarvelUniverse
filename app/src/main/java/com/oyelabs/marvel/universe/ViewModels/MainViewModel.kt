package com.oyelabs.marvel.universe.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.oyelabs.marvel.universe.Repositories.ExamplePagingSource
import com.oyelabs.marvel.universe.Repositories.Repository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val repository = Repository()
    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 20)
    ) {
        ExamplePagingSource(repository)
    }.flow
        .cachedIn(viewModelScope)
}
