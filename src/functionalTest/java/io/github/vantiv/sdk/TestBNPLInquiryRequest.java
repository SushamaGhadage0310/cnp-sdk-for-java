package io.github.vantiv.sdk;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import io.github.vantiv.sdk.generate.BNPLCancelRequest;
import io.github.vantiv.sdk.generate.BNPLCancelResponse;
import io.github.vantiv.sdk.generate.BNPLInquiryRequest;
import io.github.vantiv.sdk.generate.BNPLInquiryResponse;

public class TestBNPLInquiryRequest {

	private static CnpOnline cnp;

	@BeforeClass
	public static void beforeClass() throws Exception {
		cnp = new CnpOnline();
	}
	

    @Test
    public void testSimpleBNPLInquiryReqeust() throws Exception {
        BNPLInquiryRequest Request = new BNPLInquiryRequest();
        Request.setId("url1");
        Request.setReportGroup("XML10Mer1");
        Request.setCustomerId("154646587");
        Request.setOrderId("abc");
        Request.setCnpTxnId(106l);
        BNPLInquiryResponse response = cnp.bnplInquiry(Request);
        assertEquals(response.getMessage(), "000",response.getResponse());
        assertEquals("Approved", response.getMessage());
        assertEquals("sandbox", response.getLocation());
    }

}
