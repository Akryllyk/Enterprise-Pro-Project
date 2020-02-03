package middlelayer;

import java.sql.ResultSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Alex
 */
public class ScanInformation {

    private String sequence;
    private int fov;
    private int sliceThickness;
    private int slices;
    private String encodingDirection;
    private int phaseOversampling;
    private String voxelSize;
    private int tr;
    private int te;
    private int average;
    private int concatenations;
    private int phaseResolution;
    private String fatSuppression;
    private int flipAngle;
    ResultSet rs;
    Object[] input;
    private double timeOfAcquisition;

    /**
     * constructs a set of information of the ResultSet with all the parameters.
     *
     * @param sequence is the sequence of the record.
     * @param fov is the field of view of the record.
     * @param tr is the tr of the record.
     * @param te is the te of the record.
     * @param sT is the sliceThickness of the record.
     * @param slices is the slices of the record.
     * @param eD is the encodingDirection of the record.
     * @param pO is the phsaeOversampling of the record.
     * @param vS is the voxelSize of the record.
     * @param average is the average of the record.
     * @param concat is the concatenations of the record.
     * @param pR is the phaseResolution of the record.
     * @param fS is the fatSuppression of the record.
     * @param fA is the flipAngle of the record.
     * @param tOA is the time of acquisition of the record.
     */
    public ScanInformation(String sequence, int fov, int sT, int slices, String eD, int pO, String vS, int tr, int te, int average, int concat, int pR, String fS, int fA, double tOA) {
        this.sequence = sequence;
        this.fov = fov;
        this.sliceThickness = sT;
        this.slices = slices;
        this.encodingDirection = eD;
        this.phaseOversampling = pO;
        this.voxelSize = vS;
        this.tr = tr;
        this.te = te;
        this.average = average;
        this.concatenations = concat;
        this.phaseResolution = pR;
        this.fatSuppression = fS;
        this.flipAngle = fA;
        this.timeOfAcquisition = tOA;
    }

    /**
     * returns the sequence of the ResultSet
     *
     * @return is the sequence of the ResultSet.
     */
    public String getSequence() {
        return sequence;
    }

    /**
     * returns the tr of the ResultSet
     *
     * @return is the tr of the ResultSet.
     */
    public int getTR() {
        return tr;
    }

    /**
     * returns the te of the ResultSet
     *
     * @return is the te of the ResultSet.
     */
    public int getTE() {
        return te;
    }

    /**
     * returns the sliceThickness of the ResultSet
     *
     * @return is the sliceThickness of the ResultSet.
     */
    public int getSliceThickness() {
        return sliceThickness;
    }

    /**
     * returns the slices of the ResultSet
     *
     * @return is the slices of the ResultSet.
     */
    public int getSlices() {
        return slices;
    }

    /**
     * returns the encodingDirection of the ResultSet
     *
     * @return is the encodingDirection of the ResultSet.
     */
    public String getEncodingDirection() {
        return encodingDirection;
    }

    /**
     * returns the phaseOversampling of the ResultSet
     *
     * @return is the gap of the ResultSet.
     */
    public int getPhaseOversampling() {
        return phaseOversampling;
    }

    /**
     * returns the voxelSize of the ResultSet
     *
     * @return is the voxelSize of the ResultSet.
     */
    public String getVoxelSize() {
        return voxelSize;
    }

    /**
     * returns the average of the ResultSet
     *
     * @return is the average of the ResultSet.
     */
    public int getAverage() {
        return average;
    }

    /**
     * returns the concatenations of the ResultSet
     *
     * @return is the concatenations of the ResultSet.
     */
    public int getConcatenations() {
        return concatenations;
    }

    /**
     * returns the phaseResolution of the ResultSet
     *
     * @return is the phaseResolution of the ResultSet.
     */
    public int getPhaseResolution() {
        return phaseResolution;
    }

    /**
     * returns the fatSuppression of the ResultSet
     *
     * @return is the fatSuppression of the ResultSet.
     */
    public String getFatSuppression() {
        return fatSuppression;
    }

    /**
     * returns the flipAngle of the ResultSet
     *
     * @return is the flipAngle of the ResultSet.
     */
    public int getFlipAngle() {
        return flipAngle;
    }

    /**
     * returns the fov of the ResultSet
     *
     * @return is the fov of the ResultSet.
     */
    public int getFov() {
        return fov;
    }

    /**
     * returns the timeOfAcquisition of the ResultSet
     *
     * @return is the timeOfAcquisition of the ResultSet.
     */
    public double getTimeOfAcquisition() {
        return timeOfAcquisition;
    }

    /**
     * sets the sequence of the ResultSet
     *
     * @param sequence of the ResultSet.
     */
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    /**
     * sets the slices of the ResultSet
     *
     * @param slices of the ResultSet.
     */
    public void setSlices(int slices) {
        this.slices = slices;
    }

    /**
     * sets the te of the ResultSet
     *
     * @param te of the ResultSet.
     */
    public void setTE(int te) {
        this.te = te;
    }

    /**
     * sets the sliceThickness of the ResultSet
     *
     * @param sliceThickness of the ResultSet.
     */
    public void setSliceThickness(int sliceThickness) {
        this.sliceThickness = sliceThickness;
    }

    /**
     * sets the encodingDirection of the ResultSet
     *
     * @param encodingDirection of the ResultSet.
     */
    public void setEncodingDirection(String encodingDirection) {
        this.encodingDirection = encodingDirection;
    }

    /**
     * sets the phaseOversampling of the ResultSet
     *
     * @param phaseOversampling of the ResultSet.
     */
    public void setPhaseOversampling(int phaseOversampling) {
        this.phaseOversampling = phaseOversampling;
    }

    /**
     * sets the voxelSize of the ResultSet
     *
     * @param voxelSize of the ResultSet.
     */
    public void setGap(String voxelSize) {
        this.voxelSize = voxelSize;
    }

    /**
     * sets the average of the ResultSet
     *
     * @param average of the ResultSet.
     */
    public void setAverage(int average) {
        this.average = average;
    }

    /**
     * sets the concatenations of the ResultSet
     *
     * @param concatenations of the ResultSet.
     */
    public void setConcatenations(int concatenations) {
        this.concatenations = concatenations;
    }

    /**
     * sets the phaseResolution of the ResultSet
     *
     * @param phaseResolution of the ResultSet.
     */
    public void setPhaseResolution(int phaseResolution) {
        this.phaseResolution = phaseResolution;
    }

    /**
     * sets the fatSuppression of the ResultSet
     *
     * @param fatSuppression of the ResultSet.
     */
    public void setFatSuppression(String fatSuppression) {
        this.fatSuppression = fatSuppression;
    }

    /**
     * sets the flipAngle of the ResultSet
     *
     * @param flipAngle of the ResultSet.
     */
    public void setFlipAngle(int flipAngle) {
        this.flipAngle = flipAngle;
    }

    /**
     * sets the fov of the ResultSet
     *
     * @param fov of the ResultSet.
     */
    public void setFOV(int fov) {
        this.fov = fov;

    }

    /**
     * sets the timeOfAcquisition of the ResultSet
     *
     * @param toa of the ResultSet.
     */
    public void setTimeOfAcquisition(double toa) {
        timeOfAcquisition = toa;
    }

    /**
     * sets the tr of the ResultSet
     *
     * @param tr of the ResultSet.
     */
    public void setTR(int tr) {
        this.tr = tr;
    }
}
