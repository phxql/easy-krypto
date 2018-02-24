package de.mkammerer.easykrypto.symmetric.impl

import de.mkammerer.easykrypto.symmetric.Key
import java.io.InputStream
import java.io.OutputStream
import java.util.*

class KeyImpl(private val bytes: ByteArray) : Key {
    override fun asStream(): InputStream = bytes.inputStream()

    override fun asBytes(): ByteArray = bytes

    override fun saveToStream(stream: OutputStream) {
        asStream().copyTo(stream)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KeyImpl

        if (!Arrays.equals(bytes, other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(bytes)
    }
}