package com.example.duy.calculator.view;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView.Tokenizer;
import com.example.duy.calculator.utils.VariableUtil;
import io.github.kexanie.library.R;

public class AutoCompleteFunctionEditText extends MultiAutoCompleteTextView {

    public class SpaceTokenizer implements Tokenizer {
        public int findTokenStart(CharSequence text, int cursor) {
            int i = cursor;
            while (i > 0 && text.charAt(i - 1) != Letters.SPACE) {
                i--;
            }
            while (i < cursor && text.charAt(i) == Letters.SPACE) {
                i++;
            }
            return i;
        }

        public int findTokenEnd(CharSequence text, int cursor) {
            int len = text.length();
            for (int i = cursor; i < len; i++) {
                if (text.charAt(i) == Letters.SPACE) {
                    return i;
                }
            }
            return len;
        }

        public CharSequence terminateToken(CharSequence text) {
            int i = text.length();
            while (i > 0 && text.charAt(i - 1) == Letters.SPACE) {
                i--;
            }
            if (i > 0 && text.charAt(i - 1) == Letters.SPACE) {
                return text;
            }
            if (!(text instanceof Spanned)) {
                return text + " ";
            }
            CharSequence sp = new SpannableString(text + " ");
            TextUtils.copySpansFrom((Spanned) text, 0, text.length(), Object.class, sp, 0);
            return sp;
        }
    }

    public AutoCompleteFunctionEditText(Context context) {
        super(context);
        init(context);
    }

    public AutoCompleteFunctionEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutoCompleteFunctionEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        ArrayAdapter<String> mAdapter = new ArrayAdapter(context, 17367057, VariableUtil.getListFunction(context));
        setTokenizer(new SpaceTokenizer());
        setThreshold(1);
        setAdapter(mAdapter);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
        int color = typedValue.data;
    }
}
