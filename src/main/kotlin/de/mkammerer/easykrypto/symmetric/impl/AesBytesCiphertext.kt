package de.mkammerer.easykrypto.symmetric.impl

import de.mkammerer.easykrypto.symmetric.Ciphertext
import java.io.InputStream

class AesBytesCiphertext(
        val nonce: ByteArray,
        val ciphertext: ByteArray
) : Ciphertext {
    override fun asStream(): InputStream = (nonce + ciphertext).inputStream()
}