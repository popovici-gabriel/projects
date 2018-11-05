package com.example.coding01.stock;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Float.parseFloat;
import static java.util.Objects.requireNonNull;

@Service
@Slf4j
public class ImportService {

    private final StockRepository stockRepository;

    @Value("classpath:data")
    private Resource resourceDir;

    public ImportService(StockRepository stockRepository) {
        this.stockRepository = requireNonNull(stockRepository);
    }

    public void importData() throws IOException {
        Path inputDirectory = Paths.get(resourceDir.getURI());
        List<Path> csvPathFiles = getFiles(inputDirectory);

        final CSVFormat header = CSVFormat.RFC4180
                .withHeader("Date", "Open", "High", "Low", "Close", "Adj Close", "Volume")
                .withSkipHeaderRecord();

        List<Stock> stocks = new LinkedList<>();
        csvPathFiles.forEach(path -> {
            try (Reader reader = Files.newBufferedReader(path);
                 CSVParser csvParser = new CSVParser(reader, header)) {
                csvParser.forEach(record ->
                        stocks.add(Stock.builder()
                                .symbol(path.getFileName().toString().toUpperCase().replaceAll(".CSV", ""))
                                .valueDate(LocalDate.parse(record.get("Date"), DateTimeFormatter.ISO_DATE))
                                .open(parseFloat(record.get("Open")))
                                .high(parseFloat(record.get("High")))
                                .low(parseFloat(record.get("Low")))
                                .close(parseFloat(record.get("Close")))
                                .adjustedClose(parseFloat(record.get("Adj Close")))
                                .volume(Integer.parseInt(record.get("Volume")))
                                .build())
                );
            } catch (IOException e) {
                log.error("Import failed", e);
            }
            this.stockRepository.saveAll(stocks);
        });
    }

    private List<Path> getFiles(Path dataDirectory) throws IOException {
        try (Stream<Path> rootStream = Files.walk(dataDirectory)) {
            return rootStream
                    .filter(Files::isRegularFile) // check for files
                    .filter(path -> path.getFileName().toString().toUpperCase().endsWith("CSV")) // check specific files
                    .collect(Collectors.toList()); // collect files
        }
    }
}
