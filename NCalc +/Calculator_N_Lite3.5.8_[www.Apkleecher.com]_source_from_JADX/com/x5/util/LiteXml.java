package com.x5.util;

import com.example.duy.calculator.math_eval.Constants;
import edu.jas.ps.UnivPowerSeriesRing;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiteXml {
    private static final int MAX_PARSE = 15;
    private static final Map<String, String> STD_ENTITIES;
    private static final Pattern XML_ENTITY_REGEX;
    private Map<String, String> attrs;
    private String xml;

    public LiteXml(String xmlNode) {
        this.attrs = null;
        this.xml = xmlNode;
    }

    public String getNodeType() {
        if (this.xml == null) {
            return null;
        }
        int startAt = 0;
        int headerPos = this.xml.indexOf("?>");
        if (headerPos > -1) {
            startAt = headerPos + 2;
        }
        int begPos = this.xml.indexOf(60, startAt);
        if (begPos < 0) {
            return null;
        }
        int spacePos = this.xml.indexOf(32, begPos);
        int endPos = this.xml.indexOf(62, begPos);
        if (spacePos > -1 && spacePos < endPos) {
            endPos = spacePos;
        }
        if (endPos >= begPos + 1) {
            return this.xml.substring(begPos + 1, endPos);
        }
        return null;
    }

    public Map<String, String> getAttributes() {
        if (this.xml == null) {
            return null;
        }
        if (this.attrs != null) {
            return this.attrs;
        }
        int tagEndPos = this.xml.indexOf(62);
        if (tagEndPos < 0) {
            return null;
        }
        int spacePos = this.xml.indexOf(32);
        if (spacePos < 0 || spacePos > tagEndPos) {
            return null;
        }
        this.attrs = parseAttributes(this.xml.substring(spacePos + 1, tagEndPos));
        return this.attrs;
    }

    private Map<String, String> parseAttributes(String attrDef) {
        Map<String, String> attrs = new HashMap();
        int cursor = 0;
        while (cursor < attrDef.length()) {
            int openQuotePos = attrDef.indexOf(61, cursor);
            if (openQuotePos >= 0) {
                String param = attrDef.substring(cursor, openQuotePos);
                openQuotePos = attrDef.indexOf(34, openQuotePos + 1);
                if (openQuotePos < 0) {
                    break;
                }
                cursor = openQuotePos + 1;
                int closeQuotePos = nextUnescapedDelim("\"", attrDef, cursor);
                if (closeQuotePos < 0) {
                    break;
                }
                attrs.put(param.trim(), unescapeXML(attrDef.substring(cursor, closeQuotePos).replaceAll("\\\\\"", "\"").replaceAll("\\\\\\\\", "\\\\")));
                cursor = attrDef.indexOf(32, closeQuotePos + 1);
                if (cursor < 0) {
                    break;
                }
                cursor++;
            } else {
                break;
            }
        }
        return attrs;
    }

    public static int nextUnescapedDelim(String delim, String toScan, int searchFrom) {
        int delimPos = toScan.indexOf(delim, searchFrom);
        boolean isProvenDelimeter = false;
        while (!isProvenDelimeter) {
            int bsCount = 0;
            while (delimPos - (bsCount + 1) >= searchFrom && toScan.charAt(delimPos - (bsCount + 1)) == Letters.BACKSLASH) {
                bsCount++;
            }
            if (bsCount % 2 == 0) {
                isProvenDelimeter = true;
            } else {
                delimPos = toScan.indexOf(delim, delimPos + 1);
                if (delimPos < 0) {
                    return -1;
                }
            }
        }
        return delimPos;
    }

    public String getAttribute(String attr) {
        Map<String, String> myAttrs = getAttributes();
        if (myAttrs == null || myAttrs.size() < 1) {
            return null;
        }
        return (String) myAttrs.get(attr);
    }

    private String getRawNodeValue() {
        if (this.xml == null) {
            return null;
        }
        String nodeType = getNodeType();
        if (nodeType == null) {
            return null;
        }
        int topTagEnd = this.xml.indexOf(62, this.xml.indexOf(nodeType) + nodeType.length());
        int endTagBeg = this.xml.lastIndexOf(60);
        if (topTagEnd < 0 || endTagBeg < topTagEnd) {
            return null;
        }
        if (this.xml.indexOf(nodeType, endTagBeg) < 0) {
            return this.xml;
        }
        return this.xml.substring(topTagEnd + 1, endTagBeg);
    }

    private boolean isCDATA(String x) {
        if (x == null) {
            return false;
        }
        String contents = x.trim();
        if (contents.startsWith("<![CDATA[") && contents.endsWith("]]>")) {
            return true;
        }
        return false;
    }

    public String getNodeValue() {
        String contents = getRawNodeValue();
        if (contents == null) {
            return null;
        }
        if (isCDATA(contents)) {
            return contents.trim().substring(9, contents.length() - 3);
        }
        return unescapeXML(contents);
    }

    public LiteXml[] getChildNodes(String nodeType) {
        if (nodeType == null) {
            return null;
        }
        LiteXml[] children = getChildNodes();
        if (children == null) {
            return null;
        }
        int i;
        boolean[] isMatch = new boolean[children.length];
        int matches = 0;
        for (i = 0; i < children.length; i++) {
            if (children[i].getNodeType().equals(nodeType)) {
                matches++;
                isMatch[i] = true;
            }
        }
        if (matches == 0) {
            return null;
        }
        if (matches == children.length) {
            return children;
        }
        LiteXml[] matchingNodes = new LiteXml[matches];
        matches = 0;
        for (i = 0; i < isMatch.length; i++) {
            if (isMatch[i]) {
                matchingNodes[matches] = children[i];
                matches++;
            }
        }
        return matchingNodes;
    }

    public LiteXml[] getChildNodes() {
        if (this.xml == null) {
            return null;
        }
        String insides = getRawNodeValue();
        if (insides == null || isCDATA(insides)) {
            return null;
        }
        int[] endpoints = new int[30];
        int marker = 0;
        int len = insides.length();
        int count = 0;
        while (marker < len) {
            if (count * 2 >= endpoints.length) {
                endpoints = extendArray(endpoints);
            }
            int opening = insides.indexOf(60, marker);
            if (opening < 0) {
                break;
            }
            if (insides.charAt(opening + 1) == '/') {
                return null;
            }
            int closing = insides.indexOf(62, opening + 1);
            if (closing < 0) {
                return null;
            }
            if (insides.charAt(closing - 1) == '/') {
                endpoints[count * 2] = opening;
                endpoints[(count * 2) + 1] = closing + 1;
                count++;
                marker = closing + 1;
            } else {
                int spacePos = insides.indexOf(32, opening + 1);
                int bracketPos = insides.indexOf(62, opening + 1);
                if (spacePos < 0 && bracketPos < 0) {
                    return null;
                }
                int typeEnd = spacePos;
                if (typeEnd < 0 || typeEnd > bracketPos) {
                    typeEnd = bracketPos;
                }
                String type = insides.substring(opening + 1, typeEnd);
                String childEnd = "</" + type;
                int childEndPos = insides.indexOf(childEnd, closing + 1);
                String nestedSOB = "<" + type;
                int nestedPos = insides.indexOf(nestedSOB, closing + 1);
                while (nestedPos > -1 && nestedPos < childEndPos) {
                    childEndPos = insides.indexOf(childEnd, childEndPos + 3);
                    if (childEndPos < 0) {
                        return null;
                    }
                    nestedPos = insides.indexOf(nestedSOB, nestedPos + 3);
                }
                int finalBoundary = insides.indexOf(62, childEndPos + 2);
                if (finalBoundary < 0) {
                    return null;
                }
                endpoints[count * 2] = opening;
                endpoints[(count * 2) + 1] = finalBoundary + 1;
                count++;
                marker = finalBoundary + 1;
            }
        }
        if (count < 1) {
            return null;
        }
        LiteXml[] children = new LiteXml[count];
        for (int i = 0; i < count; i++) {
            children[i] = new LiteXml(insides.substring(endpoints[i * 2], endpoints[(i * 2) + 1]));
        }
        return children;
    }

    private int[] extendArray(int[] endpoints) {
        int[] largerArray = new int[(endpoints.length + 30)];
        System.arraycopy(endpoints, 0, largerArray, 0, endpoints.length);
        return largerArray;
    }

    public LiteXml getFirstChild() {
        LiteXml[] children = getChildNodes();
        if (children == null) {
            return null;
        }
        return children[0];
    }

    public String getPathValue(String xpathLite) {
        LiteXml x = findNode(xpathLite);
        if (x == null) {
            return null;
        }
        return x.getNodeValue();
    }

    public String getNodeValue(String branchPath) {
        if (branchPath == null) {
            return null;
        }
        return getPathValue(normalizeBranchPath(branchPath));
    }

    private String normalizeBranchPath(String branchPath) {
        if (branchPath == null) {
            return null;
        }
        if (branchPath.startsWith("/")) {
            return "*" + branchPath;
        }
        return "*/" + branchPath;
    }

    public LiteXml findChildNode(String branchPath) {
        return findNode(normalizeBranchPath(branchPath));
    }

    public LiteXml findNode(String xpathLite) {
        if (xpathLite == null) {
            return null;
        }
        if (xpathLite.charAt(0) == Constants.DIV_UNICODE) {
            if (xpathLite.charAt(1) == Constants.DIV_UNICODE) {
                return null;
            }
            xpathLite = xpathLite.substring(1);
        }
        StringTokenizer splitter = new StringTokenizer(xpathLite, "/");
        int depth = splitter.countTokens();
        String[] nodeNames = new String[depth];
        for (int i = 0; i < depth; i++) {
            nodeNames[i] = splitter.nextToken();
        }
        return findNode(nodeNames);
    }

    public LiteXml findNode(String[] xpathLite) {
        return findNodeX(xpathLite, 0);
    }

    private static boolean isMatch(String nodeName, String pattern) {
        if (nodeName == null || pattern == null) {
            return false;
        }
        if (nodeName.equals(pattern) || pattern.equals("*")) {
            return true;
        }
        return false;
    }

    private LiteXml findNodeX(String[] xpathLite, int x) {
        if (!isMatch(getNodeType(), xpathLite[x])) {
            return null;
        }
        LiteXml[] childNodes = getChildNodes();
        if (childNodes == null) {
            return null;
        }
        for (LiteXml child : childNodes) {
            if (isMatch(child.getNodeType(), xpathLite[x + 1])) {
                if (xpathLite.length == x + 2) {
                    return child;
                }
                LiteXml node = child.findNodeX(xpathLite, x + 1);
                if (node != null) {
                    return node;
                }
            }
        }
        return null;
    }

    static {
        XML_ENTITY_REGEX = Pattern.compile("&(#?)([^;]+);");
        STD_ENTITIES = getStandardEntities();
    }

    public static String unescapeXML(String xml) {
        StringBuffer unescapedOutput = new StringBuffer(xml.length());
        Matcher m = XML_ENTITY_REGEX.matcher(xml);
        while (m.find()) {
            String entity;
            String ent = m.group(2);
            String hashmark = m.group(1);
            if (hashmark == null || hashmark.length() <= 0) {
                entity = (String) STD_ENTITIES.get(ent);
                if (entity == null) {
                    entity = "&" + ent + ';';
                }
            } else {
                int code;
                if (ent.substring(0, 1).toLowerCase().equals(UnivPowerSeriesRing.DEFAULT_NAME)) {
                    code = Integer.parseInt(ent.substring(1), 16);
                } else {
                    code = Integer.parseInt(ent);
                }
                entity = Character.toString((char) code);
            }
            m.appendReplacement(unescapedOutput, entity);
        }
        m.appendTail(unescapedOutput);
        return unescapedOutput.toString();
    }

    private static Map<String, String> getStandardEntities() {
        Map<String, String> entities = new HashMap(10);
        entities.put("lt", "<");
        entities.put("gt", ">");
        entities.put("amp", "&");
        entities.put("apos", "'");
        entities.put("quot", "\"");
        return entities;
    }

    public String toString() {
        return this.xml;
    }
}
