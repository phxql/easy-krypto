package de.mkammerer.easykrypto.symmetric

import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

/**
 * Stuff that is readable.
 */
interface Readable<out T> {
    /**
     * Loads a new object from the given [stream].
     */
    fun loadFromStream(stream: InputStream): T

    /**
     * Loads a new object from the given [file].
     */
    fun loadFromFile(file: Path): T {
        return Files.newInputStream(file).use { stream ->
            loadFromStream(stream)
        }
    }

    /**
     * Loads a new object from the given [byte array][bytes].
     */
    fun createFromBytes(bytes: ByteArray): T = loadFromStream(bytes.inputStream())

    /**
     * Loads a new object from the given [string].
     */
    fun createFromString(string: String): T = createFromBytes(Base64.getDecoder().decode(string))
}