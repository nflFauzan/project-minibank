package id.ac.tazkia.minibank.dto.passbook;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NextPageResponse {
    private boolean success;
    private Integer currentPage;
    private String error;

    public NextPageResponse() {}

    public NextPageResponse(boolean success, Integer currentPage, String error) {
        this.success = success;
        this.currentPage = currentPage;
        this.error = error;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public Integer getCurrentPage() { return currentPage; }
    public void setCurrentPage(Integer currentPage) { this.currentPage = currentPage; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}
