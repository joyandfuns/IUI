package com.aom_ai.uc_component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aom_ai.uc_component.constant.ARG_IS_RESET_PASSWORD
import com.aom_ai.uc_component.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var isResetPassword: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
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
}