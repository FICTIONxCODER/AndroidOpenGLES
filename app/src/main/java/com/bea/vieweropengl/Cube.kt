package com.bea.vieweropengl
//  Created by BEA on 2021.
//  Copyright © 2021 BEA. All rights reserved.
import android.opengl.GLES10
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10
import kotlin.math.cos
import kotlin.math.sin

class Cube {
    // Buffer for vertex-array
    private var vertexBuffer : FloatBuffer? = null
    private val numFaces = 6
    // Buffer for index-array
    private var indexBuffer  : ByteBuffer? = null
    // Vertices of the 6 faces
    private fun SafetyCoordinates(xMin:Float, yMin:Float, zMin:Float, xMax:Float, yMax:Float, zMax:Float):FloatArray {

        val vertices = mutableListOf<Float>()
        //FRONT
        vertices.add(xMin)		//Left bottom front
        vertices.add(yMin)
        vertices.add(zMax)
        vertices.add(xMax)		//Right bottom front
        vertices.add(yMin)
        vertices.add(zMax)
        vertices.add(xMin)		//Left Top front
        vertices.add(yMax)
        vertices.add(zMax)
        vertices.add(xMax)		//Right Top front
        vertices.add(yMax)
        vertices.add(zMax)
        //BACK
        vertices.add(xMin)	    //Left bottom back
        vertices.add(yMin)
        vertices.add(zMin)
        vertices.add(xMax)		//Right bottom back
        vertices.add(yMin)
        vertices.add(zMin)
        vertices.add(xMin)		//Left Top back
        vertices.add(yMax)
        vertices.add(zMin)
        vertices.add(xMax)		//Right Top back
        vertices.add(yMax)
        vertices.add(zMin)
        //LEFT
        vertices.add(xMin)	    //Left bottom back
        vertices.add(yMin)
        vertices.add(zMin)
        vertices.add(xMin)		//Left bottom front
        vertices.add(yMin)
        vertices.add(zMax)
        vertices.add(xMin)		//Left Top back
        vertices.add(yMax)
        vertices.add(zMin)
        vertices.add(xMin)		//Left Top front
        vertices.add(yMax)
        vertices.add(zMax)
        // RIGHT
        vertices.add(xMax)		//Right bottom front
        vertices.add(yMin)
        vertices.add(zMax)
        vertices.add(xMax)		//Right bottom back
        vertices.add(yMin)
        vertices.add(zMin)
        vertices.add(xMax)		//Right Top front
        vertices.add(yMax)
        vertices.add(zMax)
        vertices.add(xMax)		//Right Top back
        vertices.add(yMax)
        vertices.add(zMin)
        // TOP
        vertices.add(xMin)		//Left Top front
        vertices.add(yMax)
        vertices.add(zMax)
        vertices.add(xMax)		//Right Top front
        vertices.add(yMax)
        vertices.add(zMax)
        vertices.add(xMin)		//Left Top back
        vertices.add(yMax)
        vertices.add(zMin)
        vertices.add(xMax)		//Right Top back
        vertices.add(yMax)
        vertices.add(zMin)
        // BOTTOM
        vertices.add(xMin)	    //Left bottom back
        vertices.add(yMin)
        vertices.add(zMin)
        vertices.add(xMax)		//Right bottom back
        vertices.add(yMin)
        vertices.add(zMin)
        vertices.add(xMin)		//Left bottom front
        vertices.add(yMin)
        vertices.add(zMax)
        vertices.add(xMax)		//Right bottom front
        vertices.add(yMin)
        vertices.add(zMax)

        return vertices.toFloatArray()
    }

    // Constructor - Set up the buffers
    constructor(xMin:Float, yMin:Float, zMin:Float, xMax:Float, yMax:Float, zMax:Float) {
        // Setup vertex-array buffer. Vertices in float. An float has 4 bytes
        val vbb: ByteBuffer = ByteBuffer.allocateDirect(SafetyCoordinates(xMin, yMin, zMin, xMax, yMax, zMax).size * 4)
        vbb.order(ByteOrder.nativeOrder()) // Use native byte order
        vertexBuffer = vbb.asFloatBuffer() // Convert from byte to float
        vertexBuffer?.put(SafetyCoordinates(xMin, yMin, zMin, xMax, yMax, zMax)) // Copy data into buffer
        vertexBuffer?.position(0) // Rewind

    }

    // Draw the shape
    fun draw(gl: GL10) {
        gl.glFrontFace(GL10.GL_CCW) // Front face in counter-clockwise orientation
        //gl.glEnable(GL10.GL_CULL_FACE) // Enable cull face
        gl.glCullFace(GL10.GL_BACK) // Cull the back face (don't display)
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)

        // Render all the faces
        for (face in 0 until numFaces) {
            // Set the color for each of the faces
            gl.glColor4f(1.0f,0.4f,0.2f,0.7f)
            // Draw the primitive from the vertex-array directly
            //gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, indices.size, GL10.GL_UNSIGNED_BYTE,indexBuffer)
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face * 4, 4)
        }
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
        //gl.glDisable(GL10.GL_CULL_FACE)
    }
}