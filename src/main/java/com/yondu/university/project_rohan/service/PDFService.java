package com.yondu.university.project_rohan.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.yondu.university.project_rohan.entity.CourseClass;
import com.yondu.university.project_rohan.entity.User;
import com.yondu.university.project_rohan.exception.ResourceNotFoundException;

@Service
public class PDFService {
    UserService userService;
    CourseClassService classService;

    /**
     * @param userService
     * @param classService
     */
    public PDFService(UserService userService, CourseClassService classService) {
        this.userService = userService;
        this.classService = classService;
    }

    public InputStreamResource getCertificate(String studentEmail, String courseCode, Integer batch) {
        if (!this.classService.isStudentEnrolledInClass(studentEmail, courseCode, batch)) {
            throw new ResourceNotFoundException("Student not enrolled in the class.");
        }

        User student = this.userService.findStudentByCourseClassAndEmail(courseCode, batch, studentEmail);
        CourseClass courseClass = this.classService.findByCourseAndBatchAndClosed(courseCode, batch);

        // TODO: add student grades validation before generating pdf

        String pdfFilename = String.format("%s%d-%s%s.pdf", courseCode, batch,
                student.getLastName(), student.getFirstName());

        InputStream in;

        try {
            Document document = new Document(PageSize.A4.rotate(), 10, 10, 100, 10);
            PdfWriter.getInstance(document, new FileOutputStream(pdfFilename));

            document.open();
            Font font = FontFactory.getFont(FontFactory.TIMES_ITALIC, 32, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph("This certification is given to", font);
            paragraph.setAlignment(Element.ALIGN_CENTER);

            document.add(paragraph);

            font = FontFactory.getFont(FontFactory.TIMES_BOLD, 48, BaseColor.BLACK);
            paragraph = new Paragraph(String.format("%s %s", student.getFirstName(), student.getLastName()), font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingBefore(20);

            document.add(paragraph);

            font = FontFactory.getFont(FontFactory.TIMES_ITALIC, 32, BaseColor.BLACK);
            paragraph = new Paragraph("for passing the course ", font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingBefore(20);

            document.add(paragraph);

            font = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 32, BaseColor.BLACK);
            paragraph = new Paragraph(
                    String.format("%s with batch number %d", courseClass.getCourse().getTitle(), batch), font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingBefore(20);

            document.add(paragraph);

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd LLL yyyy");
            font = FontFactory.getFont(FontFactory.TIMES_ITALIC, 32, BaseColor.BLACK);
            paragraph = new Paragraph(
                    String.format("conducted from %s to %s", courseClass.getStartDate().format(dateTimeFormatter),
                            courseClass.getEndDate().format(dateTimeFormatter)),
                    font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingBefore(20);

            document.add(paragraph);

            document.close();

            in = new FileInputStream(new File(pdfFilename));
        } catch (Exception e) {
            throw new ResourceNotFoundException("Certificate unavailable");
        }

        return new InputStreamResource(in);
    }
}
