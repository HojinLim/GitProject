package com.example.gitproject.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gitproject.R
import com.example.gitproject.api.BookService
import com.example.gitproject.model.BestSellerDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment: Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val retrofit= Retrofit.Builder()
            .baseUrl("https://book.interpark.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val bookService = retrofit.create(BookService::class.java)

        bookService.getBestSellerBooks("0FEE7726481B277F234000B0E350C709AD36DC97C7838A484C66F90DA11B45C3")
            .enqueue(object: Callback<BestSellerDto>{
                override fun onResponse(call: Call<BestSellerDto>, response: Response<BestSellerDto>) {
                    // TODO 성공처리
                    if(response.isSuccessful.not()){
                        Log.e(TAG, "Not!! SUCCESS")
                        return
                    }
                    response.body()?.let {
                        Log.d(TAG, it.toString())

                        it.books.forEach { book ->
                            Log.d(TAG, book.toString())
                        }
                    }
                }
                override fun onFailure(call: Call<BestSellerDto>, t: Throwable) {
                    // TODO 실패처리
                }
            })
    }
}