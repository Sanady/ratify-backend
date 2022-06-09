package com.ratify.backend.configs;

import com.ratify.backend.models.Address;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

public class UtilityFunctions {
    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    private UtilityFunctions() {

    }

    public static String normalizedName(String name) {
        return name.toUpperCase().replaceAll("\\s","");
    }

    public static void normalizedAddress(Address address) {
        address.setNormalizedAddress(address.getStreet().toUpperCase().replaceAll("\\s", "") +
                address.getNumber().toUpperCase().replaceAll("\\s", "") +
                address.getCity().toUpperCase().replaceAll("\\s", ""));
    }

    public static String generateSequenceOfChar() {
        byte[] randomBytes = new byte[48];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public static boolean isAtLeastFiveMinutesAgo(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        Instant twentyMinutesAgo = Instant.now().minus(Duration.ofMinutes(5));

        return instant.isBefore(twentyMinutesAgo);
    }
}
