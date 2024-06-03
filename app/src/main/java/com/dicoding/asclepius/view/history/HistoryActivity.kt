package com.dicoding.asclepius.view.history

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityHistoryBinding
import com.dicoding.asclepius.view.adapter.HistoryAdapter
import com.dicoding.asclepius.view.data.DataCancer
import com.dicoding.asclepius.view.result.ResultViewModel

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var viewModel: HistoryViewModel
    private lateinit var adapter: HistoryAdapter
    private lateinit var removeViewModel : ResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        viewmodelHandler()

        rvHandler()
        showHistoryDataHandler()
    }

    private fun viewmodelHandler() {
        viewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        removeViewModel = ViewModelProvider(this)[ResultViewModel::class.java]
    }

    private fun showHistoryDataHandler() {
        viewModel.getCancerHistory()?.observe(this) { data ->
            if (data != null) {
                val list = mapList(data)
                adapter.setList(list)
            }
        }
    }

    private fun rvHandler() {
        adapter = HistoryAdapter(removeViewModel)
        adapter.notifyDataSetChanged()

        binding.apply {
            rvHistory.setHasFixedSize(true)
            rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)
            rvHistory.adapter = adapter
        }
    }

    private fun mapList(users: List<DataCancer>): ArrayList<DataCancer> {
        val listUsers = ArrayList<DataCancer>()
        for (data in users) {
            val userMapped = DataCancer(
                data.id,
                data.result,
                data.score,
                data.image
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}