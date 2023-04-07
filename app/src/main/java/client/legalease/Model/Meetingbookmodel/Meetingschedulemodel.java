
package client.legalease.Model.Meetingbookmodel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Meetingschedulemodel {

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
