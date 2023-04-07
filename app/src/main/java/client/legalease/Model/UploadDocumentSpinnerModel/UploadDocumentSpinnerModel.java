
package client.legalease.Model.UploadDocumentSpinnerModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadDocumentSpinnerModel {

    @SerializedName("data")
    @Expose
    private List<UploadSpinnerModel> data = null;

    public List<UploadSpinnerModel> getData() {
        return data;
    }

    public void setData(List<UploadSpinnerModel> data) {
        this.data = data;
    }

}
