// Base Patient class with private fields and getters/setters
public class Patient {

    private final String id;
    private String firstName, lastName, gender, condition;
    private int age;
    private final PatientCategory category;

    // Constructor sets all values using this keyword
    public Patient(String id, String firstName, String lastName, int age,
                   String gender, String condition, PatientCategory category) {
        this.id = id; this.firstName = firstName; this.lastName = lastName;
        this.age = age; this.gender = gender; this.condition = condition;
        this.category = category;
    }

    // Getters and setters for information hiding
    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
    public PatientCategory getCategory() { return category; }

    // Display method shows all patient details in console
    public void displayDetails() {
        System.out.println("ID: " + id + ", Name: " + firstName + " " + lastName +
            ", Age: " + age + ", Gender: " + gender + ", Condition: " + condition +
            ", Category: " + category);
    }
}