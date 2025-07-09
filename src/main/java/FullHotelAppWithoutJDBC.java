import java.io.*;
import java.util.*;

class Room {
    int number;
    boolean isBooked;
    String customer;

    Room(int number) {
        this.number = number;
        this.isBooked = false;
        this.customer = "";
    }

    synchronized void book(String name) {
        if (!isBooked) {
            System.out.println(name + " is booking Room " + number + "...");
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            isBooked = true;
            customer = name;
            System.out.println("Room " + number + " successfully booked by " + name);
        } else {
            System.out.println("Room " + number + " is already booked by " + customer);
        }
    }

    synchronized void vacate() {
        if (isBooked) {
            System.out.println("Room " + number + " vacated by " + customer);
            isBooked = false;
            customer = "";
        } else {
            System.out.println("Room " + number + " is already vacant.");
        }
    }

    void display() {
        System.out.println("Room " + number + ": " + (isBooked ? "Booked by " + customer : "Vacant"));
    }

    String toFileString() {
        return number + "," + isBooked + "," + customer;
    }

    static Room fromFileString(String data) {
        String[] parts = data.split(",");
        Room r = new Room(Integer.parseInt(parts[0]));
        r.isBooked = Boolean.parseBoolean(parts[1]);
        if (r.isBooked) r.customer = parts[2];
        return r;
    }
}

class BookingThread extends Thread {
    private final Room room;
    private final String customer;

    BookingThread(Room room, String customer) {
        this.room = room;
        this.customer = customer;
    }

    public void run() {
        room.book(customer);
    }
}

public class FullHotelAppWithoutJDBC {
    static final String ROOM_FILE = "bookings.txt";
    static final String USER_FILE = "users.txt";
    static final int TOTAL_ROOMS = 5;

    static Room[] rooms = new Room[TOTAL_ROOMS];
    static Scanner scanner = new Scanner(System.in);
    static String currentRole = "";

    public static void main(String[] args) {
        loadRooms();

        if (!login()) {
            System.out.println("Too many failed attempts. Exiting...");
            return;
        }

        int choice;
        do {
            System.out.println("\n--- Hotel Menu (" + currentRole.toUpperCase() + ") ---");
            System.out.println("1. View Rooms");
            System.out.println("2. Book Room");
            if (currentRole.equals("admin")) {
                System.out.println("3. Vacate Room\n4. Exit");
            } else {
                System.out.println("3. Exit");
            }

            System.out.print("Enter choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    for (Room r : rooms) r.display();
                    break;

                case 2:
                    System.out.print("Enter room number to book (1-" + TOTAL_ROOMS + "): ");
                    int rnum = scanner.nextInt();
                    scanner.nextLine();
                    if (isValidRoom(rnum)) {
                        System.out.print("Enter your name: ");
                        String name = scanner.nextLine();
                        BookingThread t1 = new BookingThread(rooms[rnum - 1], name);
                        BookingThread t2 = new BookingThread(rooms[rnum - 1], "ConcurrentUser");
                        t1.start(); t2.start();
                        try { t1.join(); t2.join(); } catch (InterruptedException e) {}
                    }
                    break;

                case 3:
                    if (currentRole.equals("admin")) {
                        System.out.print("Enter room number to vacate: ");
                        int vnum = scanner.nextInt();
                        if (isValidRoom(vnum)) {
                            rooms[vnum - 1].vacate();
                        }
                    } else {
                        saveRooms();
                        System.out.println("Thank you! Exiting...");
                        return;
                    }
                    break;

                case 4:
                    if (currentRole.equals("admin")) {
                        saveRooms();
                        System.out.println("Data saved. Exiting...");
                        return;
                    }
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        } while (true);
    }

    static boolean login() {
        int attempts = 3;
        while (attempts-- > 0) {
            System.out.print("Username: ");
            String uname = scanner.next();
            System.out.print("Password: ");
            String pass = scanner.next();

            try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals(uname) && parts[1].equals(pass)) {
                        currentRole = parts[2];
                        System.out.println("Login successful as " + currentRole);
                        return true;
                    }
                }
                System.out.println("Invalid credentials. Attempts left: " + attempts);
            } catch (IOException e) {
                System.out.println("Error reading user file.");
                return false;
            }
        }
        return false;
    }

    static boolean isValidRoom(int num) {
        return num >= 1 && num <= TOTAL_ROOMS;
    }

    static void loadRooms() {
        File file = new File(ROOM_FILE);
        if (!file.exists()) {
            for (int i = 0; i < TOTAL_ROOMS; i++) {
                rooms[i] = new Room(i + 1);
            }
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ROOM_FILE))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null && i < TOTAL_ROOMS) {
                rooms[i++] = Room.fromFileString(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading rooms.");
        }
    }

    static void saveRooms() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ROOM_FILE))) {
            for (Room r : rooms) {
                bw.write(r.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving rooms.");
        }
    }
}
