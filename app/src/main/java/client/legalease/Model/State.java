package client.legalease.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class State {

    @SerializedName("data")
    @Expose
    private List<StateData> data;

    public List<StateData> getData() {
        return data;
    }

    public void setData(List<StateData> data) {
        this.data = data;
    }

}
