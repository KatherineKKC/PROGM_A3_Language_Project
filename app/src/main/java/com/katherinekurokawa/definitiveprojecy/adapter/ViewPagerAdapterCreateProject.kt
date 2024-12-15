package com.katherinekurokawa.definitiveprojecy.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.katherinekurokawa.definitiveprojecy.LoginUserFragment
import com.katherinekurokawa.definitiveprojecy.ui.CreateProject1Fragment
import com.katherinekurokawa.definitiveprojecy.ui.CreateProject2Fragment
import com.katherinekurokawa.definitiveprojecy.ui.CreateProject3Fragment
import com.katherinekurokawa.definitiveprojecy.ui.CreateProjectActivity
import com.katherinekurokawa.definitiveprojecy.ui.IntroFragment

class ViewPagerAdapterCreateProject(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val fragments = listOf(
        CreateProject1Fragment(),
        CreateProject2Fragment(),
        CreateProject3Fragment()
    )

    override fun createFragment(position: Int): Fragment =fragments[position]

    override fun getItemCount(): Int = fragments.size


}