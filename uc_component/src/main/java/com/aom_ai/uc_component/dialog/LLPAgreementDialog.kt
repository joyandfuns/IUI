package com.aom_ai.uc_component.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import com.aom_ai.uc_component.R
import com.aom_ai.uc_component.databinding.LlpAgreementDialogBinding
import com.aom_ai.uc_component.utils.dpiToPixels
import com.aom_ai.uc_component.utils.setTermsAndPrivacyClickableSpan

class LLPAgreementDialog(
    private val onProceed: (() -> Unit)? = null
) : BaseDialogFragment() {

    companion object {

        @JvmStatic
        fun newInstance(
            onProceed: (() -> Unit)? = null
        ): LLPAgreementDialog {
            return LLPAgreementDialog(onProceed)
        }

    }

    private var binding: LlpAgreementDialogBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        @SuppressLint("InflateParams")
        binding = LlpAgreementDialogBinding.inflate(LayoutInflater.from(context), null, false)
        val builder = AlertDialog.Builder(context, R.style.LLPDialog).apply {
            setView(binding?.root)
            isCancelable = true
        }


        binding?.checkboxAcceptTermsAndPrivacy?.setTermsAndPrivacyClickableSpan({}, {})
        binding?.ivClose?.setOnClickListener {
            dismissAllowingStateLoss()
        }
        binding?.buttonCancel?.setOnClickListener {
            dismissAllowingStateLoss()
        }
        binding?.buttonProceed?.setOnClickListener {
            if (binding?.checkboxAcceptTermsAndPrivacy?.validate(getString(R.string.llp_prompt_confirm_acceptance)) != true) {
                return@setOnClickListener
            }
            dismissAllowingStateLoss()
            onProceed?.invoke()
        }

        return builder.create().apply {
            setCanceledOnTouchOutside(true)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setGravity(Gravity.TOP)
        val params = dialog?.window?.attributes
        params?.y = dpiToPixels(requireContext(), 24F).toInt()
        dialog?.window?.attributes = params
    }
}
