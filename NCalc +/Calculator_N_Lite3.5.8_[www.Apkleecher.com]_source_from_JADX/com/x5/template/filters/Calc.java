package com.x5.template.filters;

import com.example.duy.calculator.math_eval.Constants;
import edu.jas.ps.UnivPowerSeriesRing;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Map;
import org.cheffo.jeplite.JEP;
import org.cheffo.jeplite.ParseException;

public class Calc {
    public static void main(String[] args) {
        String[] varnames = new String[]{UnivPowerSeriesRing.DEFAULT_NAME};
        String[] varvalues = new String[]{"20"};
        System.out.println(evalExpression("2 + 2 * x", Constants.ZERO, varnames, varvalues));
        System.out.println(evalExpression("sin(pi)", "0.00", null, null));
        System.out.println(evalExpression("(2 + 2 * x^x) % 2", null, varnames, varvalues));
        HashMap<String, Object> map = new HashMap();
        map.put("a", "20");
        map.put("b", "30");
        System.out.println(evalCalc("\"$x * 3 + $y * 40\",\"%.2f\",~a,~b", map));
        System.out.println(evalCalc("\"$x * $x\",\"%.2f\",~a,~b", map));
        System.out.println(evalCalc("\"$x + $y\",\"%.2f\",$a,$b", map));
        map.put("c", "40");
        System.out.println(evalCalc("\"$x + $y + $z\",$a,$b,$c", map));
    }

    public static String evalCalc(String calc, Map<String, Object> vars) {
        int quote1 = calc.indexOf("\"");
        if (quote1 < 0) {
            return null;
        }
        int quote2 = calc.indexOf("\"", quote1 + 1);
        if (quote2 < 0) {
            return null;
        }
        String expr = calc.substring(quote1 + 1, quote2);
        int argStart = quote2 + 1;
        String fmt = null;
        int quote3 = calc.indexOf("\"", quote2 + 1);
        if (quote3 > 0) {
            int quote4 = calc.indexOf("\"", quote3 + 1);
            if (quote4 > 0) {
                fmt = calc.substring(quote3 + 1, quote4);
                argStart = quote4 + 1;
            }
        }
        String[] varNames = parseVarNames(expr);
        String[] varValues = null;
        String jepExpr = expr;
        if (varNames != null) {
            jepExpr = jepExpr.replaceAll("\\$", "V");
            int comma = calc.indexOf(",", argStart);
            if (comma > 0) {
                argStart = comma + 1;
            }
            int closeParen = calc.indexOf(")", argStart);
            if (closeParen < 0) {
                closeParen = calc.length();
            }
            varValues = grokVarValues(calc.substring(argStart, closeParen), vars);
        }
        try {
            return evalExpression(jepExpr, fmt, varNames, varValues);
        } catch (NumberFormatException e) {
            StringBuilder input = new StringBuilder();
            if (varValues != null) {
                int i = 0;
                while (true) {
                    int length = varValues.length;
                    if (i >= r0) {
                        break;
                    }
                    if (i > 0) {
                        input.append(",");
                    }
                    input.append(varValues[i]);
                    i++;
                }
            }
            return "[error evaluating expression - '" + expr + "' - input (" + input + ") must be numeric]";
        }
    }

    private static String[] grokVarValues(String list, Map<String, Object> vars) {
        String[] tokens = list.split(",");
        if (tokens == null) {
            return null;
        }
        String[] values = new String[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            String key = tokens[i].trim();
            if (key.startsWith("~") || key.startsWith("$")) {
                key = key.substring(1);
            }
            Object value = vars.get(key);
            if (value instanceof String) {
                values[i] = (String) value;
            }
        }
        return values;
    }

    private static String[] parseVarNames(String expr) {
        ArrayList<String> names = null;
        int varMarker = expr.indexOf("$");
        while (varMarker > -1) {
            int endOfName = varMarker + 1;
            char c = expr.charAt(endOfName);
            while (isLegalNameChar(c)) {
                endOfName++;
                if (endOfName >= expr.length()) {
                    break;
                }
                c = expr.charAt(endOfName);
            }
            if (names == null) {
                names = new ArrayList();
            }
            String name = "V" + expr.substring(varMarker + 1, endOfName);
            if (!names.contains(name)) {
                names.add(name);
            }
            varMarker = expr.indexOf("$", endOfName);
        }
        if (names == null || names.size() == 0) {
            return null;
        }
        return (String[]) names.toArray(new String[names.size()]);
    }

    private static boolean isLegalNameChar(char c) {
        if (c >= 'a' && c <= 'z') {
            return true;
        }
        if ((c >= 'A' && c <= 'Z') || c == '_') {
            return true;
        }
        if (c < '0' || c > '9') {
            return false;
        }
        return true;
    }

    public static String evalExpression(String expr, String fmt, String[] varNames, String[] varValues) {
        JEP jep = new JEP();
        jep.addStandardConstants();
        jep.addStandardFunctions();
        if (!(varNames == null || varValues == null || varNames.length > varValues.length)) {
            for (int i = 0; i < varNames.length; i++) {
                String var = varNames[i];
                if (var != null) {
                    String val = varValues[i];
                    double v = 0.0d;
                    if (val != null) {
                        v = Double.parseDouble(val);
                    }
                    jep.addVariable(var, v);
                }
            }
        }
        try {
            jep.parseExpression(expr);
            double result = jep.getValue();
            if (fmt == null) {
                return Double.toString(result);
            }
            try {
                if (!fmt.startsWith("%")) {
                    return new DecimalFormat(fmt).format(result);
                }
                return String.format(fmt, new Object[]{Double.valueOf(result)});
            } catch (NumberFormatException e) {
                return Double.toString(result);
            } catch (IllegalFormatException e2) {
                return Double.toString(result);
            }
        } catch (ParseException e3) {
            e3.printStackTrace(System.err);
            return e3.getMessage();
        } catch (Exception e4) {
            e4.printStackTrace(System.err);
            return e4.getMessage();
        }
    }
}
