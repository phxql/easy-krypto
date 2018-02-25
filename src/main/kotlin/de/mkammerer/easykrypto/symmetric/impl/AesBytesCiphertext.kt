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

import de.mkammerer.easykrypto.helper.ByteBufferInputStream
import de.mkammerer.easykrypto.symmetric.Ciphertext
import java.io.InputStream
import java.nio.ByteBuffer

class AesBytesCiphertext(
        val nonce: ByteArray,
        val ciphertext: ByteArray
) : Ciphertext {
    override fun asStream(): InputStream {
        // version + cipher id + nonce + ciphertext
        val buffer = ByteBuffer.allocate(1 + 1 + nonce.size + ciphertext.size)

        buffer.put(VERSION)
        buffer.put(AES)
        buffer.put(nonce)
        buffer.put(ciphertext)
        buffer.flip()

        return ByteBufferInputStream(buffer)
    }

    companion object {
        const val VERSION = 1.toByte()
        const val AES = 1.toByte()
    }
}
