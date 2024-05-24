package io.github.vantiv.sdk;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import io.github.vantiv.sdk.generate.BNPLCancelRequest;
import io.github.vantiv.sdk.generate.BNPLCancelResponse;
import io.github.vantiv.sdk.generate.BNPLCaptureRequest;
import io.github.vantiv.sdk.generate.BNPLCaptureResponse;

public class TestBNPLCancleRequest {

	private static CnpOnline cnp;

	@BeforeClass
	public static void beforeClass() throws Exception {
		cnp = new CnpOnline();
	}
	

    @Test
    public void testSimpleBNPLCancleReqeust() throws Exception {
        BNPLCancelRequest bnplCancelRequest = new BNPLCancelRequest();
        bnplCancelRequest.setId("url1");
        bnplCancelRequest.setReportGroup("XML10Mer1");
        bnplCancelRequest.setCustomerId("154646587");
        bnplCancelRequest.setAmount(106L);
        bnplCancelRequest.setOrderId("abc");
        bnplCancelRequest.setCnpTxnId(106l);
        BNPLCancelResponse response = cnp.bnplCancle(bnplCancelRequest);
        assertEquals(response.getMessage(), "000",response.getResponse());
        assertEquals("Approved", response.getMessage());
        assertEquals("sandbox", response.getLocation());
    }

}
