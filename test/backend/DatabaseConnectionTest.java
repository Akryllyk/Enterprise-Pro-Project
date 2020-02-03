/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.sql.Connection;
import org.junit.*;
import java.sql.*;
import static org.junit.Assert.*;

/**
 *
 * @author Spark
 */
public class DatabaseConnectionTest {
    
    private static final String CLASSNAME = "DatabaseConnection";
    
    private static DatabaseConnection dbconn;
    
    public DatabaseConnectionTest() {
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
        
        try {
            dbconn = new DatabaseConnection();
        } catch (Exception e) {
            dbconn = null;
        }
    }
    
    @After
    public void tearDown() {
        System.out.printf("\nTear down test case\n");
    }

    /**
     * Test of GetConnection method, of class DatabaseConnection.
     */
    @Test
    public void testGetConnectionSuccess() {
        System.out.println("Test: GetConnection Success");
        
        Connection result = dbconn.GetConnection();
        
        System.out.printf("Expected outcome: %s\n", "Any non-null value");
        System.out.printf("Actual outcome:   %s\n", result);
        
        try {
            assertNotNull(result);
        } catch (AssertionError e) {
            fail("Cannot connect to the database.");
        }
    }
    
    /**
     * Test of GetConnection method, of class DatabaseConnection.
     */
    @Test
    public void testGetConnectionFailure() {
        System.out.println("Test: GetConnection Failure");
        
        dbconn = new DatabaseConnection("jdbc:mysql://localhost:3306/","","");
        Connection result = dbconn.GetConnection();
        
        System.out.printf("Expected outcome: %s\n", null);
        System.out.printf("Actual outcome:   %s\n", result);
        
        try {
            assertEquals(result, null);
        }
        catch (AssertionError e) {
            fail("Connection successful");
        }
    }
    
}
