package de.mkammerer.easykrypto.symmetric

import java.io.InputStream
import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

interface Writable {
    /**
     * Returns the contents of this object as a stream.
     */
    fun asStream(): InputStream

    /**
     * Returns the contents of this object as a byte array.
     */
    fun asBytes(): ByteArray = asStream().readBytes()

    /**
     * Returns the contents of this object as a string.
     */
    fun asString(): String = Base64.getEncoder().encodeToString(asBytes())

    /**
     * Saves the contents of this object to a [stream].
     */
    fun saveToStream(stream: OutputStream) {
        asStream().copyTo(stream)
    }

    /**
     * Saves the contents of this object to a [file].
     */
    fun saveToFile(file: Path) {
        Files.newOutputStream(file).use {
            saveToStream(it)
        }
    }
}