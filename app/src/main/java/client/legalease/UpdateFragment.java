package client.legalease;

import android.graphics.Typeface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import client.legalease.APIConstant.ApiConstant;
import client.legalease.Preference.CommonSharedPreference;

public class UpdateFragment extends Fragment {


    CommonSharedPreference commonSharedPreference;
    String token = "";
    String name = "";
    ProgressBar progressBar;
    WebView web_view;
    TextView tv;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_update, container, false);


        commonSharedPreference = new CommonSharedPreference(getActivity());

        initializeView(rootView);


        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "AbrilFatface-Regular.otf");
        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "Jipatha-Regular.ttf");


        tv.setTypeface(font1);

        WebSettings webSettings = web_view.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web_view.loadUrl("https://professionalsaathi.com/blog");

        try {

            if (commonSharedPreference.loggedin()) {
                token = commonSharedPreference.getLoginSharedPref(getActivity()).getToken();

//                name = commonSharedPreference.getLoginSharedPref(getActivity()).getName();

            } else {
                token = "";
                name = "User Name";

            }

        } catch (NullPointerException ignored) {

        } catch (IndexOutOfBoundsException ignore) {

        }


        return rootView;
    }

    private void initializeView(View rootView) {
        web_view =(WebView)rootView.findViewById(R.id.web_view);
        tv =(TextView)rootView.findViewById(R.id.tv);
    }
}
