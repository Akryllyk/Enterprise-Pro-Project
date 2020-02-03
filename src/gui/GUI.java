/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.MouseInputListener;
import middlelayer.Handler;
import middlelayer.Patient;
import middlelayer.ScanInformation;

/**
 *
 * @author Aaron
 */
public class GUI implements ActionListener, MouseInputListener, KeyListener {

    private JFrame mainFrame;

    //List of used panels
    enum PanelTypes {
        LOGINPANEL, PATIENTPANEL, SCANPANEL, PATIENTREGISTRATIONPANEL, PROCEDUREREGISTRATIONPANEL, UPDATEDSCANPANEL
    }
    private PanelTypes currentPanelType;
    private boolean onlineMode = true;
    private boolean firstRun;
    //currentPanel will always be displayed, currentPanel is set to other panels above.
    private JPanel currentPanel;

    private JButton theTestButton;
    /**
     * Minimum height of the application.
     */
    private final int MIN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height - 50;
    /**
     * Minimum width of application
     */
    private final int MIN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width - 50;
    private final String FIRST_PANEL = "first panel";
    private final String SECOND_PANEL = "second panel";
    /**
     * Used for setMinimumSize method which requires a dimension rather than two
     * ints.
     */
    private Dimension minimumSize;
    //Components for loginPanel
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginSubmit;
    private JButton registerSubmit;
    //Components for patientPanel
    JLabel scanTypeTableLabel;
    JList scanTypeList;
    JLabel patientTableLabel;
    JTable patientTable;
    JLabel selectedScanTypeLabel;
    JTextField selectedScanType;
    JLabel selectedPatientLabel;
    JTextField selectedPatient;
    JButton patientSubmit;
    JButton registerPatientButton;

    //Components for patientRegistrationPanel
    //Components for patients
    JTextField patientIDField;
    JTextField firstNameField;
    JTextField middleNameField;
    JTextField surNameField;
    JTextField dateOfBirthField;
    JTextField heightFeetField;
    JTextField heightInchField;
    JTextField weightLbField;
    JTextField weightOzField;
    JButton registrationSubmitButton;
    JButton registrationCancelButton;
    JRadioButton maleRadioButton;
    JRadioButton femaleRadioButton;
    JRadioButton otherRadioButton;
    ButtonGroup genderGroup;
    JTextArea additionalInfoArea;
    //Components for procedures
    JComboBox studyComboBox;
    JComboBox ptPositionComboBox;
    JComboBox coilComboBox;
    JButton procedureSubmitButton;
    //Components for scanPanel
    JButton executeScheduleButton;
    JButton addScheduleButton;
    JButton[] imageNext;
    JButton[] imagePrev;
    JList scheduleList;
    ImagePanel[] imagePanel;
    JPanel schedulePanel;
    JPanel parameterPanel;
    JPanel cardParameterPanel;
    JLabel scanTitle;
    JPanel inputPanel;
    JPanel parameterPanelNavigation;
    JPanel firstParameterPanel;
    JPanel secondParameterPanel;
    JScrollPane schedulePane;
    JTabbedPane parameterPane;
    JButton firstPanelButton;
    JButton secondPanelButton;
    CardLayout cardLayout;
    private BufferedImage[] imageLocations;
    private int[] currentImage;
    //Temporary names, don't know the parameters, making panel that allows for 8 parameters.
    ScanInformation[] scans;
    String currentSequence;
    JTextField fovParameter;
    JTextField sliceThicknessParameter;
    JTextField slicesParameter;
    JTextField phaseDirectionParameter;
    JTextField phaseOversamplingParameter;
    JTextField voxelSizeParameter;
    JTextField trParameter;
    JTextField teParameter;
    JTextField averageParameter;
    JTextField concatenationParameter;
    JTextField phaseResolutionParameter;
    JTextField fatSupressionParameter;
    JTextField flipAngleParameter;
    JTextField timeOfAcquisitionParameter;

    public GUI() {
        initGUI();
    }

