package com.aom_ai.learning.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aom_ai.learning.R
import com.aom_ai.learning.databinding.LlpItemCourseOutlineUnitBinding
import com.aom_ai.learning.ui.model.CourseUnitUiState
import com.aom_ai.learning.ui.model.LearningStatus

class CourseOutlineAdapter : RecyclerView.Adapter<CourseOutlineAdapter.UnitViewHolder>() {

    private val items = mutableListOf<CourseUnitUiState>()

    fun setItems(newItems: List<CourseUnitUiState>) {
        items.clear()
        items.addAll(newItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UnitViewHolder(
            LlpItemCourseOutlineUnitBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UnitViewHolder, position: Int) {
        val item = items[position]
        val isLastUnit = position == items.lastIndex
        holder.bind(item, isLastUnit)
    }

    override fun getItemCount() = items.size

    inner class UnitViewHolder(private val binding: LlpItemCourseOutlineUnitBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(unit: CourseUnitUiState, isLastUnit: Boolean) {
            binding.tvTitle.text = unit.title
            binding.tvProgress.text = binding.root.context.getString(R.string.llp_lesson_complete_progress, unit.completedLessonCount, unit.totalLessonsCount)
            binding.progressBar.max = unit.totalLessonsCount
            binding.progressBar.progress = unit.completedLessonCount

            binding.ivCheck.visibility = if (unit.completedLessonCount == unit.totalLessonsCount) View.VISIBLE else View.GONE
            showExpendUI(unit, isLastUnit)

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
                unit.expended = !unit.expended
                showExpendUI(unit, isLastUnit)
            }
        }

        private fun showExpendUI(unit: CourseUnitUiState, isLastUnit: Boolean) {
            binding.ivExpand.setImageResource(if (unit.expended) R.drawable.llp_ic_expand_gray else R.drawable.llp_ic_fold_gray)
            binding.rvLessons.visibility = if (unit.expended) View.VISIBLE else View.GONE
            if (unit.expended) {
                val adapter = LessonAdapter()
                binding.rvLessons.adapter = adapter
                adapter.setItems(unit.lessons)
            }
        }
    }

}