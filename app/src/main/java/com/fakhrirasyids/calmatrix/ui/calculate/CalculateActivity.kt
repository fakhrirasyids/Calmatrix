package com.fakhrirasyids.calmatrix.ui.calculate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fakhrirasyids.calmatrix.databinding.ActivityCalculateBinding
import com.fakhrirasyids.calmatrix.ui.adapter.SectionPagerAdapter
import com.fakhrirasyids.calmatrix.utils.Constants.Companion.KEY_CALCULATE
import com.fakhrirasyids.calmatrix.utils.Constants.Companion.TAB_TITLES
import com.fakhrirasyids.calmatrix.utils.Constants.Companion.TAB_TITLES_SCALAR
import com.google.android.material.tabs.TabLayoutMediator

class CalculateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculateBinding
    private lateinit var messageType: String
    private lateinit var sectionPagerAdapter: SectionPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        messageType = extras?.getString(KEY_CALCULATE).toString()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title =
                StringBuilder("$messageType Matrix")
        }

        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        sectionPagerAdapter = if (messageType == "Scalar Multiply") {
            SectionPagerAdapter(this, "Scalar Multiply")
        } else {
            SectionPagerAdapter(this, null)
        }
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            if (messageType == "Scalar Multiply") {
                tab.text = resources.getString(TAB_TITLES_SCALAR[position])
            } else {
                tab.text = resources.getString(TAB_TITLES[position])
            }
        }.attach()
    }

    fun getMessageType() = messageType
}