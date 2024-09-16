package com.aom_ai.uc_component

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.aom_ai.uc_component.constant.ARG_EMAIL_ADDRESS
import com.aom_ai.uc_component.databinding.FragmentSignInBinding
import com.aom_ai.uc_component.dialog.LLPAgreementDialog

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
        binding.flGoogleSignIn.setOnClickListener {
            checkAgreement {
                Toast.makeText(context, "Google Sign In clicked", Toast.LENGTH_SHORT).show()
            }
        }
        binding.flFacebookSignIn.setOnClickListener {
            checkAgreement {
                Toast.makeText(context, "Facebook Sign In clicked", Toast.LENGTH_SHORT).show()
            }
        }
        binding.flLineSignIn.setOnClickListener {
            checkAgreement {
                Toast.makeText(context, "Line Sign In clicked", Toast.LENGTH_SHORT).show()
            }
        }
        binding.buttonSignIn.setOnClickListener {
            checkAgreement {
                if (checkRequiredFieldsFilled()) {
                    activity?.startActivity(Intent(activity, FakeHomeActivity::class.java))
                    activity?.finish()
                }
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
            binding.inputEmailAddress.showInValidState(getString(R.string.llp_prompt_enter_email_address))
            isValid = false
        }
        if (binding.inputPassword.getText().isEmpty()) {
            binding.inputPassword.showInValidState(getString(R.string.llp_prompt_enter_password))
            isValid = false
        }
        if (!isValid) {
            binding.errorBanner.visibility = View.VISIBLE
        } else {
            binding.errorBanner.visibility = View.GONE
        }
        return isValid
    }

    private fun checkAgreement(onNext: () -> Unit) {
        LLPAgreementDialog.newInstance {
            onNext()
        }.show(childFragmentManager, LLPAgreementDialog::class.java.simpleName)
    }
}