
package client.legalease.Model.InvoiceDetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoiceData {

    @SerializedName("data")
    @Expose
    private List<InData> data = null;

    public List<InData> getData() {
        return data;
    }

    public void setData(List<InData> data) {
        this.data = data;
    }

}
