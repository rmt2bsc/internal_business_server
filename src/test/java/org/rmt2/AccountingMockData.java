package org.rmt2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dao.mapping.orm.rmt2.Creditor;
import org.dao.mapping.orm.rmt2.Customer;
import org.dao.mapping.orm.rmt2.GlAccountCategory;
import org.dao.mapping.orm.rmt2.GlAccountTypes;
import org.dao.mapping.orm.rmt2.GlAccounts;
import org.dao.mapping.orm.rmt2.ItemMaster;
import org.dao.mapping.orm.rmt2.ItemMasterStatus;
import org.dao.mapping.orm.rmt2.ItemMasterStatusHist;
import org.dao.mapping.orm.rmt2.ItemMasterType;
import org.dao.mapping.orm.rmt2.VwCreditorXactHist;
import org.dao.mapping.orm.rmt2.VwCustomerXactHist;
import org.dao.mapping.orm.rmt2.VwVendorItems;
import org.dto.AccountCategoryDto;
import org.dto.AccountDto;
import org.dto.AccountTypeDto;
import org.dto.CreditorDto;
import org.dto.CreditorXactHistoryDto;
import org.dto.CustomerDto;
import org.dto.CustomerXactHistoryDto;
import org.dto.ItemMasterDto;
import org.dto.ItemMasterStatusDto;
import org.dto.ItemMasterStatusHistDto;
import org.dto.ItemMasterTypeDto;
import org.dto.VendorItemDto;
import org.dto.adapter.orm.account.generalledger.Rmt2AccountDtoFactory;
import org.dto.adapter.orm.account.subsidiary.Rmt2SubsidiaryDtoFactory;
import org.dto.adapter.orm.inventory.Rmt2InventoryDtoFactory;

import com.SystemException;
import com.api.util.RMT2Date;

public class AccountingMockData {

    public AccountingMockData() {
    }
    
    /**
     * 
     * @return
     */
    public static final List<AccountDto> createMockGlAccounts() {
        List<AccountDto> list = new ArrayList<>();
        GlAccounts p = createMockOrmGlAccounts(100, 1, 120, 1, "AccountNo1", "AccountName1",
                "AccountCode1", "AccountDescription1", 1);
        AccountDto dto = Rmt2AccountDtoFactory.createAccountInstance(p);
        list.add(dto);

        p = createMockOrmGlAccounts(101, 2, 121, 1, "AccountNo2", "AccountName2", "AccountCode2",
                "AccountDescription2", 1);
        dto = Rmt2AccountDtoFactory.createAccountInstance(p);
        list.add(dto);

        p = createMockOrmGlAccounts(102, 3, 122, 1, "AccountNo3", "AccountName3", "AccountCode3",
                "AccountDescription3", 1);
        dto = Rmt2AccountDtoFactory.createAccountInstance(p);
        list.add(dto);

        p = createMockOrmGlAccounts(103, 4, 123, 1, "AccountNo4", "AccountName4", "AccountCode4",
                "AccountDescription4", 1);
        dto = Rmt2AccountDtoFactory.createAccountInstance(p);
        list.add(dto);
        return list;
    }
    
