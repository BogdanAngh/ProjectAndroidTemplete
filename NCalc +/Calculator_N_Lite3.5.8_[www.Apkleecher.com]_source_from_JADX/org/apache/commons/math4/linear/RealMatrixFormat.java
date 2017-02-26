package org.apache.commons.math4.linear;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.commons.math4.exception.MathParseException;
import org.apache.commons.math4.util.CompositeFormat;

public class RealMatrixFormat {
    private static final String DEFAULT_COLUMN_SEPARATOR = ",";
    private static final String DEFAULT_PREFIX = "{";
    private static final String DEFAULT_ROW_PREFIX = "{";
    private static final String DEFAULT_ROW_SEPARATOR = ",";
    private static final String DEFAULT_ROW_SUFFIX = "}";
    private static final String DEFAULT_SUFFIX = "}";
    private final String columnSeparator;
    private final NumberFormat format;
    private final String prefix;
    private final String rowPrefix;
    private final String rowSeparator;
    private final String rowSuffix;
    private final String suffix;

    public RealMatrixFormat() {
        this(DEFAULT_ROW_PREFIX, DEFAULT_SUFFIX, DEFAULT_ROW_PREFIX, DEFAULT_SUFFIX, DEFAULT_ROW_SEPARATOR, DEFAULT_ROW_SEPARATOR, CompositeFormat.getDefaultNumberFormat());
    }

    public RealMatrixFormat(NumberFormat format) {
        this(DEFAULT_ROW_PREFIX, DEFAULT_SUFFIX, DEFAULT_ROW_PREFIX, DEFAULT_SUFFIX, DEFAULT_ROW_SEPARATOR, DEFAULT_ROW_SEPARATOR, format);
    }

    public RealMatrixFormat(String prefix, String suffix, String rowPrefix, String rowSuffix, String rowSeparator, String columnSeparator) {
        this(prefix, suffix, rowPrefix, rowSuffix, rowSeparator, columnSeparator, CompositeFormat.getDefaultNumberFormat());
    }

    public RealMatrixFormat(String prefix, String suffix, String rowPrefix, String rowSuffix, String rowSeparator, String columnSeparator, NumberFormat format) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.rowPrefix = rowPrefix;
        this.rowSuffix = rowSuffix;
        this.rowSeparator = rowSeparator;
        this.columnSeparator = columnSeparator;
        this.format = format;
        this.format.setGroupingUsed(false);
    }

    public static Locale[] getAvailableLocales() {
        return NumberFormat.getAvailableLocales();
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public String getRowPrefix() {
        return this.rowPrefix;
    }

    public String getRowSuffix() {
        return this.rowSuffix;
    }

    public String getRowSeparator() {
        return this.rowSeparator;
    }

    public String getColumnSeparator() {
        return this.columnSeparator;
    }

    public NumberFormat getFormat() {
        return this.format;
    }

    public static RealMatrixFormat getInstance() {
        return getInstance(Locale.getDefault());
    }

    public static RealMatrixFormat getInstance(Locale locale) {
        return new RealMatrixFormat(CompositeFormat.getDefaultNumberFormat(locale));
    }

    public String format(RealMatrix m) {
        return format(m, new StringBuffer(), new FieldPosition(0)).toString();
    }

    public StringBuffer format(RealMatrix matrix, StringBuffer toAppendTo, FieldPosition pos) {
        pos.setBeginIndex(0);
        pos.setEndIndex(0);
        toAppendTo.append(this.prefix);
        int rows = matrix.getRowDimension();
        for (int i = 0; i < rows; i++) {
            toAppendTo.append(this.rowPrefix);
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                if (j > 0) {
                    toAppendTo.append(this.columnSeparator);
                }
                CompositeFormat.formatDouble(matrix.getEntry(i, j), this.format, toAppendTo, pos);
            }
            toAppendTo.append(this.rowSuffix);
            if (i < rows - 1) {
                toAppendTo.append(this.rowSeparator);
            }
        }
        toAppendTo.append(this.suffix);
        return toAppendTo;
    }

    public RealMatrix parse(String source) {
        ParsePosition parsePosition = new ParsePosition(0);
        RealMatrix result = parse(source, parsePosition);
        if (parsePosition.getIndex() != 0) {
            return result;
        }
        throw new MathParseException(source, parsePosition.getErrorIndex(), Array2DRowRealMatrix.class);
    }

    public RealMatrix parse(String source, ParsePosition pos) {
        int initialIndex = pos.getIndex();
        String trimmedPrefix = this.prefix.trim();
        String trimmedSuffix = this.suffix.trim();
        String trimmedRowPrefix = this.rowPrefix.trim();
        String trimmedRowSuffix = this.rowSuffix.trim();
        String trimmedColumnSeparator = this.columnSeparator.trim();
        String trimmedRowSeparator = this.rowSeparator.trim();
        CompositeFormat.parseAndIgnoreWhitespace(source, pos);
        if (!CompositeFormat.parseFixedstring(source, trimmedPrefix, pos)) {
            return null;
        }
        List<List<Number>> matrix = new ArrayList();
        List<Number> rowComponents = new ArrayList();
        boolean loop = true;
        while (loop) {
            if (rowComponents.isEmpty()) {
                CompositeFormat.parseAndIgnoreWhitespace(source, pos);
                if (!(trimmedRowPrefix.length() == 0 || CompositeFormat.parseFixedstring(source, trimmedRowPrefix, pos))) {
                    return null;
                }
            }
            CompositeFormat.parseAndIgnoreWhitespace(source, pos);
            if (!CompositeFormat.parseFixedstring(source, trimmedColumnSeparator, pos)) {
                if (trimmedRowSuffix.length() != 0 && !CompositeFormat.parseFixedstring(source, trimmedRowSuffix, pos)) {
                    return null;
                }
                CompositeFormat.parseAndIgnoreWhitespace(source, pos);
                if (CompositeFormat.parseFixedstring(source, trimmedRowSeparator, pos)) {
                    matrix.add(rowComponents);
                    rowComponents = new ArrayList();
                } else {
                    loop = false;
                }
            }
            if (loop) {
                CompositeFormat.parseAndIgnoreWhitespace(source, pos);
                Number component = CompositeFormat.parseNumber(source, this.format, pos);
                if (component != null) {
                    rowComponents.add(component);
                } else if (rowComponents.isEmpty()) {
                    loop = false;
                } else {
                    pos.setIndex(initialIndex);
                    return null;
                }
            }
            continue;
        }
        if (!rowComponents.isEmpty()) {
            matrix.add(rowComponents);
        }
        CompositeFormat.parseAndIgnoreWhitespace(source, pos);
        if (!CompositeFormat.parseFixedstring(source, trimmedSuffix, pos)) {
            return null;
        }
        if (matrix.isEmpty()) {
            pos.setIndex(initialIndex);
            return null;
        }
        double[][] data = new double[matrix.size()][];
        int row = 0;
        for (List<Number> rowList : matrix) {
            data[row] = new double[rowList.size()];
            for (int i = 0; i < rowList.size(); i++) {
                data[row][i] = ((Number) rowList.get(i)).doubleValue();
            }
            row++;
        }
        return MatrixUtils.createRealMatrix(data);
    }
}
