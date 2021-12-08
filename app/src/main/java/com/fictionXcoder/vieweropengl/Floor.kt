package com.fictionXcoder.vieweropengl
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10

class Floor {

    // Buffer for vertex-array
    private var vertexBuffer: FloatBuffer? = null

    private fun FloorCoordinates(): FloatArray {
        val vertices = mutableListOf<Float>()

        for(i in -10..10)
        {
            vertices.add(i.toFloat())   //X cord
            vertices.add(0.0f)    //Y cord
            vertices.add(-10.0f)   //Z cord

            vertices.add(i.toFloat())   //X cord
            vertices.add(0.0f)    //Y cord
            vertices.add(10.0f)   //Z cord

            vertices.add(-10.0f)   //X cord
            vertices.add(0.0f)    //Y cord
            vertices.add(i.toFloat())   //Z cord

            vertices.add(10.0f)   //X cord
            vertices.add(0.0f)    //Y cord
            vertices.add(i.toFloat())   //Z cord
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
        gl.glColor4f(0.3f, 0.7f, 0.8f, 1.0f);      // Set the current color (NEW)
        // Draw the primitives from the vertex-array directly
        gl.glDrawArrays(GL10.GL_LINES, 0, FloorCoordinates().size / 3)
        gl.glLineWidth(2.0f)
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
    }
}