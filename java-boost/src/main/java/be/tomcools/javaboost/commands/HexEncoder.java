package be.tomcools.javaboost.commands;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import static java.lang.Integer.toHexString;

public class HexEncoder {
    private static final Logger LOG = LoggerFactory.getLogger(HexEncoder.class);

    /**
     * Convert a Hexadecimal String to a Little Endian Hexadecimal String
     * Little Endian means the most significan bytes are at the end.
     * Beware however that, in Hexadecimal, bytes are comprised of 2 characters, not one.
     * <p>
     * Example - Decimal 1025
     * Big endian: 0x0401
     * Little endian: 0x0104
     *
     * @param hex Hexadecimal number in String format.
     */
    public static String toLittleEndian(String hex) {
        if (hex.length() % 2 != 0) {
            LOG.error("Can not convert a odd number into a Little Endian Hexadecimal number.");
            return hex;
        } else {
            StringBuilder littleEndianBuilder = new StringBuilder();
            char[] charArray = hex.toCharArray();
            for (int i = 0; i < hex.length() / 2; i++) {
                littleEndianBuilder.insert(0, charArray[i * 2 + 1]);
                littleEndianBuilder.insert(0, charArray[i * 2]);
            }
            return littleEndianBuilder.toString();
        }
    }

    /**
     * Convert an integer into a Hex string of a certain length in bytes
     * One hexadecimal digit represents a nibble (4 bits), which is half of an octet or byte (8 bits).
     * For example, a single byte can have values ranging from 00000000 to 11111111 in binary form,
     * but this may be more conveniently represented as 00 to FF in hexadecimal.
     * <p>
     * Thus, 1 byte is always represented by 2 nibbles, and a nibble is 1 hexadecimal character.
     *
     * @param number      The number to be converted
     * @param sizeInBytes The size in bytes the string needs to represent, prefixing zeroes if needed.
     */
    public static String toHex(int number, int sizeInBytes) {
        int nibbles = sizeInBytes * 2;
        String hexString = toHexString(number);
        if (hexString.length() > nibbles) {
            LOG.error("The provided number in hex is bigger than the size in bytes. This may give weird results.");
        } else {
            hexString = prefixZeroUntilLength(hexString, nibbles);
        }
        return hexString;
    }

    private static String prefixZeroUntilLength(String hex, int length) {
        StringBuilder hexStringBuilder = new StringBuilder(hex);
        while (hexStringBuilder.length() < length) {
            hexStringBuilder.insert(0, "0");
        }
        return hexStringBuilder.toString();
    }
}
