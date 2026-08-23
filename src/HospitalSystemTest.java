import org.junit.Test;
import java.io.ByteArrayInputStream;

// JUnit tests for the 5 required features
public class HospitalSystemTest {

    // Helper that sets System.in before creating the object
    private HospitalSystem createSystem(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        return new HospitalSystem();
    }

    // Test registering a new outpatient patient
    @Test
    public void testRegisterPatient() {
        String in = "P001\nJohn\nDoe\n25\nMale\nFlu\n2\n";
        HospitalSystem hs = createSystem(in);
        hs.register();
        assertNotNull(hs.findPatient("P001"));
    }

    // Test searching and finding a registered patient
    @Test
    public void testSearchPatient() {
        String in = "P002\nJane\nDoe\n30\nFemale\nCold\n3\n";
        HospitalSystem hs = createSystem(in);
        hs.register();
        assertEquals("Jane", hs.findPatient("P002").getFirstName());
    }

    // Test updating an existing patients first name
    @Test
    public void testUpdatePatient() {
        String in = "P003\nTom\nSmith\n40\nMale\nFever\n2\nP003\nTommy\nSmith\n41\nMale\nFlu\n";
        HospitalSystem hs = createSystem(in);
        hs.register();
        hs.update();
        assertEquals("Tommy", hs.findPatient("P003").getFirstName());
    }

    // Test deleting a patient and confirming removal
    @Test
    public void testDeletePatient() {
        String in = "P004\nAnn\nLee\n22\nFemale\nCough\n2\nP004\n";
        HospitalSystem hs = createSystem(in);
        hs.register();
        hs.delete();
        assertNull(hs.findPatient("P004"));
    }

    // Test allocating first available bed to inpatient
    @Test
    public void testAllocateBed() {
        String in = "P005\nBob\nJones\n50\nMale\nPain\n1\nP005\n";
        HospitalSystem hs = createSystem(in);
        hs.register();
        hs.allocateBed();
        assertEquals("B01", ((Inpatient)hs.findPatient("P005")).getBedNumber());
    }

    private void assertNotNull(Patient findPatient) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void assertEquals(String jane, String firstName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void assertNull(Patient findPatient) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}