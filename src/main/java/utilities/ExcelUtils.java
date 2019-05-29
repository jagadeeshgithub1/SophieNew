/**
 * 
 */
package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReader;

/**
 * @author deepa :This class consists of basic excel utility functions required
 *         for the project
 */
public class ExcelUtils {

	public FileInputStream fis = null;
	public FileOutputStream fout = null;
	public XSSFWorkbook wbk = null;
	public XSSFWorkbook wbk2 = null;
	public XSSFWorkbook wbkResult = null;
	public XSSFSheet sh = null;
	public XSSFCell Cell = null;
	public XSSFRow row1 = null;
	public DataFormatter formatter = null;
	public String excelPath = null;

	public ExcelUtils(String excelPath) throws Exception {

		// File src = new File(excelPath);
		this.excelPath = excelPath;

		fis = new FileInputStream(excelPath);
		// fout=new FileOutputStream(new File(excelPath));
		wbk = new XSSFWorkbook(fis);
		// wbkResult= new XSSFWorkbook();
		fis.close();

	}

	/*
	 * public String getCellData(int Row, int Col) throws Exception { try { sh =
	 * wbk.getSheet(sheetName); Cell = sh.getRow(Row).getCell(Col);
	 * 
	 * String CellData = Cell.getStringCellValue(); return CellData; } catch
	 * (Exception e) { throw (e); } }
	 */

	public String getCellData(String sheetName, int row, String col) {

		int colIdx = -1;
		String CellData = null;

		// System.out.println("i ma in getcelldata and sheetname:" + sheetName);

		sh = wbk.getSheet(sheetName);
		row1 = sh.getRow(row);
		// System.out.print(sh);

		// row1=sh.getRow(0);

		for (int i = 0; i < row1.getLastCellNum(); i++) {

			if (sh.getRow(3).getCell(i).toString().trim().equals(col)) {
				colIdx = i;
			}

		}

		Cell = row1.getCell(colIdx);

		if (Cell.getCellTypeEnum() == CellType.STRING) {

			CellData = sh.getRow(row).getCell(colIdx).getStringCellValue();
		} else if (Cell.getCellTypeEnum() == CellType.NUMERIC || Cell.getCellTypeEnum() == CellType.FORMULA) {
			// CellData =
			// String.valueOf((sh.getRow(row).getCell(colIdx).getNumericCellValue()));
			CellData = String.valueOf(Cell.getNumericCellValue());
			// CellValue cv = formulaEv.evaluate(cell);
			// double dv = cv.getNumberValue();
			if (HSSFDateUtil.isCellDateFormatted(Cell)) {
				// DateFormat df= new SimpleDateFormat("dd/mm/yy");
				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
				CellData = dateformat.format(Cell.getDateCellValue());
				// CellData=formatter.formatCellValue(Cell);

				// CellData= new CellDateFormatter(df).format(date);
			}
		}

		return CellData;

	}

	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		try {
			fis = new FileInputStream(excelPath);
			wbk = new XSSFWorkbook(fis);

			if (rowNum <= 0)
				return false;

			int index = wbk.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			System.out.println("Sheet index" + index);
			sh = wbk.getSheetAt(index);

			System.out.println("sheet:" + sh);

			XSSFRow row = sh.getRow(3);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}

			System.out.println("Column number :" + colNum + "row number: " + rowNum);
			if (colNum == -1)
				return false;

			// sh.autoSizeColumn(colNum);
			row = sh.getRow(rowNum);// changed now from rowNum-1
			if (row == null)
				row = sh.createRow(rowNum);

			Cell = row.getCell(colNum);
			if (Cell == null)
				Cell = row.createCell(colNum);

			Cell.setCellValue(data);

			// String excelPath2=excelPath.

			fout = new FileOutputStream(excelPath);

			wbk.write(fout);

			fout.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// this method is used to save the file in the path provided
	public void saveFile(String Path) {
		try {
			FileOutputStream fileOut = new FileOutputStream(Path);
			// wbk2 = new XSSFWorkbook(fileOut);
			wbk.write(fileOut);
			fileOut.close();
		} catch (Exception e) {

		}
	}

	// this method to get the rowcount of the sheetname
	public int getRowCount(String SheetName) {

		sh = wbk.getSheet(SheetName);
		// System.out.println("sh object value:>>"+sh);
		int RowCount = sh.getLastRowNum();
		// System.out.println("rowcount:>>"+RowCount);
		return RowCount;
	}

	public int getColumnCount(String SheetName) {
		int colCount = 0;
		sh = wbk.getSheet(SheetName);
		colCount = sh.getRow(0).getLastCellNum();

		return colCount;

	}

	public String getCellData(int RowNum, int ColNum, String SheetName) throws Exception {
		try {

			sh = wbk.getSheet(SheetName);
			Cell = sh.getRow(RowNum).getCell(ColNum);
			String CellData = Cell.getStringCellValue();
			return CellData;
		} catch (Exception e) {
			System.out.println("Class Utils | Method getCellData | Exception desc : " + e.getMessage());

			return "";
		}
	}

	// This method is to get the Row number of the test case
	// This methods takes three arguments(Test Case name , Column Number & Sheet
	// name)
	public int getRowContains(String sTestCaseName, String columnName, String SheetName) throws Exception {
		int i;
		// ExcelWSheet = ExcelWBook.getSheet(SheetName);
		int rowCount = getRowCount(SheetName);
		for (i = 4; i <= rowCount; i++) {
			if (getCellData(SheetName, i, columnName).equalsIgnoreCase(sTestCaseName)) {
				break;
			}
		}
		return i;
	}

	// This method is to get the count of the test steps of test case
	// This method takes three arguments (Sheet name, Test Case Id & Test case row
	// number)
	public int getTestStepsCount(String SheetName, String sTestCaseID, int iTestCaseStart, String columnName)
			throws Exception {
		int TestCount = 0;
		for (int i = iTestCaseStart; i <= getRowCount(SheetName); i++) {
			if (!sTestCaseID.equals(getCellData(SheetName, i, columnName))) {
				TestCount = i - 1;
				// TestCount = i;
				return TestCount;
			}
		}

		/// ExcelWSheet = ExcelWBook.getSheet(SheetName);

		// sh = wbk.getSheet(SheetName);

		// TestCount = sh.getLastRowNum();
		TestCount = getRowCount(SheetName);
		return TestCount;
	}

	public int getTotalScenarios(String sheetName, int row, String column) {

		int RowCount = 0;

		sh = wbk.getSheet(sheetName);
		// System.out.println("sh object value:>>"+sh);
		RowCount = sh.getLastRowNum() + 1;

		return RowCount;

	}

	public File getLatestFileFromDir(String dirPath) {
		File dir = new File(dirPath);

		File[] files = dir.listFiles();

		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];

				System.out.println(lastModifiedFile);
			}
		}
		return lastModifiedFile;

	}

	public int toGetTheNumberOfFieldsInCSV(File file) throws Exception {
		int CsvColumnCount = 0;
		CSVReader csvReader = new CSVReader(new FileReader(file));

		String[] header = csvReader.readNext();
		csvReader.close();

		if (header != null) {
			CsvColumnCount = header.length;
		}

		return CsvColumnCount;

	}

}
