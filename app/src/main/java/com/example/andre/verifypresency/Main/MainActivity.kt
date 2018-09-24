package com.example.andre.verifypresency.Main

import android.os.Bundle
import com.example.andre.verifypresency.R
import com.example.andre.verifypresency.BaseActivity
import kotlinx.android.synthetic.main.layout_center_viewpager.*
import kotlinx.android.synthetic.main.layout_top_tabs.*

class MainActivity : BaseActivity() {

    private val MAIN_NUM = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        super.configureBottomNav(this.MAIN_NUM)

        this.setupViewPager()

        //TODO - implementare sqliteDB cu ROOM + MVVM Kotlin
        //TODO - urmatoarele tabele : Dictionary, DictionaryDetail -> am nevoie de asta pentru salvarea tipurilor de evenimente;
        //TODO - Pentru evenimente; event, eventDetail, eventDetailXMember
        //TODO - Pentru membri: Member
    }

    private fun setupViewPager(){
        val adapter = SectionPagerAdapter(supportFragmentManager)
        adapter.addFragment(EventFragment())
        adapter.addFragment(MembersFragment())

        //initialized viewPager with an adapter
        container.adapter = adapter

        //initialized tabLayout with the required viewPager
        layout_top_tl_tabs.setupWithViewPager(container)

        layout_top_tl_tabs.getTabAt(0)?.setIcon(R.drawable.ic_events)
        layout_top_tl_tabs.getTabAt(1)?.setIcon(R.drawable.ic_group_ppl)

    }

}
