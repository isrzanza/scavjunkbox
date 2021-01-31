package com.dorcohen.scavjunkbox.ui.junkbox

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dorcohen.scavjunkbox.R
import com.dorcohen.scavjunkbox.data.model.ScavLine
import com.dorcohen.scavjunkbox.data.model.getTempFile
import com.dorcohen.scavjunkbox.data.model.getUri
import com.dorcohen.scavjunkbox.databinding.FragmentJunkBoxBinding
import com.dorcohen.scavjunkbox.util.DefaultViewModelFactory
import com.dorcohen.scavjunkbox.util.audio.AudioHelper
import com.dorcohen.scavjunkbox.util.audio.IAudioHelper

class JunkBoxFragment : Fragment(), IAudioHelper by AudioHelper(AudioHelper.mediaAtr), ScavLineAdapter.OnClickListener {
    companion object {
        fun newInstance() = JunkBoxFragment()
    }

    private val recyclerAdapter = ScavLineAdapter(this)
    private lateinit var binding: FragmentJunkBoxBinding
    private lateinit var junkBoxViewModel: JunkBoxViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val vmFactory = DefaultViewModelFactory(requireActivity().application)
        junkBoxViewModel = ViewModelProvider(this, vmFactory).get(JunkBoxViewModel::class.java)

        return FragmentJunkBoxBinding
            .inflate(inflater, container, false)
            .apply {
                binding = this
                with(junkboxRecycler) {
                    adapter = recyclerAdapter
                    layoutManager = GridLayoutManager(requireContext(), 3)
                    recyclerAdapter.submitList(junkBoxViewModel.scavLines)
                }
                faqImageButton.setOnClickListener {
                    findNavController().navigate(R.id.action_global_faqFragment)
                }
            }.root
    }

    override fun onClick(scavLine: ScavLine) =
        playAudioResource(scavLine.getUri(requireContext()), requireContext())

    override fun onLongClick(scavLine: ScavLine) {
        sendScavLine(scavLine)
    }

    private fun sendScavLine(scavLine: ScavLine) {
        try {
            val file = scavLine.getTempFile(requireContext())
            val uri = FileProvider
                .getUriForFile(
                    requireContext(),
                    "${requireContext().packageName}.provider",
                    file
                )

            with(Intent(Intent.ACTION_SEND)) {
                type = "audio/mp3"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                putExtra(Intent.EXTRA_STREAM, uri)
                val title = resources.getString(R.string.junkbox_share_intent_title)
                startActivity(Intent.createChooser(this, title))
            }
        } catch (e: Exception) {
            junkBoxViewModel.setStatus(getString(R.string.error_cannot_share))
            e.printStackTrace()
        }
    }
}