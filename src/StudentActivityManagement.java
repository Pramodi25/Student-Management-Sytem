import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// This is the main class for the student activity management system
public class StudentActivityManagement {

    // Static variables to store the capacity, number of students, and student array
    static int capacity = 100;
    static int numStudents = 0;
    static Student[] students = new Student[100];

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int choice;

        // Do-while loop to continuously display the menu until the user chooses to exit
        do {
            System.out.println();  // This adds a blank line
            System.out.println("--Menu (If you have already entered the student details, please load the details from the file to system before use other options 'Enter - 6'--");
            System.out.println();  // This adds a blank line
            System.out.println("1. Check Available Seats");
            System.out.println("2. Register student (With ID)");
            System.out.println("3. Delete student");
            System.out.println("4. Find student (With Student ID)");
            System.out.println("5. Save student details into a file");
            System.out.println("6. Load student details from the file to the system");
            System.out.println("7. View the list of students based on their names");
            System.out.println("8. Add marks for the modules and generate a report");
            System.out.println("9. Exit");
            System.out.println();  // This adds a blank line
            System.out.print("Enter your choice: ");
            choice = input.nextInt();

            // Switch-case statement to handle different user choices
            switch (choice) {
                case 1:
                    checkAvailableSeats();
                    break;
                case 2:
                    registerStudent();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    findStudent();
                    break;
                case 5:
                    storeStudentDetails();
                    break;
                case 6:
                    loadStudentDetails();
                    break;
                case 7:
                    viewStudents();
                    break;
                case 8:
                    addMarks();
                    break;
                case 9:
                    System.out.println("Exiting the system...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 9);
    }

    // Method to check available seats
    private static void checkAvailableSeats() {
        System.out.println("Available seats: " + (capacity - numStudents));
    }

    // Method to register a new student
    public static void registerStudent() {
        if (numStudents == capacity) {
            System.out.println("No available seats.");
            return;
        }

        Scanner input = new Scanner(System.in);
        System.out.println("Enter student ID (Start with 'w' and use 7 numbers): ");
        String id = input.next();

        // Check if the entered ID matches the pattern
        if (!isValidId(id)) {
            System.out.println("Invalid ID format. Please enter an ID starting with 'w' followed by 7 numbers.");
            return; // Exit the method if the ID format is incorrect
        }

        // Check if the ID already exists
        for (int i = 0; i < numStudents; i++) {
            if (students[i].getId().equals(id)) {
                System.out.println("The Student ID already exists.");
                return; // Exit the method if the ID already exists
            }
        }

        System.out.println("Enter student name: ");
        String name = input.next();
        System.out.println();  // This adds a blank line
        System.out.println("Student registered successfully!!!");
        System.out.println();  // This adds a blank line

        Student student = new Student(id, name, new Module[] {new Module(0, 0, 0)}); // Create a new student object
        students[numStudents] = student;
        numStudents++;
    }

    // Method to validate the student ID format
    private static boolean isValidId(String id) {
        if (!id.startsWith("w") || id.length() != 8) {
            return false;
        }
        for (int i = 1; i < id.length(); i++) {
            if (!Character.isDigit(id.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // Method to delete a student
    public static void deleteStudent() {
        Scanner input = new Scanner(System.in); // Create a Scanner object to read user input
        System.out.println("Enter student ID to delete: ");
        String id = input.next(); // Read the student ID from the user

        for (int i = 0; i < numStudents; i++) {
            if (students[i].getId().equals(id)) {
                // Shift elements to the left to delete the student
                for (int j = i; j < numStudents - 1; j++) {
                    students[j] = students[j + 1];
                }
                numStudents--; // Decrement the number of students
                System.out.println("Student deleted successfully!");
                return;
            }
        }

        System.out.println("Student not found!");
    }

    // Method to find a student by ID
    public static void findStudent() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter student ID: ");
        String id = input.next();

        for (int i = 0; i < numStudents; i++) {
            if (students[i].getId().equals(id)) {
                System.out.println("Student found!");
                System.out.println("ID: " + students[i].getId() +", Name: " + students[i].getName());
                return;
            }
        }

        System.out.println("Student not found!");
    }

    // Method to store student details in a file
    public static void storeStudentDetails() {
        try {
            File file = new File("students.txt");
            FileWriter writer = new FileWriter(file);

            for (int i = 0; i < numStudents; i++) {
                writer.write(students[i].getId() + "," + students[i].getName() + "," + students[i].getModules()[0].getMark1() + "," + students[i].getModules()[0].getMark2() + "," + students[i].getModules()[0].getMark3() + "\n");
            }

            writer.close();
            System.out.println("Student details stored to file successfully.");
        } catch (IOException e) {
            System.out.println("Error storing student details to file: " + e.getMessage());
        }
    }

    // Method to load student details from a file
    public static void loadStudentDetails() {
        try {
            students = new Student[capacity];
            numStudents = 0;
            File file = new File("students.txt"); // Create a File object
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) { // Read the file line by line
                String line = input.nextLine();
                String[] details = line.split(","); // Split the line by comma

                String id = details[0]; // Extract the ID, name, and marks from the line
                String name = details[1];
                // Extract the marks and convert them to integers
                int mark1 = Integer.parseInt(details[2]);
                int mark2 = Integer.parseInt(details[3]);
                int mark3 = Integer.parseInt(details[4]);

                Module module = new Module(mark1, mark2, mark3);
                Student student = new Student(id, name, new Module[] {module});
                students[numStudents] = student;
                numStudents++;
            }

            input.close();
            System.out.println("Student details loaded from file successfully.");
        } catch (IOException e) {
            System.out.println("Error loading student details: " + e.getMessage());
        }
    }


    // Method to view students based on their names
    public static void viewStudents() {

        // Bubble sort students array based on names
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < numStudents - 1; i++) {
                if (students[i].getName().compareTo(students[i + 1].getName()) > 0) {
                    Student temp = students[i];
                    students[i] = students[i + 1];
                    students[i + 1] = temp;
                    swapped = true;
                }
            }
        } while (swapped);

        for (int i = 0; i < numStudents; i++) {
            System.out.println("Name : " + students[i].getName());
            System.out.println("Module marks: ");
            System.out.println(" Module 1 - " + students[i].getModules()[0].getMark1());
            System.out.println(" Module 2 - " + students[i].getModules()[0].getMark2());
            System.out.println(" Module 3 - " + students[i].getModules()[0].getMark3());
            System.out.println();  // This adds a blank line
        }
    }

    // Method to add marks for the modules
    public static void addMarks() {
        Scanner input = new Scanner(System.in);
        boolean addMore = true;

        // Loop until the user decides to stop adding marks
        while(addMore) {
            System.out.println("Enter student ID to add marks: ");
            String id = input.next();

            boolean studentFound = false;

            // Search for the student with the entered ID
            for (int i = 0; i < numStudents; i++) {
                if (students[i].getId().equals(id)) {

                    // If the student is found, prompt the user to enter marks for each module
                    System.out.println("Enter marks for Module 1: ");
                    int mark1 = input.nextInt();
                    System.out.println("Enter marks for Module 2: ");
                    int mark2 = input.nextInt();
                    System.out.println("Enter marks for Module 3: ");
                    int mark3 = input.nextInt();

                    // Update the marks for the student's module
                    students[i].getModules()[0].setMark1(mark1);
                    students[i].getModules()[0].setMark2(mark2);
                    students[i].getModules()[0].setMark3(mark3);
                    System.out.println("Marks added successfully!");

                    studentFound = true;

                    //Ask the user if they want to add marks for another student
                    System.out.println("Do you want to add marks for another student? (yes/no)");
                    String choice = input.next();

                    if (choice.equalsIgnoreCase("no")) {
                        addMore = false;
                    }
                    break;
                }
            }

            //If the student is not found, display an error message and ask if the user wants to add marks for another student
            if (!studentFound) {
                System.out.println("Student not found!");
                System.out.println("Do you want to add marks for another student? (yes/no)");
                String choice = input.next();

                if (choice.equalsIgnoreCase("no")) {
                    addMore = false;
                }
            }
        }

        // Submenu to generate reports
        String choice;
        do {
            // Display the submenu options
            System.out.println("SubMenu");
            System.out.println("C. Generate detailed Report");
            System.out.println("D. Generate Summary");
            System.out.println("0. Exit");

            System.out.println("Enter your choice: ");

            // Get the user's choice
            choice = input.next();
            switch (choice) {
                case "c":
                    generateReport();
                    break;
                case "d":
                    generateSummary();
                    break;
                case "0":
                    System.out.println("Exiting the system...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }while (!choice.equals("0"));
    }


    // Method to generate a detailed report
    static void generateReport() {
        // Check if there are any students registered
        if (numStudents == 0) {
            System.out.println("No students registered yet.");
            return;
        }

        // Implement bubble sort to sort students by average marks in descending order
        for (int i = 0; i < numStudents; i++) {
            for (int j = i + 1; j < numStudents; j++) {

                // Calculate the average marks for each student
                double avg1 = (students[i].getModules()[0].getMark1() + students[i].getModules()[0].getMark2() + students[i].getModules()[0].getMark3()) / 3.0;
                double avg2 = (students[j].getModules()[0].getMark1() + students[j].getModules()[0].getMark2() + students[j].getModules()[0].getMark3()) / 3.0;
                if (avg1 < avg2) {
                    // Swap student objects
                    Student temp = students[i];
                    students[i] = students[j];
                    students[j] = temp;
                }
            }
        }
        System.out.println("\nStudent Report (Sorted by Average Marks):");
        System.out.println("-----------------------------------------");
        for (int i = 0; i < numStudents; i++) {

            // Get the marks for each module
            int mark1 = students[i].getModules()[0].getMark1();
            int mark2 = students[i].getModules()[0].getMark2();
            int mark3 = students[i].getModules()[0].getMark3();

            double totalMarks;
            double average;
            String grade;

            //Calculate the total marks, average, and grade
            if (mark1 == 0 && mark2 == 0 && mark3 == 0) {
                totalMarks = 0;
                average = 0;
                grade = "No Marks";
            } else {
                totalMarks = mark1 + mark2 + mark3;
                average = totalMarks / 3.0;
                grade = calculateGrade(average);
            }

            //Print Student details
            System.out.println("ID: " + students[i].getId() + ", Name: " + students[i].getName());
            System.out.println("Module 1: " + students[i].getModules()[0].getMark1());
            System.out.println("Module 2: " + students[i].getModules()[0].getMark2());
            System.out.println("Module 3: " + students[i].getModules()[0].getMark3());
            System.out.println("Total Marks: " + totalMarks);
            System.out.println("Average Marks: " + average);
            System.out.println("Grade: " + grade);
            System.out.println();
        }
    }

    // Method to calculate the grade based on the average marks
    static String calculateGrade(double average) {
        if (average >= 80) {
            return "Distinction";
        } else if (average >= 70) {
            return "Merit";
        } else if (average >= 40) {
            return "Pass";
        } else {
            return "Fail";
        }
    }

    //Method to generate a summary report
    static void generateSummary() {

        //Check if there are any students registered
        if (numStudents == 0) {
            System.out.println("No students registered yet.");
            return;
        }

        int above40Count = 0;

        System.out.println("\nSystem Summary:");
        System.out.println("-----------------");
        System.out.println("Total Student Registrations: " + numStudents);

        // Count the number of students with 40+ marks in all modules
        for (int i = 0; i < numStudents; i++) {
            Module[] modules = students[i].getModules();
            boolean allAbove40 = true;
            for (Module module : modules) {
                if (module.getMark1() < 40 || module.getMark2() < 40 || module.getMark3() < 40) {
                    allAbove40 = false;
                    break;
                }
            }
            // If all marks in all modules are above 40, increment the count
            if (allAbove40) {
                above40Count++;
            }
        }
        System.out.println("Total Students with 40+ Marks in all Modules: " + above40Count);
    }
}




