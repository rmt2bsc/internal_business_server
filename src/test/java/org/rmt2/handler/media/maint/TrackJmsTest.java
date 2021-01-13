package org.rmt2.handler.media.maint;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import java.util.List;

import org.dto.TracksDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.audiovideo.AudioVideoApi;
import org.modules.audiovideo.AudioVideoApiException;
import org.modules.audiovideo.AudioVideoFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.maint.TrackFetchApiHandler;
import org.rmt2.handler.media.MediaJmsMockDtoFactory;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the track related JMS messages for the
 * Media API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ TrackFetchApiHandler.class, AudioVideoFactory.class, JmsClientManager.class })
public class TrackJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "rmt2.queue.media";
    private AudioVideoApi mockApi;


    /**
     * 
     */
    public TrackJmsTest() {
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
        this.mockApi = Mockito.mock(AudioVideoApi.class);
        PowerMockito.mockStatic(AudioVideoFactory.class);
        when(AudioVideoFactory.createApi()).thenReturn(this.mockApi);
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
    public void invokeHandelrSuccess_Fetch() {
        String request = RMT2File.getFileContentsAsString("xml/media/maint/TrackQueryRequest.xml");
        List<TracksDto> apiResults = MediaJmsMockDtoFactory.createTrackMockData();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getTracks(isA(TracksDto.class))).thenReturn(apiResults);
        } catch (AudioVideoApiException e) {
            e.printStackTrace();
            Assert.fail("Track fetch test case failed");
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).getTracks(isA(TracksDto.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }

    @Test
    public void invokeHandelrSuccess_Update() {
        String request =
                RMT2File.getFileContentsAsString("xml/media/maint/TrackUpdateRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.updateTrack(isA(TracksDto.class))).thenReturn(1);
        } catch (AudioVideoApiException e) {
            e.printStackTrace();
            Assert.fail("Track update test case failed");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockApi).updateTrack(isA(TracksDto.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
}
