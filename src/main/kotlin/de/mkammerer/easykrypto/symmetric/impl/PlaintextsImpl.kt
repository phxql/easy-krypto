package de.mkammerer.easykrypto.symmetric.impl

import de.mkammerer.easykrypto.symmetric.Plaintext
import de.mkammerer.easykrypto.symmetric.Plaintexts

object PlaintextsImpl : Plaintexts {
    override fun createPlaintextFromBytes(plaintext: ByteArray): Plaintext = BytesPlaintext(plaintext)

    override fun createPlaintextFromString(plaintext: String): Plaintext = BytesPlaintext(plaintext.toByteArray())
}