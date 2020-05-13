package com.example.coronawatch.Retrofit

import com.example.taskmanager.ToDos
import com.example.taskmanager.ToDosItem
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*


interface IAPI {

    @get:GET("todos")
    val todos:Observable<ToDos>

    @FormUrlEncoded
    @POST("todos")
    fun addToDo(@Field("title") title: String,
                @Field("userId") userId: Int ,
                @Field("completed") completed:Boolean
    ): Call<ToDosItem>


    @PUT("todos/{id}")
    fun updateToDO(@Path("id") id: Int, @Body todo: ToDosItem): Call<ToDosItem>

    @DELETE("todos/{id}")
    fun deleteToDo(@Path("id") id: Int): Call<Void>






}