    /**
     * Used upon launching, sets up all the initial settings for the GUI
     * bringing it to the default panel.
     */
    private void initGUI() {
        mainFrame = new JFrame();
        minimumSize = new Dimension(MIN_WIDTH, MIN_HEIGHT);
        mainFrame.setMinimumSize(minimumSize);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(800, 600));
        firstRun = true;
        try {
            swapPanel(PanelTypes.LOGINPANEL);
        } catch (ArrayIndexOutOfBoundsException ex) {
        } catch (SQLException ex) {
        }
        mainFrame.add(currentPanel);
        mainFrame.setVisible(true);
    }

    /**
     * Generates the login panel, contains components used to log into the
     * software. Panel consists of: username and password JTextFields username
     * and password JLables loginSubmit JButton
     */
    private JPanel generateLoginPanel() {
        GridBagConstraints c = new GridBagConstraints();
        GridBagLayout tempLayout = new GridBagLayout();
        c.gridx = 0;
        JPanel tempPanel = new JPanel(tempLayout);
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        usernameField.setColumns(8);
        passwordField.setColumns(8);
        usernameField.addKeyListener(this);
        passwordField.addKeyListener(this);
        loginSubmit = new JButton("Submit");
        loginSubmit.addActionListener(this);
        registerSubmit = new JButton("Register");
        tempPanel.add(new JLabel("Login:"));
        c.ipadx = 15;
        c.gridy = 1;
        tempPanel.add(usernameLabel, c);
        c.gridx = 1;
        tempPanel.add(usernameField, c);
        c.gridy = 2;
        tempPanel.add(passwordField, c);
        c.gridx = 0;
        tempPanel.add(passwordLabel, c);
        c.gridx = 1;
        c.gridy = 3;
        tempPanel.add(loginSubmit, c);
        c.gridx--;
        //tempPanel.add(registerSubmit, c);
        return tempPanel;
    }

    /**
     * Used to obtain username from usernameField. If userName is empty returns
     * null instead of an empty string.
     *
     * @return Either contents of usernameField or null if field is empty
     */
    private String getUsername() {
        if (usernameField.getText().length() > 0) {
            return usernameField.getText();
        }
        return "";
    }

    /**
     * Used to obtain password from passwordField if passwordField is empty,
     * returns null instead of an empty char[] array
     *
     * @return
     */
    private char[] getPassword() {
        if (passwordField.getPassword().length > 0) {
            return passwordField.getPassword();
        }
        char[] empty = new char[0];
        return empty;
    }

    /**
     * Used to swap the current panel with a different panel. Panels are not
     * deleted and will remain in memory for now.
     *
     * @param newPanel
     */
    private void swapPanel(PanelTypes panelType) throws ArrayIndexOutOfBoundsException, SQLException {
        if (!firstRun) {
            mainFrame.remove(currentPanel);
        }
        switch (panelType) {
            case LOGINPANEL:
                currentPanel = generateLoginPanel();
                break;
            case PATIENTPANEL:
                currentPanel = generatePatientPanel();
                break;
            case PATIENTREGISTRATIONPANEL:
                currentPanel = generateRegistrationPanel(panelType);
                break;
            case PROCEDUREREGISTRATIONPANEL:
                currentPanel = generateRegistrationPanel(panelType);
                break;
            case SCANPANEL:
                currentPanel = generateScanPanel();
                break;
        }
        currentPanelType = panelType;
        System.out.println("Panel type is: " + currentPanelType);
        mainFrame.add(currentPanel);
        mainFrame.setVisible(true);
        firstRun = false;
    }

    /**
     * Generates an "empty" patient panel. All components will be generated and
     * the GUI structure will be in place. However the patientPanel will not be
     * populated with data.
     *
     * @return Completed patient panel.
     */
    private JPanel generatePatientPanel() throws ArrayIndexOutOfBoundsException, SQLException {
        JPanel patientPanel = new JPanel(new BorderLayout());

        //Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Patient Selector:"));

        //Content Panel
        JPanel contentPanel = new JPanel(new GridLayout(0, 1));

        //Content Panel, Scan Selection
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel scanSelectionPanel = new JPanel(new GridBagLayout());
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        scanSelectionPanel.add(new JLabel("Scan Selection:"), gbc);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        String[] scanTypes;
        if (onlineMode) {
            scanTypes = Handler.queryScanTypes();
        } else {
            scanTypes = new String[]{"Brain", "Spine", "Wrist"};
        }
        scanTypeList = new JList(scanTypes);
        scanTypeList.addMouseListener(this);
        JScrollPane scanTypeListPane = new JScrollPane(scanTypeList);
//        scanTypeListPane.setPreferredSize(new Dimension(1, Toolkit.getDefaultToolkit().getScreenSize().height/3));
        scanSelectionPanel.add(scanTypeListPane, gbc);
        gbc.weighty = 1;
        gbc.gridy++;
        scanSelectionPanel.add(new JLabel(""), gbc);

        //Contnent Panel, patient Selection.
        JPanel patientSelectionPanel = new JPanel(new GridBagLayout());
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        patientSelectionPanel.add(new JLabel("Patient Selection"), gbc);
        gbc.gridy++;
        String[][] patients;
        if (onlineMode) {
            patients = Patient.convertToString(Handler.queryPatientDetails());
        } else {
            patients = new String[][]{{"1", "Terry", "Jimmony", "Thing"}, {"2", "Terriny", "Jimmoty", "Thingoty"}};
        }
        String[] columnNames = new String[]{"PatientID", "FirstName", "MiddleName", "SurName"};
        patientTable = new JTable(patients, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        patientTable.addMouseListener(this);
        patientTable.addKeyListener(this);
        JScrollPane patientTablePane = new JScrollPane(patientTable);
        patientTablePane.setPreferredSize(new Dimension(1, Toolkit.getDefaultToolkit().getScreenSize().height / 3));
        patientSelectionPanel.add(patientTablePane, gbc);

        //Submit Panel
        JPanel submitPanel = new JPanel();
        JPanel submitPanelTop = new JPanel(new GridLayout(3, 0));
        selectedScanTypeLabel = new JLabel("Scan Type: ");
        selectedPatientLabel = new JLabel("Patient: ");
        registerPatientButton = new JButton("New Patient");
        registerPatientButton.addActionListener(this);
        submitPanelTop.add(selectedScanTypeLabel);
        submitPanelTop.add(selectedPatientLabel);
        submitPanelTop.add(registerPatientButton);
        JPanel submitPanelBottom = new JPanel(new GridLayout(3, 0));
        selectedScanType = new JTextField();
        selectedScanType.setEditable(false);
        selectedPatient = new JTextField();
        selectedPatient.setEditable(false);
        patientSubmit = new JButton("Submit");
        patientSubmit.addActionListener(this);
        submitPanelBottom.add(selectedScanType);
        submitPanelBottom.add(selectedPatient);
        submitPanelBottom.add(patientSubmit);
        submitPanel.add(submitPanelTop);
        submitPanel.add(submitPanelBottom);

        //Combining all the panels
        contentPanel.add(scanSelectionPanel);
        contentPanel.add(patientSelectionPanel);
        patientPanel.add(titlePanel, BorderLayout.NORTH);
        patientPanel.add(contentPanel, BorderLayout.CENTER);
        patientPanel.add(submitPanel, BorderLayout.SOUTH);
        return patientPanel;
    }

    /**
     * Generates an "empty" scanPanel. All components will be in place however
     * won't be filled with data.
     *
     * @return returns generated scanPanel;
     */
    private JPanel generateScanPanel() {
        //Returned panel
        JPanel scanPanel = new JPanel(new BorderLayout());
        //Title Panel
        JPanel titlePanel = new JPanel();
        scanTitle = new JLabel("Localiser Scans");
        titlePanel.add(scanTitle);
        //Image Panels
        imagePanel = new ImagePanel[3];
        currentImage = new int[3];
        JPanel outerImagePanelHolder = new JPanel(new GridLayout(0, 3));
        JPanel[] imageNavigationPanel = new JPanel[3];
        JPanel[] innerImagePanelHolder = new JPanel[3];
        imageNext = new JButton[3];
        imagePrev = new JButton[3];
        try {
            imageLocations = Handler.queryScanImages("Localisers");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Error, Image(s) not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
        for (int i = 0; i < 3; i++) {
            try {
                imagePanel[i] = new ImagePanel(imageLocations[i]);
                currentImage[i] = i;
                imagePanel[i].setPreferredSize(ImagePanel.DEFAULTDIMENSION);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(new JFrame(), "Image not found", "An error has occured", JOptionPane.ERROR_MESSAGE);
            }
            imageNavigationPanel[i] = new JPanel(new GridLayout(0, 2));
            imageNext[i] = new JButton("Next");
            imageNext[i].addActionListener(this);
            imagePrev[i] = new JButton("Prev");
            imagePrev[i].addActionListener(this);
            imageNavigationPanel[i].add(imagePrev[i]);
            imageNavigationPanel[i].add(imageNext[i]);
            innerImagePanelHolder[i] = new JPanel(new BorderLayout());
            innerImagePanelHolder[i].add(imageNavigationPanel[i], BorderLayout.NORTH);
            innerImagePanelHolder[i].add(imagePanel[i], BorderLayout.CENTER);
            outerImagePanelHolder.add(innerImagePanelHolder[i]);
        }
        //User Input Panels
        inputPanel = new JPanel(new GridLayout(0, 2));
        executeScheduleButton = new JButton("Execute Selected Schedule");
        executeScheduleButton.addActionListener(this);
        schedulePanel = new JPanel(new BorderLayout());
        String[] scheduleTypes = new String[]{"PD Axial FS", "T1 Axial", "PD Coronal FS", "T1 Coronal", "PD Sagittal FS"};
        scheduleList = new JList(scheduleTypes);
        scheduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scheduleList.setLayoutOrientation(JList.VERTICAL);
        schedulePane = new JScrollPane(scheduleList);
        schedulePane.setPreferredSize(new Dimension(200, 150));
        schedulePanel.add(executeScheduleButton, BorderLayout.SOUTH);
        schedulePanel.add(schedulePane, BorderLayout.CENTER);
        inputPanel.add(new JLabel());
        inputPanel.add(schedulePanel);

        scans = new ScanInformation[1];
        scans[0] = new ScanInformation("Localiser", 0, 0, 0, "a", 0, "a", 0, 0, 0, 0, 0, "a", 0, 0);

        scanPanel.add(titlePanel, BorderLayout.NORTH);
        scanPanel.add(outerImagePanelHolder, BorderLayout.CENTER);
        scanPanel.add(inputPanel, BorderLayout.SOUTH);
        return scanPanel;
    }

    /**
     * Method used to update the scanPanel to display images based on a
     * specified schedule
     *
     * @param schedule schedule of images to display
     */
    private void updateScanPanel(String schedule) {
        JPanel updatedScanPanel = new JPanel(new BorderLayout());
        mainFrame.remove(currentPanel);
//Title Panel
        JPanel titlePanel = new JPanel();
        scanTitle = new JLabel("Scan Results");
        titlePanel.add(scanTitle);
        //Image Panels
        imagePanel = new ImagePanel[3];
        currentImage = new int[3];
        JPanel outerImagePanelHolder = new JPanel(new GridLayout(0, 3));
        JPanel[] imageNavigationPanel = new JPanel[3];
        JPanel[] innerImagePanelHolder = new JPanel[3];
        imageNext = new JButton[3];
        imagePrev = new JButton[3];
        try {
            imageLocations = Handler.queryScanImages(schedule);
            for (int i = 0; i < 3; i++) {
                imagePanel[i] = new ImagePanel(imageLocations[i]);
                currentImage[i] = i;
                imagePanel[i].setPreferredSize(ImagePanel.DEFAULTDIMENSION);
                imageNavigationPanel[i] = new JPanel(new GridLayout(0, 2));
                imageNext[i] = new JButton("Next");
                imageNext[i].addActionListener(this);
                imagePrev[i] = new JButton("Prev");
                imagePrev[i].addActionListener(this);
                imageNavigationPanel[i].add(imagePrev[i]);
                imageNavigationPanel[i].add(imageNext[i]);
                innerImagePanelHolder[i] = new JPanel(new BorderLayout());
                innerImagePanelHolder[i].add(imageNavigationPanel[i], BorderLayout.NORTH);
                innerImagePanelHolder[i].add(imagePanel[i], BorderLayout.CENTER);
                outerImagePanelHolder.add(innerImagePanelHolder[i]);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Image not found", "An error has occured", JOptionPane.ERROR_MESSAGE);
        }
        //User Input Panels
        inputPanel = new JPanel(new GridLayout(0, 2));
        executeScheduleButton = new JButton("Execute Selected Schedule");
        executeScheduleButton.addActionListener(this);
        schedulePanel = new JPanel(new BorderLayout());
        String[] scheduleTypes = new String[]{"PD Axial FS", "T1 Axial", "PD Coronal FS", "T1 Coronal", "PD Sagittal FS"};
        scheduleList = new JList(scheduleTypes);
        scheduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scheduleList.setLayoutOrientation(JList.VERTICAL);
        schedulePane = new JScrollPane(scheduleList);
        schedulePane.setPreferredSize(new Dimension(200, 150));
        schedulePanel.add(executeScheduleButton, BorderLayout.SOUTH);
        schedulePanel.add(schedulePane, BorderLayout.CENTER);

        parameterPanel = new JPanel(new BorderLayout());
        parameterPane = new JTabbedPane(JTabbedPane.BOTTOM);
        firstParameterPanel = new JPanel(new GridLayout(0, 4));
        secondParameterPanel = new JPanel(new GridLayout(0, 4));
        firstParameterPanel.add(new JLabel("TR"));
        trParameter = new JTextField();
        firstParameterPanel.add(trParameter);
        firstParameterPanel.add(new JLabel("TE"));
        teParameter = new JTextField();
        firstParameterPanel.add(teParameter);
        firstParameterPanel.add(new JLabel("Slices"));
        slicesParameter = new JTextField();
        firstParameterPanel.add(slicesParameter);
        firstParameterPanel.add(new JLabel("Slice Thickness"));
        sliceThicknessParameter = new JTextField();
        firstParameterPanel.add(sliceThicknessParameter);
        firstParameterPanel.add(new JLabel("Phase Direction"));
        phaseDirectionParameter = new JTextField();
        firstParameterPanel.add(phaseDirectionParameter);
        firstParameterPanel.add(new JLabel("Phase oversampling"));
        phaseOversamplingParameter = new JTextField();
        firstParameterPanel.add(phaseOversamplingParameter);
        firstParameterPanel.add(new JLabel("FOV"));
        fovParameter = new JTextField();
        firstParameterPanel.add(fovParameter);
        secondParameterPanel.add(new JLabel("Voxel Size"));
        voxelSizeParameter = new JTextField();
        secondParameterPanel.add(voxelSizeParameter);
        secondParameterPanel.add(new JLabel("Averages"));
        averageParameter = new JTextField();
        secondParameterPanel.add(averageParameter);
        secondParameterPanel.add(new JLabel("Concatenations"));
        concatenationParameter = new JTextField();
        secondParameterPanel.add(concatenationParameter);
        secondParameterPanel.add(new JLabel("Phase Resolution"));
        phaseResolutionParameter = new JTextField();
        secondParameterPanel.add(phaseResolutionParameter);
        secondParameterPanel.add(new JLabel("Fat Supression"));
        fatSupressionParameter = new JTextField();
        secondParameterPanel.add(fatSupressionParameter);
        secondParameterPanel.add(new JLabel("Flip Angle"));
        flipAngleParameter = new JTextField();
        secondParameterPanel.add(flipAngleParameter);
        secondParameterPanel.add(new JLabel("Time of Acquisition"));
        timeOfAcquisitionParameter = new JTextField();
        secondParameterPanel.add(timeOfAcquisitionParameter);
        parameterPane.add("1", firstParameterPanel);
        parameterPane.add("2", secondParameterPanel);
        parameterPanel.add(parameterPane);

        inputPanel = new JPanel(new GridLayout(0, 2));
        schedulePanel = new JPanel(new BorderLayout());
        schedulePanel.add(schedulePane, BorderLayout.CENTER);
        schedulePanel.add(executeScheduleButton, BorderLayout.SOUTH);
        inputPanel.add(schedulePanel);
        inputPanel.add(parameterPanel);

        updatedScanPanel.add(titlePanel, BorderLayout.NORTH);
        updatedScanPanel.add(outerImagePanelHolder, BorderLayout.CENTER);
        updatedScanPanel.add(inputPanel, BorderLayout.SOUTH);
        currentPanel = updatedScanPanel;
        currentPanelType = PanelTypes.UPDATEDSCANPANEL;
        mainFrame.add(currentPanel);
        mainFrame.setVisible(true);
        firstRun = false;
    }

    /**
     * Used to generate a scan information object made up of the fields on the
     * GUI
     *
     * @return generated scan information object
     */
    private ScanInformation getCurrentParameters() throws NumberFormatException {
        if (!currentSequence.equalsIgnoreCase("Localiser")) {
            if(phaseDirectionParameter.getText().length() < 1 || voxelSizeParameter.getText().length() < 1 || fatSupressionParameter.getText().length() < 1 ){
                throw new NullPointerException();
            }
            
            ScanInformation temp = new ScanInformation(currentSequence, Integer.parseInt(fovParameter.getText()),
                    Integer.parseInt(sliceThicknessParameter.getText()), Integer.parseInt(slicesParameter.getText()),
                    phaseDirectionParameter.getText(), Integer.parseInt(phaseOversamplingParameter.getText()),
                    voxelSizeParameter.getText(), Integer.parseInt(trParameter.getText()), Integer.parseInt(teParameter.getText()),
                    Integer.parseInt(averageParameter.getText()), Integer.parseInt(concatenationParameter.getText()),
                    Integer.parseInt(phaseResolutionParameter.getText()), fatSupressionParameter.getText(),
                    Integer.parseInt(flipAngleParameter.getText()), Double.parseDouble(timeOfAcquisitionParameter.getText()));
            return temp;
        }
        return null;
    }

    /**
     * Used to store current values within the scan information object array.
     * Only saves new parameters if a scan information object of the same
     * schedule type already exists
     *
     * @param currentScan
     */
    private void saveCurrentParameters(ScanInformation currentScan) {
        boolean found = false;
        int position = -1;
        if (currentScan != null) {
            for (int i = 0; i < scans.length; i++) {
                if (scans[i].getSequence().equalsIgnoreCase(currentScan.getSequence())) {
                    found = true;
                    position = i;
                }
            }
            if (found) {
                scans[position] = currentScan;
            }
        }
    }

    /**
     * Used to update scanParameter text fields on the scanPanel. Updates fields
     * based on value of given ScanInformation object
     *
     * @param newScan ScanInformation object containing values wished to be
     * displayed
     */
    private void updateParameterPanel(ScanInformation newScan) {
        boolean found = false;
        int position = -1;
        System.out.println(newScan.getSequence());
        for (int i = 0; i < scans.length; i++) {
            if (scans[i].getSequence().equalsIgnoreCase(newScan.getSequence())) {
                found = true;
                position = i;
            }
        }
        if (!found) {
            ScanInformation[] temp = new ScanInformation[scans.length + 1];
            for (int i = 0; i < scans.length; i++) {
                temp[i] = scans[i];
            }
            temp[scans.length] = newScan;
            position = scans.length;
            scans = temp;
        }

        fovParameter.setText(String.valueOf(scans[position].getFov()));
        sliceThicknessParameter.setText(String.valueOf(scans[position].getSliceThickness()));
        slicesParameter.setText(String.valueOf(scans[position].getSlices()));
        phaseDirectionParameter.setText(String.valueOf(scans[position].getEncodingDirection()));
        phaseOversamplingParameter.setText(String.valueOf(scans[position].getPhaseOversampling()));
        voxelSizeParameter.setText(String.valueOf(scans[position].getVoxelSize()));
        trParameter.setText(String.valueOf(scans[position].getTR()));
        teParameter.setText(String.valueOf(scans[position].getTE()));
        averageParameter.setText(String.valueOf(scans[position].getAverage()));
        concatenationParameter.setText(String.valueOf(scans[position].getConcatenations()));
        phaseResolutionParameter.setText(String.valueOf(scans[position].getPhaseResolution()));
        fatSupressionParameter.setText(String.valueOf(scans[position].getFatSuppression()));
        flipAngleParameter.setText(String.valueOf(scans[position].getFlipAngle()));
        timeOfAcquisitionParameter.setText(String.valueOf(scans[position].getTimeOfAcquisition()));
    }

    /**
     * Generates the patient registration panel. Patient registration panel
     * covers pre-registration, adding the patients details to the database, as
     * well as registering them for the scan. Based on whether the user selects
     * pre-registration or registration for scan effects what fields they have
     * access to.
     *
     * @param panelType
     * @return
     */
    private JPanel generateRegistrationPanel(PanelTypes panelType) {
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel tempPanel = new JPanel(new GridLayout(0, 2));
        JPanel patientPanel = new JPanel(gbl);

        //Patient Panel
        JLabel patientIDLabel = new JLabel("Patient ID");
        JLabel firstNameLabel = new JLabel("First Name");
        JLabel middleNameLabel = new JLabel("Middle Name");
        JLabel surNameLabel = new JLabel("Surname");
        JLabel dateOfBirthLabel = new JLabel("Date Of Birth");
        JLabel genderLabel = new JLabel("Gender: ");
        JLabel heightLabel = new JLabel("Height: ");
        JLabel weightLabel = new JLabel("Weight:");
        registrationSubmitButton = new JButton("Register Patient");
        registrationSubmitButton.addActionListener(this);
        registrationCancelButton = new JButton("Cancel");
        registrationCancelButton.addActionListener(this);
        procedureSubmitButton = new JButton("Proceed to Exam");
        procedureSubmitButton.addActionListener(this);
        patientIDField = new JTextField();
        patientIDField.setColumns(10);
        firstNameField = new JTextField();
        firstNameField.setColumns(10);
        middleNameField = new JTextField();
        middleNameField.setColumns(10);
        surNameField = new JTextField();
        surNameField.setColumns(10);
        dateOfBirthField = new JTextField();
        dateOfBirthField.setColumns(10);
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        otherRadioButton = new JRadioButton("Other");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        genderGroup.add(otherRadioButton);
        heightFeetField = new JTextField();
        heightFeetField.setColumns(3);
        heightInchField = new JTextField();
        heightInchField.setColumns(3);
        weightLbField = new JTextField();
        weightLbField.setColumns(3);
        weightOzField = new JTextField();
        weightOzField.setColumns(3);
        additionalInfoArea = new JTextArea();

        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets.left = 5;
        gbc.insets.top = 5;
        patientPanel.add(patientIDLabel, gbc);
        gbc.gridx++;
        gbc.gridwidth++;
        patientPanel.add(patientIDField, gbc);
        gbc.gridwidth--;
        gbc.gridx--;
        gbc.gridy++;
        patientPanel.add(firstNameLabel, gbc);
        gbc.gridx++;
        gbc.gridwidth++;
        patientPanel.add(firstNameField, gbc);
        gbc.gridwidth--;
        gbc.gridx--;
        gbc.gridy++;
        patientPanel.add(middleNameLabel, gbc);
        gbc.gridx++;
        gbc.gridwidth++;
        patientPanel.add(middleNameField, gbc);
        gbc.gridwidth--;
        gbc.gridx--;
        gbc.gridy++;
        patientPanel.add(surNameLabel, gbc);
        gbc.gridx++;
        gbc.gridwidth++;
        patientPanel.add(surNameField, gbc);
        gbc.gridwidth--;
        gbc.gridx--;
        gbc.gridy++;
        patientPanel.add(dateOfBirthLabel, gbc);
        gbc.gridx++;
        gbc.gridwidth++;
        patientPanel.add(dateOfBirthField, gbc);
        gbc.gridwidth--;
        gbc.gridx++;
        gbc.gridx++;
        patientPanel.add(new JLabel("[YYYY-MM-DD]"), gbc);
        gbc.gridx--;
        gbc.gridx--;
        gbc.gridx--;
        gbc.gridy++;
        patientPanel.add(genderLabel, gbc);
        gbc.gridx++;
        patientPanel.add(maleRadioButton, gbc);
        gbc.gridx++;
        patientPanel.add(femaleRadioButton, gbc);
        gbc.gridx++;
        patientPanel.add(otherRadioButton, gbc);
        gbc.gridx--;
        gbc.gridx--;
        gbc.gridx--;
        gbc.gridy++;

        patientPanel.add(heightLabel, gbc);
        gbc.gridx++;
        JPanel hfPanel = new JPanel(new GridLayout(0, 2));
        hfPanel.add(heightFeetField);
        hfPanel.add(new JLabel("Feet"));
        patientPanel.add(hfPanel, gbc);
        gbc.gridx++;
        JPanel hiPanel = new JPanel(new GridLayout(0, 2));
        hiPanel.add(heightInchField);
        hiPanel.add(new JLabel("Inch"));
        patientPanel.add(hiPanel, gbc);
        gbc.gridx--;
        gbc.gridx--;
        gbc.gridy++;
        patientPanel.add(weightLabel, gbc);
        gbc.gridx++;
        JPanel wlPanel = new JPanel(new GridLayout(0, 2));
        wlPanel.add(weightLbField);
        wlPanel.add(new JLabel("Lb"));
        patientPanel.add(wlPanel, gbc);
        gbc.gridx++;
        JPanel woPanel = new JPanel(new GridLayout(0, 2));
        woPanel.add(weightOzField);
        woPanel.add(new JLabel("Ounce"));
        patientPanel.add(woPanel, gbc);
        gbc.gridx--;
        gbc.gridx--;
        gbc.gridx++;
        gbc.gridy++;
        gbc.gridx--;
        gbc.gridy++;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.SOUTH;
        patientPanel.add(registrationCancelButton, gbc);
        gbc.gridx++;
        patientPanel.add(registrationSubmitButton, gbc);

//Procedure Panel
        JPanel procedurePanel = new JPanel(new GridBagLayout());
        GridBagConstraints procedureGBC = new GridBagConstraints();
        JLabel studyLabel = new JLabel("Study");
        JLabel ptPositionLabel = new JLabel("PT Position");
        JLabel coilLabel = new JLabel("Coil");
        String[] studyValues = new String[]{"Brain", "Spine", "Wrist"};
        String[] ptPositionValues = new String[]{"Supine Head First", "Supine Feet First"};
        String[] coilValues = new String[]{"Wrist Coil", "Flexi Coil", "Head Coil"};
        studyComboBox = new JComboBox(studyValues);
        studyComboBox.setPreferredSize(new Dimension(150, 25));
        ptPositionComboBox = new JComboBox(ptPositionValues);
        ptPositionComboBox.setPreferredSize(new Dimension(150, 25));
        coilComboBox = new JComboBox(coilValues);
        coilComboBox.setPreferredSize(new Dimension(150, 25));
        procedureGBC.weightx = 1;
        procedureGBC.weighty = 1;
        procedureGBC.gridx = 0;
        procedureGBC.gridy = 0;
        procedureGBC.anchor = GridBagConstraints.WEST;
        procedurePanel.add(studyLabel, procedureGBC);
        procedureGBC.gridx++;
        procedurePanel.add(studyComboBox, procedureGBC);
        procedureGBC.gridx--;
        procedureGBC.gridy++;
        procedurePanel.add(ptPositionLabel, procedureGBC);
        procedureGBC.gridx++;
        procedurePanel.add(ptPositionComboBox, procedureGBC);
        procedureGBC.gridx--;
        procedureGBC.gridy++;
        procedurePanel.add(coilLabel, procedureGBC);
        procedureGBC.gridx++;
        procedurePanel.add(coilComboBox, procedureGBC);
        procedureGBC.gridx--;
        procedureGBC.gridy++;
        procedureGBC.weighty = 100;
        procedureGBC.anchor = GridBagConstraints.SOUTH;
        procedurePanel.add(procedureSubmitButton, procedureGBC);
        JPanel patientPanelHolder = new JPanel(new GridLayout(2, 0));
        patientPanelHolder.add(patientPanel);
        JPanel procedurePanelHolder = new JPanel(new GridLayout(2, 0));
        procedurePanelHolder.add(procedurePanel);
        if (panelType == PanelTypes.PATIENTREGISTRATIONPANEL) {
            for (int i = 0; i < procedurePanel.getComponentCount(); i++) {
                procedurePanel.getComponent(i).setEnabled(false);
            }
        } else {
            for (int i = 0; i < patientPanel.getComponentCount(); i++) {
                patientPanel.getComponent(i).setEnabled(false);
            }
            for (int i = 0; i < 2; i++) {
                hfPanel.getComponent(i).setEnabled(false);
                hiPanel.getComponent(i).setEnabled(false);
                wlPanel.getComponent(i).setEnabled(false);
                woPanel.getComponent(i).setEnabled(false);
            }
        }
        tempPanel.add(patientPanelHolder);
        tempPanel.add(procedurePanelHolder);
        return tempPanel;
    }

    /**
     * Method called whenever an SQL error occurs due to not being able to
     * connect to the server.
     */
    private void connectionError() {
        JOptionPane.showMessageDialog(new JFrame(), "Server error", "Server error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Handler handler = new Handler();
        try {
            if (e.getSource() == loginSubmit) {
                if (onlineMode) {
                    if (handler.queryLogin(getUsername(), getPassword())) {
                        swapPanel(PanelTypes.PATIENTPANEL);

                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Incorrect username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    swapPanel(PanelTypes.PATIENTPANEL);
                }

            } else if (e.getSource() == registerSubmit) {
                if (onlineMode) {
                    handler.registerLogin(getUsername(), getPassword());
                }
            } else if (e.getSource() == patientSubmit) {
                swapPanel(PanelTypes.PROCEDUREREGISTRATIONPANEL);
                //Updating value of studyComboBox on the procedure panel to the selected value on the scanType table
                studyComboBox.setSelectedItem(selectedScanType.getText());
            } else if (e.getSource() == registerPatientButton) {
                swapPanel(PanelTypes.PATIENTREGISTRATIONPANEL);
            } else if (e.getSource() == registrationSubmitButton) {
                //Method for registering patient
                String gender = "";
                if (genderGroup.getSelection() == maleRadioButton) {
                    gender = "male";
                } else if (genderGroup.getSelection() == femaleRadioButton) {
                    gender = "female";
                } else {
                    gender = "other";
                }
                if (onlineMode) {
                    try {
                        int[] heightArray = new int[]{Integer.parseInt(heightFeetField.getText()), Integer.parseInt(heightInchField.getText())};
                        int[] weightArray = new int[]{Integer.parseInt(weightLbField.getText()), Integer.parseInt(weightOzField.getText())};
                        Patient p = new Patient(surNameField.getText(), firstNameField.getText(), middleNameField.getText(), patientIDField.getText(), dateOfBirthField.getText(), gender, heightArray, weightArray, "Dummy String");
                        handler.registerPatientDetails(p);
                        swapPanel(PanelTypes.PATIENTPANEL);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(new JFrame(), "Please ensure all fields are filled", "Empty Field Warning", JOptionPane.ERROR_MESSAGE);
                    } catch (DateTimeParseException dtpe) {
                        JOptionPane.showMessageDialog(new JFrame(), "Please ensure a valid date is entered", "Incorrect Date Format", JOptionPane.ERROR_MESSAGE);
                    } catch (NullPointerException npe) {
                        connectionError();
                    }
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Cannot register patient due to being offline.", "Currently Offline", JOptionPane.PLAIN_MESSAGE);
                    swapPanel(PanelTypes.PATIENTPANEL);
                }

            } else if (e.getSource() == registrationCancelButton) {
                swapPanel(PanelTypes.PATIENTPANEL);
            } else if (e.getSource() == procedureSubmitButton) {
                swapPanel(PanelTypes.SCANPANEL);
                currentSequence = "Localiser";
            } else if (e.getSource() == imageNext[0]) {
                if (++currentImage[0] < imageLocations.length) {
                    imagePanel[0].setImage(imageLocations[currentImage[0]]);
                } else {
                    imagePanel[0].setImage(imageLocations[0]);
                    currentImage[0] = 0;
                }
            } else if (e.getSource() == imagePrev[0]) {
                if (--currentImage[0] >= 0) {
                    imagePanel[0].setImage(imageLocations[currentImage[0]]);
                } else {
                    currentImage[0] = imageLocations.length - 1;
                    imagePanel[0].setImage(imageLocations[currentImage[0]]);
                }
            } else if (e.getSource() == imageNext[1]) {
                if (++currentImage[1] < imageLocations.length) {
                    imagePanel[1].setImage(imageLocations[currentImage[1]]);
                } else {
                    currentImage[1] = 0;
                    imagePanel[1].setImage(imageLocations[currentImage[1]]);
                }
            } else if (e.getSource() == imagePrev[1]) {
                if (--currentImage[1] >= 0) {
                    imagePanel[1].setImage(imageLocations[currentImage[1]]);
                } else {
                    currentImage[1] = imageLocations.length - 1;
                    imagePanel[1].setImage(imageLocations[currentImage[1]]);
                }
            } else if (e.getSource() == imageNext[2]) {
                if (++currentImage[2] < imageLocations.length) {
                    imagePanel[2].setImage(imageLocations[currentImage[2]]);
                } else {
                    currentImage[2] = 0;
                    imagePanel[2].setImage(imageLocations[currentImage[1]]);
                }
            } else if (e.getSource() == imagePrev[2]) {
                if (--currentImage[2] >= 0) {
                    imagePanel[2].setImage(imageLocations[currentImage[2]]);
                } else {
                    currentImage[2] = imageLocations.length - 1;
                    imagePanel[2].setImage(imageLocations[currentImage[2]]);
                }
            } else if (e.getSource() == executeScheduleButton) {
                if ((String) scheduleList.getSelectedValue() != null) {
                    String selectedValue = (String) scheduleList.getSelectedValue();
                    try{
                    ScanInformation currentParameters = getCurrentParameters();
                    saveCurrentParameters(currentParameters);
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(new JFrame(), "Unable to save due to invalid parameter", "Error: Invalid Parameter", JOptionPane.ERROR_MESSAGE);
                    }
                    currentSequence = selectedValue;
                    updateScanPanel(selectedValue);
                    ScanInformation newScan;
                    if (onlineMode) {
                        newScan = Handler.queryScanParameters(selectedValue);
                    } else {
                        newScan = new ScanInformation(currentSequence, 1, 2, 3, "Test", 4, "Test", 5, 6, 7, 8, 9, "Ten", 11, 12);
                    }
                    System.out.println(newScan.getSequence());
                    updateParameterPanel(newScan);
                    System.out.println(currentSequence);

                }
                mainFrame.setVisible(true);
            }
        } catch (SQLException ex) {
            connectionError();

        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    /*
    Using for selecting values from scanType and patient tables.
    When a scanType cell is clicked, it'll query the database for patients waiting on selected scan type.
    When a patient cell is clicked, sets variables for submit button to get images and scan parameters
     */
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == scanTypeList) {
            String selectedValue = (String) scanTypeList.getSelectedValue();
            selectedScanType.setText(selectedValue);
        } else if (e.getSource() == patientTable) {
            int row = patientTable.getSelectedRow();
            String selectedValue = (String) patientTable.getValueAt(row, 0);
            selectedPatient.setText(selectedValue);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (currentPanelType == PanelTypes.LOGINPANEL) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                loginSubmit.doClick();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == patientTable) {
            int row = patientTable.getSelectedRow();
            String selectedValue = (String) patientTable.getValueAt(row, 0);
            selectedPatient.setText(selectedValue);
        }
    }

}
