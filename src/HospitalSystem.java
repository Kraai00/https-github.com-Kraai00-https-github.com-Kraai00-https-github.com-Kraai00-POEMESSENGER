import java.util.*;

// Main class with menu, ArrayList, and 2D bed array
public class HospitalSystem {
    ArrayList<Patient> patients = new ArrayList<>();
    String[][] beds = new String[4][5]; // 4x5 grid = 20 beds
    Scanner sc = new Scanner(System.in);

    // Fill bed array with null to mark all as empty
    public HospitalSystem() {
        for (String[] row : beds) Arrays.fill(row, null);
    }

    // Menu loop with try-catch for exception handling
    public void run() {
        while (true) {
            System.out.println("\n1.Register 2.Search 3.Update 4.Delete 5.All Patients");
            System.out.println("6.Allocate Bed 7.Release Bed 8.Layout 9.Available 10.Occupied 11.Reports 0.Exit");
            System.out.print("Choice: ");
            int c = sc.nextInt(); sc.nextLine();
            try {
                switch (c) {
                    case 1: register(); break;
                    case 2: search(); break;
                    case 3: update(); break;
                    case 4: delete(); break;
                    case 5: displayAll(); break;
                    case 6: allocateBed(); break;
                    case 7: releaseBed(); break;
                    case 8: wardLayout(); break;
                    case 9: availableBeds(); break;
                    case 10: occupiedBeds(); break;
                    case 11: reports(); break;
                    case 0: return;
                    default: System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Read input and register patient based on category chosen
    public void register() {
        System.out.print("ID: "); String id = sc.nextLine();
        if (findPatient(id) != null) throw new IllegalArgumentException("ID already exists.");
        System.out.print("First Name: "); String fn = sc.nextLine();
        System.out.print("Last Name: "); String ln = sc.nextLine();
        System.out.print("Age: "); int age = sc.nextInt(); sc.nextLine();
        System.out.print("Gender: "); String gen = sc.nextLine();
        System.out.print("Condition: "); String cond = sc.nextLine();
        System.out.print("Category (1.Inpatient 2.Outpatient 3.Emergency): ");
        int cat = sc.nextInt(); sc.nextLine();
        PatientCategory pc = PatientCategory.values()[cat - 1];
        if (pc == PatientCategory.INPATIENT) {
            patients.add(new Inpatient(id, fn, ln, age, gen, cond, 1, null));
        } else {
            patients.add(new Patient(id, fn, ln, age, gen, cond, pc));
        }
        System.out.println("Patient registered successfully.");
    }

    // Loop through ArrayList to find patient by ID
    public Patient findPatient(String id) {
        for (Patient p : patients) if (p.getId().equals(id)) return p;
        return null;
    }

    // Search for one patient and display their details
    public void search() {
        System.out.print("Enter Patient ID: ");
        Patient p = findPatient(sc.nextLine());
        if (p != null) p.displayDetails();
        else System.out.println("Patient not found.");
    }

    // Update patient fields using setter methods
    public void update() {
        System.out.print("Enter Patient ID: "); Patient p = findPatient(sc.nextLine());
        if (p == null) { System.out.println("Not found."); return; }
        System.out.print("New First Name: "); p.setFirstName(sc.nextLine());
        System.out.print("New Last Name: "); p.setLastName(sc.nextLine());
        System.out.print("New Age: "); p.setAge(sc.nextInt()); sc.nextLine();
        System.out.print("New Gender: "); p.setGender(sc.nextLine());
        System.out.print("New Condition: "); p.setCondition(sc.nextLine());
        System.out.println("Patient updated.");
    }

    // Delete patient and free their bed if they are inpatient
    public void delete() {
        System.out.print("Enter Patient ID: "); Patient p = findPatient(sc.nextLine());
        if (p == null) { System.out.println("Not found."); return; }
        if (p instanceof Inpatient) releaseBedByPatient(p.getId());
        patients.remove(p);
        System.out.println("Patient deleted.");
    }

    // Display all patients using an enhanced for loop
    public void displayAll() {
        if (patients.isEmpty()) { System.out.println("No patients."); return; }
        for (Patient p : patients) { p.displayDetails(); System.out.println(); }
    }

    // Find first free bed and assign to inpatient
    public void allocateBed() {
        System.out.print("Enter Inpatient ID: "); String id = sc.nextLine();
        Patient p = findPatient(id);
        if (p == null || !(p instanceof Inpatient)) throw new IllegalArgumentException("Not an inpatient.");
        if (getPatientBed(id) != null) throw new IllegalArgumentException("Bed already allocated.");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (beds[i][j] == null) {
                    beds[i][j] = id;
                    String bedNum = "B" + String.format("%02d", i * 5 + j + 1);
                    ((Inpatient) p).setBedNumber(bedNum);
                    System.out.println("Allocated " + bedNum);
                    return;
                }
            }
        }
        throw new IllegalStateException("No beds available.");
    }

    // Return bed number string for a given patient ID
    public String getPatientBed(String id) {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 5; j++)
                if (id.equals(beds[i][j])) return "B" + String.format("%02d", i * 5 + j + 1);
        return null;
    }

    // Free bed by patient ID when patient is deleted
    public void releaseBedByPatient(String id) {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 5; j++)
                if (id.equals(beds[i][j])) { beds[i][j] = null; return; }
    }

    // Release bed by bed number like B01
    public void releaseBed() {
        System.out.print("Enter Bed Number (e.g., B01): "); String bed = sc.nextLine();
        int num = Integer.parseInt(bed.substring(1)) - 1;
        int row = num / 5, col = num % 5;
        if (beds[row][col] == null) { System.out.println("Bed already free."); return; }
        Patient p = findPatient(beds[row][col]);
        if (p instanceof Inpatient) ((Inpatient) p).setBedNumber(null);
        beds[row][col] = null;
        System.out.println("Bed released.");
    }

    // Print 4x5 ward layout showing free or occupied
    public void wardLayout() {
        System.out.println("Ward Layout:");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                String b = "B" + String.format("%02d", i * 5 + j + 1);
                String s = (beds[i][j] == null) ? "FREE" : "OCCUPIED";
                System.out.print(b + "[" + s + "] ");
            }
            System.out.println();
        }
    }

    // Show available beds using nested loops
    public void availableBeds() {
        System.out.print("Available: ");
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 5; j++)
                if (beds[i][j] == null) System.out.print("B" + String.format("%02d", i * 5 + j + 1) + " ");
        System.out.println();
    }

    // Show occupied beds with patient ID in brackets
    public void occupiedBeds() {
        System.out.print("Occupied: ");
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 5; j++)
                if (beds[i][j] != null) System.out.print("B" + String.format("%02d", i * 5 + j + 1) + "(" + beds[i][j] + ") ");
        System.out.println();
    }

    // Report totals and occupancy percentage
    public void reports() {
        int occ = 0;
        for (int i = 0; i < 4; i++) for (int j = 0; j < 5; j++) if (beds[i][j] != null) occ++;
        System.out.println("Total Patients: " + patients.size());
        System.out.println("Occupied Beds: " + occ);
        System.out.printf("Occupancy: %.2f%%\n", (occ / 20.0) * 100);
    }

    // Main method creates object and starts menu
    public static void main(String[] args) {
        new HospitalSystem().run();
    }
}