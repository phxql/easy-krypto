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
package de.mkammerer.easykrypto.symmetric

import java.io.InputStream
import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

/**
 * Stuff that is writable.
 */
interface Writable {
    /**
     * Returns the contents of this object as a stream.
     */
    fun asStream(): InputStream

    /**
     * Returns the contents of this object as a byte array.
     */
    fun asBytes(): ByteArray = asStream().readBytes()

    /**
     * Returns the contents of this object as a string.
     */
    fun asString(): String = Base64.getEncoder().encodeToString(asBytes())

    /**
     * Saves the contents of this object to a [stream].
     */
    fun saveToStream(stream: OutputStream) {
        asStream().copyTo(stream)
    }

    /**
     * Saves the contents of this object to a [file].
     */
    fun saveToFile(file: Path) {
        Files.newOutputStream(file).use { stream ->
            saveToStream(stream)
        }
    }
}
