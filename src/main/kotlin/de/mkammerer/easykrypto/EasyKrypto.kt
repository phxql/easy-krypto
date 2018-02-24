package de.mkammerer.easykrypto

import de.mkammerer.easykrypto.symmetric.SymmetricKrypto
import de.mkammerer.easykrypto.symmetric.impl.AesSymmetricKrypto

/**
 * Easy crypto.
 */
object EasyKrypto {
    /**
     * Creates a new object for symmetric crypto.
     */
    fun symmetric(): SymmetricKrypto = AesSymmetricKrypto
}