package de.mkammerer.easykrypto

import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class EasyKryptoTest {
    @Test
    fun `encrypt and decrypt`() {
        // Given a symmetric encryption with a random key
        val symmetric = EasyKrypto.symmetric()
        val key = symmetric.createRandomKey()

        // When we encrypt and decrypt a string
        val plaintext = symmetric.createPlaintextFromString("Hello EasyKrypto")
        val ciphertext = symmetric.encrypt(plaintext, key)
        val decrypted = symmetric.decrypt(ciphertext, key)

        // The decrypted string must be the same as the plaintext
        assertEquals(plaintext.asString(), decrypted.asString())
    }

    @Test
    fun `encrypt and decrypt with 256 bit key`() {
        // Given a symmetric encryption with a random key
        val symmetric = EasyKrypto.symmetric()
        val key = symmetric.createRandomKey(256)

        // When we encrypt and decrypt a string
        val plaintext = symmetric.createPlaintextFromString("Hello EasyKrypto")
        val ciphertext = symmetric.encrypt(plaintext, key)
        val decrypted = symmetric.decrypt(ciphertext, key)

        // The decrypted string must be the same as the plaintext
        assertEquals(plaintext.asString(), decrypted.asString())
    }

    @Test
    fun `save and load key and ciphertext`() {
        // Given a symmetric encryption with a random key
        val symmetric = EasyKrypto.symmetric()
        val key = symmetric.createRandomKey()

        // We store the key in a file
        val keyFile = createTempFile()
        Files.newOutputStream(keyFile).use { stream ->
            key.saveToStream(stream)
        }

        // And we load the key from the file
        val loadedKey = Files.newInputStream(keyFile).use { stream ->
            symmetric.loadKeyFromStream(stream)
        }

        // Then both keys must be the same
        assertEquals(key, loadedKey)

        // We first create some ciphertext with the original key
        val plaintext = symmetric.createPlaintextFromString("Hello EasyKrypto")
        val ciphertext = symmetric.encrypt(plaintext, key)

        // We store the ciphertext in a file
        val ciphertextFile = createTempFile()
        Files.newOutputStream(ciphertextFile).use { stream ->
            ciphertext.saveToStream(stream)
        }

        // And we load the ciphertext from the file
        val loadedCiphertext = Files.newInputStream(ciphertextFile).use { stream ->
            symmetric.loadCiphertextFromStream(stream)
        }

        // When we decrypt the ciphertext with the loaded key
        val decrypted = symmetric.decrypt(loadedCiphertext, loadedKey)

        // The decrypted string must be the same as the plaintext
        assertEquals(plaintext.asString(), decrypted.asString())
    }

    private fun createTempFile(): Path {
        return Files.createTempFile("easy-krypto", ".tmp").apply {
            // Delete the file on end of the test runs
            toFile().deleteOnExit()
        }
    }
}