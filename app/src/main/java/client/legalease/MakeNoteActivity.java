package client.legalease;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import client.legalease.Adapter.AdapterMyNote;
import client.legalease.BroadcastReceiver.ConnectivityReceiver;
import client.legalease.Model.LoginModel.LoginModel;
import client.legalease.Model.NoteModel.NoteData;
import client.legalease.Model.NoteModel.NoteModel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.WebServices.ApiService;
import client.legalease.WebServices.CallApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeNoteActivity extends AppCompatActivity {
    String name = "";
    String clientId = "";
    ImageView back;
    TextView clientName,tv_headingNote;
    Button btn_save;
    RecyclerView rv_myNotes;
    EditText et_note,et_noteTitle;
    String title = "";
    String description = "";
    ProgressDialog pDialog;
    String token = "";
    CallApi callApi =new CallApi();
    CommonSharedPreference commonSharedPreference;
    List<NoteData> noteData = new ArrayList<>();
    ProgressBar progressBar;
    AdapterMyNote adapterMyNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_note);

        try {
            Intent intent =getIntent();
            name = intent.getStringExtra("name");
            clientId = intent.getStringExtra("accountantId");
        }catch (NullPointerException ignored){

        }

        commonSharedPreference = new CommonSharedPreference(getApplicationContext());

        try {
            if (commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken() != null) {
                token = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken();
            }else {
                token = "";
            }
        }catch (NullPointerException ignored){

        }

        initializeViews();
        clientName.setText(name);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeNotes();
            }
        });






        myNotes();
    }

    private void myNotes() {
        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<NoteModel> call = api.getNote(token,clientId);
        call.enqueue(new Callback<NoteModel>() {
            @Override
            public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                progressBar.setVisibility(View.GONE);
                try {
                    noteData = response.body().getData();

                    if (noteData.size() == 0) {
                        rv_myNotes.setVisibility(View.GONE);
                        tv_headingNote.setVisibility(View.GONE);
                    } else {
                        rv_myNotes.setVisibility(View.VISIBLE);
                        tv_headingNote.setVisibility(View.VISIBLE);
                    }


                    adapterMyNote = new AdapterMyNote(getApplicationContext(),noteData);
                    RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
                    rv_myNotes.setLayoutManager(eLayoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                    rv_myNotes.setAdapter(adapterMyNote);
                }catch (NullPointerException ignored){

                }

            }

            @Override
            public void onFailure(Call<NoteModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void makeNotes() {
        title = et_noteTitle.getText().toString().trim();
        description = et_note.getText().toString().trim();
        if (ConnectivityReceiver.isConnected() == true) {

            if (title.equals("")) {
                et_noteTitle.setError("Please enter title for your note");
                et_noteTitle.requestFocus();
            } else if (description.equals("")) {
                et_note.setError("Note cannot be empty");
                et_note.requestFocus();
            } else {

                pDialog = new ProgressDialog(MakeNoteActivity.this);
                String pleaseWait = getResources().getString(R.string.please_wait);

                pDialog.setMessage(pleaseWait);
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
                HashMap<String,String> makeNoteCredential = new HashMap<>();
                makeNoteCredential.put("token",token);
                makeNoteCredential.put("clientId",clientId);
                makeNoteCredential.put("title",title);
                makeNoteCredential.put("description",description);



                try {
                    callApi.makeNotes(MakeNoteActivity.this, makeNoteCredential);
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        }else {
            Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();

        }
    }

    private void initializeViews() {
        back = (ImageView)findViewById(R.id.back);
        clientName =(TextView)findViewById(R.id.clientName);
        btn_save  =(Button)findViewById(R.id.btn_save);
        rv_myNotes =(RecyclerView)findViewById(R.id.rv_myNotes);
        tv_headingNote =(TextView)findViewById(R.id.tv_headingNote);
        et_note = (EditText)findViewById(R.id.et_note);
        et_noteTitle = (EditText)findViewById(R.id.et_noteTitle);
        progressBar =(ProgressBar)findViewById(R.id.progressBar);


    }

    public void clossDialog() {
        pDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Something went wrong, please try after some time", Toast.LENGTH_SHORT).show();

    }

    public void Response_MakeNote(LoginModel body) {
        pDialog.dismiss();
        Toast.makeText(getApplicationContext(),"Note saved succesfully",Toast.LENGTH_SHORT).show();
        myNotes();
    }
}
