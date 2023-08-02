package com.skid.fitnesskittestapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skid.fitnesskittestapp.R
import com.skid.fitnesskittestapp.databinding.HeaderItemBinding
import com.skid.fitnesskittestapp.databinding.LessonItemBinding
import com.skid.fitnesskittestapp.domain.model.HeaderItem
import com.skid.fitnesskittestapp.domain.model.LessonItem
import com.skid.fitnesskittestapp.domain.model.ScheduleListItem

class ScheduleAdapter : ListAdapter<ScheduleListItem, RecyclerView.ViewHolder>(DiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HeaderItem -> R.layout.header_item
            is LessonItem -> R.layout.lesson_item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.header_item -> {
                val binding = HeaderItemBinding.inflate(view, parent, false)
                HeaderViewHolder(binding)
            }

            else -> {
                val binding = LessonItemBinding.inflate(view, parent, false)
                LessonViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is HeaderViewHolder -> holder.bind(item as HeaderItem)
            is LessonViewHolder -> holder.bind(item as LessonItem)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<ScheduleListItem>() {
        override fun areItemsTheSame(
            oldItem: ScheduleListItem, newItem: ScheduleListItem,
        ): Boolean {
            return when {
                oldItem is LessonItem && newItem is LessonItem -> oldItem.id == newItem.id
                oldItem is HeaderItem && newItem is HeaderItem -> oldItem.text == newItem.text
                else -> false
            }
        }

        override fun areContentsTheSame(
            oldItem: ScheduleListItem, newItem: ScheduleListItem,
        ): Boolean {
            return when {
                oldItem is LessonItem && newItem is LessonItem -> oldItem == newItem
                oldItem is HeaderItem && newItem is HeaderItem -> oldItem == newItem
                else -> false
            }
        }

    }

    class LessonViewHolder(
        private val binding: LessonItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(lessonItem: LessonItem) = with(binding) {
            lessonItemColorView.setBackgroundColor(lessonItem.color)
            lessonItemStartTime.text = lessonItem.startTime
            lessonItemEndTime.text = lessonItem.endTime
            lessonItemName.text = lessonItem.name
            lessonItemTrainer.text = lessonItem.coachName
            lessonItemDuration.text = lessonItem.duration
            lessonItemPlace.text = lessonItem.place
        }
    }

    class HeaderViewHolder(
        private val binding: HeaderItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(headerItem: HeaderItem) {
            binding.headerItemText.text = headerItem.text
        }
    }
}