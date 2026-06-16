package ru.proba.authentication.utils;

import lombok.experimental.UtilityClass;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.util.Base64;

@UtilityClass
public class HMACKeyGen {
    public static void generate() {
        try {
//            System.out.println("🔑 Generating secure HMAC-SHA256 secret (32 bytes / 256 bits)...");

            SecureRandom secureRandom = new SecureRandom();
            byte[] keyBytes = new byte[32]; // Use 64 for HMAC-SHA512
            secureRandom.nextBytes(keyBytes);

            String base64Secret = Base64.getEncoder().encodeToString(keyBytes);

            String envContent = String.format(
                    "HMAC_KEY=\"%s\"\n",
                    base64Secret
            );

            Path envPath = Path.of("docker/.env");
            Files.writeString(envPath, envContent, StandardOpenOption.WRITE, StandardOpenOption.APPEND);

//            System.out.println("✅ HMAC secret successfully generated and saved!");
//            System.out.println("📁 File location: " + envPath.toAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
