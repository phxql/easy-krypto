package de.mkammerer.easykrypto.symmetric

import java.io.InputStream

/**
 * Methods for salts.
 */
interface Salts {
    /**
     * Creates a randomly generated salt.
     */
    fun createRandom(): Salt

    /**
     * Loads a salt from the given [stream].
     */
    fun loadFromStream(stream: InputStream): Salt
}