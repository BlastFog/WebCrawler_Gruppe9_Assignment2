package at.gr6.crawler;

import com.deepl.api.DeepLException;

import java.io.IOException;

public class CrawlerThread extends Thread{

    private String targetLanguage = "";
    private String url = "";
    private int maxDepth;
    private Translation translation;
    private boolean translate = false;
    private final String authKey = "56a1abfc-d443-0e69-8963-101833b4014e:fx";
    private FileOutput filer;
    private Page page;

    public CrawlerThread(int depth, String targetLanguage, boolean translate, String url, FileOutput filer) {
        this.maxDepth = depth;
        this.targetLanguage = targetLanguage;
        this.translate = translate;
        this.url = url;
        this.filer = filer;
    }

    @Override
    public void run() {
        this.page = new Page(url, 1);
        readPage(page);
        //setupWriter();
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
        } catch (DeepLException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void translatePages(Page page) {
        try {
            translation.translatePage(page);
        } catch (DeepLException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (Main.page.getDepth() < maxDepth) {
            for (Page subPage : page.getSubPage()) {
                translatePages(subPage);
            }
        }

    }

    private void setupWriter() {
        try {
            filer = new FileOutput("./report.md");
            filer.writeBeginning(page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToFile(Page page) {
        try {
            filer.writeBody(page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (page.getDepth() < maxDepth) {
            for (Page subPage : page.getSubPage()) {
                writeToFile(subPage);
            }
        }
    }

    private void readPage(Page page) {
        try {
            JsoupWrapper jsoupWrapper = new JsoupWrapper();
            jsoupWrapper.readWebPage(page.getUrl());
            page.setHeaderStringList(jsoupWrapper.getHeadersList());
            page.setSubPages(jsoupWrapper.getLinkList());
            if (page.getDepth() < maxDepth) {
                for (Page subPage : page.getSubPage()) {
                    readPage(subPage);
                }
            }
        } catch (Exception e) {
            page.setBroken(true);
        }
    }
}
