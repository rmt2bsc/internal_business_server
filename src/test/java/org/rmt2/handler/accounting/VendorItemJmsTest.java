package org.rmt2.handler.accounting;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.List;

import org.dto.VendorItemDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.inventory.InventoryApi;
import org.modules.inventory.InventoryApiException;
import org.modules.inventory.InventoryApiFactory;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.AccountingMockData;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.inventory.VendorItemApiHandler;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the Vendor Item Type API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, VendorItemApiHandler.class, InventoryApiFactory.class })
public class VendorItemJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "Test-Accounting-Queue";
    private InventoryApiFactory mockApiFactory;
    private InventoryApi mockApi;


    /**
     * 
     */
    public VendorItemJmsTest() {
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
        this.mockApiFactory = Mockito.mock(InventoryApiFactory.class);
        this.mockApi = Mockito.mock(InventoryApi.class);
        try {
            whenNew(InventoryApiFactory.class).withNoArguments().thenReturn(this.mockApiFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        when(mockApiFactory.createApi(isA(String.class))).thenReturn(mockApi);
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
    public void invokeHandlerSuccess_FetchVendorItems() {
        String request = RMT2File.getFileContentsAsString("xml/inventory/VendorItemFetchRequest.xml");
        List<VendorItemDto> mockListData = AccountingMockData.createMockVendorItem();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getVendorItem(isA(Integer.class), isA(Integer.class))).thenReturn(mockListData);
        } catch (InventoryApiException e) {
            e.printStackTrace();
        }

        try {
            this.startTest();    
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
    
    @Test
    public void invokeHandlerSuccess_Fetch_VendorAssignedItems() {
        String request = RMT2File.getFileContentsAsString("xml/inventory/VendorAssignedItemFetchRequest.xml");
        List<VendorItemDto> mockListData = AccountingMockData.createMockVendorItem();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getVendorAssignItems(isA(Integer.class))).thenReturn(mockListData);
        } catch (InventoryApiException e) {
            e.printStackTrace();
        }

        try {
            this.startTest();    
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
    
    @Test
    public void invokeHandlerError_Fetch_Incorrect_Trans_Code() {
        String request = RMT2File.getFileContentsAsString("xml/inventory/VendorItemFetchIncorrectTransCodeRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            this.startTest();    
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
    
    @Test
    public void invokeHandlerSuccess_AssignVendorItems() {
        String request = RMT2File.getFileContentsAsString("xml/inventory/AssignVendorItemsRequest.xml");
        int rc = 3;
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.assignVendorItems(isA(Integer.class), isA(Integer[].class)))
            .thenReturn(rc);
        } catch (InventoryApiException e) {
            e.printStackTrace();
        }

        try {
            this.startTest();    
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
    
    @Test
    public void invokeHandlerSuccess_RemoveVendorItems() {
        String request = RMT2File.getFileContentsAsString("xml/inventory/RemoveVendorItemsRequest.xml");
        int rc = 3;
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.removeVendorItems(isA(Integer.class), isA(Integer[].class)))
            .thenReturn(rc);
        } catch (InventoryApiException e) {
            e.printStackTrace();
        }

        try {
            this.startTest();    
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
}
