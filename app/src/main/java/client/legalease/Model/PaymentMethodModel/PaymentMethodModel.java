
package client.legalease.Model.PaymentMethodModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentMethodModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<PaymentMethodData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PaymentMethodData> getData() {
        return data;
    }

    public void setData(List<PaymentMethodData> data) {
        this.data = data;
    }

    @SerializedName("easymoney")
    @Expose
    private List<EasyMoneyModel> easymoney = null;

    public List<EasyMoneyModel> getEasymoney() {
        return easymoney;
    }

    public void setEasymoney(List<EasyMoneyModel> easymoney) {
        this.easymoney = easymoney;
    }

}
