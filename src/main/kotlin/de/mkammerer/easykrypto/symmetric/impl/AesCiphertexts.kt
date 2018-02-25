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

import de.mkammerer.easykrypto.helper.getBytes
import de.mkammerer.easykrypto.symmetric.Ciphertext
import de.mkammerer.easykrypto.symmetric.Ciphertexts
import java.io.InputStream
import java.nio.ByteBuffer

class AesCiphertexts(private val nonceLengthInBits: Int) : Ciphertexts {
    override fun loadFromStream(stream: InputStream): Ciphertext {
        val bytes = stream.readBytes()
        val buffer = ByteBuffer.wrap(bytes)

        val version = buffer.get()
        if (version != AesBytesCiphertext.VERSION) throw IllegalStateException("Expected version ${AesBytesCiphertext.VERSION}, got $version")

        val cipherId = buffer.get()
        if (cipherId != AesBytesCiphertext.AES) throw IllegalStateException("Expected cipher id ${AesBytesCiphertext.AES}, got $cipherId")

        val nonce = buffer.getBytes(nonceLengthInBits / 8)
        val cipherBytes = buffer.getBytes(buffer.remaining())

        return AesBytesCiphertext(nonce, cipherBytes)
    }
}
