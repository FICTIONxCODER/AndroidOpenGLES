package com.bea.vieweropengl

import android.content.Context
import android.opengl.GLES10
import android.opengl.GLSurfaceView
import android.opengl.GLU
import android.util.Log
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyGLRenderer : GLSurfaceView.Renderer {
    //var triangle : Triangle? = null
    var quad: Square? = null                                //Sensor
    private var scanningCone : ScanningCone? = null         //Scanning cone
    private var cube : Cube? = null                         //Safety Area
    private var leftdoor1 : LeftDoor? = null                //SideScreen
    private var leftdoor2 : LeftDoor? = null                //SideScreen
    private var scanArea:ScanArea? = null                   //Scan Area on Floor
    private var virtualPushButton1:VirtualPushButton? =null //VOB(Virtual opening button)
    private var floor:Floor? =null                          //Grid
    private var object1:Objects? = null
    private var object2:Objects? = null
    private var waterdrop:waterDrop? = null
    // For controlling cube's z-position, x and y angles and speeds (50,-30) for clear floor)otherwise (40,-35)
    var angleX = 50f
    var angleY = -30f
    var speedX = 0f
    var speedY = 0f
    var z = -6.0f

    // Application's context
    var context : Context? = null

    // Constructor with global application context
    constructor(context: Context?) {
        this.context = context

        //quad = Square(0.0f,2.20f,0f)
        //scanningCone = ScanningCone(0.0f,2.20f,0f)
        //cube = Cube(-0.85f,0.0f,-0.45f,0.85f,2.20f,0.05f)
        //leftdoor1 = LeftDoor(-1.70f,0.00f,-0.15f,-0.85f,2.20f,0.05f)
        //leftdoor2 = LeftDoor(0.85f,0.00f,-0.15f,1.70f,2.20f,0.05f)
        //scanArea = ScanArea(-0.85f,0.0f,-0.45f,0.85f,2.20f,0.05f)
        //virtualPushButton1 = VirtualPushButton(1.00f,1.20f,-0.15f,1.20f,1.40f,0.05f)
        floor= Floor()
        //object1 = Objects(0.0f,0.0f,-0.45f,0.30f,0.70f,-0.25f)
        //object2 = Objects(-1.10f,0.20f,-0.15f,-0.70f,1.30f,0.05f)
        waterdrop = waterDrop(-1.10f,0.20f,-0.15f,-0.70f,1.30f,0.05f)
    }

    // Call back when the surface is first created or re-created
    override fun onSurfaceCreated(gl: GL10, config: EGLConfig?) {
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f) // Set color's clear-value to White
        gl.glClearDepthf(1.0f) // Set depth's clear-value to farthest
        gl.glEnable(GL10.GL_DEPTH_TEST) // Enables depth-buffer for hidden surface removal
        gl.glDepthFunc(GL10.GL_LEQUAL) // The type of depth testing to do
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST) // nice perspective view
        gl.glShadeModel(GL10.GL_SMOOTH) // Enable smooth shading of color
        gl.glDisable(GL10.GL_DITHER) // Disable dithering for better performance
    }

    // Call back after onSurfaceCreated() or whenever the window's size changes
    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        var height = height
        if (height == 0) height = 1 // To prevent divide by zero
        val aspect = width.toFloat() / height
        // Set the viewport (display area) to cover the entire window
        gl.glViewport(0, 0, width, height)
        // Setup perspective projection, with aspect ratio matches viewport
        gl.glMatrixMode(GL10.GL_PROJECTION) // Select projection matrix
        gl.glLoadIdentity() // Reset projection matrix
        // Use perspective projection
        GLU.gluPerspective(gl, 80f, aspect, 0.1f, 100f)
        gl.glMatrixMode(GL10.GL_MODELVIEW) // Select model-view matrix
        gl.glLoadIdentity() // Reset
        GLES10.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA)
        GLES10.glEnable(GL10.GL_BLEND)
    }

    // Call back to draw the current frame.
    override fun onDrawFrame(gl: GL10) {
        // Clear color and depth buffers using clear-value set earlier
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
        // Update the rotational angle after each refresh (NEW)
        angleX += speedX;  // (NEW)
        angleY += speedY;  // (NEW)
        //Log.e(MyGLRenderer::class.java.simpleName,angleX.toString())
        //Log.e(MyGLRenderer::class.java.simpleName,angleY.toString())
        // ----- Render Shapes -----
        gl.glLoadIdentity();              // Reset the model-view matrix
        gl.glTranslatef(0.0f, 0.0f, z)   // Translate into the screen
        gl.glRotatef(angleX, 1.0f, 0.0f, 0.0f) // Rotate
        gl.glRotatef(angleY, 0.0f, 1.0f, 0.0f) // Rotate
        floor?.draw(gl)
        /*object1?.draw(gl)
        object2?.draw(gl)
        quad!!.draw(gl,0.0f,2.20f,0f) // Draw Sensor
        scanArea?.draw(gl,-0.85f,0.0f,-0.45f,0.85f,2.20f,0.05f)
        virtualPushButton1?.draw(gl,1.00f,1.20f,-0.15f,1.20f,1.40f,0.05f)
        cube?.draw(gl,-0.85f,0.0f,-0.45f,0.85f,2.20f,0.05f)
        leftdoor1?.draw(gl)
        leftdoor2?.draw(gl)
        scanningCone?.draw(gl,0.0f,2.20f,0f)*/
        waterdrop?.draw(gl,-0.85f,0.0f,-0.45f,0.85f,2.20f,0.05f)
    }
}
