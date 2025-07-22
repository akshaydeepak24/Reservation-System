package SeatReservationSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class SeatReservationSystem {
    static final int BUS_COUNT = 3;
    static final int SEAT_COUNT = 20;
    static final String[] BUS_NAMES = {"Express", "Garuda", "Volvo"};
    static boolean[][] seats = new boolean[BUS_COUNT][SEAT_COUNT];
    static int[][] fare = new int[BUS_COUNT][SEAT_COUNT];
    static String[][] passengers = new String[BUS_COUNT][SEAT_COUNT];
    static final String FILE_PATH = "C:/Users/aksha/eclipse-workspace/PersonalProjects/src/SeatReservationSystem/bus_seat_status.csv";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadFromCSV();

        int choice = -1;
        do {
            System.out.println("\n===== Menu =====");
            System.out.println("1. Book Seat\n2. Cancel Seat\n3. View Seats\n4. Search by Name\n5. Search by Fare Range\n6. Show Seat Map\n7. Export to Excel\n8. Show Total Revenue\n9. Stat Chart\n10.Exit");
            System.out.print("Enter choice: ");

            if (sc.hasNextInt()) {
                choice = sc.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.next();
                continue;
            }

            switch (choice) {
                case 1 -> bookSeat(sc);
                case 2 -> cancelSeat(sc);
                case 3 -> viewSeatsSummary();
                case 4 -> searchByName(sc);
                case 5 -> searchByFare(sc);
                case 6 -> showSeatMap(sc);
                case 7 -> exportToCSV();
                case 8 -> showRevenue();
                case 9 -> showOverallStatsChart();
                case 10 -> {
                    exportToCSV();
                    System.out.println("Exiting...");
                }
                default -> System.out.println("Invalid");
            }
        } while (choice != 10);
        sc.close();
    }

    static int getBus(Scanner sc) {
        System.out.println("Select Bus:");
        for (int i = 0; i < BUS_COUNT; i++) {
            System.out.println((i + 1) + ". " + BUS_NAMES[i]);
        }
        System.out.print("Enter choice: ");
        int b = sc.nextInt() - 1;
        if (b < 0 || b >= BUS_COUNT) {
            System.out.println("Invalid choice. Defaulting to " + BUS_NAMES[0]);
            return 0;
        }
        return b;
    }

    static void bookSeat(Scanner sc) {
        int b = getBus(sc);
        System.out.print("Seat (0-19): ");
        int s = sc.nextInt();
        if (s < 0 || s >= SEAT_COUNT) {
            System.out.println("Invalid seat number");
            System.out.println("This bus is having only 20 Seats starting from 0 to 19...");
            return;
        }
        sc.nextLine();
        if (!seats[b][s]) {
            System.out.print("Name: ");
            passengers[b][s] = sc.nextLine();
            seats[b][s] = true;
            System.out.println("Booked | Fare: ₹" + fare[b][s]);
        } else System.out.println("Already booked");
    }

    static void cancelSeat(Scanner sc) {
        int b = getBus(sc);
        System.out.print("Seat (0-19): ");
        int s = sc.nextInt();
        if (s < 0 || s >= SEAT_COUNT) {
            System.out.println("Invalid seat number");
            System.out.println("This bus is having only 20 Seats starting from 0 to 19...");
            return;
        }
        if (seats[b][s]) {
            System.out.println("Canceled: " + passengers[b][s]);
            seats[b][s] = false;
            passengers[b][s] = null;
        } else System.out.println("Not booked");
    }

    static void viewSeatsSummary() {
        for (int b = 0; b < BUS_COUNT; b++) {
            System.out.println("\nBus: " + BUS_NAMES[b]);
            int[][] ranges = {{0, 4, 500}, {5, 9, 400}, {10, 19, 300}};
            for (int[] range : ranges) {
                int booked = 0, available = 0;
                StringBuilder availableSeats = new StringBuilder();
                StringBuilder bookedDetails = new StringBuilder();
                for (int s = range[0]; s <= range[1]; s++) {
                    if (seats[b][s]) {
                        booked++;
                        bookedDetails.append(s).append(":").append(passengers[b][s]).append(" ");
                    } else {
                        available++;
                        availableSeats.append(s).append(" ");
                    }
                }
                System.out.println("Seats " + range[0] + "-" + range[1] + ": ₹" + range[2] +
                        " - " + booked + " booked" +
                        (booked > 0 ? " (" + bookedDetails.toString().trim() + ")" : "") +
                        ", " + available + " available" +
                        (available > 0 ? " (" + availableSeats.toString().trim() + ")" : ""));
            }
        }
    }

    static void searchByName(Scanner sc) {
        sc.nextLine();
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        boolean found = false;
        for (int b = 0; b < BUS_COUNT; b++)
            for (int s = 0; s < SEAT_COUNT; s++)
                if (seats[b][s] && passengers[b][s].equalsIgnoreCase(name)) {
                    System.out.println("Bus: " + BUS_NAMES[b] + " Seat " + s + " | ₹" + fare[b][s]);
                    found = true;
                }
        if (!found) System.out.println("Not found");
    }

    static void searchByFare(Scanner sc) {
        System.out.print("Min Fare: ");
        int min = sc.nextInt();
        System.out.print("Max Fare: ");
        int max = sc.nextInt();
        for (int b = 0; b < BUS_COUNT; b++)
        	
            for (int s = 0; s < SEAT_COUNT; s++)
                if (fare[b][s] >= min && fare[b][s] <= max) {
                    String status = seats[b][s] ? "Booked by " + passengers[b][s] : "Available";
                    System.out.println("Bus: " + BUS_NAMES[b] + " Seat " + s + ": " + status + " | ₹" + fare[b][s]);
                }
    }

    static void showSeatMap(Scanner sc) {
        int b = getBus(sc);
        System.out.println("\nBus: " + BUS_NAMES[b] + " Seat Map:");
        for (int s = 0; s < SEAT_COUNT; s++) {
            if (seats[b][s]) {
                System.out.print("\u001B[31m|X:" + passengers[b][s] + "|\u001B[0m "); 
            } else {
                System.out.print("\u001B[32m[" + s + "]\u001B[0m "); 
            }
        }
        System.out.println();
    }

    static void exportToCSV() {
        try {
            FileWriter writer = new FileWriter(FILE_PATH);
            writer.write("Bus,Seat,Status,Fare\n");
            for (int b = 0; b < BUS_COUNT; b++) {
                for (int s = 0; s < SEAT_COUNT; s++) {
                    String status = seats[b][s] ? "Booked by " + passengers[b][s] : "Available";
                    writer.write(BUS_NAMES[b] + "," + s + "," + status + "," + fare[b][s] + "\n");
                }
            }
            writer.write("\nSummary,Bus,Booked,Available,Occupancy%\n");
            for (int b = 0; b < BUS_COUNT; b++) {
                int booked = 0;
                for (int s = 0; s < SEAT_COUNT; s++) {
                    if (seats[b][s]) booked++;
                }
                int available = SEAT_COUNT - booked;
                int percent = (booked * 100) / SEAT_COUNT;
                writer.write("Summary," + BUS_NAMES[b] + "," + booked + "," + available + "," + percent + "%\n");
            }
            
            writer.close();
            System.out.println("Exported to bus_seat_status.csv with summary for charts.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void loadFromCSV() {
        try (Scanner fileScanner = new Scanner(new File(FILE_PATH))) {
            fileScanner.nextLine(); 
            while (fileScanner.hasNextLine()) {
                String[] parts = fileScanner.nextLine().split(",");
                if (parts.length < 4) continue;

                if (parts[0].trim().equalsIgnoreCase("Summary")) continue;

                String busName = parts[0].trim();
                int busIndex = Arrays.asList(BUS_NAMES).indexOf(busName);
                
                if (busIndex == -1) continue;

                int seatIndex;
                try {
                    seatIndex = Integer.parseInt(parts[1].trim());
                } catch (NumberFormatException e) {
                    continue;
                }

                String status = parts[2].trim();
                int seatFare;
                try {
                    seatFare = Integer.parseInt(parts[3].trim().replace("%", ""));
                } catch (NumberFormatException e) {
                    seatFare = 0;
                }

                fare[busIndex][seatIndex] = seatFare;

                if (status.startsWith("Booked by")) {
                    seats[busIndex][seatIndex] = true;
                    passengers[busIndex][seatIndex] = status.substring("Booked by ".length());
                } else {
                    seats[busIndex][seatIndex] = false;
                    passengers[busIndex][seatIndex] = null;
                }
            }
            System.out.println("Data loaded from CSV.");
        } catch (IOException e) {
            System.out.println("No previous data found or error reading file.");
            for (int b = 0; b < BUS_COUNT; b++)
                for (int s = 0; s < SEAT_COUNT; s++)
                    fare[b][s] = 300 + (s < 5 ? 200 : s < 10 ? 100 : 0);
        }
    }
    static void showRevenue() {
        for (int b = 0; b < BUS_COUNT; b++) {
            int total = 0;
            for (int s = 0; s < SEAT_COUNT; s++) {
                if (seats[b][s]) {
                    total += fare[b][s];
                }
            }
            System.out.println("Revenue for " + BUS_NAMES[b] + ": ₹" + total);
        }
    }
    static void showOverallStatsChart() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===== Bus Occupancy =====\n");
        for (int b = 0; b < BUS_COUNT; b++) {
            int booked = 0;
            for (int s = 0; s < SEAT_COUNT; s++) {
                if (seats[b][s]) booked++;
            }
            int percent = (booked * 100) / SEAT_COUNT;
            sb.append(String.format("%-7s: ", BUS_NAMES[b]));
            for (int i = 0; i < SEAT_COUNT; i++) {
                if (i < booked) {
                    sb.append("\u001B[34m#\u001B[0m");
                } else {
                    sb.append("-");
                }
            }
            sb.append(" ").append(percent).append("% (")
              .append(booked).append("/").append(SEAT_COUNT).append(")\n");
        }
        System.out.print(sb.toString());
    }

}
