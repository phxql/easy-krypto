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
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

/**
 * Stuff that is readable.
 */
interface Reader<out T> {
    /**
     * Loads a new object from the given [stream].
     */
    fun loadFromStream(stream: InputStream): T

    /**
     * Loads a new object from the given [file].
     */
    fun loadFromFile(file: Path): T {
        return Files.newInputStream(file).use { stream ->
            loadFromStream(stream)
        }
    }

    /**
     * Loads a new object from the given [byte array][bytes].
     */
    fun createFromBytes(bytes: ByteArray): T = loadFromStream(bytes.inputStream())

    /**
     * Loads a new object from the given [string].
     */
    fun createFromString(string: String): T = createFromBytes(Base64.getDecoder().decode(string))
}
