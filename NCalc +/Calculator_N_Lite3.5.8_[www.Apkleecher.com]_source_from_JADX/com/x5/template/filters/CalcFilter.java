package com.x5.template.filters;

import com.x5.template.Chunk;
import edu.jas.ps.UnivPowerSeriesRing;
import io.github.kexanie.library.BuildConfig;

public class CalcFilter extends BasicFilter implements ChunkFilter {
    public String transformText(Chunk chunk, String text, FilterArgs args) {
        if (text == null) {
            return null;
        }
        return args.getFilterArgs() != null ? easyCalc(text, args) : text;
    }

    public String getFilterName() {
        return "calc";
    }

    private static String easyCalc(String text, FilterArgs arg) {
        String[] args = arg.getFilterArgs();
        String expr = args[0];
        String fmt = null;
        if (args.length > 1) {
            fmt = args[1];
        }
        if (expr.indexOf(UnivPowerSeriesRing.DEFAULT_NAME) < 0) {
            expr = UnivPowerSeriesRing.DEFAULT_NAME + expr;
        }
        try {
            return Calc.evalExpression(expr.replace("\\$", BuildConfig.FLAVOR), fmt, new String[]{UnivPowerSeriesRing.DEFAULT_NAME}, new String[]{text});
        } catch (NumberFormatException e) {
            return text;
        } catch (NoClassDefFoundError e2) {
            return "[ERROR: jeplite jar missing from classpath! calc filter requires jeplite library]";
        }
    }
}
