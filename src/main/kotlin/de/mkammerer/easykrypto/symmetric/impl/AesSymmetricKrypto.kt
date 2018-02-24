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
        return plaintexts.createPlaintextFromBytes(plaintextBytes)
    }
}