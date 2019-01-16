package com.codecool.singletonDojo;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public final class Printer {

    private static List<SinglePrinter> printerList = new ArrayList<>();
    private static final Printer instance = new Printer(10);
    private static final Random random = new Random();

    private Printer(int numberOfPrinters) {
        for (int i = 0; i < numberOfPrinters; i++) {
            SinglePrinter printer = new SinglePrinter(i);
            printerList.add(printer);
        }
    }

    // Prints out the given String
    public void print(String toPrint) {
        List<SinglePrinter> availablePrinters = printerList.stream()
                .filter(SinglePrinter::isAvailable)
                .collect(Collectors.toList());
        int availablePrinterNumber = availablePrinters.size();
        if (availablePrinterNumber > 0) {
            availablePrinters.get(random.nextInt(availablePrinterNumber)).print(toPrint);
        } else {
            printerList.get(random.nextInt(printerList.size())).print(toPrint);
        }


    }

    public static Printer getInstance() {
        return instance;
    }

    private class SinglePrinter {
        private LocalTime busyEndTime = LocalTime.now();
        private int id; // ID of the printer. Unique.

        SinglePrinter(int id) {
            this.id = id;
        }

        public void print(String toPrint) {
            // Its not needed to actually print with a printer in this exercise
            System.out.println("Printer " + id + " is printing.");
            busyEndTime = LocalTime.now().plusSeconds(5);
        }

        // Returns true if the printer is ready to print now.
        public boolean isAvailable() {
            return LocalTime.now().isAfter(busyEndTime);
        }
    }

}
