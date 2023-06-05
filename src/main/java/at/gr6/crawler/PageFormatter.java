package at.gr6.crawler;

import java.util.List;


public class PageFormatter implements Formatter {
    private Page page;
    private final String brokenLinkString = "broken link <a>";
    private final String normalLinkString = "link to <a>";
    private String outputString;

    public PageFormatter(Page page) {
        this.page = page;
    }

    public void generateOutputString() {
        outputString = "";
        appendHeader();
        appendLinks();
    }

    private void appendHeader(){
        List<Header> headerList = page.getHeaderList();
        for (Header header : headerList) {
            addHeaderGrade(header);
            addIndentation(page.getDepth());
            outputString += header.getHeaderString();
            outputString +=" <br>\n";
        }
    }
    private void appendLinks(){
        for(Page p: page.getSubPage()){
            outputString+="<br> ";
            addIndentation(p.getDepth());
            outputString+=appendLinkWithStatus(p);
        }
    }

    private String appendLinkWithStatus(Page p){
        String linkStatus = "";
        if(p.isBroken())
            linkStatus += brokenLinkString + p.getUrl() + "</a>\n\n";
        else
            linkStatus += normalLinkString + p.getUrl() + "</a>\n\n";
        return linkStatus;
    }

    private void addIndentation(int depth) {
        for (int i = 0; i < depth; i++)
            outputString += "-";
        outputString += ">";
    }
    private void addHeaderGrade(Header header){
        for(int i=0;i<header.getHeaderGrade();i++)
            outputString+="#";
        outputString+=" ";
    }

    public String getOutputString() throws Exception {
        if (outputString!=null)
            return outputString;
        else throw new Exception("Output String must be generated first");
    }
}
