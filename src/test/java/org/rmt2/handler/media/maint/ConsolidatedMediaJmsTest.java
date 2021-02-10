package org.rmt2.handler.media.maint;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import java.util.List;

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
import org.rmt2.api.handlers.maint.ConsolidatedMediaFetchApiHandler;
import org.rmt2.handler.BaseMockSingleConsumerMDBTest;
import org.rmt2.handler.media.MediaJmsMockDtoFactory;
import org.rmt2.handler.media.MediaJmsMockOrmFactory;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the consolidated media related JMS
 * messages for the Media API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ConsolidatedMediaFetchApiHandler.class, AudioVideoFactory.class, JmsClientManager.class })
public class ConsolidatedMediaJmsTest extends BaseMockSingleConsumerMDBTest {

    private static final String DESTINATION = "rmt2.queue.media";
    private AudioVideoApi mockApi;


    /**
     * 
     */
    public ConsolidatedMediaJmsTest() {
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
        String request = RMT2File.getFileContentsAsString("xml/media/maint/ConsolidateMediaQueryRequest.xml");
        List<VwArtistDto> apiResults = MediaJmsMockDtoFactory.createConsolidatedMediaDistinctMockData();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getConsolidatedArtist(isA(VwArtistDto.class))).thenReturn(apiResults);
        } catch (AudioVideoApiException e) {
            e.printStackTrace();
            Assert.fail("Consolidated media fetch test case failed");
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
    public void invokeHandelrSuccess_Fetch_SearchTerm() {
        String request = RMT2File.getFileContentsAsString("xml/media/maint/ConsolidateMediaWithSingleSearchTermQueryRequest.xml");
        List<VwArtistDto> mockListData = MediaJmsMockDtoFactory.createConsolidatedMediaDistinctMockData();

        // Modify data set for testing purposes
        mockListData.get(0).setArtistName("After 7");

        mockListData.get(1).setArtistId(MediaJmsMockOrmFactory.TEST_ARTIST_ID + 1);
        mockListData.get(1).setProjectId(MediaJmsMockOrmFactory.TEST_PROJECT_ID +
                1);
        mockListData.get(1).setArtistName("Afterlife");

        mockListData.get(2).setArtistId(MediaJmsMockOrmFactory.TEST_ARTIST_ID + 2);
        mockListData.get(2).setProjectId(MediaJmsMockOrmFactory.TEST_PROJECT_ID +
                2);
        mockListData.get(2).setProjectName("Afterlife Again");

        mockListData.get(3).setArtistId(MediaJmsMockOrmFactory.TEST_ARTIST_ID + 3);
        mockListData.get(3).setProjectId(MediaJmsMockOrmFactory.TEST_PROJECT_ID +
                3);
        mockListData.get(3).setTrackName("After The Love Is Gone");

        mockListData.get(4).setArtistId(MediaJmsMockOrmFactory.TEST_ARTIST_ID + 4);
        mockListData.get(4).setProjectId(MediaJmsMockOrmFactory.TEST_PROJECT_ID +
                4);
        mockListData.get(4).setTrackName("After The Storm");

        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getConsolidatedArtist(isA(VwArtistDto.class))).thenReturn(mockListData);
        } catch (AudioVideoApiException e) {
            Assert.fail("Unable to setup mock stub for fetching vw_audio_video_artist records");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockApi).getConsolidatedArtist(isA(VwArtistDto.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
}
