package org.rmt2.handler.accounting.transaction;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import java.util.List;

import org.dao.transaction.XactDao;
import org.dao.transaction.XactDaoFactory;
import org.dto.XactCodeGroupDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.transaction.XactApi;
import org.modules.transaction.XactApiException;
import org.modules.transaction.XactApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.AccountingMockData;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.transaction.XactGroupApiHandler;

import com.api.messaging.jms.JmsClientManager;
import com.api.persistence.DaoClient;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the Transaction Group API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, XactGroupApiHandler.class, XactApiFactory.class })
public class XactGroupJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "Test-Accounting-Queue";
    private XactApi mockApi;
    private DaoClient mockDaoClient;


    /**
     * 
     */
    public XactGroupJmsTest() {
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
        XactDaoFactory mockXactDaoFactory = Mockito.mock(XactDaoFactory.class);
        XactDao mockDao = Mockito.mock(XactDao.class);
        mockApi = Mockito.mock(XactApi.class);
        PowerMockito.mockStatic(XactApiFactory.class);
        when(mockXactDaoFactory.createRmt2OrmXactDao(isA(String.class))).thenReturn(mockDao);
        PowerMockito.when(XactApiFactory.createDefaultXactApi()).thenReturn(this.mockApi);
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
    public void invokeHandlerSuccess_FetchTransactionGroups() {
        String request = RMT2File.getFileContentsAsString("xml/accounting/transaction/codes/TransactionGroupQueryRequest.xml");
        List<XactCodeGroupDto> mockListData = AccountingMockData.createMockXactGroup();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getGroup(isA(XactCodeGroupDto.class))).thenReturn(mockListData);
        } catch (XactApiException e) {
            Assert.fail("Unable to setup mock stub for fetching a transaction groups");
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).getGroup(isA(XactCodeGroupDto.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
  
    
    @Test
    public void invokeHandlerError_Fetch_Incorrect_Trans_Code() {
        String request = RMT2File
                .getFileContentsAsString("xml/accounting/transaction/codes/TransactionGroupQueryInvalidTransCodeRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            this.startTest(); 
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
  
}
