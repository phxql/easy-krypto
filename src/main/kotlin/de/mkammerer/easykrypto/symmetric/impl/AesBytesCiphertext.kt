package de.mkammerer.easykrypto.symmetric.impl

import de.mkammerer.easykrypto.helper.ByteBufferInputStream
import de.mkammerer.easykrypto.symmetric.Ciphertext
import java.io.InputStream
import java.nio.ByteBuffer

class AesBytesCiphertext(
        val nonce: ByteArray,
        val ciphertext: ByteArray
) : Ciphertext {
    override fun asStream(): InputStream {
        // version + cipher id + nonce + ciphertext
        val buffer = ByteBuffer.allocate(1 + 1 + nonce.size + ciphertext.size)

        buffer.put(VERSION)
        buffer.put(AES)
        buffer.put(nonce)
        buffer.put(ciphertext)
        buffer.flip()

        return ByteBufferInputStream(buffer)
    }

    companion object {
        const val VERSION = 1.toByte()
        const val AES = 1.toByte()
    }
}