    public static final GlAccounts createMockOrmGlAccounts(int acctId,
            int acctTypeId, int acctCatgId, int acctSeq, String acctNo,
            String acctName, String acctCode, String acctDescription,
            int acctBalTypeId) {
        GlAccounts orm = new GlAccounts();
        orm.setAcctId(acctId);
        orm.setAcctTypeId(acctTypeId);
        orm.setAcctBaltypeId(acctBalTypeId);
        orm.setAcctCatgId(acctCatgId);
        orm.setAcctNo(acctNo);
        orm.setAcctSeq(acctSeq);
        orm.setCode(acctCode);
        orm.setDescription(acctDescription);
        orm.setName(acctName);
        return orm;
    }

    
    /**
     * 
     * @return
     */
    public static final List<AccountTypeDto> createMockGlAccountTypes() {
        List<AccountTypeDto> list = new ArrayList<>();
        GlAccountTypes p = createMockOrmGlAccountTypes(100, 1, "AccountType1");
        AccountTypeDto dto = Rmt2AccountDtoFactory.createAccountTypeInstance(p);
        list.add(dto);

        p = createMockOrmGlAccountTypes(101, 1, "AccountType2");
        dto = Rmt2AccountDtoFactory.createAccountTypeInstance(p);
        list.add(dto);;
        
        p = createMockOrmGlAccountTypes(102, 2, "AccountType3");
        dto = Rmt2AccountDtoFactory.createAccountTypeInstance(p);
        list.add(dto);
        
        p = createMockOrmGlAccountTypes(103, 2, "AccountType4");
        dto = Rmt2AccountDtoFactory.createAccountTypeInstance(p);
        list.add(dto);
        
        p = createMockOrmGlAccountTypes(104, 2, "AccountType5");
        dto = Rmt2AccountDtoFactory.createAccountTypeInstance(p);
        list.add(dto);
        return list;
    }
    
    /**
     * 
     * @param id
     * @param acctBalTypeId
     * @return
     */
    public static final GlAccountTypes createMockOrmGlAccountTypes(int id,
            int acctBalTypeId, String description) {
        GlAccountTypes orm = new GlAccountTypes();
        orm.setAcctTypeId(id);
        orm.setAcctBaltypeId(acctBalTypeId);
        orm.setDescription(description);
        return orm;
    }
    
    /**
     * 
     * @return
     */
    public static final List<AccountCategoryDto> createMockGlAccountCategories() {
        List<AccountCategoryDto> list = new ArrayList<>();
        GlAccountCategory p = createMockOrmGlAccountCategory(100, 1, "Category1");
        AccountCategoryDto dto = Rmt2AccountDtoFactory.createAccountCategoryInstance(p);
        list.add(dto);

        p = createMockOrmGlAccountCategory(101, 2, "Category2");
        dto = Rmt2AccountDtoFactory.createAccountCategoryInstance(p);
        list.add(dto);
        
        p = createMockOrmGlAccountCategory(102, 3, "Category3");
        dto = Rmt2AccountDtoFactory.createAccountCategoryInstance(p);
        list.add(dto);
        
        p = createMockOrmGlAccountCategory(103, 4, "Category4");
        dto = Rmt2AccountDtoFactory.createAccountCategoryInstance(p);
        list.add(dto);
        
        p = createMockOrmGlAccountCategory(104, 5, "Category5");
        dto = Rmt2AccountDtoFactory.createAccountCategoryInstance(p);
        list.add(dto);
        return list;
    }
    
    /**
     * 
     * @param id
     * @param acctTypeId
     * @param description
     * @return
     */
   public static final GlAccountCategory createMockOrmGlAccountCategory(int id, int acctTypeId, String description) {
       GlAccountCategory orm = new GlAccountCategory();
       orm.setAcctCatgId(id);
       orm.setAcctTypeId(acctTypeId);
       orm.setDescription(description);
       return orm;
   }

   
   /**
    * 
    * @param id
    * @param itemTypeId
    * @param serialNo
    * @param vendorItemNo
    * @param creditorId
    * @param description
    * @param qty
    * @param unitCost
    * @param reatilPrice
    * @param active
    * @return
    */
   public static final ItemMaster createMockOrmItemMaster(int id,
           int itemTypeId, String serialNo, String vendorItemNo,
           int creditorId, String description, int qty, double unitCost,
           boolean active) {
       ItemMaster i = new ItemMaster();
       i.setItemId(id);
       i.setItemTypeId(itemTypeId);
       i.setItemSerialNo(serialNo);
       i.setVendorItemNo(vendorItemNo);
       i.setCreditorId(creditorId);
       i.setDescription(description);
       i.setQtyOnHand(qty);
       i.setUnitCost(unitCost);
       i.setActive(active ? 1 : 0);
       i.setOverrideRetail(0);
       i.setMarkup(5);
       i.setRetailPrice((qty * unitCost) * i.getMarkup());
       return i;
   }
   
