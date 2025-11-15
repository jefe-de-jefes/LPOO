package com.gympos.service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.gympos.model.AccessLog;
import com.gympos.model.ClassSchedule;
import com.gympos.model.Client;
import com.gympos.model.InventoryItem;
import com.gympos.model.Membership;
import com.gympos.model.Payment;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ReportService {
    
    private static final DateTimeFormatter PDF_DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final String REPORT_DIR = "reports/";

    public ReportService() {
        new File(REPORT_DIR).mkdirs();
    }

    private Document createDocument(String title, String fileName) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(REPORT_DIR + fileName));
        document.open();
        document.add(new Paragraph("GYMPOS - " + title));
        document.add(new Paragraph("Generado el: " + LocalDateTime.now().format(PDF_DATE_FMT)));
        document.add(new Paragraph("--------------------------------------------------"));
        document.add(new Paragraph(" "));
        return document;
    }

    public void generatePaymentsReport(List<Payment> payments) {
        try {
            String filename = "Pagos_" + System.currentTimeMillis() + ".pdf";
            Document doc = createDocument("Reporte de Pagos", filename);

            PdfPTable table = new PdfPTable(4);
            table.addCell("ID Pago");
            table.addCell("Cliente");
            table.addCell("Monto");
            table.addCell("Método");

            for (Payment p : payments) {
                table.addCell(p.getId());
                table.addCell(p.getClientId());
                table.addCell(String.format("$%.2f", p.getAmount()));
                table.addCell(p.getMethod());
            }
            doc.add(table);
            doc.close();
        } catch (Exception e) {
            throw new RuntimeException("Error en reporte de pagos: " + e.getMessage());
        }
    }

    public void generateClientsReport(List<Client> clients) {
        try {
            String filename = "Clientes_" + System.currentTimeMillis() + ".pdf";
            Document doc = createDocument("Reporte General de Clientes", filename);

            PdfPTable table = new PdfPTable(4);
            table.addCell("ID");
            table.addCell("Nombre");
            table.addCell("Email");
            table.addCell("Estatus");

            for (Client c : clients) {
                table.addCell(c.getId());
                table.addCell(c.getFullName());
                table.addCell(c.getEmail());
                table.addCell(c.getStatusString()); // Activo / Inactivo
            }
            doc.add(table);
            doc.close();
        } catch (Exception e) {
            throw new RuntimeException("Error en reporte de clientes: " + e.getMessage());
        }
    }

    public void generateMembershipsReport(List<Membership> memberships) {
        try {
            String filename = "Membresias_" + System.currentTimeMillis() + ".pdf";
            Document doc = createDocument("Reporte de Membresías", filename);

            PdfPTable table = new PdfPTable(4);
            table.addCell("Cliente");
            table.addCell("Tipo");
            table.addCell("Inicio");
            table.addCell("Fin");

            for (Membership m : memberships) {
                table.addCell(m.getClientId());
                table.addCell(m.getType().toString());
                table.addCell(m.getStart().toString());
                table.addCell(m.getEnd().toString());
            }
            doc.add(table);
            doc.close();
        } catch (Exception e) {
            throw new RuntimeException("Error en reporte de membresías: " + e.getMessage());
        }
    }

    public void generateAccessReport(List<AccessLog> logs) {
        try {
            String filename = "Accesos_" + System.currentTimeMillis() + ".pdf";
            Document doc = createDocument("Historial de Accesos", filename);

            PdfPTable table = new PdfPTable(4);
            table.addCell("Cliente");
            table.addCell("Acción");
            table.addCell("Clase/Área");
            table.addCell("Fecha/Hora");

            for (AccessLog log : logs) {
                table.addCell(log.getClientId());
                table.addCell(log.getAction());
                String area = (log.getClassScheduleId() == null) ? "Gimnasio General" : log.getClassScheduleId();
                table.addCell(area);
                table.addCell(log.getTimestamp().format(PDF_DATE_FMT));
            }
            doc.add(table);
            doc.close();
        } catch (Exception e) {
            throw new RuntimeException("Error en reporte de accesos: " + e.getMessage());
        }
    }

    public void generateInventoryReport(List<InventoryItem> items) {
        try {
            String filename = "Inventario_" + System.currentTimeMillis() + ".pdf";
            Document doc = createDocument("Reporte de Inventario", filename);

            PdfPTable table = new PdfPTable(3);
            table.addCell("Producto");
            table.addCell("Cantidad");
            table.addCell("Ubicación");

            for (InventoryItem item : items) {
                table.addCell(item.getName());
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(item.getLocation());
            }
            doc.add(table);
            doc.close();
        } catch (Exception e) {
            throw new RuntimeException("Error en reporte de inventario: " + e.getMessage());
        }
    }

    public void generateScheduleReport(List<ClassSchedule> classes) {
        try {
            String filename = "Clases_" + System.currentTimeMillis() + ".pdf";
            Document doc = createDocument("Calendario de Clases", filename);

            PdfPTable table = new PdfPTable(4);
            table.addCell("Clase");
            table.addCell("Día");
            table.addCell("Horario");
            table.addCell("Cupo");

            for (ClassSchedule s : classes) {
                table.addCell(s.getClassName());
                table.addCell(s.getDay().toString());
                table.addCell(s.getTimeRange());
                table.addCell(String.valueOf(s.getCapacity()));
            }
            doc.add(table);
            doc.close();
        } catch (Exception e) {
            throw new RuntimeException("Error en reporte de clases: " + e.getMessage());
        }
    }

    public void generateBackupsReport(List<String> backupNames) {
        try {
            String filename = "Respaldos_" + System.currentTimeMillis() + ".pdf";
            Document doc = createDocument("Historial de Respaldos", filename);

            PdfPTable table = new PdfPTable(1);
            table.addCell("Carpeta / Fecha del Respaldo");

            if (backupNames.isEmpty()) {
                table.addCell("No se encontraron respaldos.");
            } else {
                for (String name : backupNames) {
                    table.addCell(name);
                }
            }
            doc.add(table);
            doc.close();
        } catch (Exception e) {
            throw new RuntimeException("Error en reporte de respaldos: " + e.getMessage());
        }
    }
}