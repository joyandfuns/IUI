package com.aom_ai.uc_component.utils

import android.content.Context
import android.util.TypedValue

fun dpiToPixels(context: Context, dpi: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dpi, context.resources
            .displayMetrics
    )
}

fun getDimensionPixelSize(context: Context, resId: Int): Int {
    val res = context.resources
    return res.getDimensionPixelSize(resId)
}

fun spToPx(context: Context?, sp: Float): Float {
    return if (context == null) 0F else {
        (sp * context.resources.displayMetrics.scaledDensity + 0.5F)
    }
}