   public static final List<ItemMasterDto> createMockItemMasterList() {
       List<ItemMasterDto> list = new ArrayList<>();
       ItemMaster o = AccountingMockData.createMockOrmItemMaster(100, 1,
               "100-111-111", "11111110", 1351, "Item1", 1, 1.23, true);
       ItemMasterDto p = Rmt2InventoryDtoFactory.createItemMasterInstance(o);
       list.add(p);

       o = AccountingMockData.createMockOrmItemMaster(101, 1,
               "101-111-111", "11111111", 1352, "Item2", 2, 1.23, true);
       p = Rmt2InventoryDtoFactory.createItemMasterInstance(o);
       list.add(p);

       o = AccountingMockData.createMockOrmItemMaster(102, 1,
               "102-111-111", "11111112", 1353, "Item3", 3, 1.23, true);
       p = Rmt2InventoryDtoFactory.createItemMasterInstance(o);
       list.add(p);

       o = AccountingMockData.createMockOrmItemMaster(103, 1,
               "103-111-111", "11111113", 1354, "Item4", 4, 1.23, true);
       p = Rmt2InventoryDtoFactory.createItemMasterInstance(o);
       list.add(p);

       o = AccountingMockData.createMockOrmItemMaster(104, 1,
               "104-111-111", "11111114", 1355, "Item5", 5, 1.23, true);
       p = Rmt2InventoryDtoFactory.createItemMasterInstance(o);
       list.add(p);
       return list;
   }
   
   /**
    * 
    * @param id
    * @param itemId
    * @param statusId
    * @param unitCost
    * @param markup
    * @param effDate
    * @param endDate
    * @param reason
    * @return
    */
   public static final ItemMasterStatusHist createMockOrmItemMasterStatusHistory(
           int id, int itemId, int statusId, double unitCost, double markup,
           String effDate, String endDate, String reason) {
       ItemMasterStatusHist i = new ItemMasterStatusHist();
       i.setItemStatusHistId(id);
       i.setItemId(itemId);
       i.setItemStatusId(statusId);
       i.setUnitCost(unitCost);
       i.setMarkup(markup);
       try {
           i.setEffectiveDate(RMT2Date.stringToDate(effDate));
       } catch (SystemException e) {
           i.setEffectiveDate(new Date());
       }
       try {
           i.setEndDate(RMT2Date.stringToDate(endDate));
       } catch (SystemException e) {
           i.setEndDate(new Date());
       }
       i.setReason(reason);
       return i;
   }

   
   /**
    * 
    * @return
    */
   public static final List<ItemMasterStatusHistDto> createMockItemStatusHistoryList() {
       List<ItemMasterStatusHistDto> list = new ArrayList<ItemMasterStatusHistDto>();
       ItemMasterStatusHist o = AccountingMockData.createMockOrmItemMasterStatusHistory(10, 100, 1000, 12.50, 3,
                       "2017-01-01", "2017-03-01",
                       "Item Status History Description 1");
       ItemMasterStatusHistDto p = Rmt2InventoryDtoFactory.createItemStatusHistoryInstance(o);
       list.add(p);

       o = AccountingMockData.createMockOrmItemMasterStatusHistory(11,
               101, 1001, 13.50, 3, "2017-01-02", "2017-03-02",
               "Item Status History Description 2");
       p = Rmt2InventoryDtoFactory.createItemStatusHistoryInstance(o);
       list.add(p);

       o = AccountingMockData.createMockOrmItemMasterStatusHistory(12,
               102, 1002, 14.50, 3, "2017-01-03", "2017-03-03",
               "Item Status History Description 3");
       p = Rmt2InventoryDtoFactory.createItemStatusHistoryInstance(o);
       list.add(p);

       o = AccountingMockData.createMockOrmItemMasterStatusHistory(13,
               103, 1003, 15.50, 3, "2017-01-04", "2017-03-04",
               "Item Status History Description 4");
       p = Rmt2InventoryDtoFactory.createItemStatusHistoryInstance(o);
       list.add(p);

       o = AccountingMockData.createMockOrmItemMasterStatusHistory(14,
               104, 1004, 16.50, 3, "2017-01-05", "2017-03-05",
               "Item Status History Description 5");
       p = Rmt2InventoryDtoFactory.createItemStatusHistoryInstance(o);
       list.add(p);
       return list;
   }
   
