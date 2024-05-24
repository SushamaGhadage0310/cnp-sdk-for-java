package io.github.vantiv.sdk;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.BeforeClass;
import org.junit.Test;

import io.github.vantiv.sdk.generate.BNPLAuthResponse;
import io.github.vantiv.sdk.generate.BNPLAuthorizationRequest;
import io.github.vantiv.sdk.generate.Contact;
import io.github.vantiv.sdk.generate.CustomerInfo;
import io.github.vantiv.sdk.generate.DetailTax;
import io.github.vantiv.sdk.generate.EnhancedData;
import io.github.vantiv.sdk.generate.FinicityAccountRequest;
import io.github.vantiv.sdk.generate.FinicityAccountResponse;
import io.github.vantiv.sdk.generate.Provider;

public class TestBNPLAuthorizationRequest {

	private static CnpOnline cnp;

	@BeforeClass
	public static void beforeClass() throws Exception {
		cnp = new CnpOnline();
	}
	

    @Test
    public void testSimpleBNPLAuthReqeust() throws Exception {
        BNPLAuthorizationRequest bnplAuthorizationRequest = new BNPLAuthorizationRequest();
        bnplAuthorizationRequest.setId("url1");
        bnplAuthorizationRequest.setReportGroup("XML10Mer1");
        bnplAuthorizationRequest.setCustomerId("154646587");
        bnplAuthorizationRequest.setAmount(106L);
        bnplAuthorizationRequest.setProvider(Provider.AFFIRM);
        bnplAuthorizationRequest.setOrderId("abc");
        bnplAuthorizationRequest.setPostCheckoutRedirectUrl("www.abc");
        CustomerInfo cust = new CustomerInfo();
        cust.setAccountUsername("Woolfoo");
        cust.setUserAccountNumber("123456ATY");
        cust.setUserAccountEmail("woolfoo@gmail.com");
        cust.setMembershipId("Member01");
        cust.setMembershipPhone("9765431234");
        cust.setMembershipEmail("mem@abc.com");
        cust.setMembershipName("memName");
        Calendar createDate = Calendar.getInstance();
        createDate.set(1990, Calendar.MARCH, 16);
        cust.setAccountCreatedDate(createDate);
        cust.setUserAccountPhone("123456789");
        bnplAuthorizationRequest.setCustomerInfo(cust);
        Contact contact = new Contact();
        contact.setZip("95032");
        bnplAuthorizationRequest.setBillToAddress(contact);
        bnplAuthorizationRequest.setShipToAddress(contact);
        EnhancedData enhanced = new EnhancedData();
        DetailTax dt1 = new DetailTax();
        dt1.setTaxAmount(100L);
        enhanced.getDetailTaxes().add(dt1);
        DetailTax dt2 = new DetailTax();
        dt2.setTaxAmount(200L);
        enhanced.getDetailTaxes().add(dt2);
        bnplAuthorizationRequest.setEnhancedData(enhanced);
        BNPLAuthResponse response = cnp.bnplAuth(bnplAuthorizationRequest);
        assertEquals(response.getMessage(), "000",response.getResponse());
        assertEquals("Approved", response.getMessage());
        assertEquals("sandbox", response.getLocation());
    }

}
