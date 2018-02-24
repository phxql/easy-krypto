package de.mkammerer.easykrypto.symmetric

import java.io.InputStream

interface Plaintext {
    fun asBytes(): ByteArray

    fun asString(): String

    fun asStream(): InputStream
}