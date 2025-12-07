package id.ac.tazkia.minibank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cs")
public class CsPendaftaranController {

    @GetMapping("/pendaftaran")
    public String pendaftaranForm() {
        return "cs/pendaftaran_nasabah";
    }
}
