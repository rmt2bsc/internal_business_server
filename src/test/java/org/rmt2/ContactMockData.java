package org.rmt2;

import java.util.ArrayList;
import java.util.List;

import org.dao.mapping.orm.rmt2.Address;
import org.dao.mapping.orm.rmt2.Business;
import org.dto.BusinessContactDto;
import org.dto.ContactDto;
import org.dto.adapter.orm.Rmt2AddressBookDtoFactory;

public class ContactMockData {

    public ContactMockData() {
    }
    
    /**
     * 
     * @return
     */
    public static final List<ContactDto> createMockSingleContactDtoResponseData() {
        Business bus = new Business();
        Address addr = new Address();

        bus.setBusinessId(1351);
        bus.setLongname("Ticket Master");

        addr.setAddrId(2258);
        addr.setBusinessId(1351);
        addr.setPhoneMain("2143738000");

        BusinessContactDto busDto = Rmt2AddressBookDtoFactory.getBusinessInstance(bus, addr);

        List<ContactDto> list = new ArrayList<ContactDto>();
        list.add(busDto);
        return list;
    }

}
