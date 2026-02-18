package bookingsystem; // Declare the package for organizing classes

import javax.swing.*;       // Import Swing components like JFrame, JButton, JLabel, JOptionPane, etc.
import java.awt.*;          // Import AWT components like Layouts, Colors, Fonts, Dimension, etc.

// ================= MODEL =================
class SeatModel { // Model class to store seat booking data

    private boolean[][] seats = new boolean[10][25]; // 2D array for seat booking: 10 rows x 25 columns, false = available

    public boolean isBooked(int row, int col) { // Method to check if a seat is booked
        return seats[row][col]; // Return true if seat is booked, false if available
    } // End of isBooked method

    public void bookSeat(int row, int col) { // Method to book a seat
        seats[row][col] = true; // Set the seat status to true (booked)
    } // End of bookSeat method

} // End of SeatModel class

// ================= VIEW =================
class SeatView extends JFrame { // View class for GUI

    JButton[][] seatButtons = new JButton[10][25]; // 2D array to hold JButton objects for seats
    String rows = "ABCDEFGHIJ"; // Row labels A-J

    public SeatView() { // Constructor to build GUI

        setTitle("Theater Seating - Booking System"); // Set the window title
        setSize(1400, 700);                          // Set the size of the window
        setDefaultCloseOperation(EXIT_ON_CLOSE);     // Exit program when window is closed
        setLayout(new BorderLayout());               // Use BorderLayout for main layout

        JLabel stage = new JLabel("STAGE", SwingConstants.CENTER); // Create a JLabel for the stage, center text
        stage.setFont(new Font("Arial", Font.BOLD, 28));            // Set font type, style, and size
        stage.setOpaque(true);                                      // Allow background color to be visible
        stage.setBackground(Color.DARK_GRAY);                       // Set background color of stage label
        stage.setForeground(Color.WHITE);                           // Set text color of stage label
        stage.setPreferredSize(new Dimension(100, 60));             // Set preferred size (width ignored in BorderLayout.NORTH)
        add(stage, BorderLayout.NORTH);                             // Add stage label to the top of the window

        JPanel seatingPanel = new JPanel(new GridBagLayout());      // Create a panel for seats using GridBagLayout
        seatingPanel.setBackground(new Color(30, 30, 30));          // Set dark background for the seating panel
        add(seatingPanel, BorderLayout.CENTER);                     // Add seating panel to the center of the window

        GridBagConstraints gbc = new GridBagConstraints();          // Create constraints object for GridBagLayout
        gbc.insets = new Insets(3, 3, 3, 3);                        // Add spacing of 3px around each component

        for (int r = 0; r < 10; r++) { // Loop through each row (0 to 9)

            gbc.gridx = 0; // First column for the row label
            gbc.gridy = r; // Row position in grid
            JLabel rowLabel = new JLabel("Row " + rows.charAt(r)); // Create a label for the row
            rowLabel.setForeground(Color.WHITE);                  // Set label text color to white
            rowLabel.setFont(new Font("Arial", Font.BOLD, 14));  // Set label font style and size
            seatingPanel.add(rowLabel, gbc);                     // Add row label to seating panel

            for (int c = 0; c < 25; c++) { // Loop through each seat in the row (0 to 24)

                gbc.gridx = c + 1; // Column position for seat (shifted by 1 because column 0 is row label)

                JButton btn = new JButton(String.valueOf(c + 1)); // Create a button with seat number as text
                btn.setPreferredSize(new Dimension(40, 40));     // Set size of the button
                btn.setFont(new Font("Arial", Font.BOLD, 12));   // Set font of the button
                btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add a black border to the button
                btn.setOpaque(true);                              // Make button background visible
                btn.setBackground(new Color(0, 180, 0));         // Set green color = available seat

                seatButtons[r][c] = btn;                         // Store the button in 2D array
                seatingPanel.add(btn, gbc);                      // Add the button to seating panel
            } // End of column loop
        } // End of row loop

    } // End of SeatView constructor

} // End of SeatView class

// ================= CONTROLLER =================
class SeatController { // Controller class for user interactions

    private SeatModel model; // Reference to the model object
    private SeatView view;   // Reference to the view object

    public SeatController(SeatModel model, SeatView view) { // Constructor with model and view

        this.model = model; // Assign the model
        this.view = view;   // Assign the view

        for (int r = 0; r < 10; r++) { // Loop through rows
            for (int c = 0; c < 25; c++) { // Loop through columns

                int row = r; // Lambda expression requires final/effectively final variable
                int col = c;

                view.seatButtons[r][c].addActionListener(e -> { // Add action listener to each seat button

                    if (!model.isBooked(row, col)) { // Check if seat is not booked

                        model.bookSeat(row, col); // Mark seat as booked in model
                        JButton btn = view.seatButtons[row][col]; // Get the clicked button
                        btn.setBackground(Color.RED); // Change button color to red (booked)

                        String seatName = (char) ('A' + row) + String.valueOf(col + 1); // Convert row and column to A1, A2, etc.
                        JOptionPane.showMessageDialog(view, // Show confirmation popup
                                "Seat " + seatName + " has been booked!");

                    } else { // If seat is already booked

                        JOptionPane.showMessageDialog(view, // Show warning popup
                                "Seat already booked!");
                    } // End of if-else
                }); // End of addActionListener
            } // End of column loop
        } // End of row loop
    } // End of SeatController constructor

} // End of SeatController class

// ================= MAIN =================
public class BookingSystem { // Main class to start the program

    public static void main(String[] args) { // Main method

        SeatModel model = new SeatModel(); // Create model instance
        SeatView view = new SeatView();    // Create view instance
        new SeatController(model, view);   // Connect controller with model and view

        view.setVisible(true); // Show GUI window
    } // End of main method

} // End of BookingSystem class
