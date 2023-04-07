package client.legalease;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import client.legalease.Fragment.Report.PandLFragment;
import client.legalease.Fragment.Report.ReportFragment;
import client.legalease.Fragment.Report.SheetFragment;
import client.legalease.Fragment.Report.SummaryFragment;

public class ReportActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private TextView mTextMessage;
    BottomNavigationView navigation;
    boolean pressBackToExit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        loadFragment(new SummaryFragment());

    }



    private boolean loadFragment(Fragment fragment) {

        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_summary:
                fragment = new SummaryFragment();

                break;
            case R.id.navigation_sheet:
                fragment = new SheetFragment();

                break;
            case R.id.navigation_pandl:

                fragment = new PandLFragment();

                break;
            case R.id.navigation_report:

                fragment = new ReportFragment();

                break;
        }

        return loadFragment(fragment);
    }
    @Override
    public void onBackPressed() {
        if (navigation.getSelectedItemId() == R.id.navigation_summary) {
                Intent intent = new Intent(ReportActivity.this,HomeActivity.class);
                startActivity(intent);
                finishAffinity();


        } else {
            navigation.setSelectedItemId(R.id.navigation_summary);
        }
    }



}
