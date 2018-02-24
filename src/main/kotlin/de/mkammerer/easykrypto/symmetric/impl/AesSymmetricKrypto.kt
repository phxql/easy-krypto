package de.mkammerer.easykrypto.symmetric.impl

import de.mkammerer.easykrypto.helper.readNBytes
import de.mkammerer.easykrypto.symmetric.*
import java.io.InputStream
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object AesSymmetricKrypto : SymmetricKrypto {
    private const val DEFAULT_KEY_SIZE_IN_BITS = 128
    private const val DEFAULT_SALT_SIZE_IN_BITS = 64
    private const val NONCE_LENGTH_IN_BITS = 128
    private const val PBE_ITERATION_COUNT = 20000

    private val secureRandom = SecureRandom()

    override fun createPlaintextFromBytes(plaintext: ByteArray): Plaintext = BytesPlaintext(plaintext)

    override fun createPlaintextFromString(plaintext: String): Plaintext = BytesPlaintext(plaintext.toByteArray())

    override fun createRandomKey(lengthInBits: Int): Key {
        val length = if (lengthInBits == -1) DEFAULT_KEY_SIZE_IN_BITS else lengthInBits

        val bytes = randomBytes(length / 8)
        return KeyImpl(bytes)
    }

    override fun createRandomSalt(): Salt {
        val bytes = randomBytes(DEFAULT_SALT_SIZE_IN_BITS / 8)
        return SaltImpl(bytes)
    }

    override fun createKeyFromPassword(password: CharArray, salt: Salt, lengthInBits: Int): Key {
        val length = if (lengthInBits == -1) DEFAULT_KEY_SIZE_IN_BITS else lengthInBits

        val spec = PBEKeySpec(password, salt.asBytes(), PBE_ITERATION_COUNT, length)
        val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val keyBytes = skf.generateSecret(spec).encoded

        return KeyImpl(keyBytes)
    }

    override fun loadKeyFromStream(stream: InputStream): Key {
        val bytes = stream.readBytes()
        return KeyImpl(bytes)
    }

    override fun loadCiphertextFromStream(stream: InputStream): Ciphertext {
        val nonce = stream.readNBytes(NONCE_LENGTH_IN_BITS / 8)
        val cipherBytes = stream.readBytes()

        return AesBytesCiphertext(nonce, cipherBytes)
    }

    override fun loadSaltFromStream(stream: InputStream): Salt {
        val bytes = stream.readBytes()
        return SaltImpl(bytes)
    }

    override fun encrypt(plaintext: Plaintext, key: Key): Ciphertext {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val keySpec = SecretKeySpec(key.asBytes(), "AES")

        // Generate random nonce
        val nonce = randomBytes(NONCE_LENGTH_IN_BITS / 8)
        val gcmSpec = GCMParameterSpec(NONCE_LENGTH_IN_BITS, nonce)

        // Put the cipher in encrypt mode
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec)

        val cipherBytes = cipher.doFinal(plaintext.asBytes())
        return AesBytesCiphertext(nonce, cipherBytes)
    }

    override fun decrypt(ciphertext: Ciphertext, key: Key): Plaintext {
        if (ciphertext !is AesBytesCiphertext) throw IllegalArgumentException("ciphertext has wrong class")

        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val keySpec = SecretKeySpec(key.asBytes(), "AES")

        val gcmSpec = GCMParameterSpec(NONCE_LENGTH_IN_BITS, ciphertext.nonce)

        // Put the cipher in decrypt mode
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec)

        val plaintextBytes = cipher.doFinal(ciphertext.ciphertext)
        return createPlaintextFromBytes(plaintextBytes)
    }

    private fun randomBytes(size: Int): ByteArray {
        val bytes = ByteArray(size)
        secureRandom.nextBytes(bytes)
        return bytes
    }
}