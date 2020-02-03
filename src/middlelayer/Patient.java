package middlelayer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Alex
 */
import java.time.*;

public class Patient {

    private String lastName;
    private String firstName;
    private String middleName;
    private String patientID;
    private String dateOfBirth;
    private String gender;
    private int age;
    private int heightFeet;
    private int heightInch;
    private int weightLbs;
    private int weightOz;
    private String additionalInfo;

    /**
     * constructs a patient with all the parameters.
     *
     * @param lastName is the last name of the patient.
     * @param firstName is the first name of the patient.
     * @param middleName is the middle name of the patient.
     * @param patientID is the ID of the patient.
     * @param dateOfBirth is the date of birth of the patient.
     * @param gender s is the gender of the patient.
     * @param additionalInfo is any additional info about the patient.
     */
    public Patient(String lastName, String firstName, String middleName, String patientID, String dateOfBirth, String gender, int[] heightArray, int[] weightArray, String additionalInfo) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.patientID = patientID;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        LocalDate birthday = java.time.LocalDate.parse(dateOfBirth);
        Period ageFull = Period.between(birthday, java.time.LocalDate.now());
        this.age = ageFull.getYears();
        this.heightFeet = heightArray[0];
        this.heightInch = heightArray[1];
        this.weightLbs = weightArray[0];
        this.weightOz = weightArray[1];
        this.additionalInfo = additionalInfo;
    }

    /**
     * returns the last name of the patient
     *
     * @return is the last name of the patient.
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Converts height from imperial to metric units
     * @param feet is height of patient in feet
     * @param inches is height of patient in inches (remainder)
     * @return is the height in metric, given as metres.
     */
    public float convertHeightImperialToMetric(int feet, int inches){
        return (float)((feet*12+inches)*0.0254);
    }
    /**
     * Converts weight from imperial to metric units
     * @param lbs is weight of patient in pounds.
     * @param oz is weight of patient in stone (remainder).
     * @return is the weight in Kilograms.
     */
    public float convertWeightImperialToMetric(int lbs, int oz){
        return (float)((lbs*16+oz)*0.02834952);
    }
    /**
     * converts height from metric to imperial;
     * @param heightMetres is the height in metres.
     * @return is the array of int returned. index 0 is the height in feet, index 1 is the remainder in inches.
     */
    public static int[] convertHeightMetricToImperial(float heightCentimetres){
        int[] array = new int[2];
        int totalInch = (int) (heightCentimetres / 2.54);
        array[0] = totalInch / 12;
        array[1] = totalInch % 12;
        return array;
    }
    
    /**
     * converts weight from metric to imperial
     * @param weight is the weight in Kilograms
     * @return is thee array of int. Index 0 is weight in pounds, index 1 is remainder in ounces.
     */
    public static int[] convertWeightMetricToImperial(float weight){
        int[] array = new int[2];
        int totalOz = (int) (weight / 28.35);
        array[0] = totalOz / 16;
        array[1] = totalOz % 16;
        return array;
    }
    /**
     * returns the first name.
     *
     * @return is the first name of the patient.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * returns the middle name(s).
     *
     * @return is the middle name (s) of the patient.
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * returns the patient ID.
     *
     * @return is the patient ID
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * returns the date of birth.
     *
     * @return is the date of birth.
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * returns the gender.
     *
     * @return is the gender of the patient.
     */
    public String getGender() {
        return gender;
    }
    /**
     * returns the age
     * @return is the age of the patient
     */
    public int getAge(){
        return age;
    }
    /**
     * returns the height of the patient, in feet.
     *
     * @return is the height in feet.
     */
    public int getHeightFeet() {
        return heightFeet;
    }

    /**
     * returns the remainder of the height of the patient, in inches.
     *
     * @return is the remainder height.
     */
    public int getHeightInch() {
        return heightInch;
    }

    /**
     * returns weight of the patient in pounds.
     * @return is the weight in pounds
     */
    public int getWeightLbs() {
        return weightLbs;
    }
    
    /**
     * returns the remainder of the weight of the patient in ounces.
     * @return is the remainder weight.
     */
    public int getWeightOz() {
        return weightOz;
    }
    
    /**
     * returns any additional info the patient has.
     * @return is a string of the information.
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }
    
    /**
     * sets the last name of the patient.
     * @param s is the new last name of the patient.
     */
    public void setLastName(String s) {
        lastName = s;
    }
    
    /**
     * sets the first name of the patient.
     * @param s is the new first name of the patient.
     */
    public void setFirstName(String s) {
        firstName = s;
    }
    
    /**
     * sets the middle name(s) of the patient.
     * @param s is the new middle names of the patient.
     */
    public void setMiddleName(String s) {
        middleName = s;
    }
    
    /**
     * sets the patient ID 
     * @param s 
     */
    public void setPatientID(String s) {
        patientID = s;
    }
    
    /**
     * sets the date of birth of the patient.
     * @param d is the new date of birth.
     */
    public void setDateOfBirth(String d) {
        dateOfBirth = d;
    }
    
    /**
     * sets the gender of the patient.
     * @param s is the new gender of the patient.
     */
    public void setGender(String s) {
        gender = s;
    }
    
    /**
     * sets the age of the patient.
     * @param a is the new age of the patient
     */
    public void setAge(int a){
        age = a;
    }
    
    /**
     * sets the patient's height in feet. 
     * @param i is the new height of the patient in feet.
     */
    public void setHeightFeet(int i) {
        heightFeet = i;
    }
    
    /**
     * sets the remainder height of the patient in inches.
     * @param i is the new height of the patient in inches.
     */
    public void setHeightInch(int i) {
        heightInch = i;
    }
    
    /**
     * sets the weight of the patient in pounds.
     * @param i is the new height of the patient in pounds.
     */
    public void setWeightLbs(int i) {
        weightLbs = i;
    }
    
    /**
     * sets the remainder weight of the patient in ounces.
     * @param i is the new remainder weight of the patient.
     */
    public void setWeightOz(int i) {
        weightOz = i;
    }

    /**
     * sets any additional information about the patient.
     * @param s is the additional info being added.
     */
    public void setAdditionalInfo(String s) {
        additionalInfo = s;
    }
    
    /**
     * converts an array of patient into a 2d array, listing patient first, middle and last names.
     * @param p is the array of patients
     * @return is the 2d array of strings converted. 
     */
    public static String[][] convertToString(Patient[] p){
        String[][] string = new String[p.length][4];
        for(int i = 0; i < p.length; i++){
            string[i][0] = p[i].getPatientID();
            string[i][1] = p[i].getFirstName();
            string[i][2] = p[i].getMiddleName();
            string[i][3] = p[i].getLastName();
        }
        return string;
    }

}
