package br.edu.ufcg.virtus.core.util;

import java.awt.RenderingHints;
import java.security.SecureRandom;
import java.util.HashMap;

public class AuthUtil {

    private static final int TOKEN_BYTES_SIZE = 32;

    protected static final HashMap<RenderingHints.Key, Object> RenderingProperties = new HashMap<>();

    static {
        AuthUtil.RenderingProperties.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        AuthUtil.RenderingProperties.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        AuthUtil.RenderingProperties.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }

    private AuthUtil() {
    }

    public static String createRandomToken() {
        // Generate a random token
        final SecureRandom random = new SecureRandom();
        final byte[] tokenBytes = new byte[AuthUtil.TOKEN_BYTES_SIZE];
        random.nextBytes(tokenBytes);

        return ByteEncoding.base62Encode(tokenBytes);
    }
}
