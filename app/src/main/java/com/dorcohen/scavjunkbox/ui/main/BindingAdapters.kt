package com.dorcohen.scavjunkbox.ui.main

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dorcohen.scavjunkbox.data.model.AppInfo
import com.dorcohen.scavjunkbox.util.AppInfoAdapter

@BindingAdapter("mainFragmentRecyclerDataSet")
fun submitAppList(recycler:RecyclerView,dataSet:List<AppInfo>?){
    if(dataSet != null){
        try{
            if(recycler.adapter != null){
                val adapter = recycler.adapter as AppInfoAdapter
                adapter.submitList(dataSet)
            } else Log.e("submitAppList","recycler adapter is null")
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}