package com.bea.vieweropengl
//  Created by BEA on 2021.
//  Copyright © 2021 BEA. All rights reserved.
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10


//Square class
class Square {
    // Buffer for vertex-array
    private var vertexBuffer : FloatBuffer? = null

    private val vertices = floatArrayOf( // Vertices for the square
            -1.0f, -1.0f, 0.0f,  // 0. left-bottom
            1.0f, -1.0f, 0.0f,  // 1. right-bottom
            -1.0f, 1.0f, 0.0f,  // 2. left-top
            1.0f, 1.0f, 0.0f // 3. right-top
    )

    // Constructor - Setup the vertex buffer
    constructor() {
        // Setup vertex array buffer. Vertices in float. A float has 4 bytes
        val vbb = ByteBuffer.allocateDirect(vertices.size * 4)
        vbb.order(ByteOrder.nativeOrder()) // Use native byte order
        vertexBuffer = vbb.asFloatBuffer() // Convert from byte to float
        vertexBuffer?.put(vertices) // Copy data into buffer
        vertexBuffer?.position(0) // Rewind
    }

    // Render the shape
    fun draw(gl: GL10) {
        // Enable vertex-array and define its buffer
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)
        gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f);      // Set the current color (NEW)
        // Draw the primitives from the vertex-array directly
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.size / 3)
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
    }
}