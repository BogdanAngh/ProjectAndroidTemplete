package org.jsoup.nodes;

import com.facebook.share.internal.ShareConstants;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document.OutputSettings;

public class DocumentType extends Node {
    public DocumentType(String name, String publicId, String systemId, String baseUri) {
        super(baseUri);
        Validate.notEmpty(name);
        attr(ShareConstants.WEB_DIALOG_PARAM_NAME, name);
        attr("publicId", publicId);
        attr("systemId", systemId);
    }

    public String nodeName() {
        return "#doctype";
    }

    void outerHtmlHead(StringBuilder accum, int depth, OutputSettings out) {
        accum.append("<!DOCTYPE ").append(attr(ShareConstants.WEB_DIALOG_PARAM_NAME));
        if (!StringUtil.isBlank(attr("publicId"))) {
            accum.append(" PUBLIC \"").append(attr("publicId")).append("\"");
        }
        if (!StringUtil.isBlank(attr("systemId"))) {
            accum.append(" \"").append(attr("systemId")).append("\"");
        }
        accum.append('>');
    }

    void outerHtmlTail(StringBuilder accum, int depth, OutputSettings out) {
    }
}
