package org.rmt2.handler.media.listener;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.document.DocumentContentApi;
import org.modules.document.DocumentContentApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.api.handlers.listener.MediaFileListenerStartApiHandler;
import org.rmt2.handler.BaseMockSingleConsumerMDBTest;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the artist related JMS messages for the
 * Media API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ MediaFileListenerStartApiHandler.class, DocumentContentApiFactory.class, JmsClientManager.class })
public class MediaFileListenerJmsTest extends BaseMockSingleConsumerMDBTest {

    private static final String DESTINATION = "rmt2.queue.media";
    private DocumentContentApi mockApi;


    /**
     * 
     */
    public MediaFileListenerJmsTest() {
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
    public void invokeHandelrSuccess_Start() {
        String request = RMT2File.getFileContentsAsString("xml/media/listener/MediaFileListenerStartRequest.xml");
        this.setupMocks(DESTINATION, request);
        doNothing().when(this.mockApi).startMediaFileListener();

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).startMediaFileListener();
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }

    @Test
    public void invokeHandelrSuccess_Stop() {
        String request = RMT2File.getFileContentsAsString("xml/media/listener/MediaFileListenerStopRequest.xml");
        this.setupMocks(DESTINATION, request);
        doNothing().when(this.mockApi).stopMediaFileListener();

        try {
            this.startTest();
            Mockito.verify(this.mockApi).stopMediaFileListener();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }

    }

    @Test
    public void invokeHandelrSuccess_HealthCheck() {
        String request = RMT2File.getFileContentsAsString("xml/media/listener/MediaFileListenerHealthCheckRequest.xml");
        this.setupMocks(DESTINATION, request);
        when(this.mockApi.getMediaFileListenerStatus()).thenReturn("Running");

        try {
            this.startTest();
            Mockito.verify(this.mockApi).getMediaFileListenerStatus();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }

    }
}
