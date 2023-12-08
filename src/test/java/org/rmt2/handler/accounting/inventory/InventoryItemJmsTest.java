package org.rmt2.handler.accounting.inventory;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.List;

import org.dto.ItemMasterDto;
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
import org.rmt2.api.handlers.inventory.ItemApiHandler;
import org.rmt2.handler.BaseMockSingleConsumerMDBTest;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the GL Account API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, ItemApiHandler.class, InventoryApiFactory.class })
public class InventoryItemJmsTest extends BaseMockSingleConsumerMDBTest {

    private static final String DESTINATION = "rmt2.queue.accounting";
    private InventoryApiFactory mockApiFactory;
    private InventoryApi mockApi;


    /**
     * 
     */
    public InventoryItemJmsTest() {
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
    public void invokeHandlerSuccess_FetchItems() {
        String request = RMT2File.getFileContentsAsString("xml/accounting/inventory/ItemFetchRequest.xml");
        List<ItemMasterDto> mockDtoDataResponse = AccountingMockData.createMockItemMasterList();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getItemExt(isA(ItemMasterDto.class))).thenReturn(mockDtoDataResponse);
        } catch (InventoryApiException e) {
            e.printStackTrace();
        }

        try {
            this.startTest();   
            Mockito.verify(this.mockApi).getItemExt(isA(ItemMasterDto.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
    
    @Test
    public void invokeHandlerSuccess_FetchVendorUnassignedItems() {
        String request = RMT2File.getFileContentsAsString("xml/accounting/inventory/VendorUnassignedItemFetchRequest.xml");
        List<ItemMasterDto> mockDtoDataResponse = AccountingMockData.createMockItemMasterList();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getVendorUnassignItems(isA(Integer.class))).thenReturn(mockDtoDataResponse);
        } catch (InventoryApiException e) {
            e.printStackTrace();
        }

        try {
            this.startTest();  
            Mockito.verify(this.mockApi).getVendorUnassignItems(isA(Integer.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
    
    @Test
    public void invokeHandelrSuccess_Update() {
        String request = RMT2File.getFileContentsAsString("xml/accounting/inventory/ItemUpdateExistingRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.updateItemMaster(isA(ItemMasterDto.class))).thenReturn(1);
        } catch (InventoryApiException e) {
            e.printStackTrace();
        }

        try {
            this.startTest(); 
            Mockito.verify(this.mockApi).updateItemMaster(isA(ItemMasterDto.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
    
    @Test
    public void invokeHandelrSuccess_Delete() {
        String request = RMT2File.getFileContentsAsString("xml/accounting/inventory/ItemDeleteRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.deleteItemMaster(isA(Integer.class))).thenReturn(1);
        } catch (InventoryApiException e) {
            e.printStackTrace();
        }

        try {
            this.startTest();  
            Mockito.verify(this.mockApi).deleteItemMaster(isA(Integer.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
    
    @Test
    public void invokeHandelrSuccess_Activate() {
        String request = RMT2File.getFileContentsAsString("xml/accounting/inventory/ItemActivateRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.activateItemMaster(isA(Integer.class))).thenReturn(1);
        } catch (InventoryApiException e) {
            e.printStackTrace();
        }

        try {
            this.startTest();  
            Mockito.verify(this.mockApi).activateItemMaster(isA(Integer.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
    
    @Test
    public void invokeHandelrSuccess_Deactivate() {
        String request = RMT2File.getFileContentsAsString("xml/accounting/inventory/ItemDeactivateRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.deactivateItemMaster(isA(Integer.class))).thenReturn(1);
        } catch (InventoryApiException e) {
            e.printStackTrace();
        }

        try {
            this.startTest();  
            Mockito.verify(this.mockApi).deactivateItemMaster(isA(Integer.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
    
    @Test
    public void invokeHandelrSuccess_AddInventoryRetailOverride() {
        String request = RMT2File.getFileContentsAsString("xml/accounting/inventory/ItemRetailOverrideAddRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.addInventoryOverride(isA(Integer.class), isA(Integer[].class))).thenReturn(1);
        } catch (InventoryApiException e) {
            e.printStackTrace();
        }

        try {
            this.startTest();  
            Mockito.verify(this.mockApi).addInventoryOverride(isA(Integer.class), isA(Integer[].class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
    
    @Test
    public void invokeHandelrSuccess_RemoveInventoryRetailOverride() {
        String request = RMT2File.getFileContentsAsString("xml/accounting/inventory/ItemRetailOverrideRemoveRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.removeInventoryOverride(isA(Integer.class), isA(Integer[].class))).thenReturn(1);
        } catch (InventoryApiException e) {
            e.printStackTrace();
        }

        try {
            this.startTest(); 
            Mockito.verify(this.mockApi).removeInventoryOverride(isA(Integer.class), isA(Integer[].class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
    
    @Test
    public void invokeHandelrError_Fetch_Incorrect_Trans_Code() {
        String request = RMT2File.getFileContentsAsString("xml/accounting/inventory/ItemFetchIncorrectTransCodeRequest.xml");
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
