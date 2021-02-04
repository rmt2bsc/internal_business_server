package org.rmt2.handler.accounting.transaction.sales;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;

import org.dao.mapping.orm.rmt2.SalesOrderStatus;
import org.dao.mapping.orm.rmt2.SalesOrderStatusHist;
import org.dto.SalesOrderDto;
import org.dto.SalesOrderStatusDto;
import org.dto.SalesOrderStatusHistDto;
import org.dto.adapter.orm.transaction.sales.Rmt2SalesOrderDtoFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.transaction.XactApiFactory;
import org.modules.transaction.sales.SalesApi;
import org.modules.transaction.sales.SalesApiConst;
import org.modules.transaction.sales.SalesApiException;
import org.modules.transaction.sales.SalesApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.api.handlers.transaction.sales.UpdateSalesOrderApiHandler;
import org.rmt2.handler.BaseMockSingleConsumerMDBTest;
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
@PrepareForTest({ JmsClientManager.class, XactApiFactory.class, UpdateSalesOrderApiHandler.class, SalesApiFactory.class })
public class SalesOrderCreateJmsTest extends BaseMockSingleConsumerMDBTest {

    private static final String DESTINATION = "rmt2.queue.accounting";
    private SalesApi mockApi;

    public static final int NEW_XACT_ID = 1234567;
    public static final double TEST_ORDER_TOTAL = 755.94;

    /**
     * 
     */
    public SalesOrderCreateJmsTest() {

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
    public void invokeHandlerSuccess_Create() {
        String request = RMT2File.getFileContentsAsString("xml/accounting/transaction/sales/SalesOrderCreateRequest.xml");

        SalesOrderStatusHist ormStatusHist = new SalesOrderStatusHist();
        ormStatusHist.setSoStatusId(SalesApiConst.STATUS_CODE_QUOTE);
        SalesOrderStatus ormStatus = new SalesOrderStatus();
        ormStatus.setSoStatusId(SalesApiConst.STATUS_CODE_QUOTE);
        ormStatus.setDescription("Quote");
        SalesOrderStatusHistDto mockStatusHistDto = Rmt2SalesOrderDtoFactory.createSalesOrderStatusHistoryInstance(ormStatusHist);
        SalesOrderStatusDto mockStatusDto = Rmt2SalesOrderDtoFactory.createSalesOrderStatusInstance(ormStatus);

        this.setupMocks(DESTINATION, request);

        try {
            when(this.mockApi.updateSalesOrder(isA(SalesOrderDto.class), isA(List.class))).thenReturn(SalesOrderJmsMockData.NEW_XACT_ID);
        } catch (SalesApiException e) {
            Assert.fail("Unable to setup mock stub for creating a sales order transaction");
        }

        try {
            when(this.mockApi.getCurrentStatus(isA(Integer.class))).thenReturn(mockStatusHistDto);
        } catch (SalesApiException e) {
            Assert.fail("Unable to setup mock stub for creating a sales order status history DTO object");
        }

        try {
            when(this.mockApi.getStatus(isA(Integer.class))).thenReturn(mockStatusDto);
        } catch (SalesApiException e) {
            Assert.fail("Unable to setup mock stub for creating a sales order status DTO object");
        }

        try {
            SalesOrderDto dto = SalesOrderJmsMockData.createMockSalesOrder().get(0);
            dto.setSalesOrderId(SalesOrderJmsMockData.NEW_XACT_ID);
            dto.setOrderTotal(TEST_ORDER_TOTAL);
            when(this.mockApi.getSalesOrder(eq(SalesOrderJmsMockData.NEW_XACT_ID))).thenReturn(dto);
        } catch (SalesApiException e) {
            Assert.fail("Unable to setup mock stub for creating a sales order status DTO object");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockApi).updateSalesOrder(isA(SalesOrderDto.class), isA(List.class));
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
