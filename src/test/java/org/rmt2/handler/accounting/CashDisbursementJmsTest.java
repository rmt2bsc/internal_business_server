package org.rmt2.handler.accounting;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;

import org.dao.transaction.XactDao;
import org.dao.transaction.XactDaoFactory;
import org.dto.XactCustomCriteriaDto;
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
import org.modules.transaction.disbursements.DisbursementsApi;
import org.modules.transaction.disbursements.DisbursementsApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.AccountingMockData;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.transaction.cashdisbursement.CashDisbursementApiHandler;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the Transaction Group API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, 
    //XactApiHandler.class,
    XactApiFactory.class, 
    CashDisbursementApiHandler.class, DisbursementsApiFactory.class })
public class CashDisbursementJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "Test-Accounting-Queue";
    private DisbursementsApi mockCashDisbApi;
    
    public static final int NEW_XACT_ID = 1234567;


    /**
     * 
     */
    public CashDisbursementJmsTest() {
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
        
        // Setup Xact DAO mocks
        XactDaoFactory mockXactDaoFactory = Mockito.mock(XactDaoFactory.class);
        XactDao mockDao = Mockito.mock(XactDao.class);
        when(mockXactDaoFactory.createRmt2OrmXactDao(isA(String.class))).thenReturn(mockDao);
        
        // Setup Xact API mocks
        XactCustomCriteriaDto mockCustomCriteriaDto = XactApiFactory.createCustomCriteriaInstance();
        XactApi mockXactApi = Mockito.mock(XactApi.class);
        PowerMockito.mockStatic(XactApiFactory.class);
        PowerMockito.when(XactApiFactory.createDefaultXactApi()).thenReturn(mockXactApi);
        PowerMockito.when(XactApiFactory.createCustomCriteriaInstance()).thenReturn(mockCustomCriteriaDto);
        
        // Setup Cash Disbursement API Mocks
        mockCashDisbApi = Mockito.mock(DisbursementsApi.class);
        PowerMockito.mockStatic(DisbursementsApiFactory.class);
        PowerMockito.when(DisbursementsApiFactory.createApi()).thenReturn(this.mockCashDisbApi);
        doNothing().when(this.mockCashDisbApi).close();
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
    public void invokeHandlerWithoutCustomCriteriaSuccess_Fetch() {
        String request = RMT2File.getFileContentsAsString("xml/transaction/cashdisbursement/CashDisbursementBasicQueryRequestFull.xml");
        List<XactDto> mockListData = AccountingMockData.createMockSingleCommonTransactions();
        List<XactTypeItemActivityDto> mockItemListData = AccountingMockData.createMockXactItems();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockCashDisbApi.get(isA(XactDto.class), eq((XactCustomCriteriaDto) null)))
                            .thenReturn(mockListData);
        } catch (XactApiException e) {
            Assert.fail("Unable to setup mock stub for fetching a cash disbursement header transaction");
        }
        
        try {
            when(this.mockCashDisbApi.getItems(isA(XactTypeItemActivityDto.class), eq((XactCustomCriteriaDto) null)))
                            .thenReturn(mockItemListData);
        } catch (XactApiException e) {
            Assert.fail("Unable to setup mock stub for fetching cash disbursement transaction line items");
        }
        
        try {
            this.startTest();    
            Mockito.verify(this.mockCashDisbApi).get(isA(XactDto.class), eq((XactCustomCriteriaDto) null));
            Mockito.verify(this.mockCashDisbApi).getItems(isA(XactTypeItemActivityDto.class), eq((XactCustomCriteriaDto) null));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
    
    @Test
    public void invokeHandlerWithCustomCriteriaSuccess_Fetch() {
        String request = RMT2File.getFileContentsAsString("xml/transaction/cashdisbursement/CashDisbursementCustomQueryRequestHeader.xml");
        List<XactDto> mockListData = AccountingMockData.createMockSingleCommonTransactions();
        List<XactTypeItemActivityDto> mockItemListData = AccountingMockData.createMockXactItems();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockCashDisbApi.get(isA(XactDto.class), isA(XactCustomCriteriaDto.class)))
                            .thenReturn(mockListData);
        } catch (XactApiException e) {
            Assert.fail("Unable to setup mock stub for fetching a cash disbursement header transaction");
        }
        
        try {
            when(this.mockCashDisbApi.getItems(isA(XactTypeItemActivityDto.class), isA(XactCustomCriteriaDto.class)))
                            .thenReturn(mockItemListData);
        } catch (XactApiException e) {
            Assert.fail("Unable to setup mock stub for fetching cash disbursement transaction line items");
        }
        
        try {
            this.startTest();    
            Mockito.verify(this.mockCashDisbApi).get(isA(XactDto.class), isA(XactCustomCriteriaDto.class));
            Mockito.verify(this.mockCashDisbApi).getItems(isA(XactTypeItemActivityDto.class), isA(XactCustomCriteriaDto.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
  
    @Test
    public void invokeHandlerSuccess_Create() {
        String request = RMT2File.getFileContentsAsString("xml/transaction/cashdisbursement/CashDisbursementCreateRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockCashDisbApi.updateTrans(isA(XactDto.class), isA(List.class)))
                    .thenReturn(AccountingMockData.NEW_XACT_ID);
        } catch (XactApiException e) {
            Assert.fail("Unable to setup mock stub for creating a cash disbursement transaction");
        }
        
        try {
            this.startTest();    
            Mockito.verify(this.mockCashDisbApi).updateTrans(isA(XactDto.class), isA(List.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
    
    
    @Test
    public void invokeHandlerError_Fetch_Incorrect_Trans_Code() {
        String request = RMT2File.getFileContentsAsString("xml/transaction/cashdisbursement/CashDisbursementQueryInvalidTransCodeRequestHeader.xml");
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
