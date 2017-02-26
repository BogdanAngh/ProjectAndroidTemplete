package im.delight.apprater;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Build.VERSION;

public class AppRater {
    private static final int DEFAULT_DAYS_BEFORE_PROMPT = 2;
    private static final int DEFAULT_LAUNCHES_BEFORE_PROMPT = 5;
    private static final String DEFAULT_PREFERENCE_DONT_SHOW = "flag_dont_show";
    private static final String DEFAULT_PREFERENCE_FIRST_LAUNCH = "first_launch_time";
    private static final String DEFAULT_PREFERENCE_LAUNCH_COUNT = "launch_count";
    private static final String DEFAULT_PREF_GROUP = "app_rater";
    private static final String DEFAULT_TARGET_URI = "market://details?id=%s";
    private static final String DEFAULT_TEXT_EXPLANATION = "Has this app convinced you? We would be very happy if you could rate our application on Google Play. Thanks for your support!";
    private static final String DEFAULT_TEXT_LATER = "Later";
    private static final String DEFAULT_TEXT_NEVER = "No, thanks";
    private static final String DEFAULT_TEXT_NOW = "Rate now";
    private static final String DEFAULT_TEXT_TITLE = "Rate this app";
    private Context mContext;
    private int mDaysBeforePrompt;
    private int mLaunchesBeforePrompt;
    private String mPackageName;
    private String mPrefGroup;
    private String mPreference_dontShow;
    private String mPreference_firstLaunch;
    private String mPreference_launchCount;
    private Intent mTargetIntent;
    private String mTargetUri;
    private String mText_buttonLater;
    private String mText_buttonNever;
    private String mText_buttonNow;
    private String mText_explanation;
    private String mText_title;

    /* renamed from: im.delight.apprater.AppRater.1 */
    class C03271 implements OnClickListener {
        private final /* synthetic */ Editor val$editor;
        private final /* synthetic */ long val$firstLaunchTime;

        C03271(Editor editor, long j) {
            this.val$editor = editor;
            this.val$firstLaunchTime = j;
        }

        public void onClick(DialogInterface dialog, int which) {
            AppRater.this.buttonLaterClick(this.val$editor, dialog, this.val$firstLaunchTime);
        }
    }

    /* renamed from: im.delight.apprater.AppRater.2 */
    class C03282 implements OnClickListener {
        private final /* synthetic */ Context val$context;
        private final /* synthetic */ Editor val$editor;

        C03282(Editor editor, Context context) {
            this.val$editor = editor;
            this.val$context = context;
        }

        public void onClick(DialogInterface dialog, int which) {
            AppRater.this.buttonNowClick(this.val$editor, dialog, this.val$context);
        }
    }

    /* renamed from: im.delight.apprater.AppRater.3 */
    class C03293 implements OnClickListener {
        private final /* synthetic */ Editor val$editor;

        C03293(Editor editor) {
            this.val$editor = editor;
        }

        public void onClick(DialogInterface dialog, int which) {
            AppRater.this.buttonNeverClick(this.val$editor, dialog);
        }
    }

    public AppRater(Context context) {
        this(context, context.getPackageName());
    }

    public AppRater(Context context, String packageName) {
        if (context == null) {
            throw new RuntimeException("context may not be null");
        }
        this.mContext = context;
        this.mPackageName = packageName;
        this.mDaysBeforePrompt = DEFAULT_DAYS_BEFORE_PROMPT;
        this.mLaunchesBeforePrompt = DEFAULT_LAUNCHES_BEFORE_PROMPT;
        this.mTargetUri = DEFAULT_TARGET_URI;
        this.mText_title = DEFAULT_TEXT_TITLE;
        this.mText_explanation = DEFAULT_TEXT_EXPLANATION;
        this.mText_buttonNow = DEFAULT_TEXT_NOW;
        this.mText_buttonLater = DEFAULT_TEXT_LATER;
        this.mText_buttonNever = DEFAULT_TEXT_NEVER;
        this.mPrefGroup = DEFAULT_PREF_GROUP;
        this.mPreference_dontShow = DEFAULT_PREFERENCE_DONT_SHOW;
        this.mPreference_launchCount = DEFAULT_PREFERENCE_LAUNCH_COUNT;
        this.mPreference_firstLaunch = DEFAULT_PREFERENCE_FIRST_LAUNCH;
    }

    public void setDaysBeforePrompt(int days) {
        this.mDaysBeforePrompt = days;
    }

    public void setLaunchesBeforePrompt(int launches) {
        this.mLaunchesBeforePrompt = launches;
    }

    public void setTargetUri(String uri) {
        this.mTargetUri = uri;
    }

    public void setPhrases(String title, String explanation, String buttonNow, String buttonLater, String buttonNever) {
        this.mText_title = title;
        this.mText_explanation = explanation;
        this.mText_buttonNow = buttonNow;
        this.mText_buttonLater = buttonLater;
        this.mText_buttonNever = buttonNever;
    }

