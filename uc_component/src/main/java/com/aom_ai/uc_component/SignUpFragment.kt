package com.aom_ai.uc_component

import android.os.Bundle
import android.text.SpannableString
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.aom_ai.uc_component.databinding.FragmentSignUpBinding
import com.aom_ai.uc_component.utils.setClickableSpan

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTermsAndPrivacyClickableSpan()
        binding.buttonJoinWithEmail.setOnClickListener {
            if (validate()) {
                findNavController().safeNavigateWithArgs(
                    R.id.action_SignUpFragment_to_UserInfoEntryFragment,
                    null
                )
            }
        }
        binding.buttonSignIn.setOnClickListener {
            findNavController().safeNavigateWithArgs(
                R.id.action_SignUpFragment_to_SignInFragment,
                null
            )
        }

        binding.flGoogleSignUp.setOnClickListener {
            if (validate()) {
                Toast.makeText(context, "Google Sign Up clicked", Toast.LENGTH_SHORT).show()
            }
        }

        binding.flFacebookSignUp.setOnClickListener {
            if (validate()) {
                Toast.makeText(context, "Google Sign Up clicked", Toast.LENGTH_SHORT).show()
            }
        }

        binding.flLineSignUp.setOnClickListener {
            if (validate()) {
                Toast.makeText(context, "Google Sign Up clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? IAuthToolbarAction)?.showBack()
        (activity as? IAuthToolbarAction)?.setTitle(getString(R.string.llp_back))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validate(): Boolean {
        val isAgeOver18Checked = binding.checkboxAcceptTermsAndPrivacy.validate(getString(R.string.llp_prompt_confirm_acceptance))

        if (!isAgeOver18Checked) {
            binding.errorBanner.visibility = View.VISIBLE
        } else {
            binding.errorBanner.visibility = View.GONE
        }
        return isAgeOver18Checked
    }

    private fun setTermsAndPrivacyClickableSpan() {
        val fullText = getString(R.string.llp_accept_terms_and_privacy)
        val spannableString = SpannableString(fullText)
        val linkColor = ContextCompat.getColor(requireContext(), R.color.llp_medium_teal)

        // 为"Terms of Service"设置样式和点击事件
        val termsOfService = getString(R.string.llp_terms_of_service)
        val termsStart = fullText.indexOf(termsOfService)
        val termsEnd = termsStart + termsOfService.length
        setClickableSpan(spannableString, termsStart, termsEnd, linkColor) {
            // 处理Terms of Service点击事件
            Toast.makeText(context, "Terms of Service clicked", Toast.LENGTH_SHORT).show()
        }

        // 为"Privacy Policy"设置样式和点击事件
        val privacyPolicy = getString(R.string.llp_privacy_policy)
        val privacyStart = fullText.indexOf(privacyPolicy)
        val privacyEnd = privacyStart + privacyPolicy.length
        setClickableSpan(spannableString, privacyStart, privacyEnd, linkColor) {
            // 处理Privacy Policy点击事件
            Toast.makeText(context, "Privacy Policy clicked", Toast.LENGTH_SHORT).show()
        }

        binding.checkboxAcceptTermsAndPrivacy.setText(spannableString, true)
    }
}