package com.example.billbill_template.ui.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.billbill_template.Login.signup.RetrofitClient
import com.example.billbill_template.post.GetCategoryManifestResponse
import com.example.billbill_template.post.GetPostsResponse
import retrofit2.Call
import retrofit2.Response

class HomeService() {
    private  lateinit var homeView: HomeView

    fun setPostView(homeView: HomeView) {
        this.homeView = homeView
    }

    fun getPosts(context: Context) {
        RetrofitClient.instance.getPosts().enqueue(object : retrofit2.Callback<GetPostsResponse> {
            override fun onResponse(
                call: Call<GetPostsResponse>,
                response: Response<GetPostsResponse>
            ) {
                Log.d("HomeService", "Response: ${response.body()}")
                if (response.isSuccessful) {
                    val getPostsResponse: GetPostsResponse = response.body()!!
                    Log.d("HomeService", getPostsResponse.toString())
                    homeView.onGetPostsSuccess(getPostsResponse.data!!)
                } else {
                    Log.e("HomeService", "게시글 목록 불러오기 실패 - 오류: ${response.code()} - ${response.message()}")
                    Toast.makeText(context, "게시글 목록을 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetPostsResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getCategories(context: Context) {
        RetrofitClient.instance.getCategoryManifest().enqueue(object : retrofit2.Callback<GetCategoryManifestResponse> {
            override fun onResponse(
                call: Call<GetCategoryManifestResponse>,
                response: Response<GetCategoryManifestResponse>
            ) {
                Log.d("HomeService", "Response: ${response.body()}")
                Log.d("HomeService", "Raw Response: ${response.raw()}")
                if (response.isSuccessful) {
                    val getCategoryResponse : GetCategoryManifestResponse = response.body()!!
                    Log.d("HomeService", getCategoryResponse.toString())
                    homeView.onGetCategoriesSuccess(getCategoryResponse)
                } else {
                    Log.e("HomeService", "카테고리 목록 불러오기 실패 - 오류: ${response.code()} - ${response.message()}")
                    Toast.makeText(context, "카데고리 목록을 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetCategoryManifestResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }

        })
    }
}