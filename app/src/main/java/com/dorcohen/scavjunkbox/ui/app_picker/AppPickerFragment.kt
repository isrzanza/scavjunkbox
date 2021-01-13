package com.dorcohen.scavjunkbox.ui.app_picker

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dorcohen.scavjunkbox.data.model.AppInfo
import com.dorcohen.scavjunkbox.databinding.FragmentAppPickerBinding
import com.dorcohen.scavjunkbox.util.AppInfoAdapter
import com.dorcohen.scavjunkbox.util.DefaultViewModelFactory

class AppPickerFragment : Fragment(), AppInfoAdapter.ClickListener {
    private lateinit var appPickerViewModel:AppPickerViewModel
    private lateinit var recyclerAdapter: AppInfoAdapter
    private lateinit var binding:FragmentAppPickerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(!::recyclerAdapter.isInitialized)recyclerAdapter = AppInfoAdapter(this)

        val vmFactory = DefaultViewModelFactory(requireActivity().application)
        appPickerViewModel = ViewModelProvider(this,vmFactory).get(AppPickerViewModel::class.java)

        return FragmentAppPickerBinding
            .inflate(inflater,container,false)
            .apply {
                binding = this
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.appListRecyclerView){
           layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
           adapter = recyclerAdapter
           recyclerAdapter.submitList(appPickerViewModel.getInstalledAppList(requireActivity().application))
        }
    }

    override fun onClick(appInfo: AppInfo) {
        appPickerViewModel.addApp(appInfo)
        findNavController().navigateUp()
    }

    override fun getApplication(): Application = requireActivity().application
}