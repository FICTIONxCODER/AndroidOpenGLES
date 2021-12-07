package com.bea.vieweropengl
//  Created by BEA on 2021.
//  Copyright © 2021 BEA. All rights reserved.
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10
import kotlin.math.cos
import kotlin.math.sin

class Circle {

    // Buffer for vertex-array
    private var vertexBuffer: FloatBuffer? = null

    fun scanAreaCoordinates():FloatArray {
        var radius: Double =1.0
        var x: Float = 0.0f        //center of circle
        var y: Float = 02.0f
        var z:Float = 1.0f
        val vertices = mutableListOf<Float>()
        for (i in 0..10) {
            var angle: Double = (2 * Math.PI * i / 10)
            var xCordinate: Double = cos(angle) * radius
            var zCordinate: Double = sin(angle) * radius

            vertices.add(x + xCordinate.toFloat())          //X coordinate added
            vertices.add(y)                                 //Y coordinate added
            vertices.add(z + zCordinate.toFloat())          //Z coordinate added
            vertices.add(0.0f)                              //X coordinate added
            vertices.add(0.0f)                              //Y coordinate added
            vertices.add(0.0f)                              //Z coordinate added
        }
        Log.d("ScaningArea",vertices.toString())
        return vertices.toFloatArray()
    }


    // Constructor - Setup the vertex buffer
    constructor() {
        // Setup vertex array buffer. Vertices in float. A float has 4 bytes
        val vbb = ByteBuffer.allocateDirect(scanAreaCoordinates().size * 4)
        vbb.order(ByteOrder.nativeOrder()) // Use native byte order
        vertexBuffer = vbb.asFloatBuffer() // Convert from byte to float
        vertexBuffer?.put(scanAreaCoordinates()) // Copy data into buffer
        vertexBuffer?.position(0) // Rewind
    }

    // Render the shape
    fun draw(gl: GL10) {
        // Enable vertex-array and define its buffer
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)
        gl.glColor4f(0.0f, 0.5f, 1.0f, 0.5f);      // Set the current color (NEW)
        // Draw the primitives from the vertex-array directly
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, scanAreaCoordinates().size / 3)
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
    }

}