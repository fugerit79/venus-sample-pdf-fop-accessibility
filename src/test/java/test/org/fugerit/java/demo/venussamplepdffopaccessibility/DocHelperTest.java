// generated from template 'DocHelperTest.ftl' on 2025-10-03T10:02:02.619+02:00
package test.org.fugerit.java.demo.venussamplepdffopaccessibility;

import org.fugerit.java.demo.venussamplepdffopaccessibility.DocHelper;
import org.fugerit.java.demo.venussamplepdffopaccessibility.People;

import org.fugerit.java.doc.base.config.DocConfig;
import org.fugerit.java.doc.base.config.DocTypeHandler;
import org.fugerit.java.doc.base.process.DocProcessContext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import lombok.AllArgsConstructor;

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

    private static final String ID_PDF_FOP_PLAIN = "pdf-fop-plain"; // FOP handler with no additional configuration

    private static final String ID_PDF_FOP_CONFIG = "pdf-fop-config"; // FOP handler with standard configuration file (fop-config.xml)

    private static final String ID_PDF_FOP_PDF_A = "pdf-fop-pdf-a"; // FOP handler with PDF/A-1b profile (fop-config-pdf-a.xml)

    private String toOutputFileName( DocHelper docHelper, String prefix, String handlerId ) {
        DocTypeHandler handler = docHelper.getDocProcessConfig().getFacade().findHandler( handlerId );
        return String.format( "target/%s_%s.%s", prefix, handlerId, handler.getType() );
    }

    @Test
    void testDocProcess() throws Exception {
        // creates the doc helper
        DocHelper docHelper = new DocHelper();
        // create custom data for the fremarker template 'document.ftl'
        List<People> listPeople = Arrays.asList( new People( "Luthien", "Tinuviel", "Queen" ), new People( "Thorin", "Oakshield", "King" ) );
        String chainId = "document";
        List<String> handlerList = Arrays.asList( ID_PDF_FOP_PLAIN, ID_PDF_FOP_CONFIG, ID_PDF_FOP_PDF_A );
        for ( String handlerId :  handlerList) {
            File outputFile = new File( this.toOutputFileName(docHelper, chainId, handlerId ) );
            log.info( "delete {}:{}", outputFile.getCanonicalPath(), outputFile.delete() );
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                log.info( "generating chainId:{}, handlerId:{}, outputFile:{}",  chainId, handlerId, outputFile.getCanonicalPath() );
                docHelper.getDocProcessConfig().fullProcess( chainId, DocProcessContext.newContext( "listPeople", listPeople ), handlerId, fos );
            }
            Assertions.assertTrue( outputFile.length() > 0 );
        }
        log.info( "output size comparison" );
        for ( String handlerId :  handlerList) {
            File outputFile = new File( this.toOutputFileName(docHelper, chainId, handlerId ) );
            log.info( "size {}:{}", outputFile.getName(), outputFile.length() );
        }
    }

}
