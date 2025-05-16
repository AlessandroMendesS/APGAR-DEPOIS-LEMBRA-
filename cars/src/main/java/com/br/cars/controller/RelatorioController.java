package com.br.cars.controller;

import com.br.cars.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {
    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/vendas/pdf")
    public ResponseEntity<byte[]> gerarRelatorioVendasPDF(
            @RequestParam String inicio,
            @RequestParam String fim) throws Exception {

        LocalDateTime dataInicio = LocalDateTime.parse(inicio);
        LocalDateTime dataFim = LocalDateTime.parse(fim);

        byte[] relatorio = relatorioService.gerarRelatorioVendasPDF(dataInicio, dataFim);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio_vendas.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(relatorio);
    }

    @GetMapping("/vendas/excel")
    public ResponseEntity<byte[]> gerarRelatorioVendasExcel(
            @RequestParam String inicio,
            @RequestParam String fim) throws Exception {

        LocalDateTime dataInicio = LocalDateTime.parse(inicio);
        LocalDateTime dataFim = LocalDateTime.parse(fim);

        byte[] relatorio = relatorioService.gerarRelatorioVendasExcel(dataInicio, dataFim);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio_vendas.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(relatorio);
    }
}