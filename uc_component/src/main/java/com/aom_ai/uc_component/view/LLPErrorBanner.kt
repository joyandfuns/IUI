package com.aom_ai.uc_component.view

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import com.aom_ai.uc_component.R
import com.aom_ai.uc_component.databinding.LlpErrorBannerBinding
import com.aom_ai.uc_component.utils.dpiToPixels

class LLPErrorBanner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: LlpErrorBannerBinding =
        LlpErrorBannerBinding.inflate(LayoutInflater.from(context), this)

    private val errorBoldTypeface: Typeface?

    init {
        orientation = HORIZONTAL
        errorBoldTypeface = ResourcesCompat.getFont(context, R.font.notosans_extrabold)
        background = ContextCompat.getDrawable(context, R.color.llp_light_red)
        setPadding(dpiToPixels(context, 16f).toInt())
        val a = context.obtainStyledAttributes(attrs, R.styleable.LLPErrorBanner)
        val message = a.getString(R.styleable.LLPErrorBanner_message)

        setErrorMessage(message ?: "")

        a.recycle()
    }

    fun setErrorMessage(message: String) {
        val errorPrefix = context.getString(R.string.llp_error_prefix)
        val fullText = "$errorPrefix$message"
        val spannableString = SpannableString(fullText)

        // 设置 "Error:" 的样式
        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.llp_alert_red)),
            0,
            errorPrefix.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        errorBoldTypeface?.let {
            spannableString.setSpan(
                LLPCustomTypefaceSpan(it),
                0,
                errorPrefix.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        binding.tvErrorMsg.text = spannableString
    }
}