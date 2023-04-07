package client.legalease.Adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import client.legalease.Model.ListOfMethodModel.ListOfMethodData;
import client.legalease.R;
import client.legalease.RedeemActivtiy;

import static client.legalease.APIConstant.ApiConstant.IMAGEURL;
import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListOfMethodAdapter extends RecyclerView.Adapter<ListOfMethodAdapter.ViewHolder> {


    public static List<ListOfMethodData> listOfMethodData;
    private Context context;

    public ListOfMethodAdapter(Context context, List<ListOfMethodData> listOfMethodData) {
        this.context = context;
        this.listOfMethodData = listOfMethodData;
    }



    @NonNull
    @Override
    public ListOfMethodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_of_method_type, viewGroup, false);


        return new ListOfMethodAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListOfMethodAdapter.ViewHolder viewHolder, int i) {

        String item ="";


        Toast.makeText(context,listOfMethodData.get(2).getAccount(),Toast.LENGTH_SHORT).show();


        try {

            item = listOfMethodData.get(i).getAccount();

            viewHolder.tv_method.setText(item);


        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }
    }



    @Override
    public int getItemCount() {
        return listOfMethodData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

      public TextView tv_method;
        RadioButton radio_method;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            tv_method = (TextView) itemView.findViewById(R.id.tv_method);
            radio_method  =(RadioButton)itemView.findViewById(R.id.radio_method);

//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos =getAdapterPosition();
//                    ListOfMethodData myData =null;
//                    String methodId = "";
//                    String link = "";
//                    try {
//                        myData = paymentMethodData.get(pos);
//                        methodId  =myData.getId();
//                        link = IMAGEURL+paymentMethodData.get(pos).getImage();
//                    }catch (NullPointerException ignored){
//
//                    }catch (Exception ignore){
//
//                    }
//                    Intent intent = new Intent(context, RedeemActivtiy.class);
//                    intent.putExtra("methodId",methodId);
//                    intent.putExtra("link",link);
//                    context.startActivity(intent);
//
//                }
//            });


        }
    }
}
