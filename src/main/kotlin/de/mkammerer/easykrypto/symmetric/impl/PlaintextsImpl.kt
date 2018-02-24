package de.mkammerer.easykrypto.symmetric.impl

import de.mkammerer.easykrypto.symmetric.Plaintext
import de.mkammerer.easykrypto.symmetric.Plaintexts
import java.io.InputStream

object PlaintextsImpl : Plaintexts {
    override fun loadFromStream(stream: InputStream): Plaintext = BytesPlaintext(stream.readBytes())

    override fun createFromString(string: String): Plaintext = BytesPlaintext(string.toByteArray())
}