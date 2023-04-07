package client.legalease.Model.PaymentMethodModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EasyMoneyModel {
    @SerializedName("easymoney")
    @Expose
    private String easymoney;

    public String getEasymoney() {
        return easymoney;
    }

    public void setEasymoney(String easymoney) {
        this.easymoney = easymoney;
    }
}
