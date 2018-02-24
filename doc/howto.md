# How-To

## Encrypt something with a password

To encrypt or decrypt something with a password, the password needs to be converted to a key. To make that process secure,
a salt is needed. You can generate a random salt and store that somewhere. The salt doesn't need to be private, everyone
can see it. When the same salt and password is given to the key converter function, the same key is returned.

```kotlin
// Use symmetric encryption
val symmetric = EasyKrypto.symmetric()

// Generate random salt
val salt = symmetric.createRandomSalt()

// And store that salt in a file
val saltFile = Paths.get("/path/to/salt")
Files.newOutputStream(saltFile).use { stream ->
    salt.saveToStream(stream)
}

// We can use that salt to generate a key from a password
val password = "secret".toCharArray()
val key = symmetric.createKeyFromPassword(password, salt)

// And now we can use that key to encrypt something
val plaintext = symmetric.createPlaintextFromString("Hello EasyKrypto")
val ciphertext = symmetric.encrypt(plaintext, key)
```

Decryption is the other way around: load the salt from the file and use the same password in the `createKeyFromPassword`
method to get the decryption key.