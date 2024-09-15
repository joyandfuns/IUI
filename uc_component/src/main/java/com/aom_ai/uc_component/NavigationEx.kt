package com.aom_ai.uc_component

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController

fun NavController.safeNavigateWithArgs(@IdRes actionId: Int, bundle: Bundle?) {
    currentDestination?.getAction(actionId)?.run {
        navigate(actionId, bundle)
    }
}