package com.fictionXcoder.vieweropengl

import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10


class Sphere {

    // Buffer for vertex-array
    private var vertexBuffer: FloatBuffer? = null

    lateinit var mVertices: FloatArray
    lateinit var mNormals: FloatArray
    lateinit var mIndexes: CharArray

    // rings defines how many circles exists from the bottom to the top of the sphere
    // sectors defines how many vertexes define a single ring
    // radius defines the distance of every vertex from the center of the sphere.
    fun generateSphereData(totalRings: Int, totalSectors: Int, radius: Float) {
        mVertices = FloatArray(totalRings * totalSectors * 3)	//Vertices calculation
        mNormals = FloatArray(totalRings * totalSectors * 3)
        mIndexes = CharArray(totalRings * totalSectors * 6)
        val R = 1f / (totalRings - 1).toFloat()
        val S = 1f / (totalSectors - 1).toFloat()
        var r: Int
        var s: Int
        var x: Float
        var y: Float
        var z: Float
        var vertexIndex = 0
        var normalIndex = 0
        r = 0
        while (r < totalRings) {
            s = 0
            while (s < totalSectors) {
                y = (Math.sin(-Math.PI / 2f + Math.PI * r * R)+1.10).toFloat()
                x = (Math.cos(2f * Math.PI * s * S).toFloat() * Math.sin(Math.PI * r * R))
                    .toFloat()
                z = (Math.sin(2f * Math.PI * s * S).toFloat() * Math.sin(Math.PI * r * R))
                    .toFloat()
                mVertices[vertexIndex] = x * radius
                mVertices[vertexIndex + 1] = y * radius
                mVertices[vertexIndex + 2] = z * radius
                vertexIndex += 3
                mNormals[normalIndex] = x
                mNormals[normalIndex + 1] = y
                mNormals[normalIndex + 2] = z
                normalIndex += 3
                s++
            }
            r++
        }

        Log.d(Sphere::class.java.simpleName, "mVertices: ${mVertices.asList()}")
        Log.d(Sphere::class.java.simpleName, "mNormals: ${mNormals.asList()}")
        Log.d(Sphere::class.java.simpleName, "mIndexes: ${mIndexes.asList()}")
    }

    // Constructor - Setup the vertex buffer
    constructor(xMin: Float, yMin: Float, zMin: Float, xMax: Float, yMax: Float, zMax: Float) {
        generateSphereData(360,360,1.0f)
        // Setup vertex array buffer. Vertices in float. A float has 4 bytes
        val vbb = ByteBuffer.allocateDirect(mVertices.size * 4)
        vbb.order(ByteOrder.nativeOrder()) // Use native byte order
        vertexBuffer = vbb.asFloatBuffer() // Convert from byte to float
        vertexBuffer?.put(mVertices) // Copy data into buffer
        vertexBuffer?.position(0) // Rewind
    }

    // Render the shape and update at every refresh
    fun draw(gl: GL10, xMin: Float, yMin: Float, zMin: Float, xMax: Float, yMax: Float, zMax: Float) {
        // Enable vertex-array and define its buffer
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)
        gl.glColor4f(0.0f, 0.5f, 1.0f, 0.5f);      // Set the current color (NEW)
        // Draw the primitives from the vertex-array directly
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, mVertices.size / 3)
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
    }

}