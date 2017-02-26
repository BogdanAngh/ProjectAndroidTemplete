package com.x5.template;

public interface ContentSource {
    String fetch(String str);

    String getProtocol();

    Snippet getSnippet(String str);

    boolean provides(String str);
}
