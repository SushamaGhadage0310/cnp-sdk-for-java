package io.github.vantiv.sdk;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import io.github.vantiv.sdk.generate.FinicityAccountRequest;
import io.github.vantiv.sdk.generate.FinicityAccountResponse;

public class TestFinicityAccountRequest {

	private static CnpOnline cnp;

	@BeforeClass
	public static void beforeClass() throws Exception {
		cnp = new CnpOnline();
	}
	

    @Test
    public void testSimpleFinicityReqeust() throws Exception {
		FinicityAccountRequest finicityAccountRequest = new FinicityAccountRequest();
        finicityAccountRequest.setId("url1");
        finicityAccountRequest.setReportGroup("XML10Mer1");
        finicityAccountRequest.setCustomerId("154646587");
        finicityAccountRequest.setEcheckCustomerId("ABC");
        FinicityAccountResponse response = cnp.finicityAccount(finicityAccountRequest);
        assertEquals(response.getMessage(), "000",response.getResponse());
        assertEquals("Approved", response.getMessage());
        assertEquals("sandbox", response.getLocation());
    }

}
