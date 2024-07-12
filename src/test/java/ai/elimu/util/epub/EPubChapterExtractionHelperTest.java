package ai.elimu.util.epub;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EPubChapterExtractionHelperTest {
    
    private final Logger logger = LogManager.getLogger();
    
    @Test
    public void testExtractChapterReferencesFromTableOfContentsFile_GLOBAL_DIGITAL_LIBRARY() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubChapterExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-gdl-761.epub_toc.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> chapterReferences = EPubChapterExtractionHelper.extractChapterReferencesFromTableOfContentsFile(xhtmlFile);
        assertEquals(12, chapterReferences.size());
    }
    
    @Test
    public void testExtractChapterReferencesFromTableOfContentsFile_GLOBAL_DIGITAL_LIBRARY_hin_nav() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubChapterExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-gdl-bear-walk.epub_nav.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> chapterReferences = EPubChapterExtractionHelper.extractChapterReferencesFromTableOfContentsFile(xhtmlFile);
        assertEquals(32, chapterReferences.size());
    }
    
    @Test
    public void testExtractChapterReferencesFromTableOfContentsFile_LETS_READ_ASIA() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubChapterExtractionHelper.class);
        Resource resource = resourceLoader.getResource("fil-lra-0acfe340-6116-4f8a-a45d-c925c8a1fd0e.epub_toc.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> chapterReferences = EPubChapterExtractionHelper.extractChapterReferencesFromTableOfContentsFile(xhtmlFile);
        assertEquals(12, chapterReferences.size());
    }
    
    @Test
    public void testExtractChapterReferencesFromTableOfContentsFile_STORYWEAVER() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubChapterExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-sw-11791-ghumkature-bhim.epub_toc.ncx");
        File ncxFile = resource.getFile();
        logger.debug("ncxFile: " + ncxFile);
        
        List<String> chapterReferences = EPubChapterExtractionHelper.extractChapterReferencesFromTableOfContentsFileNcx(ncxFile);
        assertEquals(10, chapterReferences.size());
    }
}
