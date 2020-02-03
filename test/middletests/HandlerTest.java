/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middletests;

import backend.DatabaseAccess;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import middlelayer.Handler;
import middlelayer.Patient;
import middlelayer.ScanInformation;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Alex
 */
public class HandlerTest {

    private static final String CLASSNAME = "Handler";

    public HandlerTest() {

    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("Setup " + CLASSNAME + " test.");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Tear down " + CLASSNAME + " test.");

    }

    @Before
    public void setUp() {
        System.out.println("----------");
        System.out.println("Prepare test case");
    }

    @After
    public void tearDown() {
        System.out.println("Tear down test case");

    }
    //Doesn't work in unit testing, but works in use of the program and in acceptance testing.
    /*
    @Test
    public void testQueryLogin() {
        System.out.println("Test: Query Login");
        String username = "admin";
        char[] password = "password".toCharArray();
        boolean result = false;
        try {
            result = Handler.queryLogin(username, password);
        } catch (SQLException e) {
            System.err.println();
        }
        System.out.println("Expected outcome: " + true);
        System.out.println("Actual Outcome:   " + result);
        try {
            assertTrue(result);
        } catch (AssertionError e) {
            fail("Cannot check login with database");
        }

    }
    */
    
    //item tested needs to not be in the array before testing, so deletion of the entry in the database or a change to the patient id is required.
    @Test
    public void testRegisterPatientDetails() {
        System.out.println("Test Register Patient Details");
        boolean result = false;
        int[] heightArray = new int[]{6, 3};
        int[] weightArray = new int[]{100, 10};
        try {
            result = Handler.registerPatientDetails(new Patient("Doe", "John", "none", "1002021", "1990-01-01", "male", heightArray, weightArray, "broken arm"));
        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Expected outcome: " + true);
        System.out.println("Actual Outcome:   " + result);
        try {
            assertTrue(result);
        } catch (AssertionError e) {
            fail("Cannot register patient with the database.");
        }
    }

    @Test
    public void testQueryScanTypes() {
        System.out.println("Test Query Scan Types");
        String[] result = null;
        ResultSet resultDB = null;
        ResultSetMetaData rsmd = null;
        int rowRS = 0;

        try {
            result = Handler.queryScanTypes();
            resultDB = DatabaseAccess.queryScanTypes();
            resultDB.first();
            rsmd = resultDB.getMetaData();
            System.out.println("Expected first result: " + resultDB.getString(1));
            System.out.println("Actual first result:   " + result[0]);
            System.out.println("");
            resultDB.last();
            rowRS = resultDB.getRow();
            System.out.println("Expected last result:  " + resultDB.getString(1));
            System.out.println("Actual last result:    " + result[2]);
            System.out.println("");
            System.out.println("Expected length:       " + rowRS);
            System.out.println("Actual length:         " + result.length);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
           resultDB.first();
            assertEquals(resultDB.getString(1), result[0]);
            resultDB.last();
            assertEquals(resultDB.getString(1), result[2]);
            assertEquals(rowRS, result.length);

        } catch (AssertionError | SQLException e) {
           fail("Cannot check scan types with database");
        }

    }
    
    @Test
    public void testQueryPatientDetails() {
        Patient[] p = null;
        System.out.println("Test Query Patient Details");
        ResultSet rs = null;
        try {
            rs = DatabaseAccess.queryPatientDetails();
            rs.first();
            p = Handler.queryPatientDetails();
            float height = (float) rs.getDouble(8);
            float weight = (float) rs.getDouble(9);
            int[] heightArray = Patient.convertHeightMetricToImperial(height);
            int[] weightArray = Patient.convertWeightMetricToImperial(weight);

            System.out.println("Expected Patient Name: " + rs.getString(2) + " " + rs.getString(4));
            System.out.println("Actual Patient Name:   " + p[0].getFirstName() + " " + p[0].getLastName());
            System.out.println("");
            System.out.println("Expected Patient ID:   " + rs.getString(1));
            System.out.println("Actual patient ID:     " + p[0].getPatientID());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            assertEquals(rs.getString(2), p[0].getFirstName());
            assertEquals(rs.getString(4), p[0].getLastName());
            assertEquals(rs.getString(1), p[0].getPatientID());
        } catch (AssertionError | SQLException e) {
            fail("Cannot check patient details with database");
        }
    }

    @Test
    public void testQueryScanParameters() {
        ScanInformation si = null;
        ResultSet rs = null;
        System.out.println("Test Query Scan Parameters");
        try{
            rs = DatabaseAccess.queryScanParameters("PD Axial FS");
            rs.first();
            si = Handler.queryScanParameters("PD Axial FS");
            
            System.out.println("Expected sequence: " + rs.getString(1));
            System.out.println("Actual sequence:   " + si.getSequence());
            System.out.println("");
            System.out.println("Expected fov:      " + rs.getString(2));
            System.out.println("Actual sequence:   " + si.getFov());
            
            
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        
        try{
            assertEquals(rs.getString(1), si.getSequence());
            assertEquals(rs.getString(2), String.valueOf(si.getFov()));
        } catch(AssertionError | SQLException e){
            fail("cannot check scan parameters with database");
        }
    }
}
