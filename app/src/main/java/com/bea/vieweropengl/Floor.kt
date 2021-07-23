package com.bea.vieweropengl

import android.opengl.GLES32.GL_QUADS
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10


//  Created by BEA on 2021.
//  Copyright © 2021 BEA. All rights reserved.


class Floor {

    // Buffer for vertex-array
    private var vertexBuffer: FloatBuffer? = null

    private fun FloorCoordinates(): FloatArray {
        val vertices = mutableListOf<Float>()

        for (i in -5..5) {
            for (j in -5..5) {
                if(j!=10 || i!=10){
                    vertices.add(i.toFloat())
                    vertices.add(-1.0f)
                    vertices.add(j.toFloat())
                }

            }
        }

        return vertices.toFloatArray()
    }

    // Constructor - Setup the vertex buffer
    constructor() {
        // Setup vertex array buffer. Vertices in float. A float has 4 bytes
        val vbb = ByteBuffer.allocateDirect(FloorCoordinates().size * 4)
        vbb.order(ByteOrder.nativeOrder()) // Use native byte order
        vertexBuffer = vbb.asFloatBuffer() // Convert from byte to float
        vertexBuffer?.put(FloorCoordinates()) // Copy data into buffer
        vertexBuffer?.position(0) // Rewind
    }

    // Render the shape
    fun draw(gl: GL10) {
        // Enable vertex-array and define its buffer
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)
        gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);      // Set the current color (NEW)
        // Draw the primitives from the vertex-array directly
        gl.glDrawArrays(GL10.GL_LINES, 0, FloorCoordinates().size / 3)
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
    }
}