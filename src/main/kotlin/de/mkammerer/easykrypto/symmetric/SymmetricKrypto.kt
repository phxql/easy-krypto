package de.mkammerer.easykrypto.symmetric

/**
 * Symmetric crypto.
 */
interface SymmetricKrypto {
    /**
     * Key methods.
     */
    val keys: Keys

    /**
     * Plaintext methods.
     */
    val plaintexts: Plaintexts

    /**
     * Salt methods.
     */
    val salts: Salts

    /**
     * Ciphertext methods.
     */
    val ciphertexts: Ciphertexts

    /**
     * Encrypts the given [plaintext] with the given [key] and returns the ciphertext.
     */
    fun encrypt(plaintext: Plaintext, key: Key): Ciphertext

    /**
     * Decrypts the given [ciphertext] with the given [key] and returns the plaintext.
     */
    fun decrypt(ciphertext: Ciphertext, key: Key): Plaintext
}