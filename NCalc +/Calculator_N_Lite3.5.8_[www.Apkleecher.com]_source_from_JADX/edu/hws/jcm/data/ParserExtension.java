package edu.hws.jcm.data;

public interface ParserExtension extends MathObject {
    void doParse(Parser parser, ParserContext parserContext);
}
