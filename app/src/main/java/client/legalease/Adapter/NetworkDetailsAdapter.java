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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import client.legalease.Model.NetrorkDetailsModel.NetworkDetailsData;
import client.legalease.R;

import static client.legalease.APIConstant.ApiConstant.IMAGEURL;
import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NetworkDetailsAdapter extends RecyclerView.Adapter<NetworkDetailsAdapter.ViewHolder> {


    public static List<NetworkDetailsData> networkDetailsData;
    private Context context;

    public NetworkDetailsAdapter(Context context, List<NetworkDetailsData> networkDetailsData) {
        this.context = context;
        this.networkDetailsData = networkDetailsData;
    }



    @NonNull
    @Override
    public NetworkDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_network_details, viewGroup, false);


        return new NetworkDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NetworkDetailsAdapter.ViewHolder viewHolder, int i) {

        String amount ="";
        String title ="";
        String price ="";
        String governmentFee = "";
        String professionalfee = "";
        float x;
        float a,b,c,d;

        try {
             x = Float.parseFloat(networkDetailsData.get(i).getAmount());
            amount = String.valueOf(Math.round(x));

            title = networkDetailsData.get(i).getTitle();

            price =networkDetailsData.get(i).getPrice();
            c = Float.parseFloat(networkDetailsData.get(i).getGovFee());

            governmentFee =String.valueOf(c) ;
            d = Float.parseFloat(networkDetailsData.get(i).getProfessionalFee());

            professionalfee = String.valueOf(d) ;



            viewHolder.tv_title.setText(title);
            viewHolder.tv_price.setText(price);
            viewHolder.tv_govFee.setText(governmentFee);
            viewHolder.tv_profFee.setText(professionalfee);
            viewHolder.tv_amount.setText(amount);


        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }

    }

    @Override
    public int getItemCount() {
        return networkDetailsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_price, tv_govFee, tv_profFee,tv_amount,tv_title;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_govFee = (TextView) itemView.findViewById(R.id.tv_govFee);
            tv_profFee = (TextView) itemView.findViewById(R.id.tv_profFee);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);



        }
    }
}