   /**
    * 
    * @param id
    * @param description
    * @return
    */
   public static final ItemMasterStatus createMockOrmItemMasterStatus(int id, String description) {
       ItemMasterStatus i = new ItemMasterStatus();
       i.setItemStatusId(id);
       i.setDescription(description);
       return i;
   }

   /**
    * 
    * @return
    */
   public static final List<ItemMasterStatusDto> createMockItemStatus() {
       List<ItemMasterStatusDto> list = new ArrayList<>();
       ItemMasterStatus o = AccountingMockData.createMockOrmItemMasterStatus(100, "Item Status #1");
       ItemMasterStatusDto p = Rmt2InventoryDtoFactory.createItemStatusInstance(o);
       list.add(p);

       o = AccountingMockData.createMockOrmItemMasterStatus(101, "Item Status #2");
       p = Rmt2InventoryDtoFactory.createItemStatusInstance(o);
       list.add(p);

       o = AccountingMockData.createMockOrmItemMasterStatus(102, "Item Status #3");
       p = Rmt2InventoryDtoFactory.createItemStatusInstance(o);
       list.add(p);

       o = AccountingMockData.createMockOrmItemMasterStatus(103, "Item Status #4");
       p = Rmt2InventoryDtoFactory.createItemStatusInstance(o);
       list.add(p);

       o = AccountingMockData.createMockOrmItemMasterStatus(104, "Item Status #5");
       p = Rmt2InventoryDtoFactory.createItemStatusInstance(o);
       list.add(p);
       return list;
   }
   
   /**
    * 
    * @param id
    * @param description
    * @return
    */
   public static final ItemMasterType createMockOrmItemMasterType(int id, String description) {
       ItemMasterType i = new ItemMasterType();
       i.setItemTypeId(id);
       i.setDescription(description);
       return i;
   }
   
   /**
    * 
    * @return
    */
   public static final List<ItemMasterTypeDto> createMockItemType() {
       List<ItemMasterTypeDto> list = new ArrayList<>();
       ItemMasterType o = AccountingMockData.createMockOrmItemMasterType(100, "Item Type #1");
       ItemMasterTypeDto p = Rmt2InventoryDtoFactory.createItemTypeInstance(o);
       list.add(p);

       o = AccountingMockData.createMockOrmItemMasterType(101, "Item Type #2");
       p = Rmt2InventoryDtoFactory.createItemTypeInstance(o);
       list.add(p);

       o = AccountingMockData.createMockOrmItemMasterType(102, "Item Type #3");
       p = Rmt2InventoryDtoFactory.createItemTypeInstance(o);
       list.add(p);

       o = AccountingMockData.createMockOrmItemMasterType(103, "Item Type #4");
       p = Rmt2InventoryDtoFactory.createItemTypeInstance(o);
       list.add(p);

       o = AccountingMockData.createMockOrmItemMasterType(104, "Item Type #5");
       p = Rmt2InventoryDtoFactory.createItemTypeInstance(o);
       list.add(p);
       return list;
   }
   
   /**
    * 
    * @param id
    * @param serialNo
    * @param vendorItemNo
    * @param creditorId
    * @param description
    * @param qty
    * @param unitCost
    * @param active
    * @return
    */
   public static final VwVendorItems createMockOrmVwVendorItems(int id,
           String serialNo, String vendorItemNo, int creditorId,
           String description, int qty, double unitCost) {
       VwVendorItems i = new VwVendorItems();
       i.setItemId(id);
       i.setItemSerialNo(serialNo);
       i.setVendorItemNo(vendorItemNo);
       i.setCreditorId(creditorId);
       i.setDescription(description);
       i.setQtyOnHand(qty);
       i.setUnitCost(unitCost);
       i.setOverrideRetail(0);
       i.setMarkup(3);
       return i;
   }
   
