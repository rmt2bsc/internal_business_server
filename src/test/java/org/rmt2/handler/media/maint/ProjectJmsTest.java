package org.rmt2.handler.media.maint;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import java.util.List;

import org.dto.ArtistDto;
import org.dto.VwArtistDto;
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
import org.rmt2.api.handlers.maint.AvProjectFetchApiHandler;
import org.rmt2.handler.media.MediaJmsMockDtoFactory;

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
@PrepareForTest({ AvProjectFetchApiHandler.class, AudioVideoFactory.class, JmsClientManager.class })
public class ProjectJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "rmt2.queue.media";
    private AudioVideoApi mockApi;


    /**
     * 
     */
    public ProjectJmsTest() {
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
        String request = RMT2File.getFileContentsAsString("xml/media/maint/ProjectQueryRequest.xml");
        List<VwArtistDto> apiResults = MediaJmsMockDtoFactory.createVwAudioVideoArtistsMockData();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getConsolidatedArtist(isA(VwArtistDto.class))).thenReturn(apiResults);
        } catch (AudioVideoApiException e) {
            e.printStackTrace();
            Assert.fail("Artist fetch test case failed");
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).getConsolidatedArtist(isA(VwArtistDto.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }

    @Test
    public void invokeHandelrSuccess_Update() {
        String request = RMT2File.getFileContentsAsString("xml/media/maint/ArtistUpdateRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.updateArtist(isA(ArtistDto.class))).thenReturn(1);
        } catch (AudioVideoApiException e) {
            e.printStackTrace();
            Assert.fail("Artist update test case failed");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockApi).updateArtist(isA(ArtistDto.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }

    }

}
