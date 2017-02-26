package com.mp3download.zingmp3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.provider.MediaStore.Images.Media;
import android.provider.Settings.System;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnCloseListener;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.exoplayer.C0989C;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity implements OnQueryTextListener, OnCloseListener {
    public static MediaPlayer mp;
    public static ProgressDialog mydialog;
    public customAdapterListMusics adapterListMusics;
    private ProgressDialog barProgressDialog;
    public String currentimagepath;
    Holder lastPlayed;
    private SearchView mSearchView;
    private Tracker mTracker;
    public ArrayList<String> musicAuthor;
    public ArrayList<String> musicName;
    public ArrayList<String> musicTime;
    public ArrayList<String> musicURL;
    public ListView mylist;
    private View view;

    class DownloadFileAsync extends AsyncTask<String, String, String> {
        DownloadFileAsync() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity.this.barProgressDialog = new ProgressDialog(MainActivity.this);
            MainActivity.this.barProgressDialog.setTitle("Downloading MP3 ...");
            MainActivity.this.barProgressDialog.setMessage("Download in progress ...");
            ProgressDialog access$000 = MainActivity.this.barProgressDialog;
            MainActivity.this.barProgressDialog;
            access$000.setProgressStyle(1);
            MainActivity.this.barProgressDialog.setProgress(0);
            MainActivity.this.barProgressDialog.setMax(100);
            MainActivity.this.barProgressDialog.show();
            MainActivity.this.barProgressDialog.setCancelable(false);
        }

        protected String doInBackground(String... aurl) {
            try {
                OutputStream output;
                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();
                int lenghtOfFile = conexion.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream());
                File folder;
                if (Boolean.valueOf(Environment.getExternalStorageState().equals("mounted")).booleanValue()) {
                    folder = new File(Environment.getExternalStorageDirectory() + File.separator + "Mp3Download");
                    if (!folder.exists()) {
                        folder.mkdir();
                    }
                    MainActivity.this.currentimagepath = Environment.getExternalStorageDirectory() + File.separator + "Mp3Download/" + aurl[1];
                    output = new FileOutputStream(MainActivity.this.currentimagepath);
                } else {
                    folder = new File(MainActivity.this.getApplication().getFilesDir() + File.separator + "Mp3Download/" + aurl[1]);
                    if (!folder.exists()) {
                        folder.mkdir();
                    }
                    MainActivity.this.currentimagepath = MainActivity.this.getApplication().getFilesDir() + File.separator + "Mp3Download/" + aurl[1];
                    output = new FileOutputStream(MainActivity.this.currentimagepath);
                }
                byte[] data = new byte[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT];
                long total = 0;
                while (true) {
                    int count = input.read(data);
                    if (count == -1) {
                        break;
                    }
                    total += (long) count;
                    MainActivity.this.barProgressDialog.setProgress((int) ((100 * total) / ((long) lenghtOfFile)));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
            }
            return null;
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MainActivity.this.barProgressDialog.dismiss();
            MainActivity.this.showNotification(MainActivity.this.currentimagepath);
        }
    }

    class customAdapterListMusics extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        ArrayList<String> listmusicURL;
        ArrayList<String> listmusicauthor;
        ArrayList<String> listmusicname;
        ArrayList<String> listmusictime;

        /* renamed from: com.mp3download.zingmp3.MainActivity.customAdapterListMusics.1 */
        class C15671 implements OnClickListener {
            final /* synthetic */ int val$position;

            C15671(int i) {
                this.val$position = i;
            }

            public void onClick(View view) {
                MainActivity.this.startDownload((String) customAdapterListMusics.this.listmusicURL.get(this.val$position), ((String) customAdapterListMusics.this.listmusicname.get(this.val$position)).toString() + ".mp3");
            }
        }

        /* renamed from: com.mp3download.zingmp3.MainActivity.customAdapterListMusics.2 */
        class C15682 implements OnClickListener {
            final /* synthetic */ Holder val$holder;
            final /* synthetic */ int val$position;

            C15682(Holder holder, int i) {
                this.val$holder = holder;
                this.val$position = i;
            }

            public void onClick(View view) {
                MainActivity.this.mSearchView.setIconified(false);
                MainActivity.this.mSearchView.clearFocus();
                if (MainActivity.mp.isPlaying()) {
                    MainActivity.stopMusic();
                    if (this.val$holder.equals(MainActivity.this.lastPlayed)) {
                        MainActivity.this.lastPlayed.btn_play_stop.setBackgroundResource(C1569R.drawable.stop);
                        this.val$holder.btn_play_stop.setBackgroundResource(C1569R.drawable.play);
                    } else {
                        MainActivity.this.lastPlayed.btn_play_stop.setBackgroundResource(C1569R.drawable.play);
                        MainActivity.playMusic(((String) customAdapterListMusics.this.listmusicURL.get(this.val$position)).toString());
                        this.val$holder.btn_play_stop.setBackgroundResource(C1569R.drawable.stop);
                    }
                } else {
                    MainActivity.this.lastPlayed.btn_play_stop.setBackgroundResource(C1569R.drawable.play);
                    MainActivity.playMusic(((String) customAdapterListMusics.this.listmusicURL.get(this.val$position)).toString());
                    this.val$holder.btn_play_stop.setBackgroundResource(C1569R.drawable.stop);
                }
                MainActivity.this.lastPlayed = this.val$holder;
            }
        }

        public class Holder {
            ImageButton btn_download;
            ImageButton btn_play_stop;
            TextView txt_music_author;
            TextView txt_music_name;
            TextView txt_music_time;
        }

        public customAdapterListMusics(Context cnt, ArrayList<String> listmusic, ArrayList<String> listmusicname, ArrayList<String> listmusicauthor, ArrayList<String> listmusictime) {
            this.inflater = null;
            this.context = cnt;
            this.listmusicURL = listmusic;
            this.listmusicname = listmusicname;
            this.listmusicauthor = listmusicauthor;
            this.listmusictime = listmusictime;
            this.inflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
        }

        public int getCount() {
            return this.listmusicURL.size();
        }

        public Object getItem(int position) {
            return Integer.valueOf(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();
            View rowView = this.inflater.inflate(C1569R.layout.list_item_row, null);
            holder.txt_music_name = (TextView) rowView.findViewById(C1569R.id.songtext);
            holder.txt_music_author = (TextView) rowView.findViewById(C1569R.id.authortext);
            holder.txt_music_time = (TextView) rowView.findViewById(C1569R.id.musictime);
            holder.btn_play_stop = (ImageButton) rowView.findViewById(C1569R.id.playstop);
            holder.btn_download = (ImageButton) rowView.findViewById(C1569R.id.downbtn);
            holder.txt_music_name.setText(((String) this.listmusicname.get(position)).toString().trim());
            holder.txt_music_author.setText(((String) this.listmusicauthor.get(position)).toString().trim());
            holder.txt_music_time.setText(((String) this.listmusictime.get(position)).toString().trim());
            MainActivity.this.lastPlayed = holder;
            holder.btn_download.setOnClickListener(new C15671(position));
            holder.btn_play_stop.setOnClickListener(new C15682(holder, position));
            return rowView;
        }
    }

    static class task extends AsyncTask<String, Void, Void> {
        task() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity.mydialog.setMessage("Playing...");
            MainActivity.mydialog.show();
        }

        protected Void doInBackground(String... strings) {
            try {
                if (MainActivity.mp.isPlaying()) {
                    MainActivity.stopMusic();
                }
                MainActivity.mp.setDataSource(strings[0]);
                MainActivity.mp.prepare();
                MainActivity.mp.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (MainActivity.mydialog.isShowing()) {
                MainActivity.mydialog.dismiss();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1569R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());
        this.view = getWindow().getDecorView().getRootView();
        FacebookAds.facebookLoadBanner(getApplicationContext(), this.view);
        FacebookAds.facebookInterstitialAd(this);
        this.mTracker = ((GoogleAnalyticsApplication) getApplication()).getDefaultTracker();
        this.mylist = (ListView) findViewById(C1569R.id.listView);
        mydialog = new ProgressDialog(this);
        mp = new MediaPlayer();
        mp.setAudioStreamType(3);
        StrictMode.setThreadPolicy(new Builder().permitAll().build());
        if (VERSION.SDK_INT >= 23 && !System.canWrite(this)) {
            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 2909);
        }
    }

    private void startDownload(String url, String filename) {
        new DownloadFileAsync().execute(new String[]{url, filename});
    }

    public boolean onClose() {
        return false;
    }

    public boolean onQueryTextSubmit(String query) {
        SearchMusic(query);
        return false;
    }

    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C1569R.menu.search_music, menu);
        this.mSearchView = (SearchView) menu.findItem(C1569R.id.action_search).getActionView();
        this.mSearchView.setQueryHint("Enter your mp3 name ...");
        setupSearchView();
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == C1569R.id.action_search) {
            return true;
        }
        if (id == C1569R.id.folder) {
            stopMusic();
            startActivity(new Intent(this, ListMusicActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void showNotification(String filepath) {
        Uri soundUri = RingtoneManager.getDefaultUri(2);
        Intent intentnatif = new Intent("android.intent.action.VIEW", Uri.parse(filepath));
        intentnatif.setDataAndType(Uri.parse("file://" + filepath), "audio/mp3");
        ((NotificationManager) getSystemService("notification")).notify(0, new Notification.Builder(this).setContentTitle("Successful mp3 file download !").setContentText("Download directory 'Mp3Download'.Click to open mp3 !").setSmallIcon(17301589).setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, intentnatif, 0)).setSound(soundUri).build());
    }

    public void openFolder(String filePath) {
        File file = new File(filePath);
        Intent intent = new Intent("android.intent.action.VIEW", Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(Uri.fromFile(file), "audio/mp3");
        startActivity(Intent.createChooser(intent, BuildConfig.FLAVOR));
    }

    public void SearchMusic(String search) {
        try {
            Elements url_content = Jsoup.connect(decodeString("aHR0cDovL21wM3BuLm5ldC9zZWFyY2gvcy9mLw==") + search.replace(" ", "+") + "/").get().select("div#xbody").select("li.cplayer-sound-item");
            Elements mp3_time_content = url_content.select("em.cplayer-data-sound-time");
            Elements music_name_content = url_content.select("a[href]").select("b.cplayer-data-sound-title");
            Elements music_author_content = url_content.select("a[href]").select("i.cplayer-data-sound-author");
            this.musicURL = null;
            this.musicURL = new ArrayList();
            this.musicName = null;
            this.musicName = new ArrayList();
            this.musicAuthor = null;
            this.musicAuthor = new ArrayList();
            this.musicTime = null;
            this.musicTime = new ArrayList();
            Iterator it = url_content.iterator();
            while (it.hasNext()) {
                this.musicURL.add(((Element) it.next()).attr("data-download-url").toString().trim());
            }
            it = mp3_time_content.iterator();
            while (it.hasNext()) {
                this.musicTime.add(((Element) it.next()).text().toString().trim());
            }
            it = music_name_content.iterator();
            while (it.hasNext()) {
                this.musicName.add(((Element) it.next()).text().trim());
            }
            it = music_author_content.iterator();
            while (it.hasNext()) {
                this.musicAuthor.add(((Element) it.next()).text().trim());
            }
            this.adapterListMusics = new customAdapterListMusics(getApplication(), this.musicURL, this.musicName, this.musicAuthor, this.musicTime);
            this.mylist.setAdapter(this.adapterListMusics);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupSearchView() {
        this.mSearchView.setIconifiedByDefault(true);
        SearchManager searchManager = (SearchManager) getSystemService("search");
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();
            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
            for (SearchableInfo inf : searchables) {
                if (inf.getSuggestAuthority() != null && inf.getSuggestAuthority().startsWith("applications")) {
                    info = inf;
                }
            }
            this.mSearchView.setSearchableInfo(info);
        }
        this.mSearchView.setOnQueryTextListener(this);
        this.mSearchView.setOnCloseListener(this);
        this.mSearchView.setIconifiedByDefault(true);
        this.mSearchView.setFocusable(true);
        this.mSearchView.setIconified(false);
        this.mSearchView.requestFocusFromTouch();
    }

    private String decodeString(String encoded) {
        byte[] dataDec = Base64.decode(encoded, 0);
        String decodedString = BuildConfig.FLAVOR;
        try {
            String decodedString2 = new String(dataDec, C0989C.UTF8_NAME);
            return decodedString2;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return decodedString;
        } catch (Throwable th) {
            return decodedString;
        }
    }

    public static void playMusic(String url) {
        stopMusic();
        new task().execute(new String[]{url});
    }

    public static void stopMusic() {
        mp.stop();
        mp.reset();
    }

    protected void onResume() {
        super.onResume();
        this.mTracker.setScreenName("MP3 Arama Ekran");
        this.mTracker.send(new ScreenViewBuilder().build());
    }

    public void onBackPressed() {
        super.onBackPressed();
        stopMusic();
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}
