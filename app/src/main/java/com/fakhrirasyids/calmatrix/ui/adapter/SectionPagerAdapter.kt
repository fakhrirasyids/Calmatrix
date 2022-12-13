package com.fakhrirasyids.calmatrix.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fakhrirasyids.calmatrix.ui.calculate.CalculateActivity
import com.fakhrirasyids.calmatrix.ui.calculate.fragment.MatrixAFragment
import com.fakhrirasyids.calmatrix.ui.calculate.fragment.MatrixBFragment
import com.fakhrirasyids.calmatrix.ui.calculate.fragment.ResultFragment
import com.fakhrirasyids.calmatrix.ui.calculate.fragment.ScalarKFragment

class SectionPagerAdapter(activity: CalculateActivity, val type: String?) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MatrixAFragment()
            1 -> {
                fragment = if (type == "Scalar Multiply") {
                    ScalarKFragment()
                } else {
                    MatrixBFragment()
                }
            }
            2 -> fragment = ResultFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 3
    }
}