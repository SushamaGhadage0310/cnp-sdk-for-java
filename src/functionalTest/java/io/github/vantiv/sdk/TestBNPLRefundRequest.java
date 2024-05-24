package io.github.vantiv.sdk;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import io.github.vantiv.sdk.generate.BNPLCaptureRequest;
import io.github.vantiv.sdk.generate.BNPLCaptureResponse;
import io.github.vantiv.sdk.generate.BNPLRefundRequest;
import io.github.vantiv.sdk.generate.BNPLRefundResponse;

public class TestBNPLRefundRequest {

	private static CnpOnline cnp;

	@BeforeClass
	public static void beforeClass() throws Exception {
		cnp = new CnpOnline();
	}
	

    @Test
    public void testSimpleRefundReqeust() throws Exception {
        BNPLRefundRequest bnplRefundRequest = new BNPLRefundRequest();
        bnplRefundRequest.setId("url1");
        bnplRefundRequest.setReportGroup("XML10Mer1");
        bnplRefundRequest.setCustomerId("154646587");
        bnplRefundRequest.setAmount(106L);
        bnplRefundRequest.setOrderId("abc");
        bnplRefundRequest.setCnpTxnId(106L);
        BNPLRefundResponse response = cnp.bnplRefund(bnplRefundRequest);
        assertEquals(response.getMessage(), "000",response.getResponse());
        assertEquals("Approved", response.getMessage());
        assertEquals("sandbox", response.getLocation());
    }

}
