package com.zayd.topstoriesbynewyorktimes.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.zayd.topstoriesbynewyorktimes.R

class FragmentUtils {

    fun addFragment(
        fragmentActivity: FragmentActivity?, fragment: Fragment, activeFragment: Fragment?,
        containerViewId: Int = R.id.fragmentContainer, addToStack: Boolean
    ) {
        if (isContextInvalid(fragmentActivity))
            return

        val fragmentManager = fragmentActivity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (!fragment.isAdded)
            fragmentTransaction.add(containerViewId, fragment)

        if (activeFragment != null)
            fragmentTransaction.hide(activeFragment).show(fragment)

        if (!fragment.isAdded && addToStack) {
            val backStackName = fragment.javaClass.simpleName
            fragmentTransaction.addToBackStack(backStackName)
        }

        fragmentTransaction.commit()
    }

    fun remove(fragmentActivity: FragmentActivity, fragment: Fragment?) {
        if (isContextInvalid(fragmentActivity)) {
            return
        }
        if (fragment != null && fragment.isAdded) {
            fragmentActivity.supportFragmentManager.beginTransaction().remove(fragment)
                .commitAllowingStateLoss()
        }
    }

    fun getCurrentFragment(fragmentActivity: FragmentActivity): Fragment? {
        val fragmentManager: FragmentManager = fragmentActivity.supportFragmentManager
        if (fragmentManager.fragments.size > 0) {
            return fragmentManager.fragments[fragmentManager.fragments.size - 1]
        }
        return null
    }

    private fun isContextInvalid(context: FragmentActivity?): Boolean {
        return context == null || context.isFinishing
    }
}