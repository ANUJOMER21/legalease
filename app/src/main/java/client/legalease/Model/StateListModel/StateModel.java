package client.legalease.Model.StateListModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateModel {


    @SerializedName("data")
    @Expose
    private List<StateData> getStateData = null;

    public List<StateData> getStateData() {
        return getStateData;
    }

    public void getStateData(List<StateData> getStateData) {
        this.getStateData = getStateData;
    }

}
