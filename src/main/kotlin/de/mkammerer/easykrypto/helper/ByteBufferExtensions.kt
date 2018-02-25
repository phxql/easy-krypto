package de.mkammerer.easykrypto.helper

import java.nio.ByteBuffer

fun ByteBuffer.getBytes(size: Int): ByteArray {
    val bytes = ByteArray(size)
    this.get(bytes)
    return bytes
}