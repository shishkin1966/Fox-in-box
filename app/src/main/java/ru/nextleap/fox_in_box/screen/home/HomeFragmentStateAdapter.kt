package ru.nextleap.fox_in_box.screen.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.nextleap.fox_in_box.ApplicationSingleton
import ru.nextleap.fox_in_box.screen.aggregated.AggregatedFragment
import ru.nextleap.fox_in_box.screen.empty.EmptyFragment
import ru.nextleap.fox_in_box.screen.materials.MaterialsFragment
import ru.nextleap.fox_in_box.screen.more.MoreFragment
import ru.nextleap.fox_in_box.screen.news.NewsFragment
import ru.nextleap.fox_in_box.screen.profile.ProfileFragment
import ru.nextleap.fox_in_box.screen.settings.SettingsFragment

class HomeFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private var items: List<String> = ApplicationSingleton.instance.desktopProvider.getItems()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        when (items[position]) {
            "news" -> {
                return NewsFragment.newInstance()
            }
            "settings" -> {
                return SettingsFragment.newInstance()
            }
            "more" -> {
                return MoreFragment.newInstance()
            }
            "materials" -> {
                return MaterialsFragment.newInstance()
            }
            "prize" -> {
                return AggregatedFragment.newInstance()
            }
            "profile" -> {
                return ProfileFragment.newInstance()
            }
        }
        return EmptyFragment.newInstance()
    }
}