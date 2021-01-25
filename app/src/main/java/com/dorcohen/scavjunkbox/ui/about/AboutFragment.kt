package com.dorcohen.scavjunkbox.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dorcohen.scavjunkbox.R
import com.dorcohen.scavjunkbox.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    companion object {
        fun newInstance() = AboutFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentAboutBinding
            .inflate(inflater,container,false)
            .apply {
                preOrderImageView.setOnClickListener {
                    openTrakovSite()
                }
            }.root
    }

    private fun openTrakovSite(){
        val url = getString(R.string.buy_tarkov_link)
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
            startActivity(this)
        }
    }
}