package com.example.demo.tereni.service;

import com.example.demo.tereni.model.ReservationReport;
import com.example.demo.tereni.repository.ReservationRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReservationRepository reservationRepository;

    public ReportService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // HELPER: mapira Object[] -> ReservationReport
    private List<ReservationReport> getReservationReportData() {
        List<Object[]> rows = reservationRepository.fetchReservationStats();
        return rows.stream().map(r -> {
            String fieldName = (String) r[0];
            LocalDate ld = (LocalDate) r[1];
            java.util.Date day = (ld == null) ? null : java.sql.Date.valueOf(ld);
            Long count = (r[2] == null) ? 0L : ((Number) r[2]).longValue();
            BigDecimal total = (r[3] == null) ? BigDecimal.ZERO : (BigDecimal) r[3];
            return new ReservationReport(fieldName, day, count, total);
        }).collect(Collectors.toList());
    }

    // GLAVNA: puni Jasper istim stilom kao kod druga
    public JasperPrint generateReservationsReport() throws JRException {
        List<ReservationReport> data = getReservationReportData();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

        // >>> VAŽNO: JRXML mora da odgovara poljima iz ReservationReport <<<
        InputStream reportStream = getClass().getResourceAsStream("/reports/reservations_report.jrxml");
        if (reportStream == null) {
            throw new JRException("JRXML '/reports/reservations_report.jrxml' not found on classpath.");
        }
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        Map<String, Object> params = new HashMap<>();
        params.put("ReportTitle", "Rezervacije - dnevna statistika po terenu");

        return JasperFillManager.fillReport(jasperReport, params, dataSource);
    }
}
