package de.mkammerer.easykrypto.symmetric

import java.io.InputStream

/**
 * Methods for salts.
 */
interface Salts {
    /**
     * Creates a randomly generated salt.
     */
    fun createRandomSalt(): Salt

    /**
     * Loads a salt from the given [stream].
     */
    fun loadSaltFromStream(stream: InputStream): Salt
}