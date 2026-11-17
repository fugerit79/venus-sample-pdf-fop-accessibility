package org.fugerit.java.demo.venussamplepdffopaccessibility;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.doc.base.config.DocTypeHandler;
import org.fugerit.java.doc.freemarker.process.FreemarkerDocProcessConfig;
import org.fugerit.java.doc.freemarker.process.FreemarkerDocProcessConfigFacade;
import org.fugerit.java.doc.mod.pdfbox.PdfBoxConfig;
import org.fugerit.java.doc.mod.pdfbox.PdfBoxTypeHandler;

import java.util.Properties;

/**
 * DocHelper, version : auto generated on 2025-10-03 10:02:02.616
 */
public class DocHelper {

     /*
      * FreemarkerDocProcessConfig is thread-safe and should be initialized once for each config file.
      * 
      * Consider using a @ApplicationScoped or Singleton approach.
      */
     private FreemarkerDocProcessConfig docProcessConfig = initConfig();

     private static FreemarkerDocProcessConfig initConfig() {
          FreemarkerDocProcessConfig config = FreemarkerDocProcessConfigFacade.loadConfigSafe(
                  "cl://venus-sample-pdf-fop-accessibility/fm-doc-process-config.xml" );
          // fj-doc-mod-pdfbox 8.18.8 configures PDF/UA via configure(Properties) rather than XML handleConfigTag(Element)
          DocTypeHandler pdfBoxUa = config.getFacade().findHandler( "pdf-pdfbox-pdf-ua" );
          if ( pdfBoxUa instanceof PdfBoxTypeHandler ) {
               Properties props = new Properties();
               props.setProperty( PdfBoxConfig.PDFUA_ENABLED, Boolean.TRUE.toString() );
               try {
                    ((PdfBoxTypeHandler) pdfBoxUa).configure( props );
               } catch (ConfigException e) {
                    throw new ConfigRuntimeException( e );
               }
          }
          return config;
     }

     /**
      * Accessor for FreemarkerDocProcessConfig configuration.
      *
      * @return the FreemarkerDocProcessConfig instance associated with this helper.
      */
     public FreemarkerDocProcessConfig getDocProcessConfig() { return this.docProcessConfig; }

}
