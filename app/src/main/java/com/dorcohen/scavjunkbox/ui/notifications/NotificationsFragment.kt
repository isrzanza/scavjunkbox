package com.dorcohen.scavjunkbox.ui.notifications

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dorcohen.scavjunkbox.R
import com.dorcohen.scavjunkbox.data.model.AppInfo
import com.dorcohen.scavjunkbox.databinding.FragmentNotificationsBinding
import com.dorcohen.scavjunkbox.util.AppInfoAdapter
import com.dorcohen.scavjunkbox.util.DefaultViewModelFactory


class NotificationsFragment : Fragment(),AppInfoAdapter.ClickListener {

    companion object {
        fun newInstance() = NotificationsFragment()
    }

    private val adapter = AppInfoAdapter(this, showToggle = true)
    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var notificationsFragmentViewModel: NotificationsFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vmFactory = DefaultViewModelFactory(requireActivity().application)
        notificationsFragmentViewModel = ViewModelProvider(this, vmFactory).get(NotificationsFragmentViewModel::class.java)

        return FragmentNotificationsBinding
            .inflate(inflater, container, false)
            .apply {
                binding = this
                appListRecycler.adapter = adapter
                lifecycleOwner = viewLifecycleOwner
                viewModel = notificationsFragmentViewModel
                mainFragmentMenuButton.setOnClickListener {
                    popMenu(it)
                }
            }
            .root
    }

    override fun onContainerClick(appInfo: AppInfo) {
        val intent: Intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            .putExtra(Settings.EXTRA_APP_PACKAGE, appInfo.packageName)
        startActivity(intent)
    }

    override fun onToggle(appInfo: AppInfo) {
        notificationsFragmentViewModel.toggleApp(appInfo)
    }

    override fun getApplication(): Application = requireActivity().application

    private fun popMenu(view:View){
        PopupMenu(requireContext(),view).apply {
            menuInflater.inflate(R.menu.fragment_main_menu,menu)
            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.notification_access_settings -> {
                        startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
                        true
                    }
                    R.id.faq_fragment -> {
                        findNavController().navigate(R.id.action_global_faqFragment)
                        true
                    }
                    else -> false
                }
            }
        }.show()
    }
}