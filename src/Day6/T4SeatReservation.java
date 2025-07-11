package Day6;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class T4SeatReservation {
    static boolean[] seats = new boolean[10];
    static int[] fare = {500, 500, 400, 400, 300, 300, 300, 300, 300, 300};
    static String[] passengers = new String[00];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(" Welcome to Akshay Bus and Seat Reservation ");

        int choice;
        do {
            System.out.println("\n******** Menu *********");
            System.out.println("1. Book a Seat");
            System.out.println("2. View Booked Seats");
            System.out.println("3. Cancel a Seat");
            System.out.println("4. Show Seat Map");
            System.out.println("5. Save & Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    bookSeat(sc);
                    break;
                case 2:
                    viewBookedSeats();
                    break;
                case 3:
                    cancelSeat(sc);
                    break;
                case 4:
                    displaySeats();
                    break;
                case 5:
                    saveToFile();
                    System.out.println("Exiting... Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 5);

        sc.close();
    }

    public static void bookSeat(Scanner sc) {
        displaySeats();
        System.out.print("Enter seat number to book (0-9): ");
        int seat = sc.nextInt();
        sc.nextLine();  // consume newline

        if (seat >= 0 && seat < seats.length) {
            if (!seats[seat]) {
                System.out.print("Enter passenger name: ");
                String name = sc.nextLine();
                seats[seat] = true;
                passengers[seat] = name;
                System.out.println("Seat " + seat + " booked successfully for " + name + ". Fare: ₹" + fare[seat]);
            } else {
                System.out.println("Seat already booked.");
            }
        } else {
            System.out.println("Invalid seat number.");
        }
    }

    public static void viewBookedSeats() {
        System.out.println("\n--- Booked Seats ---");
        boolean bookedFound = false;
        for (int i = 0; i < seats.length; i++) {
            if (seats[i]) {
                System.out.println("Seat " + i + ": Booked by " + passengers[i] + " | Fare: ₹" + fare[i]);
                bookedFound = true;
            }
        }
        if (!bookedFound) {
            System.out.println("No seats booked yet.");
        }

        System.out.println("\n--- Available Seats ---");
        boolean availableFound = false;
        for (int i = 0; i < seats.length; i++) {
            if (!seats[i]) {
                System.out.println("Seat " + i + ": Available | Fare: ₹" + fare[i]);
                availableFound = true;
            }
        }
        if (!availableFound) {
            System.out.println("No available seats left.");
        }
    }


    public static void cancelSeat(Scanner sc) {
        System.out.print("Enter seat number to cancel (0-9): ");
        int seat = sc.nextInt();

        if (seat >= 0 && seat < seats.length) {
            if (seats[seat]) {
                System.out.println("Seat canceled for " + passengers[seat]);
                seats[seat] = false;
                passengers[seat] = null;
            } else {
                System.out.println("Seat is not booked yet.");
            }
        } else {
            System.out.println("Invalid seat number.");
        }
    }

    public static void displaySeats() {
        System.out.println("\n--- Bus Seat Map ---");
        for (int i = 0; i < seats.length; i++) {
            if (seats[i]) {
                System.out.print("[X] ");
            } else {
                System.out.print("[" + i + "] ");
            }
        }
        System.out.println();
    }

    public static void saveToFile() {
        try {
            FileWriter writer = new FileWriter("bus_seats.txt");
            writer.write("Bus Seat Booking Status:\n");
            for (int i = 0; i < seats.length; i++) {
                if (seats[i]) {
                    writer.write("Seat " + i + " - Booked by " + passengers[i] + " (₹" + fare[i] + ")\n");
                } else {
                    writer.write("Seat " + i + " - Available\n");
                }
            }
            writer.close();
            System.out.println("Bookings saved to 'bus_seats.txt'.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}


