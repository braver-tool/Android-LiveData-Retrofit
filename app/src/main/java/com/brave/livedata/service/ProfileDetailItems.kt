/*
 *
 *  * Created by https://github.com/braver-tool
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 25/03/22, 11:00 AM
 *
 */

package com.brave.livedata.service

import com.google.gson.annotations.SerializedName


class ProfileDetailItems {
    @SerializedName("name")
    private var name: String? = ""

    @SerializedName("about")
    private var about: String? = ""

    @SerializedName("link")
    private var link: String? = ""

    @SerializedName("profile_url")
    private var profile_url: String? = ""

    @SerializedName("quote")
    private var quote: String? = ""

    @SerializedName("id")
    private var id: Int? = 0

    constructor()

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getAbout(): String? {
        return about
    }

    fun setAbout(about: String?) {
        this.about = about
    }

    fun getLink(): String? {
        return link
    }

    fun setLink(link: String?) {
        this.link = link
    }

    fun getProfile_url(): String? {
        return profile_url
    }

    fun setProfile_url(profile_url: String?) {
        this.profile_url = profile_url
    }

    fun getQuote(): String? {
        return quote
    }

    fun setQuote(quote: String?) {
        this.quote = quote
    }

    fun getId(): Int? {
        return id
    }

}