package com.happyorder.dataproviders;

import com.happyorder.utils.ExcelUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Base abstract class for all Data Providers
 * Provides common utility methods for reading Excel data
 * Follows DRY principle to avoid code duplication
 *
 * @author HappyOrder QA Team
 * @since 1.0
 */
public abstract class BaseDataProvider {

    protected static final String TEST_DATA_PATH =
            System.getProperty("user.dir") + "/src/test/resources/testdata/";

    /**
     * Generic method to read Excel data and convert to Object[][]
     *
     * @param fileName    Excel file name (e.g., "LoginData.xlsx")
     * @param sheetName   Sheet name in Excel file
     * @param columnNames Array of column names to read
     * @return Object[][] containing test data
     * @throws IOException if file cannot be read
     */
    protected static Object[][] readExcelData(String fileName,
                                               String sheetName,
                                               String[] columnNames) throws IOException {
        String filePath = TEST_DATA_PATH + fileName;
        ExcelUtils excel = new ExcelUtils(filePath, sheetName);

        int rowCount = excel.getRowCount() - 1; // Exclude header row
        int colCount = columnNames.length;
        Object[][] data = new Object[rowCount][colCount];

        for (int i = 1; i <= rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                data[i - 1][j] = excel.getCellData(i, columnNames[j]);
            }
        }

        excel.close();
        return data;
    }

    /**
     * Read Excel data using column indices instead of names
     *
     * @param fileName  Excel file name
     * @param sheetName Sheet name
     * @param colCount  Number of columns to read
     * @return Object[][] containing test data
     * @throws IOException if file cannot be read
     */
    protected static Object[][] readExcelDataByIndex(String fileName,
                                                      String sheetName,
                                                      int colCount) throws IOException {
        String filePath = TEST_DATA_PATH + fileName;
        ExcelUtils excel = new ExcelUtils(filePath, sheetName);

        int rowCount = excel.getRowCount() - 1; // Exclude header row
        Object[][] data = new Object[rowCount][colCount];

        for (int i = 1; i <= rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                data[i - 1][j] = excel.getCellData(i, j);
            }
        }

        excel.close();
        return data;
    }

    /**
     * Filter data based on a specific column value
     *
     * @param data        Original data array
     * @param columnIndex Index of column to filter on
     * @param filterValue Value to filter by
     * @return Filtered Object[][] array
     */
    protected static Object[][] filterData(Object[][] data, int columnIndex, String filterValue) {
        List<Object[]> filteredList = new ArrayList<>();

        for (Object[] row : data) {
            if (row[columnIndex].toString().equalsIgnoreCase(filterValue)) {
                filteredList.add(row);
            }
        }

        Object[][] filtered = new Object[filteredList.size()][];
        return filteredList.toArray(filtered);
    }

    /**
     * Get file path for test data file
     *
     * @param fileName Name of the test data file
     * @return Full file path
     */
    protected static String getTestDataFilePath(String fileName) {
        return TEST_DATA_PATH + fileName;
    }

    /**
     * Read a single row of data from Excel
     *
     * @param fileName    Excel file name
     * @param sheetName   Sheet name
     * @param rowIndex    Row index (1-based, excluding header)
     * @param columnNames Column names to read
     * @return Object[] containing single row data
     * @throws IOException if file cannot be read
     */
    protected static Object[] readSingleRow(String fileName,
                                           String sheetName,
                                           int rowIndex,
                                           String[] columnNames) throws IOException {
        String filePath = TEST_DATA_PATH + fileName;
        ExcelUtils excel = new ExcelUtils(filePath, sheetName);

        Object[] rowData = new Object[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            rowData[i] = excel.getCellData(rowIndex, columnNames[i]);
        }

        excel.close();
        return rowData;
    }
}
