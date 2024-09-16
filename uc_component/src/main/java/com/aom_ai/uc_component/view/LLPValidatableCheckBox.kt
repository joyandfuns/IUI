package com.aom_ai.uc_component.view

import android.content.Context
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.aom_ai.uc_component.R
import com.aom_ai.uc_component.databinding.LlpValidatableCheckboxBinding

class LLPValidatableCheckBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: LlpValidatableCheckboxBinding =
        LlpValidatableCheckboxBinding.inflate(LayoutInflater.from(context), this)

    private var onCheckedChangeListener: ((Boolean) -> Unit)? = null

    init {
        orientation = VERTICAL
        val a = context.obtainStyledAttributes(attrs, R.styleable.LLPValidatableCheckBox)
        val text = a.getString(R.styleable.LLPValidatableCheckBox_text)

        binding.checkbox.text = text

        binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.tvHint.visibility = GONE
                binding.checkbox.buttonDrawable = ContextCompat.getDrawable(context, R.drawable.llp_bg_checkbox)
            }
            onCheckedChangeListener?.invoke(isChecked)
        }
        a.recycle()
    }

    fun setText(text: SpannableString, clickable: Boolean) {
        if (clickable) {
            binding.tvLegal.text = text
            binding.tvLegal.movementMethod = LinkMovementMethod.getInstance()
            binding.tvLegal.visibility = VISIBLE
            binding.checkbox.text = ""
        } else {
            binding.checkbox.text = text
            binding.tvLegal.visibility = GONE
        }
    }

    fun setText(text: String) {
        binding.checkbox.text = text
        binding.tvLegal.visibility = GONE
    }

    fun validate(errorMsg: String): Boolean {
        if (binding.checkbox.isChecked) {
            binding.tvHint.visibility = GONE
        } else {
            binding.tvHint.text = errorMsg
            binding.tvHint.visibility = VISIBLE
            binding.checkbox.buttonDrawable = ContextCompat.getDrawable(context, R.drawable.llp_ic_checkbox_error)
        }
        return binding.checkbox.isChecked
    }

    fun setOnCheckedChangeListener(listener: (Boolean) -> Unit) {
        onCheckedChangeListener = listener
    }

}