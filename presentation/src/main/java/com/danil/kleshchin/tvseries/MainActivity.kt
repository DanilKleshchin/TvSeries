package com.danil.kleshchin.tvseries

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.danil.kleshchin.tvseries.screens.popular.TvShowPopularFragment

class MainActivity : AppCompatActivity() {

    private val TV_SHOW_POPULAR_TAG = "TV_SHOW_POPULAR_TAG"
    private  var tvShowPopularFragment: TvShowPopularFragment = TvShowPopularFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initActivity(savedInstanceState)

        if (savedInstanceState == null) {
            initTvShowPopularView()
        }
    }

    private fun initTvShowPopularView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, tvShowPopularFragment)
            .addToBackStack(TV_SHOW_POPULAR_TAG)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(outState, TV_SHOW_POPULAR_TAG, tvShowPopularFragment)
    }

    private fun initActivity(savedInstanceState: Bundle?) {
        tvShowPopularFragment = if (savedInstanceState == null) {
            TvShowPopularFragment()
        } else {
            supportFragmentManager.getFragment(
                savedInstanceState,
                TV_SHOW_POPULAR_TAG
            ) as TvShowPopularFragment
        }
    }
}
