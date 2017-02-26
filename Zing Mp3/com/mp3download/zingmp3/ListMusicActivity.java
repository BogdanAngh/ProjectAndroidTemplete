package com.mp3download.zingmp3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.exoplayer.hls.HlsChunkSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ListMusicActivity extends Activity {
    public Holder lastPlayed;
    public ArrayList<String> listMusicName;
    public ArrayList<String> listMusicNameCont;
    public ArrayList<String> listMusicTime;
    public MediaPlayer mp;
    public ProgressDialog mydialog;
    public ListView mylist;

    class customAdapterListMusics extends BaseAdapter {
        Context context;
        private LayoutInflater inflater;
        ArrayList<String> listmusicURL;
        ArrayList<String> listmusicauthor;
        ArrayList<String> listmusicname;
        ArrayList<String> listmusictime;

        /* renamed from: com.mp3download.zingmp3.ListMusicActivity.customAdapterListMusics.1 */
        class C15661 implements OnClickListener {
            final /* synthetic */ Holder val$holder;
            final /* synthetic */ int val$position;

            C15661(Holder holder, int i) {
                this.val$holder = holder;
                this.val$position = i;
            }

            public void onClick(View view) {
                if (ListMusicActivity.this.mp.isPlaying()) {
                    ListMusicActivity.this.stopMusic();
                    if (this.val$holder.equals(ListMusicActivity.this.lastPlayed)) {
                        this.val$holder.btn_play_stop.setBackgroundResource(C1569R.drawable.play);
                    } else {
                        ListMusicActivity.this.lastPlayed.btn_play_stop.setBackgroundResource(C1569R.drawable.play);
                        ListMusicActivity.this.playMusic(((String) customAdapterListMusics.this.listmusicURL.get(this.val$position)).toString());
                        this.val$holder.btn_play_stop.setBackgroundResource(C1569R.drawable.stop);
                    }
                    ListMusicActivity.this.lastPlayed = this.val$holder;
                    return;
                }
                ListMusicActivity.this.lastPlayed = this.val$holder;
                ListMusicActivity.this.playMusic(((String) customAdapterListMusics.this.listmusicURL.get(this.val$position)).toString());
                this.val$holder.btn_play_stop.setBackgroundResource(C1569R.drawable.stop);
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
            View rowView = this.inflater.inflate(C1569R.layout.list_item_row_2, null);
            holder.txt_music_name = (TextView) rowView.findViewById(C1569R.id.songtext);
            holder.txt_music_author = (TextView) rowView.findViewById(C1569R.id.authortext);
            holder.txt_music_time = (TextView) rowView.findViewById(C1569R.id.musictime);
            holder.btn_play_stop = (ImageButton) rowView.findViewById(C1569R.id.playstop);
            holder.txt_music_name.setText(((String) this.listmusicname.get(position)).toString().trim());
            holder.txt_music_author.setText(((String) this.listmusicauthor.get(position)).toString().trim());
            holder.txt_music_time.setText(((String) this.listmusictime.get(position)).toString().trim());
            holder.btn_play_stop.setOnClickListener(new C15661(holder, position));
            return rowView;
        }
    }

    class task extends AsyncTask<String, Void, Void> {
        task() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            ListMusicActivity.this.mydialog.setMessage("Playing...");
            ListMusicActivity.this.mydialog.show();
            ListMusicActivity.this.mydialog.setCancelable(false);
        }

        protected Void doInBackground(String... strings) {
            try {
                if (ListMusicActivity.this.mp.isPlaying()) {
                    ListMusicActivity.this.stopMusic();
                }
                ListMusicActivity.this.mp.setDataSource(strings[0]);
                ListMusicActivity.this.mp.prepare();
                ListMusicActivity.this.mp.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (ListMusicActivity.this.mydialog.isShowing()) {
                ListMusicActivity.this.mydialog.dismiss();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1569R.layout.list_music_activity);
        this.mylist = (ListView) findViewById(C1569R.id.liste);
        this.mp = new MediaPlayer();
        this.mydialog = new ProgressDialog(this);
        this.listMusicName = new ArrayList();
        this.listMusicTime = new ArrayList();
        this.listMusicNameCont = new ArrayList();
        getAllMusics(pathControl());
        this.mylist.setAdapter(new customAdapterListMusics(getApplication(), getAllMusics(pathControl()), this.listMusicNameCont, this.listMusicName, this.listMusicTime));
        FacebookAds.facebookInterstitialAd(this);
    }

    public void playMusic(String url) {
        new task().execute(new String[]{url});
    }

    public void stopMusic() {
        this.mp.stop();
        this.mp.reset();
    }

    public ArrayList<String> getAllMusics(String path) {
        ArrayList<String> f = new ArrayList();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] listFile = file.listFiles();
            for (int i = 0; i < listFile.length; i++) {
                f.add("file://" + listFile[i].getAbsolutePath());
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(listFile[i].getAbsolutePath());
                String timem = mmr.extractMetadata(9);
                String teee = mmr.extractMetadata(7);
                long dur = Long.parseLong(timem);
                String seconds = String.valueOf((dur % HlsChunkSource.DEFAULT_PLAYLIST_BLACKLIST_MS) / 1000);
                String minutes = String.valueOf(dur / HlsChunkSource.DEFAULT_PLAYLIST_BLACKLIST_MS);
                if (seconds.length() == 1) {
                    this.listMusicTime.add(AppEventsConstants.EVENT_PARAM_VALUE_NO + minutes + ":0" + seconds);
                } else {
                    this.listMusicTime.add(AppEventsConstants.EVENT_PARAM_VALUE_NO + minutes + ":" + seconds);
                }
                Date lastModDate = new Date(listFile[i].lastModified());
                this.listMusicName.add(listFile[i].getName());
                this.listMusicNameCont.add(lastModDate.toString());
            }
        }
        return f;
    }

    public String pathControl() {
        String path = Environment.getExternalStorageDirectory() + File.separator + "Mp3Download/";
        if (path != null) {
            return path;
        }
        return getApplication().getFilesDir() + File.separator + "Mp3Download/";
    }

    public void onBackPressed() {
        super.onBackPressed();
        stopMusic();
    }
}
