package com.aom_ai.uc_component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aom_ai.uc_component.databinding.FragmentLlpLandingPageBinding

class LandingPageFragment : Fragment() {

    private var _binding: FragmentLlpLandingPageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLlpLandingPageBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonNext.setOnClickListener {
            if (validate()) {
                findNavController().safeNavigateWithArgs(
                    R.id.action_LandingPageFragment_to_SignUpFragment,
                    null
                )
            }
        }
        binding.buttonSignIn.setOnClickListener {
            if (validate()) {
                findNavController().safeNavigateWithArgs(
                    R.id.action_LandingPageFragment_to_SignInFragment,
                    null
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? IAuthToolbarAction)?.hideBack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validate(): Boolean {
        val isAgeOver18Checked = binding.checkboxAgeOver18.validate(getString(R.string.llp_prompt_confirm_age))
        val isWorkEligibilityThailandChecked = binding.checkboxWorkEligibilityThailand.validate(getString(R.string.llp_prompt_confirm_eligibility))

        val isAllChecked = isAgeOver18Checked && isWorkEligibilityThailandChecked
        if (!isAllChecked) {
            binding.errorBanner.visibility = View.VISIBLE
        } else {
            binding.errorBanner.visibility = View.GONE
        }
        return isAllChecked
    }
}