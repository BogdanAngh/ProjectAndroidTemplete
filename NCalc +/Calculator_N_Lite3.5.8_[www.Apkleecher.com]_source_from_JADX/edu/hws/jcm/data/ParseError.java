package edu.hws.jcm.data;

public class ParseError extends RuntimeException {
    public ParserContext context;

    public ParseError(String message, ParserContext context) {
        super(message);
        this.context = context;
    }
}
