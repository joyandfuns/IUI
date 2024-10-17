package com.aom_ai.learning.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aom_ai.learning.R
import com.aom_ai.learning.ui.model.LearningStatus

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