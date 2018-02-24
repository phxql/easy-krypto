package de.mkammerer.easykrypto.symmetric

import java.io.InputStream

/**
 * Methods for ciphertexts.
 */
interface Ciphertexts {
    /**
     * Loads a ciphertext from the given [stream].
     */
    fun loadCiphertextFromStream(stream: InputStream): Ciphertext
}