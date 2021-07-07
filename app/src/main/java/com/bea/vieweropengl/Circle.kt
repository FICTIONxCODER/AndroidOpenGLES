package com.bea.vieweropengl

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10

//  Created by BEA on 2021.
//  Copyright © 2021 BEA. All rights reserved.


class Circle {
    // Buffer for vertex-array
    private var vertexBuffer: FloatBuffer? = null
    // Buffer for index-array
    private var indexBuffer: ByteBuffer? = null
    // Buffer for color-array
    private var colorBuffer: FloatBuffer? = null

    private val vertices = floatArrayOf( // Vertices of the triangle
            0.0f, 1.0f, 0.0f,  // 0. top
            -1.0f, -1.0f, 0.0f,  // 1. left-bottom
            1.0f, -1.0f, 0.0f // 2. right-bottom
    )
    private val indices = byteArrayOf(0, 1, 2) // Indices to above vertices (in CCW)

    /*private val colors = floatArrayOf( // Colors for the vertices (NEW)
            1.0f, 0.0f, 0.0f, 1.0f,  // Red (NEW)
            0.0f, 1.0f, 0.0f, 1.0f,  // Green (NEW)
            0.0f, 0.0f, 1.0f, 1.0f // Blue (NEW)
    )*/
    // Colors for the vertices (NEW)
    private val colors = floatArrayOf( 1.0f, 1.0f, 0.0f, 1.0f)

    // Constructor - Setup the data-array buffers
    constructor() {
        // Setup vertex-array buffer. Vertices in float. A float has 4 bytes.
        val vbb = ByteBuffer.allocateDirect(vertices.size * 4)
        vbb.order(ByteOrder.nativeOrder()) // Use native byte order
        vertexBuffer = vbb.asFloatBuffer() // Convert byte buffer to float
        vertexBuffer?.put(vertices) // Copy data into buffer
        vertexBuffer?.position(0) // Rewind


        // Setup color-array buffer. Colors in float. A float has 4 bytes (NEW)
        val cbb = ByteBuffer.allocateDirect(colors.size * 4)
        cbb.order(ByteOrder.nativeOrder()) // Use native byte order (NEW)

        colorBuffer = cbb.asFloatBuffer() // Convert byte buffer to float (NEW)

        colorBuffer?.put(colors) // Copy data into buffer (NEW)

        colorBuffer?.position(0) // Rewind (NEW)


        // Setup index-array buffer. Indices in byte.
        indexBuffer = ByteBuffer.allocateDirect(indices.size)
        indexBuffer?.put(indices)
        indexBuffer?.position(0)
    }

    // Render this shape
    fun draw(gl: GL10) {
        // Enable vertex-array and define the buffers
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);          // Enable color-array (NEW)
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);  // Define color-array buffer (NEW)

        // Draw the primitives via index-array
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.size, GL10.GL_UNSIGNED_BYTE, indexBuffer)
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);   // Disable color-array (NEW)
    }

}