package client.legalease.Adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import client.legalease.Model.SubServicesListModel.SubservicesListData;
import client.legalease.R;
import client.legalease.SearchActivity;
import client.legalease.ServicesDetails;

public class SubServicesListAdapter extends RecyclerView.Adapter<SubServicesListAdapter.SubServicesViewHolder> implements Filterable {

    private SearchActivity context;
    private List<SubservicesListData> subservicesListData;
    private List<SubservicesListData> filteredNameList;

    public SubServicesListAdapter(SearchActivity context, List<SubservicesListData> subservicesListData) {
        super();
        this.context = context;
        this.subservicesListData = subservicesListData;
        this.filteredNameList = subservicesListData;
    }

    @NonNull
    @Override
    public SubServicesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.subserviceslist_item, viewGroup, false);
        return new SubServicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubServicesViewHolder holder, int position) {

        try {
            holder.tv_subservicesTitle.setText(filteredNameList.get(position).getTitle());

            holder.tv_subservicesSubTitle.setText(filteredNameList.get(position).getSubTitle());
        }catch (NullPointerException ignored){

        }catch (IndexOutOfBoundsException ignore){

        }
    }

    @Override
    public int getItemCount() {
        return filteredNameList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSequenceString = constraint.toString();
                if (charSequenceString.isEmpty()) {
                    filteredNameList = subservicesListData;
                } else {
                    List<SubservicesListData> filteredList = new ArrayList<>();
                    HashMap<String,String> searchData= new HashMap<>();
                    for (SubservicesListData title : subservicesListData) {
                        if (title.getTitle().toLowerCase().contains(charSequenceString.toLowerCase())) {
                            filteredList.add(title);
                        }
                        filteredNameList = filteredList;
                    }

                }
                FilterResults results = new FilterResults();
                results.values = filteredNameList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredNameList = (List<SubservicesListData>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class SubServicesViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_subservicesSubTitle,tv_subservicesTitle;

        SubServicesViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_subservicesTitle = (TextView)itemView.findViewById(R.id.tv_subservicesTitle);

            tv_subservicesSubTitle = (TextView)itemView.findViewById(R.id.tv_subservicesSubTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String parentId = "";
                    String name = "";
                    String price = "";
                    int pos = getAdapterPosition();
                    SubservicesListData mydata = null;

                    try {
                        mydata = filteredNameList.get(pos);
                        parentId = String.valueOf(mydata.getId());
                        name = mydata.getTitle();
                        price = mydata.getPrice();

                    }catch (NullPointerException ignored){

                    }catch (IndexOutOfBoundsException ignore){

                    }
                    Intent intent  =new Intent(context, ServicesDetails.class);
                    intent.putExtra("parentId",parentId);
                    intent.putExtra("name",name);
                    intent.putExtra("price",price);
                    context.startActivity(intent);
                }
            });
        }
    }

}