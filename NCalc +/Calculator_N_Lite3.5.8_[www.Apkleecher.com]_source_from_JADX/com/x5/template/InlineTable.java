package com.x5.template;

import com.x5.util.TableData;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InlineTable {
    private static final Pattern DELIM;

    static {
        DELIM = Pattern.compile("[,\\]]");
    }

    public static void main(String[] args) {
        System.out.println("Reading in table...");
        TableData table = parseTable("[[code,name,price],[abc,Apples,$2.50],[xyz,Whiz-Bang \\[you\\, and everyone\\, will love it!\\],$13.99]]");
        System.out.println("...finished.  Checking data structures:");
        String[] labels = table.getColumnLabels();
        while (table.hasNext()) {
            Map<String, Object> record = table.nextRecord();
            for (int i = 0; i < labels.length; i++) {
                if (i > 0) {
                    System.out.print(", ");
                }
                String label = labels[i];
                System.out.print(label + "=" + record.get(label));
            }
            System.out.println();
        }
    }

    public static TableData parseTable(String data) {
        return _parseTable(data);
    }

    private static SimpleTable _parseTable(String data) {
        ArrayList<String> row = new ArrayList();
        int dataLen = data.length();
        int marker = data.indexOf("[");
        if (marker < 0) {
            return null;
        }
        String[] labels = null;
        ArrayList records = null;
        while (marker > -1 && marker < dataLen) {
            marker = data.indexOf("[", marker + 1);
            if (marker < 0) {
                break;
            }
            while (marker > 0 && marker < dataLen && data.charAt(marker) != ']') {
                marker++;
                int delimPos = nextUnescapedDelim(DELIM, data, marker);
                if (delimPos > 0) {
                    row.add(data.substring(marker, delimPos).replace("\\[", "[").replace("\\]", "]").replace("\\,", ","));
                }
                marker = delimPos;
            }
            if (row.size() > 0) {
                String[] parsedRow = (String[]) row.toArray(new String[row.size()]);
                if (labels == null) {
                    labels = parsedRow;
                } else {
                    if (records == null) {
                        records = new ArrayList();
                    }
                    records.add(parsedRow);
                }
                row.clear();
            }
            if (marker > 0) {
                marker = data.indexOf(",", marker + 1);
            }
        }
        if (labels != null) {
            return new SimpleTable(labels, records);
        }
        return null;
    }

    private static int nextUnescapedDelim(Pattern delim, String input, int searchFrom) {
        Matcher m = delim.matcher(input);
        if (!m.find(searchFrom)) {
            return -1;
        }
        int delimPos = m.start();
        boolean isProvenDelimeter = false;
        while (!isProvenDelimeter) {
            int bsCount = 0;
            while (delimPos - (bsCount + 1) >= searchFrom && input.charAt(delimPos - (bsCount + 1)) == Letters.BACKSLASH) {
                bsCount++;
            }
            if (bsCount % 2 == 0) {
                isProvenDelimeter = true;
            } else if (!m.find()) {
                return -1;
            } else {
                delimPos = m.start();
            }
        }
        return delimPos;
    }
}
