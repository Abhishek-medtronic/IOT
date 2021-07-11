package com.college.userlatlong

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.college.userlatlong.adapter.AuthenticationPagerAdapter
import com.college.userlatlong.fragment.Login
import com.college.userlatlong.fragment.Registration
import com.college.userlatlong.utils.AES256
import com.college.userlatlong.utils.MCrypt
import com.college.userlatlong.utils.SharedPreference

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreference: SharedPreference;

    lateinit var mViewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreference = SharedPreference(this)
        mViewPager = findViewById(R.id.viewPager)
        val pagerAdapter =
            AuthenticationPagerAdapter(supportFragmentManager)
        // pagerAdapter.addFragmet(Login())
        pagerAdapter.addFragmet(Registration())
        mViewPager.setAdapter(pagerAdapter)
        if (sharedPreference.getValueString("UserId")!=null) {
            var callback = Intent(this, HomeActivity::class.java)
            startActivity(callback)
        }
    }
}