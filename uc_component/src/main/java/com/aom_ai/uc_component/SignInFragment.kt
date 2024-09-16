package com.aom_ai.uc_component

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aom_ai.uc_component.constant.ARG_EMAIL_ADDRESS
import com.aom_ai.uc_component.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignIn.setOnClickListener {

        }
        binding.buttonJoin.setOnClickListener {
            findNavController().popBackStack(R.id.LandingPageFragment, false)
        }
        binding.tvResetPassword.setOnClickListener {
            val bundle = Bundle().apply {
                putString(ARG_EMAIL_ADDRESS, binding.inputEmailAddress.getText())
            }
            findNavController().safeNavigateWithArgs(
                R.id.action_SignInFragment_to_ResetPasswordFragment,
                bundle
            )
        }
        binding.buttonSignIn.setOnClickListener {
            if (checkRequiredFieldsFilled()) {
                activity?.startActivity(Intent(activity, FakeHomeActivity::class.java))
                activity?.finish()
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

    private fun checkRequiredFieldsFilled(): Boolean {
        var isValid = true
        if (binding.inputEmailAddress.getText().isEmpty()) {
            binding.inputEmailAddress.showInValidState(getString(R.string.llp_prompt_enter_last_name))
            isValid = false
        }
        if (binding.inputPassword.getText().isEmpty()) {
            binding.inputPassword.showInValidState(getString(R.string.llp_prompt_enter_email_address))
            isValid = false
        }
        if (!isValid) {
            binding.errorBanner.visibility = View.VISIBLE
        } else {
            binding.errorBanner.visibility = View.GONE
        }
        return isValid
    }
}