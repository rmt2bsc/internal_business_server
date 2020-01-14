/**
 * 
 */
package org.rmt2.handler.accounting.transaction;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import org.dao.transaction.XactDao;
import org.dao.transaction.XactDaoFactory;
import org.dto.XactCustomCriteriaDto;
import org.mockito.Mockito;
import org.modules.transaction.XactApi;
import org.modules.transaction.XactApiFactory;
import org.powermock.api.mockito.PowerMockito;

/**
 * @author roy.terrell
 *
 */
public class TransactionDatasourceMock {

    public static final void setupXactMocks() {
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
    }
}
