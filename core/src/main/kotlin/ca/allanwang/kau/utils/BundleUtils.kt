package ca.allanwang.kau.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.os.Bundle
import android.support.annotation.AnimRes
import android.support.v4.app.ActivityOptionsCompat
import ca.allanwang.kau.R

/**
 * Created by Allan Wang on 10/12/17.
 */
/**
 * Similar to [Bundle.putAll], but checks for a null insert and returns the parent bundle
 */
infix fun Bundle.with(bundle: Bundle?): Bundle {
    if (bundle != null) putAll(bundle)
    return this
}

/**
 * Adds transition bundle if context is activity and build is lollipop+
 */
@SuppressLint("NewApi")
fun Bundle.withSceneTransitionAnimation(context: Context) {
    if (context !is Activity || !buildIsLollipopAndUp) return
    this with ActivityOptions.makeSceneTransitionAnimation(context).toBundle()
}

fun Bundle.withCustomAnimation(context: Context,
                               @AnimRes enterResId: Int,
                               @AnimRes exitResId: Int) {
    this with ActivityOptionsCompat.makeCustomAnimation(context,
            enterResId, exitResId).toBundle()
}

fun Bundle.withSlideIn(context: Context) = withCustomAnimation(context,
        R.anim.kau_slide_in_right, R.anim.kau_fade_out)

fun Bundle.withSlideOut(context: Context) = withCustomAnimation(context,
        R.anim.kau_fade_in, R.anim.kau_slide_out_right_top)

fun Bundle.withFade(context: Context) = withCustomAnimation(context,
        android.R.anim.fade_in, android.R.anim.fade_out)