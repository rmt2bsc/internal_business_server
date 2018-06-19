package org.rmt2;

import org.junit.Before;
import org.rmt2.handlers.AbstractMessageDrivenBean;

import com.api.config.SystemConfigurator;
import com.util.RMT2File;

/**
 * Base class for testing the API handlers.
 * <p>
 * 
 * @author royterrell
 *
 */
public class BaseMessageHandlerTest extends AbstractMessageDrivenBean {

    private static String APP_CONFIG_FILENAME;
    
    

    @Before
    public void setUp() throws Exception {
        String curDir = RMT2File.getCurrentDirectory();
        APP_CONFIG_FILENAME = curDir + "/src/test/resources/config/TestAppServerConfig.xml";
        SystemConfigurator appConfig = new SystemConfigurator();
        appConfig.start(APP_CONFIG_FILENAME);
    }

}

