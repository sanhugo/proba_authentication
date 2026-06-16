package ru.proba.authentication.utils;

import lombok.experimental.UtilityClass;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@UtilityClass
public class RSAKeyGen {
    public static void generate() {
        try {
//            System.out.println("🔑 Generating 2048-bit RSA Key Pair...");

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);

            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            String privateBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            String publicBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());

            String envContent = String.format(
                    """
                            RSA_PRIVATE_KEY="%s"
                            RSA_PUBLIC_KEY="%s"
                            """,
                    privateBase64,
                    publicBase64
            );

            Path envPath = Path.of("src/main/resources/.env");
            Files.writeString(envPath, envContent, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

//            System.out.println("✅ Keys successfully generated and saved!");
//            System.out.println("📁 File location: " + envPath.toAbsolutePath());

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
