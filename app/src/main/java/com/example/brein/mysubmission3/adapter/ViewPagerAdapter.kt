package com.example.brein.mysubmission3.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.brein.mysubmission3.R
import com.example.brein.mysubmission3.fragment.FollowerFragment
import com.example.brein.mysubmission3.fragment.FollowingFragment

class ViewPagerAdapter(private val context: Context, fragment: FragmentManager, bundle: Bundle) :
    FragmentPagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var fragmentBundle: Bundle = bundle

    init {
        fragmentBundle = bundle
    }

    @StringRes
    private val TAB_TITLES = intArrayOf(
        R.string.tab_follower,
        R.string.tab_following
    )

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

}