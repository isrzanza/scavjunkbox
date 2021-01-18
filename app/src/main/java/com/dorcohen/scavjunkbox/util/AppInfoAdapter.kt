package com.dorcohen.scavjunkbox.util

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dorcohen.scavjunkbox.databinding.ItemAppDetailBinding
import com.dorcohen.scavjunkbox.data.model.AppInfo
import com.dorcohen.scavjunkbox.data.model.getAppIcon


class AppInfoAdapter(private val listener: ClickListener,private val showToggle:Boolean = false) : ListAdapter<AppInfo, AppDetailsViewHolder>(
    AppDetailsDiffUtilCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppDetailsViewHolder {
        val binding = ItemAppDetailBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AppDetailsViewHolder(binding,showToggle)
    }

    override fun onBindViewHolder(holder: AppDetailsViewHolder, position: Int) {
        val appInfo = getItem(position)
        holder.bind(appInfo,listener)
    }

    interface ClickListener {
        fun onContainerClick(appInfo: AppInfo)
        fun onToggle(appInfo: AppInfo)
        fun getApplication():Application
    }
}

class AppDetailsViewHolder(private val binding:ItemAppDetailBinding,private val showToggle: Boolean) : RecyclerView.ViewHolder(binding.root){
    fun bind(appInfo:AppInfo,listener: AppInfoAdapter.ClickListener){
        with(binding){
            appNameTextView.text = appInfo.name
            appInfo.getAppIcon(listener.getApplication())?.apply {
                appIconImageView.setImageDrawable(this)
            }

            if(showToggle){
                toggleAppSwitch.apply {
                    visibility = View.VISIBLE
                    isChecked = appInfo.active
                    setOnClickListener {
                        listener.onToggle(appInfo)
                    }
                }
            }

            appInfoItemContainer.setOnClickListener {
                listener.onContainerClick(appInfo)
            }
        }
    }
}

class AppDetailsDiffUtilCallback() : DiffUtil.ItemCallback<AppInfo>(){
    override fun areItemsTheSame(oldItem: AppInfo, newItem: AppInfo): Boolean = oldItem.packageName == newItem.packageName

    override fun areContentsTheSame(oldItem: AppInfo, newItem: AppInfo): Boolean = oldItem.name == newItem.name
}