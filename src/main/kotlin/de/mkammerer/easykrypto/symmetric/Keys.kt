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

/**
 * Methods for keys.
 */
interface Keys : Reader<Key> {
    /**
     * Creates a randomly generated key with the default key length.
     */
    fun createRandom(): Key

    /**
     * Creates a randomly generated key with the given [length in bits][lengthInBits].
     */
    fun createRandom(lengthInBits: Int): Key

    /**
     * Creates a key from the given [password] and the given [salt] with the default key length.
     */
    fun createFromPassword(password: CharArray, salt: Salt): Key

    /**
     * Creates a key from the given [password] and the given [salt] with the given [length in bits][lengthInBits].
     */
    fun createFromPassword(password: CharArray, salt: Salt, lengthInBits: Int): Key
}
