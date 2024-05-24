package io.github.vantiv.sdk;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.BeforeClass;
import org.junit.Test;

import io.github.vantiv.sdk.generate.BNPLAuthResponse;
import io.github.vantiv.sdk.generate.BNPLAuthorizationRequest;
import io.github.vantiv.sdk.generate.BNPLCaptureRequest;
import io.github.vantiv.sdk.generate.BNPLCaptureResponse;
import io.github.vantiv.sdk.generate.Contact;
import io.github.vantiv.sdk.generate.CustomerInfo;
import io.github.vantiv.sdk.generate.DetailTax;
import io.github.vantiv.sdk.generate.EnhancedData;
import io.github.vantiv.sdk.generate.Provider;

public class TestBNPLCaptureRequest {

	private static CnpOnline cnp;

	@BeforeClass
	public static void beforeClass() throws Exception {
		cnp = new CnpOnline();
	}
	

    @Test
    public void testSimpleBNPLCaptureReqeust() throws Exception {
        BNPLCaptureRequest bnplCaptureRequest = new BNPLCaptureRequest();
        bnplCaptureRequest.setId("url1");
        bnplCaptureRequest.setReportGroup("XML10Mer1");
        bnplCaptureRequest.setCustomerId("154646587");
        bnplCaptureRequest.setAmount(106L);
        bnplCaptureRequest.setOrderId("abc");
        bnplCaptureRequest.setCnpTxnId(106l);
        BNPLCaptureResponse response = cnp.bnplCapture(bnplCaptureRequest);
        assertEquals(response.getMessage(), "000",response.getResponse());
        assertEquals("Approved", response.getMessage());
        assertEquals("sandbox", response.getLocation());
    }

}
