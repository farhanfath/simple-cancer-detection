package com.dicoding.asclepius.view.result

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.view.result.news.NewsAdapter
import com.dicoding.asclepius.view.result.news.NewsViewModel
import com.dicoding.asclepius.view.result.news.api.ArticlesItem
import kotlinx.coroutines.launch
import org.tensorflow.lite.task.vision.classifier.Classifications

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var viewModel: ResultViewModel
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var image: String
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this)[ResultViewModel::class.java]
        newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        analyzeResultSetup()
    }

    private fun analyzeResultSetup() {
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        imageUri.let {
            Log.d(TAG, "ShowImage: $it")
            binding.resultImage.setImageURI(it)
        }
        image = imageUri.toString()

        val imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    Log.d(TAG, "ShowImage: $imageUri")
                }

                @SuppressLint("SetTextI18n")
                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    results?.let {
                        val topResult = it[0]
                        val label = topResult.categories[0].label
                        val finalScore = topResult.categories[0].score

                        fun Float.formatToString(): String {
                            return String.format("%.2f%%", this * 100)
                        }
                        resultLabelCustom(label)
                        binding.resultScore.text = finalScore.formatToString()
                        viewModel.addToHistory(id, label, finalScore, image)
                    }
                }
            }
        )
        imageClassifierHelper.classifyStaticImage(imageUri)
    }

    private fun resultLabelCustom(label: String) {
        if (label == "Cancer") {
            binding.resultName.text = label
            binding.resultName.setTextColor(resources.getColor(R.color.red))
            getNews("kanker")
        } else {
            binding.resultName.text = label
            binding.resultName.setTextColor(resources.getColor(R.color.green))
            getNews("kesehatan")
        }
    }

    private fun getNews(category: String) {
        val apiKey = "961e7609fd264e679b86297ff001344d"
        lifecycleScope.launch {
            newsViewModel.getNews(category, apiKey)
            newsViewModel.news.observe(this@ResultActivity) { news ->
                newsAdapterHandler(news)
            }
            newsViewModel.loading.observe(this@ResultActivity) {
                showLoading(it)
            }
        }
    }

    private fun newsAdapterHandler(news: List<ArticlesItem>) {
        adapter = NewsAdapter(news)
        binding.rvNews.adapter = adapter
        binding.rvNews.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.newsProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_img_uri"
        const val TAG = "imagePicker"
        const val EXTRA_ID = "extra_id"
    }
}