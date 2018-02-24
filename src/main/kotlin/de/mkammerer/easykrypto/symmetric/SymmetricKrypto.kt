package de.mkammerer.easykrypto.symmetric

import java.io.InputStream

/**
 * Symmetric crypto.
 */
interface SymmetricKrypto {
    /**
     * Creates a randomly generated key.
     */
    fun createKey(): Key

    /**
     * Creates a randomly generated key with the given [size in bits][lengthInBits].
     */
    fun createKeyWithSize(lengthInBits: Int): Key

    /**
     * Creates a plaintext from the given [byte array][plaintext].
     */
    fun createPlaintextFromBytes(plaintext: ByteArray): Plaintext

    /**
     * Creates a plaintext from the given [string][plaintext].
     */
    fun createPlaintextFromString(plaintext: String): Plaintext

    /**
     * Loads a key from the given [stream].
     */
    fun loadKeyFromStream(stream: InputStream): Key

    /**
     * Loads a ciphertext from the given [stream].
     */
    fun loadCiphertextFromStream(stream: InputStream): Ciphertext

    /**
     * Encrypts the given [plaintext] with the given [key] and returns the ciphertext.
     */
    fun encrypt(plaintext: Plaintext, key: Key): Ciphertext

    /**
     * Decrypts the given [ciphertext] with the given [key] and returns the plaintext.
     */
    fun decrypt(ciphertext: Ciphertext, key: Key): Plaintext
}