package com.aom_ai.uc_component.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.aom_ai.uc_component.utils.dpiToPixels
import com.aom_ai.uc_component.utils.getScreenDimension

abstract class BaseDialogFragment : DialogFragment() {

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (getLayoutId() != 0) {
            inflater.inflate(getLayoutId(), container, false)
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    @LayoutRes
    protected open fun getLayoutId() = 0

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onStart() {
        super.onStart()
        val ctx = context ?: return
        val screen = getScreenDimension(activity)
        val dialogWidth = screen.x - dpiToPixels(ctx, 48F)
        dialog?.window?.setLayout(dialogWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    final override fun show(manager: FragmentManager, tag: String?) {
        if (shouldShow(manager, tag)) {
            super.show(manager, tag)
        }
    }

    protected open fun shouldShow(manager: FragmentManager?, tag: String?): Boolean = true

}
