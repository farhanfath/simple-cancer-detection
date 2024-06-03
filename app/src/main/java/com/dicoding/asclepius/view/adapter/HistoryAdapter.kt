package com.dicoding.asclepius.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.databinding.ItemHistoryBinding
import com.dicoding.asclepius.view.data.DataCancer
import com.dicoding.asclepius.view.result.ResultViewModel

class HistoryAdapter(private val viewModel: ResultViewModel) : RecyclerView.Adapter<HistoryAdapter.UserViewHolder>() {

    private val list = ArrayList<DataCancer>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(users: ArrayList<DataCancer>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataCancer) {
            binding.apply {
                resultTv.text = data.result
                scoreTv.text = data.score.toString()
                Glide.with(itemView)
                    .load(data.image)
                    .centerCrop()
                    .into(imgCancer)

                removeHistoryBtn.setOnClickListener {
                    viewModel.removeDataFromHistory(data.id)
                    historyRemoveToast()
                }
            }
        }

        private fun historyRemoveToast() {
            val context = binding.root.context
            Toast.makeText(context, "Removed from History", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}