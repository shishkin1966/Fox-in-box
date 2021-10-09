package ru.nextleap.fox_in_box.screen.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import ru.nextleap.fox_in_box.R
import ru.nextleap.fox_in_box.screen.AbsDesktopFragment
import ru.nextleap.sl.model.IModel
import ru.nextleap.sl.provider.ApplicationProvider


class HomeFragment : AbsDesktopFragment("fragment_home", R.layout.fragment_home) {
    companion object {
        const val NAME = "HomeFragment"

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private lateinit var button1: TextView
    private lateinit var button2: TextView
    private lateinit var button3: TextView
    private lateinit var button4: TextView
    private lateinit var button5: TextView
    private lateinit var menuShadow: View
    private lateinit var viewPager: ViewPager2
    private var position: Int = 0
    private lateinit var fl: FrameLayout
    private var countNewsBadger: Int = 3

    override fun createModel(): IModel {
        return HomeModel(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuShadow = view.findViewById(R.id.menuShadow)

        button1 = view.findViewById(R.id.button1)
        fl = view.findViewById(R.id.fl)
        button2 = view.findViewById(R.id.button2)
        button3 = view.findViewById(R.id.button3)
        button4 = view.findViewById(R.id.button4)
        button5 = view.findViewById(R.id.button5)

        viewPager = view.findViewById(R.id.viewPager)
        viewPager.isUserInputEnabled = false
        val adapter = HomeFragmentStateAdapter(this)
        viewPager.adapter = adapter

        button1.setOnClickListener(this::onClick)
        button2.setOnClickListener(this::onClick)
        button3.setOnClickListener(this::onClick)
        button4.setOnClickListener(this::onClick)
        button5.setOnClickListener(this::onClick)

        refreshBottomMenu()

        button1.post {
            setBadger()
        }
    }

    @SuppressLint("RestrictedApi", "UnsafeExperimentalUsageError", "UnsafeOptInUsageError")
    private fun setBadger() {
        val badgeDrawable = BadgeDrawable.create(button1.context)
        badgeDrawable.number = countNewsBadger
        badgeDrawable.isVisible = badgeDrawable.number > 0
        badgeDrawable.horizontalOffset =
            ApplicationProvider.appContext.resources.getDimension(R.dimen.badger_horizontal_offset)
                .toInt()
        badgeDrawable.verticalOffset =
            ApplicationProvider.appContext.resources.getDimension(R.dimen.badger_vertical_offset)
                .toInt()
        BadgeUtils.attachBadgeDrawable(badgeDrawable, button1, fl)
    }

    override fun isTop(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

    @SuppressLint("RestrictedApi")
    private fun refreshBottomMenu() {
        if (button1.isSelected) {
            button1.isSelected = false
            button1.compoundDrawables.forEach {
                it?.setTint(
                    ContextCompat.getColor(
                        button1.context,
                        R.color.gray
                    )
                )
            }
        }
        if (button2.isSelected) {
            button2.isSelected = false
            button2.compoundDrawables.forEach {
                it?.setTint(
                    ContextCompat.getColor(
                        button1.context,
                        R.color.gray
                    )
                )
            }
        }
        if (button3.isSelected) {
            button3.isSelected = false
            button3.compoundDrawables.forEach {
                it?.setTint(
                    ContextCompat.getColor(
                        button1.context,
                        R.color.gray
                    )
                )
            }
        }
        if (button4.isSelected) {
            button4.isSelected = false
            button4.compoundDrawables.forEach {
                it?.setTint(
                    ContextCompat.getColor(
                        button1.context,
                        R.color.gray
                    )
                )
            }
        }
        if (button5.isSelected) {
            button5.isSelected = false
            button5.compoundDrawables.forEach {
                it?.setTint(
                    ContextCompat.getColor(
                        button1.context,
                        R.color.gray
                    )
                )
            }
        }

        when (position) {
            0 -> {
                button1.isSelected = true
                menuShadow.visibility = View.INVISIBLE
                button1.compoundDrawables.forEach {
                    it?.setTint(
                        ContextCompat.getColor(
                            button1.context,
                            R.color.orange
                        )
                    )
                }
            }
            1 -> {
                button2.isSelected = true
                menuShadow.visibility = View.VISIBLE
                button2.compoundDrawables.forEach {
                    it?.setTint(
                        ContextCompat.getColor(
                            button1.context,
                            R.color.orange
                        )
                    )
                }
            }
            2 -> {
                button3.isSelected = true
                menuShadow.visibility = View.VISIBLE
                button3.compoundDrawables.forEach {
                    it?.setTint(
                        ContextCompat.getColor(
                            button1.context,
                            R.color.orange
                        )
                    )
                }
            }
            3 -> {
                button4.isSelected = true
                menuShadow.visibility = View.VISIBLE
                button4.compoundDrawables.forEach {
                    it?.setTint(
                        ContextCompat.getColor(
                            button1.context,
                            R.color.orange
                        )
                    )
                }
            }
            4 -> {
                button5.isSelected = true
                menuShadow.visibility = View.VISIBLE
                button5.compoundDrawables.forEach {
                    it?.setTint(
                        ContextCompat.getColor(
                            button1.context,
                            R.color.orange
                        )
                    )
                }
            }
        }
    }

    private fun onClick(v: View?) {
        when (v?.id) {
            R.id.button1 -> {
                if (position != 0) {
                    position = 0
                    refreshBottomMenu()
                    viewPager.currentItem = 0
                }
            }
            R.id.button2 -> {
                if (position != 1) {
                    position = 1
                    refreshBottomMenu()
                    viewPager.currentItem = 1
                }
            }
            R.id.button3 -> {
                if (position != 2) {
                    position = 2
                    refreshBottomMenu()
                    viewPager.currentItem = 2
                }
            }
            R.id.button4 -> {
                if (position != 3) {
                    position = 3
                    refreshBottomMenu()
                    viewPager.currentItem = 3
                }
            }
            R.id.button5 -> {
                if (position != 4) {
                    position = 4
                    refreshBottomMenu()
                    viewPager.currentItem = 4
                }
            }
        }
    }

    fun showNews() {
        onClick(button1)
    }

    override fun onDestroyView() {
        viewPager.adapter = null

        super.onDestroyView()
    }


}
