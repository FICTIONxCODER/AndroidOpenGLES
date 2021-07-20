package com.bea.vieweropengl
//  Created by BEA on 2021.
//  Copyright © 2021 BEA. All rights reserved.
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10

class Cube {
    // Buffer for vertex-array
    private var vertexBuffer : FloatBuffer? = null
    private val numFaces = 6
    // Buffer for index-array
    private var indexBuffer  : ByteBuffer? = null
    // Vertices of the 6 faces
    private val vertices = floatArrayOf(
            // FRONT
            -1.0f, -1.0f, 1.0f,  // 0. left-bottom-front
            1.0f, -1.0f, 1.0f,  // 1. right-bottom-front
            -1.0f, 1.0f, 1.0f,  // 2. left-top-front
            1.0f, 1.0f, 1.0f,  // 3. right-top-front
            // BACK
            -1.0f, -1.0f, -1.0f,  // 4. left-bottom-back
            1.0f, -1.0f, -1.0f,  // 6. right-bottom-back
            -1.0f, 1.0f, -1.0f,  // 5. left-top-back
            1.0f, 1.0f, -1.0f,  // 7. right-top-back
    )
    // Vertex indices of the 4 Triangles
    private val indices = byteArrayOf(
            0, 1, 2, 3, 7, 1, 5, 4, 7, 6, 2, 4, 0, 1
    )

    // Constructor - Set up the buffers
    constructor() {
        // Setup vertex-array buffer. Vertices in float. An float has 4 bytes
        val vbb: ByteBuffer = ByteBuffer.allocateDirect(vertices.size * 4)
        vbb.order(ByteOrder.nativeOrder()) // Use native byte order
        vertexBuffer = vbb.asFloatBuffer() // Convert from byte to float
        vertexBuffer?.put(vertices) // Copy data into buffer
        vertexBuffer?.position(0) // Rewind

        // Setup index-array buffer. Indices in byte.
        indexBuffer = ByteBuffer.allocateDirect(indices.size)
        indexBuffer?.put(indices)
        indexBuffer?.position(0)
    }

    // Draw the shape
    fun draw(gl: GL10) {
        gl.glFrontFace(GL10.GL_CCW) // Front face in counter-clockwise orientation
        gl.glEnable(GL10.GL_CULL_FACE) // Enable cull face
        gl.glCullFace(GL10.GL_BACK) // Cull the back face (don't display)
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)

        //GLES10.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA)
        //GLES10.glEnable(GL10.GL_BLEND)

        // Render all the faces
        for (face in 0 until numFaces) {
            // Set the color for each of the faces
            gl.glColor4f(1f,0.4f,0.2f,0.7f)
            // Draw the primitive from the vertex-array directly
            gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, indices.size, GL10.GL_UNSIGNED_BYTE,indexBuffer)
        }
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glDisable(GL10.GL_CULL_FACE)
    }
}