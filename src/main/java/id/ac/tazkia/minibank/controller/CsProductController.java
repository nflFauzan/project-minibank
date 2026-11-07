package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.dto.ProductFormDto;
import id.ac.tazkia.minibank.entity.ProdukTabungan;
import id.ac.tazkia.minibank.repository.ProdukTabunganRepository;
import id.ac.tazkia.minibank.service.CsProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cs/product")
public class CsProductController {

    private final ProdukTabunganRepository produkTabunganRepository;
    private final CsProductService csProductService;

    @GetMapping({"", "/list"})
    public String list(@RequestParam(required = false) String q,
                       @RequestParam(required = false) String search,
                       @RequestParam(required = false) String productType,
                       @RequestParam(required = false) String category,
                       @RequestParam(defaultValue = "ALL") String status,
                       @RequestParam(defaultValue = "productName") String sortBy,
                       @RequestParam(defaultValue = "asc") String sortDir,
                       @RequestParam(defaultValue = "0") int page,
                       Model model) {

        String searchQuery = (search != null && !search.isBlank()) ? search : q;

        Boolean aktif = switch (status == null ? "ALL" : status.toUpperCase()) {
            case "ACTIVE", "AKTIF" -> Boolean.TRUE;
            case "INACTIVE", "NONAKTIF", "NONAKTIVE", "NONAKTIFKAN" -> Boolean.FALSE;
            default -> null; // ALL
        };

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, 10, sort);
        Page<ProdukTabungan> result = csProductService.search(searchQuery, productType, category, aktif, pageable);