   /**
    * 
    * @return
    */
   public static final List<VendorItemDto> createMockVendorItem() {
       List<VendorItemDto> list = new ArrayList<>();
       VwVendorItems o = AccountingMockData.createMockOrmVwVendorItems(
               100, "111-111-111", "11111111", 1234, "Item # 1", 5, 1.23);
       VendorItemDto p = Rmt2InventoryDtoFactory.createVendorItemInstance(o);
       list.add(p);

       o = AccountingMockData.createMockOrmVwVendorItems(200,
               "222-222-222", "22222222", 1234, "Item # 2", 15, 0.99);
       p = Rmt2InventoryDtoFactory.createVendorItemInstance(o);
       list.add(p);

       o = AccountingMockData.createMockOrmVwVendorItems(300,
               "333-333-333", "3333333", 1234, "Item # 3", 15, 4.55);
       p = Rmt2InventoryDtoFactory.createVendorItemInstance(o);
       list.add(p);

       o = AccountingMockData.createMockOrmVwVendorItems(400,
               "444-444-444", "4444444", 1234, "Item # 4", 100, 10.99);
       p = Rmt2InventoryDtoFactory.createVendorItemInstance(o);
       list.add(p);

       o = AccountingMockData.createMockOrmVwVendorItems(500,
               "555-555-555", "5555555", 1234, "Item # 5", 55, 32.99);
       p = Rmt2InventoryDtoFactory.createVendorItemInstance(o);
       list.add(p);
       return list;
   }
   
   /**
    * 
    * @param id
    * @param businessId
    * @param personId
    * @param acctId
    * @param acctNo
    * @param description
    * @return
    */
   public static final Customer createMockOrmCustomer(int id, int businessId,
           int personId, int acctId, String acctNo, String description) {
       Customer o = new Customer();
       o.setCustomerId(id);
       o.setBusinessId(businessId);
       o.setPersonId(personId);
       o.setAcctId(acctId);
       o.setAccountNo(acctNo);
       o.setDescription(description);
       o.setCreditLimit(10000);
       o.setActive(1);
       o.setDateCreated(new Date());
       o.setDateUpdated(o.getDateCreated());
       o.setUserId("testuser");
       o.setIpCreated("111.222.101.100");
       o.setIpUpdated(o.getIpCreated());
       return o;
   }
   
   /**
    * 
    * @return
    */
   public static final List<CustomerDto> createMockCustomers() {
       List<CustomerDto> list = new ArrayList<>();
       Customer o = AccountingMockData.createMockOrmCustomer(200, 1351, 0,
               333, "C1234580", "Customer 1");
       CustomerDto d = Rmt2SubsidiaryDtoFactory.createCustomerInstance(o, null);
       list.add(d);
       
       o = AccountingMockData.createMockOrmCustomer(201, 1352, 0,
               333, "C1234581", "Customer 2");
       d = Rmt2SubsidiaryDtoFactory.createCustomerInstance(o, null);
       list.add(d);
       
       o = AccountingMockData.createMockOrmCustomer(202, 1353, 0,
               333, "C1234582", "Customer 3");
       d = Rmt2SubsidiaryDtoFactory.createCustomerInstance(o, null);
       list.add(d);
       
       o = AccountingMockData.createMockOrmCustomer(203, 1354, 0,
               333, "C1234583", "Customer 4");
       d = Rmt2SubsidiaryDtoFactory.createCustomerInstance(o, null);
       list.add(d);
       
       o = AccountingMockData.createMockOrmCustomer(204, 1355, 0,
               333, "C1234584", "Customer 5");
       d = Rmt2SubsidiaryDtoFactory.createCustomerInstance(o, null);
       list.add(d);
       return list;
   }
   
