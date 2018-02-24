package de.mkammerer.easykrypto

import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class EasyKryptoTest {
    @Test
    fun `encrypt and decrypt`() {
        // Given a symmetric encryption with a random key
        val symmetric = EasyKrypto.symmetric()
        val key = symmetric.keys.createRandomKey()

        // When we encrypt and decrypt a string
        val plaintext = symmetric.plaintexts.createPlaintextFromString("Hello EasyKrypto")
        val ciphertext = symmetric.encrypt(plaintext, key)
        val decrypted = symmetric.decrypt(ciphertext, key)

        // The decrypted string must be the same as the plaintext
        assertEquals(plaintext.asString(), decrypted.asString())
    }

    @Test
    fun `encrypt and decrypt with 256 bit key`() {
        // Given a symmetric encryption with a random key
        val symmetric = EasyKrypto.symmetric()
        val key = symmetric.keys.createRandomKey(256)

        // When we encrypt and decrypt a string
        val plaintext = symmetric.plaintexts.createPlaintextFromString("Hello EasyKrypto")
        val ciphertext = symmetric.encrypt(plaintext, key)
        val decrypted = symmetric.decrypt(ciphertext, key)

        // The decrypted string must be the same as the plaintext
        assertEquals(plaintext.asString(), decrypted.asString())
    }

    @Test
    fun `encrypt and decrypt with password`() {
        // Given a symmetric encryption with a key from a password
        val symmetric = EasyKrypto.symmetric()
        val salt = symmetric.salts.createRandomSalt()
        val key = symmetric.keys.createKeyFromPassword("secret".toCharArray(), salt)

        // When we encrypt and decrypt a string
        val plaintext = symmetric.plaintexts.createPlaintextFromString("Hello EasyKrypto")
        val ciphertext = symmetric.encrypt(plaintext, key)
        val decrypted = symmetric.decrypt(ciphertext, key)

        // The decrypted string must be the same as the plaintext
        assertEquals(plaintext.asString(), decrypted.asString())
    }

    @Test
    fun `key derivation is deterministic`() {
        // Given a symmetric encryption and a random salt
        val symmetric = EasyKrypto.symmetric()
        val salt = symmetric.salts.createRandomSalt()

        // When we create two keys from the same password
        val key1 = symmetric.keys.createKeyFromPassword("secret".toCharArray(), salt)
        val key2 = symmetric.keys.createKeyFromPassword("secret".toCharArray(), salt)

        // And one from another password
        val key3 = symmetric.keys.createKeyFromPassword("foobar".toCharArray(), salt)

        // The keys from the same passwords must be equal
        assertEquals(key1, key2)

        // But the key from the other password must not be equal
        assertNotEquals(key3, key1)
    }

    @Test
    fun `save and load salt`() {
        // Given a symmetric encryption and a random salt
        val symmetric = EasyKrypto.symmetric()
        val salt = symmetric.salts.createRandomSalt()

        // When we store that salt in a file
        val saltFile = createTempFile()
        Files.newOutputStream(saltFile).use { stream ->
            salt.saveToStream(stream)
        }

        // And load it
        val loadedSalt = Files.newInputStream(saltFile).use { stream ->
            symmetric.salts.loadSaltFromStream(stream)
        }

        // They must be the same
        assertEquals(salt, loadedSalt)
    }

    @Test
    fun `save and load key and ciphertext`() {
        // Given a symmetric encryption with a random key
        val symmetric = EasyKrypto.symmetric()
        val key = symmetric.keys.createRandomKey()

        // We store the key in a file
        val keyFile = createTempFile()
        Files.newOutputStream(keyFile).use { stream ->
            key.saveToStream(stream)
        }

        // And we load the key from the file
        val loadedKey = Files.newInputStream(keyFile).use { stream ->
            symmetric.keys.loadKeyFromStream(stream)
        }

        // Then both keys must be the same
        assertEquals(key, loadedKey)

        // We first create some ciphertext with the original key
        val plaintext = symmetric.plaintexts.createPlaintextFromString("Hello EasyKrypto")
        val ciphertext = symmetric.encrypt(plaintext, key)

        // We store the ciphertext in a file
        val ciphertextFile = createTempFile()
        Files.newOutputStream(ciphertextFile).use { stream ->
            ciphertext.saveToStream(stream)
        }

        // And we load the ciphertext from the file
        val loadedCiphertext = Files.newInputStream(ciphertextFile).use { stream ->
            symmetric.ciphertexts.loadCiphertextFromStream(stream)
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