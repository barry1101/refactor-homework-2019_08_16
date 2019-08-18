package sales;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

    public void generateSalesActivityReport(String salesId, boolean isNatTrade) {
        if (salesId == null) return;

        SalesDao salesDao = getSalesDao();
        Sales sales = salesDao.getSalesBySalesId(salesId);
        if (!isInValidityPeriod(sales)) return;

        SalesReportDao salesReportDao = getSalesReportDao();
        List<SalesReportData> reportDataList = salesReportDao.getReportData(sales);
        List<String> headers = generateHeaders(isNatTrade);
        SalesActivityReport report = this.generateReport(headers, reportDataList);

        EcmService ecmService = getEcmService();
        ecmService.uploadDocument(report.toXml());
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
