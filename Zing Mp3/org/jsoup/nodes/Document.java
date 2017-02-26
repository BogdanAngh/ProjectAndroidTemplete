package org.jsoup.nodes;

import com.facebook.share.internal.ShareConstants;
import com.google.android.exoplayer.C0989C;
import com.mp3download.zingmp3.BuildConfig;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

public class Document extends Element {
    private String location;
    private OutputSettings outputSettings;
    private QuirksMode quirksMode;

    public static class OutputSettings implements Cloneable {
        private Charset charset;
        private CharsetEncoder charsetEncoder;
        private EscapeMode escapeMode;
        private int indentAmount;
        private boolean outline;
        private boolean prettyPrint;

        public OutputSettings() {
            this.escapeMode = EscapeMode.base;
            this.charset = Charset.forName(C0989C.UTF8_NAME);
            this.charsetEncoder = this.charset.newEncoder();
            this.prettyPrint = true;
            this.outline = false;
            this.indentAmount = 1;
        }

        public EscapeMode escapeMode() {
            return this.escapeMode;
        }

        public OutputSettings escapeMode(EscapeMode escapeMode) {
            this.escapeMode = escapeMode;
            return this;
        }

        public Charset charset() {
            return this.charset;
        }

        public OutputSettings charset(Charset charset) {
            this.charset = charset;
            this.charsetEncoder = charset.newEncoder();
            return this;
        }

        public OutputSettings charset(String charset) {
            charset(Charset.forName(charset));
            return this;
        }

        CharsetEncoder encoder() {
            return this.charsetEncoder;
        }

        public boolean prettyPrint() {
            return this.prettyPrint;
        }

        public OutputSettings prettyPrint(boolean pretty) {
            this.prettyPrint = pretty;
            return this;
        }

        public boolean outline() {
            return this.outline;
        }

        public OutputSettings outline(boolean outlineMode) {
            this.outline = outlineMode;
            return this;
        }

        public int indentAmount() {
            return this.indentAmount;
        }

        public OutputSettings indentAmount(int indentAmount) {
            Validate.isTrue(indentAmount >= 0);
            this.indentAmount = indentAmount;
            return this;
        }

        public OutputSettings clone() {
            try {
                OutputSettings clone = (OutputSettings) super.clone();
                clone.charset(this.charset.name());
                clone.escapeMode = EscapeMode.valueOf(this.escapeMode.name());
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public enum QuirksMode {
        noQuirks,
        quirks,
        limitedQuirks
    }

    public Document(String baseUri) {
        super(Tag.valueOf("#root"), baseUri);
        this.outputSettings = new OutputSettings();
        this.quirksMode = QuirksMode.noQuirks;
        this.location = baseUri;
    }

    public static Document createShell(String baseUri) {
        Validate.notNull(baseUri);
        Document doc = new Document(baseUri);
        Element html = doc.appendElement("html");
        html.appendElement(TtmlNode.TAG_HEAD);
        html.appendElement(TtmlNode.TAG_BODY);
        return doc;
    }

    public String location() {
        return this.location;
    }

    public Element head() {
        return findFirstElementByTagName(TtmlNode.TAG_HEAD, this);
    }

    public Element body() {
        return findFirstElementByTagName(TtmlNode.TAG_BODY, this);
    }

    public String title() {
        Element titleEl = getElementsByTag(ShareConstants.WEB_DIALOG_PARAM_TITLE).first();
        return titleEl != null ? StringUtil.normaliseWhitespace(titleEl.text()).trim() : BuildConfig.FLAVOR;
    }

    public void title(String title) {
        Validate.notNull(title);
        Element titleEl = getElementsByTag(ShareConstants.WEB_DIALOG_PARAM_TITLE).first();
        if (titleEl == null) {
            head().appendElement(ShareConstants.WEB_DIALOG_PARAM_TITLE).text(title);
        } else {
            titleEl.text(title);
        }
    }

    public Element createElement(String tagName) {
        return new Element(Tag.valueOf(tagName), baseUri());
    }

    public Document normalise() {
        Element htmlEl = findFirstElementByTagName("html", this);
        if (htmlEl == null) {
            htmlEl = appendElement("html");
        }
        if (head() == null) {
            htmlEl.prependElement(TtmlNode.TAG_HEAD);
        }
        if (body() == null) {
            htmlEl.appendElement(TtmlNode.TAG_BODY);
        }
        normaliseTextNodes(head());
        normaliseTextNodes(htmlEl);
        normaliseTextNodes(this);
        normaliseStructure(TtmlNode.TAG_HEAD, htmlEl);
        normaliseStructure(TtmlNode.TAG_BODY, htmlEl);
        return this;
    }

    private void normaliseTextNodes(Element element) {
        List<Node> toMove = new ArrayList();
        for (Node node : element.childNodes) {
            Node node2;
            if (node2 instanceof TextNode) {
                TextNode tn = (TextNode) node2;
                if (!tn.isBlank()) {
                    toMove.add(tn);
                }
            }
        }
        for (int i = toMove.size() - 1; i >= 0; i--) {
            node2 = (Node) toMove.get(i);
            element.removeChild(node2);
            body().prependChild(new TextNode(" ", BuildConfig.FLAVOR));
            body().prependChild(node2);
        }
    }

    private void normaliseStructure(String tag, Element htmlEl) {
        Elements elements = getElementsByTag(tag);
        Element master = elements.first();
        if (elements.size() > 1) {
            Node dupe;
            List<Node> toMove = new ArrayList();
            for (int i = 1; i < elements.size(); i++) {
                dupe = elements.get(i);
                for (Node node : dupe.childNodes) {
                    toMove.add(node);
                }
                dupe.remove();
            }
            for (Node dupe2 : toMove) {
                master.appendChild(dupe2);
            }
        }
        if (!master.parent().equals(htmlEl)) {
            htmlEl.appendChild(master);
        }
    }

    private Element findFirstElementByTagName(String tag, Node node) {
        if (node.nodeName().equals(tag)) {
            return (Element) node;
        }
        for (Node child : node.childNodes) {
            Node found = findFirstElementByTagName(tag, child);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    public String outerHtml() {
        return super.html();
    }

    public Element text(String text) {
        body().text(text);
        return this;
    }

    public String nodeName() {
        return "#document";
    }

    public Document clone() {
        Document clone = (Document) super.clone();
        clone.outputSettings = this.outputSettings.clone();
        return clone;
    }

    public OutputSettings outputSettings() {
        return this.outputSettings;
    }

    public Document outputSettings(OutputSettings outputSettings) {
        Validate.notNull(outputSettings);
        this.outputSettings = outputSettings;
        return this;
    }

    public QuirksMode quirksMode() {
        return this.quirksMode;
    }

    public Document quirksMode(QuirksMode quirksMode) {
        this.quirksMode = quirksMode;
        return this;
    }

    public boolean equals(Object o) {
        return super.equals(o);
    }
}
