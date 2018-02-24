package de.mkammerer.easykrypto

import de.mkammerer.easykrypto.symmetric.SymmetricKrypto
import de.mkammerer.easykrypto.symmetric.impl.AesSymmetricKrypto

object EasyKrypto {
    fun symmetric(): SymmetricKrypto = AesSymmetricKrypto
}