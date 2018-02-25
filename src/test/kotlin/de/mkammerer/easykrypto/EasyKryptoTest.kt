/*-
 * #%L
 * Easy Krypto
 * %%
 * Copyright (C) 2018 Moritz Kammerer
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package de.mkammerer.easykrypto

import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class EasyKryptoTest {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Test
    fun `encrypt and decrypt`() {
        // Given a symmetric encryption with a random key
        val symmetric = EasyKrypto.symmetric()
        val key = symmetric.keys.createRandom()

        // When we encrypt and decrypt a string
        val plaintext = symmetric.plaintexts.createFromString("Hello EasyKrypto")
        val ciphertext = symmetric.encrypt(plaintext, key)
        val decrypted = symmetric.decrypt(ciphertext, key)

        // The decrypted string must be the same as the plaintext
        assertEquals(plaintext.asString(), decrypted.asString())
    }

    @Test
    fun `encrypt and decrypt with string serialization`() {
        // Given a symmetric encryption with a random key
        val symmetric = EasyKrypto.symmetric()
        val key = symmetric.keys.createRandom()

        // Serialize the key to string and back
        val serializedKey = key.asString()
        logger.debug("Serialized key: {}", serializedKey)
        val loadedKey = symmetric.keys.createFromString(serializedKey)

        // When we encrypt and decrypt a string
        val plaintext = symmetric.plaintexts.createFromString("Hello EasyKrypto")
        val ciphertext = symmetric.encrypt(plaintext, key)

        // Serialize the ciphertext to string and back
        val serializedCiphertext = ciphertext.asString()
        logger.debug("Serialized ciphertext: {}", serializedCiphertext)
        val loadedCiphertext = symmetric.ciphertexts.createFromString(serializedCiphertext)

        val decrypted = symmetric.decrypt(loadedCiphertext, loadedKey)

        // The decrypted string must be the same as the plaintext
        assertEquals(plaintext.asString(), decrypted.asString())
    }

    @Test
    fun `encrypt and decrypt with 256 bit key`() {
        // Given a symmetric encryption with a random key
        val symmetric = EasyKrypto.symmetric()
        val key = symmetric.keys.createRandom(256)

        // When we encrypt and decrypt a string
        val plaintext = symmetric.plaintexts.createFromString("Hello EasyKrypto")
        val ciphertext = symmetric.encrypt(plaintext, key)
        val decrypted = symmetric.decrypt(ciphertext, key)

        // The decrypted string must be the same as the plaintext
        assertEquals(plaintext.asString(), decrypted.asString())
    }

    @Test
    fun `encrypt and decrypt with password`() {
        // Given a symmetric encryption with a key from a password
        val symmetric = EasyKrypto.symmetric()
        val salt = symmetric.salts.createRandom()
        val key = symmetric.keys.createFromPassword("secret".toCharArray(), salt)

        // When we encrypt and decrypt a string
        val plaintext = symmetric.plaintexts.createFromString("Hello EasyKrypto")
        val ciphertext = symmetric.encrypt(plaintext, key)
        val decrypted = symmetric.decrypt(ciphertext, key)

        // The decrypted string must be the same as the plaintext
        assertEquals(plaintext.asString(), decrypted.asString())
    }

    @Test
    fun `key derivation is deterministic`() {
        // Given a symmetric encryption and a random salt
        val symmetric = EasyKrypto.symmetric()
        val salt = symmetric.salts.createRandom()

        // When we create two keys from the same password
        val key1 = symmetric.keys.createFromPassword("secret".toCharArray(), salt)
        val key2 = symmetric.keys.createFromPassword("secret".toCharArray(), salt)

        // And one from another password
        val key3 = symmetric.keys.createFromPassword("foobar".toCharArray(), salt)

        // The keys from the same passwords must be equal
        assertEquals(key1, key2)

        // But the key from the other password must not be equal
        assertNotEquals(key3, key1)
    }

    @Test
    fun `save and load salt`() {
        // Given a symmetric encryption and a random salt
        val symmetric = EasyKrypto.symmetric()
        val salt = symmetric.salts.createRandom()

        // When we store that salt in a file
        val saltFile = createTempFile()
        salt.saveToFile(saltFile)

        // And load it
        val loadedSalt = symmetric.salts.loadFromFile(saltFile)

        // They must be the same
        assertEquals(salt, loadedSalt)
    }

    @Test
    fun `serialize and deserialize salt`() {
        // Given a symmetric encryption and a random salt
        val symmetric = EasyKrypto.symmetric()
        val salt = symmetric.salts.createRandom()

        val serializedSalt = salt.asString()
        logger.info("Serialized salt: {}", serializedSalt)

        // And load it
        val loadedSalt = symmetric.salts.createFromString(serializedSalt)

        // They must be the same
        assertEquals(salt, loadedSalt)
    }

    @Test
    fun `save and load key and ciphertext`() {
        // Given a symmetric encryption with a random key
        val symmetric = EasyKrypto.symmetric()
        val key = symmetric.keys.createRandom()

        // We store the key in a file
        val keyFile = createTempFile()
        key.saveToFile(keyFile)

        // And we load the key from the file
        val loadedKey = symmetric.keys.loadFromFile(keyFile)

        // Then both keys must be the same
        assertEquals(key, loadedKey)

        // We first create some ciphertext with the original key
        val plaintext = symmetric.plaintexts.createFromString("Hello EasyKrypto")
        val ciphertext = symmetric.encrypt(plaintext, key)

        // We store the ciphertext in a file
        val ciphertextFile = createTempFile()
        ciphertext.saveToFile(ciphertextFile)

        // And we load the ciphertext from the file
        val loadedCiphertext = symmetric.ciphertexts.loadFromFile(ciphertextFile)

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
