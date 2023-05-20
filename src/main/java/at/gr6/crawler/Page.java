package at.gr6.crawler;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Page {

    private ArrayList<Header> headerList;
    private boolean isBroken;
    private int depth;
    private String url;
    private List<Page> subPage;

    public String getUrl() {
        return this.url;
    }

    public void setHeaderStringList(ArrayList<Header> headerStringList) {
        this.headerList = headerStringList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return isBroken == page.isBroken && depth == page.depth && Objects.equals(headerList, page.headerList) && url.equals(page.url) && Objects.equals(subPage, page.subPage);
    }


    public Page(String url, int depth) {
        this.subPage = new ArrayList<>();
        this.url = url;
        this.depth = depth;
        this.isBroken = false;
        this.headerList = new ArrayList<>();
    }

    public ArrayList<Header> getHeaderList() {
        return this.headerList;
    }

    public boolean isBroken() {
        return this.isBroken;
    }

    public void setBroken(boolean broken) {
        this.isBroken = broken;
    }

    public List<Page> getSubPage() {
        return this.subPage;
    }

    public int getDepth() {
        return this.depth;
    }

    public void setSubPages(ArrayList<String> linkList) {
        for (String link : linkList)
            this.subPage.add(new Page(link, this.depth + 1));
    }
}
