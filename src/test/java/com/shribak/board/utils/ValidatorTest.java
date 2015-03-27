package com.shribak.board.utils;


import org.junit.Test;

import static org.junit.Assert.*;


public class ValidatorTest {
    private Validator validator = new Validator();

    @Test
    public void testValidateName() {
        assertTrue(validator.validateName("test"));
        assertTrue(validator.validateName("test 34 test"));
        assertFalse(validator.validateName("12test"));
        assertFalse(validator.validateName("tes"));
        assertFalse(validator.validateName("test test test test test test"));
    }

    @Test
    public void testIsInteger() {
        assertTrue(validator.isInteger("123"));
        assertFalse(validator.isInteger("test"));
    }

    @Test
    public void testValidateTitle() {
        assertTrue(validator.validateTitle("test test test test"));
        assertTrue(validator.validateTitle("12345 test test"));
        assertFalse(validator.validateTitle("test"));
        assertFalse(validator.validateTitle("test test test test test test test test test test test test test test"));
    }

    @Test
    public void testValidateContent() {
        String wrongContent = "wrong content test, wrong content test wrong content test wrong content test" +
                "wrong content test wrong content test wrong content test wrong content test wrong content test" +
                "wrong content test wrong content test wrong content test wrong content test wrong content test" +
                "wrong content test wrong content test wrong content test wrong content test wrong content test" +
                "wrong content test wrong content test wrong content test wrong content test wrong content test" +
                "wrong content test wrong content test wrong content test wrong content test wrong content test";

        assertTrue(validator.validateContent("test content test content test content"));
        assertFalse(validator.validateContent("test content"));
        assertFalse(validator.validateContent(wrongContent));
    }

    @Test
    public void testValidateAction() {
        assertTrue(validator.validateAction("1"));
        assertTrue(validator.validateAction("6"));
        assertFalse(validator.validateAction("8"));
        assertFalse(validator.validateAction("test"));
    }

    @Test
    public void testValidateCategory() {
        assertTrue(validator.validateAction("1"));
        assertTrue(validator.validateAction("5"));
        assertFalse(validator.validateAction("8"));
        assertFalse(validator.validateAction("test"));
    }
}
