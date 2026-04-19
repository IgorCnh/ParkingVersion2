# ParkingV2 

A parking management system built with Java and MySQL.\
This application manages vehicle registration, entry, and exit in
environments such as parking lots, shopping centers, and garages.

------------------------------------------------------------------------

## 📌 Features

-   Vehicle registration
-   Entry time recording
-   Exit time update (no record deletion)
-   License plate validation
-   Database validation to prevent duplicate entries

------------------------------------------------------------------------

## 🛠️ Technologies

-   Java
-   MySQL

------------------------------------------------------------------------

## ⚙️ How to Run

### 1. Clone the repository

git clone https://github.com/IgorCnh/parkingVersion2.git

### 2. Open the project

-   Open IntelliJ IDEA
-   Click on **Open**
-   Select the project folder

------------------------------------------------------------------------

### 3. Configure the database

1.  Open MySQL
2.  Import the dump file:

ParkingV2.sql

OR via MySQL Workbench: - Go to **Server → Data Import** - Select
**Import from Self-Contained File** - Choose `./database/ParkingV2.sql` - Click
**Start Import**

------------------------------------------------------------------------

### 4. Configure database connection

Update your connection settings in the project:

String url = "jdbc:mysql://localhost:3306/parking"; 
String user ="your_user"; 
String password = "your_password";

------------------------------------------------------------------------

### 5. Run the application

-   Run the main class via IntelliJ
-   Or execute through terminal

------------------------------------------------------------------------

## 🗄️ Database

The system uses MySQL to store:

-   Vehicles
-   License plates
-   Entry time
-   Exit time
-   Parking records

> The schema and data are automatically created when importing
> `ParkingV2.sql`.

------------------------------------------------------------------------

## 📖 How It Works

1.  The user registers a vehicle using its license plate\
2.  The system records the entry time\
3.  When the vehicle exits, the system updates the exit time\
4.  Validation is performed based on the license plate to avoid
    duplicate records

------------------------------------------------------------------------

## 👤 Author

Igor Cunha

- LinkedIn: https://www.linkedin.com/in/igor-cunha-ba363b397/
- GitHub: https://github.com/IgorCnh

------------------------------------------------------------------------

## 🚀 Future Improvements

-   Graphical User Interface (GUI)
-   Payment system integration
-   Reports and analytics dashboard
-   REST API