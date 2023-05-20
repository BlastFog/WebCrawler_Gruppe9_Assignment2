package at.gr6.crawler;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileOutput {
    private final String path;
    private final FileWriter fileWriter;
    PageFormatter pageFormatter;


    public static void clearFile(String path) throws IOException {
        PrintWriter writer = new PrintWriter(path);
        writer.print("");
        writer.close();
    }

    public FileOutput(String path) throws IOException {
        this.path = path;
        fileWriter = new FileWriter(this.path,true);
    }


    public void writeBeginning(Page page) throws IOException {
        fileWriter.write("-----START OF FILE-----\n");
        fileWriter.write("input: <a>" + page.getUrl() + "</a>");
        fileWriter.write("\n");
    }

    public void writeLanguage(Translation language) throws IOException {
        fileWriter.write("<br>source language: " + language.getSourceLang());
        fileWriter.write("\n");
        fileWriter.write("<br>target language: " + language.getTargetLang());
        fileWriter.write("\n");
        fileWriter.write("<br>summary: ");
        fileWriter.write("\n");
    }

    public void writeBody(Page page) throws Exception {
        pageFormatter = new PageFormatter(page);
        pageFormatter.generateOutputString();
        fileWriter.write(pageFormatter.getOutputString());
    }


    public void closeFile() throws IOException {
        fileWriter.write("\n-----END OF FILE-----\n");
        fileWriter.close();
    }
    public void  closeWriter() throws IOException {
        fileWriter.close();
    }
}
