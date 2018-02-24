package de.mkammerer.easykrypto.symmetric.impl

import de.mkammerer.easykrypto.symmetric.Plaintext
import java.io.InputStream

class BytesPlaintext(private val bytes: ByteArray) : Plaintext {
    override fun asStream(): InputStream = bytes.inputStream()

    override fun asString(): String = String(bytes)
}