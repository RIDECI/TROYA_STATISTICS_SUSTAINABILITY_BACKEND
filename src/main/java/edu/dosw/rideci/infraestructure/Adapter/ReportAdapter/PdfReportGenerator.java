package edu.dosw.rideci.infraestructure.Adapter.ReportAdapter;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.borders.SolidBorder;
import edu.dosw.rideci.application.port.out.ReportGenerator;
import edu.dosw.rideci.domain.model.Report;
import edu.dosw.rideci.domain.model.UserStats;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class PdfReportGenerator implements ReportGenerator {

    @Override
    public byte[] generate(Report report) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(baos);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc, PageSize.A4)) {

            // margenes
            document.setMargins(50, 50, 50, 50);

            // el header
            addHeader(document, report);

            // info
            addBasicInfo(document, report);

            // estadisticas
            addGeneralStats(document, report);

            //  viajes
            addTripsChart(document, report);

            // pie de pagina
            addFooter(pdfDoc);

        }

        return baos.toByteArray();
    }

    private void addHeader(Document document, Report report) {
        // titulo
        Paragraph title = new Paragraph("REPORTE DE SOSTENIBILIDAD")
                .setFontSize(24)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);
        document.add(title);

        // subtitulo
        Paragraph subtitle = new Paragraph("Tipo: " + report.getCriteria().getPeriod().toString())
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
        document.add(subtitle);

        // una linea
        document.add(new LineSeparator(new SolidLine())
                .setMarginBottom(20));
    }

    private void addBasicInfo(Document document, Report report) {
        // titulo
        document.add(new Paragraph("Información Basica")
                .setFontSize(16)
                .setBold()
                .setMarginBottom(10));

        UserStats stats = report.getUserStats();

        // Tabla de info basica
        Table infoTable = new Table(2);
        infoTable.setWidth(UnitValue.createPercentValue(100));

        addTableRow(infoTable, "Usuario:", stats.getUserId().toString());
        addTableRow(infoTable, "Fecha de Generacion:",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        document.add(infoTable);
        document.add(new Paragraph("\n"));
    }

    private void addGeneralStats(Document document, Report report) {
        // titulo
        document.add(new Paragraph("Estadisticas Generales")
                .setFontSize(16)
                .setBold()
                .setMarginBottom(10));

        UserStats stats = report.getUserStats();

        // Tabla de stats
        Table statsTable = new Table(new float[]{3, 2});
        statsTable.setWidth(UnitValue.createPercentValue(100));

        // encabez
        statsTable.addHeaderCell(createHeaderCell("Métrica"));
        statsTable.addHeaderCell(createHeaderCell("Valor"));

        // Datos
        addStatsRow(statsTable, "Total de Viajes",
                String.valueOf(stats.getTotalTrips()));
        addStatsRow(statsTable, "Monto Total Ganado",
                String.format("$%,.2f", stats.getTotalEarnings()));
        addStatsRow(statsTable, "Monto Total Gastado",
                String.format("$%,.2f", stats.getTotalMoneySpent()));
        addStatsRow(statsTable, "Balance ",
                String.format("$%,.2f", stats.getTotalEarnings() - stats.getTotalMoneySpent()));
        addStatsRow(statsTable, "Distancia Total (km)",
                String.format("%.2f", stats.getTotalDistance()));
        addStatsRow(statsTable, "CO₂ Ahorrado (kg)",
                String.format("%.2f", stats.getTotalCO2Saved()));

        document.add(statsTable);
        document.add(new Paragraph("\n"));
    }

    private void addTripsChart(Document document, Report report) {
        // titulo

        document.add(new Paragraph("Distribución de Viajes")
                .setFontSize(16)
                .setBold()
                .setMarginBottom(10));

        UserStats stats = report.getUserStats();

        // tabla simple
        if (stats.getUserTypeCount() != null && !stats.getUserTypeCount().isEmpty()) {
            Table chartTable = new Table(new float[]{2, 3, 1});
            chartTable.setWidth(UnitValue.createPercentValue(100));

            chartTable.addHeaderCell(createHeaderCell("Tipo de Usuario"));
            chartTable.addHeaderCell(createHeaderCell("Visualización"));
            chartTable.addHeaderCell(createHeaderCell("Cantidad"));

            int maxTrips = stats.getUserTypeCount().values().stream()
                    .mapToInt(Integer::intValue)
                    .max()
                    .orElse(1);

            for (Map.Entry<String, Integer> entry : stats.getUserTypeCount().entrySet()) {
                String userType = entry.getKey();
                Integer count = entry.getValue();

                // Barra visual con caracteres
                int barLength = (int) ((count * 30.0) / maxTrips);
                String bar = "█".repeat(Math.max(1, barLength));

                chartTable.addCell(new Cell().add(new Paragraph(userType)));
                chartTable.addCell(new Cell().add(new Paragraph(bar)
                        .setFontColor(ColorConstants.BLUE)));
                chartTable.addCell(new Cell().add(new Paragraph(String.valueOf(count)))
                        .setTextAlignment(TextAlignment.CENTER));
            }

            document.add(chartTable);
        }

        document.add(new Paragraph("\n"));
    }

    private void addFooter(PdfDocument pdfDoc) {
        int numberOfPages = pdfDoc.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            Rectangle pageSize = pdfDoc.getPage(i).getPageSize();

            // los numeros de pagina
            Canvas canvas = new Canvas(pdfDoc.getPage(i), pageSize);
            canvas.showTextAligned(
                    new Paragraph(String.format("Página %d de %d", i, numberOfPages))
                            .setFontSize(10),
                    pageSize.getWidth() / 2,
                    20,
                    TextAlignment.CENTER
            );
            canvas.close();
        }
    }


    //metodos axu7extra
    private void addTableRow(Table table, String label, String value) {
        table.addCell(new Cell().add(new Paragraph(label).setBold()));
        table.addCell(new Cell().add(new Paragraph(value)));
    }

    private void addStatsRow(Table table, String metric, String value) {
        table.addCell(new Cell().add(new Paragraph(metric)));
        table.addCell(new Cell().add(new Paragraph(value))
                .setTextAlignment(TextAlignment.RIGHT));
    }

    private Cell createHeaderCell(String text) {
        return new Cell().add(new Paragraph(text).setBold())
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER)
                .setPadding(5);
    }
}
