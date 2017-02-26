package com.csvreader;

import com.getkeepsafe.taptargetview.R;
import io.github.kexanie.library.BuildConfig;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.HashMap;
import org.apache.commons.math4.linear.BlockRealMatrix;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public class CsvReader {
    public static final int ESCAPE_MODE_BACKSLASH = 2;
    public static final int ESCAPE_MODE_DOUBLED = 1;
    private Charset charset;
    private boolean closed;
    private ColumnBuffer columnBuffer;
    private int columnsCount;
    private long currentRecord;
    private DataBuffer dataBuffer;
    private String fileName;
    private boolean hasMoreData;
    private boolean hasReadNextLine;
    private HeadersHolder headersHolder;
    private boolean initialized;
    private Reader inputStream;
    private boolean[] isQualified;
    private char lastLetter;
    private RawRecordBuffer rawBuffer;
    private String rawRecord;
    private boolean startedColumn;
    private boolean startedWithQualifier;
    private boolean useCustomRecordDelimiter;
    private UserSettings userSettings;
    private String[] values;

    private class ColumnBuffer {
        public char[] Buffer;
        public int Position;

        public ColumnBuffer() {
            this.Buffer = new char[50];
            this.Position = 0;
        }
    }

    private class ComplexEscape {
        private static final int DECIMAL = 3;
        private static final int HEX = 4;
        private static final int OCTAL = 2;
        private static final int UNICODE = 1;

        private ComplexEscape() {
        }
    }

    private class DataBuffer {
        public char[] Buffer;
        public int ColumnStart;
        public int Count;
        public int LineStart;
        public int Position;

        public DataBuffer() {
            this.Buffer = new char[IExpr.ASTID];
            this.Position = 0;
            this.Count = 0;
            this.ColumnStart = 0;
            this.LineStart = 0;
        }
    }

    private class HeadersHolder {
        public String[] Headers;
        public HashMap IndexByName;
        public int Length;

        public HeadersHolder() {
            this.Headers = null;
            this.Length = 0;
            this.IndexByName = new HashMap();
        }
    }

    private class Letters {
        public static final char ALERT = '\u0007';
        public static final char BACKSLASH = '\\';
        public static final char BACKSPACE = '\b';
        public static final char COMMA = ',';
        public static final char CR = '\r';
        public static final char ESCAPE = '\u001b';
        public static final char FORM_FEED = '\f';
        public static final char LF = '\n';
        public static final char NULL = '\u0000';
        public static final char POUND = '#';
        public static final char QUOTE = '\"';
        public static final char SPACE = ' ';
        public static final char TAB = '\t';
        public static final char VERTICAL_TAB = '\u000b';

        private Letters() {
        }
    }

    private class RawRecordBuffer {
        public char[] Buffer;
        public int Position;

        public RawRecordBuffer() {
            this.Buffer = new char[500];
            this.Position = 0;
        }
    }

    private class StaticSettings {
        public static final int INITIAL_COLUMN_BUFFER_SIZE = 50;
        public static final int INITIAL_COLUMN_COUNT = 10;
        public static final int MAX_BUFFER_SIZE = 1024;
        public static final int MAX_FILE_BUFFER_SIZE = 4096;

        private StaticSettings() {
        }
    }

    private class UserSettings {
        public boolean CaptureRawRecord;
        public char Comment;
        public char Delimiter;
        public int EscapeMode;
        public char RecordDelimiter;
        public boolean SafetySwitch;
        public boolean SkipEmptyRecords;
        public char TextQualifier;
        public boolean TrimWhitespace;
        public boolean UseComments;
        public boolean UseTextQualifier;

        public UserSettings() {
            this.TextQualifier = Letters.QUOTE;
            this.TrimWhitespace = true;
            this.UseTextQualifier = true;
            this.Delimiter = Letters.COMMA;
            this.RecordDelimiter = '\u0000';
            this.Comment = Letters.POUND;
            this.UseComments = false;
            this.EscapeMode = CsvReader.ESCAPE_MODE_DOUBLED;
            this.SafetySwitch = true;
            this.SkipEmptyRecords = true;
            this.CaptureRawRecord = true;
        }
    }

    public CsvReader(String fileName, char delimiter, Charset charset) throws FileNotFoundException {
        this.inputStream = null;
        this.fileName = null;
        this.userSettings = new UserSettings();
        this.charset = null;
        this.useCustomRecordDelimiter = false;
        this.dataBuffer = new DataBuffer();
        this.columnBuffer = new ColumnBuffer();
        this.rawBuffer = new RawRecordBuffer();
        this.isQualified = null;
        this.rawRecord = BuildConfig.FLAVOR;
        this.headersHolder = new HeadersHolder();
        this.startedColumn = false;
        this.startedWithQualifier = false;
        this.hasMoreData = true;
        this.lastLetter = '\u0000';
        this.hasReadNextLine = false;
        this.columnsCount = 0;
        this.currentRecord = 0;
        this.values = new String[10];
        this.initialized = false;
        this.closed = false;
        if (fileName == null) {
            throw new IllegalArgumentException("Parameter fileName can not be null.");
        } else if (charset == null) {
            throw new IllegalArgumentException("Parameter charset can not be null.");
        } else if (new File(fileName).exists()) {
            this.fileName = fileName;
            this.userSettings.Delimiter = delimiter;
            this.charset = charset;
            this.isQualified = new boolean[this.values.length];
        } else {
            throw new FileNotFoundException("File " + fileName + " does not exist.");
        }
    }

    public CsvReader(String fileName, char delimiter) throws FileNotFoundException {
        this(fileName, delimiter, Charset.forName("ISO-8859-1"));
    }

    public CsvReader(String fileName) throws FileNotFoundException {
        this(fileName, (char) Letters.COMMA);
    }

    public CsvReader(Reader inputStream, char delimiter) {
        this.inputStream = null;
        this.fileName = null;
        this.userSettings = new UserSettings();
        this.charset = null;
        this.useCustomRecordDelimiter = false;
        this.dataBuffer = new DataBuffer();
        this.columnBuffer = new ColumnBuffer();
        this.rawBuffer = new RawRecordBuffer();
        this.isQualified = null;
        this.rawRecord = BuildConfig.FLAVOR;
        this.headersHolder = new HeadersHolder();
        this.startedColumn = false;
        this.startedWithQualifier = false;
        this.hasMoreData = true;
        this.lastLetter = '\u0000';
        this.hasReadNextLine = false;
        this.columnsCount = 0;
        this.currentRecord = 0;
        this.values = new String[10];
        this.initialized = false;
        this.closed = false;
        if (inputStream == null) {
            throw new IllegalArgumentException("Parameter inputStream can not be null.");
        }
        this.inputStream = inputStream;
        this.userSettings.Delimiter = delimiter;
        this.initialized = true;
        this.isQualified = new boolean[this.values.length];
    }

    public CsvReader(Reader inputStream) {
        this(inputStream, (char) Letters.COMMA);
    }

    public CsvReader(InputStream inputStream, char delimiter, Charset charset) {
        this(new InputStreamReader(inputStream, charset), delimiter);
    }

    public CsvReader(InputStream inputStream, Charset charset) {
        this(new InputStreamReader(inputStream, charset));
    }

    public boolean getCaptureRawRecord() {
        return this.userSettings.CaptureRawRecord;
    }

    public void setCaptureRawRecord(boolean captureRawRecord) {
        this.userSettings.CaptureRawRecord = captureRawRecord;
    }

    public String getRawRecord() {
        return this.rawRecord;
    }

    public boolean getTrimWhitespace() {
        return this.userSettings.TrimWhitespace;
    }

    public void setTrimWhitespace(boolean trimWhitespace) {
        this.userSettings.TrimWhitespace = trimWhitespace;
    }

    public char getDelimiter() {
        return this.userSettings.Delimiter;
    }

    public void setDelimiter(char delimiter) {
        this.userSettings.Delimiter = delimiter;
    }

    public char getRecordDelimiter() {
        return this.userSettings.RecordDelimiter;
    }

    public void setRecordDelimiter(char recordDelimiter) {
        this.useCustomRecordDelimiter = true;
        this.userSettings.RecordDelimiter = recordDelimiter;
    }

    public char getTextQualifier() {
        return this.userSettings.TextQualifier;
    }

    public void setTextQualifier(char textQualifier) {
        this.userSettings.TextQualifier = textQualifier;
    }

    public boolean getUseTextQualifier() {
        return this.userSettings.UseTextQualifier;
    }

    public void setUseTextQualifier(boolean useTextQualifier) {
        this.userSettings.UseTextQualifier = useTextQualifier;
    }

    public char getComment() {
        return this.userSettings.Comment;
    }

    public void setComment(char comment) {
        this.userSettings.Comment = comment;
    }

    public boolean getUseComments() {
        return this.userSettings.UseComments;
    }

    public void setUseComments(boolean useComments) {
        this.userSettings.UseComments = useComments;
    }

    public int getEscapeMode() {
        return this.userSettings.EscapeMode;
    }

    public void setEscapeMode(int escapeMode) throws IllegalArgumentException {
        if (escapeMode == ESCAPE_MODE_DOUBLED || escapeMode == ESCAPE_MODE_BACKSLASH) {
            this.userSettings.EscapeMode = escapeMode;
            return;
        }
        throw new IllegalArgumentException("Parameter escapeMode must be a valid value.");
    }

    public boolean getSkipEmptyRecords() {
        return this.userSettings.SkipEmptyRecords;
    }

    public void setSkipEmptyRecords(boolean skipEmptyRecords) {
        this.userSettings.SkipEmptyRecords = skipEmptyRecords;
    }

    public boolean getSafetySwitch() {
        return this.userSettings.SafetySwitch;
    }

    public void setSafetySwitch(boolean safetySwitch) {
        this.userSettings.SafetySwitch = safetySwitch;
    }

    public int getColumnCount() {
        return this.columnsCount;
    }

    public long getCurrentRecord() {
        return this.currentRecord - 1;
    }

    public int getHeaderCount() {
        return this.headersHolder.Length;
    }

    public String[] getHeaders() throws IOException {
        checkClosed();
        if (this.headersHolder.Headers == null) {
            return null;
        }
        String[] clone = new String[this.headersHolder.Length];
        System.arraycopy(this.headersHolder.Headers, 0, clone, 0, this.headersHolder.Length);
        return clone;
    }

    public void setHeaders(String[] headers) {
        this.headersHolder.Headers = headers;
        this.headersHolder.IndexByName.clear();
        if (headers != null) {
            this.headersHolder.Length = headers.length;
        } else {
            this.headersHolder.Length = 0;
        }
        for (int i = 0; i < this.headersHolder.Length; i += ESCAPE_MODE_DOUBLED) {
            this.headersHolder.IndexByName.put(headers[i], new Integer(i));
        }
    }

    public String[] getValues() throws IOException {
        checkClosed();
        String[] clone = new String[this.columnsCount];
        System.arraycopy(this.values, 0, clone, 0, this.columnsCount);
        return clone;
    }

    public String get(int columnIndex) throws IOException {
        checkClosed();
        if (columnIndex <= -1 || columnIndex >= this.columnsCount) {
            return BuildConfig.FLAVOR;
        }
        return this.values[columnIndex];
    }

    public String get(String headerName) throws IOException {
        checkClosed();
        return get(getIndex(headerName));
    }

    public static CsvReader parse(String data) {
        if (data != null) {
            return new CsvReader(new StringReader(data));
        }
        throw new IllegalArgumentException("Parameter data can not be null.");
    }

    public boolean readRecord() throws IOException {
        checkClosed();
        this.columnsCount = 0;
        this.rawBuffer.Position = 0;
        this.dataBuffer.LineStart = this.dataBuffer.Position;
        this.hasReadNextLine = false;
        if (this.hasMoreData) {
            do {
                if (this.dataBuffer.Position == this.dataBuffer.Count) {
                    checkDataLength();
                } else {
                    DataBuffer dataBuffer;
                    this.startedWithQualifier = false;
                    char currentLetter = this.dataBuffer.Buffer[this.dataBuffer.Position];
                    boolean readingComplexEscape;
                    int escape;
                    int escapeLength;
                    char escapeValue;
                    if (this.userSettings.UseTextQualifier && currentLetter == this.userSettings.TextQualifier) {
                        this.lastLetter = currentLetter;
                        this.startedColumn = true;
                        this.dataBuffer.ColumnStart = this.dataBuffer.Position + ESCAPE_MODE_DOUBLED;
                        this.startedWithQualifier = true;
                        boolean lastLetterWasQualifier = false;
                        char escapeChar = this.userSettings.TextQualifier;
                        if (this.userSettings.EscapeMode == ESCAPE_MODE_BACKSLASH) {
                            escapeChar = Letters.BACKSLASH;
                        }
                        boolean eatingTrailingJunk = false;
                        boolean lastLetterWasEscape = false;
                        readingComplexEscape = false;
                        escape = ESCAPE_MODE_DOUBLED;
                        escapeLength = 0;
                        escapeValue = '\u0000';
                        dataBuffer = this.dataBuffer;
                        dataBuffer.Position += ESCAPE_MODE_DOUBLED;
                        do {
                            if (this.dataBuffer.Position == this.dataBuffer.Count) {
                                checkDataLength();
                            } else {
                                currentLetter = this.dataBuffer.Buffer[this.dataBuffer.Position];
                                if (eatingTrailingJunk) {
                                    this.dataBuffer.ColumnStart = this.dataBuffer.Position + ESCAPE_MODE_DOUBLED;
                                    if (currentLetter == this.userSettings.Delimiter) {
                                        endColumn();
                                    } else if ((!this.useCustomRecordDelimiter && (currentLetter == Letters.CR || currentLetter == '\n')) || (this.useCustomRecordDelimiter && currentLetter == this.userSettings.RecordDelimiter)) {
                                        endColumn();
                                        endRecord();
                                    }
                                } else if (readingComplexEscape) {
                                    escapeLength += ESCAPE_MODE_DOUBLED;
                                    switch (escape) {
                                        case ESCAPE_MODE_DOUBLED /*1*/:
                                            escapeValue = (char) (hexToDec(currentLetter) + ((char) (escapeValue * 16)));
                                            if (escapeLength == 4) {
                                                readingComplexEscape = false;
                                                break;
                                            }
                                            break;
                                        case ESCAPE_MODE_BACKSLASH /*2*/:
                                            escapeValue = (char) (((char) (currentLetter - 48)) + ((char) (escapeValue * 8)));
                                            if (escapeLength == 3) {
                                                readingComplexEscape = false;
                                                break;
                                            }
                                            break;
                                        case ValueServer.EXPONENTIAL_MODE /*3*/:
                                            escapeValue = (char) (((char) (currentLetter - 48)) + ((char) (escapeValue * 10)));
                                            if (escapeLength == 3) {
                                                readingComplexEscape = false;
                                                break;
                                            }
                                            break;
                                        case IExpr.DOUBLECOMPLEXID /*4*/:
                                            escapeValue = (char) (hexToDec(currentLetter) + ((char) (escapeValue * 16)));
                                            if (escapeLength == ESCAPE_MODE_BACKSLASH) {
                                                readingComplexEscape = false;
                                                break;
                                            }
                                            break;
                                    }
                                    if (readingComplexEscape) {
                                        this.dataBuffer.ColumnStart = this.dataBuffer.Position + ESCAPE_MODE_DOUBLED;
                                    } else {
                                        appendLetter(escapeValue);
                                    }
                                } else if (currentLetter == this.userSettings.TextQualifier) {
                                    if (lastLetterWasEscape) {
                                        lastLetterWasEscape = false;
                                        lastLetterWasQualifier = false;
                                    } else {
                                        updateCurrentValue();
                                        if (this.userSettings.EscapeMode == ESCAPE_MODE_DOUBLED) {
                                            lastLetterWasEscape = true;
                                        }
                                        lastLetterWasQualifier = true;
                                    }
                                } else if (this.userSettings.EscapeMode == ESCAPE_MODE_BACKSLASH && lastLetterWasEscape) {
                                    switch (currentLetter) {
                                        case R.styleable.AppCompatTheme_spinnerDropDownItemStyle /*48*/:
                                        case R.styleable.AppCompatTheme_homeAsUpIndicator /*49*/:
                                        case R.styleable.AppCompatTheme_actionButtonStyle /*50*/:
                                        case R.styleable.AppCompatTheme_buttonBarStyle /*51*/:
                                        case BlockRealMatrix.BLOCK_SIZE /*52*/:
                                        case R.styleable.AppCompatTheme_selectableItemBackground /*53*/:
                                        case R.styleable.AppCompatTheme_selectableItemBackgroundBorderless /*54*/:
                                        case R.styleable.AppCompatTheme_borderlessButtonStyle /*55*/:
                                            escape = ESCAPE_MODE_BACKSLASH;
                                            readingComplexEscape = true;
                                            escapeLength = ESCAPE_MODE_DOUBLED;
                                            escapeValue = (char) (currentLetter - 48);
                                            this.dataBuffer.ColumnStart = this.dataBuffer.Position + ESCAPE_MODE_DOUBLED;
                                            break;
                                        case R.styleable.AppCompatTheme_textColorSearchUrl /*68*/:
                                        case R.styleable.AppCompatTheme_panelBackground /*79*/:
                                        case R.styleable.AppCompatTheme_colorAccent /*85*/:
                                        case R.styleable.AppCompatTheme_colorControlHighlight /*88*/:
                                        case R.styleable.AppCompatTheme_buttonBarNeutralButtonStyle /*100*/:
                                        case R.styleable.AppCompatTheme_seekBarStyle /*111*/:
                                        case 'u':
                                        case 'x':
                                            switch (currentLetter) {
                                                case R.styleable.AppCompatTheme_textColorSearchUrl /*68*/:
                                                case R.styleable.AppCompatTheme_buttonBarNeutralButtonStyle /*100*/:
                                                    escape = 3;
                                                    break;
                                                case R.styleable.AppCompatTheme_panelBackground /*79*/:
                                                case R.styleable.AppCompatTheme_seekBarStyle /*111*/:
                                                    escape = ESCAPE_MODE_BACKSLASH;
                                                    break;
                                                case R.styleable.AppCompatTheme_colorAccent /*85*/:
                                                case 'u':
                                                    escape = ESCAPE_MODE_DOUBLED;
                                                    break;
                                                case R.styleable.AppCompatTheme_colorControlHighlight /*88*/:
                                                case 'x':
                                                    escape = 4;
                                                    break;
                                            }
                                            readingComplexEscape = true;
                                            escapeLength = 0;
                                            escapeValue = '\u0000';
                                            this.dataBuffer.ColumnStart = this.dataBuffer.Position + ESCAPE_MODE_DOUBLED;
                                            break;
                                        case R.styleable.AppCompatTheme_textColorAlertDialogListItem /*97*/:
                                            appendLetter('\u0007');
                                            break;
                                        case R.styleable.AppCompatTheme_buttonBarPositiveButtonStyle /*98*/:
                                            appendLetter('\b');
                                            break;
                                        case R.styleable.AppCompatTheme_autoCompleteTextViewStyle /*101*/:
                                            appendLetter(Letters.ESCAPE);
                                            break;
                                        case R.styleable.AppCompatTheme_buttonStyle /*102*/:
                                            appendLetter(Letters.FORM_FEED);
                                            break;
                                        case R.styleable.AppCompatTheme_ratingBarStyleSmall /*110*/:
                                            appendLetter('\n');
                                            break;
                                        case R.styleable.AppCompatTheme_listMenuViewStyle /*114*/:
                                            appendLetter(Letters.CR);
                                            break;
                                        case 't':
                                            appendLetter('\t');
                                            break;
                                        case 'v':
                                            appendLetter(Letters.VERTICAL_TAB);
                                            break;
                                    }
                                    lastLetterWasEscape = false;
                                } else if (currentLetter == escapeChar) {
                                    updateCurrentValue();
                                    lastLetterWasEscape = true;
                                } else if (lastLetterWasQualifier) {
                                    if (currentLetter == this.userSettings.Delimiter) {
                                        endColumn();
                                    } else if ((this.useCustomRecordDelimiter || !(currentLetter == Letters.CR || currentLetter == '\n')) && !(this.useCustomRecordDelimiter && currentLetter == this.userSettings.RecordDelimiter)) {
                                        this.dataBuffer.ColumnStart = this.dataBuffer.Position + ESCAPE_MODE_DOUBLED;
                                        eatingTrailingJunk = true;
                                    } else {
                                        endColumn();
                                        endRecord();
                                    }
                                    lastLetterWasQualifier = false;
                                }
                                this.lastLetter = currentLetter;
                                if (this.startedColumn) {
                                    dataBuffer = this.dataBuffer;
                                    dataBuffer.Position += ESCAPE_MODE_DOUBLED;
                                    if (this.userSettings.SafetySwitch && (this.dataBuffer.Position - this.dataBuffer.ColumnStart) + this.columnBuffer.Position > 100000) {
                                        close();
                                        throw new IOException("Maximum column length of 100,000 exceeded in column " + NumberFormat.getIntegerInstance().format((long) this.columnsCount) + " in record " + NumberFormat.getIntegerInstance().format(this.currentRecord) + ". Set the SafetySwitch property to false" + " if you're expecting column lengths greater than 100,000 characters to" + " avoid this error.");
                                    }
                                }
                            }
                            if (this.hasMoreData) {
                            }
                        } while (this.startedColumn);
                    } else if (currentLetter == this.userSettings.Delimiter) {
                        this.lastLetter = currentLetter;
                        endColumn();
                    } else if (this.useCustomRecordDelimiter && currentLetter == this.userSettings.RecordDelimiter) {
                        if (this.startedColumn || this.columnsCount > 0 || !this.userSettings.SkipEmptyRecords) {
                            endColumn();
                            endRecord();
                        } else {
                            this.dataBuffer.LineStart = this.dataBuffer.Position + ESCAPE_MODE_DOUBLED;
                        }
                        this.lastLetter = currentLetter;
                    } else if (!this.useCustomRecordDelimiter && (currentLetter == Letters.CR || currentLetter == '\n')) {
                        if (this.startedColumn || this.columnsCount > 0 || (!this.userSettings.SkipEmptyRecords && (currentLetter == Letters.CR || this.lastLetter != Letters.CR))) {
                            endColumn();
                            endRecord();
                        } else {
                            this.dataBuffer.LineStart = this.dataBuffer.Position + ESCAPE_MODE_DOUBLED;
                        }
                        this.lastLetter = currentLetter;
                    } else if (this.userSettings.UseComments && this.columnsCount == 0 && currentLetter == this.userSettings.Comment) {
                        this.lastLetter = currentLetter;
                        skipLine();
                    } else if (this.userSettings.TrimWhitespace && (currentLetter == Letters.SPACE || currentLetter == '\t')) {
                        this.startedColumn = true;
                        this.dataBuffer.ColumnStart = this.dataBuffer.Position + ESCAPE_MODE_DOUBLED;
                    } else {
                        this.startedColumn = true;
                        this.dataBuffer.ColumnStart = this.dataBuffer.Position;
                        boolean lastLetterWasBackslash = false;
                        readingComplexEscape = false;
                        escape = ESCAPE_MODE_DOUBLED;
                        escapeLength = 0;
                        escapeValue = '\u0000';
                        boolean firstLoop = true;
                        do {
                            if (firstLoop || this.dataBuffer.Position != this.dataBuffer.Count) {
                                if (!firstLoop) {
                                    currentLetter = this.dataBuffer.Buffer[this.dataBuffer.Position];
                                }
                                if (!this.userSettings.UseTextQualifier && this.userSettings.EscapeMode == ESCAPE_MODE_BACKSLASH && currentLetter == Letters.BACKSLASH) {
                                    if (lastLetterWasBackslash) {
                                        lastLetterWasBackslash = false;
                                    } else {
                                        updateCurrentValue();
                                        lastLetterWasBackslash = true;
                                    }
                                } else if (readingComplexEscape) {
                                    escapeLength += ESCAPE_MODE_DOUBLED;
                                    switch (escape) {
                                        case ESCAPE_MODE_DOUBLED /*1*/:
                                            escapeValue = (char) (hexToDec(currentLetter) + ((char) (escapeValue * 16)));
                                            if (escapeLength == 4) {
                                                readingComplexEscape = false;
                                                break;
                                            }
                                            break;
                                        case ESCAPE_MODE_BACKSLASH /*2*/:
                                            escapeValue = (char) (((char) (currentLetter - 48)) + ((char) (escapeValue * 8)));
                                            if (escapeLength == 3) {
                                                readingComplexEscape = false;
                                                break;
                                            }
                                            break;
                                        case ValueServer.EXPONENTIAL_MODE /*3*/:
                                            escapeValue = (char) (((char) (currentLetter - 48)) + ((char) (escapeValue * 10)));
                                            if (escapeLength == 3) {
                                                readingComplexEscape = false;
                                                break;
                                            }
                                            break;
                                        case IExpr.DOUBLECOMPLEXID /*4*/:
                                            escapeValue = (char) (hexToDec(currentLetter) + ((char) (escapeValue * 16)));
                                            if (escapeLength == ESCAPE_MODE_BACKSLASH) {
                                                readingComplexEscape = false;
                                                break;
                                            }
                                            break;
                                    }
                                    if (readingComplexEscape) {
                                        this.dataBuffer.ColumnStart = this.dataBuffer.Position + ESCAPE_MODE_DOUBLED;
                                    } else {
                                        appendLetter(escapeValue);
                                    }
                                } else if (this.userSettings.EscapeMode == ESCAPE_MODE_BACKSLASH && lastLetterWasBackslash) {
                                    switch (currentLetter) {
                                        case R.styleable.AppCompatTheme_spinnerDropDownItemStyle /*48*/:
                                        case R.styleable.AppCompatTheme_homeAsUpIndicator /*49*/:
                                        case R.styleable.AppCompatTheme_actionButtonStyle /*50*/:
                                        case R.styleable.AppCompatTheme_buttonBarStyle /*51*/:
                                        case BlockRealMatrix.BLOCK_SIZE /*52*/:
                                        case R.styleable.AppCompatTheme_selectableItemBackground /*53*/:
                                        case R.styleable.AppCompatTheme_selectableItemBackgroundBorderless /*54*/:
                                        case R.styleable.AppCompatTheme_borderlessButtonStyle /*55*/:
                                            escape = ESCAPE_MODE_BACKSLASH;
                                            readingComplexEscape = true;
                                            escapeLength = ESCAPE_MODE_DOUBLED;
                                            escapeValue = (char) (currentLetter - 48);
                                            this.dataBuffer.ColumnStart = this.dataBuffer.Position + ESCAPE_MODE_DOUBLED;
                                            break;
                                        case R.styleable.AppCompatTheme_textColorSearchUrl /*68*/:
                                        case R.styleable.AppCompatTheme_panelBackground /*79*/:
                                        case R.styleable.AppCompatTheme_colorAccent /*85*/:
                                        case R.styleable.AppCompatTheme_colorControlHighlight /*88*/:
                                        case R.styleable.AppCompatTheme_buttonBarNeutralButtonStyle /*100*/:
                                        case R.styleable.AppCompatTheme_seekBarStyle /*111*/:
                                        case 'u':
                                        case 'x':
                                            switch (currentLetter) {
                                                case R.styleable.AppCompatTheme_textColorSearchUrl /*68*/:
                                                case R.styleable.AppCompatTheme_buttonBarNeutralButtonStyle /*100*/:
                                                    escape = 3;
                                                    break;
                                                case R.styleable.AppCompatTheme_panelBackground /*79*/:
                                                case R.styleable.AppCompatTheme_seekBarStyle /*111*/:
                                                    escape = ESCAPE_MODE_BACKSLASH;
                                                    break;
                                                case R.styleable.AppCompatTheme_colorAccent /*85*/:
                                                case 'u':
                                                    escape = ESCAPE_MODE_DOUBLED;
                                                    break;
                                                case R.styleable.AppCompatTheme_colorControlHighlight /*88*/:
                                                case 'x':
                                                    escape = 4;
                                                    break;
                                            }
                                            readingComplexEscape = true;
                                            escapeLength = 0;
                                            escapeValue = '\u0000';
                                            this.dataBuffer.ColumnStart = this.dataBuffer.Position + ESCAPE_MODE_DOUBLED;
                                            break;
                                        case R.styleable.AppCompatTheme_textColorAlertDialogListItem /*97*/:
                                            appendLetter('\u0007');
                                            break;
                                        case R.styleable.AppCompatTheme_buttonBarPositiveButtonStyle /*98*/:
                                            appendLetter('\b');
                                            break;
                                        case R.styleable.AppCompatTheme_autoCompleteTextViewStyle /*101*/:
                                            appendLetter(Letters.ESCAPE);
                                            break;
                                        case R.styleable.AppCompatTheme_buttonStyle /*102*/:
                                            appendLetter(Letters.FORM_FEED);
                                            break;
                                        case R.styleable.AppCompatTheme_ratingBarStyleSmall /*110*/:
                                            appendLetter('\n');
                                            break;
                                        case R.styleable.AppCompatTheme_listMenuViewStyle /*114*/:
                                            appendLetter(Letters.CR);
                                            break;
                                        case 't':
                                            appendLetter('\t');
                                            break;
                                        case 'v':
                                            appendLetter(Letters.VERTICAL_TAB);
                                            break;
                                    }
                                    lastLetterWasBackslash = false;
                                } else if (currentLetter == this.userSettings.Delimiter) {
                                    endColumn();
                                } else if ((!this.useCustomRecordDelimiter && (currentLetter == Letters.CR || currentLetter == '\n')) || (this.useCustomRecordDelimiter && currentLetter == this.userSettings.RecordDelimiter)) {
                                    endColumn();
                                    endRecord();
                                }
                                this.lastLetter = currentLetter;
                                firstLoop = false;
                                if (this.startedColumn) {
                                    dataBuffer = this.dataBuffer;
                                    dataBuffer.Position += ESCAPE_MODE_DOUBLED;
                                    if (this.userSettings.SafetySwitch && (this.dataBuffer.Position - this.dataBuffer.ColumnStart) + this.columnBuffer.Position > 100000) {
                                        close();
                                        throw new IOException("Maximum column length of 100,000 exceeded in column " + NumberFormat.getIntegerInstance().format((long) this.columnsCount) + " in record " + NumberFormat.getIntegerInstance().format(this.currentRecord) + ". Set the SafetySwitch property to false" + " if you're expecting column lengths greater than 100,000 characters to" + " avoid this error.");
                                    }
                                }
                            }
                            checkDataLength();
                            if (this.hasMoreData) {
                            }
                        } while (this.startedColumn);
                    }
                    if (this.hasMoreData) {
                        dataBuffer = this.dataBuffer;
                        dataBuffer.Position += ESCAPE_MODE_DOUBLED;
                    }
                }
                if (this.hasMoreData) {
                }
                if (this.startedColumn || this.lastLetter == this.userSettings.Delimiter) {
                    endColumn();
                    endRecord();
                }
            } while (!this.hasReadNextLine);
            endColumn();
            endRecord();
        }
        if (!this.userSettings.CaptureRawRecord) {
            this.rawRecord = BuildConfig.FLAVOR;
        } else if (!this.hasMoreData) {
            this.rawRecord = new String(this.rawBuffer.Buffer, 0, this.rawBuffer.Position);
        } else if (this.rawBuffer.Position == 0) {
            this.rawRecord = new String(this.dataBuffer.Buffer, this.dataBuffer.LineStart, (this.dataBuffer.Position - this.dataBuffer.LineStart) - 1);
        } else {
            this.rawRecord = new String(this.rawBuffer.Buffer, 0, this.rawBuffer.Position) + new String(this.dataBuffer.Buffer, this.dataBuffer.LineStart, (this.dataBuffer.Position - this.dataBuffer.LineStart) - 1);
        }
        return this.hasReadNextLine;
    }

    private void checkDataLength() throws IOException {
        if (!this.initialized) {
            if (this.fileName != null) {
                this.inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(this.fileName), this.charset), StaticSettings.MAX_FILE_BUFFER_SIZE);
            }
            this.charset = null;
            this.initialized = true;
        }
        updateCurrentValue();
        if (this.userSettings.CaptureRawRecord && this.dataBuffer.Count > 0) {
            if (this.rawBuffer.Buffer.length - this.rawBuffer.Position < this.dataBuffer.Count - this.dataBuffer.LineStart) {
                char[] holder = new char[(this.rawBuffer.Buffer.length + Math.max(this.dataBuffer.Count - this.dataBuffer.LineStart, this.rawBuffer.Buffer.length))];
                System.arraycopy(this.rawBuffer.Buffer, 0, holder, 0, this.rawBuffer.Position);
                this.rawBuffer.Buffer = holder;
            }
            System.arraycopy(this.dataBuffer.Buffer, this.dataBuffer.LineStart, this.rawBuffer.Buffer, this.rawBuffer.Position, this.dataBuffer.Count - this.dataBuffer.LineStart);
            RawRecordBuffer rawRecordBuffer = this.rawBuffer;
            rawRecordBuffer.Position += this.dataBuffer.Count - this.dataBuffer.LineStart;
        }
        try {
            this.dataBuffer.Count = this.inputStream.read(this.dataBuffer.Buffer, 0, this.dataBuffer.Buffer.length);
            if (this.dataBuffer.Count == -1) {
                this.hasMoreData = false;
            }
            this.dataBuffer.Position = 0;
            this.dataBuffer.LineStart = 0;
            this.dataBuffer.ColumnStart = 0;
        } catch (IOException ex) {
            close();
            throw ex;
        }
    }

    public boolean readHeaders() throws IOException {
        boolean result = readRecord();
        this.headersHolder.Length = this.columnsCount;
        this.headersHolder.Headers = new String[this.columnsCount];
        for (int i = 0; i < this.headersHolder.Length; i += ESCAPE_MODE_DOUBLED) {
            String columnValue = get(i);
            this.headersHolder.Headers[i] = columnValue;
            this.headersHolder.IndexByName.put(columnValue, new Integer(i));
        }
        if (result) {
            this.currentRecord--;
        }
        this.columnsCount = 0;
        return result;
    }

    public String getHeader(int columnIndex) throws IOException {
        checkClosed();
        if (columnIndex <= -1 || columnIndex >= this.headersHolder.Length) {
            return BuildConfig.FLAVOR;
        }
        return this.headersHolder.Headers[columnIndex];
    }

    public boolean isQualified(int columnIndex) throws IOException {
        checkClosed();
        if (columnIndex >= this.columnsCount || columnIndex <= -1) {
            return false;
        }
        return this.isQualified[columnIndex];
    }

    private void endColumn() throws IOException {
        String currentValue = BuildConfig.FLAVOR;
        if (this.startedColumn) {
            int lastLetter;
            if (this.columnBuffer.Position != 0) {
                updateCurrentValue();
                lastLetter = this.columnBuffer.Position - 1;
                if (this.userSettings.TrimWhitespace && !this.startedWithQualifier) {
                    while (lastLetter >= 0 && (this.columnBuffer.Buffer[lastLetter] == Letters.SPACE || this.columnBuffer.Buffer[lastLetter] == Letters.SPACE)) {
                        lastLetter--;
                    }
                }
                currentValue = new String(this.columnBuffer.Buffer, 0, lastLetter + ESCAPE_MODE_DOUBLED);
            } else if (this.dataBuffer.ColumnStart < this.dataBuffer.Position) {
                lastLetter = this.dataBuffer.Position - 1;
                if (this.userSettings.TrimWhitespace && !this.startedWithQualifier) {
                    while (lastLetter >= this.dataBuffer.ColumnStart && (this.dataBuffer.Buffer[lastLetter] == Letters.SPACE || this.dataBuffer.Buffer[lastLetter] == '\t')) {
                        lastLetter--;
                    }
                }
                currentValue = new String(this.dataBuffer.Buffer, this.dataBuffer.ColumnStart, (lastLetter - this.dataBuffer.ColumnStart) + ESCAPE_MODE_DOUBLED);
            }
        }
        this.columnBuffer.Position = 0;
        this.startedColumn = false;
        if (this.columnsCount < 100000 || !this.userSettings.SafetySwitch) {
            if (this.columnsCount == this.values.length) {
                int newLength = this.values.length * ESCAPE_MODE_BACKSLASH;
                String[] holder = new String[newLength];
                System.arraycopy(this.values, 0, holder, 0, this.values.length);
                this.values = holder;
                boolean[] qualifiedHolder = new boolean[newLength];
                System.arraycopy(this.isQualified, 0, qualifiedHolder, 0, this.isQualified.length);
                this.isQualified = qualifiedHolder;
            }
            this.values[this.columnsCount] = currentValue;
            this.isQualified[this.columnsCount] = this.startedWithQualifier;
            currentValue = BuildConfig.FLAVOR;
            this.columnsCount += ESCAPE_MODE_DOUBLED;
            return;
        }
        close();
        throw new IOException("Maximum column count of 100,000 exceeded in record " + NumberFormat.getIntegerInstance().format(this.currentRecord) + ". Set the SafetySwitch property to false" + " if you're expecting more than 100,000 columns per record to" + " avoid this error.");
    }

    private void appendLetter(char letter) {
        if (this.columnBuffer.Position == this.columnBuffer.Buffer.length) {
            char[] holder = new char[(this.columnBuffer.Buffer.length * ESCAPE_MODE_BACKSLASH)];
            System.arraycopy(this.columnBuffer.Buffer, 0, holder, 0, this.columnBuffer.Position);
            this.columnBuffer.Buffer = holder;
        }
        char[] cArr = this.columnBuffer.Buffer;
        ColumnBuffer columnBuffer = this.columnBuffer;
        int i = columnBuffer.Position;
        columnBuffer.Position = i + ESCAPE_MODE_DOUBLED;
        cArr[i] = letter;
        this.dataBuffer.ColumnStart = this.dataBuffer.Position + ESCAPE_MODE_DOUBLED;
    }

    private void updateCurrentValue() {
        if (this.startedColumn && this.dataBuffer.ColumnStart < this.dataBuffer.Position) {
            if (this.columnBuffer.Buffer.length - this.columnBuffer.Position < this.dataBuffer.Position - this.dataBuffer.ColumnStart) {
                char[] holder = new char[(this.columnBuffer.Buffer.length + Math.max(this.dataBuffer.Position - this.dataBuffer.ColumnStart, this.columnBuffer.Buffer.length))];
                System.arraycopy(this.columnBuffer.Buffer, 0, holder, 0, this.columnBuffer.Position);
                this.columnBuffer.Buffer = holder;
            }
            System.arraycopy(this.dataBuffer.Buffer, this.dataBuffer.ColumnStart, this.columnBuffer.Buffer, this.columnBuffer.Position, this.dataBuffer.Position - this.dataBuffer.ColumnStart);
            ColumnBuffer columnBuffer = this.columnBuffer;
            columnBuffer.Position += this.dataBuffer.Position - this.dataBuffer.ColumnStart;
        }
        this.dataBuffer.ColumnStart = this.dataBuffer.Position + ESCAPE_MODE_DOUBLED;
    }

    private void endRecord() throws IOException {
        this.hasReadNextLine = true;
        this.currentRecord++;
    }

    public int getIndex(String headerName) throws IOException {
        checkClosed();
        Object indexValue = this.headersHolder.IndexByName.get(headerName);
        if (indexValue != null) {
            return ((Integer) indexValue).intValue();
        }
        return -1;
    }

    public boolean skipRecord() throws IOException {
        checkClosed();
        boolean recordRead = false;
        if (this.hasMoreData) {
            recordRead = readRecord();
            if (recordRead) {
                this.currentRecord--;
            }
        }
        return recordRead;
    }

    public boolean skipLine() throws IOException {
        checkClosed();
        this.columnsCount = 0;
        boolean skippedLine = false;
        if (this.hasMoreData) {
            boolean foundEol = false;
            do {
                if (this.dataBuffer.Position == this.dataBuffer.Count) {
                    checkDataLength();
                } else {
                    skippedLine = true;
                    char currentLetter = this.dataBuffer.Buffer[this.dataBuffer.Position];
                    if (currentLetter == Letters.CR || currentLetter == '\n') {
                        foundEol = true;
                    }
                    this.lastLetter = currentLetter;
                    if (!foundEol) {
                        DataBuffer dataBuffer = this.dataBuffer;
                        dataBuffer.Position += ESCAPE_MODE_DOUBLED;
                    }
                }
                if (!this.hasMoreData) {
                    break;
                }
            } while (!foundEol);
            this.columnBuffer.Position = 0;
            this.dataBuffer.LineStart = this.dataBuffer.Position + ESCAPE_MODE_DOUBLED;
        }
        this.rawBuffer.Position = 0;
        this.rawRecord = BuildConfig.FLAVOR;
        return skippedLine;
    }

    public void close() {
        if (!this.closed) {
            close(true);
            this.closed = true;
        }
    }

    private void close(boolean closing) {
        if (!this.closed) {
            if (closing) {
                this.charset = null;
                this.headersHolder.Headers = null;
                this.headersHolder.IndexByName = null;
                this.dataBuffer.Buffer = null;
                this.columnBuffer.Buffer = null;
                this.rawBuffer.Buffer = null;
            }
            try {
                if (this.initialized) {
                    this.inputStream.close();
                }
            } catch (Exception e) {
            }
            this.inputStream = null;
            this.closed = true;
        }
    }

    private void checkClosed() throws IOException {
        if (this.closed) {
            throw new IOException("This instance of the CsvReader class has already been closed.");
        }
    }

    protected void finalize() {
        close(false);
    }

    private static char hexToDec(char hex) {
        if (hex >= 'a') {
            return (char) ((hex - 97) + 10);
        }
        if (hex >= 'A') {
            return (char) ((hex - 65) + 10);
        }
        return (char) (hex - 48);
    }
}
