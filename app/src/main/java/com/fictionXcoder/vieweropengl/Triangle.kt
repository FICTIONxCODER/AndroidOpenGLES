package com.fictionXcoder.vieweropengl

import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10
import kotlin.math.cos
import kotlin.math.sin

class Triangle {
    // Buffer for vertex-array
    private var vertexBuffer: FloatBuffer? = null

    fun TriangleCoordinates(): FloatArray {

        val vertices = mutableListOf<Float>()
        vertices.add(0.0f)
        vertices.add(0.0f)
        vertices.add(-2.5f)
        vertices.add(1.0f)
        vertices.add(0.0f)
        vertices.add(-1.0f)
        vertices.add(-1.0f)
        vertices.add(0.0f)
        vertices.add(-1.0f)
        Log.d("Triangle", vertices.toString())
        return vertices.toFloatArray()
    }


    // Constructor - Setup the vertex buffer
    constructor() {
        // Setup vertex array buffer. Vertices in float. A float has 4 bytes
        val vbb = ByteBuffer.allocateDirect(TriangleCoordinates().size * 4)
        vbb.order(ByteOrder.nativeOrder()) // Use native byte order
        vertexBuffer = vbb.asFloatBuffer() // Convert from byte to float
        vertexBuffer?.put(TriangleCoordinates()) // Copy data into buffer
        vertexBuffer?.position(0) // Rewind
    }

    // Render the shape
    fun draw(gl: GL10) {
        // Enable vertex-array and define its buffer
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)
        gl.glColor4f(1.0f, 0.5f, 1.0f, 0.5f);      // Set the current color (NEW)
        // Draw the primitives from the vertex-array directly
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, TriangleCoordinates().size / 3)
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
    }
}
