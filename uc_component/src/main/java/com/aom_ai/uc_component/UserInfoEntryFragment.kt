package com.aom_ai.uc_component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
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
            findNavController().safeNavigateWithArgs(
                R.id.action_UserInfoEntryFragment_to_EmailVerificationFragment,
                null
            )
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? IAuthToolbarAction)?.showBackIcon()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}