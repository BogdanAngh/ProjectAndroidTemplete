package com.example.duy.calculator.math_eval;

import java.util.regex.Pattern;

public class Constants {
    public static char BINARY_SEPARATOR = '\u0000';
    public static char DECIMAL_POINT = '\u0000';
    public static char DECIMAL_SEPARATOR = '\u0000';
    public static final char DEGREE_UNICODE = '\u00b0';
    public static final char DIV_UNICODE = '/';
    public static final char EQUAL_UNICODE = '=';
    public static final String FACTOR = "Factor";
    public static final char FACTORIAL_UNICODE = '!';
    public static final String FALSE = "false";
    public static final String FROM = "From";
    public static char HEXADECIMAL_SEPARATOR = '\u0000';
    public static final String INFINITY = "Infinity";
    public static final char INFINITY_UNICODE = '\u221e';
    public static final String INTEGRATE = "Integrate";
    public static final char LEFT_PAREN = '(';
    public static final String LIMIT = "Limit";
    public static char MATRIX_SEPARATOR = '\u0000';
    public static final char MINUS_UNICODE = '-';
    public static final char MUL_UNICODE = '*';
    public static final String NAN = "NaN";
    public static char OCTAL_SEPARATOR = '\u0000';
    public static final char PLUS_UNICODE = '+';
    public static final char POWER_PLACEHOLDER = '\u200b';
    public static final char POWER_UNICODE = '^';
    public static final String PRIMITIVE = "Integrate";
    public static String REGEX_NOT_NUMBER = null;
    public static String REGEX_NUMBER = null;
    public static final char RIGHT_PAREN = ')';
    public static final String SOLVE = "Solve";
    public static final String SYNTAX_Q = "SyntaxQ";
    public static final String TABLE = "Table";
    public static final String TO = "To";
    public static final String TRUE = "true";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String ZERO = "0";

    static {
        rebuildConstants();
    }

    public static void rebuildConstants() {
        DECIMAL_POINT = '.';
        DECIMAL_SEPARATOR = Letters.SPACE;
        BINARY_SEPARATOR = Letters.SPACE;
        HEXADECIMAL_SEPARATOR = Letters.SPACE;
        OCTAL_SEPARATOR = Letters.SPACE;
        MATRIX_SEPARATOR = Letters.COMMA;
        String number = "A-F0-9" + Pattern.quote(String.valueOf(DECIMAL_POINT)) + Pattern.quote(String.valueOf(DECIMAL_SEPARATOR)) + Pattern.quote(String.valueOf(BINARY_SEPARATOR)) + Pattern.quote(String.valueOf(OCTAL_SEPARATOR)) + Pattern.quote(String.valueOf(HEXADECIMAL_SEPARATOR));
        REGEX_NUMBER = "[" + number + "]";
        REGEX_NOT_NUMBER = "[^" + number + "]";
    }
}
