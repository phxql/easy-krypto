package de.mkammerer.easykrypto.symmetric

import java.io.InputStream

/**
 * A plaintext (the stuff you want to encrypt).
 */
interface Plaintext {
    /**
     * Returns this plaintext as byte array.
     */
    fun asBytes(): ByteArray

    /**
     * Returns this plaintext as a string.
     */
    fun asString(): String

    /**
     * Returns this plaintext as a stream.
     */
    fun asStream(): InputStream
}