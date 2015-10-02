package edu.carleton.stensaethf;

import sun.security.krb5.EncryptedData;


public class Main {

    public static void main(String[] args) {
        theInsaneCommandLineArgumentTree(args);
    }

    /**
     * A helper method, that checks to see how many bytes correspond to a
     * character.
     *
     * @param length length of the number of bytes in a character, i.e. if a
     *               character has 4 bytes, length = 4
     * @param byteArray byte array that contains all the bytes specified by
     *                  the user
     * @param i keeps track of what index in byteArray the program is on
     * @return returns an object EncodingHelperChar
     */
    private static EncodingHelperChar byteChecker(int length, byte[] byteArray,
                                                  int i) {
        byte[] oneChar = new byte[length];
        for (int x = 0; x < length; x++) {
            oneChar[x] = byteArray[i];
            i++;
        }
        EncodingHelperChar enc = null;
        try {
            enc = new EncodingHelperChar(oneChar);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UTF-8 input.");
            System.exit(1);
        }
        return enc;
    }

    /**
     * A helper method that deals with string input
     *
     * @param args args passed by user
     */
    private static String[] inputString(String[] args)  {
        // A string array to hold all necessary info to be printed.
        String[] data = new String[4];
        try {
            // A variable to determine what the string is. It changes depending
            // upon whether or not the user specifies input or output.
            String string = "";
            if (!args[0].equals("-i") && !args[0].equals("--input")) {
                string = args[0];
            } else if (!args[2].equals("-o") && !args[2].equals("--output")) {
                string = args[2];
            } else {
                string = args[4];
            }
            // Variables that will be assigned to values in our string array,
            // info.
            String stringOutput = "";
            String codePoints = "Code points: ";
            if (string.length() == 1) {
                stringOutput = "Character: " + string;
                codePoints = "Code point: ";
            } else {
                stringOutput = "String: " + string;
            }
            String utf8Encodings = "UTF-8: ";
            EncodingHelperChar enc = null;
            for (int i = 0; i < string.length(); i++) {
                enc = new EncodingHelperChar
                        (string
                                .charAt(i));
                codePoints += enc.toCodePointString() + " ";
                utf8Encodings += enc.toUtf8String();
            }
            data[0] = stringOutput;
            data[1] = codePoints;
            data[2] = "Name: " + enc.getCharacterName();
            data[3] = utf8Encodings;
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Invalid syntax.");
            System.exit(1);
        }
        return data;
    }

    /**
     * If the user specifies code point as input, then this helper method
     * returns a String array containing the string, code point, UTF-8 bytes
     * and character name's
     *
     *
     * @param args args that the user inputs
     * @return
     */
    private static String[] noInputCodePoint(String[] args) {
        // Create a string array that returns the data needed to print in the
        // terminal
        String[] info = new String[4];
        info[0] = "String: ";
        info[1] = "Code points: ";
        info[2] = "Name: ";
        info[3] = "UTF-8: ";
        if (args.length != 1) {
            // The for loop goes through each code points
            for (int i = 0; i < args.length; i++) {
                // The program then splits the string in order to turn it
                // into a hex string. (Note; change 1 and 2 to 0 and 1 in
                // string uPlus prior to submission.)
                String[] args2Split = args[i].split("");
                String uPlus = args2Split[0] + args2Split[1];
                // Makes sure the two characters of the code point are indeed
                // U+.
                if (uPlus.equals("U+")) {
                    String hexString = "";
                    // Creates a hex string to be converted into an integer.
                    for (int j = 3; j < args2Split.length; j++) {
                        hexString += args2Split[j];
                    }
                    // Change the string into a hexadecimal int. If it cannot
                    // be converted into a hexadecimal string tell the user
                    // that the code point is bad.
                    int codePoint = 0;
                    try {
                        codePoint = Integer.parseInt(hexString, 16);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid code point.");
                        System.exit(1);
                    }
                    // Get the character based on the code point.
                    String symbol = Character.toString((char) codePoint);
                    EncodingHelperChar enc = new EncodingHelperChar(codePoint);
                    // Set the info string array according to whether or not
                    // there is one code point or multiple.
                    info[0] += symbol;
                    info[1] += enc.toCodePointString() + " ";
                    info[2] = enc.getCharacterName();
                    info[3] += enc.toUtf8String();
                } else {
                    System.out.println("Invalid input type.");
                    System.exit(1);
                }
            }
        } else {
            info = inputCodePoint(args);
        }
        return info;
    }

