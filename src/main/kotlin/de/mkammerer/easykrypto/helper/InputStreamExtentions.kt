package de.mkammerer.easykrypto.helper

import java.io.InputStream

fun InputStream.readNBytes(n: Int): ByteArray {
    val result = ByteArray(n)

    var read = 0
    while (read < n) {
        val currentRead = this.read(result, read, n - read)
        if (currentRead == -1) throw IllegalStateException("Stream is EOF")
        read += currentRead
    }

    return result
}