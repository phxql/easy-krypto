package de.mkammerer.easykrypto.symmetric.impl

import de.mkammerer.easykrypto.helper.getBytes
import de.mkammerer.easykrypto.symmetric.Ciphertext
import de.mkammerer.easykrypto.symmetric.Ciphertexts
import java.io.InputStream
import java.nio.ByteBuffer

class AesCiphertexts(private val nonceLengthInBits: Int) : Ciphertexts {
    override fun loadFromStream(stream: InputStream): Ciphertext {
        val bytes = stream.readBytes()
        val buffer = ByteBuffer.wrap(bytes)

        val version = buffer.get()
        if (version != AesBytesCiphertext.VERSION) throw IllegalStateException("Expected version ${AesBytesCiphertext.VERSION}, got $version")

        val cipherId = buffer.get()
        if (cipherId != AesBytesCiphertext.AES) throw IllegalStateException("Expected cipher id ${AesBytesCiphertext.AES}, got $cipherId")

        val nonce = buffer.getBytes(nonceLengthInBits / 8)
        val cipherBytes = buffer.getBytes(buffer.remaining())

        return AesBytesCiphertext(nonce, cipherBytes)
    }
}