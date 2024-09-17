package com.aom_ai.uc_component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.aom_ai.uc_component.databinding.FragmentLlpSignUpBinding
import com.aom_ai.uc_component.utils.setTermsAndPrivacyClickableSpan

class SignUpFragment : Fragment() {

    private var _binding: FragmentLlpSignUpBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLlpSignUpBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkboxAcceptTermsAndPrivacy.setTermsAndPrivacyClickableSpan({}, {})
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
        val isAcceptTermsAndPrivacy = binding.checkboxAcceptTermsAndPrivacy.validate(getString(R.string.llp_prompt_confirm_acceptance))

        if (!isAcceptTermsAndPrivacy) {
            binding.errorBanner.visibility = View.VISIBLE
        } else {
            binding.errorBanner.visibility = View.GONE
        }
        return isAcceptTermsAndPrivacy
    }
}