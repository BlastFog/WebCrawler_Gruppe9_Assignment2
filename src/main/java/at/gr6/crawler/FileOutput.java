package at.gr6.crawler;


import java.io.IOException;

public interface FileOutput {
    void writeBeginning(Page page) throws IOException;
    void writeLanguage(TranslationManager language) throws IOException;
    void writeBody(Page page) throws Exception;
    void closeFile() throws IOException;
    void closeWriter() throws IOException;

}
