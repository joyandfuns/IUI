package com.aom_ai.uc_component.utils

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

fun setClickableSpan(spannableString: SpannableString, start: Int, end: Int, color: Int, onClick: () -> Unit) {
    spannableString.setSpan(
        object : ClickableSpan() {
            override fun onClick(widget: View) {
                onClick()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = color
                ds.isUnderlineText = false
            }
        },
        start,
        end,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}