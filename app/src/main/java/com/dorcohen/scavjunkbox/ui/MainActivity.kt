package com.dorcohen.scavjunkbox.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.dorcohen.scavjunkbox.R
import com.dorcohen.scavjunkbox.databinding.ActivityMainBinding
import com.dorcohen.scavjunkbox.util.DefaultViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
        private const val RC = 777

        private val fullScreenFragments = listOf(
            R.id.appPickerFragment,
            R.id.faqFragment
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Setup viewModel
        val vmFactory = DefaultViewModelFactory(application)
        viewModel = ViewModelProvider(this, vmFactory).get(MainViewModel::class.java)

        //Setup system message
        viewModel.systemMessage.observe(this, Observer { message ->
            message.toast(this)
        })

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)

        setupBottomNav(navController)

        setupDestinationChangedListener(navController)
    }

    private fun setupDestinationChangedListener(navController: NavController) {
        navController
            .addOnDestinationChangedListener { _, destination, _ ->
                binding.bottomNavigationView.apply {
                    visibility =
                        if (destination.id in fullScreenFragments) View.GONE else View.VISIBLE
                }
                if (destination.id == R.id.notificationsFragment) {
                    //Check notification access
                    if (!checkNotificationAccessPermission()) requestNotificationAccessPermission()
                }
            }
    }

    private fun setupBottomNav(navController: NavController) {
        with(binding.bottomNavigationView) {
            selectedItemId = R.id.go_to_junkboxFragment
            setOnNavigationItemSelectedListener {
                with(viewModel) {
                    when (it.itemId) {
                        R.id.go_to_notificationsFragment -> navigateTo(navController, R.id.notificationsFragment)
                        R.id.go_to_aboutFragment -> navigateTo(navController, R.id.aboutFragment)
                        R.id.go_to_junkboxFragment -> navigateTo(navController, R.id.junkBoxFragment
                        )
                        else -> false
                    }
                }
            }
        }
    }

    private fun checkNotificationAccessPermission(): Boolean {
        val enabledNotificationListeners: String =
            Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        return enabledNotificationListeners.contains(packageName)
    }

    private fun requestNotificationAccessPermission() {
        val dialog =
            AlertDialog.Builder(this, R.style.AlertDialogCustom)
                .setTitle(getString(R.string.notification_listener_permission_title))
                .setMessage(getString(R.string.notification_listener_permission_explanation))
                .setPositiveButton(getString(R.string.notification_listener_permission_positive_button))
                { _, _ ->
                    startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
                }
                .setNegativeButton(getString(R.string.notification_listener_permission_negative_button))
                { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorSecondary))
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            .setTextColor(getColor(R.color.colorSecondaryDark))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RC) {
            Log.d(TAG, "on request permission result, granted: ${grantResults.first()}")
        }
    }

}