package org.rmt2.handler.accounting.transaction.sales;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.List;

import org.dto.SalesInvoiceDto;
import org.dto.SalesOrderItemDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.transaction.XactApiFactory;
import org.modules.transaction.sales.SalesApi;
import org.modules.transaction.sales.SalesApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.transaction.sales.QuerySalesOrderApiHandler;
import org.rmt2.handler.accounting.transaction.TransactionDatasourceMock;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;

/**
 * Test the idenity and invocation of the sales order create message handler
 * Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, XactApiFactory.class, QuerySalesOrderApiHandler.class, SalesApiFactory.class })
public class SalesOrderQueryJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "rmt2.queue.accounting";
    private SalesApi mockApi;

    public static final int NEW_XACT_ID = 1234567;

    /**
     * 
     */
    public SalesOrderQueryJmsTest() {

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

        // Setup Sales Order API Mocks
        mockApi = Mockito.mock(SalesApi.class);
        PowerMockito.mockStatic(SalesApiFactory.class);
        PowerMockito.when(SalesApiFactory.createApi()).thenReturn(this.mockApi);
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
    public void invokeHandler_Success() {
        String request = RMT2File.getFileContentsAsString("xml/accounting/transaction/sales/SalesOrderQueryFullRequest.xml");
        
        List<SalesInvoiceDto> mockSalesOrderDtoList = SalesOrderJmsMockData.createMockSalesInvoices();
        List<SalesOrderItemDto> mockSalesOrderItems1000 = SalesOrderJmsMockData.createMockSalesOrderItems(1000);
        List<SalesOrderItemDto> mockSalesOrderItems1001 = SalesOrderJmsMockData.createMockSalesOrderItems(1001);
        List<SalesOrderItemDto> mockSalesOrderItems1002 = SalesOrderJmsMockData.createMockSalesOrderItems(1002);
        List<SalesOrderItemDto> mockSalesOrderItems1003 = SalesOrderJmsMockData.createMockSalesOrderItems(1003);
        List<SalesOrderItemDto> mockSalesOrderItems1004 = SalesOrderJmsMockData.createMockSalesOrderItems(1004);
        
        try {
            when(this.mockApi.getInvoice(isA(SalesInvoiceDto.class))).thenReturn(mockSalesOrderDtoList);
        } catch (Exception e) {
            Assert.fail("Unable to setup mock stub for fetching sales order DTO list");
        }

        try {
            when(this.mockApi.getLineItems(eq(1000))).thenReturn(mockSalesOrderItems1000);
        } catch (Exception e) {
            Assert.fail("Unable to setup mock stub for fetching sales order item DTO list for Sales Order Id 1000");
        }

        try {
            when(this.mockApi.getLineItems(eq(1001))).thenReturn(mockSalesOrderItems1001);
        } catch (Exception e) {
            Assert.fail("Unable to setup mock stub for fetching sales order item DTO list for Sales Order Id 1001");
        }

        try {
            when(this.mockApi.getLineItems(eq(1002))).thenReturn(mockSalesOrderItems1002);
        } catch (Exception e) {
            Assert.fail("Unable to setup mock stub for fetching sales order item DTO list for Sales Order Id 1002");
        }

        try {
            when(this.mockApi.getLineItems(eq(1003))).thenReturn(mockSalesOrderItems1003);
        } catch (Exception e) {
            Assert.fail("Unable to setup mock stub for fetching sales order item DTO list for Sales Order Id 1003");
        }

        try {
            when(this.mockApi.getLineItems(eq(1004))).thenReturn(mockSalesOrderItems1004);
        } catch (Exception e) {
            Assert.fail("Unable to setup mock stub for fetching sales order item DTO list for Sales Order Id 1004");
        }
        
        this.setupMocks(DESTINATION, request);


        try {
            this.startTest();
            Mockito.verify(this.mockApi).getInvoice(isA(SalesInvoiceDto.class));
            Mockito.verify(this.mockApi, times(5)).getLineItems(isA(Integer.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

    @Test
    public void invokeHandlerError_Incorrect_Trans_Code() {
        String request = RMT2File
                .getFileContentsAsString("xml/accounting/transaction/sales/SalesOrderCreateInvalidTransCodeRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            this.startTest();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

}
