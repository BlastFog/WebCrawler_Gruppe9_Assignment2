package at.gr6.crawler;

import com.deepl.api.DeepLException;

import java.io.IOException;

public class CrawlerThread extends Thread{

    private final String targetLanguage;
    private final String url;
    private final int maxDepth;
    private Translation translation;
    private final boolean translate;
    private final String authKey = "56a1abfc-d443-0e69-8963-101833b4014e:fx";
    private FileOutput filer;
    private Page page;

    private JsoupWrapper jsoupWrapper;

    public CrawlerThread(int depth, String targetLanguage, boolean translate, String url) {
        this.maxDepth = depth;
        this.targetLanguage = targetLanguage;
        this.translate = translate;
        this.url = url;
    }

    @Override
    public void run() {
        this.page = new Page(url, 1);
        readPageFromJsoup(page);
        setupWriter();
        setupTranslation();
        translatePages(page);
        writeLangHeader();
        writeToFile(page);
        try {
            filer.closeFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeLangHeader() {
        try {
            translation.setDetectedLanguage();
            filer.writeLanguage(translation);
        } catch (IOException | DeepLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupTranslation() {
        try {
            translation = new Translation(targetLanguage, translate, authKey);
        } catch (DeepLException|InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void translatePages(Page page) {
        try {
            //System.out.println("read for thread: "+this.getName());
            translation.translatePage(page);
        } catch (DeepLException|InterruptedException e) {
            throw new RuntimeException(e);
        } 
        if (this.page.getDepth() < maxDepth) {
            for (Page subPage : page.getSubPage()) {
                translatePages(subPage);
            }
        }

    }

    private void setupWriter() {
        try {
            //System.out.println("setup Writer for thread: "+this.getName());
            filer = new FileOutput("./report.md");
            filer.writeBeginning(page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToFile(Page page) {
        try {
            //System.out.println("writing for thread: "+this.getName());
            filer.writeBody(page);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (page.getDepth() < maxDepth) {
            for (Page subPage : page.getSubPage()) {
                writeToFile(subPage);
            }
        }
    }
    private void setUpJsoupWrapper(){
        jsoupWrapper = new JsoupWrapper();
    }

    private void readPageFromJsoup(Page page) {
        try {
            //System.out.println("read for thread: "+this.getName());
            setUpJsoupWrapper();
            jsoupWrapper.readWebPage(page.getUrl());
            setPageElements();
            checkForDepthAndCallForNextDepth();
        } catch (Exception e) {
            page.setBroken(true);
        }
    }
    private void setPageElements(){
        page.setHeaderStringList(jsoupWrapper.getHeadersList());
        page.setSubPages(jsoupWrapper.getLinkList());
    }

    private void checkForDepthAndCallForNextDepth(){
        if(page.getDepth()<maxDepth){
            for (Page subPage : page.getSubPage()) {
                readPageFromJsoup(subPage);

            }

        }
    }

}
