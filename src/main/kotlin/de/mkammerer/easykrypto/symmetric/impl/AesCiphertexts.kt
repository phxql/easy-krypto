package de.mkammerer.easykrypto.symmetric.impl

import de.mkammerer.easykrypto.helper.readNBytes
import de.mkammerer.easykrypto.symmetric.Ciphertext
import de.mkammerer.easykrypto.symmetric.Ciphertexts
import java.io.InputStream

class AesCiphertexts(private val nonceLengthInBits: Int) : Ciphertexts {
    override fun loadCiphertextFromStream(stream: InputStream): Ciphertext {
        val nonce = stream.readNBytes(nonceLengthInBits / 8)
        val cipherBytes = stream.readBytes()

        return AesBytesCiphertext(nonce, cipherBytes)
    }
}