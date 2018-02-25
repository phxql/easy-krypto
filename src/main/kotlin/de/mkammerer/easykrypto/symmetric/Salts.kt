package de.mkammerer.easykrypto.symmetric

/**
 * Methods for salts.
 */
interface Salts : Reader<Salt> {
    /**
     * Creates a randomly generated salt.
     */
    fun createRandom(): Salt
}