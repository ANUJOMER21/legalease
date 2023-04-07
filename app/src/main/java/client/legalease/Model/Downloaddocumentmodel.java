package client.legalease.Model;

public class Downloaddocumentmodel {
    private String docname,docimage,docpage;


    public Downloaddocumentmodel(String docname, String docimage, String docpage) {
        this.docname = docname;
        this.docimage = docimage;
        this.docpage = docpage;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getDocimage() {
        return docimage;
    }

    public void setDocimage(String docimage) {
        this.docimage = docimage;
    }

    public String getDocpage() {
        return docpage;
    }

    public void setDocpage(String docpage) {
        this.docpage = docpage;
    }
}
