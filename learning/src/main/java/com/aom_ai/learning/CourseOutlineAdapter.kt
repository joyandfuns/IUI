package com.aom_ai.learning

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aom_ai.learning.databinding.LlpItemCourseOutlineHeaderBinding
import com.aom_ai.learning.databinding.LlpItemCourseOutlineLessonBinding
import com.aom_ai.learning.databinding.LlpItemCourseOutlineSubItemBinding
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
            VIEW_TYPE_SUB_ITEM -> SubItemViewHolder(
                LlpItemCourseOutlineSubItemBinding.inflate(inflater, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is CourseItem.Header -> (holder as HeaderViewHolder).bind(item)
            is CourseItem.Unit -> (holder as UnitViewHolder).bind(item)
            is CourseItem.Lesson -> (holder as LessonViewHolder).bind(item)
            is CourseItem.SubItem -> (holder as SubItemViewHolder).bind(item)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CourseItem.Header -> VIEW_TYPE_HEADER
            is CourseItem.Unit -> VIEW_TYPE_UNIT
            is CourseItem.Lesson -> VIEW_TYPE_LESSON
            is CourseItem.SubItem -> VIEW_TYPE_SUB_ITEM
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

        fun bind(unit: CourseItem.Unit) {
            binding.tvTitle.text = unit.title
            binding.tvProgress.text = binding.root.context.getString(R.string.llp_lesson_complete_progress, unit.completedLessons, unit.totalLessons)
            binding.progressBar.max = unit.totalLessons
            binding.progressBar.progress = unit.completedLessons

            binding.ivCheck.visibility = if (unit.completedLessons == unit.totalLessons) View.VISIBLE else View.GONE
            binding.ivExpand.setImageResource(if (unit.isExpanded) R.drawable.llp_ic_expand_gray else R.drawable.llp_ic_fold_gray)
        }
    }

    inner class LessonViewHolder(private val binding: LlpItemCourseOutlineLessonBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(lesson: CourseItem.Lesson) {
            binding.tvTitle.text = lesson.title
            binding.ivCheck.visibility = if (lesson.isCompleted) View.VISIBLE else View.GONE
            binding.ivExpand.setImageResource(if (lesson.isExpanded) R.drawable.llp_ic_expand_gray else R.drawable.llp_ic_fold_gray)
        }
    }

    inner class SubItemViewHolder(private val binding: LlpItemCourseOutlineSubItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(subItem: CourseItem.SubItem) {
            binding.tvTitle.text = subItem.title
            binding.ivCheck.visibility = if (subItem.isCompleted) View.VISIBLE else View.GONE
        }
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_UNIT = 1
        private const val VIEW_TYPE_LESSON = 2
        private const val VIEW_TYPE_SUB_ITEM = 3
    }

    sealed class CourseItem {
        data class Header(val completedLessons: Int, val totalLessons: Int) : CourseItem()
        data class Unit(val title: String, val completedLessons: Int, val totalLessons: Int, val isExpanded: Boolean = false) : CourseItem()
        data class Lesson(val title: String, val isCompleted: Boolean, val isExpanded: Boolean = false) : CourseItem()
        data class SubItem(val title: String, val duration: String, val isCompleted: Boolean) : CourseItem()
    }
}