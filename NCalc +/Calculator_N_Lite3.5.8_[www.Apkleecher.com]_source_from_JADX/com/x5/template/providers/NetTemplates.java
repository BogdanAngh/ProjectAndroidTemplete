package com.x5.template.providers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class NetTemplates extends TemplateProvider {
    private String baseURL;

    public NetTemplates(String baseURL) {
        this.baseURL = null;
        this.baseURL = baseURL;
    }

    public String loadContainerDoc(String docName) throws IOException {
        return getUrlContents(this.baseURL + docName);
    }

    public String getProtocol() {
        return "net";
    }

    private static String getUrlContents(String theUrl) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(theUrl).openConnection().getInputStream()));
        StringBuilder content = new StringBuilder();
        while (true) {
            String line = bufferedReader.readLine();
            if (line != null) {
                content.append(line + "\n");
            } else {
                bufferedReader.close();
                return content.toString();
            }
        }
    }
}
