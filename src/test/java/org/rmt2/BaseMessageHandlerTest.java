package org.rmt2;

import org.junit.Before;
import org.rmt2.constants.MessagingConstants;
import org.rmt2.handlers.AbstractMessageDrivenBean;

import com.api.config.ConfigConstants;
import com.api.config.SystemConfigurator;
import com.api.util.RMT2File;
import com.api.xml.jaxb.JaxbUtil;

/**
 * Base class for testing the API handlers.
 * <p>
 * 
 * @author royterrell
 *
 */
public class BaseMessageHandlerTest extends AbstractMessageDrivenBean {

    private static String APP_CONFIG_FILENAME;
    protected JaxbUtil jaxb;
    

    @Before
    public void setUp() throws Exception {
        String curDir = RMT2File.getCurrentDirectory();
        APP_CONFIG_FILENAME = curDir + "/src/test/resources/config/TestAppServerConfig.xml";
        SystemConfigurator appConfig = new SystemConfigurator();
        appConfig.start(APP_CONFIG_FILENAME);
        
        try {
            this.jaxb = SystemConfigurator.getJaxb(ConfigConstants.JAXB_CONTEXNAME_DEFAULT);
        }
        catch (Exception e) {
            this.jaxb = new JaxbUtil(MessagingConstants.JAXB_RMT2_PKG);
        }
    }
   
}

