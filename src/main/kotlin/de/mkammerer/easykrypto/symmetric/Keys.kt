package de.mkammerer.easykrypto.symmetric

/**
 * Methods for keys.
 */
interface Keys : Readable<Key> {
    /**
     * Creates a randomly generated key, optionally with the given [length in bits][lengthInBits].
     */
    fun createRandom(lengthInBits: Int = -1): Key

    /**
     * Creates a key from the given [password] and the given [salt], optionally with the given [length in bits][lengthInBits].
     */
    fun createFromPassword(password: CharArray, salt: Salt, lengthInBits: Int = -1): Key
}