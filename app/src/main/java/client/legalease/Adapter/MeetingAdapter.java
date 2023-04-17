package client.legalease.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import client.legalease.Model.meetingmodel.Datum;
import client.legalease.R;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {
Context context;
List<client.legalease.Model.meetingmodel.Datum> list ;

    public MeetingAdapter(Context context, List<client.legalease.Model.meetingmodel.Datum> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meeting_schedule, parent, false);
return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      String assn="";
      String meetate="",meettim="",messag="";
       Datum datum=list.get(position);
     if(datum.getAssociate()==null) {
         assn = "";
     }
     else {
         assn = datum.getAssociate().getName();

     }
        messag=datum.getRemarks();
        try {
            String startDateString = datum.getMeetingDate();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
          meetate=(sdf.format(sdf2.parse(startDateString)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
       // meetate=datum.getMeetingDate();

        meettim=datum.getMeetingTime();
        meettim=getFormatedDateTime(datum.getMeetingTime(),"HH:mm:ss","hh:mm a");
        holder.assname.setText(assn);
        holder.meetdate.setText(meetate);
        holder.meettime.setText(meettim);
        holder.message.setText(messag);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static String getFormatedDateTime(String dateStr, String strReadFormat, String strWriteFormat) {

        String formattedDate = dateStr;

        DateFormat readFormat = new SimpleDateFormat(strReadFormat, Locale.getDefault());
        DateFormat writeFormat = new SimpleDateFormat(strWriteFormat, Locale.getDefault());

        Date date = null;

        try {
            date = readFormat.parse(dateStr);
        } catch (ParseException e) {
        }

        if (date != null) {
            formattedDate = writeFormat.format(date);
        }

        return formattedDate;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView assname,meettime,meetdate,message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            assname=itemView.findViewById(R.id.ascname);
            meetdate=itemView.findViewById(R.id.meetingdate);
            meettime=itemView.findViewById(R.id.meetingtime);
            message=itemView.findViewById(R.id.message);
        }
    }
}
