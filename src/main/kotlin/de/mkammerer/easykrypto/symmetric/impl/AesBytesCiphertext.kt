package de.mkammerer.easykrypto.symmetric.impl

import de.mkammerer.easykrypto.symmetric.Ciphertext
import java.io.InputStream
import java.io.OutputStream

class AesBytesCiphertext(
        val nonce: ByteArray,
        val ciphertext: ByteArray
) : Ciphertext {
    override fun asBytes(): ByteArray = nonce + ciphertext

    override fun asStream(): InputStream = asBytes().inputStream()

    override fun saveToStream(stream: OutputStream) {
        asStream().copyTo(stream)
    }
}