package com.bookstore.boundary.controller.report;

import com.bookstore.domain.report.ReportService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "reports")
class ReportsController {

    private final ReportService reportService;

    @GetMapping
    void printReport() {
        reportService.printReport();
    }

}
