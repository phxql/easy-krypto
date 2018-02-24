package de.mkammerer.easykrypto.symmetric

import java.io.InputStream
import java.io.OutputStream

interface Ciphertext {
    fun asBytes(): ByteArray

    fun asStream(): InputStream

    fun saveToStream(stream: OutputStream)
}