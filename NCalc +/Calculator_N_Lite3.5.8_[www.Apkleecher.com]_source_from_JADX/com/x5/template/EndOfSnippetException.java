package com.x5.template;

public class EndOfSnippetException extends Exception {
    private String line;

    public EndOfSnippetException(String line) {
        this.line = line;
    }

    public String getRestOfLine() {
        return this.line;
    }
}
