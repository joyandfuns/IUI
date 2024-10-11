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
import com.aom_ai.uc_component.databinding.LlpInfoBannerBinding
import com.aom_ai.uc_component.utils.dpiToPixels

class LLPInfoBanner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: LlpInfoBannerBinding =
        LlpInfoBannerBinding.inflate(LayoutInflater.from(context), this)

    private val infoBoldTypeface: Typeface?

    init {
        orientation = HORIZONTAL
        infoBoldTypeface = ResourcesCompat.getFont(context, R.font.notosans_extrabold)
        background = ContextCompat.getDrawable(context, R.color.llp_light_teal)
        setPadding(dpiToPixels(context, 16f).toInt())
        val a = context.obtainStyledAttributes(attrs, R.styleable.LLPInfoBanner)
        val message = a.getString(R.styleable.LLPInfoBanner_llpBannerInfoMessage)

        setMessage(message ?: "")

        a.recycle()
    }

    fun setMessage(message: String) {
        val notePrefix = context.getString(R.string.llp_note_prefix)
        val fullText = "$notePrefix$message"
        val spannableString = SpannableString(fullText)

        // 设置 "Info:" 的样式
        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.llp_primary_teal)),
            0,
            notePrefix.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        infoBoldTypeface?.let {
            spannableString.setSpan(
                LLPCustomTypefaceSpan(it),
                0,
                notePrefix.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        binding.tvMsg.text = spannableString
    }
}