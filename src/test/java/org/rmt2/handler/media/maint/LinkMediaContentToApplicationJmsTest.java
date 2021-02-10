package org.rmt2.handler.media.maint;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;

import org.dao.transaction.XactDao;
import org.dao.transaction.XactDaoFactory;
import org.dto.TimesheetDto;
import org.dto.XactDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.timesheet.TimesheetApi;
import org.modules.timesheet.TimesheetApiException;
import org.modules.timesheet.TimesheetApiFactory;
import org.modules.transaction.XactApi;
import org.modules.transaction.XactApiException;
import org.modules.transaction.XactApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.AccountingMockData;
import org.rmt2.api.handlers.timesheet.TimesheetAttachDocumentApiHandler;
import org.rmt2.api.handlers.transaction.XactAttachDocumentApiHandler;
import org.rmt2.handler.BaseMockMultipleConsumerMDBTest;
import org.rmt2.handler.projecttracker.ProjectTrackerJmsMockData;
import org.rmt2.listeners.mdb.AccountingMediaContentUpdateBean;
import org.rmt2.listeners.mdb.ProjectTrackerMediaContentUpdateBean;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the JMS operation responsible for linking
 * media content to a home application
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ XactAttachDocumentApiHandler.class, XactApiFactory.class, XactDaoFactory.class,
        TimesheetAttachDocumentApiHandler.class, TimesheetApiFactory.class, JmsClientManager.class })
public class LinkMediaContentToApplicationJmsTest extends BaseMockMultipleConsumerMDBTest {

    private static final String DESTINATION = "rmt2.topic.media";

    /**
     * Default contructor
     */
    public LinkMediaContentToApplicationJmsTest() {
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
    public void invokeHandelrSuccess_AccountingApp() {
        String request = RMT2File.getFileContentsAsString("xml/media/maint/AccountingMediaContentLinkRequest.xml");
        List<XactDto> mockListData = AccountingMockData.createMockSingleCommonTransactions();

        this.setupMocks(DESTINATION, request, AccountingMediaContentUpdateBean.class.getName());

        XactDaoFactory mockXactDaoFactory = Mockito.mock(XactDaoFactory.class);
        XactDao mockDao = Mockito.mock(XactDao.class);
        XactApi mockApi = Mockito.mock(XactApi.class);
        PowerMockito.mockStatic(XactApiFactory.class);
        when(mockXactDaoFactory.createRmt2OrmXactDao(isA(String.class))).thenReturn(mockDao);
        PowerMockito.when(XactApiFactory.createDefaultXactApi()).thenReturn(mockApi);

        try {
            when(mockApi.getXactById(isA(Integer.class))).thenReturn(mockListData.get(0));
        } catch (XactApiException e) {
            Assert.fail("Unable to setup mock stub for reversing a transaction");
        }
        try {
            when(mockApi.update(isA(XactDto.class))).thenReturn(1);
        } catch (XactApiException e) {
            Assert.fail("Unable to setup mock stub for reversing a transaction");
        }

        try {
            this.startTest();    
            Mockito.verify(mockApi).getXactById(isA(Integer.class));
            Mockito.verify(mockApi).update(isA(XactDto.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

    @Test
    public void invokeHandelrSuccess_ProjectTrackerApp() {
        String request = RMT2File.getFileContentsAsString("xml/media/maint/ProjectTrackerMediaContentLinkRequest.xml");
        List<TimesheetDto> mockListData = ProjectTrackerJmsMockData.createMockExtTimesheetList();

        this.setupMocks(DESTINATION, request, ProjectTrackerMediaContentUpdateBean.class.getName());

        TimesheetApi mockApi = Mockito.mock(TimesheetApi.class);
        PowerMockito.mockStatic(TimesheetApiFactory.class);
        TimesheetApiFactory mockApiFactory = Mockito.mock(TimesheetApiFactory.class);
        try {
            PowerMockito.whenNew(TimesheetApiFactory.class).withNoArguments().thenReturn(mockApiFactory);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        when(mockApiFactory.createApi(isA(String.class))).thenReturn(mockApi);
        doNothing().when(mockApi).close();

        try {
            when(mockApi.get(isA(Integer.class))).thenReturn(mockListData.get(0));
        } catch (TimesheetApiException e) {
            Assert.fail("Unable to setup mock stub for reversing a transaction");
        }
        try {
            when(mockApi.updateTimesheet(isA(TimesheetDto.class))).thenReturn(1);
        } catch (TimesheetApiException e) {
            Assert.fail("Unable to setup mock stub for reversing a transaction");
        }

        try {
            this.startTest();
            Mockito.verify(mockApi).get(isA(Integer.class));
            Mockito.verify(mockApi).updateTimesheet(isA(TimesheetDto.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
}
