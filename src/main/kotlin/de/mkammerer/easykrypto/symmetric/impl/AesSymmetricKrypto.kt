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
import de.mkammerer.easykrypto.symmetric.*
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

object AesSymmetricKrypto : SymmetricKrypto {
    private const val NONCE_LENGTH_IN_BITS = 128
    private const val CIPHER = "AES/GCM/NoPadding"

    private val secureRandom = SecureRandom()

    override val keys: Keys = KeysImpl

    override val plaintexts: Plaintexts = PlaintextsImpl

    override val salts: Salts = SaltsImpl

    override val ciphertexts: Ciphertexts = AesCiphertexts(NONCE_LENGTH_IN_BITS)

    override fun encrypt(plaintext: Plaintext, key: Key): Ciphertext {
        val cipher = Cipher.getInstance(CIPHER)
        val keySpec = SecretKeySpec(key.asBytes(), "AES")

        // Generate random nonce
        val nonce = secureRandom.nextBytes(NONCE_LENGTH_IN_BITS / 8)
        val gcmSpec = GCMParameterSpec(NONCE_LENGTH_IN_BITS, nonce)

        // Put the cipher in encrypt mode
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec)

        val cipherBytes = cipher.doFinal(plaintext.asBytes())
        return AesBytesCiphertext(nonce, cipherBytes)
    }

    override fun decrypt(ciphertext: Ciphertext, key: Key): Plaintext {
        if (ciphertext !is AesBytesCiphertext) throw IllegalArgumentException("ciphertext has wrong class")

        val cipher = Cipher.getInstance(CIPHER)
        val keySpec = SecretKeySpec(key.asBytes(), "AES")

        val gcmSpec = GCMParameterSpec(NONCE_LENGTH_IN_BITS, ciphertext.nonce)

        // Put the cipher in decrypt mode
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec)

        val plaintextBytes = cipher.doFinal(ciphertext.ciphertext)
        return plaintexts.createFromBytes(plaintextBytes)
    }
}
