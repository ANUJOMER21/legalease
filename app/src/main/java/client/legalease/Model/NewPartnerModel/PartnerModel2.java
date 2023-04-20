package client.legalease.Model.NewPartnerModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartnerModel2 {
    private String status;
    private List<Assoicate> assoicate = new ArrayList<Assoicate>();
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public List<Assoicate> getAssoicate() {
        return assoicate;
    }
    public void setAssoicate(List<Assoicate> assoicate) {
        this.assoicate = assoicate;
    }
}

