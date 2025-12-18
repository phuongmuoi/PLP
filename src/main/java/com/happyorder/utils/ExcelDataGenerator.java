package com.happyorder.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelDataGenerator {

    public static void createTestDataFile(String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("TestData");

        // Create header row style
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        // Create header row with new structure
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Title", "Step", "UserName", "Password", "Expected Message", "Result(pass/Failed)"};

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        // Create data style
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setWrapText(true); // Enable text wrapping for Step column

        // Sample test data with new structure
        Object[][] testData = {
            {
                "TC001 - Valid Login Test",
                "1. Truy cập trang đăng nhập\n2. Nhập username hợp lệ\n3. Nhập password hợp lệ\n4. Click button ĐĂNG NHẬP",
                "testuser@gmail.com",
                "Test@123",
                "Đăng nhập thành công, chuyển đến trang profile",
                ""  // Will be filled after test execution
            },
            {
                "TC002 - Invalid Username Test",
                "1. Truy cập trang đăng nhập\n2. Nhập username không đúng\n3. Nhập password hợp lệ\n4. Click button ĐĂNG NHẬP",
                "wronguser@gmail.com",
                "Test@123",
                "Hiển thị lỗi: Tên tài khoản hoặc mật khẩu không chính xác",
                ""
            },
            {
                "TC003 - Invalid Password Test",
                "1. Truy cập trang đăng nhập\n2. Nhập username hợp lệ\n3. Nhập password không đúng\n4. Click button ĐĂNG NHẬP",
                "testuser@gmail.com",
                "WrongPass123",
                "Hiển thị lỗi: Tên tài khoản hoặc mật khẩu không chính xác",
                ""
            }
        };

        // Fill data rows
        int rowNum = 1;
        for (Object[] data : testData) {
            Row row = sheet.createRow(rowNum++);

            for (int i = 0; i < data.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(data[i].toString());
                cell.setCellStyle(dataStyle);
            }
        }

        // Auto-size columns with minimum widths
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
            // Set minimum width for specific columns
            if (i == 1) { // Step column
                sheet.setColumnWidth(i, 15000);
            } else if (i == 4) { // Expected Message column
                sheet.setColumnWidth(i, 12000);
            } else if (i == 0) { // Title column
                sheet.setColumnWidth(i, 8000);
            }
        }

        // Write to file
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            FileOutputStream fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            System.out.println("Excel file created successfully at: " + filePath);
        } catch (IOException e) {
            System.out.println("Error creating Excel file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/LoginData.xlsx";
        createTestDataFile(filePath);
    }
}
