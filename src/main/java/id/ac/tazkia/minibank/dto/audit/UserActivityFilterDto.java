package id.ac.tazkia.minibank.dto.audit;

import id.ac.tazkia.minibank.entity.ActivityType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UserActivityFilterDto {
    private String username;
    private String module;
    private ActivityType activityType;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    private String q;
}

