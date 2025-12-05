package edu.dosw.rideci.infraestructure.Adapter.ReportAdapter;

import edu.dosw.rideci.application.port.out.ReportGenerator;
import edu.dosw.rideci.domain.model.EmissionRecord;
import edu.dosw.rideci.domain.model.Report;
import edu.dosw.rideci.domain.model.UserStats;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
public class ExcelReportGenerator implements ReportGenerator {

    @Override
    public byte[] generate(Report report) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();

        try {
            // Hoja 1: Información General y Estadísticas
            createStatsSheet(workbook, report);

            // Hoja 2: el pie  de Tipos de Usuario
            createUserTypeChartSheet(workbook, report);

            // Hoja 3: Registros de Emisiones
            createEmissionRecordsSheet(workbook, report);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            workbook.close();

            return out.toByteArray();
        } catch (Exception e) {
            workbook.close();
            throw e;
        }
    }

    //  HOJA 1:
    private void createStatsSheet(XSSFWorkbook workbook, Report report) {
        XSSFSheet sheet = workbook.createSheet("Estadísticas");
        UserStats stats = report.getUserStats();

        CellStyle titleStyle = estiloTitulo(workbook);
        CellStyle headerStyle = estiloEncabezado(workbook);
        CellStyle cellStyle = estiloCeldas(workbook);
        CellStyle numericStyle = estiloNumerico(workbook);

        int rowIndex = 0;

        // titulo
        Row titleRow = sheet.createRow(rowIndex++);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("REPORTE DE SOSTENIBILIDAD");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
        rowIndex++; // Espacio

        // info
        Row infoHeader = sheet.createRow(rowIndex++);
        Cell infoCell = infoHeader.createCell(0);
        infoCell.setCellValue("Informacion Basica");
        infoCell.setCellStyle(headerStyle);

        addDataRow(sheet, rowIndex++, "Usuario ID:", String.valueOf(stats.getUserId()), cellStyle, cellStyle);
        addDataRow(sheet, rowIndex++, "Período:", report.getCriteria().getPeriod().toString(), cellStyle, cellStyle);
        addDataRow(sheet, rowIndex++, "Fecha de Generación:",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), cellStyle, cellStyle);
        rowIndex++;

        // estadisticas generales
        Row statsHeader = sheet.createRow(rowIndex++);
        Cell statsCell = statsHeader.createCell(0);
        statsCell.setCellValue("Estadisticas Generales");
        statsCell.setCellStyle(headerStyle);

        addDataRow(sheet, rowIndex++, "Total de Viajes", String.valueOf(stats.getTotalTrips()), cellStyle, numericStyle);
        addDataRow(sheet, rowIndex++, "Monto Total Ganado", String.format("$%,.2f", stats.getTotalEarnings()), cellStyle, numericStyle);
        addDataRow(sheet, rowIndex++, "Monto Total Gastado", String.format("$%,.2f", stats.getTotalMoneySpent()), cellStyle, numericStyle);
        addDataRow(sheet, rowIndex++, "Balance ",
                String.format("$%,.2f", stats.getTotalEarnings() - stats.getTotalMoneySpent()), cellStyle, numericStyle);
        addDataRow(sheet, rowIndex++, "Distancia Total (km)", String.format("%.2f", stats.getTotalDistance()), cellStyle, numericStyle);
        addDataRow(sheet, rowIndex++, "CO₂ Ahorrado (kg)", String.format("%.2f", stats.getTotalCO2Saved()), cellStyle, numericStyle);
        rowIndex++;

        // destinos top
        if (stats.getFrequentDestinations() != null && !stats.getFrequentDestinations().isEmpty()) {
            Row destHeader = sheet.createRow(rowIndex++);
            Cell destCell = destHeader.createCell(0);
            destCell.setCellValue("Destinos Frecuentes");
            destCell.setCellStyle(headerStyle);

            Row destTableHeader = sheet.createRow(rowIndex++);
            Cell destHeaderCell1 = destTableHeader.createCell(0);
            destHeaderCell1.setCellValue("Destino");
            destHeaderCell1.setCellStyle(headerStyle);
            Cell destHeaderCell2 = destTableHeader.createCell(1);
            destHeaderCell2.setCellValue("Cantidad");
            destHeaderCell2.setCellStyle(headerStyle);

            for (Map.Entry<String, Integer> entry : stats.getFrequentDestinations().entrySet()) {
                addDataRow(sheet, rowIndex++, entry.getKey(), String.valueOf(entry.getValue()), cellStyle, numericStyle);
            }
        }

        //  anchos
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.setColumnWidth(0, 8000);
        sheet.setColumnWidth(1, 6000);
    }

    // HOJA 2: el pie
    private void createUserTypeChartSheet(XSSFWorkbook workbook, Report report) {
        XSSFSheet sheet = workbook.createSheet("Gráfica Tipos Usuario");
        UserStats stats = report.getUserStats();

        if (stats.getUserTypeCount() == null || stats.getUserTypeCount().isEmpty()) {
            sheet.createRow(0).createCell(0).setCellValue("No hay datos de tipos de usuario");
            return;
        }

        CellStyle headerStyle = estiloEncabezado(workbook);

        // tabla para crear el grafico
        Row header = sheet.createRow(0);
        Cell headerCell1 = header.createCell(0);
        headerCell1.setCellValue("Tipo de Usuario");
        headerCell1.setCellStyle(headerStyle);
        Cell headerCell2 = header.createCell(1);
        headerCell2.setCellValue("Cantidad");
        headerCell2.setCellStyle(headerStyle);

        int rowIndex = 1;
        for (Map.Entry<String, Integer> entry : stats.getUserTypeCount().entrySet()) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        // crear el pastel
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 3, 1, 13, 20);

        XSSFChart chart = drawing.createChart(anchor);
        chart.setTitleText(" Viajes por Tipo de Usuario");
        chart.getOrAddLegend().setPosition(LegendPosition.RIGHT);

        XDDFDataSource<String> categorias = XDDFDataSourcesFactory.fromStringCellRange(
                sheet, new CellRangeAddress(1, rowIndex - 1, 0, 0)
        );

        XDDFNumericalDataSource<Double> valores = XDDFDataSourcesFactory.fromNumericCellRange(
                sheet, new CellRangeAddress(1, rowIndex - 1, 1, 1)
        );

        XDDFChartData data = chart.createData(ChartTypes.PIE, null, null);
        XDDFChartData.Series series = data.addSeries(categorias, valores);
        series.setTitle("Tipos", null);
        chart.plot(data);
    }

    //  HOJA 3
    private void createEmissionRecordsSheet(XSSFWorkbook workbook, Report report) {
        XSSFSheet sheet = workbook.createSheet("Registros de Emisiones");

        CellStyle headerStyle = estiloEncabezado(workbook);
        CellStyle cellStyle = estiloCeldas(workbook);
        CellStyle numericStyle = estiloNumerico(workbook);

        // headers
        String[] columnas = {"Fecha", "CO₂ Ahorrado (kg)", "Rol del Usuario"};
        Row header = sheet.createRow(0);
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
        }

        // Datos
        List<EmissionRecord> records = report.getRecords();
        if (records != null && !records.isEmpty()) {
            int rowIndex = 1;
            for (EmissionRecord record : records) {
                Row row = sheet.createRow(rowIndex++);

                Cell c1 = row.createCell(0);
                c1.setCellValue(record.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                c1.setCellStyle(cellStyle);

                Cell c2 = row.createCell(1);
                c2.setCellValue(record.getTotalCO2Saved());
                c2.setCellStyle(numericStyle);

                Cell c3 = row.createCell(2);
                c3.setCellValue(record.getUserRol() != null ? record.getUserRol().toString() : "N/A");
                c3.setCellStyle(cellStyle);
            }
        } else {
            sheet.createRow(1).createCell(0).setCellValue("No hay registros de emisiones para este período");
        }

        //  anchos
        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    // estilos
    private CellStyle estiloTitulo(XSSFWorkbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 18);
        font.setColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private CellStyle estiloEncabezado(XSSFWorkbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = wb.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true);
        style.setFont(font);

        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;
    }

    private CellStyle estiloCeldas(XSSFWorkbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private CellStyle estiloNumerico(XSSFWorkbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    // metodos aux
    private void addDataRow(Sheet sheet, int rowIndex, String label, String value,
                            CellStyle labelStyle, CellStyle valueStyle) {
        Row row = sheet.createRow(rowIndex);

        Cell labelCell = row.createCell(0);
        labelCell.setCellValue(label);
        labelCell.setCellStyle(labelStyle);

        Cell valueCell = row.createCell(1);
        valueCell.setCellValue(value);
        valueCell.setCellStyle(valueStyle);
    }
}
