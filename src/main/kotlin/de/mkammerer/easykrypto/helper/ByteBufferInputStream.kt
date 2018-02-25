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
package de.mkammerer.easykrypto.helper

import java.io.InputStream
import java.nio.ByteBuffer

/**
 * Wraps a [ByteBuffer] into an [InputStream].
 */
class ByteBufferInputStream(private val buffer: ByteBuffer) : InputStream() {
    override fun read(): Int {
        if (!buffer.hasRemaining()) return -1

        return buffer.get().toInt().and(0xFF)
    }

    override fun read(b: ByteArray, off: Int, len: Int): Int {
        if (!buffer.hasRemaining()) return -1

        val length = Math.min(len, buffer.remaining())
        buffer.get(b, off, length)
        return length
    }
}
