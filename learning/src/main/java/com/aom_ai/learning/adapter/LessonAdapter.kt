package com.aom_ai.learning.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aom_ai.learning.R
import com.aom_ai.learning.databinding.LlpItemCourseOutlineLessonBinding
import com.aom_ai.learning.ui.model.LearningStatus
import com.aom_ai.learning.ui.model.LessonUiState

class LessonAdapter : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {

    private val items = mutableListOf<LessonUiState>()

    fun setItems(newItems: List<LessonUiState>) {
        items.clear()
        items.addAll(newItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return LessonViewHolder(
            LlpItemCourseOutlineLessonBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    inner class LessonViewHolder(private val binding: LlpItemCourseOutlineLessonBinding) : BaseItemViewHolder(binding.root) {

        fun bind(lesson: LessonUiState) {
            setBackgroundColor(lesson.status)
            binding.tvTitle.text = lesson.title
            binding.ivCheck.visibility = if (lesson.status == LearningStatus.COMPLETED) View.VISIBLE else View.GONE
            showExpendUI(lesson)
            binding.root.setOnClickListener {
                lesson.expended = !lesson.expended
                showExpendUI(lesson)
            }
        }

        private fun showExpendUI(lesson: LessonUiState) {
            binding.ivExpand.setImageResource(if (lesson.expended) R.drawable.llp_ic_expand_gray else R.drawable.llp_ic_fold_gray)
            binding.rvResources.visibility = if (lesson.expended) View.VISIBLE else View.GONE
            if (lesson.expended) {
                val adapter = ResourceAdapter()
                binding.rvResources.adapter = adapter
                adapter.setItems(lesson.resources)
            }
        }
    }



}