package com.dicoding.asclepius.view.result.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.view.result.news.api.ApiConfig
import com.dicoding.asclepius.view.result.news.api.ArticlesItem
import com.dicoding.asclepius.view.result.news.api.NewsReponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    private val _news = MutableLiveData<List<ArticlesItem>>()
    val news: LiveData<List<ArticlesItem>> = _news

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getNews(query: String, apiKey: String) {
        _loading.value = true
        val response = ApiConfig().getApiService().getNews(query, apiKey)
        response.enqueue(object : Callback<NewsReponse>{
            override fun onResponse(call: Call<NewsReponse>, response: Response<NewsReponse>) {
                if (response.isSuccessful) {
                    _news.value = response.body()!!.articles
                    _error.value = false
                    _loading.value = false
                } else {
                    _error.value = true
                    _loading.value = false
                }
            }

            override fun onFailure(call: Call<NewsReponse>, t: Throwable) {
                _error.value = true
                _loading.value = false
            }
        })
    }
}