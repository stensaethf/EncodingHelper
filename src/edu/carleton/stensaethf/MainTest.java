package edu.carleton.stensaethf;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alexgriese on 4/13/15.
 */
public class MainTest {

    @Test
    public void testCodepointOutputAndUtf8ShouldNotExit() throws Exception {
        String[] testArguments = new String[3];
        testArguments[0] = "-o";
        testArguments[1] = "codepoint";
        testArguments[2] = "'\\x41\\x42\\x43'";
        String[] outputData = Main.inputUtf8(testArguments);
        String[] expectedData = new String[3];
        expectedData[1] = "Codepoints: U+0041 U+0042 U+0043";
        assertEquals(outputData[1], expectedData[1]);
        // passes
    }

    @Test
    public void testCodepointOutputAndCodepointStringShouldNotExit() throws
            Exception {
        String[] testArguments = new String[3];
        testArguments[0] = "-o";
        testArguments[1] = "codepoint";
        testArguments[2] = "U+0041 U+0042 U+0043";
        String[] outputData = Main.noInputCodePoint(testArguments);
        String[] expectedData = new String[3];
        expectedData[1] = "Codepoints: U+0041 U+0042 U+0043";
        assertEquals(outputData[1], expectedData[1]);
        // passes
    }

    @Test
    public void testCodepointOutputAndMultipleCodepointsShouldNotExit() throws
            Exception {
        String[] testArguments = new String[5];
        testArguments[0] = "-o";
        testArguments[1] = "codepoint";
        testArguments[2] = "U+0041";
        testArguments[3] = "U+0042";
        testArguments[4] = "U+0043";
        String[] outputData = Main.inputCodePoint(testArguments);
        String[] expectedData = new String[3];
        expectedData[1] = "Codepoints: U+0041 U+0042 U+0043";
        assertEquals(outputData[1], expectedData[1]);
        // passes
    }
}