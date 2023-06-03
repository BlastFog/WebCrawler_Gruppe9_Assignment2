package at.gr6.crawler;

import com.deepl.api.DeepLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CrawlerThread extends Thread{

    private final String targetLanguage;
    private final String url;
    private final int maxDepth;
    private TranslationManager translationManager;
    private final boolean translate;
    private final String authKey = "56a1abfc-d443-0e69-8963-101833b4014e:fx";
    private ReportWriter filer;
    private Page page;
    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerThread.class);
    private JsoupWrapper jsoupWrapper;
    private LanguageStatisticsProvider languageStatistics;
    //private CrawlerThread proxyInstance;

    public CrawlerThread(int depth, String targetLanguage, boolean translate, String url) {
        this.maxDepth = depth;
        this.targetLanguage = targetLanguage;
        this.translate = translate;
        this.url = url;
        this.languageStatistics = new LanguageStatisticsManager();

        /*this.proxyInstance = (CrawlerThread) Proxy.newProxyInstance(
                CrawlerThread.class.getClassLoader(),
                new Class[] {CrawlerThread.class},
                new DynamicInvocationHandler()
        );*/
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
            translationManager.setDetectedLanguage();
            filer.writeLanguage(translationManager);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupTranslation() {
        try {
            translationManager = new TranslationManager(targetLanguage, translate, authKey, languageStatistics);
        } catch (DeepLException|InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void translatePages(Page page) {
        try {
            translationManager.translatePage(page);
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

    private void readPageFromJsoup(Page page) {
        try {
            setUpJsoupWrapper();
            jsoupWrapper.readWebPage(page.getUrl());
            setPageElements(page);
            checkForDepthAndCallForNextDepth(page);
        } catch (Exception e) {
            page.setBroken(true);
            LOGGER.info("Broken Link detected: {}",page.getUrl());
            //proxyInstance.get("Broken Link detected:"+page.getUrl());
            //proxyInstance.readPageFromJsoup(page);
        }
    }
    private void setPageElements(Page page){
        page.setHeaderStringList(jsoupWrapper.getHeadersList());
        page.setSubPages(jsoupWrapper.getLinkList());
    }

    private void checkForDepthAndCallForNextDepth(Page page){
        if(page.getDepth()<maxDepth){
            for (Page subPage : page.getSubPage()) {
                readPageFromJsoup(subPage);
            }

        }
    }

}
