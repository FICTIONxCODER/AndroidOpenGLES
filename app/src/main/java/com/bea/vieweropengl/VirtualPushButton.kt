package com.bea.vieweropengl
//  Created by BEA on 2021.
//  Copyright © 2021 BEA. All rights reserved.
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10
import kotlin.math.cos
import kotlin.math.sin

class VirtualPushButton {

        // Buffer for vertex-array
        private var vertexBuffer: FloatBuffer? = null

        private fun CircleCoordinates():FloatArray {
            var radius: Float = 0.2f
            var x: Float = 1.0f          //center of circle
            var y: Float = 0.0f
            val vertices = mutableListOf<Float>()
            for (i in 0..30) {
                var angle: Double = (2 * Math.PI * i / 30)
                var xCordinate: Double = cos(angle) * radius
                var yCordinate: Double = sin(angle) * radius

                vertices.add(x + xCordinate.toFloat())      //X coordinate added
                vertices.add( y + yCordinate.toFloat())     //Y coordinate added
                vertices.add(-1.0f)                         //Z coordinate added
            }
            return vertices.toFloatArray()
        }

    // Constructor - Setup the vertex buffer
    constructor() {
        // Setup vertex array buffer. Vertices in float. A float has 4 bytes
        val vbb = ByteBuffer.allocateDirect(CircleCoordinates()?.size * 4)
        vbb.order(ByteOrder.nativeOrder()) // Use native byte order
        vertexBuffer = vbb.asFloatBuffer() // Convert from byte to float
        vertexBuffer?.put(CircleCoordinates()) // Copy data into buffer
        vertexBuffer?.position(0) // Rewind
    }

    // Render the shape
    fun draw(gl: GL10) {
        // Enable vertex-array and define its buffer
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)
        gl.glColor4f(1.0f, 0.7f, 0.0f, 1.0f);      // Set the current color (NEW)
        // Draw the primitives from the vertex-array directly
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, CircleCoordinates().size / 3)
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
    }

}