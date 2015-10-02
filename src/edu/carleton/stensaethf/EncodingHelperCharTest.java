package edu.carleton.stensaethf;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Frederik Stensaeth (stensaethf) and Alex Griese (griesea).
 */
public class EncodingHelperCharTest {
    private EncodingHelperChar enc;


    @Test
    /* Test to see if the getCodePoint method to see if the input (41) matches
     the retrieved codepoint, 41
     */
    public void testGetCodePointShouldReturn41() throws Exception {
        enc = new EncodingHelperChar(41);
        assertEquals(41, enc.getCodePoint());
    }

    @Test
    /* Test to see if the setCodePoint method to see changes the initial
    input from 45 to 41
     */
    public void testSetCodePointShouldReturn41() throws Exception {
        enc = new EncodingHelperChar(45);
        enc.setCodePoint(41);
        assertEquals(41, enc.getCodePoint());

    }

    @Test
     /* Test to see if the toUTF8Bytes method returns the appropriate byte
      array, [41]
     */
    public void testToUtf8BytesShouldReturnArrayWith41() throws Exception {
        enc = new EncodingHelperChar(41);
        byte[] enc_byte_array = enc.toUtf8Bytes();
        assertEquals(41, enc_byte_array[0]);
    }

    @Test
     /* Test to see if the toUTF8Bytes method returns the appropriate byte
      array, [0xE2,0x90,0xA3]
     */
    public void testToUtf8BytesShouldReturnArrayWithE290A3() throws Exception {
        enc = new EncodingHelperChar(9251);
        byte[] enc_byte_array = enc.toUtf8Bytes();
        byte[] enc_byte_array_correct = new byte[3];
        enc_byte_array_correct[0] = (byte)0xE2;
        enc_byte_array_correct[1] = (byte)0x90;
        enc_byte_array_correct[2] = (byte)0xA3;
        assertArrayEquals(enc_byte_array_correct, enc_byte_array);
    }

    @Test
    /* Test to see if the toCodePointString method returns the appropriate
    codepoint string, U+0041
     */
    public void testToCodePointStringShouldReturnStringU0041() throws
    Exception {
        enc = new EncodingHelperChar(65);
        assertEquals("U+0041", enc.toCodePointString());
    }

    @Test
    /* Test to see if the toCodePointString method returns the appropriate
    codepoint string, U+2423
     */
    public void testToCodePointStringShouldReturnStringU0977() throws
    Exception {
        enc = new EncodingHelperChar(2423);
        assertEquals("U+0977", enc.toCodePointString());
    }

    @Test
    /* Test to see if the toUtf8String method returns the appropriate
    Utf-8 string, 41
     */
    public void testToUtf8StringShouldReturnString41() throws Exception {
        enc = new EncodingHelperChar(65);
        assertEquals("\\x41", enc.toUtf8String());
    }

    @Test
    /* Test to see if the toUtf8String method returns the appropriate
    UTF-8 string, E290A3
     */
    public void testToUtf8StringShouldReturnStringE290A3() throws Exception {
        enc = new EncodingHelperChar(9251);
        assertEquals("\\xE2\\x90\\xA3", enc.toUtf8String());
    }

    @Test
    /* Test to see if the getCharacterName method returns the appropriate
    letter name, LATIN CAPITAL LETTER A
     */
    public void testGetCharacterNameShouldReturnStringLatinCapitalLetterA()
            throws Exception {
        enc = new EncodingHelperChar(65);
        assertEquals("LATIN CAPITAL LETTER A", enc.getCharacterName());
    }

    @Test
    /* Test to see if the character constructor runs, and assert the letter
    A's codepoint is 41
     */
    public void testCharConstructorShouldReturn41AsCodePoint() throws
            Exception {
        char A = 'A';
        enc = new EncodingHelperChar(A);
        assertEquals(65, enc.getCodePoint());
    }

    @Test
    /* Asserts the char constructor runs and toUTF8Bytes returns a byte array,
    [41]
     */
    public void testCharConstructorShouldReturnByteArrayWith65() throws
            Exception {
        char A = 'A';
        enc = new EncodingHelperChar(A);
        byte[] A_byte_array = new byte[1];
        A_byte_array[0] = 65;
        assertArrayEquals(A_byte_array, enc.toUtf8Bytes());
    }

