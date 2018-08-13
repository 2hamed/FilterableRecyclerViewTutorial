package com.hmomeni.filterablerecyclerview

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SimpleRecyclerAdapter(this, viewModel.oldFilteredPosts)

        searchInput
                .textChanges()
                .debounce(200, TimeUnit.MILLISECONDS)
                .subscribe {
                    viewModel
                            .search(it.toString())
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                val diffResult = DiffUtil.calculateDiff(PostsDiffUtilCallback(viewModel.oldFilteredPosts, viewModel.filteredPosts))
                                viewModel.oldFilteredPosts.clear()
                                viewModel.oldFilteredPosts.addAll(viewModel.filteredPosts)
                                diffResult.dispatchUpdatesTo(recyclerView.adapter)
                            }
                }

    }
}
