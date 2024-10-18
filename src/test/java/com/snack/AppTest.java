package com.snack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testShowMenu() {
        App.showMenu();

        String expectedOutput = "\n1 - New product\n"
                + "2 - Update product\n"
                + "3 - List products\n"
                + "4 - Sell\n"
                + "5 - Remove product\n"
                + "6 - Exit\n";

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }
}
