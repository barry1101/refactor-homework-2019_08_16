package sales;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class SalesAppTest {

    @Test
    public void testGenerateSalesActivityReport() {
        Sales sales = spy(new Sales());
        SalesApp salesApp = spy(new SalesApp());
        SalesDao salesDao = spy(new SalesDao());
        SalesReportDao salesReportDao = spy(new SalesReportDao());
        SalesReportData salesReportData = spy(new SalesReportData());
        List<SalesReportData> salesReportDataList = Collections.singletonList(salesReportData);
        EcmService ecmService = spy(new EcmService());
        SalesActivityReport salesActivityReport = spy(new SalesActivityReport());

        doReturn(sales).when(salesDao).getSalesBySalesId(anyString());
        doReturn(salesReportDataList).when(salesReportDao).getReportData(any(Sales.class));
        doReturn(salesDao).when(salesApp).getSalesDao();
        doReturn(salesReportDao).when(salesApp).getSalesReportDao();
        doReturn(ecmService).when(salesApp).getEcmService();
        doReturn("").when(salesActivityReport).toXml();
        doReturn(salesActivityReport).when(salesApp).generateReport(anyListOf(String.class), anyListOf(SalesReportData.class));
        doReturn(true).when(salesApp).isInValidityPeriod(any(Sales.class));
        doReturn(Arrays.asList("Sales ID", "Sales Name", "Activity", "Time")).when(salesApp).generateHeaders(false);

        salesApp.generateSalesActivityReport("DUMMY", false);

        verify(ecmService).uploadDocument(anyString());
    }

    @Test
    public void should_return_true_when_sales_is_in_validity_period() {
        SalesApp salesApp = new SalesApp();

        Sales sales = spy(new Sales());

        Calendar yesterday = Calendar.getInstance();
        yesterday.set(Calendar.DATE, yesterday.get(Calendar.DATE) - 1);
        doReturn(yesterday.getTime()).when(sales).getEffectiveFrom();

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.set(Calendar.DATE, tomorrow.get(Calendar.DATE) + 1);
        doReturn(tomorrow.getTime()).when(sales).getEffectiveTo();

        boolean result = salesApp.isInValidityPeriod(sales);
        assertTrue(result);
    }

    @Test
    public void should_return_headers_with_time_column_when_generate_headers_with_national_trade() {
        SalesApp salesApp = new SalesApp();
        List<String> headers = salesApp.generateHeaders(true);
        assertEquals("Time", headers.get(3));
    }

    @Test
    public void should_return_headers_with_local_time_column_when_generate_headers_with_not_national_trade() {
        SalesApp salesApp = new SalesApp();
        List<String> headers = salesApp.generateHeaders(false);
        assertEquals("Local Time", headers.get(3));
    }
}
