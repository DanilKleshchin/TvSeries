package com.danil.kleshchin.tvseries.di.modules

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.danil.kleshchin.tvseries.R
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.Module
import dagger.Provides

@Module
class NavigationModule(
    private val activity: FragmentActivity
) {
    @Provides
    fun provideCiceroneNavigator(): Navigator =
        object : AppNavigator(activity, R.id.fragment_container) {
            override fun setupFragmentTransaction(
                fragmentTransaction: FragmentTransaction,
                currentFragment: Fragment?,
                nextFragment: Fragment?
            ) {
                //No need to show the main fragment with transition animation when app is launching
                if (currentFragment == null && nextFragment != null) {
                    return
                }
                fragmentTransaction.setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )
            }
        }
}