    public void setPhrases(int title, int explanation, int buttonNow, int buttonLater, int buttonNever) {
        try {
            this.mText_title = this.mContext.getString(title);
        } catch (Exception e) {
            this.mText_title = DEFAULT_TEXT_TITLE;
        }
        try {
            this.mText_explanation = this.mContext.getString(explanation);
        } catch (Exception e2) {
            this.mText_explanation = DEFAULT_TEXT_EXPLANATION;
        }
        try {
            this.mText_buttonNow = this.mContext.getString(buttonNow);
        } catch (Exception e3) {
            this.mText_buttonNow = DEFAULT_TEXT_NOW;
        }
        try {
            this.mText_buttonLater = this.mContext.getString(buttonLater);
        } catch (Exception e4) {
            this.mText_buttonLater = DEFAULT_TEXT_LATER;
        }
        try {
            this.mText_buttonNever = this.mContext.getString(buttonNever);
        } catch (Exception e5) {
            this.mText_buttonNever = DEFAULT_TEXT_NEVER;
        }
    }

    public void setPreferenceKeys(String group, String dontShow, String launchCount, String firstLaunchTime) {
        this.mPrefGroup = group;
        this.mPreference_dontShow = dontShow;
        this.mPreference_launchCount = launchCount;
        this.mPreference_firstLaunch = firstLaunchTime;
    }

    private void createTargetIntent() {
        this.mTargetIntent = new Intent("android.intent.action.VIEW", Uri.parse(String.format(this.mTargetUri, new Object[]{this.mPackageName})));
    }

    @SuppressLint({"CommitPrefEdits"})
    public AlertDialog show() {
        AlertDialog alertDialog = null;
        createTargetIntent();
        if (this.mContext.getPackageManager().queryIntentActivities(this.mTargetIntent, 0).size() > 0) {
            SharedPreferences prefs = this.mContext.getSharedPreferences(this.mPrefGroup, 0);
            Editor editor = prefs.edit();
            if (!prefs.getBoolean(this.mPreference_dontShow, false)) {
                long launch_count = prefs.getLong(this.mPreference_launchCount, 0) + 1;
                editor.putLong(this.mPreference_launchCount, launch_count);
                long firstLaunchTime = prefs.getLong(this.mPreference_firstLaunch, 0);
                if (firstLaunchTime == 0) {
                    firstLaunchTime = System.currentTimeMillis();
                    editor.putLong(this.mPreference_firstLaunch, firstLaunchTime);
                }
                savePreferences(editor);
                if (launch_count >= ((long) this.mLaunchesBeforePrompt) && System.currentTimeMillis() >= (((long) this.mDaysBeforePrompt) * 86400000) + firstLaunchTime) {
                    try {
                        alertDialog = showDialog(this.mContext, editor, firstLaunchTime);
                    } catch (Exception e) {
                    }
                }
            }
        }
        return alertDialog;
    }

    @Deprecated
    public AlertDialog demo() {
        createTargetIntent();
        return showDialog(this.mContext, this.mContext.getSharedPreferences(this.mPrefGroup, 0).edit(), System.currentTimeMillis());
    }

    @SuppressLint({"NewApi"})
    private static void savePreferences(Editor editor) {
        if (editor == null) {
            return;
        }
        if (VERSION.SDK_INT < 9) {
            editor.commit();
        } else {
            editor.apply();
        }
    }

    private static void closeDialog(DialogInterface dialog) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private void setDontShow(Editor editor) {
        if (editor != null) {
            editor.putBoolean(this.mPreference_dontShow, true);
            savePreferences(editor);
        }
    }

    private void setFirstLaunchTime(Editor editor, long time) {
        if (editor != null) {
            editor.putLong(this.mPreference_firstLaunch, time);
            savePreferences(editor);
        }
    }

    private void buttonNowClick(Editor editor, DialogInterface dialog, Context context) {
        setDontShow(editor);
        closeDialog(dialog);
        context.startActivity(this.mTargetIntent);
    }

    private void buttonLaterClick(Editor editor, DialogInterface dialog, long firstLaunchTime) {
        setFirstLaunchTime(editor, 86400000 + firstLaunchTime);
        closeDialog(dialog);
    }

    private void buttonNeverClick(Editor editor, DialogInterface dialog) {
        setDontShow(editor);
        closeDialog(dialog);
    }

    private AlertDialog showDialog(Context context, Editor editor, long firstLaunchTime) {
        Builder rateDialog = new Builder(context);
        rateDialog.setTitle(this.mText_title);
        rateDialog.setMessage(this.mText_explanation);
        rateDialog.setNeutralButton(this.mText_buttonLater, new C03271(editor, firstLaunchTime));
        rateDialog.setPositiveButton(this.mText_buttonNow, new C03282(editor, context));
        rateDialog.setNegativeButton(this.mText_buttonNever, new C03293(editor));
        return rateDialog.show();
    }
}
