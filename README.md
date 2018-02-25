# Easy Krypto [![Build Status](https://travis-ci.org/phxql/easy-krypto.svg?branch=master)](https://travis-ci.org/phxql/easy-krypto)

This Kotlin library aims to provide a simple way to encrypt and decrypt stuff in a secure manner.

Using Java, Scala, Groovy, etc.? No problem, this library can also be used with any JVM language. See [Java interop](doc/java-interop.md) for details.

## Maven
```xml
<dependency>
    <groupId>de.mkammerer.easy-krypto</groupId>
    <artifactId>easy-krypto</artifactId>
    <version>1.0</version>
</dependency>
```

## Usage

```kotlin
// We want to use symmetric crypto (same key for encrypting and decrypting)
val symmetric = EasyKrypto.symmetric()

// Generate a random key
val key = symmetric.keys.createRandom()

// Save that key to a file
val keyFile = Paths.get("/path/to/key")
key.saveToFile(keyFile)

// We can also load the key from the file again
val loadedKey = symmetric.keys.loadFromFile(keyFile)

// We create a plaintext from a string we want to encrypt
val plaintext = symmetric.plaintexts.createFromString("Hello EasyKrypto")

// And then we encrypt that plaintext with the key to the ciphertext
val ciphertext = symmetric.encrypt(plaintext, key)

// We can now store the ciphertext to a file and no one without the key can read it
val ciphertextFile = Paths.get("/path/to/ciphertext")
ciphertext.saveToFile(ciphertextFile)

// We can load the ciphertext from the file again
val loadedCiphertext = symmetric.ciphertexts.loadFromFile(ciphertextFile)

// And use the key to decrypt the ciphertext to the plaintext
val decrypted = symmetric.decrypt(loadedCiphertext, loadedKey)

// We can now print the decrypted string. This prints "Hello EasyKrypto"
println(decrypted.asString())
```

For more examples, [see the How-To document](doc/howto.md).

## License

Licensed under [LGPLv3](https://www.gnu.org/licenses/lgpl-3.0.en.html).

## Developer guide

See [the developer guide](doc/development.md).