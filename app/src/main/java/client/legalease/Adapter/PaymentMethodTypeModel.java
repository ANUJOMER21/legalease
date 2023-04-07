package client.legalease.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import client.legalease.Model.PaymentMethodModel.PaymentMethodData;
import client.legalease.R;
import client.legalease.RedeemActivtiy;

import static client.legalease.APIConstant.ApiConstant.IMAGEURL;
import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PaymentMethodTypeModel extends RecyclerView.Adapter<PaymentMethodTypeModel.ViewHolder> {


    public static List<PaymentMethodData> paymentMethodData;
    private Context context;
    String cashMoney;

    public PaymentMethodTypeModel(Context context, List<PaymentMethodData> paymentMethodData,String cashMoney) {
        this.context = context;
        this.paymentMethodData = paymentMethodData;
        this.cashMoney =cashMoney;
    }



    @NonNull
    @Override
    public PaymentMethodTypeModel.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_paymemtmethod, viewGroup, false);


        return new PaymentMethodTypeModel.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PaymentMethodTypeModel.ViewHolder viewHolder, int i) {

        String name ="";
        String email ="";
        String mobile ="";
        String imageUrl = "";


        try {

            imageUrl = IMAGEURL+paymentMethodData.get(i).getImage();
//            viewHolder.iv_method.setImageResource(imageUrl);


            Glide.with(context)
                    .load(imageUrl)
                    .apply(fitCenterTransform())
                    .into(viewHolder.iv_method);


        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }


    }

    @Override
    public int getItemCount() {
        return paymentMethodData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_method;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            iv_method = (ImageView) itemView.findViewById(R.id.iv_method);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos =getAdapterPosition();
                    PaymentMethodData myData =null;
                    String methodId = "";
                    String link = "";
                    try {
                        myData = paymentMethodData.get(pos);
                        methodId  =myData.getId();
                        link = IMAGEURL+paymentMethodData.get(pos).getImage();
                    }catch (NullPointerException ignored){

                    }catch (Exception ignore){

                    }
                    Intent intent = new Intent(context, RedeemActivtiy.class);
                    intent.putExtra("methodId",methodId);
                    intent.putExtra("link",link);
                    intent.putExtra("cashMoney",cashMoney);
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);

                }
            });


        }
    }
}
