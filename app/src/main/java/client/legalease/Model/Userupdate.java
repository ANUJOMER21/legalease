package client.legalease.Model;

public class Userupdate {
    private String name,dob,state,qualification,gst,city,pincode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getQual() {
        return qualification;
    }

    public void setQual(String qual) {
        this.qualification = qual;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Userupdate(String name, String dob, String state, String qual, String gst, String city, String pincode) {
        this.name = name;
        this.dob = dob;
        this.state = state;
        this.qualification = qual;
        this.gst = gst;
        this.city = city;
        this.pincode = pincode;
    }
}
