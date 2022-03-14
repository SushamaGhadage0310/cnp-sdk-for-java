package io.github.vantiv.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

//import com.cnp.sdk.generate.*;
import java.util.Properties;

import io.github.vantiv.sdk.generate.*;

import org.hamcrest.MatcherAssert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class TestQueryTransaction {

    private static CnpOnline cnp;

    @BeforeClass
    public static void beforeClass() throws Exception {
        cnp = new CnpOnline();
    }

    @Test
    public void simpleQueryTransaction() throws Exception {
        QueryTransaction queryTransaction = new QueryTransaction();
        queryTransaction.setId("findId");
        queryTransaction.setCustomerId("customerId");
        queryTransaction.setOrigId("orgId1");
        queryTransaction.setOrigActionType(ActionTypeEnum.A);
        queryTransaction.setReportGroup("default");

        TransactionTypeWithReportGroup response = cnp.queryTransaction(queryTransaction);
        QueryTransactionResponse queryTransactionResponse = (QueryTransactionResponse)response;
        assertEquals("findId", queryTransactionResponse.getId());
        assertEquals("customerId", queryTransactionResponse.getCustomerId());
        assertEquals("150", queryTransactionResponse.getResponse());
        assertEquals("Original transaction found",queryTransactionResponse.getMessage());
        assertEquals(1, queryTransactionResponse.getResultsMax10().getTransactionResponses().size());
        assertEquals("sandbox", queryTransactionResponse.getLocation());
    }

    @Test
    public void simpleQueryTransaction_showStatusOnly() throws Exception {
        QueryTransaction queryTransaction = new QueryTransaction();
        queryTransaction.setId("findId");
        queryTransaction.setCustomerId("customerId");
        queryTransaction.setOrigId("orgId1");
        queryTransaction.setOrigActionType(ActionTypeEnum.A);
        queryTransaction.setReportGroup("default");
        queryTransaction.setShowStatusOnly(YesNoType.Y);

        TransactionTypeWithReportGroup response = cnp.queryTransaction(queryTransaction);
        QueryTransactionResponse queryTransactionResponse = (QueryTransactionResponse)response;
        assertEquals("findId", queryTransactionResponse.getId());
        assertEquals("customerId", queryTransactionResponse.getCustomerId());
        assertEquals("150", queryTransactionResponse.getResponse());
        assertEquals("Original transaction found",queryTransactionResponse.getMessage());
        assertEquals(1, queryTransactionResponse.getResultsMax10().getTransactionResponses().size());
        assertEquals("sandbox", queryTransactionResponse.getLocation());
    }

    @Test
    public void simpleQueryTransaction_multipleResponses() throws Exception {
        QueryTransaction queryTransaction = new QueryTransaction();
        queryTransaction.setId("findId");
        queryTransaction.setCustomerId("customerId");
        queryTransaction.setOrigId("orgId2");
        queryTransaction.setOrigActionType(ActionTypeEnum.D);
        queryTransaction.setReportGroup("default");

        TransactionTypeWithReportGroup response = cnp.queryTransaction(queryTransaction);
        QueryTransactionResponse queryTransactionResponse = (QueryTransactionResponse)response;
        assertEquals("findId", queryTransactionResponse.getId());
        assertEquals("customerId", queryTransactionResponse.getCustomerId());
        assertEquals("150", queryTransactionResponse.getResponse());
        assertEquals("Original transaction found",queryTransactionResponse.getMessage());
        assertEquals(2, queryTransactionResponse.getResultsMax10().getTransactionResponses().size());
        assertEquals("sandbox", queryTransactionResponse.getLocation());
    }

    @Test
    public void simpleQueryTransaction_transactionNotFound() throws Exception {
        QueryTransaction queryTransaction = new QueryTransaction();
        queryTransaction.setId("findId");
        queryTransaction.setCustomerId("customerId");
        queryTransaction.setOrigId("orgId0");
        queryTransaction.setOrigActionType(ActionTypeEnum.A);
        queryTransaction.setReportGroup("default");

        TransactionTypeWithReportGroup response = cnp.queryTransaction(queryTransaction);
        QueryTransactionResponse queryTransactionResponse = (QueryTransactionResponse)response;
        assertEquals("findId", queryTransactionResponse.getId());
        assertEquals("customerId", queryTransactionResponse.getCustomerId());
        assertEquals("151", queryTransactionResponse.getResponse());
        assertEquals("Original transaction not found",queryTransactionResponse.getMessage());
        assertNull(queryTransactionResponse.getResultsMax10());
        assertEquals("sandbox", queryTransactionResponse.getLocation());
    }

    @Test
    public void simpleQueryTransaction_transactionNotFoundWrongActionType() throws Exception {
        QueryTransaction queryTransaction = new QueryTransaction();
        queryTransaction.setId("findId");
        queryTransaction.setCustomerId("customerId");
        queryTransaction.setOrigId("orgId5");
        queryTransaction.setOrigActionType(ActionTypeEnum.R);
        queryTransaction.setReportGroup("default");

        TransactionTypeWithReportGroup response = cnp.queryTransaction(queryTransaction);
        QueryTransactionResponse queryTransactionResponse = (QueryTransactionResponse)response;
        assertEquals("findId", queryTransactionResponse.getId());
        assertEquals("customerId", queryTransactionResponse.getCustomerId());
        assertEquals("151", queryTransactionResponse.getResponse());
        assertEquals("Original transaction not found",queryTransactionResponse.getMessage());
        assertNull(queryTransactionResponse.getResultsMax10());
        assertEquals("sandbox", queryTransactionResponse.getLocation());
    }

    @Test
    public void simpleQueryTransaction_queryTransactionUnavailaleResponse() throws Exception {
        QueryTransaction queryTransaction = new QueryTransaction();
        queryTransaction.setId("findId");
        queryTransaction.setCustomerId("customerId");
        queryTransaction.setOrigId("orgId");
        queryTransaction.setOrigActionType(ActionTypeEnum.A);
        queryTransaction.setReportGroup("default");

        TransactionTypeWithReportGroup response = cnp.queryTransaction(queryTransaction);
        QueryTransactionUnavailableResponse queryTransactionResponse = (QueryTransactionUnavailableResponse)response;
        assertEquals("findId", queryTransactionResponse.getId());
        assertEquals("customerId", queryTransactionResponse.getCustomerId());
        assertEquals("152", queryTransactionResponse.getResponse());
        assertEquals("Original transaction found but response not yet available",queryTransactionResponse.getMessage());
        assertEquals("sandbox", queryTransactionResponse.getLocation());
    }

    @Test
    public void simpleQueryTransaction_queryTransactionFoundInPrimarySite() throws Exception {
        Properties p= Mockito.mock(Properties.class);
        CnpOnline cnpOnlineMock=Mockito.mock(CnpOnline.class);
        QueryTransaction queryTransaction=Mockito.mock(QueryTransaction.class);
        queryTransaction.setId("findId");
        queryTransaction.setCustomerId("customerId");
        queryTransaction.setOrigId("orgId5");
        queryTransaction.setOrigActionType(ActionTypeEnum.A);
        queryTransaction.setReportGroup("default");
        QueryTransactionResponse resp=new QueryTransactionResponse();
        resp.setMessage("Original transaction found");
        resp.setResponse("150");
        Mockito.when(p.getProperty(Mockito.<String>any(), Mockito.<String>any())).thenReturn("https://payments.east.vantivprelive.com/vap/communicator/online");
        Mockito.when(cnpOnlineMock.queryTransaction(queryTransaction)).thenReturn(resp);
        TransactionTypeWithReportGroup response = cnpOnlineMock.queryTransaction(queryTransaction);
        QueryTransactionResponse queryTransactionResponse = (QueryTransactionResponse)response;
        assertEquals("Original transaction found",queryTransactionResponse.getMessage());
        assertEquals("150", queryTransactionResponse.getResponse());
    }

    @Test
    public void simpleQueryTransaction_TransactionNotFoundInPrimarySiteButFoundInSecondarySite() throws Exception {
        Properties p= Mockito.mock(Properties.class);
        CnpOnline cnpOnlineMock=Mockito.mock(CnpOnline.class);
        QueryTransaction queryTransaction=Mockito.mock(QueryTransaction.class);
        queryTransaction.setId("findId");
        queryTransaction.setCustomerId("customerId");
        queryTransaction.setOrigId("orgId5");
        queryTransaction.setOrigActionType(ActionTypeEnum.A);
        queryTransaction.setReportGroup("default");
        QueryTransactionResponse resp=new QueryTransactionResponse();
        resp.setMessage("Original transaction found");
        resp.setResponse("150");
        Mockito.when(p.getProperty(Mockito.matches("multiSiteUrl1"), Mockito.<String>any())).thenReturn("https://payments.east.vantivprelive.com/vap/communicator/online");
        Mockito.when(p.getProperty(Mockito.matches("multiSiteUrl2"), Mockito.<String>any())).thenReturn("https://payments.west.vantivprelive.com/vap/communicator/online");
        Mockito.when(cnpOnlineMock.queryTransaction(queryTransaction)).thenReturn(resp);
        TransactionTypeWithReportGroup response = cnpOnlineMock.queryTransaction(queryTransaction);
        QueryTransactionResponse queryTransactionResponse = (QueryTransactionResponse)response;
        System.out.println("Value is: "+((QueryTransactionResponse) response).getMessage());
        assertEquals("Original transaction found",queryTransactionResponse.getMessage());
        assertEquals("150", queryTransactionResponse.getResponse());
    }

    @Test
    public void simpleQueryTransaction_TransactionNotFoundInBothPrimaryAmdSecondarySite() throws Exception {
        Properties p = Mockito.mock(Properties.class);
        CnpOnline cnpOnlineMock = Mockito.mock(CnpOnline.class);
        QueryTransaction queryTransaction = Mockito.mock(QueryTransaction.class);
        queryTransaction.setId("findId");
        queryTransaction.setCustomerId("customerId");
        queryTransaction.setOrigId("orgId0");
        queryTransaction.setOrigActionType(ActionTypeEnum.A);
        queryTransaction.setReportGroup("default");
        QueryTransactionResponse resp = new QueryTransactionResponse();
        resp.setMessage("Original transaction not found");
        resp.setResponse("151");
        Mockito.when(p.getProperty(Mockito.matches("multiSiteUrl1"), Mockito.<String>any())).thenReturn("https://payments.east.vantivprelive.com/vap/communicator/online");
        Mockito.when(p.getProperty(Mockito.matches("multiSiteUrl2"), Mockito.<String>any())).thenReturn("https://payments.west.vantivprelive.com/vap/communicator/online");
        Mockito.when(cnpOnlineMock.queryTransaction(queryTransaction)).thenReturn(resp);
        TransactionTypeWithReportGroup response = cnpOnlineMock.queryTransaction(queryTransaction);
        QueryTransactionResponse queryTransactionResponse = (QueryTransactionResponse) response;
        System.out.println("Value is: " + ((QueryTransactionResponse) response).getMessage());
        assertEquals("Original transaction not found", queryTransactionResponse.getMessage());
        assertEquals("151", queryTransactionResponse.getResponse());
    }

    @Test
    public void simpleQueryTransaction_TransactionFoundButResponseNotAvailable() throws Exception {
        Properties p = Mockito.mock(Properties.class);
        CnpOnline cnpOnlineMock = Mockito.mock(CnpOnline.class);
        QueryTransaction queryTransaction = Mockito.mock(QueryTransaction.class);
        queryTransaction.setId("findId");
        queryTransaction.setCustomerId("customerId");
        queryTransaction.setOrigId("orgId");
        queryTransaction.setOrigActionType(ActionTypeEnum.A);
        queryTransaction.setReportGroup("default");
        QueryTransactionResponse resp = new QueryTransactionResponse();
        resp.setMessage("Original transaction found but response not yet available");
        resp.setResponse("152");
        Mockito.when(p.getProperty(Mockito.matches("multiSiteUrl1"), Mockito.<String>any())).thenReturn("https://payments.east.vantivprelive.com/vap/communicator/online");
        Mockito.when(p.getProperty(Mockito.matches("multiSiteUrl2"), Mockito.<String>any())).thenReturn("https://payments.west.vantivprelive.com/vap/communicator/online");
        Mockito.when(cnpOnlineMock.queryTransaction(queryTransaction)).thenReturn(resp);
        TransactionTypeWithReportGroup response = cnpOnlineMock.queryTransaction(queryTransaction);
        QueryTransactionResponse queryTransactionResponse = (QueryTransactionResponse) response;
        System.out.println("Value is: " + ((QueryTransactionResponse) response).getMessage());
        assertEquals("Original transaction found but response not yet available", queryTransactionResponse.getMessage());
        assertEquals("152", queryTransactionResponse.getResponse());
    }

    @Test
    public void simpleQueryTransaction_PrimarySiteDownTransactionFoundInSecondarySite() throws Exception {
        QueryTransaction queryTransaction = new QueryTransaction();
        queryTransaction.setId("findId");
        queryTransaction.setCustomerId("customerId");
        queryTransaction.setOrigId("orgId1");
        queryTransaction.setOrigActionType(ActionTypeEnum.A);
        queryTransaction.setReportGroup("default");
        TransactionTypeWithReportGroup response = cnp.queryTransaction(queryTransaction);
        QueryTransactionResponse queryTransactionResponse = (QueryTransactionResponse)response;
        System.out.println("Value is: "+queryTransactionResponse.getLocation());
        assertEquals("findId", queryTransactionResponse.getId());
        assertEquals("customerId", queryTransactionResponse.getCustomerId());
        assertEquals("150", queryTransactionResponse.getResponse());
        assertEquals("Original transaction found",queryTransactionResponse.getMessage());
        assertEquals("sandbox", queryTransactionResponse.getLocation());
    }

    @Test
    public void simpleQueryTransaction_PrimarySiteDownTransactionNotFoundInSecondarySite() throws Exception {
        QueryTransaction queryTransaction = new QueryTransaction();
        queryTransaction.setId("findId");
        queryTransaction.setCustomerId("customerId");
        queryTransaction.setOrigId("orgId0");
        queryTransaction.setOrigActionType(ActionTypeEnum.A);
        queryTransaction.setReportGroup("default");
        TransactionTypeWithReportGroup response = cnp.queryTransaction(queryTransaction);
        QueryTransactionResponse queryTransactionResponse = (QueryTransactionResponse)response;
        assertEquals("findId", queryTransactionResponse.getId());
        assertEquals("customerId", queryTransactionResponse.getCustomerId());
        assertEquals("151", queryTransactionResponse.getResponse());
        MatcherAssert.assertThat("Site down",queryTransactionResponse.getMessage().contains("Transaction not found - Site Down : "));
        assertEquals("sandbox", queryTransactionResponse.getLocation());
    }

    @Test
    public void simpleQueryTransaction_bothSiteDown() throws Exception {
        Properties p= Mockito.mock(Properties.class);
        CnpOnline cnpOnlineMock=Mockito.mock(CnpOnline.class);
        QueryTransaction queryTransaction=Mockito.mock(QueryTransaction.class);
        queryTransaction.setId("findId");
        queryTransaction.setCustomerId("customerId");
        queryTransaction.setOrigId("orgId5");
        queryTransaction.setOrigActionType(ActionTypeEnum.A);
        queryTransaction.setReportGroup("default");
        QueryTransactionResponse resp=new QueryTransactionResponse();
        resp.setMessage("Transaction not found - Sites Down");
        Mockito.when(p.getProperty(Mockito.matches("multiSiteUrl1"), Mockito.<String>any())).thenReturn("https://payments.east.vantivprelive.com/vap/communicator/online");
        Mockito.when(p.getProperty(Mockito.matches("multiSiteUrl2"), Mockito.<String>any())).thenReturn("https://payments.west.vantivprelive.com/vap/communicator/online");
        Mockito.when(cnpOnlineMock.queryTransaction(queryTransaction)).thenReturn(resp);
        TransactionTypeWithReportGroup response = cnpOnlineMock.queryTransaction(queryTransaction);
        QueryTransactionResponse queryTransactionResponse = (QueryTransactionResponse)response;
        assertEquals("Transaction not found - Sites Down",queryTransactionResponse.getMessage());
    }
}