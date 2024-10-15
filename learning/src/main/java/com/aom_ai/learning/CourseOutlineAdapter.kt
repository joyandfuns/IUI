package com.aom_ai.learning

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aom_ai.learning.databinding.LlpItemCourseOutlineHeaderBinding
import com.aom_ai.learning.databinding.LlpItemCourseOutlineLessonBinding
import com.aom_ai.learning.databinding.LlpItemCourseOutlineResourceBinding
import com.aom_ai.learning.databinding.LlpItemCourseOutlineUnitBinding

class CourseOutlineAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<CourseItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> HeaderViewHolder(
                LlpItemCourseOutlineHeaderBinding.inflate(inflater, parent, false)
            )
            VIEW_TYPE_UNIT -> UnitViewHolder(
                LlpItemCourseOutlineUnitBinding.inflate(inflater, parent, false)
            )
            VIEW_TYPE_LESSON -> LessonViewHolder(
                LlpItemCourseOutlineLessonBinding.inflate(inflater, parent, false)
            )
            VIEW_TYPE_RESOURCE -> ResourceViewHolder(
                LlpItemCourseOutlineResourceBinding.inflate(inflater, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val isNearUnit = position != items.lastIndex && items[position + 1] is CourseItem.Unit
        when (val item = items[position]) {
            is CourseItem.Header -> (holder as HeaderViewHolder).bind(item)
            is CourseItem.Unit -> {
                val isLastUnit = position == items.lastIndex || items[position + 1] !is CourseItem.Unit
                (holder as UnitViewHolder).bind(item, isLastUnit)
            }
            is CourseItem.Lesson -> (holder as LessonViewHolder).bind(item, isNearUnit)
            is CourseItem.Resource -> (holder as ResourceViewHolder).bind(item, isNearUnit)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CourseItem.Header -> VIEW_TYPE_HEADER
            is CourseItem.Unit -> VIEW_TYPE_UNIT
            is CourseItem.Lesson -> VIEW_TYPE_LESSON
            is CourseItem.Resource -> VIEW_TYPE_RESOURCE
        }
    }

    fun setItems(newItems: List<CourseItem>) {
        val diffCallback = CourseItemDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    // 在这里定义各种ViewHolder类
    inner class HeaderViewHolder(private val binding: LlpItemCourseOutlineHeaderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(header: CourseItem.Header) {
            binding.progressBar.max = header.totalLessons
            binding.progressBar.progress = header.completedLessons
            binding.tvProgress.text = binding.root.context.getString(R.string.llp_lesson_complete_progress, header.completedLessons, header.totalLessons)
            binding.tvPercentage.text = binding.root.context.getString(R.string.llp_lesson_complete_percentage, (header.completedLessons.toFloat() / header.totalLessons * 100).toInt())
        }
    }

    inner class UnitViewHolder(private val binding: LlpItemCourseOutlineUnitBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(unit: CourseItem.Unit, isLastUnit: Boolean) {
            binding.tvTitle.text = unit.title
            binding.tvProgress.text = binding.root.context.getString(R.string.llp_lesson_complete_progress, unit.completedLessons, unit.totalLessons)
            binding.progressBar.max = unit.totalLessons
            binding.progressBar.progress = unit.completedLessons

            binding.ivCheck.visibility = if (unit.completedLessons == unit.totalLessons) View.VISIBLE else View.GONE
            binding.ivExpand.setImageResource(if (unit.isExpanded) R.drawable.llp_ic_expand_gray else R.drawable.llp_ic_fold_gray)
            binding.divider.visibility = if (isLastUnit && !unit.isExpanded) View.GONE else View.VISIBLE

            if (unit.status == LearningStatus.NOT_STARTED) {
                binding.tvTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.llp_body_text_2))
                binding.tvProgress.setTextColor(ContextCompat.getColor(binding.root.context, R.color.llp_body_text_2))
                binding.progressBar.progressDrawable = ContextCompat.getDrawable(binding.root.context, R.drawable.llp_custom_disable_progress_bar)
            } else {
                binding.tvTitle.setTextColor(ContextCompat.getColor(binding.root.context, com.aom_ai.uc_component.R.color.llp_body_text_1))
                binding.tvProgress.setTextColor(ContextCompat.getColor(binding.root.context, com.aom_ai.uc_component.R.color.llp_body_text_1))
                binding.progressBar.progressDrawable = ContextCompat.getDrawable(binding.root.context, R.drawable.llp_custom_progress_bar)
            }
            binding.llUnitHeader.setOnClickListener {
                toggleExpansion(unit)
            }
        }
    }

    inner class LessonViewHolder(private val binding: LlpItemCourseOutlineLessonBinding) : BaseItemViewHolder(binding.root) {

        fun bind(lesson: CourseItem.Lesson, isNearUnit: Boolean) {
            setBackgroundColor(lesson.status)
            binding.tvTitle.text = lesson.title
            binding.ivCheck.visibility = if (lesson.status == LearningStatus.COMPLETED) View.VISIBLE else View.GONE
            binding.ivExpand.setImageResource(if (lesson.isExpanded) R.drawable.llp_ic_expand_gray else R.drawable.llp_ic_fold_gray)
            binding.divider.visibility = if (isNearUnit) View.VISIBLE else View.GONE
            binding.root.setOnClickListener {
                toggleExpansion(lesson)
            }
        }
    }

    inner class ResourceViewHolder(private val binding: LlpItemCourseOutlineResourceBinding) : BaseItemViewHolder(binding.root) {

        fun bind(resource: CourseItem.Resource, isNearUnit: Boolean) {
            setBackgroundColor(resource.lessonStatus)
            val title: String
            val iconDrawableId: Int
            when (resource.type) {
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
            binding.divider.visibility = if (isNearUnit) View.VISIBLE else View.GONE
        }
    }

    abstract class BaseItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        protected fun setBackgroundColor(status: LearningStatus) {
            val backgroundColor = when (status) {
                LearningStatus.COMPLETED, LearningStatus.IN_PROGRESS ->
                    ContextCompat.getColor(itemView.context, com.aom_ai.uc_component.R.color.llp_white)
                LearningStatus.NOT_STARTED ->
                    ContextCompat.getColor(itemView.context, R.color.llp_background_1)
            }
            (itemView as? ViewGroup)?.setBackgroundColor(backgroundColor)
        }
    }

    private fun toggleExpansion(item: CourseItem) {
        when (item) {
            is CourseItem.Unit -> {
                val position = items.indexOf(item)
                if (position == -1) return  // Item not found in the list

                val newUnit = item.copy(isExpanded = !item.isExpanded)
                val newItems = items.toMutableList()
                newItems[position] = newUnit
                if (newUnit.isExpanded) {
                    newItems.addAll(position + 1, newUnit.lessons)
                } else {
                    val itemsToRemove = newUnit.lessons.flatMap { lesson ->
                        val newLesson = lesson.copy(isExpanded = false)
                        listOf(newLesson) + if (lesson.isExpanded) lesson.resources else emptyList()
                    }
                    newItems.removeAll(itemsToRemove)
                }
                setItems(newItems)
            }
            is CourseItem.Lesson -> {
                val position = items.indexOf(item)
                if (position == -1) return  // Item not found in the list

                val newLesson = item.copy(isExpanded = !item.isExpanded)
                val newItems = items.toMutableList()
                newItems[position] = newLesson
                if (newLesson.isExpanded) {
                    newItems.addAll(position + 1, newLesson.resources)
                } else {
                    newItems.removeAll(newLesson.resources)
                }
                setItems(newItems)
            }
            else -> return
        }
    }

    private class CourseItemDiffCallback(
        private val oldList: List<CourseItem>,
        private val newList: List<CourseItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val areItemsTheSame = oldList[oldItemPosition] == newList[newItemPosition]
            Log.d("AAAAA", "areItemsTheSame oldItemPosition: $oldItemPosition, newItemPosition: $newItemPosition $areItemsTheSame")
            Log.d("AAAAA", "Old ${oldList[oldItemPosition]}")
            Log.d("AAAAA", "New ${newList[newItemPosition]}")
            return areItemsTheSame
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val areContentTheSame = oldList[oldItemPosition] == newList[newItemPosition]
            Log.d("AAAAA", "areContentsTheSame oldItemPosition: $oldItemPosition, newItemPosition: $newItemPosition $areContentTheSame")
            return areContentTheSame
        }
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_UNIT = 1
        private const val VIEW_TYPE_LESSON = 2
        private const val VIEW_TYPE_RESOURCE = 3
    }

    sealed class CourseItem {
        data class Header(val completedLessons: Int, val totalLessons: Int) : CourseItem()

        data class Unit(
            val title: String,
            val completedLessons: Int,
            val totalLessons: Int,
            val isExpanded: Boolean = false,
            val status: LearningStatus,
            val lessons: List<Lesson>
        ) : CourseItem()

        data class Lesson(
            val title: String,
            val isExpanded: Boolean = false,
            val status: LearningStatus,
            val resources: List<Resource>
        ) : CourseItem()

        data class Resource(
            val title: String,
            val duration: Int,
            val lessonStatus: LearningStatus,
            val status: LearningStatus,
            val type: ResourceType,
            val isPlaying: Boolean = false
        ) : CourseItem()
    }

    enum class LearningStatus {
        COMPLETED, IN_PROGRESS, NOT_STARTED
    }

    enum class ResourceType {
        VIDEO, AUDIO, DOCUMENT
    }
}