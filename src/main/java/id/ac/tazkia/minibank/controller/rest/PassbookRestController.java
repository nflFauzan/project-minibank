package id.ac.tazkia.minibank.controller.rest;

import id.ac.tazkia.minibank.dto.passbook.*;
import id.ac.tazkia.minibank.service.PassbookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/passbook")
public class PassbookRestController {

    private static final Logger log = LoggerFactory.getLogger(PassbookRestController.class);

    private final PassbookService passbookService;

    public PassbookRestController(PassbookService passbookService) {
        this.passbookService = passbookService;
    }

    @GetMapping("/{accountId}/status")
    public ResponseEntity<PassbookStatusResponse> getStatus(@PathVariable String accountId) {
        try {
            PassbookStatusResponse status = passbookService.getStatus(accountId);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            log.error("Failed to get passbook status for {}: {}", accountId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{accountId}/print-data")
    public ResponseEntity<?> getPrintData(@PathVariable String accountId) {
        try {
            PassbookPrintDataResponse data = passbookService.getPrintData(accountId);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            log.error("Failed to get print data for {}: {}", accountId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage() != null ? e.getMessage() : "Failed to fetch print data"));
        }
    }

    @PostMapping("/{accountId}/next-page")
    public ResponseEntity<NextPageResponse> advanceNextPage(@PathVariable String accountId) {
        NextPageResponse response = passbookService.advanceToNextPage(accountId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/print-result")
    public ResponseEntity<Map<String, Object>> reportPrintResult(@RequestBody(required = false) PrintResultRequest request) {
        try {
            if (request == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("success", false, "error", "Request payload is required"));
            }

            if (request.getPrintedTransactionIds() == null || request.getPrintedTransactionIds().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("success", false, "error", "No valid transaction IDs provided"));
            }

            boolean ok = passbookService.reportPrintResult(request);
            if (ok) {
                return ResponseEntity.ok(Map.of("success", true, "message", "Print result reported successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("success", false, "error", "Failed to record print result"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing print result: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "error", "Internal error reporting print result"));
        }
    }
}
