package com.aom_ai.uc_component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aom_ai.uc_component.constant.ARG_EMAIL_ADDRESS
import com.aom_ai.uc_component.databinding.FragmentUserInfoEntryBinding

class UserInfoEntryFragment : Fragment() {

    private var _binding: FragmentUserInfoEntryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserInfoEntryBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonNext.setOnClickListener {
            if (checkRequiredFieldsFilled()) {
                val bundle = Bundle().apply {
                    putString(ARG_EMAIL_ADDRESS, binding.inputEmailAddress.getText())
                }
                findNavController().safeNavigateWithArgs(
                    R.id.action_UserInfoEntryFragment_to_EmailVerificationFragment,
                    bundle
                )
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
        if (binding.inputFirstName.getText().isEmpty()) {
            binding.inputFirstName.showInValidState("Please enter your first name.")
            return false
        }
        return true
    }
}