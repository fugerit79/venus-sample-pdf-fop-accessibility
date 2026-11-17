// generated from template 'DocHelperTest.ftl' on 2025-10-03T10:02:02.619+02:00
package test.org.fugerit.java.demo.venussamplepdffopaccessibility;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDStructureElement;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDStructureTreeRoot;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.pdmodel.interactive.viewerpreferences.PDViewerPreferences;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.util.Matrix;
import org.fugerit.java.core.io.FileIO;
import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.demo.venussamplepdffopaccessibility.DocHelper;
import org.fugerit.java.demo.venussamplepdffopaccessibility.People;

import org.fugerit.java.doc.base.config.DocConfig;
import org.fugerit.java.doc.base.config.DocTypeHandler;
import org.fugerit.java.doc.base.process.DocProcessContext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

/**
 * This is a basic example of Fugerit Venus Doc usage,
 * running this junit will :
 * - creates data to be used in document model
 * - renders the 'document.ftl' template
 * - print the result in markdown format
 *
 * For further documentation :
 * https://github.com/fugerit-org/fj-doc
 *
 * NOTE: This is a 'Hello World' style example, adapt it to your scenario, especially :
 *  - change the doc handler and the output mode (here a ByteArrayOutputStream buffer is used)
 */
@Slf4j
class DocHelperTest {

    private static final String ID_XML = "xml"; // XML output for debug

    private static final String ID_PDF_FOP_FO = "fo-fop"; // XSL-FO rendering for debug

    private static final String ID_PDF_FOP_PLAIN = "pdf-fop-plain"; // FOP handler with no additional configuration

    private static final String ID_PDF_FOP_CONFIG = "pdf-fop-config"; // FOP handler with standard configuration file (fop-config.xml)

    private static final String ID_PDF_FOP_PDF_A = "pdf-fop-pdf-a"; // FOP handler with PDF/A-1b profile (fop-config-pdf-a.xml)

    private static final String ID_PDF_FOP_PDF_UA = "pdf-fop-pdf-ua"; // FOP handler with PDF/UA-1 profile (fop-config-pdf-ua.xml)

    private static final String ID_PDF_FOP_PDF_UA_NO_FONT_EMBEDDING = "pdf-fop-pdf-ua-no-font-embedding"; // FOP handler with PDF/UA-1 profile wit no embedded font (fop-config-pdf-ua-no-font-embedding.xml)

    private static final String ID_PDF_OPENPDF_PLAIN = "pdf-openpdf-plain"; // OpenPDF handler without PDF/UA-1 mode

    private static final String ID_PDF_OPENPDF_PDF_UA = "pdf-openpdf-pdf-ua"; // OpenPDF handler with activatePdfUA1="true"

    private static final String ID_PDF_PDFBOX_PLAIN = "pdf-pdfbox-plain"; // PDFBox handler without PDF/UA-1 mode

    private static final String ID_PDF_PDFBOX_PDF_UA = "pdf-pdfbox-pdf-ua"; // PDFBox handler with pdfuaEnabled="true"


    private String toOutputFileName( DocHelper docHelper, String prefix, String handlerId ) {
        DocTypeHandler handler = docHelper.getDocProcessConfig().getFacade().findHandler( handlerId );
        return String.format( "target/%s_%s.%s", prefix, handlerId, handler.getType() );
    }

