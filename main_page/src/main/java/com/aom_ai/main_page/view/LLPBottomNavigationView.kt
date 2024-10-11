package com.aom_ai.main_page.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.aom_ai.main_page.R
import com.aom_ai.main_page.databinding.LlpBottomNavItemBinding
import com.aom_ai.main_page.databinding.LlpBottomNavigationViewBinding


class LLPBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var navItems: List<String>? = null

    private val binding: LlpBottomNavigationViewBinding =
        LlpBottomNavigationViewBinding.inflate(LayoutInflater.from(context), this)


    init {
        orientation = HORIZONTAL
        val a = context.obtainStyledAttributes(attrs, R.styleable.LLPBottomNavigationView)
        val navItemsResId = a.getResourceId(R.styleable.LLPBottomNavigationView_llpNavItems, 0)
        navItems = loadNavItems(context, navItemsResId)
        initNavItems()
        a.recycle()
    }

    private fun loadNavItems(context: Context, resId: Int): List<String> {
        val res = context.resources
        val array = res.obtainTypedArray(resId)
        val items: MutableList<String> = ArrayList()

        for (i in 0 until array.length()) {
            val title = array.getText(i)
            items.add(title.toString())
        }

        array.recycle()
        return items
    }

    private fun initNavItems() {
        navItems?.forEach { title ->
            val navItemBinding = LlpBottomNavItemBinding.inflate(LayoutInflater.from(context), this, false)
            navItemBinding.tvTitle.text = title
            addView(navItemBinding.root)
        }
    }

}