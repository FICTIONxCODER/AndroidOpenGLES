package com.bea.vieweropengl

import android.content.Context
import android.opengl.GLES10.*
import android.opengl.GLSurfaceView
import android.opengl.GLU
import android.util.Log
import android.util.Log.ASSERT
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class MyGLRenderer : GLSurfaceView.Renderer {

    var triangle : Triangle? = null
    var quad: Square? = null
    private var pyramid : Pyramid? = null

    private var cube : Cube? = null
    private var leftdoor1 : LeftDoor? = null
    private var leftdoor2 : LeftDoor? = null
    //private var rightDoor : RightDoor? = null

    //private var circle:Circle? = null

    private var sensorCone:cone? = null
    private var scanArea:ScanArea? = null
    private var virtualPushButton1:VirtualPushButton? =null
    private var virtualPushButton2:VirtualPushButton? =null
    private var floor:Floor? =null

    // For controlling cube's z-position, x and y angles and speeds (50,-30) for clear floor)otherwise (40,-35)
    var angleX = 50f // (NEW)

    var angleY = -30f // (NEW)

    var speedX = 0f // (NEW)

    var speedY = 0f // (NEW)

    var z = -6.0f // (NEW)


    /*private var anglePyramid = 0f // Rotational angle in degree for pyramid (NEW)

    private var angleCube = 0f // Rotational angle in degree for cube (NEW)

    private val speedPyramid = 2.0f // Rotational speed for pyramid (NEW)

    private val speedCube = -1.5f // Rotational speed for cube (NEW)*/


    // Application's context
    var context : Context? = null

    // Constructor with global application context
    constructor(context: Context?) {
        this.context = context

        triangle = Triangle()

        quad = Square()

        //pyramid = Pyramid() // (NEW)

        cube = Cube(-0.85f,0.0f,-0.45f,0.85f,2.20f,0.05f)
        leftdoor1 = LeftDoor(-1.70f,0.00f,-0.15f,-0.85f,2.20f,0.05f)
        leftdoor2 = LeftDoor(0.85f,0.00f,-0.15f,1.70f,2.20f,0.05f)
        //rightDoor = RightDoor()

        //circle = Circle()
        sensorCone = cone()
        scanArea = ScanArea(-0.85f,0.0f,-0.45f,0.85f,2.20f,0.05f)
        virtualPushButton1 = VirtualPushButton(1.00f,1.20f,-0.15f,1.20f,1.40f,0.05f)
        //virtualPushButton2 = VirtualPushButton(0.2f,-0.7f,0.7f,0.05f)
        floor= Floor()
    }

    // Call back when the surface is first created or re-created
    override fun onSurfaceCreated(gl: GL10, config: EGLConfig?) {
        //Blend & transpency
        /*glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        glEnable(GL_BLEND)
*/
        //glClearColor(0.0f,0.0f,0.0f,0.5f)
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

        // You OpenGL|ES display re-sizing code here
        // ......
    }

    // Call back to draw the current frame.
    override fun onDrawFrame(gl: GL10) {
        // Clear color and depth buffers using clear-value set earlier
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)

        //gl.glDisable(GL10.GL_DEPTH_TEST); // Turn depth testing off (NEW)
        /*// You OpenGL|ES rendering code here
        // Clear color and depth buffers using clear-values set earlier
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)

        gl.glLoadIdentity() // Reset model-view matrix ( NEW )

        gl.glTranslatef(-1.5f, 0.0f, -6.0f) // Translate left and into the screen ( NEW )

        triangle!!.draw(gl) // Draw triangle ( NEW )


        // Translate right, relative to the previous translation ( NEW )

        // Translate right, relative to the previous translation ( NEW )
        gl.glTranslatef(3.0f, 0.0f, 0.0f)
        quad!!.draw(gl) // Draw quad ( NEW )*/


       /* // ----- Render the Pyramid -----
        gl.glLoadIdentity();                 // Reset the model-view matrix
        gl.glTranslatef(-1.5f, 0.0f, -6.0f); // Translate left and into the screen
        gl.glRotatef(anglePyramid, 0.1f, 1.0f, -0.1f); // Rotate (NEW)
        pyramid?.draw(gl);                              // Draw the pyramid (NEW)

        // ----- Render the Color Cube -----
        gl.glLoadIdentity();                // Reset the model-view matrix
        gl.glTranslatef(1.5f, 0.0f, -6.0f); // Translate right and into the screen
        gl.glScalef(0.8f, 0.8f, 0.8f);      // Scale down (NEW)
        gl.glRotatef(angleCube, 1.0f, 1.0f, 1.0f); // rotate about the axis (1,1,1) (NEW)
        cube?.draw(gl);                      // Draw the cube (NEW)

        // Update the rotational angle after each refresh (NEW)
        anglePyramid += speedPyramid;   // (NEW)
        angleCube += speedCube;         // (NEW)        */

        // Update the rotational angle after each refresh (NEW)
        angleX += speedX;  // (NEW)
        angleY += speedY;  // (NEW)
        Log.e(MyGLRenderer::class.java.simpleName,angleX.toString())
        Log.e(MyGLRenderer::class.java.simpleName,angleY.toString())
        // ----- Render the Cube -----
        gl.glLoadIdentity();              // Reset the model-view matrix
        gl.glTranslatef(0.0f, 0.0f, z)   // Translate into the screen
        gl.glRotatef(angleX, 1.0f, 0.0f, 0.0f) // Rotate
        gl.glRotatef(angleY, 0.0f, 1.0f, 0.0f) // Rotate
        floor?.draw(gl)
        cube?.draw(gl)
        leftdoor1?.draw(gl)
        leftdoor2?.draw(gl)
        //rightDoor?.draw(gl)


        quad!!.draw(gl) // Draw quad
        //virtual push button
        //gl.glTranslatef(0f, 0.0f, 2.0f) // Translate left and into the screen
        //circle?.draw(gl) // Draw circle ( NEW )
        scanArea?.draw(gl,-0.85f,0.0f,-0.45f,0.85f,2.20f,0.05f)
        virtualPushButton1?.draw(gl,1.00f,1.20f,-0.15f,1.20f,1.40f,0.05f)
        //virtualPushButton2?.draw(gl,0.2f,-0.7f,0.7f,0.05f)
        //sensorCone?.draw(gl)        //Sensor scan(Umbrela)
        floor?.draw(gl)
    }
}
