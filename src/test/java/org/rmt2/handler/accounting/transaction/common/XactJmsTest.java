package org.rmt2.handler.accounting.transaction.common;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import java.util.List;

import org.dao.transaction.XactDao;
import org.dao.transaction.XactDaoFactory;
import org.dto.XactDto;
import org.dto.XactTypeItemActivityDto;
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
import org.rmt2.api.handlers.transaction.XactApiHandler;
import org.rmt2.handler.BaseMockSingleConsumerMDBTest;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the Transaction API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, XactApiHandler.class, XactApiFactory.class })
public class XactJmsTest extends BaseMockSingleConsumerMDBTest {

    private static final String DESTINATION = "rmt2.queue.accounting";
    private XactApi mockApi;
    
    public static final int NEW_XACT_ID = 1234567;


    /**
     * 
     */
    public XactJmsTest() {
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
    public void invokeHandlerSuccess_Fetch() {
        String request = RMT2File.getFileContentsAsString("xml/accounting/transaction/common/TransactionQueryRequest.xml");
        List<XactDto> mockListData = AccountingMockData.createMockSingleCommonTransactions();
        List<XactTypeItemActivityDto> mockItemListData = AccountingMockData.createMockXactItems();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getXact(isA(XactDto.class))).thenReturn(mockListData);
        } catch (XactApiException e) {
            Assert.fail("Unable to setup mock stub for fetching a BASE transaction");
        }
        
        try {
            when(this.mockApi.getXactTypeItemActivityExt(isA(Integer.class))).thenReturn(mockItemListData);
        } catch (XactApiException e) {
            Assert.fail("Unable to setup mock stub for fetching a transaction line items");
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).getXact(isA(XactDto.class));
            Mockito.verify(this.mockApi).getXactTypeItemActivityExt(isA(Integer.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
  
    @Test
    public void invokeHandlerSuccess_Create() {
        String request = RMT2File.getFileContentsAsString("xml/accounting/transaction/common/TransactionCreateRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.update(isA(XactDto.class), isA(List.class))).thenReturn(NEW_XACT_ID);
        } catch (XactApiException e) {
            Assert.fail("Unable to setup mock stub for creating a transaction");
        }
        
        try {
            this.startTest();    
            Mockito.verify(this.mockApi).update(isA(XactDto.class), isA(List.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
    
    @Test
    public void invokeHandlerSuccess_Reverse() {
        String request = RMT2File.getFileContentsAsString("xml/accounting/transaction/common/TransactionReverseRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.reverse(isA(XactDto.class), isA(List.class))).thenReturn(NEW_XACT_ID);
        } catch (XactApiException e) {
            Assert.fail("Unable to setup mock stub for reversing a transaction");
        }
        
        try {
            this.startTest();    
            Mockito.verify(this.mockApi).reverse(isA(XactDto.class), isA(List.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
    
    @Test
    public void invokeHandlerSuccess_Delete() {
        String request = RMT2File.getFileContentsAsString("xml/accounting/transaction/common/TransactionDeleteRequest.xml");
        
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.deleteXact(isA(List.class))).thenReturn(3);
        } catch (XactApiException e) {
            Assert.fail("Unable to setup mock stub for deleting a transaction");
        }
        
        try {
            this.startTest();    
            Mockito.verify(this.mockApi).deleteXact(isA(List.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
    
    @Test
    public void invokeHandlerError_Fetch_Incorrect_Trans_Code() {
        String request = RMT2File
                .getFileContentsAsString("xml/accounting/transaction/common/TransactionQueryInvalidTranCodeRequest.xml");
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
