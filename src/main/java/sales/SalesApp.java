package sales;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

    public void generateSalesActivityReport(String salesId, boolean isNatTrade) {
        if (salesId == null) return;

        Sales sales = getSalesDao().getSalesBySalesId(salesId);
        if (!isInValidityPeriod(sales)) return;

        List<SalesReportData> reportDataList = getSalesReportDao().getReportData(sales);
        List<String> headers = generateHeaders(isNatTrade);
        SalesActivityReport report = this.generateReport(headers, reportDataList);

        uploadDocument(report);
    }

    protected boolean isInValidityPeriod(Sales sales) {
        Date today = new Date();
        return !today.after(sales.getEffectiveTo())
                && !today.before(sales.getEffectiveFrom());
    }

    protected List<String> generateHeaders(boolean isNatTrade) {
        String column_4 = isNatTrade ? "Time" : "Local Time";
        return Arrays.asList("Sales ID", "Sales Name", "Activity", column_4);
    }

    protected SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
        return null;
    }

    protected void uploadDocument(SalesActivityReport report) {
        getEcmService().uploadDocument(report.toXml());
    }

    protected SalesDao getSalesDao() {
        return null;
    }

    protected SalesReportDao getSalesReportDao() {
        return null;
    }

    protected EcmService getEcmService() {
        return null;
    }
}
