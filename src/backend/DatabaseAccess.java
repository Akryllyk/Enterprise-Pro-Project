/**
 * @author Ben Y
 */
package backend;

import java.sql.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;

/**
 * Class for accessing the database
 */
public class DatabaseAccess {
    
    private static final String DBNAME = "";
    private static final String URL = "jdbc:mysql://lamp.scim.brad.ac.uk:3306/".concat(DBNAME);
    private static final String USERNAME = "";
    private static final String PASSWORD = "";
    private static final String ASSETS = "assets";

    /**
     * Method to save login details into the database
     *
     * @param details A 2 index array, with the first value being the username,
     * and the second being the password.
     * @return a boolean indicating the success of the details saving.
     * @throws ArrayIndexOutOfBoundsException if the array passed does not have
     * data in the first 2 indexes.
     * @throws SQLException If the SQL statement fails.
     */
    public static boolean saveLoginDetails(String[] details) throws ArrayIndexOutOfBoundsException, SQLException {

        String username = details[0];
        String password = details[1];

        DatabaseConnection connection = new DatabaseConnection(URL, USERNAME, PASSWORD);
        Connection conn = connection.GetConnection();

        String saveLoginStatement = "INSERT INTO `enp_login` (username, encryptPassword) VALUES (?, ?);";

        PreparedStatement saveLogin = conn.prepareStatement(saveLoginStatement);
        saveLogin.setString(1, username);
        saveLogin.setString(2, password);

        int rows = saveLogin.executeUpdate();
        return rows == 1;
    }

    /**
     * Method to check if the entered login details are correct.
     *
     * @param details A 2 index array, with the first value being the username,
     * and the second being the password.
     * @return a boolean indicating whether the entered login details is correct
     * or not.
     * @throws ArrayIndexOutOfBoundsException if the array passed does not have
     * at least 2 filled indexes.
     * @throws SQLException If the SQL statement fails.
     */
    public static boolean checkLoginDetails(String[] details) throws ArrayIndexOutOfBoundsException, SQLException {
        String username = details[0];
        String password = details[1];

        DatabaseConnection connection = new DatabaseConnection(URL, USERNAME, PASSWORD);

        if (connection.GetConnection() != null) {
            Connection conn = connection.GetConnection();

            String getLoginStatement = "SELECT encryptPassword FROM `enp_login` WHERE username = ?;";
            PreparedStatement getLogin = conn.prepareStatement(getLoginStatement);
            getLogin.setString(1, username);

            ResultSet results = getLogin.executeQuery();

            return (results.first()
                    /*&& (results.getString("username").equals(username)*/
                    && results.getString("encryptPassword").equals(password));
        }
        return false;
    }

    /**
     * Adds the specified patient details to the database.
     * @param lastName The last name of the patient.
     * @param firstName The first name of the patient.
     * @param middleName (Optional) The middle name(s) of the patient. Enter and empty string if not applicable.
     * @param patientID The ID of the patient.
     * @param dateOfBirth The patient's DOB, in SQLDate format (YYYY-MM-DD).
     * @param age The patient's age, in years.
     * @param gender The patient's gender (male, female or other).
     * @param height The patient's height, in centimetres.
     * @param weight The patient's weight, in kilograms.
     * @param additionalInfo Any additional information about the patient to be stored.
     * @return boolean of if the data was stored successfully or not.
     * @throws SQLException If the SQL statement fails.
     */
    public static boolean registerPatientDetails(String lastName, String firstName, String middleName, String patientID, Date dateOfBirth, int age, String gender, int height, int weight, String additionalInfo) throws SQLException {

        DatabaseConnection connection = new DatabaseConnection(URL, USERNAME, PASSWORD);
        Connection conn = connection.GetConnection();

        String saveLoginStatement = "INSERT INTO `enp_patient` (patientID, firstName, middleName, lastName, dateOfBirth, sex, age, height, weight, additionalInfo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement savePatient = conn.prepareStatement(saveLoginStatement);

        savePatient.setString(1, patientID);
        savePatient.setString(2, firstName);
        savePatient.setString(3, middleName);
        savePatient.setString(4, lastName);
        savePatient.setDate(5, dateOfBirth);
        savePatient.setString(6, gender);
        savePatient.setInt(7, age);
        savePatient.setDouble(8, height);
        savePatient.setDouble(9, weight);
        savePatient.setString(10, additionalInfo);

        int rows = savePatient.executeUpdate();
        return rows == 1;

    }

