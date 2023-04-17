
package client.legalease.Model.meetingmodel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meetingmodel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("meetinglist")
    @Expose
    private Meetinglist meetinglist;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Meetinglist getMeetinglist() {
        return meetinglist;
    }

    public void setMeetinglist(Meetinglist meetinglist) {
        this.meetinglist = meetinglist;
    }

}
