package com.yondu.university.project_rohan.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yondu.university.project_rohan.service.PDFService;

@RestController
public class PDFController {
    private final PDFService pdfService;

    /**
     * @param pdfService
     */
    public PDFController(PDFService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping(path = "student/courses/{code}/classes/{batch}/generate-certificate", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasRole('STUDENT')")
    public InputStreamResource getCourseCertificate(
            @CurrentSecurityContext(expression = "authentication.getName()") String currentUser,
            @PathVariable String code, @PathVariable int batch) {
        return this.pdfService.getCertificate(currentUser, code, batch);
    }
}
