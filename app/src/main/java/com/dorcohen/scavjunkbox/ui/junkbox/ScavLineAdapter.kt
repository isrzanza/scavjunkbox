package com.dorcohen.scavjunkbox.ui.junkbox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dorcohen.scavjunkbox.data.model.ScavLine
import com.dorcohen.scavjunkbox.databinding.ItemScavLineBinding
import java.util.*

class ScavLineAdapter(private val listener:ScavLineAdapter.OnClickListener) : ListAdapter<ScavLine, ScavLineViewHolder>(ScavLineDiffUtilCallback()) {
    interface OnClickListener {
        fun onClick(scavLine:ScavLine)
        fun onLongClick(scavLine: ScavLine)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScavLineViewHolder {
        val binding = ItemScavLineBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ScavLineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScavLineViewHolder, position: Int) =  holder.bind( currentList[position],listener)
}

class ScavLineDiffUtilCallback : DiffUtil.ItemCallback<ScavLine>(){
    override fun areItemsTheSame(oldItem: ScavLine, newItem: ScavLine): Boolean = oldItem.resId == newItem.resId

    override fun areContentsTheSame(oldItem: ScavLine, newItem: ScavLine): Boolean = oldItem.name == newItem.name
}

class ScavLineViewHolder(val binding: ItemScavLineBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(scavLine:ScavLine,listener:ScavLineAdapter.OnClickListener){
        with(binding){
            trackNameTextView.text = scavLine.name
            playAudioImageButton.apply {
                setOnClickListener {
                    listener.onClick(scavLine)
                }
                setOnLongClickListener {
                    listener.onLongClick(scavLine)
                    true
                }
            }
        }
    }
}