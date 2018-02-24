package de.mkammerer.easykrypto.symmetric

import java.io.InputStream
import java.io.OutputStream

/**
 * A ciphertext (encrypted stuff).
 */
interface Ciphertext {
    /**
     * Returns this ciphertext as byte array.
     */
    fun asBytes(): ByteArray

    /**
     * Returns this ciphertext as stream.
     */
    fun asStream(): InputStream

    /**
     * Saves this ciphertext to a [stream].
     */
    fun saveToStream(stream: OutputStream)
}