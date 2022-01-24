/*
 *
 *  * Created by https://github.com/braver-tool
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 24/01/22, 11:45 AM
 *
 */

package com.brave.livedata.service

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("leaders.json")
    fun getProfileDetails(): Call<List<ProfileDetailItems>>
}