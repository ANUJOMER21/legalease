package client.legalease;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {
    ImageView imageView;
    public final static int SPLASH_TIME_OUT= 2000;
    Animation zoom_in_exit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        imageView = (ImageView)findViewById(R.id.imageView);



        zoom_in_exit = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in_exit);


//        String hashKey ="";
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                 hashKey = new String(Base64.encode(md.digest(), 0));
//                Log.i("adfs", "printHashKey() Hash Key: " + hashKey);
//            }
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("dfs", "printHashKey()", e);
//        } catch (Exception e) {
//            Log.e("fasf", "printHashKey()", e);
//        }
//        Log.i("adfs", "printHashKey() Hash Key: " + hashKey);



        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                imageView.startAnimation(zoom_in_exit);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);


    }
}
