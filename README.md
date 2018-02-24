# Easy Krypto [![Build Status](https://travis-ci.org/phxql/easy-krypto.svg?branch=master)](https://travis-ci.org/phxql/easy-krypto)

This Kotlin library aims to provide a simple way to encrypt and decrypt stuff in a secure manner.

## Usage

```kotlin
// We want to use symmetric crypto (same key for encrypting and decrypting)
val symmetric = EasyKrypto.symmetric()

// Generate a random key
val key = symmetric.createRandomKey()

// Save that key to a file
val keyFile = Paths.get("/path/to/key")
Files.newOutputStream(keyFile).use { stream ->
    key.saveToStream(stream)
}

// We can also load the key from the file again
val loadedKey = Files.newInputStream(keyFile).use { stream ->
    symmetric.loadKeyFromStream(stream)
}

// We create a plaintext from a string we want to encrypt
val plaintext = symmetric.createPlaintextFromString("Hello EasyKrypto")

// And then we encrypt that plaintext with the key to the ciphertext
val ciphertext = symmetric.encrypt(plaintext, key)

// We can now store the ciphertext to a file and no one without the key can read it
val ciphertextFile = Paths.get("/path/to/ciphertext")
Files.newOutputStream(ciphertextFile).use { stream ->
    ciphertext.saveToStream(stream)
}

// We can load the ciphertext from the file again
val loadedCiphertext = Files.newInputStream(ciphertextFile).use { stream ->
    symmetric.loadCiphertextFromStream(stream)
}

// And use the key to decrypt the ciphertext to the plaintext
val decrypted = symmetric.decrypt(loadedCiphertext, loadedKey)

// We can now print the decrypted string. This prints "Hello EasyKrypto"
println(decrypted.asString())
```

## License

Licensed under [LGPLv3](https://www.gnu.org/licenses/lgpl-3.0.en.html).

## Developer guide

See [the developer guide](doc/development.md).