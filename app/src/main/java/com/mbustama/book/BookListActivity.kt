package com.mbustama.book

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_book_list.*
import kotlinx.android.synthetic.main.book_list.*
import modelo.BookModel.ITEMS


class BookListActivity : AppCompatActivity() {

    private var mTwoPane: Boolean = false
    private var authorIsAsc: Boolean = true
    private var titleIsAsc: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)
        setSupportActionBar(toolbar)

        if (book_detail_container != null) { // values-w900dp
            mTwoPane = true
        }
        setupRecyclerView(book_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {

        recyclerView.layoutManager = StaggeredGridLayoutManager(2, 1)

        recyclerView.adapter = SimpleItemRecyclerViewAdapter(
            this,
            ITEMS,
            mTwoPane
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sortbyauthor -> {
                authorIsAsc = if (authorIsAsc) {
                    ITEMS.sortBy { it.author }
                    false
                } else {
                    ITEMS.sortByDescending { it.author }
                    true
                }
                setupRecyclerView(book_list)
                true
            }
            R.id.sortbytitle -> {
                titleIsAsc = if (titleIsAsc) {
                    ITEMS.sortBy { it.title }
                    false
                } else {
                    ITEMS.sortByDescending { it.title }
                    true
                }
                setupRecyclerView(book_list)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
