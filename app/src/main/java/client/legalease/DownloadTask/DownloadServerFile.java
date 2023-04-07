package client.legalease.DownloadTask;

import android.app.ProgressDialog;
import android.content.Context;

public class DownloadServerFile {
    private static final String TAG = "Download Task";
    private Context context;

    private String downloadUrl = "";
    String filename ="";
    public DownloadServerFile(Context context, String downloadUrl, String filename) {
        this.context = context;
        this.downloadUrl = downloadUrl;
        this.filename = filename;
    }



    }
