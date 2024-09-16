package com.aom_ai.uc_component.view

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan

class LLPCustomTypefaceSpan(private val typeface: Typeface) : TypefaceSpan("") {
    override fun updateDrawState(ds: TextPaint) {
        applyCustomTypeface(ds, typeface)
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyCustomTypeface(paint, typeface)
    }

    private fun applyCustomTypeface(paint: Paint, tf: Typeface) {
        paint.typeface = tf
    }
}