    @Test
    /*
    Test to see if the char constructor runs, and tests to see if the toCodePointString method returns,
    U+0041
     */
    public void testCharConstructorShouldReturnCodePointString0041() throws
            Exception {
        char A = 'A';
        enc = new EncodingHelperChar(A);
        assertEquals("U+0041", enc.toCodePointString());
    }

    @Test
    /*
    tests to see if char constructor runs and that the toUTF8String method
    returns, 41
     */
    public void testCharConstructorShouldReturnByteString41() throws Exception {
        char A = 'A';
        enc = new EncodingHelperChar(A);
        assertEquals("\\x41", enc.toUtf8String());
    }

    @Test
     /*
    tests to see if char constructor runs and that the getCharacterName method
    returns, LATIN CAPITAL LETTER A
     */
    public void testCharConstructorShouldReturnLatinCapitalLetterA() throws
            Exception {
        char A = 'A';
        enc = new EncodingHelperChar(A);
        assertEquals("LATIN CAPITAL LETTER A", enc.getCharacterName());
    }

    @Test
     /*
    tests to see if the int constructor accepts ints not in unicode.
     */
    public void testIntConstructorWithInvalidNumberShouldThrowException() throws
            Exception {
        try {
            enc = new EncodingHelperChar(1114112);
            fail("My method didn't throw when I expected it to");
        } catch(IllegalArgumentException expectedException) {
            // No action needed.
        }
    }

    @Test
    /*
    tests to see if the int constructor accepts ints not in unicode.
     */
    public void testIntConstructorWithNegativeNumberShouldThrowException()
            throws Exception {
        try {
            enc = new EncodingHelperChar(-1);
            fail("My method didn't throw when I expected it to");
        } catch(IllegalArgumentException expectedException) {
            // No action needed.
        }
    }


    @Test
    /*
    tests to see if the constructor can construct null, should return false
     */
    public void testNullConstructorShouldThrowException() throws Exception {
        try {
            enc = new EncodingHelperChar(null);
            fail("My method didn't throw when I expected it to");
        } catch(IllegalArgumentException expectedException) {
            // No action needed.
        }
    }

    @Test
    /*
    tests byte constructor, and sees if getCodePoint returns appropriate
    byte, 0x41-->65
     */
    public void testByteConstructorShouldReturn41() throws Exception {
        byte A = (byte)0x41;
        enc = new EncodingHelperChar(A);
        assertEquals(65, enc.getCodePoint());
    }

    @Test
    /*
    tests byte constructor, and sees if toUtf8Bytes returns appropriate
    byte, 41
     */
    public void testByteConstructorShouldReturnUtf8Byte41() throws Exception {
        byte A = (byte)0x41;
        enc = new EncodingHelperChar(A);
        assertEquals(65, enc.toUtf8Bytes()[0]);
    }

    @Test
    /*
    tests byte constructor and sees if toCodePointString returns appropriate
    unicode, U+0041
     */
    public void testByteConstructorShouldReturnCodePointU0041() throws
            Exception {
        byte A = (byte)0x41;
        enc = new EncodingHelperChar(A);
        assertEquals("U+0041", enc.toCodePointString());
    }

    @Test
    /*
    test byte constructor and sees if toUTF8String returns appropriate
    string, 41
     */
    public void testByteConstructorShouldReturnString41() throws Exception {
        byte A = (byte)0x41;
        enc = new EncodingHelperChar(A);
        assertEquals("\\x41", enc.toUtf8String());
    }

    @Test
    /*
    tests byte constructor and sees if getCharacterName returns right
    character name, LATIN CAPITAL LETTER A
     */
    public void testByteConstructorShouldReturnStringLatinCapitalLetterA()
            throws Exception {
        byte A = (byte)0x41;
        enc = new EncodingHelperChar(A);
        assertEquals("LATIN CAPITAL LETTER A", enc.getCharacterName());
    }
}