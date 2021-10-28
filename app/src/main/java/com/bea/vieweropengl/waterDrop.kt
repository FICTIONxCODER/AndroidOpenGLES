package com.bea.vieweropengl

import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10
import kotlin.math.cos
import kotlin.math.sin

//  Created by BEA on 2021.
//  Copyright © 2021 BEA. All rights reserved.


class waterDrop {

    // Buffer for vertex-array
    private var vertexBuffer: FloatBuffer? = null

    /*fun scanAreaCoordinates(xMin: Float, yMin: Float, zMin: Float, xMax: Float, yMax: Float, zMax: Float):FloatArray {
        //var mid:Double = (((xMax - xMin) / 2 - xMin).toDouble() - xMin).pow(2.0) + (((yMax - yMin) / 2 - yMin).toDouble() - yMin).pow(2.0)
        //var radius: Double = sqrt(mid)
        var radius: Double =1.0
        var x: Float = xMax-((xMax-xMin)/2)          //center of circle
        var y: Float = 0.0f
        var z:Float = 1.05f
        //Log.d(ScanArea::class.java.simpleName, "radius: $radius")
        //Log.d(ScanArea::class.java.simpleName, "Center: $x,$y,$z")
        val vertices = mutableListOf<Float>()
        for (i in 0..10) {
            for (j in 0..360) {
                var angle: Double = (2 * Math.PI * j / 360)
                var xCordinate: Double = cos(angle) * radius
                var zCordinate: Double = sin(angle) * radius

                vertices.add(x + xCordinate.toFloat())      //X coordinate added
                vertices.add(y)                           //Y coordinate added
                vertices.add(z + zCordinate.toFloat())       //Z coordinate added
            }
        }
        generateSphereData(10,10,2.0f)


        return vertices.toFloatArray()
    }*/

    lateinit var mVertices: FloatArray
    lateinit var mNormals: FloatArray
    //lateinit var mTexture: FloatArray
    lateinit var mIndexes: CharArray

    // rings defines how many circles exists from the bottom to the top of the sphere
    // sectors defines how many vertexes define a single ring
    // radius defines the distance of every vertex from the center of the sphere.
    fun generateSphereData(totalRings: Int, totalSectors: Int, radius: Float) {
        mVertices = FloatArray(totalRings * totalSectors * 3)	//Vertices calculation
        mNormals = FloatArray(totalRings * totalSectors * 3)
        //mTexture = FloatArray(totalRings * totalSectors * 2)
        mIndexes = CharArray(totalRings * totalSectors * 6)
        val R = 1f / (totalRings - 1).toFloat()
        val S = 1f / (totalSectors - 1).toFloat()
        var r: Int
        var s: Int
        var x: Float
        var y: Float
        var z: Float
        var vertexIndex = 0
        var textureIndex = 0
        var indexIndex = 0
        var normalIndex = 0
        r = 0
        while (r < totalRings) {
            s = 0
            while (s < totalSectors) {
                y = (Math.sin(-Math.PI / 2f + Math.PI * r * R)+1.10).toFloat()
                x = (Math.cos(2f * Math.PI * s * S).toFloat() * Math.sin(Math.PI * r * R)*0.75)
                    .toFloat()
                z = (Math.sin(2f * Math.PI * s * S).toFloat() * Math.sin(Math.PI * r * R)+1)
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
        /*var r1: Int
        var s1: Int
        r = 0
        while (r < totalRings) {
            s = 0
            while (s < totalSectors) {
                r1 = if (r + 1 == totalRings) 0 else r + 1
                s1 = if (s + 1 == totalSectors) 0 else s + 1
                mIndexes[indexIndex] = (r * totalSectors + s).toChar()
                mIndexes[indexIndex + 1] = (r * totalSectors + s1).toChar()
                mIndexes[indexIndex + 2] = (r1 * totalSectors + s1).toChar()
                mIndexes[indexIndex + 3] = (r1 * totalSectors + s).toChar()
                mIndexes[indexIndex + 4] = (r1 * totalSectors + s1).toChar()
                mIndexes[indexIndex + 5] = (r * totalSectors + s).toChar()
                indexIndex += 6
                s++
            }
            r++
        }*/
        Log.d(waterDrop::class.java.simpleName, "mVertices: ${mVertices.asList()}")
        Log.d(waterDrop::class.java.simpleName, "mNormals: ${mNormals.asList()}")
        //Log.d(waterDrop::class.java.simpleName, "mTexture: ${mTexture.asList()}")
        Log.d(waterDrop::class.java.simpleName, "mIndexes: ${mIndexes.asList()}")
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