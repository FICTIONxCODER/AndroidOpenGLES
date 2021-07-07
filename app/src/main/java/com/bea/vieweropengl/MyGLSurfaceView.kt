package com.bea.vieweropengl
//  Created by BEA on 2021.
//  Copyright © 2021 BEA. All rights reserved.
import android.content.Context
import android.opengl.GLSurfaceView
import android.view.KeyEvent
import android.view.MotionEvent

class MyGLSurfaceView: GLSurfaceView {
    var renderer // Custom GL Renderer
            : MyGLRenderer? = null

    // For touch event
    private val TOUCH_SCALE_FACTOR = 180.0f / 320.0f
    private var previousX = 0f
    private var previousY = 0f

    // Constructor - Allocate and set the renderer
    constructor (context: Context?) : super(context) {
        renderer = MyGLRenderer(context)
        setRenderer(renderer)
        // Request focus, otherwise key/button won't react
        this.requestFocus()
        this.isFocusableInTouchMode = true
    }

    // Handler for key event
    override fun onKeyUp(keyCode: Int, evt: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_LEFT -> renderer!!.speedY -= 0.1f
            KeyEvent.KEYCODE_DPAD_RIGHT -> renderer!!.speedY += 0.1f
            KeyEvent.KEYCODE_DPAD_UP -> renderer!!.speedX -= 0.1f
            KeyEvent.KEYCODE_DPAD_DOWN -> renderer!!.speedX += 0.1f
            KeyEvent.KEYCODE_VOLUME_UP -> renderer!!.z -= 0.2f
            KeyEvent.KEYCODE_VOLUME_DOWN -> renderer!!.z += 0.2f
        }
        return true // Event handled
    }

    // Handler for touch event
    override fun onTouchEvent(evt: MotionEvent): Boolean {
        val currentX = evt.x
        val currentY = evt.y
        val deltaX: Float
        val deltaY: Float
        when (evt.action) {
            MotionEvent.ACTION_MOVE -> {
                // Modify rotational angles according to movement
                deltaX = currentX - previousX
                deltaY = currentY - previousY
                renderer!!.angleX += deltaY * TOUCH_SCALE_FACTOR
                renderer!!.angleY += deltaX * TOUCH_SCALE_FACTOR
            }
        }
        // Save current x, y
        previousX = currentX
        previousY = currentY
        return true // Event handled
    }
}