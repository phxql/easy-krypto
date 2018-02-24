package de.mkammerer.easykrypto.helper

import java.security.SecureRandom

fun SecureRandom.nextBytes(size: Int): ByteArray {
    val bytes = ByteArray(size)
    this.nextBytes(bytes)
    return bytes
}