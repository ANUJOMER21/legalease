
package client.legalease.Model.MyDocuments;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyDocumentModel {

    @SerializedName("data")
    @Expose
    private List<DocumentData> data = null;

    public List<DocumentData> getData() {
        return data;
    }

    public void setData(List<DocumentData> data) {
        this.data = data;
    }

}