    /**
     * If the user specifies code point as input, then this helper method
     * returns a String array containing the string, code point, UTF-8 bytes
     * and character name's
     *
     *
     * @param args args that the user inputs
     * @return
     */
    private static String[] inputCodePoint(String[] args) {
        // Create a string array that returns the data needed to print in the
        // terminal
        String[] info = new String[4];
        info[0] = "String: ";
        info[1] = "Code points: ";
        info[3] = "UTF-8: ";
        // If there are only 3 arguments, this means no output type is
        // specified or no input type is specified.
        String argsUsing = "";
        String[] argsSplit = null;
        if (args.length == 1) {
            argsUsing = args[0];
            argsSplit = argsUsing.split(" ");
        } else if (args.length == 3) {
            argsUsing = args[2];
            argsSplit = argsUsing.split(" ");
        } else if (args.length == 5) {
            argsUsing = args[4];
            argsSplit = argsUsing.split(" ");
        } else {
            System.out.println("The input is formatted incorrectly");
        }
        // The for loop goes through each code point(s)
        for (int i = 0; i <argsSplit.length; i++) {
            // The program then splits the string in order to turn it
            // into a hex string.
            String[] argsUsingSplit = argsSplit[i].split("");
            String uPlus = argsUsingSplit[0] + argsUsingSplit[1];
            // Makes sure the two characters of the code point are indeed U+.
            if (uPlus.equals("U+")) {
                String hexString = "";
                // Creates a hex string to be converted into an integer.
                for (int j = 2; j < argsUsingSplit.length;
                     j++) {
                    hexString += argsUsingSplit[j];
                }
                // Change the string into a hexadecimal int. If it cannot
                // be converted into a hexadecimal string tell the user
                // that the code point is bad.
                int codePoint = 0;
                try {
                    codePoint = Integer.parseInt(hexString, 16);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid code " +
                            "point.");
                    System.exit(1);
                }
                // Get the character based on the code point.
                String symbol = Character.toString((char) codePoint);
                EncodingHelperChar enc = new EncodingHelperChar(codePoint);
                // Set the info string array according to whether or not
                // there is one code point or multiple.
                if (argsSplit.length == 1) {
                    info[0] = "Character: " + symbol;
                    info[1] = "Code point: " + enc.toCodePointString();
                    info[2] = "Name: " + enc.getCharacterName();
                    info[3] = "UTF-8: " + enc.toUtf8String();
                } else {
                    info[0] += symbol;
                    info[1] += enc.toCodePointString() + " ";
                    info[3] += enc.toUtf8String();
                }
            } else {
                System.out.println("Invalid input type.");
                System.exit(1);
            }
        }
        return info;
    }

    /**
     * Takes the arguments if the user give utf8 as an input. Returns a string array containing
     * all necessary data to print.
     *
     * @param args
     * @return String[] info, all info needed for printing
     */
    private static String[] inputUtf8(String[] args) {
        // A string array that stores all potential data to be printed.
        String[] data = new String[4];
        // A byte array to keep track of the UTF-8 encoded bytes
        byte[] hexByteArray = null;
        int argsLength = 0;
        // A string, argsUsing, that changes depending on whether the user
        // specifies no input, input, output or input and output.
        String argsUsing = null;
        // Determines what the user has inputted, and sets hexByteArray,
        // argsLength and argsUsing accordingly. Let's the user know if they have
        // not properly formatted the input.
        if (args.length == 1) {
            hexByteArray = new byte[args[0].length() / 4];
            argsLength = args[0].length();
            argsUsing = args[0];
        } else if (args.length == 3) {
            hexByteArray = new byte[args[2].length() / 4];
            argsLength = args[2].length();
            argsUsing = args[2];
        } else if (args.length == 5) {
            hexByteArray = new byte[args[4].length() / 4];
            argsLength = args[4].length();
            argsUsing = args[4];
        } else {
            System.out.println("The input is improperly formatted");
            System.exit(1);
        }
        // An int to keep track of what index of hexByteArray, we are on. We
        // start at the third character of argsUsing as the input starts off
        // as '\x'. We then add 4 to the original i, as we want to move to the
        // next important characters, i.e. the bytes themselves, in argsUsing.
        int y = 0;
        for (int i = 3; i < argsLength; i += 4) {
            hexByteArray[y] = (byte) ((Character.digit
                    (argsUsing.charAt(i), 16) << 4) +
                    Character.digit(argsUsing.charAt(i + 1), 16));
            y++;
        }
        // Initialize an object, enc, of type EncodingHelperChar, in order
        // to determine all the info needed for printing.
        EncodingHelperChar enc = null;
        String stringOutput = "";
        String codePoints = "";
        String[] argsSplit = argsUsing.split("");
        String utf8Encodings = "UTF-8: ";
        // Gets rid of the quotations.
        for (int u = 1; u<argsSplit.length-1;u++) {
            utf8Encodings += argsSplit[u];
        }
        // Char that gets the char based off the code point. int variable that
        // determines if there is one or multiple characters in the byte array.
        char[] symbol = new char[1];
        int tracker = 0;
        // Check to see how many bytes, correspond to a character.
        for (int j = 0; j < hexByteArray.length; j++) {
            if ((byte) (hexByteArray[j] & 0xF8) == (byte) 0xF0) {
                enc = byteChecker(4, hexByteArray, j);
                j += 3;
            } else if ((byte) (hexByteArray[j] & 0xF0) == (byte) 0xE0) {
                enc = byteChecker(3, hexByteArray, j);
                j += 2;
            } else if ((byte) (hexByteArray[j] & 0xE0) == (byte) 0xC0) {
                enc = byteChecker(2, hexByteArray, j);
                j += 1;
            } else if ((byte) (hexByteArray[j] & 0x80) == (byte) 0x00) {
                enc = byteChecker(1, hexByteArray, j);
            } else {
                System.out.println("Invalid UTF-8 input.");
                System.exit(1);
            }
            // Concenate the code point(s) and character(s).
            codePoints += enc.toCodePointString() + " ";
            symbol = Character.toChars(enc.getCodePoint());
            stringOutput += String.valueOf(symbol);
            tracker++;
        }
        // Checks to see if the byte(s) specified by the user correspond to
        // more than one character.
        if (tracker > 1) {
            data[0] = "String: " + stringOutput;
            data[1] = "Code points: " + codePoints;
            data[3] = utf8Encodings;
        } else {
            data[0] = "Character: " + stringOutput;
            data[1] = "Code point: " + codePoints;
            data[2] = "Name: " + enc.getCharacterName();
            data[3] = utf8Encodings;
        }
        return data;
    }

    /**
     * A helper method that prints out the summary.
     * @param info A string array that carries all the info necessary to print.
     */
    private static void summaryPrint(String[] info) {
        System.out.println(info[0]);
        System.out.println(info[1]);
        if (info[0].length() == 12) {
            System.out.println(info[2]);
        }
        System.out.println(info[3]);
    }

    private static void theInsaneCommandLineArgumentTree(String[] args) {
        // Try to complete this, but catch IndexOutOfBounds errors.
        try {
            if (args.length == 0) {
                // USAGE message
                System.out.println("EncodingHelper [-i | --input <type>] [-o " +
                        "| --output <type>] <data>");
                System.out.println("");
                System.out.println("Default type is string");
                System.out.println("Types supported with -i: string, utf8, " +
                        "codepoint");
                System.out.println("Types supported with -o: string, utf8, " +
                        "codepoint, summary");
                System.out.println("");
                System.out.println("Example: $ EncodingHelper ABC");
                System.out.println("Example: $ EncodingHelper " +
                        "-i utf8 '\\x41\\x42\\x43'");
                System.out.println("Example: $ EncodingHelper -i codepoint " +
                        "\"U+0041 U+0042 U+0043\"");
                System.out.println("Example: $ EncodingHelper -i codepoint " +
                        "U+0041 U+0042 U+0043");
            } else if (args[0].equals("-usage") || args[0].equals("-h") ||
                    args[0].equals("-help") || args[0].equals("--usage") ||
                    args[0].equals("--help")) {
                // Usage message
                System.out.println("EncodingHelper [-i | --input <type>] [-o " +
                        "| --output <type>] <data>");
                System.out.println("");
                System.out.println("Default type is string");
                System.out.println("Types supported with -i: string, utf8, " +
                        "codepoint");
                System.out.println("Types supported with -o: string, utf8, " +
                        "codepoint, summary");
                System.out.println("");
                System.out.println("Example: $ EncodingHelper ABC");
                System.out.println("Example: $ EncodingHelper " +
                        "-i utf8 '\\x41\\x42\\x43'");
                System.out.println("Example: $ EncodingHelper -i codepoint " +
                        "\"U+0041 U+0042 U+0043\"");
                System.out.println("Example: $ EncodingHelper -i codepoint " +
                        "U+0041 U+0042 U+0043");
            } else if (args.length == 1) {
                if (args[0].length() == 1) {
                    // summary includes characterName
                    String[] info = inputString(args);
                    System.out.println(info[0]);
                    System.out.println(info[1]);
                    System.out.println(info[2]);
                    System.out.println(info[3]);
                } else {
                    String[] args0 = args[0].split("");
                    String type = "";
                    if (args0.length >=  3) {
                        type = args0[0] + args0[1];
                    }
                    if (type.equals("U+")) {
                        String[] info = noInputCodePoint(args);
                        summaryPrint(info);
                    } else if (type.equals("'\\")) {
                        String[] info = inputUtf8(args);
                        summaryPrint(info);
                    } else {
                        // summary does not include characterName
                        String[] info = inputString(args);
                        System.out.println(info[0]);
                        System.out.println(info[1]);
                        System.out.println(info[3]);
                    }
                }
            } else if (args[0].equals("-i") || args[0].equals("--input")) {
                // -i xxx xxx
                if (args[1].equals("codepoint")) {
                    // -i codepoint <something>
                    if (args[2].equals("-o") || args[2].equals("--output")) {
                        // -i codepoint -o xxxxxxxxx
                        if (args.length > 5) {
                            // -i codepoint -o xxx <point> <point> <etc>
                            if (args[3].equals("codepoint")) {
                                // -i codepoint -o codepoint <point> <point>
                                // <etc>
                                String[] info = inputCodePoint(args);
                                System.out.println(info[1]);
                            } else if (args[3].equals("utf8")) {
                                // -i codepoint -o utf8 <point> <point> <etc>
                                String[] info = inputCodePoint(args);
                                System.out.println(info[3]);
                            } else if (args[3].equals("string")) {
                                // -i codepoint -o string <point> <point> <etc>
                                String[] info = inputCodePoint(args);
                                System.out.println(info[0]);
                            } else if (args[3].equals("summary")) {
                                // -i codepoint -o summary <point> <point> <etc>
                                String[] info = inputCodePoint(args);
                                System.out.println(info[0]);
                                System.out.println(info[1]);
                                System.out.println(info[3]);
                            } else {
                                // throw an error
                                System.out.println("Invalid output type.");
                                System.exit(1);
                            }
                        } else if (args.length == 5) {
                            // -i codepoint -o xxx "codepoints"
                            if (args[3].equals("codepoint")) {
                                // -i codepoint -o codepoint "codepoints"
                                String[] info = inputCodePoint(args);
                                System.out.println(info[1]);
                            } else if (args[3].equals("utf8")) {
                                // -i codepoint -o utf8 "codepoints"
                                String[] info = inputCodePoint(args);
                                System.out.println(info[3]);
                            } else if (args[3].equals("string")) {
                                // -i codepoint -o string "codepoints"
                                String[] info = inputCodePoint(args);
                                System.out.println(info[0]);
                            } else if (args[3].equals("summary")) {
                                // -i codepoint -o summary "codepoints"
                                String[] info = inputCodePoint(args);
                                System.out.println(info[0]);
                                System.out.println(info[1]);
                                System.out.println(info[3]);
                            } else {
                                // throw an error
                                System.out.println("Invalid output type.");
                                System.exit(1);
                            }
                        } else {
                            // throw an error
                            System.out.println("Invalid number of arguments.");
                            System.exit(1);
                        }
                    } else {
                        // -i codepoint "codepoints"
                        if (args.length == 3) {
                            // -i codepoint "codepoints"
                            String[] info = inputCodePoint(args);
                            System.out.println(info[0]);
                            System.out.println(info[1]);
                            System.out.println(info[2]);
                            System.out.println(info[3]);
                        } else {
                            // -i codepoint <point> <point> <etc>
                            String[] info = inputCodePoint(args);
                            System.out.println(info[0]);
                            System.out.println(info[1]);
                            System.out.println(info[3]);
                        }
                    }
                } else if (args.length > 5 || args.length == 4 || args.length
                        == 2) {
                    // throw an error
                    System.out.println("Invalid number of arguments.");
                    System.exit(1);
                } else if (args.length == 3) {
                    // -i xxx xxx
                    if (args[1].equals("utf8")) {
                        // -i utf8 xxx
                        String[] info = inputUtf8(args);
                        summaryPrint(info);
                    } else if (args[1].equals("string")) {
                        // -i string xxx
                        String[] info = inputString(args);
                        summaryPrint(info);
                    } else {
                        // throw an error
                        System.out.println("Invalid input type.");
                        System.exit(1);
                    }
                } else if (args.length == 5) {
                    // -i xxx -o xxx xxx
                    if (args[2].equals("-o") || args[2].equals("--output")) {
                        if (args[1].equals("utf8")) {
                            // -i utf8 xxx
                            String[] info = inputUtf8(args);
                            if (args[3].equals("codepoint")) {
                                // -i utf8 -o codepoint xxx
                                System.out.println(info[1]);
                            } else if (args[3].equals("utf8")) {
                                // -i utf8 -o utf8 xxx
                                System.out.println(info[3]);
                            } else if (args[3].equals("string")) {
                                // -i utf8 -o string xxx
                                System.out.println(info[0]);
                            } else if (args[3].equals("summary")) {
                                // -i utf8 -o summary xxx
                                summaryPrint(info);
                            } else {
                                // throw an error
                                System.out.println("Invalid output type.");
                                System.exit(1);
                            }
                        } else if (args[1].equals("string")) {
                            // -i string xxx
                            String[] info = inputString(args);
                            if (args[3].equals("codepoint")) {
                                // -i string -o codepoint xxx
                                System.out.println(info[1]);
                            } else if (args[3].equals("utf8")) {
                                // -i string -o utf8 xxx
                                System.out.println(info[3]);
                            } else if (args[3].equals("string")) {
                                // -i string -o string xxx
                                System.out.println(info[0]);
                            } else if (args[3].equals("summary")) {
                                // -i string -o summary xxx
                                summaryPrint(info);
                            } else {
                                // throw an error
                                System.out.println("Invalid output type.");
                                System.exit(1);
                            }
                        } else {
                            // throw an error
                            System.out.println("Invalid input type.");
                            System.exit(1);
                        }
                    } else {
                        // throw an error
                        System.out.println("Invalid syntax.");
                        System.exit(1);
                    }
                } else {
                    // throw an error
                    System.out.println("Invalid number of arguments.");
                    System.exit(1);
                }
            } else if (args[0].equals("-o") || args[0].equals("--output")) {
                if (args.length == 3) {
                    String[] args2 = args[2].split("");
                    ;
                    String type = "";
                    if (args2.length > 3) {
                        type = args2[1] + args2[2];
                    }
                    String[] arguments = new String[args.length];
                    // We are changing the first argument by the user, as the
                    // our helper method is only concerned about the index
                    // within args, that the method will use to get data on.
                    // All our methods already due this if the input is "-i",
                    // so it is much easier to pass all the same inputs
                    // except we change the initial argument.
                    arguments = args;
                    arguments[0] = "-i";

                    //String[] info = inputString(arguments);
                    if (args[1].equals("codepoint")) {
                        // -o codepoint xxx
                        if (type.equals("U+")) {
                            String[] info = inputCodePoint(arguments);
                            System.out.println(info[1]);
                        } else if (type.equals("'\\")) {
                            String[] info = inputUtf8(arguments);
                            System.out.println(info[1]);
                        } else {
                            String[] info = inputString(arguments);
                            System.out.println(info[1]);
                        }
                    } else if (args[1].equals("utf8")) {
                        // -o utf8 xxx
                        if (type.equals("U+")) {
                            String[] info = inputCodePoint(arguments);
                            System.out.println(info[3]);
                        } else if (type.equals("'\\")) {
                            String[] info = inputUtf8(arguments);
                            System.out.println(info[3]);
                        } else {
                            String[] info = inputString(arguments);
                            System.out.println(info[3]);
                        }
                    } else if (args[1].equals("string")) {
                        // -o string xxx
                        if (type.equals("U+")) {
                            String[] info = inputCodePoint(arguments);
                            System.out.println(info[0]);
                        } else if (type.equals("'\\")) {
                            String[] info = inputUtf8(arguments);
                            System.out.println(info[0]);
                        } else {
                            String[] info = inputString(arguments);
                            System.out.println(info[0]);
                        }
                    } else if (args[1].equals("summary")) {
                        // -o summary xxx
                        if (type.equals("U+")) {
                            String[] info = inputCodePoint(arguments);
                            summaryPrint(info);
                        } else if (type.equals("'\\")) {
                            String[] info = inputUtf8(arguments);
                            summaryPrint(info);
                        } else {
                            String[] info = inputString(arguments);
                            summaryPrint(info);
                        }

                    } else {
                        // throw an error
                        System.out.println("Invalid output type.");
                        System.exit(1);
                    }
                } else if (args[2].equals("-i") || args[2].equals("-input")) {
                    // -o xxx -i xxxxxxxxxx
                    if (args.length == 5) {
                        if (args[1].equals("codepoint")) {
                            // -o codepoint -i xxxxx
                            String[] info;
                            String[] arguments = args;
                            arguments[0] = "-i";
                            arguments[2] = "-o";
                            if (args[3].equals("codepoint")) {
                                // -o codepoint -i codepoint xxx
                                info = inputCodePoint(arguments);
                                System.out.println(info[1]);
                            } else if (args[3].equals("utf8")) {
                                // -o codepont -i utf8 xxx
                                info = inputUtf8(arguments);
                                System.out.println(info[1]);
                            } else if (args[3].equals("string")) {
                                // -o codepoint -i string xxx
                                info = inputString(arguments);
                                System.out.println(info[1]);
                            } else {
                                // throw an error
                                System.out.println("Invalid input type.");
                                System.exit(1);
                            }
                        } else if (args[1].equals("utf8")) {
                            // -o utf8 -i xxxx
                            String[] info;
                            String[] arguments = args;
                            arguments[0] = "-i";
                            arguments[2] = "-o";
                            if (args[3].equals("codepoint")) {
                                // -o utf8 -i codepoint xxx
                                info = inputCodePoint(arguments);
                                System.out.println(info[3]);
                            } else if (args[3].equals("utf8")) {
                                // -o utf8 -i utf8 xxx
                                info = inputUtf8(arguments);
                                System.out.println(info[3]);
                            } else if (args[3].equals("string")) {
                                // -o utf8 -i string xxx
                                info = inputString(arguments);
                                System.out.println(info[3]);
                            } else {
                                // throw an error
                                System.out.println("Invalid input type.");
                                System.exit(1);
                            }
                        } else if (args[1].equals("string")) {
                            // -o string -i xxx
                            String[] info;
                            String[] arguments = args;
                            arguments[0] = "-i";
                            arguments[2] = "-o";
                            if (args[3].equals("codepoint")) {
                                // -o string -i codepoint xxx
                                info = inputCodePoint(arguments);
                                System.out.println(info[0]);
                            } else if (args[3].equals("utf8")) {
                                // -o string -i utf8 xxx
                                info = inputUtf8(arguments);
                                System.out.println(info[0]);
                            } else if (args[3].equals("string")) {
                                // -o string -i string xxx
                                info = inputString(arguments);
                                System.out.println(info[0]);
                            } else {
                                // throw an error
                                System.out.println("Invalid input type.");
                                System.exit(1);
                            }
                        } else {
                            // throw an error
                            System.out.println("Invalid output type.");
                            System.exit(1);
                        }
                    } else if (args[3].equals("codepoint")) {
                        String[] arguments = args;
                        arguments[0] = "-i";
                        arguments[2] = "-o";
                        String[] info = inputCodePoint(arguments);
                        if (args[1].equals("codepoint")) {
                            // -o codepoint -i codepoint <point> <point> <etc>
                            System.out.println(info[1]);
                        } else if (args[1].equals("utf8")) {
                            // -o utf8 -i codepoint <point> <point> <etc>
                            System.out.println(info[3]);
                        } else if (args[1].equals("string")) {
                            // -o string -i codepoint <point> <point> <etc>
                            System.out.println(info[0]);
                        } else if (args[1].equals("summary")) {
                            // -o summary -i codepoint <point> <point> <etc>
                            System.out.println(info[0]);
                            System.out.println(info[1]);
                            System.out.println(info[3]);
                        } else {
                            // throw an error
                            System.out.println("Invalid output type.");
                            System.exit(1);
                        }
                    } else {
                        // throw an error
                        System.out.println("Invalid syntax.");
                        System.exit(1);
                    }
                    // If -o at index 0, but no input specified.
                } else if (args[1].equals("codepoint") || args[1].equals("utf8")
                        || args[1].equals("string") || args[1].equals("summary")) {
                    // -o xxx xxx
                    String[] arguments = new String[args.length - 2];
                    for (int g = 2; g < args.length; g++) {
                        arguments[g - 2] = args[g];
                    }
                    String[] info = noInputCodePoint(arguments);

                    if (args[1].equals("codepoint")) {
                        System.out.println(info[1]);
                    } else if (args[1].equals("utf8")) {
                        System.out.println(info[3]);
                    } else if (args[1].equals("string")) {
                        System.out.println(info[0]);
                    } else if (args[1].equals("summary")) {
                        summaryPrint(info);
                    } else {
                        // throw an error
                        System.out.println("Invalid output type.");
                        System.exit(1);
                    }

                } else {
                    // throw an error
                    System.out.println("Invalid syntax.");
                    System.exit(1);
                }
            } else if ((args[0].split("")[0].equals("U")) && (args[0].split
                    ("")[1].equals("+"))) {
                String[] info = noInputCodePoint(args);
                summaryPrint(info);
            } else {
                // throw an error
                System.out.println("Invalid syntax.");
                System.exit(1);
            }
        } catch (IndexOutOfBoundsException e) {
            // throw an error
            System.out.println("Invalid syntax.");
            System.exit(1);
        }
    }
}
