/*
 *
 *  * Created by https://github.com/braver-tool
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 24/01/22, 11:45 AM
 *
 */

package com.brave.livedata.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brave.livedata.service.ProfileDetailItems
import com.brave.livedata.service.ProfileRepository

class RecyclerViewModel : ViewModel() {
    private var profileRepository: ProfileRepository? = ProfileRepository()

    fun getProfileModels(): MutableLiveData<List<ProfileDetailItems>> {
        return profileRepository!!.getMutableLiveData()
    }
}