package com.snack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetUserInputTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testGetUserInput() {
        String simulatedInput = "3\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        int result = App.getUserInput();

        assertEquals(3, result);

        String expectedOutput = "Please indicate the option you want: \n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }
}
