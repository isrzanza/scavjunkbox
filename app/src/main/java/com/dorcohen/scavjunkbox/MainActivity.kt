package com.dorcohen.scavjunkbox

import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
        private const val RC = 777

        private val fullScreenFragments = listOf(
            R.id.appPickerFragment
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val permissionGranted = checkPermission()
        Log.d(TAG, "permission granted: $permissionGranted")
        if (!permissionGranted) {
            requestPermission()
        }
        findNavController(R.id.nav_host_fragment)
            .addOnDestinationChangedListener { _, destination, _ ->
                val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
                if(destination.id in fullScreenFragments){
                    bottomNav.visibility = View.GONE
                } else bottomNav.visibility = View.VISIBLE
            }
    }

    private fun checkPermission(): Boolean {
        val enabledNotificationListeners: String =
            Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        return enabledNotificationListeners.contains(packageName)
    }

    private fun requestPermission() {
        val dialog =
            AlertDialog.Builder(this)
                .setMessage(getString(R.string.notification_listener_permission_explanation))
                .setPositiveButton(getString(R.string.notification_listener_permission_positive_button))
                { _, _ ->
                    startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
                }
                .create()
        dialog.show()
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