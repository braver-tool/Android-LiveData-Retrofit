/*
 *
 *  * Created by https://github.com/braver-tool
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 25/03/22, 11:00 AM
 *
 */

package com.brave.livedata.fragments

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brave.livedata.ImageUtils
import com.brave.livedata.R
import com.brave.livedata.databinding.AdapterRecyclerDataBinding
import com.brave.livedata.service.ProfileDetailItems
import com.bumptech.glide.Glide


class RecyclerAdapter(
    private val profileDetailItemsList: List<ProfileDetailItems>,
    private val listener: RecyclerViewClickListener,
) :
    RecyclerView.Adapter<RecyclerAdapter.ProfileViewHolder>() {
    private lateinit var context: Context
    override fun getItemCount() = profileDetailItemsList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: AdapterRecyclerDataBinding =
            AdapterRecyclerDataBinding.inflate(layoutInflater, parent, false)
        return ProfileViewHolder(itemBinding)
    }


    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.adapterRecyclerDataBinding.aboutLeaderTextView.text =
            profileDetailItemsList[position].getAbout()
        holder.adapterRecyclerDataBinding.leaderNameTextView.text =
            profileDetailItemsList[position].getName()
        val quote = "“" + profileDetailItemsList[position].getQuote() + "”"
        holder.adapterRecyclerDataBinding.leaderQuoteTextView.text =
            quote
        try {
            val bitmap: Bitmap = ImageUtils.getLeaderProfile(context, position)!!
            Glide.with(context).load(bitmap)
                .placeholder(R.drawable.default_profile)
                .into(holder.adapterRecyclerDataBinding.leaderProfileImageView)
        } catch (e: Exception) {
        }
        holder.adapterRecyclerDataBinding.knowMoreTextView.setOnClickListener {
            listener.onRecyclerViewItemClick(2601, profileDetailItemsList[position].getLink())
        }
    }

    inner class ProfileViewHolder(val adapterRecyclerDataBinding: AdapterRecyclerDataBinding) :
        RecyclerView.ViewHolder(adapterRecyclerDataBinding.root)

    interface RecyclerViewClickListener {
        fun onRecyclerViewItemClick(action: Int, data: Any?)
    }
}