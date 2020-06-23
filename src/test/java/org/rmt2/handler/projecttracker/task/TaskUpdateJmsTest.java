package org.rmt2.handler.projecttracker.task;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import org.dto.TaskDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.ProjectTrackerApiConst;
import org.modules.admin.ProjectAdminApi;
import org.modules.admin.ProjectAdminApiException;
import org.modules.admin.ProjectAdminApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.admin.task.TaskUpdateApiHandler;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the task update related JMS messages for
 * the Project Tracker API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ TaskUpdateApiHandler.class, JmsClientManager.class, ProjectAdminApiFactory.class })
public class TaskUpdateJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "rmt2.queue.projecttracker";
    public static final int TASK_ID = 39;
    private ProjectAdminApi mockApi;


    /**
     * 
     */
    public TaskUpdateJmsTest() {
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
        this.mockApi = Mockito.mock(ProjectAdminApi.class);
        PowerMockito.mockStatic(ProjectAdminApiFactory.class);
        when(ProjectAdminApiFactory.createApi(eq(ProjectTrackerApiConst.APP_NAME))).thenReturn(this.mockApi);
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
    public void invokeHandelrSuccess_Update() {
        String request = RMT2File.getFileContentsAsString("xml/projecttracker/admin/TaskUpdateRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.updateTask(isA(TaskDto.class))).thenReturn(1);
        } catch (ProjectAdminApiException e) {
            e.printStackTrace();
            Assert.fail("Task update test case failed");
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).updateTask(isA(TaskDto.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
    
}
