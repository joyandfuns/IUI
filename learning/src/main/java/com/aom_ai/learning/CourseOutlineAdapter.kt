package com.aom_ai.learning

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
        when (val item = items[position]) {
            is CourseItem.Header -> (holder as HeaderViewHolder).bind(item)
            is CourseItem.Unit -> {
                val isLastUnit = position == items.lastIndex || items[position + 1] !is CourseItem.Unit
                (holder as UnitViewHolder).bind(item, position, isLastUnit)
            }
            is CourseItem.Lesson -> (holder as LessonViewHolder).bind(item, position)
            is CourseItem.Resource -> (holder as ResourceViewHolder).bind(item)
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
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
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

        fun bind(unit: CourseItem.Unit, position: Int, isLastUnit: Boolean) {
            binding.tvTitle.text = unit.title
            binding.tvProgress.text = binding.root.context.getString(R.string.llp_lesson_complete_progress, unit.completedLessons, unit.totalLessons)
            binding.progressBar.max = unit.totalLessons
            binding.progressBar.progress = unit.completedLessons

            binding.ivCheck.visibility = if (unit.completedLessons == unit.totalLessons) View.VISIBLE else View.GONE
            binding.ivExpand.setImageResource(if (unit.isExpanded) R.drawable.llp_ic_expand_gray else R.drawable.llp_ic_fold_gray)
            binding.divider.visibility = if (isLastUnit && !unit.isExpanded) View.GONE else View.VISIBLE

            binding.ivExpand.setOnClickListener {
                unit.isExpanded = !unit.isExpanded
                if (unit.isExpanded) {
                    val index = items.indexOf(unit)
                    items.addAll(index + 1, unit.lessons)
                    notifyItemRangeInserted(index + 1, unit.lessons.size)
                } else {
                    val index = items.indexOf(unit)
                    val itemsToRemove = unit.lessons.flatMap { lesson ->
                        listOf(lesson) + if (lesson.isExpanded) lesson.resources else emptyList()
                    }
                    items.removeAll(itemsToRemove)
                    notifyItemRangeRemoved(index + 1, itemsToRemove.size)
                }
                notifyItemChanged(position) // 更新单元项本身以反映展开/折叠状态
            }
        }
    }

    inner class LessonViewHolder(private val binding: LlpItemCourseOutlineLessonBinding) : BaseItemViewHolder(binding.root) {

        fun bind(lesson: CourseItem.Lesson, position: Int) {
            setBackgroundColor(lesson.status)
            binding.tvTitle.text = lesson.title
            binding.ivCheck.visibility = if (lesson.status == LearningStatus.COMPLETED) View.VISIBLE else View.GONE
            binding.ivExpand.setImageResource(if (lesson.isExpanded) R.drawable.llp_ic_expand_gray else R.drawable.llp_ic_fold_gray)
            binding.ivExpand.setOnClickListener {
                lesson.isExpanded = !lesson.isExpanded
                if (lesson.isExpanded) {
                    val index = items.indexOf(lesson)
                    items.addAll(index + 1, lesson.resources)
                    notifyItemRangeInserted(index + 1, lesson.resources.size)
                } else {
                    val index = items.indexOf(lesson)
                    val itemsToRemove = lesson.resources
                    items.removeAll(itemsToRemove)
                    notifyItemRangeRemoved(index + 1, itemsToRemove.size)
                }
                notifyItemChanged(position) // 更新单元项本身以反映展开/折叠状态
            }
        }
    }

    inner class ResourceViewHolder(private val binding: LlpItemCourseOutlineResourceBinding) : BaseItemViewHolder(binding.root) {

        fun bind(resource: CourseItem.Resource) {
            setBackgroundColor(resource.lessonStatus)
            binding.tvTitle.text = resource.title
            binding.ivCheck.visibility = if (resource.status == LearningStatus.COMPLETED) View.VISIBLE else View.GONE
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

    private class CourseItemDiffCallback(
        private val oldList: List<CourseItem>,
        private val newList: List<CourseItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
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
            var isExpanded: Boolean = false,
            val lessons: List<Lesson>
        ) : CourseItem()

        data class Lesson(
            val title: String,
            var isExpanded: Boolean = false,
            val status: LearningStatus,
            val resources: List<Resource>
        ) : CourseItem()

        data class Resource(
            val title: String,
            val duration: String,
            val lessonStatus: LearningStatus,
            val status: LearningStatus
        ) : CourseItem()
    }

    enum class LearningStatus {
        COMPLETED, IN_PROGRESS, NOT_STARTED
    }
}