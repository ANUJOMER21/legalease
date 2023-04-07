package client.legalease.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import client.legalease.HomeActivity;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.R;

public class ReferFragment extends Fragment {

    CommonSharedPreference commonSharedPreference;
    String token = "";
    int id = 0;
    String mobile = "";
    String name = "";
    Context context;
    Button send;
    ImageButton back_refer;
    String referal ="";
    TextView referal_code;
    ImageView iv_copy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_refer, container, false);

        commonSharedPreference = new CommonSharedPreference(getActivity());


        try {

            if (commonSharedPreference.loggedin()) {
                token = commonSharedPreference.getLoginSharedPref(getActivity()).getToken();
                id = commonSharedPreference.getLoginSharedPref(getActivity()).getId();
                mobile = commonSharedPreference.getLoginSharedPref(getActivity()).getMobile();
                name = commonSharedPreference.getLoginSharedPref(getActivity()).getName();
                referal=commonSharedPreference.getLoginSharedPref(getActivity()).getReferalcode();

            } else {
                token = "";
                id = 0;
                mobile = "";
                name = "User Name";
                referal="";

            }

        } catch (NullPointerException ignored) {

        } catch (IndexOutOfBoundsException ignore) {

        }
        referal_code = (TextView)rootView.findViewById(R.id.referal_code);
        iv_copy = (ImageView)rootView.findViewById(R.id.iv_copy);
        iv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String refer = referal_code.getText().toString().trim();


            }
        });



        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(),"AbrilFatface-Regular.otf");
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),"Jipatha-Regular.ttf");



        referal_code.setText(referal);

//                Fragment fragment = new FragmentHome();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frame_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();

        send = (Button)rootView.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRefered();
            }
        });

        return rootView;
    }



    private void getRefered() {
        String m1 = "Use my referral code " +referal
                +" and get wallets point on your first Login \n\n";
        String m2 = "Download The Legalease App From PlayStore \n  https://play.google.com/store/apps/details?id=client.legalease";
        String message = m1+m2;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(sendIntent);
        }
    }
}