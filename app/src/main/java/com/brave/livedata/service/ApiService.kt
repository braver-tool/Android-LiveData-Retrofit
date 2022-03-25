/*
 *
 *  * Created by https://github.com/braver-tool
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 25/03/22, 11:00 AM
 *
 */

package com.brave.livedata.service

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("braver-tool/Android-LiveData-Retrofit/main/leaders.json")
    fun getProfileDetails(): Call<List<ProfileDetailItems>>
}