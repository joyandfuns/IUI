package com.aom_ai.uc_component.view

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView.OnEditorActionListener
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.aom_ai.uc_component.R
import com.aom_ai.uc_component.databinding.LlpTextInputBinding
import com.aom_ai.uc_component.utils.dpiToPixels
import com.google.android.material.textfield.TextInputLayout

class LLPTextInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        const val INPUT_TYPE_NONE = 0x00000000
        const val IME_OPTION_NORMAL = 0x00000000
    }

    private val binding: LlpTextInputBinding
    private var listener: CheckableTextInputListener? = null

    init {
        orientation = VERTICAL
        binding = LlpTextInputBinding.inflate(LayoutInflater.from(context), this)

        val a = context.obtainStyledAttributes(attrs, R.styleable.LLPTextInput, defStyleAttr, 0)
        val showPasswordToggle = a.getBoolean(R.styleable.LLPTextInput_showPasswordToggle, false)
        val inputType = a.getInt(R.styleable.LLPTextInput_textInputType, INPUT_TYPE_NONE)
        val imeOptions = a.getInt(R.styleable.LLPTextInput_textInputImeOptions, IME_OPTION_NORMAL)
        a.getString(R.styleable.LLPTextInput_textInputHint)?.let {
            binding.edittext.hint = it
        }
        val maxLength = a.getInt(R.styleable.LLPTextInput_maxLength, -1)
        if (maxLength != -1) {
            binding.edittext.filters = arrayOf(LengthFilter(maxLength))
        }
        val height = a.getDimension(R.styleable.LLPTextInput_textInputHeight, -1F)
        if (height != -1F) {
            binding.edittext.layoutParams.height = height.toInt()
        } else {
            binding.edittext.layoutParams.height = dpiToPixels(context, 48F).toInt()
        }
        val gravity = a.getInt(R.styleable.LLPTextInput_textGravity, -1)
        if (gravity != -1) {
            binding.edittext.gravity = gravity
        }
        val myPaddingTop = a.getDimension(R.styleable.LLPTextInput_textPaddingTop, -1F)
        if (myPaddingTop != -1F) {
            binding.edittext.apply {
                setPadding(paddingLeft, myPaddingTop.toInt(), paddingRight, paddingBottom)
            }
        }
        a.recycle()

        //for value restore when return to a page from another page
        binding.edittext.let {
            it.id = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                it.id xor this.id
            } else {
                this.id
            }
        }

        binding.edittext.inputType = inputType
        binding.edittext.imeOptions = imeOptions
//        if (showPasswordToggle) {
//            binding.textInputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
//            binding.textInputLayout.setEndIconDrawable(R.drawable.llp_password_toggle)
//            binding.edittext.typeface = Typeface.DEFAULT
//        }
    }

    fun setText(text: String?) {
        binding.edittext.setText(text)
    }

    fun getText(): String {
        return binding.edittext.text.toString()
    }

    fun isComplete(): Boolean {
        return !binding.edittext.text.isNullOrBlank()
    }

    fun getEditText(): EditText {
        return binding.edittext
    }

    fun setCheckableTextInputListener(listener: CheckableTextInputListener) {
        this.listener = listener
    }

    fun setTextInputFocusChangeListener(listener: View.OnFocusChangeListener) {
        binding.edittext.onFocusChangeListener = listener
    }

    fun setOnEditorActionListener(listener: OnEditorActionListener) {
        binding.edittext.setOnEditorActionListener(listener)
    }

    fun doOnTextChanged(
        action: (
            text: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) -> Unit
    ) {
        binding.edittext.doOnTextChanged(action)
    }

    fun addTextChangedListener(listener: TextWatcher) {
        binding.edittext.addTextChangedListener(listener)
    }

    fun removeTextChangedListener(listener: TextWatcher) {
        binding.edittext.removeTextChangedListener(listener)
    }

    fun showValidState(hint: String) {
        binding.tvHint.visibility = View.VISIBLE
        binding.edittext.background =
            ContextCompat.getDrawable(context, R.drawable.llp_edit_text_bg_enabled)
        binding.tvHint.text = hint
    }

    fun showInValidState(hint: String) {
        binding.tvHint.visibility = View.VISIBLE
        binding.edittext.background =
            ContextCompat.getDrawable(context, R.drawable.llp_edit_text_bg_validate_failed)
        binding.tvHint.text = hint
    }

    fun resetState(){
        binding.tvHint.visibility = View.GONE
        binding.edittext.background =
            ContextCompat.getDrawable(context, R.drawable.llp_edit_text)
    }

    interface CheckableTextInputListener {
        fun onTextChange(s: CharSequence?)
    }
}