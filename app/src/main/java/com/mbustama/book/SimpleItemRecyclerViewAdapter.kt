package com.mbustama.book

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.book_list_content.view.*
import modelo.BookModel
import java.io.IOException


class SimpleItemRecyclerViewAdapter(
    private val mParentActivity: BookListActivity,
    private val mValues: List<BookModel.BookItem>,
    private val mTwoPane: Boolean
) :
    RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    companion object{
        private const val EVEN:Int = 0
        private const val ODD:Int = 1
    }

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as BookModel.BookItem
            if (mTwoPane) {
                val fragment = BookDetailFragment().apply {
                    arguments = Bundle()
                    arguments!!.putString(
                        BookDetailFragment.ARG_ITEM_ID,
                        item.identifier.toString()
                    )
                }
                mParentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.book_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(v.context, BookDetailActivity::class.java).apply {
                    putExtra(BookDetailFragment.ARG_ITEM_ID, item.identifier.toString())
                }
                v.context.startActivity(intent)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = if( viewType == 0 ) { //even
            LayoutInflater.from(parent.context).inflate(
                R.layout.book_list_content_even,
                parent,
                false
            )
        } else { //odd
            LayoutInflater.from(parent.context).inflate(
                R.layout.book_list_content_odd,
                parent,
                false
            )
        }
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if( position % 2 == 0 ) {
            EVEN
        } else {
            ODD
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]

        holder.mContentTitle.text = item.title
        holder.mContentAuthor.text = item.author
        try {
            holder.mContentImage.setImageBitmap(
                BitmapFactory.decodeStream(
                    mParentActivity.assets?.open(
                        item.URLimage.toString()
                    )
                )
            )
        } catch (e: IOException) {

        }

        with(holder.itemView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mContentTitle: TextView = mView.title
        val mContentAuthor: TextView = mView.author
        val mContentImage: ImageView = mView.book_image
    }
}