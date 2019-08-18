package sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

	public void generateSalesActivityReport(String salesId, int maxRow, boolean isNatTrade, boolean isSupervisor) {
		SalesDao salesDao = getSalesDao();
		SalesReportDao salesReportDao = getSalesReportDao();
		List<String> headers = null;

		List<SalesReportData> filteredReportDataList = new ArrayList<SalesReportData>();

		if (salesId == null) {
			return;
		}

		Sales sales = salesDao.getSalesBySalesId(salesId);

		Date today = new Date();
		if (today.after(sales.getEffectiveTo())
				|| today.before(sales.getEffectiveFrom())){
			return;
		}

		List<SalesReportData> reportDataList = salesReportDao.getReportData(sales);

		for (SalesReportData data : reportDataList) {
			if ("SalesActivity".equalsIgnoreCase(data.getType())) {
				if (data.isConfidential()) {
					if (isSupervisor) {
						filteredReportDataList.add(data);
					}
				}else {
					filteredReportDataList.add(data);
				}
			}
		}

		List<SalesReportData> tempList = new ArrayList<SalesReportData>();
		for (int i=0; i < reportDataList.size() || i < maxRow; i++) {
			tempList.add(reportDataList.get(i));
		}
		filteredReportDataList = tempList;

		if (isNatTrade) {
			headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
		} else {
			headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
		}

		SalesActivityReport report = this.generateReport(headers, reportDataList);

		EcmService ecmService = getEcmService();
		ecmService.uploadDocument(report.toXml());

	}

	protected SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
		// TODO Auto-generated method stub
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
