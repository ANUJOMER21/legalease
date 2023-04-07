package client.legalease.Adapter;

import android.content.Context;
import android.os.Parcelable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import client.legalease.BillListActivity;
import client.legalease.R;

public class CustomPagerAdapter extends PagerAdapter {

    private String[] urls;
    private LayoutInflater inflater;
    private Context context;


    public CustomPagerAdapter(Context context, String[] imageValue) {

        this.context = context;
        this.urls = imageValue;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {

        int n =0;
        try {
            return urls.length;

        }catch (NullPointerException ignored){

        }
        return n;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.pager_item, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.imageView);


        Glide.with(context)
                .load(urls[position])
                .into(imageView);

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}