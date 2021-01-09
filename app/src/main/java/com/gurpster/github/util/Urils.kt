package com.gurpster.github.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.gurpster.github.R


/**
 * Created by Williaan Souza (dextter) on 09/01/2021
 * Contact williaanlopes@gmail.com
 */
object Urils {

    fun getThemeColor(context: Context, id: Int): Int {
        val theme: Resources.Theme = context.theme
        val a: TypedArray = theme.obtainStyledAttributes(intArrayOf(id))
        val result = a.getColor(0, 0)
        a.recycle()
        return result
    }

    fun isRtl(resources: Resources): Boolean {
        return resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL
    }

    fun animateSearchToolbar(context: Context, searchView: SearchView, numberOfMenuIcon: Int, containsOverflow: Boolean, show: Boolean) {
        searchView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
        if (show) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val width: Int = searchView.width -
                        (if (containsOverflow) context.resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) else 0) -
                        context.resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon / 2
                val createCircularReveal: Animator = ViewAnimationUtils.createCircularReveal(
                    searchView,
                    if (isRtl(context.resources)) searchView.width - width else width,
                    searchView.height / 2,
                    0.0f,
                    width.toFloat()
                )
                createCircularReveal.duration = 250
                createCircularReveal.start()
            } else {
                val translateAnimation =
                    TranslateAnimation(0.0f, 0.0f, -searchView.height as Float, 0.0f)
                translateAnimation.duration = 220
                searchView.clearAnimation()
                searchView.startAnimation(translateAnimation)
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val width: Int = searchView.width -
                        (if (containsOverflow) context.resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) else 0) -
                        context.resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon / 2
                val createCircularReveal: Animator = ViewAnimationUtils.createCircularReveal(
                    searchView,
                    if (isRtl(context.resources)) searchView.width - width else width,
                    searchView.height / 2,
                    width.toFloat(),
                    0.0f
                )
                createCircularReveal.duration = 250
                createCircularReveal.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        searchView.setBackgroundColor(
                            getThemeColor(
                                context,
                                R.attr.colorPrimary
                            )
                        )
                    }
                })
                createCircularReveal.start()
            } else {
                val alphaAnimation = AlphaAnimation(1.0f, 0.0f)
                val translateAnimation: Animation =
                    TranslateAnimation(0.0f, 0.0f, 0.0f, -searchView.height as Float)
                val animationSet = AnimationSet(true)
                animationSet.addAnimation(alphaAnimation)
                animationSet.addAnimation(translateAnimation)
                animationSet.duration = 220
                animationSet.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {}
                    override fun onAnimationEnd(animation: Animation?) {
                        searchView.setBackgroundColor(
                            getThemeColor(
                                context,
                                R.attr.colorPrimary
                            )
                        )
                    }

                    override fun onAnimationRepeat(animation: Animation?) {}
                })
                searchView.startAnimation(animationSet)
            }
        }
    }
}