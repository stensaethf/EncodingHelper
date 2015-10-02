package edu.carleton.stensaethf;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * The main model class for the EncodingHelper project in
 * CS 257, Spring 2015, Carleton College. Each object of this class
 * represents a single Unicode character. The class's methods
 * generate various representations of the character.
 */
public class EncodingHelperChar {
    private int codePoint;
    private Scanner reader;
    private String unicodeFile = "edu/carleton/stensaethf/UnicodeData.txt";

    /**
     * Constructor for EncodingHelperChar when an int is passed. Verifies
     * that the int is in the valid range, throws IllegalArgumentException if
     * not.
     *
     * @param codePoint
     * @return n/a.
     */
    public EncodingHelperChar(int codePoint) {
        if ((codePoint > 0x10FFFF) || (codePoint < 0)) {
            throw new IllegalArgumentException("Invalid code point");
        }
        this.codePoint = codePoint;

    }

    /**
     * Construction for EncodingHelperChar when the input is an array of
     * bytes. The bytes should be in UTF-8 encoding. Invalid input throws an
     * IllegalArgumentException.
     *
     * @return n/a.
     */
    public EncodingHelperChar(byte[] utf8Bytes) {

        if (utf8Bytes ==  null) {
            throw new IllegalArgumentException("Invalid byte array.");
        }

        int lengthArray = utf8Bytes.length;

        // The mask changes dependent upon the length of the array. If the
        // length of the array is 2, this implies the first bit of the UTF-8
        // encoded byte should be 110, as all the information is contained in
        // bits 0 to 5 and 8 to 12. In order to check to make sure that bits
        // 15 to 12 = 110 we need to check to see if utf8Bytes[0] & mask =
        // shouldBe. shouldBe calculates what utf8Bytes[0] & mask equal, in
        // the case of the array being of length 2, shouldBe = 0xC0.
        byte mask = (byte)((byte)0xFF << (7 - lengthArray));
        byte shouldBe = (byte)((byte)0xFF << (8 - lengthArray));

        int codePointTotal = 0;

        // Arrays of different sizes are encoded from UTF-8 to
        // Unicode differently; thus, each if or else if statement handles a
        // different array length. If the given bytes are not encoded using
        // UTF-8 standards, an IllegalArgumentException is thrown
        if (lengthArray == 1) {
            //The mask and shouldBe bytes are different when the array is of
            // length 1, as the first byte of a 2 byte UTF-8 encoded
            // character must start with 110; whereas, a 1 byte UTF-8 encoded
            // must start with a 0. Thus, the comparison between utf8Bytes[0]
            // and mask changes, as our original formula would set mask =
            // 0xC0, when it should 0x80. We change shouldBe's formula
            // accordingly. The if and else if statements also convert the
            // first byte in the byte array to unicode.
            mask = (byte)((byte)0xFF << (8 - lengthArray));
            shouldBe = (byte)((byte)0xFF << (9 - lengthArray));
            if ((byte)(utf8Bytes[0] & mask) == shouldBe) {
                this.codePoint = utf8Bytes[0];
            } else {
                throw new IllegalArgumentException("The byte array is not " +
                        "encoded using UTF-8");
            }
        } else if (lengthArray == 2) {
            if ((byte)(utf8Bytes[0] & mask) != shouldBe) {
                throw new IllegalArgumentException("The byte array is not " +
                        "encoded using UTF-8");
            } else {
                codePointTotal = (((byte)(utf8Bytes[0] & 0x3F) <<
                        ((lengthArray - 1)) * 6)) | codePointTotal;
            }
        } else if (lengthArray == 3) {
            if ((byte)(utf8Bytes[0] & mask) != shouldBe) {
                throw new IllegalArgumentException("The byte array is not " +
                        "encoded using UTF-8");
            } else {
                codePointTotal = ((int)((byte)(utf8Bytes[0] & 0x0F) <<
                        ((lengthArray - 1)) * 6)) | codePointTotal;
            }
        } else if (lengthArray == 4) {
            if ((byte)(utf8Bytes[0] & mask) != shouldBe) {
                throw new IllegalArgumentException("The byte array is not " +
                        "encoded using UTF-8");
            } else {
                codePointTotal = ((int)((byte)(utf8Bytes[0] & 0x07) <<
                        ((lengthArray - 1)) * 6)) | codePointTotal;
            }
        } else {
            throw new IllegalArgumentException("The byte array is too large, " +
                    "and is not supported by Unicode");
        }

        // Calls checker10 if the length of the array is greater than 1 to
        // verify that the bytes (not including index 0) are valid. See
        // checker10, for info on its function. Throws an error if the bytes
        // are not encoded in UTF-8.
        if (lengthArray > 1) {
            if (checker10(utf8Bytes) != false) {
                throw new IllegalArgumentException("The byte array is not " +
                        "encoded using UTF-8");
            } else {
                //This converts the other (utf8Bytes[1] to
                // utf8Bytes[utf8Bytes.length-1] UTF-8 bytes to
                // unicode.
                int shifterAmount = 0;

                for (int i = lengthArray - 1; i > 0; i--) {
                    codePointTotal = ((int)((byte)(utf8Bytes[i] & 0x3F) <<
                            shifterAmount)) | codePointTotal;
                    shifterAmount += 6;
                }
                this.codePoint = codePointTotal;
            }
        }
    }

