package com.bea.vieweropengl

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10

//  Created by BEA on 2021.
//  Copyright © 2021 BEA. All rights reserved.


class VirtualPushButton {


        // Buffer for vertex-array
        private var vertexBuffer: FloatBuffer? = null

        /*private val vertices:FloatArray? =null
        fun CircleCoordinates() {
            var radius: Float = 2.0f
            var x: Float = 0.0f          //center of circle
            var y: Float = 0.0f
            for (i in 0..360) {
                var angle: Double = (2 * Math.PI * i / 360)
                var xCordinate: Double = Math.cos(angle) * radius
                var yCordinate: Double = Math.sin(angle) * radius
                //System.out.println(ori_x + x)
                //println("X:"+(x + xCordinate)+"Y:"+(y + yCordinate))
                vertices?.set(i, x + xCordinate.toFloat())
                vertices?.set(i + 1, y + yCordinate.toFloat())
                vertices?.set(i + 2, -1.0f)
            }
        }*/


    // Vertices for the square
    private val vertices = floatArrayOf(
            1.0999999940395355f,0.20000000298023224f,-1.0f,
            1.0912590324878693f,0.2831646800041199f,-1.0f,
            1.0654181838035583f,0.3626946657896042f,-1.0f,
            1.023606777191162f,0.4351141005754471f,-1.0f,
            0.9676522314548492f,0.49725793302059174f,-1.0f,
            0.8999999910593033f,0.5464101582765579f,-1.0f,
            0.8236067891120911f,0.5804226249456406f,-1.0f,
            0.7418113723397255f,0.597808763384819f,-1.0f,
            0.6581886038184166f,0.597808763384819f,-1.0f,
            0.576393187046051f,0.5804226249456406f,-1.0f,
            0.4999999850988388f,0.5464101582765579f,-1.0f,
            0.43234774470329285f,0.49725793302059174f,-1.0f,
            0.37639319896698f,0.4351141005754471f,-1.0f,
            0.33458179235458374f,0.3626946657896042f,-1.0f,
            0.3087409436702728f,0.2831646800041199f,-1.0f,
            0.29999998211860657f,0.20000000298023246f,-1.0f,
            0.3087409436702728f,0.1168353259563446f,-1.0f,
            0.33458179235458374f,0.03730534017086029f,-1.0f,
            0.37639319896698f,-0.035114094614982605f,-1.0f,
            0.43234774470329285f,-0.09725792706012726f,-1.0f,
            0.4999999850988388f,-0.14641015231609344f,-1.0f,
            0.576393187046051f,-0.1804226189851761f,-1.0f,
            0.6581886038184166f,-0.19780875742435455f,-1.0f,
            0.7418113723397255f,-0.19780875742435455f,-1.0f,
            0.8236067891120911f,-0.1804226189851761f,-1.0f,
            0.8999999910593033f,-0.14641015231609344f,-1.0f,
            0.9676522314548492f,-0.09725792706012726f,-1.0f,
            1.023606777191162f,-0.035114094614982605f,-1.0f,
            1.0654181838035583f,0.03730534017086029f,-1.0f,
            1.0912590324878693f,0.1168353259563446f,-1.0f,
            1.0999999940395355f,0.2000000029802318f,-1.0f)

    // Constructor - Setup the vertex buffer
    constructor() {
        // Setup vertex array buffer. Vertices in float. A float has 4 bytes
        val vbb = ByteBuffer.allocateDirect(vertices?.size * 4)
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
        gl.glColor4f(1.0f, 0.7f, 0.0f, 1.0f);      // Set the current color (NEW)
        // Draw the primitives from the vertex-array directly
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, vertices.size / 3)
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
    }

}