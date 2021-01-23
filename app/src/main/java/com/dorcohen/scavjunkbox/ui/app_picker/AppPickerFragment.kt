package com.dorcohen.scavjunkbox.ui.app_picker

import android.app.Application
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dorcohen.scavjunkbox.closeKeyboard
import com.dorcohen.scavjunkbox.data.model.AppInfo
import com.dorcohen.scavjunkbox.databinding.FragmentAppPickerBinding
import com.dorcohen.scavjunkbox.util.AppInfoAdapter
import com.dorcohen.scavjunkbox.util.DefaultViewModelFactory

class AppPickerFragment : Fragment(), AppInfoAdapter.ClickListener {
    private lateinit var appPickerViewModel: AppPickerViewModel
    private val recyclerAdapter: AppInfoAdapter = AppInfoAdapter(this)
    private lateinit var binding: FragmentAppPickerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vmFactory = DefaultViewModelFactory(requireActivity().application)
        appPickerViewModel = ViewModelProvider(this, vmFactory).get(AppPickerViewModel::class.java)

        return FragmentAppPickerBinding
            .inflate(inflater, container, false)
            .apply {
                binding = this

                with(appListRecyclerView) {
                    layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    adapter = recyclerAdapter
                }

                viewModel = appPickerViewModel
                lifecycleOwner = viewLifecycleOwner
            }
            .root
    }

    override fun onResume() {
        super.onResume()
        appPickerViewModel.appList.observe(viewLifecycleOwner) {
            recyclerAdapter.submitList(it)
        }
        appPickerViewModel.refreshAppList(requireActivity().application)
    }
    
    override fun onContainerClick(appInfo: AppInfo) {
        requireContext().closeKeyboard(binding.root)
        appPickerViewModel.addApp(appInfo, findNavController())
    }

    override fun onToggle(appInfo: AppInfo) {
        //do nothing
    }

    override fun getApplication(): Application = requireActivity().application
}