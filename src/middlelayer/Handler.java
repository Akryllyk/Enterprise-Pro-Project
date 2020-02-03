/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlelayer;

import backend.DatabaseAccess;
import exceptions.NoResultSetFoundException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Alex
 */
public class Handler {

    private static final int SCAN_INFORMATION = 11;
    private static final int PATIENT = 10;

    /**
     * Converts a result set to an array of objects.
     *
     * @param rs is the result set being converted to the array
     * @param objectType is the type of object being converted
     * @return is the array being returned.
     * @throws java.sql.SQLException because the result set needs it.
     */
    public static Object[] convertToObject(ResultSet rs, int objectType) throws SQLException {
        rs.last();
        int length = rs.getRow();
        rs.first();
        switch (objectType) {
            case SCAN_INFORMATION:
                ScanInformation[] si = new ScanInformation[length];
                for (int i = 0; i < length; i++) {
                    si[i] = new ScanInformation(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11), rs.getInt(12), rs.getString(13), rs.getInt(14), rs.getDouble(15));
                }
                return si;
            case PATIENT:
                Patient[] pi = new Patient[length];
                for (int i = 0; i < length; i++) {
                    float height = (float) rs.getDouble(8);
                    float weight = (float) rs.getDouble(9);
                    int[] heightArray = Patient.convertHeightMetricToImperial(height);
                    int[] weightArray = Patient.convertWeightMetricToImperial(weight);
                    pi[i] = new Patient(rs.getString(4), rs.getString(2), rs.getString(3), rs.getString(1), rs.getString(5), rs.getString(6), heightArray, weightArray, rs.getString(10));
                }
                return pi;
        }

