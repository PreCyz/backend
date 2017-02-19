package backend.dao;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import backend.AbstractUnitTest;
import backend.dao.InboxDAO;
import backend.dto.InboxDetails;
import backend.util.JacksonUtilJson;

public class InboxDAOUnitTest extends AbstractUnitTest{
	
	@Resource(name = "inboxDao")
	private InboxDAO dao;
	
	@Mock 
	private InboxDAO mockDao;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	
	@Test
	public void getTransactionDataReturnJson(){
		InboxDetails inboxDetails = new InboxDetails();
		inboxDetails.setContractNumber("contractNumber");
		inboxDetails.setSecondaryUsername("secondaryUsername");
		inboxDetails.setUserContractRole("userContractRole");
		System.out.println(JacksonUtilJson.marshalToString(inboxDetails, JacksonUtilJson.SerializationFeatures.WRAP_ROOT));
	}	
}
