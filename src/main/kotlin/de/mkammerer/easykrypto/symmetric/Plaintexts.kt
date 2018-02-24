package de.mkammerer.easykrypto.symmetric

/**
 * Methods for plaintexts.
 */
interface Plaintexts {
    /**
     * Creates a plaintext from the given [byte array][plaintext].
     */
    fun createPlaintextFromBytes(plaintext: ByteArray): Plaintext

    /**
     * Creates a plaintext from the given [string][plaintext].
     */
    fun createPlaintextFromString(plaintext: String): Plaintext
}