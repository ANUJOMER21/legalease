package client.legalease.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class StateData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("CountryID")
    @Expose
    private Integer countryID;
    @SerializedName("StateName")
    @Expose
    private String stateName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCountryID() {
        return countryID;
    }

    public void setCountryID(Integer countryID) {
        this.countryID = countryID;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

}
