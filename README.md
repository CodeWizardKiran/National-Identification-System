# National-Identification-System
National Identification System
This project is a simulation of a national identification system developed using Java. It provides a simple command-line interface for managing citizen records, including adding new citizens, updating existing records, and searching for citizens based on various criteria.

Features
Add new citizens to the system with unique identification numbers
Update existing citizen records, including name, address, and other details
Search for citizens by ID number, name, or other criteria
View a list of all citizens registered in the system
Usage
Before Executing creating a users table in MySQL and replacing database name, username, and password is must for inserting and retreiving data :

---

## Database Setup

1. **Create a Users Table:**

   To store user information, create a table in your MySQL database. Use the following SQL query as an example:

   ```sql
   CREATE TABLE users (
       id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(255) NOT NULL,
       dob DATE NOT NULL,
       phone_number VARCHAR(10) NOT NULL,
       aadhaar_number VARCHAR(12) NOT NULL,
       email VARCHAR(255) NOT NULL,
       city VARCHAR(255) NOT NULL,
       state VARCHAR(255) NOT NULL,
       password VARCHAR(255) NOT NULL,
       approved BOOLEAN NOT NULL,
       unique_id VARCHAR(255)
   );
   ```

2. **Replace Database Name, Username, and Password:**

   In your Java code, replace the following placeholders with your actual database name, username, and password:

   - `DB_URL`: Replace `nis` with your database name.
   - `DB_USERNAME`: Replace `root` with your MySQL username.
   - `DB_PASSWORD`: Replace `kiran2003` with your MySQL password.

   ```java
   private static final String DB_URL = "jdbc:mysql://localhost:3306/Your_database_name";
   private static final String DB_USERNAME = "UserName";
   private static final String DB_PASSWORD = "password";
   ```

   Ensure that your MySQL server is running and accessible with the provided credentials.

---
To run the application, compile the Main.java file and run the generated executable. Follow the on-screen instructions to navigate the system and perform operations.


Contributors
CodeWizardKiran : https://github.com/CodeWizardKiran/
Feel free to contribute to this project by forking the repository and submitting pull requests with your enhancements or bug fixes.
