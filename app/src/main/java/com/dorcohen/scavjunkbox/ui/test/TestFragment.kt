package com.dorcohen.scavjunkbox.ui.test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dorcohen.scavjunkbox.R
import com.dorcohen.scavjunkbox.util.audio.AudioHelper
import com.dorcohen.scavjunkbox.util.audio.IAudioHelper
import com.dorcohen.scavjunkbox.databinding.FragmentTestBinding
import com.dorcohen.scavjunkbox.util.notifications.INotificationHelper
import com.dorcohen.scavjunkbox.util.notifications.NotificationHelper
import com.dorcohen.scavjunkbox.resIdToUri
import com.dorcohen.scavjunkbox.util.scav.IScavHelper
import com.dorcohen.scavjunkbox.util.scav.ScavHelper


class TestFragment : Fragment(),
    INotificationHelper by NotificationHelper,
    IAudioHelper by AudioHelper,
    IScavHelper by ScavHelper{
    private lateinit var binding: FragmentTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentTestBinding
            .inflate(inflater, container, false)
            .apply {
                binding = this
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            testNotificationButton.setOnClickListener {
                Log.d(TAG, "click!")
                notify(
                    requireContext(),
                    1,
                    NotificationHelper.CHANNEL_MAIN,
                    "test title",
                    "test body",
                    null,
                )
            }
            testSoundButton.setOnClickListener {
                val uri = requireContext().resIdToUri(getRandomAudioResource())
                Log.d(TAG,uri.toString())
                playAudioResource(uri,requireContext())
            }

            testButton.setOnClickListener {
                startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
            }
        }
    }

    companion object {
        private const val TAG = "TestFragment"

        @JvmStatic
        fun newInstance() = TestFragment()
    }
}