    /**
     * checker10 checks whether the bytes in the UTF-8 byte array are encoded
     * using UTF-8. Does not check the byte at index 0, as this has already
     * been verified.
     * Returns a boolean.
     *
     * @param utf8Bytes
     * @return boolean.
     */
    private boolean checker10(byte[] utf8Bytes) {
        for (int i = utf8Bytes.length - 1; i > 0; i--) {
            if ((byte) (utf8Bytes[i] & 0xC0) != 0x80) {
                return false;
            }
        }
        return true;
    }

    /**
     * This is a constructor that takes a character, and sets the code point of
     * the given char.
     *
     * @param ch
     * @return n/a.
     */
    public EncodingHelperChar(char ch) {
        int tmpCodePoint = (int)ch;
        if ((tmpCodePoint > 0x10FFFF) || (tmpCodePoint < 0)) {
            throw new IllegalArgumentException("Invalid Code point");
        }
        this.codePoint = tmpCodePoint;
    }

    /**
     * Returns the code point.
     *
     * @return codepoint (int).
     */
    public int getCodePoint() {
        return this.codePoint;
    }

    /**
     * Changes the member variables, based on the new code point given.
     * Checks if codePoint given is invalid - larger than 0x10FFFF or
     * negative. If it is valid, sets this.codePoint to codePoint.
     *
     * @param codePoint
     * @return none.
     */
    public void setCodePoint(int codePoint) {
        if ((codePoint > 0x10FFFF) || (codePoint < 0)) {
            throw new IllegalArgumentException("message");
        }
        this.codePoint = codePoint;
    }
    
    /**
     * Converts this character into an array of the bytes in its UTF-8
     * representation.
     *   For example, if this character is a lower-case letter e with an acute
     * accent, whose UTF-8 form consists of the two-byte sequence C3 A9, then
     * this method returns a byte[] of length 2 containing C3 and A9.
     * 
     * @return the UTF-8 byte array for this character
     */
    public byte[] toUtf8Bytes() {
        // utf8Bytes is a byte array that gets sent to the utf8Bytes
        // constructor, in order to get converted to unicode.
        // shiftedRight is a variable that keeps track of the code point
        // once it has been shifted right.
        // byteZero is a temporary variable that keeps track of a
        // utf8bytes[0] byte.
        byte[] utf8Bytes = null;
        byte shiftedRight;
        byte byteZero;

        // There are 4 separate if and else if statements, that determine how
        // long the utf8Bytes array will be. If the code point is less than
        // 0x7F then it returns the byte. Otherwise, it shifts the bytes to
        // the right according to the length of the byte array.
        if (this.codePoint <= 0x7F) {
            utf8Bytes = new byte[1];
            utf8Bytes[0] = (byte)this.codePoint;
            return utf8Bytes;
        } else if (this.codePoint <= 0x7FF) {
            utf8Bytes = new byte[2];
            shiftedRight = (byte)(this.codePoint >>> 6);
            byteZero = (byte)(shiftedRight | 0xC0);
            utf8Bytes[0] = byteZero;
        } else if (this.codePoint <= 0xFFFF) {
            utf8Bytes = new byte[3];
            shiftedRight = (byte)(this.codePoint >>> 12);
            byteZero = (byte)(shiftedRight | 0xE0);
            utf8Bytes[0] = byteZero;
        } else if (this.codePoint <= 0x1FFFFF) {
            utf8Bytes = new byte[4];
            shiftedRight = (byte)(this.codePoint >>> 18);
            byteZero = (byte)(shiftedRight | 0xF0);
            utf8Bytes[0] = byteZero;
        }

        // If the byte array is longer than 1, we create the other bytes in
        // the array.
        if (this.codePoint > 0x7F) {
            int k = 0;
            for (int i = utf8Bytes.length - 1; i > 0; i--) {
                byte newByte = (byte)(this.codePoint >> (6 * k));
                newByte = (byte)(newByte | 0x80);
                newByte = (byte)(newByte & 0xBF);
                utf8Bytes[i] = newByte;
                k++;
            }
        }

        return utf8Bytes;
    }
    
