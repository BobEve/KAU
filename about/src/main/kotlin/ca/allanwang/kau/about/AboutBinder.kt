package ca.allanwang.kau.about

import android.app.Activity
import ca.allanwang.kau.utils.startActivity
import ca.allanwang.kau.utils.withSceneTransitionAnimation

/**
 * Created by Allan Wang on 2017-07-22.
 */

/**
 * About activity launcher
 */
fun Activity.kauLaunchAbout(clazz: Class<out AboutActivityBase>) {
    startActivity(clazz, bundleBuilder = {
        withSceneTransitionAnimation(this@kauLaunchAbout)
    })
}