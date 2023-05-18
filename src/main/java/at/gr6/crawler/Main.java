package at.gr6.crawler;

import java.io.IOException;

import com.deepl.api.*;

public class Main {
    static String targetLanguage = "";
    static int maxDepth;
    static boolean translate = false;


    public static void main(String[] args) throws InterruptedException {
        // new Args: depth, targetlang, translate boolean, links: 2...n
        //url = args[0];

        int numberOfLinks = args.length-3;

        maxDepth = Integer.parseInt(args[0]);
        targetLanguage = args[1];
        if (args[2].equals("true"))
            translate = true;

        try {
            FileOutput.clearFile("./report.md");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CrawlerThread thread;

        for(int i = 0; i < numberOfLinks; i++){
            thread = new CrawlerThread(maxDepth,targetLanguage,translate,args[i+3]);
            thread.start();
            //thread.join();                  // illegal
            System.out.println(i);
        }

    }

    /*
    private static void writeLangHeader() {
        try {
            translation.setDetectedLanguage();
            filer.writeLanguage(translation);
        } catch (IOException | DeepLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setupTranslation() {
        try {
            translation = new Translation(targetLanguage, translate, authKey);
        } catch (DeepLException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void translatePages(Page page) {
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

    private static void setupWriter() {
        try {
            filer = new FileOutput("./report.md");
            filer.writeBeginning(page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeToFile(Page page) {
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

    private static void readPage(Page page) {
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
    */
}