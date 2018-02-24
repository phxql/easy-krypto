package de.mkammerer.easykrypto.symmetric.impl

import de.mkammerer.easykrypto.helper.nextBytes
import de.mkammerer.easykrypto.symmetric.Salt
import de.mkammerer.easykrypto.symmetric.Salts
import java.io.InputStream
import java.security.SecureRandom

object SaltsImpl : Salts {
    private const val DEFAULT_SALT_SIZE_IN_BITS = 64

    private val secureRandom = SecureRandom()

    override fun createRandomSalt(): Salt {
        val bytes = secureRandom.nextBytes(DEFAULT_SALT_SIZE_IN_BITS / 8)
        return SaltImpl(bytes)
    }

    override fun loadSaltFromStream(stream: InputStream): Salt {
        val bytes = stream.readBytes()
        return SaltImpl(bytes)
    }
}