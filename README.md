

🏨 Hotel Management Console App

A simple console-based Hotel Management System built using Java and Maven, designed to simulate hotel room booking and administration. Supports file handling and optionally PostgreSQL database with JDBC.


---

📌 Features

👥 User login system (Admin & Customer roles)

📖 View room status

🛏️ Book room (thread-safe with concurrency handling)

❌ Vacate room (admin only)

💾 Data saved/loaded using plain text files or PostgreSQL

🧵 Multithreading for simulating real-time booking



---

💡 Technologies Used

Java (JDK 8+)

Maven (for project structure & build)

File Handling (.txt for users and bookings)

JDBC (PostgreSQL database) – optional

Multithreading



---

🛢️ Database Integration (Optional)

To use PostgreSQL instead of text files, create a class named DBConnection.java:

// DBConnection.java
import java.sql.*;

public class DBConnection {
    public static Connection getConnection() {
        try {
            String url = "jdbc:postgresql://localhost:5432/hotel_db";
            String user = "postgres";
            String password = "your_password";
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

> 📌 Make sure PostgreSQL is installed and running. Create the database manually:



CREATE DATABASE hotel_db;


---

📦 Maven Dependency for PostgreSQL

Add the following to your pom.xml:

<dependencies>
  <!-- PostgreSQL JDBC Driver -->
  <dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.3</version>
  </dependency>
</dependencies>

<build>
  <plugins>
    <plugin>
      <groupId>org.codehaus.mojo</groupId>
      <artifactId>exec-maven-plugin</artifactId>
      <version>3.1.0</version>
      <configuration>
        <mainClass>FullHotelAppWithoutJDBC</mainClass> <!-- or your main class -->
      </configuration>
    </plugin>
  </plugins>
</build>


---

📂 Folder Structure

HotelApp-Maven/
├── pom.xml                        
└── src/
    └── main/
        └── java/
            ├── FullHotelAppWithoutJDBC.java  
            └── DBConnection.java            
├── users.txt                       
├── bookings.txt                    

---

🧪 Sample Login Credentials (File-based)

Create a file named users.txt in the root folder:

admin,admin123,admin
john,1234,customer
alice,abcd,customer


---

🔄 Switching from File to Database

To use database storage instead of .txt files:

Replace all FileReader/FileWriter code with PreparedStatement and ResultSet.

Create tables like:


CREATE TABLE users (
  username VARCHAR(50) PRIMARY KEY,
  password VARCHAR(50),
  role VARCHAR(20)
);

CREATE TABLE bookings (
  room_number INT PRIMARY KEY,
  is_booked BOOLEAN,
  booked_by VARCHAR(50)
);

Update your logic to:

Fetch users from the users table

Insert and update booking info in the bookings table




---

🚀 How to Run

🖥️ Prerequisites

Java 8 or higher

Apache Maven

PostgreSQL (optional, for DB version)

Git (optional)



---

🔧 Run using Command Line

cd HotelApp-Maven
mvn compile
mvn exec:java




---

📷 Screenshots

(Optional – Add CLI screenshots of your app running here)


---

👨‍💻 Author

Abhijeet Jadhav
GitHub: @abhijeet0093


---

⭐ License

This project is open-source and free to use for learning and academic purposes.


