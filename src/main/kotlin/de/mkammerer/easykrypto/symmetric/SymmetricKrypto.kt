package de.mkammerer.easykrypto.symmetric

import java.io.InputStream

interface SymmetricKrypto {
    fun createKey(): Key

    fun createKeyWithSize(lengthInBits: Int): Key

    fun createPlaintextFromBytes(plaintext: ByteArray): Plaintext

    fun createPlaintextFromString(plaintext: String): Plaintext

    fun loadKeyFromStream(stream: InputStream): Key

    fun loadCiphertextFromStream(stream: InputStream): Ciphertext

    fun encrypt(plaintext: Plaintext, key: Key): Ciphertext

    fun decrypt(ciphertext: Ciphertext, key: Key): Plaintext
}