package org.rmt2;

import java.util.ArrayList;
import java.util.List;

import org.dao.mapping.orm.rmt2.GlAccountCategory;
import org.dao.mapping.orm.rmt2.GlAccountTypes;
import org.dao.mapping.orm.rmt2.GlAccounts;
import org.dto.AccountCategoryDto;
import org.dto.AccountDto;
import org.dto.AccountTypeDto;
import org.dto.adapter.orm.account.generalledger.Rmt2AccountDtoFactory;

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

}
