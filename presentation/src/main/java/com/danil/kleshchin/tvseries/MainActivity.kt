package com.danil.kleshchin.tvseries

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.danil.kleshchin.tvseries.screens.CiceroneScreens
import com.github.terrakok.cicerone.Navigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            TvShowApplication.INSTANCE.router.navigateTo(CiceroneScreens.tvShowPopularScreen())
        }
    }

    override fun onResume() {
        super.onResume()
        TvShowApplication.INSTANCE.initNavigationComponent(this)
        TvShowApplication.INSTANCE.getNavigationComponent()?.inject(this)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        TvShowApplication.INSTANCE.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        TvShowApplication.INSTANCE.navigatorHolder.removeNavigator()
    }

    override fun onStop() {
        super.onStop()
        TvShowApplication.INSTANCE.removeNavigationComponent()
    }
}
