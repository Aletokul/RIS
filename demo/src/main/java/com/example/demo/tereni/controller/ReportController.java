package com.example.demo.tereni.controller;

import com.example.demo.tereni.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reservations/pdf")
    public ResponseEntity<byte[]> reservationsPdf() throws JRException {
        JasperPrint jasperPrint = reportService.generateReservationsReport();
        byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename("reservations-report.pdf").build());
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}
