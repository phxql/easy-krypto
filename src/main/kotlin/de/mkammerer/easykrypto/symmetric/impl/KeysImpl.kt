package de.mkammerer.easykrypto.symmetric.impl

import de.mkammerer.easykrypto.helper.nextBytes
import de.mkammerer.easykrypto.symmetric.Key
import de.mkammerer.easykrypto.symmetric.Keys
import de.mkammerer.easykrypto.symmetric.Salt
import java.io.InputStream
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object KeysImpl : Keys {
    private const val DEFAULT_KEY_SIZE_IN_BITS = 128
    private const val PBE_ITERATION_COUNT = 20000

    private val secureRandom = SecureRandom()

    override fun createRandom(lengthInBits: Int): Key {
        val length = if (lengthInBits == -1) DEFAULT_KEY_SIZE_IN_BITS else lengthInBits

        val bytes = secureRandom.nextBytes(length / 8)
        return KeyImpl(bytes)
    }

    override fun createFromPassword(password: CharArray, salt: Salt, lengthInBits: Int): Key {
        val length = if (lengthInBits == -1) DEFAULT_KEY_SIZE_IN_BITS else lengthInBits

        val spec = PBEKeySpec(password, salt.asBytes(), PBE_ITERATION_COUNT, length)
        val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val keyBytes = skf.generateSecret(spec).encoded

        return KeyImpl(keyBytes)
    }

    override fun loadFromStream(stream: InputStream): Key {
        val bytes = stream.readBytes()
        return KeyImpl(bytes)
    }
}