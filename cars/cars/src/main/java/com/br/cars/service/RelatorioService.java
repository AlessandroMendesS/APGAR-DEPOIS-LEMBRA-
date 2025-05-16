package com.br.cars.service;

import com.br.cars.model.Venda;
import com.br.cars.repository.VendaRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RelatorioService {
    @Autowired
    private VendaRepository vendaRepository;

    // Método para gerar relatório em PDF
    public byte[] gerarRelatorioVendasPDF(LocalDateTime inicio, LocalDateTime fim) throws JRException, IOException {
        List<Venda> vendas = vendaRepository.findByPeriodo(inicio, fim);

        // Carrega o template do relatório Jasper
        InputStream jasperStream = this.getClass().getResourceAsStream("/reports/vendas.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperStream);

        // Cria uma fonte de dados a partir da lista de vendas
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(vendas);

        // Configuração de parâmetros
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("periodo", "De " + inicio + " a " + fim);

        // Preenche o relatório com os dados
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Exporta para PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream.toByteArray();
    }

    // Método para gerar relatório em Excel
    public byte[] gerarRelatorioVendasExcel(LocalDateTime inicio, LocalDateTime fim) throws IOException {
        List<Venda> vendas = vendaRepository.findByPeriodo(inicio, fim);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Vendas");

            // Cabeçalho
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Carro");
            headerRow.createCell(2).setCellValue("Vendedor");
            headerRow.createCell(3).setCellValue("Data");
            headerRow.createCell(4).setCellValue("Valor");

            // Dados
            int rowNum = 1;
            for (Venda venda : vendas) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(venda.getId());
                row.createCell(1).setCellValue(venda.getCarro().getMarca() + " " + venda.getCarro().getModelo());
                row.createCell(2).setCellValue(venda.getVendedor().getNome());
                row.createCell(3).setCellValue(venda.getDataVenda().toString());
                row.createCell(4).setCellValue(venda.getValorVenda());
            }

            // Exporta para bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return outputStream.toByteArray();
        }
    }
}