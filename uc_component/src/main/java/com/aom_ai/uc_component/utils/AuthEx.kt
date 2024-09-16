package com.aom_ai.uc_component.utils

import android.text.SpannableString
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.aom_ai.uc_component.R
import com.aom_ai.uc_component.view.LLPValidatableCheckBox

fun LLPValidatableCheckBox.setTermsAndPrivacyClickableSpan(
    onTermsOfServiceClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit
) {
    val fullText = context.getString(R.string.llp_accept_terms_and_privacy)
    val spannableString = SpannableString(fullText)
    val linkColor = ContextCompat.getColor(context, R.color.llp_medium_teal)

    // 为"Terms of Service"设置样式和点击事件
    val termsOfService = context.getString(R.string.llp_terms_of_service)
    val termsStart = fullText.indexOf(termsOfService)
    val termsEnd = termsStart + termsOfService.length
    setClickableSpan(spannableString, termsStart, termsEnd, linkColor) {
        // 处理Terms of Service点击事件
        Toast.makeText(context, "Terms of Service clicked", Toast.LENGTH_SHORT).show()
        onTermsOfServiceClick()
    }

    // 为"Privacy Policy"设置样式和点击事件
    val privacyPolicy = context.getString(R.string.llp_privacy_policy)
    val privacyStart = fullText.indexOf(privacyPolicy)
    val privacyEnd = privacyStart + privacyPolicy.length
    setClickableSpan(spannableString, privacyStart, privacyEnd, linkColor) {
        // 处理Privacy Policy点击事件
        Toast.makeText(context, "Privacy Policy clicked", Toast.LENGTH_SHORT).show()
        onPrivacyPolicyClick()
    }

    setText(spannableString, true)
}