package com.example.twieasy

import android.view.MotionEvent
import android.view.View

class FlickListener(
    private val listener: Listener
) : View.OnTouchListener {

    // フリックイベントのリスナー
    interface Listener {
        fun onButtonPressed()
        fun onButtonReleased()
        fun onFlickToLeft()
        fun onFlickToRight()
        fun onFlickToUp()
        fun onFlickToDown()
        fun onFlickOutside()
        fun onSwipingOnLeft()
        fun onSwipingOnRight()
        fun onSwipingOnUp()
        fun onSwipingOnDown()
        fun onSwipingOnCenter()
        fun onSwipingOutside()
    }

    companion object {
        // フリック判定時の遊び。小さいほど判定が敏感になる
        const val DEFAULT_PLAY = 100f
    }

    private val play = DEFAULT_PLAY
    private var startX: Float = 0f
    private var startY: Float = 0f
    private var endX: Float = 0f
    private var endY: Float = 0f

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchDown(event)
            MotionEvent.ACTION_MOVE -> swipe(event)
            MotionEvent.ACTION_UP -> touchOff(event)
        }
        return true
    }

    private fun touchDown(event: MotionEvent) {
        startX = event.x
        startY = event.y
        listener.onButtonPressed()
    }

    private fun swipe(event: MotionEvent) {
        endX = event.x
        endY = event.y
        when {
            outScope()   -> listener.onSwipingOutside()
            leftScope()  -> listener.onSwipingOnLeft()
            rightScope() -> listener.onSwipingOnRight()
            upScope()    -> listener.onSwipingOnUp()
            downScope()  -> listener.onSwipingOnDown()
            else         -> listener.onSwipingOnCenter()
        }
    }

    private fun touchOff(event: MotionEvent) {
        endX = event.x
        endY = event.y
        when {
            outScope()   -> listener.onFlickOutside()
            leftScope()  -> listener.onFlickToLeft()
            rightScope() -> listener.onFlickToRight()
            upScope()    -> listener.onFlickToUp()
            downScope()  -> listener.onFlickToDown()
            else         -> listener.onButtonReleased()
        }
    }

    private fun leftScope(): Boolean = endX < startX - play
    private fun rightScope(): Boolean = startX + play < endX
    private fun upScope(): Boolean = endY < startY - play
    private fun downScope(): Boolean = startY + play < endY
    private fun outScope(): Boolean =
        (leftScope() && upScope()) || (rightScope() && upScope()) ||
                (leftScope() && downScope()) || (rightScope() && downScope())
}