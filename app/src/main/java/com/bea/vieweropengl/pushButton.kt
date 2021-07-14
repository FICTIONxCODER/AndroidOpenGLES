package com.bea.vieweropengl
//  Created by BEA on 2021.
//  Copyright © 2021 BEA. All rights reserved.
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10

class pushButton {

    companion object{
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
    }

    // Vertices for the square
    private val vertices = floatArrayOf(
            1.0f,-1.00f,0.0f,
            0.9781476259231567f,-1.00f,0.2079116851091385f,
            0.9135454297065735f,-1.00f,0.4067366421222687f,
            0.80901700258255f,-1.00f,0.5877852439880371f,
            0.6691306233406067f,-1.00f,0.7431448101997375f,
            0.5f,-1.00f,0.8660253882408142f,
            0.30901700258255005f,-1.00f,0.9510565400123596f,
            0.10452846437692642f,-1.00f,0.9945219159126282f,
            -0.10452846437692642f,-1.00f,0.9945219159126282f,
            -0.30901700258255005f,-1.00f,0.9510565400123596f,
            -0.5f,-1.00f,0.8660253882408142f,
            -0.6691306233406067f,-1.00f,0.7431448101997375f,
            -0.80901700258255f,-1.00f,0.5877852439880371f,
            -0.9135454297065735f,-1.00f,0.4067366421222687f,
            -0.9781476259231567f,-1.00f,0.2079116851091385f,
            -1.0f,-1.00f,5.665538686387998E-16f,
            -0.9781476259231567f,-1.00f,-0.2079116851091385f,
            -0.9135454297065735f,-1.00f,-0.4067366421222687f,
            -0.80901700258255f,-1.00f,-0.5877852439880371f,
            -0.6691306233406067f,-1.00f,-0.7431448101997375f,
            -0.5f,-1.00f,-0.8660253882408142f,
            -0.30901700258255005f,-1.00f,-0.9510565400123596f,
            -0.10452846437692642f,-1.00f,-0.9945219159126282f,
            0.10452846437692642f,-1.00f,-0.9945219159126282f,
            0.30901700258255005f,-1.00f,-0.9510565400123596f,
            0.5f,-1.00f,-0.8660253882408142f,
            0.6691306233406067f,-1.00f,-0.7431448101997375f,
            0.80901700258255f,-1.00f,-0.5877852439880371f,
            0.9135454297065735f,-1.00f,-0.4067366421222687f,
            0.9781476259231567f,-1.00f,-0.2079116851091385f,
            1.0f,-1.00f,-1.1331077372775996E-15f)

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
        gl.glColor4f(0.0f, 0.5f, 1.0f, 1.0f);      // Set the current color (NEW)
        // Draw the primitives from the vertex-array directly
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, vertices.size / 3)
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
    }

}