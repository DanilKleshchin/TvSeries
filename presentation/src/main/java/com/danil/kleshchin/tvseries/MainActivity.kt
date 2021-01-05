package com.danil.kleshchin.tvseries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.danil.kleshchin.tvseries.screens.popular.TvShowPopularFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    //TODO think about location of dagger components initialization
    private fun initSectionView() {
        val tvShowPopularFragment = TvShowPopularFragment()
        //(application as TvShowApplication).initSectionComponent(tvShowPopularFragment)
        //(application as TvShowApplication).getSectionComponent().inject(tvShowPopularFragment)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, tvShowPopularFragment)
            .commitNow()
    }
}
