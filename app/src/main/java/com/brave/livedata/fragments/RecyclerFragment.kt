/*
 *
 *  * Created by https://github.com/braver-tool
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 25/03/22, 11:00 AM
 *
 */

package com.brave.livedata.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brave.livedata.R
import com.brave.livedata.databinding.FragmentRecyclerBinding
import com.brave.livedata.service.ProfileDetailItems
import com.brave.livedata.viewmodel.RecyclerViewModel
import com.braver.utils.AppUtils
import java.util.*

class RecyclerFragment : Fragment(), RecyclerAdapter.RecyclerViewClickListener {
    private var _binding: FragmentRecyclerBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerViewModel: RecyclerViewModel
    private val webPageUrl: String = "https://github.com/braver-tool/Android-LiveData-Retrofit"
    private val alertNoData: String = "No Data"
    private val alertNoInternet: String = "No Internet"
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private val profileDetailItemsList: MutableList<ProfileDetailItems> = ArrayList()
    private var isLoaded = false
    private var recyclerAdapter: RecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        recyclerViewModel = ViewModelProvider(this).get(RecyclerViewModel::class.java)
        _binding = FragmentRecyclerBinding.inflate(inflater, container, false)
        initializeViews()
        return binding.root
    }

    override fun onRecyclerViewItemClick(action: Int, data: Any?) {
        if (action == 2601) {
            val selectedProfile = data as String
            val bundle =
                bundleOf("arg_selected_profile" to selectedProfile)
            findNavController().navigate(R.id.action_profile_to_web, bundle)
        }
    }

    private fun initializeViews() {
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.profileRecyclerView.layoutManager = linearLayoutManager
        binding.profileRecyclerView.setHasFixedSize(true)
        if (AppUtils.isNetworkAvailable(context)) {
            binding.progressCircular.visibility = View.VISIBLE
            recyclerViewModel.getProfileModels().observe(viewLifecycleOwner) { profileList ->
                binding.progressCircular.visibility = View.GONE
                if (profileList.isNotEmpty()) {
                    profileDetailItemsList.addAll(profileList)
                    recyclerAdapter = RecyclerAdapter(profileDetailItemsList, this)
                    binding.profileRecyclerView.adapter = recyclerAdapter
                } else {
                    binding.noDataText.text = alertNoData
                    binding.noDataText.visibility = View.VISIBLE
                }
            }
        } else {
            binding.noDataText.text = alertNoInternet
            binding.noDataText.visibility = View.VISIBLE
        }
        binding.gitHubLogoImageView.setOnClickListener {
            if (AppUtils.isNetworkAvailable(context)) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(webPageUrl)
                startActivity(intent)
            }
        }

        binding.profileRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0) return
                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                val offsetCount: Int = profileDetailItemsList.size
                if (!isLoaded && totalItemCount <= lastVisibleItem + 1 && offsetCount > 5) {
                    binding.progressCircular.visibility = View.VISIBLE
                    isLoaded = true
                    recyclerViewModel.getProfileModels()
                        .observe(viewLifecycleOwner) { profileList ->
                            binding.progressCircular.visibility = View.GONE
                            binding.profileRecyclerView.stopScroll()
                            if (profileList.isNotEmpty()) {
                                if (profileDetailItemsList.isNotEmpty() && recyclerAdapter != null) {
                                    profileDetailItemsList.addAll(profileList)
                                    isLoaded = false
                                    recyclerAdapter!!.notifyItemRangeInserted(
                                        offsetCount,
                                        profileDetailItemsList.size
                                    )
                                    //recyclerAdapter!!.notifyDataSetChanged()
                                }
                            }
                        }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}