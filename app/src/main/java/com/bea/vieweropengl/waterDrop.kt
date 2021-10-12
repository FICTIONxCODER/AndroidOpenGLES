package com.bea.vieweropengl

import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10
import kotlin.math.PI

//  Created by BEA on 2021.
//  Copyright © 2021 BEA. All rights reserved.


class waterDrop {
    // Buffer for vertex-array
    private var vertexBuffer : FloatBuffer? = null
    private val numFaces = 6

    // Vertices of the 6 faces
    private fun SideScreenSafetyCoordinates(xMin:Float, yMin:Float, zMin:Float, xMax:Float, yMax:Float, zMax:Float):FloatArray {
        val vertices = mutableListOf<Float>()
        val radius:Float = 10.0f
        val totalPoints:Float = 100.0f
        for(i in 0..100){
            var longitude:Float = mapOf<>(i,0,totalPoints,-PI, PI)
            for(j in 0..100){

            }
        }
        vertices.add(xMax)		//Sphere coordinates
        vertices.add(yMin)
        vertices.add(zMax)
        Log.e(waterDrop::class.java.simpleName,vertices.toString())
        return vertices.toFloatArray()
    }

    // Constructor - Set up the buffers
    constructor(xMin:Float, yMin:Float, zMin:Float, xMax:Float, yMax:Float, zMax:Float) {
        // Setup vertex-array buffer. Vertices in float. An float has 4 bytes
        val vbb: ByteBuffer = ByteBuffer.allocateDirect(SideScreenSafetyCoordinates(xMin, yMin, zMin, xMax, yMax, zMax).size * 4)
        vbb.order(ByteOrder.nativeOrder()) // Use native byte order
        vertexBuffer = vbb.asFloatBuffer() // Convert from byte to float
        vertexBuffer?.put(SideScreenSafetyCoordinates(xMin, yMin, zMin, xMax, yMax, zMax)) // Copy data into buffer
        vertexBuffer?.position(0) // Rewind
    }

    // Draw the shape
    fun draw(gl: GL10) {
        gl.glFrontFace(GL10.GL_CCW) // Front face in counter-clockwise orientation
        //gl.glEnable(GL10.GL_CULL_FACE) // Enable cull face
        gl.glCullFace(GL10.GL_BACK) // Cull the back face (don't display)
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)

        /* GLES10.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA)
         GLES10.glEnable(GL10.GL_BLEND)*/

        // Render all the faces
        for (face in 0 until numFaces) {
            // Set the color for each of the faces
            gl.glColor4f(0.5f,0.7f,0.5f,0.5f)
            // Draw the primitive from the vertex-array directly
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face * 4, 4)
        }
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
        //gl.glDisable(GL10.GL_CULL_FACE)
    }
}