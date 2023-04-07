package client.legalease;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.barteksc.pdfviewer.PDFView;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import client.legalease.Adapter.CustomPagerAdapter;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class BillListActivity extends AppCompatActivity {
    String[] imageValue;
    ViewPager viewPager;
    CustomPagerAdapter mCustomPagerAdapter;
    private static int NUM_PAGES = 0;
    int currentPage = 1;
    String urlToBeCheck = "";
    String url = "";
    PDFView pdfView;
    String imageList = "";
    ImageView imageView;
    RelativeLayout relative_image;
    ProgressBar progress_image ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_list);
        pdfView = (PDFView)findViewById(R.id.pdfView);
        viewPager = (ViewPager) findViewById(R.id.pager);
        imageView = (ImageView)findViewById(R.id.imageView);
        relative_image = (RelativeLayout)findViewById(R.id.relative_image);
        progress_image = (ProgressBar)findViewById(R.id.progress_image);

        try {
            Intent intent = getIntent();
            urlToBeCheck = intent.getStringExtra("urlToBeCheck");
            imageList = intent.getStringExtra("imageList");
            if (imageList.equals("1")){
                if (urlToBeCheck.equals(".pdf")){
                    url = intent.getStringExtra("url");
                    pdfView.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.GONE);
                    relative_image.setVisibility(View.GONE);

                    FileLoader.with(BillListActivity.this)
                            .load(url)
                            .fromDirectory("test4", FileLoader.DIR_INTERNAL)
                            .asFile(new FileRequestListener<File>() {
                                @Override
                                public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                                    File file = response.getBody();

                                    pdfView.fromFile(file)
                                            .password(null)
                                            .defaultPage(0)
                                            .enableSwipe(true)
                                            .load();
                                    // do something with the file
                                }

                                @Override
                                public void onError(FileLoadRequest request, Throwable t) {
                                }
                            });

                }else {
                    url = intent.getStringExtra("url");
                    pdfView.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);
                    relative_image.setVisibility(View.VISIBLE);
                    Glide.with(getApplicationContext())
                            .load(url)
                            .apply(fitCenterTransform())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    progress_image.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progress_image.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(imageView);

                }
            }else {
                imageValue = intent.getStringArrayExtra("imageValue");
                pdfView.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
                relative_image.setVisibility(View.GONE);
                Log.d("fds", String.valueOf(imageValue));
                Log.d("fds", String.valueOf(imageValue));


                init();
                }

        }catch (NullPointerException ignored){

        }catch (ArrayIndexOutOfBoundsException Ignore){

        }catch (IndexOutOfBoundsException ignor){

        }




    }





    private void init() {

        viewPager.setAdapter(new CustomPagerAdapter(getApplicationContext(),imageValue));


        final float density = getResources().getDisplayMetrics().density;

        NUM_PAGES = imageValue.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);


    }

}
