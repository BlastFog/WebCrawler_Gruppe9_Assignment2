package at.gr6.crawler;

import java.io.IOException;


public class Main {
    static String targetLanguage = "";
    static int maxDepth;
    static boolean translate = false;


    public static void main(String[] args) {
        // new Args: depth, targetlang, translate boolean, links: 2...n
        //url = args[0];

        int numberOfLinks = args.length-3;

        maxDepth = Integer.parseInt(args[0]);
        targetLanguage = args[1];
        if (args[2].equals("true"))
            translate = true;
        try {
            ReportWriter.clearFile("./report.md");
            //ExceptionLogger.initializeLogger();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CrawlerThread thread;



        for(int i = 0; i < numberOfLinks; i++){
            thread = new CrawlerThread(maxDepth,targetLanguage,translate,args[i+3]);
            thread.start();
            //System.out.println(i);
        }

    }
}