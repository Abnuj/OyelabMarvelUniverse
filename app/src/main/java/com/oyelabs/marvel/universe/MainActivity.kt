package com.oyelabs.marvel.universe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.oyelabs.marvel.universe.Adapter.MainRecylerAdapter
import com.oyelabs.marvel.universe.Adapter.UserComparator
import com.oyelabs.marvel.universe.DataClasses.CharacterData
import com.oyelabs.marvel.universe.Repositories.Repository
import com.oyelabs.marvel.universe.ViewModels.MainViewModel
import com.oyelabs.marvel.universe.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    val viewModel by viewModels<MainViewModel>()
    val pagingAdapter = MainRecylerAdapter(UserComparator)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = Repository()
        binding.mainRecyclerView.adapter = pagingAdapter
        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingdata: PagingData<CharacterData> ->
                pagingAdapter.submitData(pagingdata)
            }
        }
    }
}