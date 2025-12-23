package id.ac.tazkia.minibank.dto;

public class ProductOptionDto {
    private Long id;
    private String label;
    private String suffix2Digit;

    public ProductOptionDto(Long id, String label, String suffix2Digit) {
        this.id = id;
        this.label = label;
        this.suffix2Digit = suffix2Digit;
    }

    public Long getId() { return id; }
    public String getLabel() { return label; }
    public String getSuffix2Digit() { return suffix2Digit; }
}