   public static final VwCustomerXactHist createMockOrmCustomerXactHistory(
           int xactId, int customerId, int businessId, int personId,
           String acctNo, double xactAmt, Date xactDate, int xactTypeId) {
       VwCustomerXactHist o = new VwCustomerXactHist();
       o.setXactId(xactId);
       o.setCustomerId(customerId);
       o.setBusinessId(businessId);
       o.setPersonId(personId);
       o.setAccountNo(acctNo);
       o.setXactAmount(xactAmt);
       o.setXactDate(xactDate);
       o.setXactTypeId(xactTypeId);
       o.setReason("Transaction History for customer, " + customerId);
       o.setActive(1);
       o.setCreditLimit(5000.00);
       o.setXactSubtypeId(1);
       o.setXactTypeName("Xact Type Name" + xactId);
       o.setConfirmNo(String.valueOf(xactDate.getTime()));
       o.setDocumentId(xactId + customerId);
       o.setCustomerActivityId(xactId * customerId);
       o.setCustomerActivityAmount(xactAmt);
       return o;
   }
   
   /**
    * 
    * @return
    */
   public static final List<CustomerXactHistoryDto> createMockCustomerXactHistory() {
       List<CustomerXactHistoryDto> list = new ArrayList<>();
       VwCustomerXactHist o = AccountingMockData.createMockOrmCustomerXactHistory(1200, 100, 1351, 0, "C8434", 1000.00,
                       new Date(), 1);
       CustomerXactHistoryDto d = Rmt2SubsidiaryDtoFactory.createCustomerTransactionInstance(o);
       list.add(d);

       o = AccountingMockData.createMockOrmCustomerXactHistory(1201, 100, 1351, 0, "C8434", 1000.00,
               new Date(), 1);
       d = Rmt2SubsidiaryDtoFactory.createCustomerTransactionInstance(o);
       list.add(d);

       o = AccountingMockData.createMockOrmCustomerXactHistory(1202, 100, 1351, 0, "C8434", 2000.00,
               new Date(), 2);
       d = Rmt2SubsidiaryDtoFactory.createCustomerTransactionInstance(o);
       list.add(d);

       o = AccountingMockData.createMockOrmCustomerXactHistory(1203, 100, 1351, 0, "C8434", 3000.00,
               new Date(), 3);
       d = Rmt2SubsidiaryDtoFactory.createCustomerTransactionInstance(o);
       list.add(d);

       o = AccountingMockData.createMockOrmCustomerXactHistory(1204, 100, 1351, 0, "C8434", 4000.00,
               new Date(), 4);
       d = Rmt2SubsidiaryDtoFactory.createCustomerTransactionInstance(o);
       list.add(d);
       return list;
   }
   
   /**
    * 
    * @param id
    * @param businessId
    * @param acctId
    * @param acctNo
    * @param extAcctNo
    * @param creditorTypeId
    * @return
    */
   public static final Creditor createMockOrmCreditor(int id, int businessId,
           int acctId, String acctNo, String extAcctNo, int creditorTypeId) {
       Creditor o = new Creditor();
       o.setCreditorId(id);
       o.setBusinessId(businessId);
       o.setAcctId(acctId);
       o.setAccountNumber(acctNo);
       o.setExtAccountNumber(extAcctNo);
       o.setCreditorTypeId(creditorTypeId);
       o.setCreditLimit(10000);
       o.setApr(12.5);
       o.setActive(1);
       o.setDateCreated(new Date());
       o.setDateUpdated(o.getDateCreated());
       o.setUserId("testuser");
       o.setIpCreated("111.222.101.100");
       o.setIpUpdated(o.getIpCreated());
       return o;
   }
   
