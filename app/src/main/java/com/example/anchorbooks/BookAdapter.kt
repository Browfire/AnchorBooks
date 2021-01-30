package com.example.anchorbooks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anchorbooks.model.BookItem


class BookAdapter(val callback: FirstFragment): RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private var bookList = emptyList<BookItem>()

    fun updateAdapter(myList: List<BookItem>) {
        bookList = myList
        notifyDataSetChanged()
    }

    inner class BookViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val listImage: ImageView = itemView.findViewById(R.id.imageRecycler)
        val listTitle: TextView = itemView.findViewById(R.id.tvListTitle)
        val listAuthor: TextView = itemView.findViewById(R.id.tvListAuthor)
        val click = itemView.setOnClickListener {
            callback.passItemInfo(bookList[adapterPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.booklist_item, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookAdapter.BookViewHolder, position: Int) {
        val image = bookList[position].imageLink
        Glide.with(holder.itemView.context).load(image).fitCenter().into(holder.listImage)
        holder.listTitle.text = bookList[position].title
        holder.listAuthor.text = bookList[position].author
    }

    override fun getItemCount() = bookList.size

    interface PassBookData{
        fun passItemInfo(book: BookItem)
    }

}