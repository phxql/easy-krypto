/*-
 * #%L
 * Easy Krypto
 * %%
 * Copyright (C) 2018 Moritz Kammerer
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
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

    override fun createRandom(): Key = createRandom(DEFAULT_KEY_SIZE_IN_BITS)

    override fun createRandom(lengthInBits: Int): Key {
        val bytes = secureRandom.nextBytes(lengthInBits / 8)
        return KeyImpl(bytes)
    }

    override fun createFromPassword(password: CharArray, salt: Salt): Key = createFromPassword(password, salt, DEFAULT_KEY_SIZE_IN_BITS)

    override fun createFromPassword(password: CharArray, salt: Salt, lengthInBits: Int): Key {
        val spec = PBEKeySpec(password, salt.asBytes(), PBE_ITERATION_COUNT, lengthInBits)
        val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val keyBytes = skf.generateSecret(spec).encoded

        return KeyImpl(keyBytes)
    }

    override fun loadFromStream(stream: InputStream): Key {
        val bytes = stream.readBytes()
        return KeyImpl(bytes)
    }
}
