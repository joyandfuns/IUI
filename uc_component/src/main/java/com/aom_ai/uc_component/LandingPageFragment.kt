package com.aom_ai.uc_component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aom_ai.uc_component.databinding.FragmentLandingPageBinding

class LandingPageFragment : Fragment() {

    private var _binding: FragmentLandingPageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLandingPageBinding.inflate(inflater, container, false)
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
            findNavController().safeNavigateWithArgs(
                R.id.action_LandingPageFragment_to_SignInFragment,
                null
            )
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
        var isValid = true
//        binding.checkboxAgeOver18.apply {
//            if (!isChecked) {
//                isValid = false
//                setError(true)
//            } else {
//                setError(false)
//            }
//        }
        binding.checkboxWorkEligibilityThailand.apply {
        }

        return isValid
    }
}