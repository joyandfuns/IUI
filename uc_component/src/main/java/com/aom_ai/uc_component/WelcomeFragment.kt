package com.aom_ai.uc_component

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.aom_ai.uc_component.constant.ARG_IS_RESET_PASSWORD
import com.aom_ai.uc_component.databinding.FragmentLlpWelcomeBinding

class WelcomeFragment : Fragment() {

    private var _binding: FragmentLlpWelcomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var onBackPressedCallback: OnBackPressedCallback? = null

    private var isResetPassword: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isResetPassword) {
                    findNavController().popBackStack(R.id.SignInFragment, false)
                } else {
                    findNavController().popBackStack(R.id.LandingPageFragment, false)
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

        _binding = FragmentLlpWelcomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isResetPassword = arguments?.getBoolean(ARG_IS_RESET_PASSWORD) ?: false

        if (isResetPassword) {
            binding.tvTitle.text = getString(R.string.llp_reset_my_password)
            binding.tvSuccess.text = getString(R.string.llp_password_save_success)
            binding.tvHint.text = getString(R.string.llp_password_change_confirmation)
        } else {
            binding.tvTitle.text = getString(R.string.llp_join_our_community)
            binding.tvSuccess.text = getString(R.string.llp_account_create_success)
            binding.tvHint.text = getString(R.string.llp_account_creation_success_message)
        }

        binding.buttonSignIn.setOnClickListener {
            activity?.startActivity(Intent(activity, FakeHomeActivity::class.java))
            activity?.finish()
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

    override fun onDestroy() {
        super.onDestroy()
        onBackPressedCallback?.remove()
    }
}