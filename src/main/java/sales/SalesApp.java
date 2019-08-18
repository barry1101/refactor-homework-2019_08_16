package sales;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

    public void generateSalesActivityReport(String salesId, int maxRow, boolean isNatTrade, boolean isSupervisor) {
        SalesDao salesDao = getSalesDao();
        SalesReportDao salesReportDao = getSalesReportDao();

        if (salesId == null) return;

        Sales sales = salesDao.getSalesBySalesId(salesId);

        if (!isInValidityPeriod(sales)) return;

        List<SalesReportData> reportDataList = salesReportDao.getReportData(sales);

        List<String> headers;
        if (isNatTrade) {
            headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
        } else {
            headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
        }

        SalesActivityReport report = this.generateReport(headers, reportDataList);

        EcmService ecmService = getEcmService();
        ecmService.uploadDocument(report.toXml());

    }

    protected boolean isInValidityPeriod(Sales sales) {
        Date today = new Date();
        return !today.after(sales.getEffectiveTo())
                && !today.before(sales.getEffectiveFrom());
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
