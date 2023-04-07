package client.legalease.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import client.legalease.Model.NoteModel.NoteData;
import client.legalease.R;

public class AdapterMyNote extends RecyclerView.Adapter<AdapterMyNote.ViewHolder> {


    public static List<NoteData> noteData;

    private Context context;

    public AdapterMyNote(Context context, List<NoteData> noteData) {
        this.context = context;
        this.noteData = noteData;
    }



    @NonNull
    @Override
    public AdapterMyNote.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_note, viewGroup, false);


        return new AdapterMyNote.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterMyNote.ViewHolder viewHolder, int i) {

        String title="";
        String  description="";



        try {
            title = noteData.get(i).getTitle();
            description = noteData.get(i).getDescription();


            viewHolder.tv_title.setText(title);
            viewHolder.tv_description.setText(description);



        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }


    }

    @Override
    public int getItemCount() {
        return noteData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_description;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
        }
    }
}
