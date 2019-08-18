package sales;

import static org.mockito.Mockito.*;

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
    when(salesReportData.isConfidential()).thenReturn(false);
    List<SalesReportData> salesReportDataList = Collections.singletonList(salesReportData);
    EcmService ecmService = spy(new EcmService());
    SalesActivityReport salesActivityReport = spy(new SalesActivityReport());

    when(salesDao.getSalesBySalesId(anyString())).thenReturn(sales);
    when(salesReportDao.getReportData(any(Sales.class))).thenReturn(salesReportDataList);
    when(salesApp.getSalesDao()).thenReturn(salesDao);
    when(salesApp.getSalesReportDao()).thenReturn(salesReportDao);
    when(salesApp.getEcmService()).thenReturn(ecmService);
    when(salesActivityReport.toXml()).thenReturn("");
    when(salesApp.generateReport(anyListOf(String.class), anyListOf(SalesReportData.class)))
        .thenReturn(salesActivityReport);

    Calendar yesterday = Calendar.getInstance();
    yesterday.set(Calendar.DATE, yesterday.get(Calendar.DATE) - 1);
    when(sales.getEffectiveFrom()).thenReturn(yesterday.getTime());

    Calendar tomorrow = Calendar.getInstance();
    tomorrow.set(Calendar.DATE, tomorrow.get(Calendar.DATE) + 1);
    when(sales.getEffectiveTo()).thenReturn(tomorrow.getTime());

    salesApp.generateSalesActivityReport("DUMMY", 1, false, false);

    verify(ecmService).uploadDocument(anyString());
  }
}
