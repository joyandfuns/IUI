package com.aom_ai.uc_component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aom_ai.uc_component.constant.ARG_IS_RESET_PASSWORD
import com.aom_ai.uc_component.databinding.FragmentPasswordSetupBinding

class PasswordSetupFragment : Fragment() {

    private var _binding: FragmentPasswordSetupBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var isResetPassword: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPasswordSetupBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isResetPassword = arguments?.getBoolean(ARG_IS_RESET_PASSWORD) ?: false

        binding.buttonPrevious.setOnClickListener {
            findNavController().popBackStack(R.id.UserInfoEntryFragment, false)
        }
        binding.buttonNext.setOnClickListener {
            if (checkRequiredFieldsFilled()) {
                val bundle = Bundle().apply {
                    putBoolean(ARG_IS_RESET_PASSWORD, isResetPassword)
                }
                findNavController().safeNavigateWithArgs(
                    R.id.action_PasswordSetupFragment_to_WelcomeFragment,
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
        if (binding.inputPassword.getText().isEmpty()) {
            binding.inputPassword.showInValidState("Your password does not meet the format requirements. Please amend.")
            return false
        }
        return true
    }
}