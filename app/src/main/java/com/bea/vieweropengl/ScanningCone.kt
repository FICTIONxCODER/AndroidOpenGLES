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

class ScanningCone {
    // Buffer for vertex-array
    private var vertexBuffer : FloatBuffer? = null

      fun scanSphereCoordinates(xMin: Float, yMin: Float, zMin: Float, xMax: Float, yMax: Float, zMax: Float):FloatArray {
          //var mid:Double = (((xMax - xMin) / 2 - xMin).toDouble() - xMin).pow(2.0) + (((yMax - yMin) / 2 - yMin).toDouble() - yMin).pow(2.0)
          //var radius: Double = sqrt(mid)
          var radius: Double =1.0
          val vertices = mutableListOf<Float>()
          //CONE
          vertices.add(0.0f)		//cone top points
          vertices.add(2.20f)
          vertices.add(0.0f)

          for (i in 0..360) {
              vertices.add((radius * cos(i * Math.PI / 180f)).toFloat())      //X coordinate added
              vertices.add(0.0f)       //Y coordinate added
              vertices.add((radius * sin(i * Math.PI / 180f) +1.05f).toFloat())      //Z coordinate added
          }

          return vertices.toFloatArray()
      }

    // Constructor - Set up the buffers
    constructor(xMin: Float, yMin: Float, zMin: Float, xMax: Float, yMax: Float, zMax: Float) {
        // Setup vertex-array buffer. Vertices in float. An float has 4 bytes
        val vbb: ByteBuffer = ByteBuffer.allocateDirect(scanSphereCoordinates(xMin, yMin, zMin, xMax, yMax, zMax).size * 4)
        vbb.order(ByteOrder.nativeOrder()) // Use native byte order
        vertexBuffer = vbb.asFloatBuffer() // Convert from byte to float
        vertexBuffer?.put(scanSphereCoordinates(xMin, yMin, zMin, xMax, yMax, zMax)) // Copy data into buffer
        vertexBuffer?.position(0) // Rewind


    }

    // Draw the shape
    fun draw(gl: GL10,xMin: Float, yMin: Float, zMin: Float, xMax: Float, yMax: Float, zMax: Float) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)
        gl.glColor4f(0.0f, 0.5f, 1.0f, 0.5f);      // Set the current color (NEW)
        // Draw the primitives from the vertex-array directly
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN,  0,scanSphereCoordinates(xMin, yMin, zMin, xMax, yMax, zMax).size/3)
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
    }
}