        model.addAttribute("active", "product");
        model.addAttribute("page", result);
        model.addAttribute("q", searchQuery);
        model.addAttribute("search", searchQuery);
        model.addAttribute("productType", productType);
        model.addAttribute("category", category);
        model.addAttribute("status", (status == null ? "ALL" : status.toUpperCase()));
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);

        model.addAttribute("productTypeOptions", csProductService.productTypeOptions());
        model.addAttribute("categoryOptions", csProductService.categoryOptions());

        return "cs/product/list";
    }

    @GetMapping({"/new", "/create"})
    public String createForm(Model model) {
        ProductFormDto form = new ProductFormDto();
        form.setCurrency("IDR");
        form.setIsActive(true);
        form.setIsDefault(false);
        form.setAllowOverdraft(false);
        form.setRequireMaintainingBalance(true);
        form.setIsShariahCompliant(true);
        form.setProfitDistributionFrequency("MONTHLY");
        form.setAllowedCustomerTypes("PERSONAL,CORPORATE");

        model.addAttribute("active", "product");
        model.addAttribute("mode", "CREATE");
        model.addAttribute("form", form);
        model.addAttribute("actionUrl", "/cs/product/new");
        model.addAttribute("submitLabel", "Create Product");

        populateOptions(model);
        return "cs/product/form";
    }

    @PostMapping({"/new", "/create"})
    public String createProcess(@ModelAttribute("form") ProductFormDto form,
                                RedirectAttributes ra) {
        try {
            csProductService.create(form);
            ra.addFlashAttribute("success", "Produk berhasil dibuat.");
            return "redirect:/cs/product";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/cs/product/new";
        }
    }

    @GetMapping({"/view/{id}", "/{id}"})
    public String detail(@PathVariable String id, Model model) {
        ProdukTabungan p = csProductService.findByIdOrUuid(id);
        model.addAttribute("active", "product");
        model.addAttribute("p", p);
        return "cs/product/detail";
    }

    @GetMapping({"/edit/{id}", "/{id}/edit"})
    public String editForm(@PathVariable String id,
                           Model model,
                           RedirectAttributes ra) {
        try {
            ProdukTabungan p = csProductService.findByIdOrUuid(id);

            ProductFormDto form = new ProductFormDto();
            form.setId(p.getUuid() != null ? p.getUuid() : p.getId().toString());
            form.setProductCode(p.getKodeProduk());
            form.setKodeProduk(p.getKodeProduk());
            form.setProductName(p.getNamaProduk());
            form.setNamaProduk(p.getNamaProduk());
            form.setProductType(p.getProductType());
            form.setProductCategory(p.getProductCategory());
            form.setCurrency(p.getCurrency() != null ? p.getCurrency() : "IDR");
            form.setDescription(p.getDeskripsiSingkat());
            form.setDeskripsiSingkat(p.getDeskripsiSingkat());
            form.setJenisAkad(p.getJenisAkad());

            form.setIsActive(p.getAktif());
            form.setAktif(p.getAktif());
            form.setIsDefault(p.getIsDefault());
            form.setAllowOverdraft(p.getAllowOverdraft());
            form.setRequireMaintainingBalance(p.getRequireMaintainingBalance());

            form.setMinimumOpeningBalance(p.getSetoranAwalMinimum());
            form.setSetoranAwalMinimum(p.getSetoranAwalMinimum());
            form.setMinimumBalance(p.getMinimumBalance());
            form.setMaximumBalance(p.getMaximumBalance());

            form.setProfitSharingRatio(p.getProfitSharingRatio());
            form.setProfitSharingType(p.getProfitSharingType());
            form.setProfitDistributionFrequency(p.getProfitDistributionFrequency());
            form.setNisbahCustomer(p.getNisbahCustomer());
            form.setNisbahBank(p.getNisbahBank());
            form.setIsShariahCompliant(p.getIsShariahCompliant());
            form.setShariahBoardApprovalNumber(p.getShariahBoardApprovalNumber());
            form.setShariahBoardApprovalDate(p.getShariahBoardApprovalDate());

            form.setDailyWithdrawalLimit(p.getDailyWithdrawalLimit());
            form.setMonthlyTransactionLimit(p.getMonthlyTransactionLimit());
            form.setOverdraftLimit(p.getOverdraftLimit());
            form.setMonthlyMaintenanceFee(p.getMonthlyMaintenanceFee());
            form.setAtmWithdrawalFee(p.getAtmWithdrawalFee());
            form.setInterBankTransferFee(p.getInterBankTransferFee());

            form.setFreeTransactionsPerMonth(p.getFreeTransactionsPerMonth());
            form.setExcessTransactionFee(p.getExcessTransactionFee());
            form.setBelowMinimumBalanceFee(p.getBelowMinimumBalanceFee());

            form.setMinCustomerAge(p.getMinCustomerAge());
            form.setMaxCustomerAge(p.getMaxCustomerAge());
            form.setAllowedCustomerTypes(p.getAllowedCustomerTypes());
            form.setRequiredDocuments(p.getRequiredDocuments());

            form.setLaunchDate(p.getLaunchDate());
            form.setRetirementDate(p.getRetirementDate());

            String actionId = (p.getUuid() != null) ? p.getUuid() : p.getId().toString();
            String actionUrl = "/cs/product/" + actionId + "/edit";

            model.addAttribute("active", "product");
            model.addAttribute("mode", "EDIT");
            model.addAttribute("form", form);
            model.addAttribute("p", p);
            model.addAttribute("actionUrl", actionUrl);
            model.addAttribute("submitLabel", "Update Product");

            populateOptions(model);
            return "cs/product/form";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/cs/product";
        }
    }

    @PostMapping({"/edit/{id}", "/{id}/edit"})
    public String editProcess(@PathVariable String id,
                              @ModelAttribute("form") ProductFormDto form,
                              RedirectAttributes ra) {
        try {
            ProdukTabungan updated = csProductService.update(id, form);
            ra.addFlashAttribute("success", "Produk berhasil diupdate.");
            String viewId = (updated.getUuid() != null) ? updated.getUuid() : updated.getId().toString();
            return "redirect:/cs/product/" + viewId;
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/cs/product/" + id + "/edit";
        }
    }

    @PostMapping({"/delete/{id}", "/{id}/toggle", "/{id}/delete", "/{id}/deactivate", "/{id}/activate"})
    public String toggleOrDelete(@PathVariable String id,
                                 @RequestParam(required = false) String q,
                                 @RequestParam(required = false) String search,
                                 @RequestParam(required = false) String productType,
                                 @RequestParam(required = false) String category,
                                 @RequestParam(required = false, defaultValue = "ALL") String status,
                                 @RequestParam(required = false, defaultValue = "0") int page,
                                 RedirectAttributes ra) {

        try {
            csProductService.toggleAktif(id);
            ra.addFlashAttribute("success", "Status produk berhasil diubah.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }

        String queryParam = (search != null && !search.isBlank()) ? search : (q != null ? q : "");
        return "redirect:/cs/product?search=" + queryParam + "&productType=" + (productType == null ? "" : productType)
                + "&category=" + (category == null ? "" : category) + "&status=" + status + "&page=" + page;
    }

    private void populateOptions(Model model) {
        model.addAttribute("akadOptions", csProductService.akadOptions());
        model.addAttribute("productTypeOptions", csProductService.productTypeOptions());
        model.addAttribute("categoryOptions", csProductService.categoryOptions());
        model.addAttribute("profitSharingTypeOptions", csProductService.profitSharingTypeOptions());
        model.addAttribute("profitDistributionFrequencyOptions", csProductService.profitDistributionFrequencyOptions());
    }
}
