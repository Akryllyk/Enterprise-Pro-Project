/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import org.junit.*;
import java.sql.*;
import java.awt.image.*;
import java.io.*;
import static org.junit.Assert.*;

/**
 *
 * @author Spark
 */
public class DatabaseAccessTest {
    
    private static final String CLASSNAME = "DatabaseAccess";
    
    public DatabaseAccessTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.printf("Setup %s test\n", CLASSNAME);
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.printf("\nTear down %s test\n\n", CLASSNAME);
    }
    
    @Before
    public void setUp() {
        System.out.printf("----------\nPrepare test case\n\n");
    }
    
    @After
    public void tearDown() {
        System.out.printf("\nTear down test case\n");
    }

    /**
     * Test of saveLoginDetails method, of class DatabaseAccess.
     */
    @Test
    public void testSaveLoginDetailsSuccess() {
        System.out.println("Test: saveLoginDetails Success");
        
        String[] details = {"username", "password"};
        boolean result = false;
        try {
            result = DatabaseAccess.saveLoginDetails(details);
        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        
        System.out.printf("Expected outcome: %s\n", true);
        System.out.printf("Actual outcome:   %s\n", result);
        
        try {
            assertTrue(result);
        } catch (AssertionError e) {
            fail("Cannot connect to the database.");
        }
    }
    
    /**
     * Test of checkLoginDetails method, of class DatabaseAccess.
     */
    @Test
    public void testCheckLoginDetails() {
        System.out.println("Test: saveLoginDetails Success");
        
        String[] details = {"username", "password"};
        boolean result = false;
        try {
            result = DatabaseAccess.checkLoginDetails(details);
        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        
        System.out.printf("Expected outcome: %s\n", true);
        System.out.printf("Actual outcome:   %s\n", result);
        
        try {
            assertTrue(result);
        } catch (AssertionError e) {
            fail("Cannot connect to the database.");
        }
    }
    
    /**
     * Test of registerPatientDetails method, of class DatabaseAccess.
     */
    //item tested needs to not be in the array before testing, so deletion of the entry in the database or a change to the patient id is required.
    @Test
    public void testRegisterPatientDetails() {
        System.out.println("Test: registerPatientDetails Success");
        
        boolean result = false;
        try {
            result = DatabaseAccess.registerPatientDetails("Doe", "John", "", "PAT02", new Date(1960, 3, 3), 50, "male", 166, 100, "");
        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        
        System.out.printf("Expected outcome: %s\n", true);
        System.out.printf("Actual outcome:   %s\n", result);
        
        try {
            assertTrue(result);
        } catch (AssertionError e) {
            fail("Issue with details regestering.");
        }
    }
    
    /**
     * Test of queryPatientDetails method, of class DatabaseAccess.
     */
    @Test
    public void testQueryPatientDetails() {
        System.out.println("Test: registerPatientDetails Success");
        
        ResultSet result;
        ResultSetMetaData rsmd;
        int cols = 0;
        int rows = 0;
        
        try {
            result = DatabaseAccess.queryPatientDetails();
            rsmd = result.getMetaData();
            
            cols = rsmd.getColumnCount();
            
            result.last();
            rows = result.getRow();
        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        
        
        // User output
        System.out.printf("Expected outcome: %d\n", 10);
        System.out.printf("Actual outcome:   %d\n", cols);
        
        System.out.printf("Expected outcome: %s\n", "A number of rows > 0");
        System.out.printf("Actual outcome:   %d\n", rows);
        
        try {
            assertEquals(10, cols);
            assertTrue(rows > 0);
        } catch (AssertionError e) {
            fail("Issue with details retrieval.");
        }
    }
    
    /**
     * Test of queryScanTypes method, of class DatabaseAccess.
     */
    @Test
    public void testQueryScanTypes() {
        System.out.println("Test: queryScanTypes Success");
        
        ResultSet result;
        ResultSetMetaData rsmd;
        int cols = 0;
        int rows = 0;
        
        try {
            result = DatabaseAccess.queryScanTypes();
            rsmd = result.getMetaData();
            
            cols = rsmd.getColumnCount();
            
            result.last();
            rows = result.getRow();
        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        
        
        // User output
        System.out.printf("Expected outcome: %d\n", 1);
        System.out.printf("Actual outcome:   %d\n", cols);
        
        System.out.printf("Expected outcome: %s\n", "A number of rows > 0");
        System.out.printf("Actual outcome:   %d\n", rows);
        
        try {
            assertEquals(1, cols);
            assertTrue(rows > 0);
        } catch (AssertionError e) {
            fail("Issue with scan types retrieval.");
        }
    }
    
    /**
     * Test of queryScanTypes method, of class DatabaseAccess.
     */
    @Test
    public void testQueryScanParameters() {
        System.out.println("Test: queryScanParameters Success");
        
        ResultSet resultNone, resultSchedule;
        ResultSetMetaData metaNone, metaSchedule;
        int colsNone = 0, colsSchedule = 0;
        int rowsNone = 0, rowsSchedule = 0;
        
        try {
            resultNone = DatabaseAccess.queryScanParameters();
            resultSchedule = DatabaseAccess.queryScanParameters("PD Axial FS");
            metaNone = resultNone.getMetaData();
            metaSchedule = resultSchedule.getMetaData();
            
            colsNone = metaNone.getColumnCount();
            colsSchedule = metaSchedule.getColumnCount();
            
            resultNone.last();
            resultSchedule.last();
            rowsNone = resultNone.getRow();
            rowsSchedule = resultSchedule.getRow();
        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        
        
        // User output
        System.out.println("No parameters");
        System.out.printf("Expected outcome: %d\n", 15);
        System.out.printf("Actual outcome:   %d\n", colsNone);
        
        System.out.printf("Expected outcome: %s\n", "A number of rows > 0");
        System.out.printf("Actual outcome:   %d\n", rowsNone);
        
        System.out.println("Schedule parameter");
        System.out.printf("Expected outcome: %d\n", 15);
        System.out.printf("Actual outcome:   %d\n", colsSchedule);
        
        System.out.printf("Expected outcome: %s\n", "A number of rows > 0");
        System.out.printf("Actual outcome:   %d\n", rowsSchedule);
        
        try {
            assertEquals(15, colsNone);
            assertTrue(rowsNone > 0);
            assertEquals(15, colsSchedule);
            assertTrue(rowsSchedule > 0);
        } catch (AssertionError e) {
            fail("Issue with scan parameters retrieval.");
        }
    }
    
    /**
     * Test of queryScanTypes method, of class DatabaseAccess.
     */
    @Test
    public void testGetImages() {
        System.out.println("Test: getImages Success");
        BufferedImage[] images;
        
        try {
            images = DatabaseAccess.getImages("PD Axial FS");
        } catch (IOException ex) {
            images = new BufferedImage[0];
        }
        
        // User output
        System.out.printf("Expected outcome: %s\n", "Array of length > 0");
        System.out.printf("Actual outcome:   %d\n", images.length);
        
        try {
            assertTrue(images.length > 0);
        } catch (AssertionError e) {
            fail("Issue with image retrieval.");
        }
    }
    
}
