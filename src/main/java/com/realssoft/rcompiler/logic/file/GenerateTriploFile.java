package com.realssoft.rcompiler.logic.file;

import com.realssoft.Main;
import com.realssoft.rcompiler.logic.model.TriploModel;
import com.realssoft.rcompiler.ui.support.notification.Notifications;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class GenerateTriploFile
{

    private static final String workbookName = "Triplo.xlsx";
    private static final String SHEET_NAME = "Triplo";
    private static final String[] HEADERS = {"Dato Objeto", "Dato Fuente", "Operador"};

    private Workbook workbook;
    private Sheet sheet;

    public CellStyle loadStyle(int key)
    {
        CellStyle style = workbook.createCellStyle();

        // Configurar el degradado del fondo
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Configurar la alineación del contenido
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        // Configurar el borde
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.WHITE.getIndex());

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);


        if (key % 2 == 0)
        {
            style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            font.setColor(IndexedColors.BLACK.getIndex());
        }
        else {
            style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            font.setColor(IndexedColors.BLACK.getIndex());
        }
        style.setFont(font);
        return style;
    }

    public void loadData()
    {
        int rowIndex = 1;
        Row row = sheet.createRow(rowIndex);

        CellStyle style = workbook.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
        style.setFillBackgroundColor(IndexedColors.BLUE1.getIndex());
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.WHITE.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.WHITE.getIndex());

        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);

        Cell  cellOne = row.createCell(1);
        cellOne.setCellStyle(style);
        sheet.setColumnWidth(1, 8 * 256);

        for (int i = 0; i < HEADERS.length; i++)
        {
            row.setHeightInPoints(20);
            Cell  cell = row.createCell(i + 2);
            cell.setCellValue(HEADERS[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i + 2, 18 * 256);
        }

        rowIndex++;
        for (Map.Entry<Integer, TriploModel> entry : Main.TRIPLO_MAP.entrySet())
        {
            TriploModel triploModel = entry.getValue();
            int id = entry.getKey();

            row.setHeightInPoints(20);
            row = sheet.createRow(rowIndex);
            Cell key = row.createCell(1);
            key.setCellValue(entry.getKey());
            key.setCellStyle(loadStyle(id));

            Cell objectData = row.createCell(2);
            objectData.setCellValue(triploModel.getObjectData());
            objectData.setCellStyle(loadStyle(id));

            Cell sourceData = row.createCell(3);
            sourceData.setCellStyle(loadStyle(id));
            if (triploModel.getSourceData().matches("\\d+"))
            {
                sourceData.setCellValue(Integer.parseInt(triploModel.getSourceData()));
            }
            else
            {
                sourceData.setCellValue(triploModel.getSourceData());
            }

            Cell operator = row.createCell(4);
            operator.setCellStyle(loadStyle(id));
            if (triploModel.getOperator().matches("\\d+"))
            {
                operator.setCellValue(Integer.parseInt(triploModel.getOperator()));
            }
            else
            {
                operator.setCellValue(triploModel.getOperator());
            }
            rowIndex++;
        }
    }

    public void save()
    {
        Notifications.getInstance().show(
                Notifications.Type.WARNING,
                Notifications.Location.BOTTOM_RIGHT,
                "Procesando archivo."
        );
        CompletableFuture.runAsync(() -> {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet(SHEET_NAME);
            loadData();
            File file = new File(workbookName);
            String filePath = file.getAbsolutePath();
            try(FileOutputStream fileOutputStream = new FileOutputStream(filePath))
            {
                workbook.write(fileOutputStream);
                workbook.close();
                Notifications.getInstance().show(
                        Notifications.Type.SUCCESS,
                        Notifications.Location.BOTTOM_RIGHT,
                        "Se guardo el archivo " + workbookName + " correctamente"
                );
            } catch (IOException e)
            {
                Notifications.getInstance().show(
                        Notifications.Type.ERROR,
                        Notifications.Location.BOTTOM_RIGHT,
                        "Error al guardar el archivo."
                );
                e.printStackTrace();
            }
        });

    }

}
