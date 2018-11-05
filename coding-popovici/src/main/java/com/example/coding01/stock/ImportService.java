package com.example.coding01.stock;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static java.lang.Float.parseFloat;
import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class ImportService {

    private final StockRepository stockRepository;

    public ImportService(StockRepository stockRepository) {
        this.stockRepository = Objects.requireNonNull(stockRepository);
    }

    public void importData() throws IOException {
        Path root = Paths.get("/Users/Gabe/projects/coding-popovici/src/main/resources/data/");
        List<Path> csvPath = Files
                .walk(root)
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().toUpperCase().endsWith("CSV"))
                .collect(toList());

        final CSVFormat header = CSVFormat.RFC4180
                .withHeader("Date", "Open", "High", "Low", "Close", "Adj Close", "Volume")
                .withSkipHeaderRecord();

        List<Stock> stocks = new LinkedList<>();
        csvPath.forEach(path -> {
            try (Reader reader = Files.newBufferedReader(path);
                 CSVParser csvParser = new CSVParser(reader, header)) {
                csvParser.forEach(record -> {
                    record.get("Date");
                    stocks.add(Stock.builder()
                            .symbol(path.getFileName().toString().toUpperCase().replaceAll(".CSV", ""))
                            .valueDate(LocalDate.parse(record.get("Date"), DateTimeFormatter.ISO_DATE))
                            .open(parseFloat(record.get("Open")))
                            .high(parseFloat(record.get("High")))
                            .low(parseFloat(record.get("Low")))
                            .close(parseFloat(record.get("Close")))
                            .adjustedClose(parseFloat(record.get("Adj Close")))
                            .volume(Integer.parseInt(record.get("Volume")))
                            .build());
                });
            } catch (IOException e) {
                log.error("Import failed", e);
            }
            this.stockRepository.saveAll(stocks);
        });
    }
}
