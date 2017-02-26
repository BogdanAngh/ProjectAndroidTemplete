package com.example.duy.calculator.math_eval;

import io.github.kexanie.library.BuildConfig;

public class FormatExpression {
    private String TAG;

    public FormatExpression() {
        this.TAG = FormatExpression.class.getName();
    }

    public static String appendParenthesis(String input) {
        int open = 0;
        int close = 0;
        for (char c : input.toCharArray()) {
            if (c == Constants.LEFT_PAREN) {
                open++;
            } else if (c == Constants.RIGHT_PAREN) {
                close++;
            }
        }
        int j;
        if (open > close) {
            for (j = 0; j < open - close; j++) {
                input = input + ")";
            }
        } else if (close > open) {
            for (j = 0; j < close - open; j++) {
                input = "(" + input;
            }
        }
        return input;
    }

    public String insertSupScripts(String input) {
        StringBuilder formattedInput = new StringBuilder();
        int sub_open = 0;
        int sub_closed = 0;
        int paren_open = 0;
        int paren_closed = 0;
        int i = 0;
        while (i < input.length()) {
            char c = input.charAt(i);
            if (c == Constants.POWER_UNICODE) {
                formattedInput.append("<sup>");
                if (sub_open == 0) {
                    formattedInput.append("<small>");
                }
                sub_open++;
                if (i + 1 == input.length()) {
                    formattedInput.append(c);
                    if (sub_closed == 0) {
                        formattedInput.append("</small>");
                    }
                    formattedInput.append("</sup>");
                    sub_closed++;
                } else {
                    formattedInput.append(Constants.POWER_PLACEHOLDER);
                }
            } else {
                if (sub_open > sub_closed) {
                    if (paren_open == paren_closed && (c == Constants.PLUS_UNICODE || ((c == Constants.MINUS_UNICODE && input.charAt(i - 1) != Constants.POWER_UNICODE) || c == Constants.MUL_UNICODE || c == Constants.DIV_UNICODE || c == Constants.EQUAL_UNICODE || ((c == Constants.LEFT_PAREN && (Solver.isDigit(input.charAt(i - 1)) || input.charAt(i - 1) == Constants.RIGHT_PAREN)) || ((Solver.isDigit(c) && input.charAt(i - 1) == Constants.RIGHT_PAREN) || !(Solver.isDigit(c) || !Solver.isDigit(input.charAt(i - 1)) || c == Constants.DECIMAL_POINT)))))) {
                        while (sub_open > sub_closed) {
                            if (sub_closed == 0) {
                                formattedInput.append("</small>");
                            }
                            formattedInput.append("</sup>");
                            sub_closed++;
                        }
                        sub_open = 0;
                        sub_closed = 0;
                        paren_open = 0;
                        paren_closed = 0;
                        if (c == Constants.LEFT_PAREN) {
                            paren_open = 0 - 1;
                        } else if (c == Constants.RIGHT_PAREN) {
                            paren_closed = 0 - 1;
                        }
                    }
                    if (c == Constants.LEFT_PAREN) {
                        paren_open++;
                    } else if (c == Constants.RIGHT_PAREN) {
                        paren_closed++;
                    }
                }
                formattedInput.append(c);
            }
            i++;
        }
        while (sub_open > sub_closed) {
            if (sub_closed == 0) {
                formattedInput.append("</small>");
            }
            formattedInput.append("</sup>");
            sub_closed++;
        }
        return formattedInput.toString();
    }

    public String addComas(Solver solver, String text) {
        return addComas(solver, text, -1).replace("\u2620", BuildConfig.FLAVOR);
    }

    public String addComas(Solver solver, String text, int selectionHandle) {
        return solver.getBaseModule().groupSentence(text, selectionHandle);
    }

    public String format(Solver solver, String text) {
        return appendParenthesis(insertSupScripts(addComas(solver, text)));
    }
}
