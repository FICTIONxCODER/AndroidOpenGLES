package com.bea.vieweropengl

import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    // Use GLSurfaceView
    private var glView : GLSurfaceView? = null

    // Call back when the activity is started, to initialize the view
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        glView = GLSurfaceView(this) // Allocate a GLSurfaceView
        glView!!.setRenderer(MyGLRenderer(applicationContext)) // Use a custom renderer
        this.setContentView(glView) // This activity sets to GLSurfaceView
    }

    // Call back when the activity is going into the background
    override fun onPause() {
        super.onPause()
        glView!!.onPause()
    }

    // Call back after onPause()
    override fun onResume() {
        super.onResume()
        glView!!.onResume()
    }
}