package org.rmt2.handler.accounting.transaction.purchases;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;

import org.dto.XactCreditChargeDto;
import org.dto.XactCustomCriteriaDto;
import org.dto.XactTypeItemActivityDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.transaction.XactApiFactory;
import org.modules.transaction.purchases.creditor.CreditorPurchasesApi;
import org.modules.transaction.purchases.creditor.CreditorPurchasesApiException;
import org.modules.transaction.purchases.creditor.CreditorPurchasesApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.AccountingMockData;
import org.rmt2.api.handlers.transaction.purchases.QueryCreditorPurchasesApiHandler;
import org.rmt2.handler.BaseMockSingleConsumerMDBTest;
import org.rmt2.handler.accounting.transaction.TransactionDatasourceMock;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;

/**
 * Test the idenity and invocation of the Creditor purchases Transaction API
 * Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, XactApiFactory.class, QueryCreditorPurchasesApiHandler.class, CreditorPurchasesApiFactory.class })
public class CreditorPurchasesJmsTest extends BaseMockSingleConsumerMDBTest {

    private static final String DESTINATION = "rmt2.queue.accounting";
    private CreditorPurchasesApi mockApi;

    public static final int NEW_XACT_ID = 1234567;

    /**
     * 
     */
    public CreditorPurchasesJmsTest() {
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

        // Setup Transaction Datasource mocks
        TransactionDatasourceMock.setupXactMocks();

        // Setup Cash Disbursement API Mocks
        mockApi = Mockito.mock(CreditorPurchasesApi.class);
        PowerMockito.mockStatic(CreditorPurchasesApiFactory.class);
        PowerMockito.when(CreditorPurchasesApiFactory.createApi()).thenReturn(this.mockApi);
        doNothing().when(this.mockApi).close();
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
    public void invokeHandlerWithoutCreditorCriteriaSuccess_Fetch() {
        String request = RMT2File
                .getFileContentsAsString("xml/accounting/transaction/purchases/CreditorPurchasesBasicQueryRequestFull.xml");
        List<XactCreditChargeDto> mockListData = AccountingMockData.createMockCreditPurchaseHeader();
        List<XactTypeItemActivityDto> mockItemListData = AccountingMockData.createMockCreditPurchaseDetails();

        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.get(isA(XactCreditChargeDto.class), eq((XactCustomCriteriaDto) null))).thenReturn(mockListData);
        } catch (CreditorPurchasesApiException e) {
            Assert.fail("Unable to setup mock stub for fetching a creditor purchases transaction");
        }

        try {
            when(this.mockApi.getItems(isA(Integer.class))).thenReturn(mockItemListData);
        } catch (CreditorPurchasesApiException e) {
            Assert.fail("Unable to setup mock stub for fetching creditor purchases transaction line items");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockApi).get(isA(XactCreditChargeDto.class), eq((XactCustomCriteriaDto) null));
            Mockito.verify(this.mockApi).getItems(isA(Integer.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

    @Test
    public void invokeHandlerSuccess_Create() {
        String request = RMT2File
                .getFileContentsAsString("xml/accounting/transaction/purchases/CreditorPurchasesUpdateRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.update(isA(XactCreditChargeDto.class), isA(List.class))).thenReturn(AccountingMockData.NEW_XACT_ID);
        } catch (CreditorPurchasesApiException e) {
            Assert.fail("Unable to setup mock stub for creating a creditor purchases transaction");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockApi).update(isA(XactCreditChargeDto.class), isA(List.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

    @Test
    public void invokeHandlerError_Fetch_Incorrect_Trans_Code() {
        String request = RMT2File
                .getFileContentsAsString("xml/accounting/transaction/purchases/CreditorPurchasesInvalidTransCodeQueryRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            this.startTest();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

}
