/*
 *
 *  * Created by https://github.com/braver-tool
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 25/03/22, 11:00 AM
 *
 */

package com.brave.livedata.service

import androidx.lifecycle.MutableLiveData
import com.braver.utils.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ProfileRepository {
    private var profileDetailItemsList: List<ProfileDetailItems> =
        ArrayList<ProfileDetailItems>()
    private val mutableLiveData: MutableLiveData<List<ProfileDetailItems>> = MutableLiveData()

    private fun getApiService(): ApiService {
        return ApiUtils.getRetrofitBuilder("https://raw.githubusercontent.com/")
            .create(ApiService::class.java)
    }

    fun getMutableLiveData(): MutableLiveData<List<ProfileDetailItems>> {
        val apiService: ApiService = getApiService()
        val call: Call<List<ProfileDetailItems>> = apiService.getProfileDetails()
        call.enqueue(object : Callback<List<ProfileDetailItems>?> {
            override fun onResponse(
                call: Call<List<ProfileDetailItems>?>,
                response: Response<List<ProfileDetailItems>?>
            ) {
                if (response.code() == 200 && response.body() != null && response.body()!!
                        .isNotEmpty()
                ) {
                    profileDetailItemsList = response.body()!!
                    mutableLiveData.setValue(profileDetailItemsList)
                } else {
                    mutableLiveData.setValue(profileDetailItemsList)
                }
            }

            override fun onFailure(call: Call<List<ProfileDetailItems>?>, t: Throwable) {
                mutableLiveData.setValue(profileDetailItemsList)
            }
        })
        return mutableLiveData
    }
}