   /**
    * 
    * @return
    */
   public static final List<CreditorDto> createMockCreditors() {
       List<CreditorDto> list = new ArrayList<>();
       Creditor o = AccountingMockData.createMockOrmCreditor(200, 1351,
               330, "C1234580", "7437437JDJD8480", 22);
       CreditorDto d = Rmt2SubsidiaryDtoFactory.createCreditorInstance(o, null);
       list.add(d);
       
       o = AccountingMockData.createMockOrmCreditor(201, 1352,
               331, "C1234581", "7437437JDJD8481", 22);
       d = Rmt2SubsidiaryDtoFactory.createCreditorInstance(o, null);
       list.add(d);
       
       o = AccountingMockData.createMockOrmCreditor(202, 1353,
               332, "C1234582", "7437437JDJD8482", 22);
       d = Rmt2SubsidiaryDtoFactory.createCreditorInstance(o, null);
       list.add(d);
       
       o = AccountingMockData.createMockOrmCreditor(203, 1354,
               333, "C1234583", "7437437JDJD8483", 22);
       d = Rmt2SubsidiaryDtoFactory.createCreditorInstance(o, null);
       list.add(d);
       
       o = AccountingMockData.createMockOrmCreditor(204, 1355,
               334, "C1234584", "7437437JDJD8484", 22);
       d = Rmt2SubsidiaryDtoFactory.createCreditorInstance(o, null);
       list.add(d);
       return list;
   }
   
   /**
    * 
    * @param xactId
    * @param creditorId
    * @param acctNo
    * @param xactAmt
    * @param xactDate
    * @param xactTypeId
    * @return
    */
   public static final VwCreditorXactHist createMockOrmCreditorXactHistory(
           int xactId, int creditorId, String acctNo, double xactAmt,
           Date xactDate, int xactTypeId) {
       VwCreditorXactHist o = new VwCreditorXactHist();
       o.setXactId(xactId);
       o.setCreditorTypeId(creditorId);
       o.setAccountNumber(acctNo);
       o.setXactAmount(xactAmt);
       o.setXactDate(xactDate);
       o.setXactTypeId(xactTypeId);
       o.setReason("Transaction History for creditor, " + creditorId);
       o.setDateCreated(new Date());
       o.setUserId("testuser");
       o.setActive(1);
       o.setApr(1.56);
       o.setCreditLimit(5000.00);
       o.setCreditorTypeDescription(
               "Creditor type description for creditor, " + creditorId);
       o.setXactSubtypeId(1);
       o.setXactTypeName("Xact Type Name" + xactId);
       o.setConfirmNo(String.valueOf(o.getDateCreated().getTime()));
       o.setTenderId(35);
       o.setDocumentId(xactId + creditorId);
       o.setCreditorActivityId(xactId * o.getTenderId());
       o.setCreditorActivityAmount(xactAmt);
       return o;
   }

   /**
    * 
    * @return
    */
   public static final List<CreditorXactHistoryDto> createMockCreditorXactHistory() {
       List<CreditorXactHistoryDto> list = new ArrayList<>();
       VwCreditorXactHist o = AccountingMockData
               .createMockOrmCreditorXactHistory(1200, 100, "C8434", 1000.00,
                       new Date(), 1);
       CreditorXactHistoryDto d = Rmt2SubsidiaryDtoFactory.createCreditorTransactionInstance(o);
       list.add(d);

       o = AccountingMockData.createMockOrmCreditorXactHistory(1201,
               100, "C8434", 32.00, new Date(), 1);
       d = Rmt2SubsidiaryDtoFactory.createCreditorTransactionInstance(o);
       list.add(d);

       o = AccountingMockData.createMockOrmCreditorXactHistory(1202,
               100, "C8434", 1223.00, new Date(), 2);
       d = Rmt2SubsidiaryDtoFactory.createCreditorTransactionInstance(o);
       list.add(d);

       o = AccountingMockData.createMockOrmCreditorXactHistory(1203,
               100, "C8434", 25.67, new Date(), 1);
       d = Rmt2SubsidiaryDtoFactory.createCreditorTransactionInstance(o);
       list.add(d);

       o = AccountingMockData.createMockOrmCreditorXactHistory(1204,
               100, "C8434", 745.59, new Date(), 3);
       d = Rmt2SubsidiaryDtoFactory.createCreditorTransactionInstance(o);
       list.add(d);
       return list;
   }
}
