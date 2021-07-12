package com.bea.vieweropengl
//  Created by BEA on 2021.
//  Copyright © 2021 BEA. All rights reserved.

import android.opengl.GLES10
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10

class RightDoor {
    // Buffer for vertex-array
    private var vertexBuffer : FloatBuffer? = null
    private val numFaces = 6

    /*private val colors = arrayOf(floatArrayOf(0.0f, 1.0f, 0.0f, 1.0f),
            floatArrayOf(0.0f, 1.0f, 0.0f, 1.0f),
            floatArrayOf(0.0f, 1.0f, 0.0f, 1.0f),
            floatArrayOf(0.0f, 1.0f, 0.0f, 1.0f),
            floatArrayOf(0.0f, 1.0f, 0.0f, 1.0f),
            floatArrayOf(0.0f, 1.0f, 0.0f, 1.0f))*/

    // Vertices of the 6 faces
    private val vertices = floatArrayOf(
            // FRONT
            1.0f, -1.0f, 1.0f,  // 0. left-bottom-front
            2.0f, -1.0f, 1.0f,  // 1. right-bottom-front
            1.0f, 1.0f, 1.0f,  // 2. left-top-front
            2.0f, 1.0f, 1.0f,  // 3. right-top-front
            // BACK
            2.0f, -1.0f, 0.7f,  // 6. right-bottom-back
            1.0f, -1.0f, 0.7f,  // 4. left-bottom-back
            2.0f, 1.0f, 0.7f,  // 7. right-top-back
            1.0f, 1.0f, 0.7f,  // 5. left-top-back
            // LEFT
            1.0f, -1.0f, 0.7f,  // 4. left-bottom-back
            1.0f, -1.0f, 1.0f,  // 0. left-bottom-front
            1.0f, 1.0f, 0.7f,  // 5. left-top-back
            1.0f, 1.0f, 1.0f,  // 2. left-top-front
            // RIGHT
            2.0f, -1.0f, 1.0f,  // 1. right-bottom-front
            2.0f, -1.0f, 0.7f,  // 6. right-bottom-back
            2.0f, 1.0f, 1.0f,  // 3. right-top-front
            2.0f, 1.0f, 0.7f,  // 7. right-top-back
            // TOP
            1.0f, 1.0f, 1.0f,  // 2. left-top-front
            2.0f, 1.0f, 1.0f,  // 3. right-top-front
            1.0f, 1.0f, 0.7f,  // 5. left-top-back
            2.0f, 1.0f, 0.7f,  // 7. right-top-back
            // BOTTOM
            1.0f, -1.0f, 0.7f,  // 4. left-bottom-back
            2.0f, -1.0f, 0.7f,  // 6. right-bottom-back
            1.0f, -1.0f, 1.0f,  // 0. left-bottom-front
            2.0f, -1.0f, 1.0f // 1. right-bottom-front
    )

    // Constructor - Set up the buffers
    constructor() {
        // Setup vertex-array buffer. Vertices in float. An float has 4 bytes
        val vbb: ByteBuffer = ByteBuffer.allocateDirect(vertices.size * 4)
        vbb.order(ByteOrder.nativeOrder()) // Use native byte order
        vertexBuffer = vbb.asFloatBuffer() // Convert from byte to float
        vertexBuffer?.put(vertices) // Copy data into buffer
        vertexBuffer?.position(0) // Rewind
    }

    // Draw the shape
    fun draw(gl: GL10) {
        gl.glFrontFace(GL10.GL_CCW) // Front face in counter-clockwise orientation
        gl.glEnable(GL10.GL_CULL_FACE) // Enable cull face
        gl.glCullFace(GL10.GL_BACK) // Cull the back face (don't display)
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)

        GLES10.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA)
        GLES10.glEnable(GL10.GL_BLEND)

        // Render all the faces
        for (face in 0 until numFaces) {
            // Set the color for each of the faces
            //gl.glColor4f(colors[face][0], colors[face][1], colors[face][2], colors[face][3])
            gl.glColor4f(0.5f,0.7f,0.5f,0.5f)
            // Draw the primitive from the vertex-array directly
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face * 4, 4)
        }
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glDisable(GL10.GL_CULL_FACE)
    }
}