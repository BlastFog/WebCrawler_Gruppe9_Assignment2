package at.gr6.crawler;

import java.util.List;

public class PageFormatter {

    private Page page;

    private final String brokenLinkString = "broken link <a>";
    private final String normalLinkString = "linkTo <a>";

    private String outputString;

    public PageFormatter(Page page){
        this.page = page;
    }

    public void generateOutputString(){
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
            outputString +="\n";
        }
    }
    private void appendLinks(){
        for(Page p: page.getSubPage()){
            outputString+="<br> ";
            addIndentation(p.getDepth());
            if(p.isBroken())
                outputString+=brokenLinkString + p.getUrl() + "</a>\n";
            else
                outputString += normalLinkString + p.getUrl() + "</a>\n";
        }
    }

    private void addIndentation(int depth) {
        for (int i = 0; i < depth; i++)
            outputString += "-";
        outputString += ">";
    }
    private void addHeaderGrade(Header header){
        for(int i=0;i<header.getHeaderGrade();i++){
            outputString+="#";
        }
        outputString+=" ";
    }

    public String getOutputString() throws Exception {
        if (outputString!=null){
            return outputString;
        }else throw new Exception("Output String must be generated first");
    }

}