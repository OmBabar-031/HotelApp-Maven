
# 🏨 Hotel Management Console App

A simple console-based **Hotel Management System** built using **Java** and **Maven**, designed to simulate hotel room booking and administration without the use of JDBC or a database.

---

## 📌 Features

- 👥 User login system (Admin & Customer roles)
- 📖 View room status
- 🛏️ Book room (thread-safe with concurrency handling)
- ❌ Vacate room (admin only)
- 💾 Data saved/loaded using plain text files
- 🧵 Multithreading for simulating real-time booking

---

## 💡 Technologies Used

- Java (JDK 8+)
- Maven (for project structure & build)
- File Handling (`.txt` for users and bookings)
- Multithreading
- No external libraries or database required

---

## 📂 Folder Structure

```
HotelApp-Maven/
├── pom.xml                         # Maven config file
└── src/
    └── main/
        └── java/
            └── FullHotelAppWithoutJDBC.java  # Main class
├── users.txt                       # User login data (admin, customers)
├── bookings.txt                    # Auto-generated room booking status
```

---

## 🧪 Sample Login Credentials

Create a file named `users.txt` in the root folder:

```
admin,admin123,admin
john,1234,customer
alice,abcd,customer
```

---

## 🚀 How to Run

### 🖥️ Prerequisites

- Java 8 or higher installed
- [Apache Maven](https://maven.apache.org/download.cgi) installed
- Git installed (optional, for pushing to GitHub)

---

### 🔧 Run using Command Line

```bash
cd HotelApp-Maven
mvn compile
mvn exec:java
```

If `exec:java` fails, make sure this is in your `pom.xml`:

```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.codehaus.mojo</groupId>
      <artifactId>exec-maven-plugin</artifactId>
      <version>3.1.0</version>
      <configuration>
        <mainClass>FullHotelAppWithoutJDBC</mainClass>
      </configuration>
    </plugin>
  </plugins>
</build>
```

---

## 📝 Notes

- Data is saved in `bookings.txt` when you exit the app.
- Admin can **vacate** any room; customer can only **book**.
- Multithreading used for safe concurrent booking simulation.

---

## 📷 Screenshots

_(Optional – Add CLI screenshots of your app running here)_

---

## 👨‍💻 Author

**Abhijeet Jadhav**  
GitHub: [@abhijeet0093](https://github.com/abhijeet0093)

---

## ⭐ License

This project is open-source and free to use for learning and academic purposes.
