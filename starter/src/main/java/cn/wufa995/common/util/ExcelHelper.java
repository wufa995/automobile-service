//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.wufa995.common.util;

import cn.wufa995.web.entity.ExcelSheetData;
import cn.wufa995.common.enums.ExcelType;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelHelper {
    private static final Logger log = LoggerFactory.getLogger(ExcelHelper.class);
    private String fileName = "";
    private String fileTitle = "";
    private List<ExcelSheetData> sheetDatas = new ArrayList();
    private HSSFFont headerFont;
    private HSSFCellStyle headerStyle;
    private HSSFCellStyle cellStyle;
    private HSSFCellStyle oddStyle;
    private boolean isWrapText = false;

    public ExcelHelper() {
    }

    public static String getValueAsString(Sheet sheet, int rowNum, int colNum) {
        String value = getValue(sheet, rowNum, colNum);
        int len = value.length();
        if (len >= 2) {
            String rex = value.substring(len - 2, len);
            String end = ".0";
            if (end.equals(rex)) {
                value = value.substring(0, len - 2);
            }
        }

        return value;
    }

    public static String getValue(Sheet sheet, int rowNum, int colNum) {
        if (sheet == null) {
            return "";
        } else {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                return "";
            } else {
                Cell cell = row.getCell(colNum);
                return cell == null ? "" : getValue(cell);
            }
        }
    }

    public static String getValue(Cell hssfCell) {
//        if (hssfCell.getCellType() == 4) {
//            return String.valueOf(hssfCell.getBooleanCellValue()).trim();
//        } else {
//            return hssfCell.getCellType() == 0 ? String.valueOf(hssfCell.getNumericCellValue()).trim() : String.valueOf(hssfCell.getStringCellValue()).trim();
//        }
        return null;
    }

    public static Sheet getSheetFromFile(MultipartFile imgFile) {
        return getSheetFromFile(imgFile, 0);
    }

    public static Sheet getSheetFromStream(InputStream stream, int index) {
        XSSFSheet sheet = null;

        try {
            XSSFWorkbook wb = new XSSFWorkbook(stream);
            sheet = wb.getSheetAt(index);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return sheet;
    }

    public static Sheet getSheetFromFile(MultipartFile imgFile, int index) {
        String excelName = imgFile.getOriginalFilename();
        Object sheet = null;

        try {
            String xlsx = "xlsx";
            if (excelName.indexOf(xlsx) == -1) {
                HSSFWorkbook wb = new HSSFWorkbook(imgFile.getInputStream());
                sheet = wb.getSheetAt(index);
            } else {
                XSSFWorkbook wb = new XSSFWorkbook(imgFile.getInputStream());
                sheet = wb.getSheetAt(index);
            }
        } catch (IOException var6) {
            var6.printStackTrace();
        }

        return (Sheet)sheet;
    }

    public Object exportToResponse(HttpServletResponse response) {
        try {
            String excelFileName = this.getFileName();
            excelFileName = new String(excelFileName.getBytes("utf-8"), "ISO8859-1");
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-disposition", "attachment; filename=" + excelFileName);
            OutputStream os = response.getOutputStream();
            HSSFWorkbook workbook = this.exportExcel();
            workbook.getBytes();
            workbook.write(os);
            os.close();
            return null;
        } catch (Exception var5) {
            var5.printStackTrace();
            return "导出数据出错";
        }
    }

    public Object exportToResponseByPost(HttpServletResponse response) {
        try {
            String excelFileName = this.getFileName();
            excelFileName = new String(excelFileName.getBytes("utf-8"), "ISO8859-1");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-disposition", "attachment; filename* = UTF-8''"+ excelFileName);
            OutputStream os = response.getOutputStream();
            HSSFWorkbook workbook = this.exportExcel();
            workbook.getBytes();
            workbook.write(os);
            os.close();
            return null;
        } catch (Exception var5) {
            var5.printStackTrace();
            return "导出数据出错";
        }
    }

    public byte[] exportToBytes() {
        try {
            HSSFWorkbook workBook = this.exportExcel();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workBook.write(os);
            return os.toByteArray();
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public HSSFWorkbook exportExcel() {
        HSSFWorkbook workbook = this.createWorkbook();
        this.sheetDatas.forEach((sheet) -> {
            if (sheet.getExcelType() == ExcelType.NORMAL) {
                this.commonExport(workbook, sheet);
            } else if (sheet.getExcelType() == ExcelType.ROW_GROUP) {
                this.rowGroupExport(workbook, sheet);
            } else if (sheet.getExcelType() == ExcelType.ROW_ODD) {
                this.rowOddExport(workbook, sheet);
            }

        });
        return workbook;
    }

    public HSSFWorkbook commonExport(HSSFWorkbook workbook, ExcelSheetData sheetData) {
        HSSFSheet sheet = workbook.createSheet(sheetData.getTitle());
        this.createSheetHeader(sheet, sheetData);

        for(int i = 0; i < sheetData.getDatas().size(); ++i) {
            HSSFRow dataRow = sheet.createRow(i + 1);
            Object data = sheetData.getDatas().get(i);
            if (sheetData.isHasIndexColumn()) {
                HSSFCell cell = dataRow.createCell(0);
                cell.setCellStyle(this.cellStyle);
                cell.setCellValue((double)(i + 1));
            }

            this.createRowData(workbook, dataRow, this.cellStyle, data, sheetData);
        }

        return workbook;
    }

    private HSSFWorkbook rowGroupExport(HSSFWorkbook workbook, ExcelSheetData sheetData) {
        HSSFSheet sheet = workbook.createSheet(sheetData.getTitle());
        this.createSheetHeader(sheet, sheetData);
        String currentGroup = "";
        boolean isOddStyle = true;
        HSSFCellStyle currentStyle = this.oddStyle;

        for(int i = 0; i < sheetData.getDatas().size(); ++i) {
            HSSFRow dataRow = sheet.createRow(i + 1);
            Object data = sheetData.getDatas().get(i);
            String group;
            if(sheetData.getIsMap()) {
                group = this.getDataValueByMap(sheetData.getGroupField(), (Map<String, Object>)data, sheetData);
            } else {
                group = this.getDataValue(sheetData.getGroupField(), data, sheetData);
            }
            if (!currentGroup.equals(group)) {
                currentGroup = group;
                if (isOddStyle) {
                    currentStyle = this.cellStyle;
                    isOddStyle = false;
                } else {
                    currentStyle = this.oddStyle;
                    isOddStyle = true;
                }
            }

            if (sheetData.isHasIndexColumn()) {
                HSSFCell cell = dataRow.createCell(0);
                cell.setCellStyle(currentStyle);
                cell.setCellValue((double)(i + 1));
            }

            this.createRowData(workbook, dataRow, currentStyle, data, sheetData);
        }

        HSSFPalette palette = workbook.getCustomPalette();
        palette.setColorAtIndex((short)50, (byte)-14, (byte)-14, (byte)-14);
        return workbook;
    }

    private HSSFWorkbook rowOddExport(HSSFWorkbook workbook, ExcelSheetData sheetData) {
        HSSFSheet sheet = workbook.createSheet(sheetData.getTitle());
        this.createSheetHeader(sheet, sheetData);
        boolean isOddStyle = true;
        HSSFCellStyle currentStyle = this.oddStyle;

        for(int i = 0; i < sheetData.getDatas().size(); ++i) {
            HSSFRow dataRow = sheet.createRow(i + 1);
            Object data = sheetData.getDatas().get(i);
            if (isOddStyle) {
                currentStyle = this.cellStyle;
                isOddStyle = false;
            } else {
                currentStyle = this.oddStyle;
                isOddStyle = true;
            }

            if (sheetData.isHasIndexColumn()) {
                HSSFCell cell = dataRow.createCell(0);
                cell.setCellStyle(currentStyle);
                cell.setCellValue((double)(i + 1));
            }

            this.createRowData(workbook, dataRow, currentStyle, data, sheetData);
        }

        HSSFPalette palette = workbook.getCustomPalette();
        palette.setColorAtIndex((short)50, (byte)-14, (byte)-14, (byte)-14);
        return workbook;
    }

    private List<String> createSheetHeader(HSSFSheet sheet, ExcelSheetData sheetData) {
        List<String> headerField = new ArrayList();
        HSSFRow row = sheet.createRow(0);
        if (sheetData.isHasIndexColumn()) {
            sheetData.setIndex(1);
            HSSFCell cell = row.createCell(0);
            cell.setCellStyle(this.headerStyle);
            cell.setCellValue("序号");
            sheet.setColumnWidth(0, 1792);
        }

        for(int i = 0; i < sheetData.getHeaders().size(); ++i) {
            int cellNum = i + sheetData.getIndex();
            HSSFCell cell = row.createCell(cellNum);
            cell.setCellStyle(this.headerStyle);
            Map<String, String> head = (Map)sheetData.getHeaders().get(i);
            headerField.add(head.get("field"));
            HSSFRichTextString text = new HSSFRichTextString((String)head.get("title"));
            cell.setCellValue(text);
            sheet.setColumnWidth(cellNum, Integer.valueOf((String)head.get("width")));
        }

        return headerField;
    }

    private void createRowData(HSSFWorkbook workbook, HSSFRow dataRow, HSSFCellStyle currentStyle, Object data, ExcelSheetData sheetData) {
        for(int j = 0; j < sheetData.getHeaders().size(); ++j) {
            Map<String, String> head = (Map)sheetData.getHeaders().get(j);
            HSSFCell cell = dataRow.createCell(j + sheetData.getIndex());
            cell.setCellStyle(currentStyle);
            Class clazz = sheetData.getClazz();
            String value;
            if(sheetData.getIsMap()) {
                value = this.getDataValueByMap(head.get("field"), (Map<String, Object>)data, sheetData);
            } else {
                value = this.getDataValue(head.get("field"), data, sheetData);
            }
            HSSFRichTextString text = new HSSFRichTextString(value);
            cell.setCellValue(text);
        }

    }

    private String getDataValue(String field, Object data, ExcelSheetData sheetData) {
        StringBuilder sb = new StringBuilder();
        sb.append("get");
        sb.append(field.substring(0, 1).toUpperCase());
        sb.append(field.substring(1));

        try {
            Method m = sheetData.getClazz().getMethod(sb.toString());
            Object value = m.invoke(data);
            String vTemp = null;
            if (value instanceof Date) {
                try {
                    vTemp = TimeStampHelper.dateTimeToString((Date)value);
                } catch (Exception var9) {
                    // throw ExceptionHelper.build(ExceptionEnum.DATE_CONVERT_ERROR);
                }

                return vTemp;
            } else if (sheetData.isTrim()) {
                vTemp = value.toString().trim();
                vTemp = vTemp.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
                vTemp = vTemp.replaceAll("(\\r\\n)", "\n");
                return vTemp;
            } else {
                return value.toString();
            }
        } catch (Exception var10) {
            return "";
        }
    }

    private String getDataValueByMap(String field, Map<String, Object> data, ExcelSheetData sheetData) {
        Object value = data.get(field);
        String vTemp = null;
        if (value instanceof Date) {
            try {
                vTemp = TimeStampHelper.dateTimeToString((Date)value);
            } catch (Exception var9) {
                // throw ExceptionHelper.build(ExceptionEnum.DATE_CONVERT_ERROR);
            }
            return vTemp;
        } else if (sheetData.isTrim()) {
            vTemp = value.toString().trim();
            vTemp = vTemp.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
            vTemp = vTemp.replaceAll("(\\r\\n)", "\n");
            return vTemp;
        } else {
            return value.toString();
        }
    }

    private HSSFWorkbook createWorkbook() {
        HSSFWorkbook workbook = new HSSFWorkbook();
//        this.headerFont = workbook.createFont();
//        this.headerFont.setFontHeightInPoints((short)11);
//        this.headerFont.setBoldweight((short)700);
//        this.headerFont.setFontName("微软雅黑");
//        this.headerStyle = workbook.createCellStyle();
//        this.headerStyle.setFillForegroundColor((short)22);
//        this.headerStyle.setFillPattern((short)1);
//        this.headerStyle.setBorderBottom((short)1);
//        this.headerStyle.setBorderLeft((short)1);
//        this.headerStyle.setBorderRight((short)1);
//        this.headerStyle.setBorderTop((short)1);
//        this.headerStyle.setAlignment((short)2);
//        this.headerStyle.setVerticalAlignment((short)1);
//        this.headerStyle.setFont(this.headerFont);
//        HSSFFont font = workbook.createFont();
//        font.setFontHeightInPoints((short)10);
//        font.setFontName("微软雅黑");
//        this.cellStyle = workbook.createCellStyle();
//        this.cellStyle.setBorderBottom((short)1);
//        this.cellStyle.setBorderLeft((short)1);
//        this.cellStyle.setBorderRight((short)1);
//        this.cellStyle.setBorderTop((short)1);
//        this.cellStyle.setAlignment((short)1);
//        this.cellStyle.setVerticalAlignment((short)0);
//        this.cellStyle.setFont(font);
//        this.oddStyle = workbook.createCellStyle();
//        this.oddStyle.setFillForegroundColor((short)50);
//        this.oddStyle.setFillPattern((short)1);
//        this.oddStyle.setBorderBottom((short)1);
//        this.oddStyle.setBorderLeft((short)1);
//        this.oddStyle.setBorderRight((short)1);
//        this.oddStyle.setBorderTop((short)1);
//        this.oddStyle.setAlignment((short)1);
//        this.oddStyle.setVerticalAlignment((short)0);
//        this.oddStyle.setFont(font);
//        if (this.isWrapText) {
//            this.headerStyle.setWrapText(true);
//            this.cellStyle.setWrapText(true);
//            this.oddStyle.setWrapText(true);
//        }

        return workbook;
    }

    public void addSheet(ExcelSheetData sheetData) {
        this.sheetDatas.add(sheetData);
    }

    public String getFileTitle() {
        return this.fileTitle;
    }

    public void setFileTitle(String title) {
        this.fileTitle = title;
    }

    public String getFileName() {
        if (CheckParam.isEmpty(this.fileName)) {
            this.fileName = this.fileTitle + "_" + System.currentTimeMillis() + ".xls";
        }

        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setIsWrapText(boolean isWrapText) {
        this.isWrapText = isWrapText;
    }
}
