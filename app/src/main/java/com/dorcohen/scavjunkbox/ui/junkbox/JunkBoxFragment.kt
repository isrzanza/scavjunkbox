package com.dorcohen.scavjunkbox.ui.junkbox

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.dorcohen.scavjunkbox.data.model.ScavLine
import com.dorcohen.scavjunkbox.data.model.getUri
import com.dorcohen.scavjunkbox.databinding.JunkBoxFragmentBinding
import com.dorcohen.scavjunkbox.util.DefaultViewModelFactory
import com.dorcohen.scavjunkbox.util.audio.AudioHelper
import com.dorcohen.scavjunkbox.util.audio.IAudioHelper

class JunkBoxFragment : Fragment(), IAudioHelper by AudioHelper, ScavLineAdapter.OnClickListener {
    companion object {
        fun newInstance() = JunkBoxFragment()
    }

    private val recyclerAdapter = ScavLineAdapter(this)
    private lateinit var binding: JunkBoxFragmentBinding
    private lateinit var junkboxViewModel: JunkBoxViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vmFactory = DefaultViewModelFactory(requireActivity().application)
        junkboxViewModel = ViewModelProvider(this,vmFactory).get(JunkBoxViewModel::class.java)

        return JunkBoxFragmentBinding
            .inflate(inflater,container,false)
            .apply {
                binding = this
                with(junkboxRecycler){
                    adapter = recyclerAdapter
                    layoutManager = GridLayoutManager(requireContext(),3)
                    recyclerAdapter.submitList(junkboxViewModel.scavLines)
                }
            }.root
    }

    override fun onClick(scavLine: ScavLine) =  playAudioResource(scavLine.getUri(requireContext()),requireContext())
}