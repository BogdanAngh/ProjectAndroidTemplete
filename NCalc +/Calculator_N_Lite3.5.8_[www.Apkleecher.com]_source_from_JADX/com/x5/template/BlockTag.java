package com.x5.template;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public abstract class BlockTag {
    public abstract String getBlockEndMarker();

    public abstract String getBlockStartMarker();

    public abstract void renderBlock(Writer writer, Chunk chunk, String str, int i) throws IOException;

    private static int locateTag(List<SnippetPart> parts, String tagToMatch, int startAt) {
        for (int i = startAt; i < parts.size(); i++) {
            SnippetPart part = (SnippetPart) parts.get(i);
            if (part.isTag()) {
                String tagText = ((SnippetTag) part).getTag();
                if (tagText != null && tagText.startsWith(tagToMatch)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int findMatchingBlockEnd(BlockTag helper, List<SnippetPart> parts, int startAt) {
        String scanFor = "." + helper.getBlockEndMarker();
        String nestedScanFor = "." + helper.getBlockStartMarker();
        int nestDepth = 0;
        int nestedBlockPos = locateTag(parts, nestedScanFor, startAt);
        int endMarkerPos = locateTag(parts, scanFor, startAt);
        if (nestedBlockPos > -1 && nestedBlockPos < endMarkerPos) {
            nestDepth = 0 + 1;
        }
        while (nestDepth > 0 && endMarkerPos > 0) {
            while (nestedBlockPos > -1 && nestedBlockPos < endMarkerPos) {
                nestedBlockPos = locateTag(parts, nestedScanFor, nestedBlockPos + 1);
                if (nestedBlockPos > -1 && nestedBlockPos < endMarkerPos) {
                    nestDepth++;
                }
            }
            nestDepth--;
            endMarkerPos = locateTag(parts, scanFor, endMarkerPos + 1);
            if (nestedBlockPos > -1 && nestedBlockPos < endMarkerPos) {
                nestDepth++;
            }
        }
        return endMarkerPos;
    }

    public boolean hasBody(String openingTag) {
        return true;
    }

    public boolean doSmartTrimAroundBlock() {
        return false;
    }

    public static String qualifyTemplateRef(String origin, String templateRef) {
        if (origin == null || templateRef.charAt(0) != Letters.POUND) {
            return templateRef;
        }
        return origin + templateRef;
    }
}
