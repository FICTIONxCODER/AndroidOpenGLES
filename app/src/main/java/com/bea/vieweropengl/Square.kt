package com.bea.vieweropengl
//  Created by BEA on 2021.
//  Copyright © 2021 BEA. All rights reserved.
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10
//Square class
class Square {
    // Buffer for vertex-array
    private var vertexBuffer : FloatBuffer? = null

    /*// Vertices for the square
    private val vertices = floatArrayOf(
            -0.2f, 2.20f, 0.0f,      // 0. left-bottom
            0.2f, 2.20f, 0.0f,       // 1. right-bottom
            -0.2f, 2.40f, 0.0f,      // 2. left-top
            0.2f, 2.40f, 0.0f        // 3. right-top
    )*/

    // Vertices of the 6 faces
    private fun SensorCoordinates(x:Float, y:Float, z:Float):FloatArray {
        val vertices = mutableListOf<Float>()
        //FRONT
        vertices.add(x-0.1f)		//Left bottom front
        vertices.add(y)
        vertices.add(z)
        vertices.add(x+0.1f)		//Right bottom front
        vertices.add(y)
        vertices.add(z)
        vertices.add(x-0.1f)		//Left Top front
        vertices.add(y+0.1f)
        vertices.add(z)
        vertices.add(x+0.1f)		//Right Top front
        vertices.add(y+0.1f)
        vertices.add(z)
        //BACK
        vertices.add(x-0.1f)	    //Left bottom back
        vertices.add(y)
        vertices.add(z-0.1f)
        vertices.add(x+0.1f)		//Right bottom back
        vertices.add(y)
        vertices.add(z-0.1f)
        vertices.add(x-0.1f)		//Left Top back
        vertices.add(y+0.1f)
        vertices.add(z-0.1f)
        vertices.add(x+0.1f)		//Right Top back
        vertices.add(y+0.1f)
        vertices.add(z-0.1f)
        //LEFT
        vertices.add(x-0.1f)	    //Left bottom back
        vertices.add(y)
        vertices.add(z-0.1f)
        vertices.add(x-0.1f)		//Left bottom front
        vertices.add(y)
        vertices.add(z)
        vertices.add(x-0.1f)		//Left Top back
        vertices.add(y+0.1f)
        vertices.add(z-0.1f)
        vertices.add(x-0.1f)		//Left Top front
        vertices.add(y+0.1f)
        vertices.add(z)
        // RIGHT
        vertices.add(x+0.1f)		//Right bottom front
        vertices.add(y)
        vertices.add(z)
        vertices.add(x+0.1f)		//Right bottom back
        vertices.add(y)
        vertices.add(z-0.1f)
        vertices.add(x+0.1f)		//Right Top front
        vertices.add(y+0.1f)
        vertices.add(z)
        vertices.add(x+0.1f)		//Right Top back
        vertices.add(y+0.1f)
        vertices.add(z-0.1f)
        // TOP
        vertices.add(x-0.1f)		//Left Top front
        vertices.add(y+0.1f)
        vertices.add(z)
        vertices.add(x+0.1f)		//Right Top front
        vertices.add(y+0.1f)
        vertices.add(z)
        vertices.add(x-0.1f)		//Left Top back
        vertices.add(y+0.1f)
        vertices.add(z-0.1f)
        vertices.add(x+0.1f)		//Right Top back
        vertices.add(y+0.1f)
        vertices.add(z-0.1f)
        // BOTTOM
        vertices.add(x-0.1f)	    //Left bottom back
        vertices.add(y)
        vertices.add(z-0.1f)
        vertices.add(x+0.1f)		//Right bottom back
        vertices.add(y)
        vertices.add(z-0.1f)
        vertices.add(x-0.1f)		//Left bottom front
        vertices.add(y)
        vertices.add(z)
        vertices.add(x+0.1f)		//Right bottom front
        vertices.add(y)
        vertices.add(z)
        Log.e(LeftDoor::class.java.simpleName,vertices.toString())
        return vertices.toFloatArray()
    }


    // Constructor - Setup the vertex buffer
    constructor(x:Float,y:Float,z:Float) {
        // Setup vertex array buffer. Vertices in float. A float has 4 bytes
        val vbb = ByteBuffer.allocateDirect(SensorCoordinates(x,y,z).size * 4)
        vbb.order(ByteOrder.nativeOrder()) // Use native byte order
        vertexBuffer = vbb.asFloatBuffer() // Convert from byte to float
        vertexBuffer?.put(SensorCoordinates(x,y,z)) // Copy data into buffer
        vertexBuffer?.position(0) // Rewind
    }

    // Render the shape
    fun draw(gl: GL10,x:Float,y:Float,z:Float) {
        //Updating points on each draw
        vertexBuffer?.clear()
        vertexBuffer?.put(SensorCoordinates(x,y,z))     // Copy data into buffer
        vertexBuffer?.position(0)             // Rewind
        // Enable vertex-array and define its buffer
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)
        gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);      // Set the current color (NEW)
        // Draw the primitives from the vertex-array directly
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, SensorCoordinates(x,y,z).size / 3)
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
    }
}