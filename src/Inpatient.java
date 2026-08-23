// Inpatient extends Patient and adds ward and bed info
public class Inpatient extends Patient {
    private int wardNumber;
    private String bedNumber;

    // super() calls parent constructor to set inherited fields
    public Inpatient(String id, String firstName, String lastName, int age,
                     String gender, String condition, int wardNumber, String bedNumber) {
        super(id, firstName, lastName, age, gender, condition, PatientCategory.INPATIENT);
        this.wardNumber = wardNumber;
        this.bedNumber = bedNumber;
    }

    public int getWardNumber() { return wardNumber; }
    public void setWardNumber(int wardNumber) { this.wardNumber = wardNumber; }
    public String getBedNumber() { return bedNumber; }
    public void setBedNumber(String bedNumber) { this.bedNumber = bedNumber; }

    // Override to add ward and bed info when displaying
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Ward: " + wardNumber + ", Bed: " + bedNumber);
    }
}