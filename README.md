#  Hotel Reservation System

A simple **Java console-based hotel reservation system** that allows users to:

- Search and view available rooms
- Book hotel rooms (Standard, Deluxe, Suite)
- Cancel reservations
- View booking details
- Simulate payment processing
- Store data using file I/O (`rooms.txt`, `bookings.txt`)

---

##  Features

 Room categorization:
- Standard  
- Deluxe  
- Suite

 Functionalities:
- View available rooms by category
- Book a room with simulated payment
- Cancel an existing booking
- Booking and room data stored in text files
- Object-Oriented Programming structure

---

##  Technologies Used

- Java (JDK 8+)
- OOP (Object-Oriented Programming)
- Console Input/Output
- File I/O using `BufferedReader`, `BufferedWriter`
- Exception handling

---

## How to Run

1. **Create your project folder** and add the following files:
   - `HotelReservationSystem.java`
   - `rooms.txt` (initial room list)
   - `bookings.txt` (can be empty)

2. **Add initial rooms to `rooms.txt`**:
101,Standard,true
102,Standard,true
201,Deluxe,true
202,Deluxe,true
301,Suite,true

markdown
Copy
Edit

3. **Compile and run the project**:
```bash
javac HotelReservationSystem.java
java HotelReservationSystem
📷 Sample Interaction
markdown
Copy
Edit
--- Hotel Reservation System ---
1. View Available Rooms
2. Book Room
3. Cancel Booking
4. Exit
Enter your choice: 1
Enter category (Standard/Deluxe/Suite): Deluxe
Available Deluxe rooms:
Room: 201
Room: 202
yaml
Copy
Edit
Enter your name: Himanshu
Enter room category: Suite
Processing payment for Himanshu (Suite)
Payment successful!
Room 301 booked successfully.
