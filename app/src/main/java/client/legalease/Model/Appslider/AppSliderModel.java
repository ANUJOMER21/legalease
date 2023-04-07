
package client.legalease.Model.Appslider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppSliderModel {

    @SerializedName("data")
    @Expose
    private List<SliderData> data = null;

    public List<SliderData> getData() {
        return data;
    }

    public void setData(List<SliderData> data) {
        this.data = data;
    }

}
