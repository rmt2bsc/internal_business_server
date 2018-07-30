package org.rmt2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dao.mapping.orm.rmt2.GlAccountCategory;
import org.dao.mapping.orm.rmt2.GlAccountTypes;
import org.dao.mapping.orm.rmt2.GlAccounts;
import org.dao.mapping.orm.rmt2.ItemMaster;
import org.dao.mapping.orm.rmt2.ItemMasterStatusHist;
import org.dto.AccountCategoryDto;
import org.dto.AccountDto;
import org.dto.AccountTypeDto;
import org.dto.ItemMasterDto;
import org.dto.ItemMasterStatusHistDto;
import org.dto.adapter.orm.account.generalledger.Rmt2AccountDtoFactory;
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
}
