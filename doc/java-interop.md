# Java interop

Kotlin provides Java interop, so this library is also usable with Java (or any other JVM language).

This is the same example from the readme, translated to Java.

```java
// We want to use symmetric crypto (same key for encrypting and decrypting)
SymmetricKrypto symmetric = EasyKrypto.INSTANCE.symmetric();

// Generate a random key
Key key = symmetric.getKeys().createRandom();

// Save that key to a file
Path keyFile = Paths.get("/path/to/key");
key.saveToFile(keyFile);

// We can also load the key from the file again
Key loadedKey = symmetric.getKeys().loadFromFile(keyFile);

// We create a plaintext from a string we want to encrypt
Plaintext plaintext = symmetric.getPlaintexts().createFromString("Hello EasyKrypto");

// And then we encrypt that plaintext with the key to the ciphertext
Ciphertext ciphertext = symmetric.encrypt(plaintext, key);

// We can now store the ciphertext to a file and no one without the key can read it
Path ciphertextFile = Paths.get("/path/to/ciphertext");
ciphertext.saveToFile(ciphertextFile);

// We can load the ciphertext from the file again
Ciphertext loadedCiphertext = symmetric.getCiphertexts().loadFromFile(ciphertextFile);

// And use the key to decrypt the ciphertext to the plaintext
Plaintext decrypted = symmetric.decrypt(loadedCiphertext, loadedKey);

// We can now print the decrypted string. This prints "Hello EasyKrypto"
System.out.println(decrypted.asString());
```

If you need more help on Java interop, read the [Calling Kotlin from Java](https://kotlinlang.org/docs/reference/java-to-kotlin-interop.html)
guide in the Kotlin documentation or [open an issue asking for help](https://github.com/phxql/easy-krypto/issues).