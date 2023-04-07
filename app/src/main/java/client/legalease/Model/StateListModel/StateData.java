package client.legalease.Model.StateListModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("CountryID")
    @Expose
    private String countryID;
    @SerializedName("StateName")
    @Expose
    private String stateName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

}