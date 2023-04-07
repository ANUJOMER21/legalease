
package client.legalease.Model.MyEasyMoneyModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyEasyMoneyData {

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
