package client.legalease.Model;

public class Uploadeddocmodel {
    String nameofdoc,imageofdoc;

    public String getNameofdoc() {
        return nameofdoc;
    }

    public void setNameofdoc(String nameofdoc) {
        this.nameofdoc = nameofdoc;
    }

    public String getImageofdoc() {
        return imageofdoc;
    }

    public void setImageofdoc(String imageofdoc) {
        this.imageofdoc = imageofdoc;
    }

    public Uploadeddocmodel(String nameofdoc, String imageofdoc) {
        this.nameofdoc = nameofdoc;
        this.imageofdoc = imageofdoc;
    }
}