        Object[] o = new Object[0];
        return o;
    }

    /**
     *
     * @param user is the username of the user being registered
     * @param pass is the password of the user to be encrypted using SHA-512 and
     * then added to the array of string
     * @throws ArrayIndexOutOfBoundsException is thrown in case the details
     * string goes wrong.
     * @throws SQLException is thrown if the SQL queries don't work correctly
     */
    public static void registerLogin(String user, char[] pass) throws ArrayIndexOutOfBoundsException, SQLException {
        String[] details = new String[2];
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(String.copyValueOf(pass).getBytes());
            String s = new String(messageDigest.digest());
            details[0] = user;
            details[1] = s;
        } catch (NoSuchAlgorithmException e) {
        }
        DatabaseAccess.saveLoginDetails(details);
    }

    /**
     * gets the user's login details as an array of strings, then passes that to
     * the database to check for a valid user. it hashes it using SHA-512
     * encryption to make passwords secure.
     *
     * @param user is the username passed from the GUI
     * @param pass is the password passed from the GUI
     * @return returns a Boolean to say whether the user logged in correctly or
     * not.
     * @throws java.sql.SQLException
     */
    public static boolean queryLogin(String user, char[] pass) throws SQLException {
        String username = user;
        char[] password = pass;
        String[] details = new String[2];
        boolean correctDetails;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(String.copyValueOf(password).getBytes());
            String s = new String(messageDigest.digest());
            details[0] = username;
            details[1] = s;
        } catch (NoSuchAlgorithmException e) {
        }
        correctDetails = DatabaseAccess.checkLoginDetails(details);
        //pass to database
        return correctDetails;
    }

    /**
     * checks to see if the result set is empty
     *
     * @param rs Is the result set being check if its got items.
     * @throws NoResultSetFoundException throws if the result set is empty
     * @throws SQLException throws if the result set doesn't exist
     */
    public static void checkrows(ResultSet rs) throws NoResultSetFoundException, SQLException {
        if (!rs.last()) {
            throw new NoResultSetFoundException("No variables within the resultset");
        }
        int length = rs.getRow();
        System.out.println(length);
        rs.first();
    }

    /**
     * Converts a result set into an array of string.
     *
     * @param rs Is the result set to be converted.
     * @return Is the array of string that the gui can display.
     * @throws SQLException If the result set can't be found.
     */
    private static String[] manageResults(ResultSet rs) throws SQLException {
        String[] data;
        rs.last();
        int length = rs.getRow();
        rs.first();
        data = new String[length];
        for (int i = 0; i < data.length; i++) {
            data[i] = rs.getString(1);
            rs.next();
        }
        return data;
    }

    /**
     * Registers a patient to the database.
     *
     * @param p Is the patient object created in the GUI to be passed through to
     * the database.
     * @return Returns a Boolean to say whether the registration was successful.
     * @throws ArrayIndexOutOfBoundsException Is thrown if the height/weight
     * array doesn't correctly
     * @throws SQLException Is thrown if the database query doesn't work
     * @throws IllegalArgumentException Is thrown if the date isn't in the
     * correct format. correctly.
     */
    public static boolean registerPatientDetails(Patient p) throws ArrayIndexOutOfBoundsException, SQLException, IllegalArgumentException {

        return DatabaseAccess.registerPatientDetails(p.getLastName(), p.getFirstName(), p.getMiddleName(), p.getPatientID(), java.sql.Date.valueOf(p.getDateOfBirth()), p.getAge(), p.getGender(), (int) p.convertHeightImperialToMetric(p.getHeightFeet(), p.getHeightInch()), (int) p.convertWeightImperialToMetric(p.getWeightLbs(), p.getWeightOz()), p.getAdditionalInfo());

    }

    /**
     * Returns patient details after a query, returns as an array of patient.
     *
     * @return Is an array of patient to be displayed by the GUI.
     * @throws ArrayIndexOutOfBoundsException Is thrown if the patient array
     * goes out of bounds.
     * @throws SQLException Is thrown if the query doesn't work.
     */
    public static Patient[] queryPatientDetails() throws ArrayIndexOutOfBoundsException, SQLException {
        ResultSet rs = DatabaseAccess.queryPatientDetails();
        rs.last();
        int length = rs.getRow();
        rs.first();
        Patient[] p = new Patient[length];
        for (int i = 0; i < length; i++) {
            float height = (float) rs.getDouble(8);
            float weight = (float) rs.getDouble(9);
            int[] heightArray = Patient.convertHeightMetricToImperial(height);
            int[] weightArray = Patient.convertWeightMetricToImperial(weight);
            p[i] = new Patient(rs.getString(4), rs.getString(2), rs.getString(3), rs.getString(1), rs.getString(5), rs.getString(6), heightArray, weightArray, rs.getString(10));
            rs.next();
        }
        return p;
    }

    /**
     * Queries the scan types in the database.
     *
     * @return Returns an array of string.
     * @throws ArrayIndexOutOfBoundsException Thrown if the array doesn't work
     * correctly.
     * @throws SQLException Thrown if the query doesn't work correctly.
     */
    public static String[] queryScanTypes() throws ArrayIndexOutOfBoundsException, SQLException {

        ResultSet rs = DatabaseAccess.queryScanTypes();
        String[] s = manageResults(rs);
        return s;
    }
    
        /**
     * Queries the scan parameters for a specified schedule
     *
     * @return Returns a ScanInformation file that the GUI can then display.
     * @throws ArrayIndexOutOfBoundsException Thrown if the conversion method
     * doesn't convert correctly.
     * @throws SQLException Thrown if the database query doesn't query
     * correctly.
     */
    public static ScanInformation queryScanParameters(String schedule) throws ArrayIndexOutOfBoundsException, SQLException {
        ResultSet rs = DatabaseAccess.queryScanParameters(schedule);
        rs.next();
        ScanInformation si = new ScanInformation(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11), rs.getInt(12), rs.getString(13), rs.getInt(14),rs.getDouble(15));
        return si;
    }

    /**
     * Returns images from the database to display.
     *
     * @param procID is the procedure ID attached to images.
     */
    //Temporarily empty for first iteration.
    public static BufferedImage[] queryScanImages(String procID) throws IOException {
        return DatabaseAccess.getImages(procID);
    }
}
