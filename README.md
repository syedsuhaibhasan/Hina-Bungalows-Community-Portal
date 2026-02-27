# Hina Bungalows Community Portal (H.B.C.P)

Hina Bungalows Community Portal is a **Java Swing desktop application** designed to streamline the management of a residential community. It provides dedicated interfaces for both **administrators** and **residents**, enabling efficient handling of maintenance payments, utility bill management, complaint tracking, expense breakdowns, and community-wide notifications — all through a modern, user-friendly GUI.

## Features

### Admin Panel
- **Dashboard** — Central hub for accessing all administrative operations
- **User Registration** — Register new residents with details such as name, email, house number, and ownership type (owned/rented)
- **Payment Records** — Track and manage monthly maintenance payment statuses for all houses
- **Utility Bill Upload** — Upload electricity (KE) bill images along with invoice metadata; bills are stored as BLOBs in the database
- **Maintenance Breakdown** — Record and view monthly expense breakdowns by category (utilities, guard salary, sweeper salary, repairs, miscellaneous)
- **Complaint Management** — View and manage resident complaints with status tracking (Pending, Open, Closed)
- **Notifications** — Post community-wide announcements visible to all residents

### Resident Panel
- **Dashboard** — Personalized view showing house information, payment status, and recent notifications
- **View Payment Records** — Check personal maintenance payment history and outstanding dues
- **View Monthly Expenses** — Review the community's monthly expense breakdown
- **View Utility Bills** — Access uploaded electricity bills
- **Submit Complaints** — File maintenance or community-related complaints

### Security
- **BCrypt Password Hashing** — All user and admin passwords are securely hashed using the OpenBSD-style Blowfish algorithm
- **Role-Based Access** — Separate login flow for administrators and residents

## Technology Stack

| Component        | Technology                          |
|------------------|-------------------------------------|
| Language         | Java                                |
| GUI Framework    | Swing with FlatLaf Look-and-Feel    |
| Database         | MySQL 8.0+                          |
| JDBC Driver      | MySQL Connector/J (`com.mysql.cj`)  |
| Password Hashing | jBCrypt                             |
| Build System     | Apache Ant (NetBeans IDE project)    |

## Project Structure

```
src/
├── hinabungalowsportal/
│   └── HinaBungalowsPortal.java        # Application entry point
├── dao/
│   ├── ConnectionProvider.java          # JDBC connection manager
│   └── Tables.java                      # Table initialization utility
├── form/
│   ├── adminView/
│   │   ├── login.java                   # Dual-role login screen
│   │   ├── registrationPage.java        # Resident registration form
│   │   ├── admindashboard.java          # Admin main dashboard
│   │   ├── KEBillUpload.java            # Electricity bill upload
│   │   ├── payementRecords.java         # Payment tracking
│   │   ├── maintenanceBreakdown.java    # Expense breakdown view
│   │   ├── addNotification.java         # Post notifications
│   │   └── viewComplain.java            # Complaint management
│   └── userView/
│       ├── userdashboard.java           # Resident dashboard
│       ├── complain.java                # Submit complaints
│       ├── viewpayementRecords.java     # View payment history
│       ├── viewMonthlyExpense.java       # View expense breakdown
│       └── viewKEBill.java              # View electricity bills
├── utility/
│   ├── BDutility.java                   # Image loading & window management
│   ├── UIUtils.java                     # Theme & UI helpers
│   ├── RoundedPanel.java                # Custom rounded-corner panel
│   ├── RoundedLabel.java                # Custom rounded-corner label
│   └── images/                          # Application assets (logo, backgrounds)
└── org/mindrot/jbcrypt/
    └── BCrypt.java                      # BCrypt password hashing library
```

## Database

The file **`HBCP.sql`** contains the complete MySQL database schema and seed data required to run the application. It defines all tables (users, houses, payments, complaints, bills, notifications, and maintenance breakdowns) along with sample records for initial setup.

Import this file into your MySQL server as part of the setup process (see instructions below).

## Setup Guide

Follow these steps to set up and run the project on your local machine.

### Prerequisites

1. **Java Development Kit (JDK) 8 or higher** — [Download from Oracle](https://www.oracle.com/java/technologies/downloads/) or install via your package manager
2. **Apache NetBeans IDE** (recommended) — [Download from Apache](https://netbeans.apache.org/download/)
3. **MySQL Server 8.0+** — [Download from MySQL](https://dev.mysql.com/downloads/mysql/)
4. **MySQL Connector/J** — Included in the `lib/` directory of this project

### Step 1 — Clone the Repository

```bash
git clone https://github.com/syedsuhaibhasan/Hina-Bungalows-Community-Portal.git
cd Hina-Bungalows-Community-Portal
```

### Step 2 — Import the Database

1. Start your MySQL server and log in to the MySQL shell:
   ```bash
   mysql -u root -p
   ```
2. Import the provided SQL file to create the database, tables, and seed data:
   ```bash
   source /path/to/Hina-Bungalows-Community-Portal/HBCP.sql;
   ```
   Alternatively, you can import it directly from the command line:
   ```bash
   mysql -u root -p < HBCP.sql
   ```
   This will create a database named **`communityportal`** with all required tables and initial data.

### Step 3 — Configure Database Credentials

Open `src/dao/ConnectionProvider.java` and update the database credentials to match your local MySQL setup:

```java
String url = "jdbc:mysql://localhost:3306/communityportal";
String user = "root";          // your MySQL username
String password = "123456";    // your MySQL password
```

### Step 4 — Open the Project in NetBeans

1. Open Apache NetBeans IDE
2. Go to **File → Open Project**
3. Navigate to and select the cloned `Hina-Bungalows-Community-Portal` folder
4. NetBeans will recognize it as an Ant-based project

### Step 5 — Verify Library Dependencies

Ensure the following JAR files are present in the `lib/` directory and added to the project's classpath:

- `mysql-connector-j-8.x.x.jar` — MySQL JDBC driver
- `flatlaf-x.x.jar` — FlatLaf Look-and-Feel library

If any library is missing, right-click the project in NetBeans → **Properties → Libraries → Add JAR/Folder** and add the required JARs from the `lib/` directory.

### Step 6 — Build and Run

1. **Clean and Build:** Right-click the project → **Clean and Build** (or press `Shift + F11`)
2. **Run:** Right-click the project → **Run** (or press `F6`)

The application will launch with the login screen. Use the seeded admin credentials from the database to log in as an administrator, or register a new resident account.

## License

This project is developed for educational and community management purposes.
