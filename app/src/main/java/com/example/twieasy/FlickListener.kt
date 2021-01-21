package com.example.twieasy

import android.animation.ObjectAnimator
import android.util.Log
import android.view.MotionEvent
import android.view.View
import jnr.ffi.annotations.In

class FlickListener(
    private val listener: Listener

) : View.OnTouchListener {

    // フリックイベントのリスナー
    interface Listener {
        fun onButtonPressed()
        fun onFlickToLeft()
        fun onFlickToRight()
        fun onFlickToUp()
        fun onSwipingOnLeft()
        fun onSwipingOnRight()
        fun onSwipingOnUp()
    }

    // Android
    // 左下が原点(x = 0, y = 0)
    private var startX: Float = 0f
    private var startY: Float = 0f
    private var endX: Float = 0f
    private var endY: Float = 0f

    private var targetLocalX: Float = 0f
    private var targetLocalY: Float = 0f
    private var screenX: Float = 0f
    private var screenY: Float = 0f

    private var sx = 0f
    private var sy = 0f

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        var x = event.rawX
        var y = event.rawY

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                targetLocalX = v.left.toFloat()
                targetLocalY = v.top.toFloat()

                sx = targetLocalX
                sy = targetLocalY

                screenX = x
                screenY = y
                touchDown(event)
            }
            MotionEvent.ACTION_MOVE -> {
                val diffX = screenX - x
                val diffY = screenY - y

                targetLocalX -= diffX
                targetLocalY -= diffY

                v.layout(
                    targetLocalX.toInt(),
                    targetLocalY.toInt(),
                    targetLocalX.toInt() + v.width,
                    targetLocalY.toInt() + v.height
                )

                screenX = x
                screenY = y

                swipe(event)
            }
            MotionEvent.ACTION_UP -> {
                v.layout(
                    sx.toInt(),
                    sy.toInt(),
                    sx.toInt() + v.width,
                    sy.toInt() + v.height
                )
                touchOff(event)
            }
        }
        return true
    }

    private fun touchDown(event: MotionEvent) {
        startX = event.rawX
        startY = event.rawY
        listener.onButtonPressed()
    }

    private fun swipe(event: MotionEvent) {
        endX = event.rawX
        endY = event.rawY
        when {
            leftScope()  -> listener.onSwipingOnLeft()
            rightScope() -> listener.onSwipingOnRight()
            upScope()    -> listener.onSwipingOnUp()
        }
    }

    private fun touchOff(event: MotionEvent) {
        when {
            leftScope()  -> listener.onFlickToLeft()
            rightScope() -> listener.onFlickToRight()
            upScope()    -> listener.onFlickToUp()
        }
    }

    private fun leftScope(): Boolean = (endX < startX)  && (endY > startY)
    private fun rightScope(): Boolean = (endX > startX) && (endY > startY)
    private fun upScope(): Boolean = endY < startY
}