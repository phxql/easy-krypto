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
import de.mkammerer.easykrypto.symmetric.Salt
import de.mkammerer.easykrypto.symmetric.Salts
import java.io.InputStream
import java.security.SecureRandom

object SaltsImpl : Salts {
    private const val DEFAULT_SALT_SIZE_IN_BITS = 64

    private val secureRandom = SecureRandom()

    override fun createRandom(): Salt {
        val bytes = secureRandom.nextBytes(DEFAULT_SALT_SIZE_IN_BITS / 8)
        return SaltImpl(bytes)
    }

    override fun loadFromStream(stream: InputStream): Salt {
        val bytes = stream.readBytes()
        return SaltImpl(bytes)
    }
}
