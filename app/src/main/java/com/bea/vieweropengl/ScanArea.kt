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

class ScanArea {


    // Buffer for vertex-array
    private var vertexBuffer: FloatBuffer? = null

    fun scanAreaCoordinates(xMin: Float, yMin: Float, zMin: Float, xMax: Float, yMax: Float, zMax: Float):FloatArray {
        //var mid:Double = (((xMax - xMin) / 2 - xMin).toDouble() - xMin).pow(2.0) + (((yMax - yMin) / 2 - yMin).toDouble() - yMin).pow(2.0)
        //var radius: Double = sqrt(mid)
        var radius: Double =1.0
        var x: Float = xMax-((xMax-xMin)/2)          //center of circle
        var y: Float = 0.0f
        var z:Float = 1.05f
        Log.d(ScanArea::class.java.simpleName, "radius: $radius")
        Log.d(ScanArea::class.java.simpleName, "Center: $x,$y,$z")
        val vertices = mutableListOf<Float>()
        for (i in 0..360) {
            var angle: Double = (2 * Math.PI * i / 360)
            var xCordinate: Double = cos(angle) * radius
            var zCordinate: Double = sin(angle) * radius

            vertices.add(x + xCordinate.toFloat())      //X coordinate added
            vertices.add(y)                           //Y coordinate added
            vertices.add(z + zCordinate.toFloat())       //Z coordinate added
        }
        Log.d("ScaningArea",vertices.toString())
        return vertices.toFloatArray()
    }


    // Constructor - Setup the vertex buffer
    constructor(xMin: Float, yMin: Float, zMin: Float, xMax: Float, yMax: Float, zMax: Float) {
        // Setup vertex array buffer. Vertices in float. A float has 4 bytes
        val vbb = ByteBuffer.allocateDirect(scanAreaCoordinates(xMin, yMin, zMin, xMax, yMax, zMax).size * 4)
        vbb.order(ByteOrder.nativeOrder()) // Use native byte order
        vertexBuffer = vbb.asFloatBuffer() // Convert from byte to float
        vertexBuffer?.put(scanAreaCoordinates(xMin, yMin, zMin, xMax, yMax, zMax)) // Copy data into buffer
        vertexBuffer?.position(0) // Rewind
    }

    // Render the shape
    fun draw(gl: GL10, xMin: Float, yMin: Float, zMin: Float, xMax: Float, yMax: Float, zMax: Float) {
        // Enable vertex-array and define its buffer
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)
        gl.glColor4f(0.0f, 0.5f, 1.0f, 0.5f);      // Set the current color (NEW)
        // Draw the primitives from the vertex-array directly
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, scanAreaCoordinates(xMin, yMin, zMin, xMax, yMax, zMax).size / 3)
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
    }

}