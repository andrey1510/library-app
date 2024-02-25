package com.libraryapp.controllers;

import com.libraryapp.dto.BookDTO;
import com.libraryapp.dto.ClientDTO;
import com.libraryapp.exceptions.NoBooksInLibraryException;
import com.libraryapp.models.Book;
import com.libraryapp.models.Client;
import com.libraryapp.services.LendingService;
import com.libraryapp.services.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/registration/")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final LendingService lendingService;

    @PostMapping("register_book")
    @Operation(description = "Зарегистрировать книгу в библиотеке.")
    public ResponseEntity<Book> createBook(@RequestBody BookDTO bookDTO) {
        registrationService.createBook(bookDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("register_client")
    @Operation(description = "Зарегистрировать клиента в библиотеке.")
    public ResponseEntity<Client> createClient(@RequestBody ClientDTO clientDTO) {
        registrationService.createClient(clientDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("books")
    @Operation(description = "Выдать pdf со всеми книгами.")
    public ResponseEntity<byte[]> getBook() {

        List<BookDTO> books = lendingService.getAllBooks();

        String sourceFileName = "C:\\Users\\WA\\Documents\\GitHub\\library-app\\src\\main\\resources\\report.jrxml";
        try {

            // Компиляция шаблона JRXML файла
            JasperCompileManager.compileReportToFile(sourceFileName);

            // Заполнение отчета данными
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    "C:\\Users\\WA\\Documents\\GitHub\\library-app\\src\\main\\resources\\BookReport.jasper",
                    new HashMap<>(), new JRBeanCollectionDataSource(books));

            // Экспорт отчета в PDF
            byte[] reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);

            // Возвращение отчета в виде HTTP-ответа
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "books-report.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(reportBytes);

        } catch (JRException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
