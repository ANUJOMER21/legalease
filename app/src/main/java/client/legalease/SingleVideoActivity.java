package client.legalease;

import android.content.Intent;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static android.net.Uri.parse;

import androidx.appcompat.app.AppCompatActivity;

public class SingleVideoActivity extends AppCompatActivity {



     String mp4URL;
     PlayerView player_view;
     SimpleExoPlayer player;
     String myUrl = "https://rgyan.com/public/uploads/sposts/6c1fa0621e2818713c0e31c1e99bc9b94bdc277e.mp4";
//     private ImaAdsLoader imaAdsLoader;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_single_video);

        player_view = (PlayerView)findViewById(R.id.player_view);
        Intent intent  =getIntent();
        mp4URL =intent.getStringExtra("videoUrl");
//        imaAdsLoader = new ImaAdsLoader(this,parse(myUrl));





    }

    @Override
    protected void onStart() {
        super.onStart();

        player = ExoPlayerFactory.newSimpleInstance(this,
                new DefaultTrackSelector());
        player_view.setPlayer(player);
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this,"exo-demo"));
        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(parse(mp4URL));

//       AdsMediaSource adsMediaSource =  new AdsMediaSource(mediaSource,dataSourceFactory,imaAdsLoader,player_view.getOverlayFrameLayout());
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        player_view.setPlayer(null);
        player.release();
        player =null;
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        imaAdsLoader.release();
//    }
}
















