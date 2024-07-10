/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mysiexperience;

/**
 *
 * @author z004kptc
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class KeyManager {

    // Path to the secret key file
    private static final String KEY_FILE_PATH = "path_to_your_key_file";

    // AES encryption algorithm
    private static final String ALGORITHM = "AES";

    // Generate and save a new secret key
    public static void generateAndSaveKey() throws Exception {
        // Generate a secret key for AES encryption
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(256); // AES key size
        SecretKey secretKey = keyGenerator.generateKey();

        // Save the secret key to a file
        saveKey(secretKey);
    }

    // Save the secret key to a file
    private static void saveKey(Key key) throws IOException {
        // Encode the key to Base64
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());

        // Write the encoded key to the file
        Files.write(Paths.get(KEY_FILE_PATH), encodedKey.getBytes());
    }

    // Load the secret key from a file
    public static SecretKey loadKey() throws IOException {
        // Read the encoded key from the file
        byte[] encodedKey = Files.readAllBytes(Paths.get(KEY_FILE_PATH));

        // Decode the Base64 encoded key
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);

        // Reconstruct the secret key
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
    }
}