package com.aom_ai.uc_component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aom_ai.uc_component.constant.ARG_EMAIL_ADDRESS
import com.aom_ai.uc_component.constant.ARG_IS_RESET_PASSWORD
import com.aom_ai.uc_component.databinding.FragmentResetPasswordBinding

class ResetPasswordFragment : Fragment() {

    private var _binding: FragmentResetPasswordBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var emailAddress: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailAddress = arguments?.getString(ARG_EMAIL_ADDRESS) ?: ""

        binding.inputEmailAddress.setText(emailAddress)
        binding.buttonNext.setOnClickListener {
            if (checkRequiredFieldsFilled()) {
                val bundle = Bundle().apply {
                    putBoolean(ARG_IS_RESET_PASSWORD, true)
                    putString(ARG_EMAIL_ADDRESS, binding.inputEmailAddress.getText())
                }
                findNavController().safeNavigateWithArgs(
                    R.id.action_ResetPasswordFragment_to_EmailVerificationFragment,
                    bundle
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? IAuthToolbarAction)?.showBack()
        (activity as? IAuthToolbarAction)?.setTitle(getString(R.string.llp_action_back_to_sign_in))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkRequiredFieldsFilled(): Boolean {
        if (binding.inputEmailAddress.getText().isEmpty()) {
            binding.inputEmailAddress.showInValidState(getString(R.string.llp_error_invalid_email_format))
            binding.errorBanner.visibility = View.VISIBLE
            return false
        }
        binding.errorBanner.visibility = View.GONE
        return true
    }
}