    @Test
    void testDocProcess() throws Exception {
        // creates the doc helper
        DocHelper docHelper = new DocHelper();
        byte[] data = FileIO.readBytes( "src/main/docs/images/check-ok-no-transparency.jpg" );
        String imageBase64 = Base64.getEncoder().encodeToString( data );
        // create custom data for the fremarker template 'document.ftl'
        List<People> listPeople = Arrays.asList( new People( "Luthien", "Tinuviel", "Queen" ), new People( "Thorin", "Oakshield", "King" ) );
        String chainId = "document";
        List<String> handlerList = Arrays.asList(
                ID_XML, ID_PDF_FOP_FO,
                ID_PDF_FOP_PLAIN, ID_PDF_FOP_CONFIG, ID_PDF_FOP_PDF_A, ID_PDF_FOP_PDF_UA, ID_PDF_FOP_PDF_UA_NO_FONT_EMBEDDING,
                ID_PDF_OPENPDF_PLAIN, ID_PDF_OPENPDF_PDF_UA,
                ID_PDF_PDFBOX_PLAIN, ID_PDF_PDFBOX_PDF_UA
        );
        for ( String handlerId :  handlerList) {
            File outputFile = new File( this.toOutputFileName(docHelper, chainId, handlerId ) );
            log.info( "delete {}:{}" , outputFile.getCanonicalPath(), outputFile.delete() );
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                log.info( "generating chainId:{}, handlerId:{}, outputFile:{}",  chainId, handlerId, outputFile.getCanonicalPath() );
                docHelper.getDocProcessConfig().fullProcess( chainId, DocProcessContext.newContext( "listPeople", listPeople ).withAtt( "checkImageBase64", imageBase64 ), handlerId, fos );
            }
            Assertions.assertTrue( outputFile.length() > 0 );
        }
        log.info( "output size comparison" );
        for ( String handlerId :  handlerList) {
            File outputFile = new File( this.toOutputFileName(docHelper, chainId, handlerId ) );
            log.info( "size {}:{}" , outputFile.getName(), outputFile.length() );
        }
        for ( String pdfUaHandlerId : Arrays.asList( ID_PDF_FOP_PDF_UA, ID_PDF_OPENPDF_PDF_UA, ID_PDF_PDFBOX_PDF_UA ) ) {
            File outputFile = new File( this.toOutputFileName( docHelper, chainId, pdfUaHandlerId ) );
            this.inspectPdfUaAccessibility( pdfUaHandlerId, outputFile );
        }
    }

    private void inspectPdfUaAccessibility( String handlerId, File pdfFile ) throws IOException {
        try ( PDDocument document = Loader.loadPDF( pdfFile ) ) {
            PDDocumentCatalog catalog = document.getDocumentCatalog();
            boolean isMarked = catalog.getMarkInfo() != null && catalog.getMarkInfo().isMarked();
            PDStructureTreeRoot structureTreeRoot = catalog.getStructureTreeRoot();
            String language = catalog.getLanguage();
            PDViewerPreferences viewerPrefs = catalog.getViewerPreferences();
            boolean displayDocTitle = viewerPrefs != null && viewerPrefs.displayDocTitle();

            String xmpContent = "";
            PDMetadata metadata = catalog.getMetadata();
            if ( metadata != null ) {
                try ( InputStream is = metadata.exportXMPMetadata() ) {
                    xmpContent = new String( StreamIO.readBytes( is ), StandardCharsets.UTF_8 );
                }
            }
            boolean hasPdfUaXmp = xmpContent.contains( "pdfuaid:part" );

            List<String> fontSummary = new ArrayList<>();
            for ( PDPage page : document.getPages() ) {
                if ( page.getResources() != null ) {
                    for ( org.apache.pdfbox.cos.COSName fontKey : page.getResources().getFontNames() ) {
                        PDFont font = page.getResources().getFont( fontKey );
                        if ( font != null ) {
                            fontSummary.add( font.getName() + "(embedded=" + font.isEmbedded() + ")" );
                        }
                    }
                }
            }

            List<String> rootStructTypes = new ArrayList<>();
            if ( structureTreeRoot != null ) {
                for ( Object kid : structureTreeRoot.getKids() ) {
                    if ( kid instanceof PDStructureElement ) {
                        PDStructureElement elem = (PDStructureElement) kid;
                        rootStructTypes.add( elem.getStructureType() + "(children=" + elem.getKids().size() + ")" );
                    } else if ( kid instanceof COSBase ) {
                        rootStructTypes.add( kid.toString() );
                    }
                }
            }

            String text = new PDFTextStripper().getText( document );
            log.info(
                    "PDF/UA inspection [{}] -> size={} bytes, pdfVersion={}, pages={}, marked={}, lang={}, displayDocTitle={}, pdfUaXmp={}, structTree={}, rootStruct={}, fonts={}",
                    handlerId, pdfFile.length(), document.getVersion(), document.getNumberOfPages(),
                    isMarked, language, displayDocTitle, hasPdfUaXmp,
                    structureTreeRoot != null, rootStructTypes, fontSummary
            );

            Assertions.assertTrue( isMarked, handlerId + " must have MarkInfo.Marked=true" );
            Assertions.assertNotNull( structureTreeRoot, handlerId + " must have StructureTreeRoot" );
            Assertions.assertNotNull( structureTreeRoot.getK(), handlerId + " must have StructureTreeRoot /K" );
            Assertions.assertEquals( "en", language, handlerId + " must have catalog /Lang=en" );
            Assertions.assertTrue( displayDocTitle, handlerId + " must have ViewerPreferences DisplayDocTitle=true" );
            Assertions.assertTrue( hasPdfUaXmp, handlerId + " must include pdfuaid:part in XMP metadata" );
            Assertions.assertTrue( text.contains( "Luthien" ), handlerId + " text must contain Luthien" );
            Assertions.assertTrue( text.contains( "Thorin" ), handlerId + " text must contain Thorin" );
        }
    }

    public static final String ATT_WATERMARK_MODE = "watermarkMode";

    public static final String ATT_WATERMARK_MODE_CUSTOM = "custom";

    private File testWorker( String watermarkMode  ) throws Exception {
        String chainId = "document";
        // handler id
        String handlerId = ID_PDF_FOP_PDF_UA;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // creates the doc helper
            DocHelper docHelper = new DocHelper();
            // create custom data for the fremarker template 'document.ftl'
            List<People> listPeople = Arrays.asList(
                    new People("Luthien", "Tinuviel", "Queen"), new People("Thorin", "Oakshield", "King"));

            DocProcessContext context = DocProcessContext.newContext("listPeople", listPeople)
                    .withAtt(ATT_WATERMARK_MODE, watermarkMode );

            // output generation
            docHelper.getDocProcessConfig().fullProcess(chainId, context, handlerId, baos);

            File outputFile = new File( "target/", String.format( "%s-watermark-%s.%s", chainId, watermarkMode, DocConfig.TYPE_PDF ) );
            log.info( "delete file? : {} ({})" , outputFile.delete(), outputFile );

            if ( ATT_WATERMARK_MODE_CUSTOM.equalsIgnoreCase( watermarkMode ) ) {
                FileIO.writeBytes( addTextWatermark( baos.toByteArray(), "watermark" ), outputFile );
            } else {
                FileIO.writeBytes(baos.toByteArray(), outputFile);
            }
            return outputFile;
        }
    }

    @Test
    void testDocProcessTemplateWatermark() throws Exception {
        File output = this.testWorker( "template" );
        Assertions.assertNotEquals( 0, output.length() );
    }

    @Test
    void testDocProcessCustomHandlerWatermark() throws Exception {
        File output = this.testWorker( ATT_WATERMARK_MODE_CUSTOM );
        Assertions.assertNotEquals( 0, output.length() );
    }

    private static byte[] addTextWatermark( byte[] input, String watermarkText) throws IOException {
        try (PDDocument document = Loader.loadPDF( input );
             ByteArrayOutputStream buffer = new ByteArrayOutputStream();
             InputStream fontStream = ClassHelper.loadFromDefaultClassLoader( "font/TitilliumWeb-Regular.ttf" )) {

            // Load font from file system
            PDType0Font font = PDType0Font.load(document,fontStream);

            // Ensure document has structure tree for tagging
            PDStructureTreeRoot structureTreeRoot = document.getDocumentCatalog()
                    .getStructureTreeRoot();

            if (structureTreeRoot == null) {
                structureTreeRoot = new PDStructureTreeRoot();
                document.getDocumentCatalog().setStructureTreeRoot(structureTreeRoot);
            }

            // Iterate through all pages
            for (PDPage page : document.getPages()) {

                PDPageContentStream contentStream = new PDPageContentStream(
                        document, page, PDPageContentStream.AppendMode.APPEND, true, true);

                // Begin marked content for accessibility
                contentStream.beginMarkedContent(
                        org.apache.pdfbox.cos.COSName.ARTIFACT);

                // Set transparency
                PDExtendedGraphicsState graphicsState = new PDExtendedGraphicsState();
                graphicsState.setNonStrokingAlphaConstant(0.3f);
                contentStream.setGraphicsStateParameters(graphicsState);

                // Set font and color
                contentStream.setFont(font, 120);
                contentStream.setNonStrokingColor( 0.2F, 0.2F, 0.2F); // Light gray

                // Calculate position (center of page, diagonal)
                float pageWidth = page.getMediaBox().getWidth();
                float pageHeight = page.getMediaBox().getHeight();

                // Position and rotate the text
                contentStream.beginText();
                contentStream.setTextMatrix(
                        Matrix.getRotateInstance(Math.toRadians(45),
                                pageWidth / 5,
                                pageHeight / 5));
                contentStream.showText(watermarkText);
                contentStream.endText();

                // Begin marked content for accessibility
                contentStream.endMarkedContent();

                contentStream.close();
            }
            document.save(buffer);
            return buffer.toByteArray();
        }
    }


}
