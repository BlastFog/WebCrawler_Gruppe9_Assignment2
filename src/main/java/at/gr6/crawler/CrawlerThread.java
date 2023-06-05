package at.gr6.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class CrawlerThread extends Thread{
    private final String targetLanguage;
    private final String url;
    private final int maxDepth;
    private Translation deepLTranslator;
    private final boolean translate;
    private final String authKey = "56a1abfc-d443-0e69-8963-101833b4014e:fx";
    private FileOutput filer;
    private Page page;
    private static final Logger LOGGER;
    private CrawlerWrapper jsoupWrapper;
    private LanguageStatisticsProvider languageStatistics;
    private CountDownLatch countDownLatch;

    static {
        LOGGER = LoggerFactory.getLogger(CrawlerThread.class);
    }

    /**
     * This is the standard constructor
     * @param depth Depth to crawl
     * @param targetLanguage Target language to translate to
     * @param translate Translate boolean
     * @param url Url to crawl
     */
    public CrawlerThread(int depth, String targetLanguage, boolean translate, String url) {
        this.maxDepth = depth;
        this.targetLanguage = targetLanguage;
        this.translate = translate;
        this.url = url;
        this.languageStatistics = new LanguageStatistics();
    }

    /**
     * This is a constructor for testing purposes
     * @param depth Depth to crawl
     * @param targetLanguage Target language to translate to
     * @param translate Translate boolean
     * @param url Url to crawl
     * @param countDownLatch CountDownLatch to countdown after a thread is finished
     */
    public CrawlerThread(int depth, String targetLanguage, boolean translate, String url, CountDownLatch countDownLatch) {
        this.maxDepth = depth;
        this.targetLanguage = targetLanguage;
        this.translate = translate;
        this.url = url;
        this.languageStatistics = new LanguageStatistics();
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        this.page = new Page(url, 1);
        readPageRecursivelyFromJsoup(page);
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
        if(countDownLatch != null)
            countDownLatch.countDown();
    }

    private void writeLangHeader() {
        try {
            deepLTranslator.setDetectedLanguage();
            filer.writeLanguage(deepLTranslator);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupTranslation() {
        try {
            deepLTranslator = new DeepLTranslator(targetLanguage, translate, authKey, languageStatistics);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void translatePages(Page page) {
        try {
            deepLTranslator.translatePage(page);
        } catch (Exception e) {
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
            filer = new ReportWriter("./report.md");
            filer.writeBeginning(page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToFile(Page page) {
        try {
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

    /**
     * This method reads a page and its subpages recursively, while setting header and link attributes
     * @param page The page to be read
     */
    private void readPageRecursivelyFromJsoup(Page page) {
        try {
            setUpJsoupWrapper();
            jsoupWrapper.readWebPage(page.getUrl());
            setPageElements(page);
            checkForDepthAndCallForNextDepth(page);
        } catch (Exception e) {
            handleStatusInformation(page);
        }
    }

    private void handleStatusInformation(Page page) {
        addStatusInformation(page);
        logStatusInformation(page);
    }

    private void addStatusInformation(Page page){
        page.setBroken(true);
    }

    private void logStatusInformation(Page page){
        LOGGER.info("Broken Link detected: {}",page.getUrl());
    }

    private void setPageElements(Page page){
        page.setHeaderStringList(jsoupWrapper.getHeadersList());
        page.setSubPages(jsoupWrapper.getLinkList());
    }

    private void checkForDepthAndCallForNextDepth(Page page){
        if(page.getDepth()<maxDepth){
            for (Page subPage : page.getSubPage()) {
                readPageRecursivelyFromJsoup(subPage);
            }
        }
    }

}
