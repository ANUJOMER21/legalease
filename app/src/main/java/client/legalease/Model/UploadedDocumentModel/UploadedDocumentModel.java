
package client.legalease.Model.UploadedDocumentModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadedDocumentModel {

    @SerializedName("data")
    @Expose
    private List<UploadedData> data = null;

    public List<UploadedData> getData() {
        return data;
    }

    public void setData(List<UploadedData> data) {
        this.data = data;
    }

}
