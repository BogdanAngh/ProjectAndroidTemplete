package io.github.kexanie.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.webkit.WebView;
import com.x5.template.Chunk;
import com.x5.template.ContentSource;
import com.x5.template.Theme;
import com.x5.template.providers.AndroidTemplates;
import org.apache.commons.math4.random.ValueServer;

public class MathView extends WebView {
    private int mEngine;
    private String mText;

    public static class Engine {
        public static final int KATEX = 0;
        public static final int MATHJAX = 1;
    }

    public MathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setCacheMode(2);
        setBackgroundColor(0);
        TypedArray mTypeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MathView, 0, 0);
        try {
            setEngine(mTypeArray.getInteger(R.styleable.MathView_engine, 0));
            setText(mTypeArray.getString(R.styleable.MathView_text));
        } finally {
            mTypeArray.recycle();
        }
    }

    private Chunk getChunk() {
        String TEMPLATE_KATEX = "katex";
        String TEMPLATE_MATHJAX = "mathjax";
        String template = TEMPLATE_KATEX;
        ContentSource loader = new AndroidTemplates(getContext());
        switch (this.mEngine) {
            case ValueServer.DIGEST_MODE /*0*/:
                template = TEMPLATE_KATEX;
                break;
            case ValueServer.REPLAY_MODE /*1*/:
                template = TEMPLATE_MATHJAX;
                break;
        }
        return new Theme(loader).makeChunk(template);
    }

    public void setText(String text) {
        this.mText = text;
        Chunk chunk = getChunk();
        chunk.set("formula", this.mText);
        loadDataWithBaseURL(null, chunk.toString(), "text/html", "utf-8", "about:blank");
    }

    public String getText() {
        return this.mText;
    }

    public void setEngine(int engine) {
        switch (engine) {
            case ValueServer.DIGEST_MODE /*0*/:
                this.mEngine = 0;
            case ValueServer.REPLAY_MODE /*1*/:
                this.mEngine = 1;
            default:
                this.mEngine = 0;
        }
    }
}
