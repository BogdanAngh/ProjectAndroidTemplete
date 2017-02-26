package com.x5.template.filters;

import com.x5.template.BlockTag;
import com.x5.template.Chunk;
import com.x5.template.ContentSource;
import com.x5.template.Snippet;
import edu.jas.ps.UnivPowerSeriesRing;

public class ExecFilter extends BasicFilter {
    public String transformText(Chunk chunk, String text, FilterArgs arg) {
        String[] args = arg.getFilterArgs();
        if (args == null || args.length <= 0) {
            return null;
        }
        String templateName = args[0];
        if (chunk != null) {
            templateName = BlockTag.qualifyTemplateRef(chunk.getTemplateOrigin(), templateName);
        }
        if (chunk == null) {
            return null;
        }
        ContentSource theme = chunk.getTemplateSet();
        if (theme == null) {
            return null;
        }
        Snippet filterBody = theme.getSnippet(templateName);
        if (filterBody == null) {
            return null;
        }
        Chunk miniMacro = new Chunk();
        miniMacro.append(filterBody);
        miniMacro.setOrDelete(UnivPowerSeriesRing.DEFAULT_NAME, text);
        return miniMacro.toString();
    }

    public String getFilterName() {
        return "filter";
    }
}
