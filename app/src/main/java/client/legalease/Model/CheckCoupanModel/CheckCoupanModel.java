
package client.legalease.Model.CheckCoupanModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckCoupanModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<CoupanData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CoupanData> getData() {
        return data;
    }

    public void setData(List<CoupanData> data) {
        this.data = data;
    }


    @SerializedName("price")
    @Expose
    private Integer price;

    @SerializedName("mainprice")
    @Expose
    private String mainprice;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


    public String getMainprice() {
        return mainprice;
    }

    public void setMainprice(String mainprice) {
        this.mainprice = mainprice;
    }
}
