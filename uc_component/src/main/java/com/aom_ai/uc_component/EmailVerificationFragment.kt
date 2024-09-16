package com.aom_ai.uc_component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.aom_ai.uc_component.constant.ARG_EMAIL_ADDRESS
import com.aom_ai.uc_component.constant.ARG_IS_RESET_PASSWORD
import com.aom_ai.uc_component.databinding.FragmentEmailVerificationBinding

class EmailVerificationFragment : Fragment() {

    private var _binding: FragmentEmailVerificationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var onBackPressedCallback: OnBackPressedCallback? = null

    private var emailAddress: String = ""
    private var isResetPassword: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isResetPassword) {
                    findNavController().popBackStack(R.id.SignInFragment, false)
                } else {
                    findNavController().popBackStack()
                }
            }
        }
        onBackPressedCallback?.let {
            requireActivity().onBackPressedDispatcher.addCallback(this, it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEmailVerificationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailAddress = arguments?.getString(ARG_EMAIL_ADDRESS) ?: ""
        isResetPassword = arguments?.getBoolean(ARG_IS_RESET_PASSWORD) ?: false

        binding.tvVerificationEmailSentInstructions.text = getString(
            R.string.llp_verification_email_sent_instructions,
            emailAddress
        )
        binding.buttonPrevious.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.buttonNext.setOnClickListener {
            if (checkRequiredFieldsFilled()) {
                val bundle = Bundle().apply {
                    putBoolean(ARG_IS_RESET_PASSWORD, isResetPassword)
                }
                findNavController().safeNavigateWithArgs(
                    R.id.action_EmailVerificationFragment_to_PasswordSetupFragment,
                    bundle
                )
            }
        }

        if (isResetPassword) {
            binding.tvTitle.text = getString(R.string.llp_reset_my_password)
        } else {
            binding.tvTitle.text = getString(R.string.llp_join_our_community)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? IAuthToolbarAction)?.showBack()
        if (isResetPassword) {
            (activity as? IAuthToolbarAction)?.setTitle(getString(R.string.llp_action_back_to_sign_in))
        } else {
            (activity as? IAuthToolbarAction)?.setTitle(getString(R.string.llp_back))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkRequiredFieldsFilled(): Boolean {
        if (binding.inputVerificationCode.getText().isEmpty()) {
            binding.inputVerificationCode.showInValidState("Please enter your verification code.")
            return false
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        onBackPressedCallback?.remove()
    }
}