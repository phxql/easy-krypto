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
 * Symmetric crypto.
 */
interface SymmetricKrypto {
    /**
     * Key methods.
     */
    val keys: Keys

    /**
     * Plaintext methods.
     */
    val plaintexts: Plaintexts

    /**
     * Salt methods.
     */
    val salts: Salts

    /**
     * Ciphertext methods.
     */
    val ciphertexts: Ciphertexts

    /**
     * Encrypts the given [plaintext] with the given [key] and returns the ciphertext.
     */
    fun encrypt(plaintext: Plaintext, key: Key): Ciphertext

    /**
     * Decrypts the given [ciphertext] with the given [key] and returns the plaintext.
     */
    fun decrypt(ciphertext: Ciphertext, key: Key): Plaintext
}