    /**
     * Retrieves all patient data from the database.
     * @return ResultSet of the patient data.
     * @throws SQLException If the SQL statement fails or if an empty result set is returned.
     */
    public static ResultSet queryPatientDetails() throws SQLException {

        DatabaseConnection connection = new DatabaseConnection(URL, USERNAME, PASSWORD);
        Connection conn = connection.GetConnection();

        String getPatientStatement = "SELECT * FROM `enp_patient`;";

        PreparedStatement getPatient = conn.prepareStatement(getPatientStatement);

        ResultSet results = getPatient.executeQuery();

        return results;

    }

    /**
     * Retrieves all scan types from the database. 
     * @return ResultSet of the scan types, in alphabetical order.
     * @throws SQLException If the SQL statement fails or if an empty result set is returned.
     */
    public static ResultSet queryScanTypes() throws SQLException {

        DatabaseConnection connection = new DatabaseConnection(URL, USERNAME, PASSWORD);
        Connection conn = connection.GetConnection();

        String getPatientStatement = "SELECT * FROM `enp_scanTypes` ORDER BY `scanType` ASC;";

        PreparedStatement getPatient = conn.prepareStatement(getPatientStatement);

        ResultSet results = getPatient.executeQuery();

        return results;

    }

    /**
     * Retrieves all parameters from the database.
     * @return ResultSet of the scan parameters, sorted by ID.
     * @throws SQLException If the SQL statement fails or if an empty result set is returned.
     */
    public static ResultSet queryScanParameters() throws SQLException {

        DatabaseConnection connection = new DatabaseConnection(URL, USERNAME, PASSWORD);
        Connection conn = connection.GetConnection();

        String getParametersStatement = "SELECT * FROM `enp_parameters`;";

        PreparedStatement getParameters = conn.prepareStatement(getParametersStatement);

        ResultSet results = getParameters.executeQuery();

        return results;

    }

    /**
     * Retrieves all parameters from the database for the entered sequence name
     * @param sequence String corresponding to the sequence name relating to the parameters.
     * @return ResultsSet containing the corresponding parameters.
     * @throws SQLException If the SQL statement fails or if an empty result set is returned.
     */
    public static ResultSet queryScanParameters(String sequence) throws SQLException {
        
        DatabaseConnection connection = new DatabaseConnection(URL, USERNAME, PASSWORD);
        Connection conn = connection.GetConnection();
        
        String getParametersStatement = "SELECT * from `enp_parameters` WHERE `sequence`= ?;";
        
        PreparedStatement getParameters = conn.prepareStatement(getParametersStatement);
        getParameters.setString(1, sequence);
        
        ResultSet results = getParameters.executeQuery();
        
        return results;

    }
    
    /**
     * Retrieves the images from the folder specified in ASSETS.
     * @param schedule the name of the group of images to use
     * @return an image list of the images found
     * @throws IOException If there is an issue reading the files
     */
    public static BufferedImage[] getImages(String schedule) throws IOException{
        
        File assetsFolder = new File(String.format("%s\\%s", ASSETS, schedule));
        String[] files = assetsFolder.list();
        Arrays.sort(files);
        BufferedImage[] images = new BufferedImage[files.length];
        
        for (int i = 0; i < files.length; i++) {
            BufferedImage img = ImageIO.read(new File(String.format("%s\\%s\\%s", ASSETS, schedule, files[i])));
            images[i] = img;
        }
        
        return images;
    }

}
