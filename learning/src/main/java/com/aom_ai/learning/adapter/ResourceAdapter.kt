package com.aom_ai.learning.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aom_ai.learning.R
import com.aom_ai.learning.databinding.LlpItemCourseOutlineResourceBinding
import com.aom_ai.learning.ui.model.LearningStatus
import com.aom_ai.learning.ui.model.ResourceType
import com.aom_ai.learning.ui.model.ResourceUiState

class ResourceAdapter : RecyclerView.Adapter<ResourceAdapter.ResourceViewHolder>() {

    private val items = mutableListOf<ResourceUiState>()

    fun setItems(newItems: List<ResourceUiState>) {
        items.clear()
        items.addAll(newItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ResourceViewHolder(
            LlpItemCourseOutlineResourceBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    inner class ResourceViewHolder(private val binding: LlpItemCourseOutlineResourceBinding) : BaseItemViewHolder(binding.root) {

        fun bind(resource: ResourceUiState) {
            setBackgroundColor(resource.lessonStatus)
            val title: String
            val iconDrawableId: Int
            when (resource.resourceType) {
                ResourceType.VIDEO -> {
                    title = binding.root.context.getString(R.string.llp_video_duration, resource.title, resource.duration)
                    iconDrawableId = if (resource.isPlaying) {
                        R.drawable.llp_ic_video_cam_teal
                    } else if (resource.lessonStatus == LearningStatus.NOT_STARTED) {
                        R.drawable.llp_ic_video_cam_grey
                    } else {
                        R.drawable.llp_ic_video_cam
                    }
                }
                ResourceType.AUDIO -> {
                    title = binding.root.context.getString(R.string.llp_video_duration, resource.title, resource.duration)
                    iconDrawableId = if (resource.isPlaying) {
                        R.drawable.llp_ic_headphones_teal
                    } else if (resource.lessonStatus == LearningStatus.NOT_STARTED) {
                        R.drawable.llp_ic_headphones_grey
                    } else {
                        R.drawable.llp_ic_headphones
                    }
                }
                ResourceType.DOCUMENT -> {
                    title = binding.root.context.getString(R.string.llp_doc_duration, resource.title, resource.duration)
                    iconDrawableId = if (resource.isPlaying) {
                        R.drawable.llp_ic_document_teal
                    } else if (resource.lessonStatus == LearningStatus.NOT_STARTED) {
                        R.drawable.llp_ic_document_grey
                    } else {
                        R.drawable.llp_ic_document
                    }
                }
            }
            binding.tvTitle.text = title
            binding.ivIcon.setImageResource(iconDrawableId)
            if (resource.isPlaying) {
                binding.tvTitle.setTextColor(ContextCompat.getColor(binding.root.context, com.aom_ai.uc_component.R.color.llp_primary_teal))
            } else if (resource.lessonStatus == LearningStatus.NOT_STARTED) {
                binding.tvTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.llp_body_text_2))
            } else {
                binding.tvTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.llp_black_2))
            }
            binding.ivCheck.visibility = if (resource.status == LearningStatus.COMPLETED) View.VISIBLE else View.GONE
        }
    }

}