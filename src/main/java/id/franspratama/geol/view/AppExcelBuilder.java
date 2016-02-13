package id.franspratama.geol.view;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

public interface AppExcelBuilder<T> {
	public Workbook createWorkbook(List<T> data);
	public Workbook createWorkbook(Workbook workbook, List<T> data);
}
