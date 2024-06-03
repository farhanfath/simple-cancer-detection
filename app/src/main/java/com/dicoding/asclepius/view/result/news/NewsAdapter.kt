package com.dicoding.asclepius.view.result.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ItemNewsBinding
import com.dicoding.asclepius.view.result.dateFormat
import com.dicoding.asclepius.view.result.news.api.ArticlesItem

class NewsAdapter(private var listNews: List<ArticlesItem>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listNews.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listNews[position])
    }

    class ViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: ArticlesItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(news.urlToImage)
                    .placeholder(R.drawable.placeholder)
                    .centerCrop()
                    .into(newsThumbnail)

                newsTitle.text = news.author
                newsDate.text = news.publishedAt.dateFormat()
                newsDescription.text = news.title
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ArticlesItem)
    }
}