package de.mkammerer.easykrypto.symmetric

import java.io.InputStream

/**
 * Methods for keys.
 */
interface Keys {
    /**
     * Creates a randomly generated key, optionally with the given [length in bits][lengthInBits].
     */
    fun createRandom(lengthInBits: Int = -1): Key

    /**
     * Creates a key from the given [password] and the given [salt], optionally with the given [length in bits][lengthInBits].
     */
    fun createFromPassword(password: CharArray, salt: Salt, lengthInBits: Int = -1): Key

    /**
     * Loads a key from the given [stream].
     */
    fun loadFromStream(stream: InputStream): Key
}