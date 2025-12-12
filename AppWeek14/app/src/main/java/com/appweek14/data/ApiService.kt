package com.appweek14.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Api interface
interface JsonPlaceholderApi {
    @GET("users") //비동기 처리를 하고 싶으면 suspend를 쓰는 게 좋음
    suspend fun getUsers() : List<User>
}

//인터페이스는 구현 부분이 없음 대체로


// Singleton   synchronized 키워드로 싱글톤 패턴 구현 -> 자바에서는 enum (Enum은 딱 하나밖에 가질수가 없음)/ 코틀린에서는 object
//자바 싱글톤 enum 코틀린 object
object RetrofitClient{  //users는 안쓰는 이유가 get방식으로 우리가 리다이렉션 하기 대문
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"


    val api: JsonPlaceholderApi by lazy{ //늦게 바인딩 위함   //api는 getUsers()를 사용할 수 있음
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) //json으로 되어있는 것을 자바에서 다룰 수 있게 역직렬화를 함
            .build()
            .create(JsonPlaceholderApi::class.java)
    }

}