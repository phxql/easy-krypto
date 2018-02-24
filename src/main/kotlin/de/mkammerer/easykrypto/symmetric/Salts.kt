package de.mkammerer.easykrypto.symmetric

/**
 * Methods for salts.
 */
interface Salts : Readable<Salt> {
    /**
     * Creates a randomly generated salt.
     */
    fun createRandom(): Salt
}