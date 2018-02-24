package de.mkammerer.easykrypto.symmetric.impl

import de.mkammerer.easykrypto.symmetric.Salt
import java.io.InputStream
import java.util.*

class SaltImpl(private val bytes: ByteArray) : Salt {
    override fun asStream(): InputStream = bytes.inputStream()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SaltImpl

        if (!Arrays.equals(bytes, other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(bytes)
    }
}