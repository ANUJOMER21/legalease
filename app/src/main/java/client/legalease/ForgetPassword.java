package client.legalease;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class ForgetPassword extends AppCompatActivity {
    WebView web_fotgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        web_fotgetPassword= (WebView)findViewById(R.id.web_fotgetPassword);
        web_fotgetPassword.getSettings().setJavaScriptEnabled(true);
        web_fotgetPassword.loadUrl("http://capanel.in/password/reset");
    }


}
