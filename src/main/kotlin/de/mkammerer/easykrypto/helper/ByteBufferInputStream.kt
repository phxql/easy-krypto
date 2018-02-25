package de.mkammerer.easykrypto.helper

import java.io.InputStream
import java.nio.ByteBuffer

/**
 * Wraps a [ByteBuffer] into an [InputStream].
 */
class ByteBufferInputStream(private val buffer: ByteBuffer) : InputStream() {
    override fun read(): Int {
        if (!buffer.hasRemaining()) return -1

        return buffer.get().toInt().and(0xFF)
    }

    override fun read(b: ByteArray, off: Int, len: Int): Int {
        if (!buffer.hasRemaining()) return -1

        val length = Math.min(len, buffer.remaining())
        buffer.get(b, off, length)
        return length
    }
}