    /**
     * Generates the conventional 4-digit hexadecimal code point notation for
     * this character.
     *   For example, if this character is a lower-case letter e with an acute
     * accent, then this method returns the string U+00E9 (no quotation marks in
     * the returned String).
     *
     * @return the U+ string for this character
     */
    public String toCodePointString() {
        String hexCodePoint = (Integer.toString(this.codePoint, 16)).toUpperCase();
        // Adds appropriate amount of 0s.
        if (hexCodePoint.length() == 1) {
            hexCodePoint = "000" + hexCodePoint;
        } else if (hexCodePoint.length() == 2) {
            hexCodePoint = "00" + hexCodePoint;
        } else if (hexCodePoint.length() == 3) {
            hexCodePoint = "0" + hexCodePoint;
        }

        return "U+" + hexCodePoint;
    }
    
    /**
     * Generates a hexadecimal representation of this character suitable for
     * pasting into a string literal in languages that support hexadecimal byte
     * escape sequences in string literals (e.g. C, C++, and Python).
     *   For example, if this character is a lower-case letter e with an acute
     * accent (U+00E9), then this method returns the string \xC3\xA9. Note that
     * quotation marks should not be included in the returned String.
     *
     * @return the escaped hexadecimal byte string
     */
    public String toUtf8String() {
        //Uses toUTF8Bytes to get the byte array, and build the string up
        // and add \x before each byte. The 0xff in the sb.append line makes
        // sure the byte is unsigned.
        byte[] utf8ByteArray = this.toUtf8Bytes();
        StringBuilder sb = new StringBuilder(utf8ByteArray.length * 2);
        for (int i = 0; i < utf8ByteArray.length; i++) {
            sb.append(String.format("\\x%02X", utf8ByteArray[i] & 0xFF));
        }
        return sb.toString();
    }
    
    /**
     * Generates the official Unicode name for this character.
     *   For example, if this character is a lower-case letter e with an acute
     * accent, then this method returns the string "LATIN SMALL LETTER E WITH
     * ACUTE" (without quotation marks).
     *
     * @return this character's Unicode name
     */
    public String getCharacterName() {
        //Constructs a scanner called reader that goes line by line searching
        // for the specified code point.
        try {
            reader = new Scanner(new File(unicodeFile));
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }

        String characterName = "";
        String hexCodePoint = (Integer.toString(this.codePoint, 16)).toUpperCase();
        // Adds appropriate amount of 0s.
        if (hexCodePoint.length() == 1) {
            hexCodePoint = "000" + hexCodePoint;
        } else if (hexCodePoint.length() == 2) {
            hexCodePoint = "00" + hexCodePoint;
        } else if (hexCodePoint.length() == 3) {
            hexCodePoint = "0" + hexCodePoint;
        }
        //A variable that holds the string of each line in the UnicodeData.txt
        String line;
        //A while loop that parses through the document, UnicodeData.txt,
        // searching for the user's inputted code point.
        while (reader.hasNextLine()) {
            line = reader.nextLine();

            //Splits the line by the semicolon, in order to look at the
            // actual info in the line.
            String[] lineSeparated = line.split(";");

            //We only look at the first string in the line, as for example in
            // Latin Capital Letter A, the code point is 0041. However, in the
            // same line there exists a string, 0061, which is the code point
            // for Latin Lower Case Letter A. If a user were to input 0061,
            // and we didn't look at the first string in each line the program
            // would find the 0061 in the line concerning Latin Capital
            // Letter A rather than Latin Lower Case Letter A.
            if (lineSeparated[0].equals(hexCodePoint)) {
                characterName = lineSeparated[1];
                if (characterName.equals("<control>")) {
                    characterName = characterName + " " + lineSeparated[10];
                }
            }
        }

        // Assigns characterName appropriate string if the codePoint is
        // valid, but not in the UnicodeData.txt file.
        if (characterName.equals("")) {
            characterName = "<unknown> U+" + hexCodePoint;
        }

        return characterName;
    }
}
