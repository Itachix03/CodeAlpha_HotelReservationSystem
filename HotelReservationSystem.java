import java.io.*;
import java.util.*;

// Room class
class Room {
    private int roomNumber;
    private String category;
    private boolean isAvailable;

    public Room(int roomNumber, String category, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    public int getRoomNumber() { return roomNumber; }
    public String getCategory() { return category; }
    public boolean isAvailable() { return isAvailable; }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return roomNumber + "," + category + "," + isAvailable;
    }
}

// Reservation class
class Reservation {
    private String guestName;
    private int roomNumber;

    public Reservation(String guestName, int roomNumber) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
    }

    public String getGuestName() { return guestName; }
    public int getRoomNumber() { return roomNumber; }

    @Override
    public String toString() {
        return guestName + "," + roomNumber;
    }
}

// Payment simulation class
class Payment {
    public static boolean processPayment(String guestName, String category) {
        System.out.println("Processing payment for " + guestName + " (" + category + ")");
        try {
            Thread.sleep(1000); // simulate delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Payment successful!");
        return true;
    }
}

// File I/O manager class
class Database {
    public static List<Room> loadRooms() throws IOException {
        List<Room> rooms = new ArrayList<>();
        File file = new File("rooms.txt");

        if (!file.exists()) {
            // Add default rooms if file not found
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("101,Standard,true\n102,Standard,true\n201,Deluxe,true\n202,Deluxe,true\n301,Suite,true\n");
            writer.close();
        }

        BufferedReader br = new BufferedReader(new FileReader("rooms.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            rooms.add(new Room(Integer.parseInt(data[0]), data[1], Boolean.parseBoolean(data[2])));
        }
        br.close();
        return rooms;
    }

    public static void saveRooms(List<Room> rooms) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("rooms.txt"));
        for (Room room : rooms) {
            bw.write(room.toString());
            bw.newLine();
        }
        bw.close();
    }

    public static void saveReservation(Reservation res) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("bookings.txt", true));
        bw.write(res.toString());
        bw.newLine();
        bw.close();
    }

    public static void cancelReservation(String guestName) throws IOException {
        File inputFile = new File("bookings.txt");
        File tempFile = new File("temp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;
        while((currentLine = reader.readLine()) != null) {
            if(currentLine.startsWith(guestName + ",")) continue;
            writer.write(currentLine);
            writer.newLine();
        }

        writer.close();
        reader.close();
        inputFile.delete();
        tempFile.renameTo(inputFile);
    }
}

// Hotel logic class
class Hotel {
    private List<Room> rooms;

    public Hotel(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void showAvailableRooms(String category) {
        System.out.println("Available " + category + " rooms:");
        for (Room room : rooms) {
            if (room.isAvailable() && room.getCategory().equalsIgnoreCase(category)) {
                System.out.println("Room: " + room.getRoomNumber());
            }
        }
    }

    public Room findAvailableRoom(String category) {
        for (Room room : rooms) {
            if (room.isAvailable() && room.getCategory().equalsIgnoreCase(category)) {
                return room;
            }
        }
        return null;
    }

    public boolean bookRoom(String guestName, String category) {
        Room room = findAvailableRoom(category);
        if (room == null) {
            System.out.println("No " + category + " rooms available.");
            return false;
        }

        boolean paid = Payment.processPayment(guestName, category);
        if (!paid) return false;

        room.setAvailable(false);
        try {
            Database.saveRooms(rooms);
            Database.saveReservation(new Reservation(guestName, room.getRoomNumber()));
            System.out.println("Room " + room.getRoomNumber() + " booked successfully.");
        } catch (Exception e) {
            System.out.println("Error saving booking.");
        }
        return true;
    }

    public void cancelBooking(String guestName) {
        try {
            for (Room room : rooms) {
                if (!room.isAvailable()) {
                    Database.cancelReservation(guestName);
                    room.setAvailable(true);
                    break;
                }
            }
            Database.saveRooms(rooms);
            System.out.println("Reservation cancelled.");
        } catch (Exception e) {
            System.out.println("Cancellation failed.");
        }
    }
}

// Main driver class
public class HotelReservationSystem {
    public static void main(String[] args) {
        try {
            List<Room> rooms = Database.loadRooms();
            Hotel hotel = new Hotel(rooms);
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n--- Hotel Reservation System ---");
                System.out.println("1. View Available Rooms");
                System.out.println("2. Book Room");
                System.out.println("3. Cancel Booking");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter category (Standard/Deluxe/Suite): ");
                        hotel.showAvailableRooms(sc.nextLine());
                        break;
                    case 2:
                        System.out.print("Enter your name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter room category: ");
                        hotel.bookRoom(name, sc.nextLine());
                        break;
                    case 3:
                        System.out.print("Enter your name: ");
                        hotel.cancelBooking(sc.nextLine());
                        break;
                    case 4:
                        System.out.println("Thank you for using the system!");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error initializing system: " + e.getMessage());
        }
    }
}
