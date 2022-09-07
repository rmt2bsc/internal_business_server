package org.rmt2.handler.media.lookup;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import java.util.List;

import org.dto.GenreDto;
import org.dto.MediaTypeDto;
import org.dto.ProjectTypeDto;
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
import org.rmt2.api.handlers.lookup.LookupDataApiHandler;
import org.rmt2.handler.BaseMockSingleConsumerMDBTest;
import org.rmt2.handler.media.MediaJmsMockDtoFactory;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of all media related lookup types via JMS
 * messages for the Media API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ LookupDataApiHandler.class, AudioVideoFactory.class, JmsClientManager.class })
public class AllMediaLookupTypesJmsTest extends BaseMockSingleConsumerMDBTest {

    private static final String DESTINATION = "rmt2.queue.media";
    private AudioVideoApi mockApi;


    /**
     * 
     */
    public AllMediaLookupTypesJmsTest() {
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
        String request = RMT2File.getFileContentsAsString("xml/media/lookup/LookupTypesQueryRequest.xml");
        List<GenreDto> genreResults = MediaJmsMockDtoFactory.createGenreMockData();
        List<MediaTypeDto> mediaTypeResults = MediaJmsMockDtoFactory.createMediaTypeMockData();
        List<ProjectTypeDto> projectTypeResults = MediaJmsMockDtoFactory.createProjectTypeMockData();

        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getGenre(isA(GenreDto.class))).thenReturn(genreResults);
        } catch (AudioVideoApiException e) {
            e.printStackTrace();
            Assert.fail("Genre fetch test case failed");
        }

        try {
            when(this.mockApi.getMediaType(isA(MediaTypeDto.class))).thenReturn(mediaTypeResults);
        } catch (AudioVideoApiException e) {
            e.printStackTrace();
            Assert.fail("Media type fetch test case failed");
        }

        try {
            when(this.mockApi.getProjectType(isA(ProjectTypeDto.class))).thenReturn(projectTypeResults);
        } catch (AudioVideoApiException e) {
            e.printStackTrace();
            Assert.fail("Project type fetch test case failed");
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).getGenre(isA(GenreDto.class));
            Mockito.verify(this.mockApi).getMediaType(isA(MediaTypeDto.class));
            Mockito.verify(this.mockApi).getProjectType(isA(ProjectTypeDto.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }

}
