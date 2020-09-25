package org.rmt2.handler.media.batch;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.audiovideo.batch.AvBatchFileFactory;
import org.modules.audiovideo.batch.AvBatchFileProcessorApi;
import org.modules.audiovideo.batch.AvBatchImportParameters;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.batch.VideoMetadataBatchImportApiHandler;

import com.api.BatchFileException;
import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the video batch import related JMS
 * messages for the Media API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ VideoMetadataBatchImportApiHandler.class, AvBatchFileFactory.class, JmsClientManager.class })
public class VideoBatchImportJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "rmt2.queue.media";
    private AvBatchFileProcessorApi mockApi;


    /**
     * 
     */
    public VideoBatchImportJmsTest() {
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
        this.mockApi = Mockito.mock(AvBatchFileProcessorApi.class);
        PowerMockito.mockStatic(AvBatchFileFactory.class);
        when(AvBatchFileFactory.createCsvBatchImportApiInstance(isA(AvBatchImportParameters.class))).thenReturn(this.mockApi);
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
    public void invokeHandelrSuccess_Import() {
        String request = RMT2File.getFileContentsAsString("xml/media/batch/VideoMetadataImportBatchRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.processBatch()).thenReturn(100);
        } catch (BatchFileException e) {
            Assert.fail("Unable to setup mock stub for audio batch file import");
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).processBatch();
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }

}
