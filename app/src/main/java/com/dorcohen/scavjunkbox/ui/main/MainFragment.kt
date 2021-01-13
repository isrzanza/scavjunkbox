package com.dorcohen.scavjunkbox.ui.main

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dorcohen.scavjunkbox.R
import com.dorcohen.scavjunkbox.data.model.AppInfo
import com.dorcohen.scavjunkbox.databinding.FragmentMainBinding
import com.dorcohen.scavjunkbox.util.AppInfoAdapter
import com.dorcohen.scavjunkbox.util.DefaultViewModelFactory

class MainFragment : Fragment(),AppInfoAdapter.ClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val adapter = AppInfoAdapter(this,showToggle = true)
    private lateinit var binding:FragmentMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vmFactory = DefaultViewModelFactory(requireActivity().application)
        mainViewModel = ViewModelProvider(this,vmFactory).get(MainViewModel::class.java)

        return FragmentMainBinding
            .inflate(inflater,container,false)
            .apply {
                binding = this
                appListRecycler.adapter = adapter
                lifecycleOwner = viewLifecycleOwner
                viewModel = mainViewModel
            }
            .root
    }

    override fun onClick(appInfo: AppInfo) {
        mainViewModel.toggleApp(appInfo)
    }

    override fun getApplication(): Application = requireActivity().application
}