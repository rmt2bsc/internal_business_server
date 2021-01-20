package org.rmt2.handler.media.maint;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import org.dto.ContentDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.MediaModuleException;
import org.modules.document.DocumentContentApi;
import org.modules.document.DocumentContentApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.maint.DocumentManualUploadApiHandler;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the media manual upload related JMS
 * messages for the Media API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ DocumentManualUploadApiHandler.class, DocumentContentApiFactory.class, JmsClientManager.class })
public class DocumentManualUploadJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "rmt2.queue.media";
    private static final int NEW_CONTENT_ID = 12345;
    private DocumentContentApi mockApi;


    /**
     * 
     */
    public DocumentManualUploadJmsTest() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see testcases.messaging.MessageToListenerToHandlerTest#setUp()
     */
    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.mockApi = Mockito.mock(DocumentContentApi.class);
        PowerMockito.mockStatic(DocumentContentApiFactory.class);
        when(DocumentContentApiFactory.createMediaContentApi()).thenReturn(this.mockApi);
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see testcases.messaging.MessageToListenerToHandlerTest#tearDown()
     */
    @After
    public void tearDown() throws Exception {
        return;
    }

    @Test
    public void invokeHandelrSuccess_Manual_Upload() {
        String request = RMT2File.getFileContentsAsString("xml/media/maint/DocumentManualUploadRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.add(isA(ContentDto.class))).thenReturn(NEW_CONTENT_ID);
        } catch (MediaModuleException e) {
            e.printStackTrace();
            Assert.fail("Media manual upload test case failed");
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).add(isA(ContentDto.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }


}
