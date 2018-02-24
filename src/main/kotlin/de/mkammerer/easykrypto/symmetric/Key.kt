package de.mkammerer.easykrypto.symmetric

import java.io.InputStream
import java.io.OutputStream

/**
 * A key, used to encrypt and decrypt stuff.
 */
interface Key {
    /**
     * Returns this key as byte array.
     */
    fun asBytes(): ByteArray

    /**
     * Returns this key as stream.
     */
    fun asStream(): InputStream

    /**
     * Saves this key to a [stream].
     */
    fun saveToStream(stream: OutputStream)
}