package at.gr6.crawler;

public class Header {

    private String headerString;
    private int headerGrade;

    public Header(String headerString,int headerGrade){
        this.headerString = headerString;
        this.headerGrade = headerGrade;
    }

    public String getHeaderString() {
        return headerString;
    }

    public int getHeaderGrade() {
        return headerGrade;
    }
    public void setHeaderString(String headerString){
        this.headerString = headerString;
    }
}
