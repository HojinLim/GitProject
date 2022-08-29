package com.example.gitproject.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitproject.R
import com.example.gitproject.adapter.BookAdapter
import com.example.gitproject.api.BookService
import com.example.gitproject.databinding.FragmentHomeBinding
import com.example.gitproject.model.BestSellerDto
import com.example.gitproject.model.SearchBookDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment: Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: BookAdapter
    private lateinit var bookService: BookService


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding= FragmentHomeBinding.inflate(layoutInflater)

        //super.onViewCreated(view, savedInstanceState)


        initBookRecyclerView()

        val retrofit= Retrofit.Builder()
            .baseUrl("https://book.interpark.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        bookService = retrofit.create(BookService::class.java)

        bookService.getBestSellerBooks(getString(R.string.interparkAPIKey))
            .enqueue(object: Callback<BestSellerDto> {
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
                        adapter.submitList(it.books)

                    }
                }
                override fun onFailure(call: Call<BestSellerDto>, t: Throwable) {
                    // TODO 실패처리
                    Log.e(TAG, t.toString())
                }
            })
        binding.searchEditText.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == MotionEvent.ACTION_DOWN){
                search(binding.searchEditText.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        return binding.root
    }
    private fun search(keyword: String) {
        bookService.getBooksByName(getString(R.string.interparkAPIKey), keyword)
            .enqueue(object: Callback<SearchBookDto> {
                override fun onResponse(call: Call<SearchBookDto>, response: Response<SearchBookDto>) {
                    // TODO 성공처리
                    if(response.isSuccessful.not()){
                        Log.e(TAG, "Not!! SUCCESS")
                        return
                    }
                    adapter.submitList(response.body()?.books.orEmpty())

                }
                override fun onFailure(call: Call<SearchBookDto>, t: Throwable) {
                    // TODO 실패처리
                    Log.e(TAG, t.toString())
                }
            })

    }


    fun initBookRecyclerView(){
        adapter= BookAdapter()

        binding.bookRecyclerView.layoutManager= LinearLayoutManager(context)
        binding.bookRecyclerView.adapter= adapter
    }
    companion object {
        private const val TAG= "HomeFragment"
    }
}