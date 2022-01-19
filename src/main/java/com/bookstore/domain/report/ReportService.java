package com.bookstore.domain.report;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public void printReport() {
        List<ReportProjection> reportData = reportRepository.buildReport();
        reportData.forEach(System.out::println);
    }

}
