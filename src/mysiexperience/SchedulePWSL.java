/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mysiexperience;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author z004kptc
 */
public class SchedulePWSL extends javax.swing.JFrame {

    //Objects
    private boolean isChanged = false;
    private JButton[] btnCal = new JButton[42];
    private java.time.DayOfWeek dayOfWeek;                              //Current Day of week
    private java.time.DayOfWeek firstOfMonth;                           //initCalendar 
    private String selectedDayOfMonth;                                  //PARAMETER
    private String selectedYear;                                        //PARAMETER
    private String selectedMonthOfYear;                                 //PARAMETER
    private String dateString;                                          //formatted variable for calculating the first weekday of the month
    private int dayOfMonth;                                             //initCalendar

    public enum MonthOfYear {
        January,
        February,
        March,
        April,
        May,
        June,
        July,
        August,
        September,
        October,
        November,
        December;

    }                             //Enum for month: January, February, etc. 
    private MonthOfYear monthOfYear;                                    //Based on view
    private String year;                                                //Based on view
    private Boolean changeView;                                         //If Toggled, then refreshCalendar
    private int startCal;                                               //First tile used in month
    public String selectedDay;                                          //JTextField Label

    private void exitApp() {
        this.dispose();
    }
    private static JButton selectedButton = null;

    private void selectButton(JButton clickedButton) {
    // Prompt the user if there are unsaved changes
    if (isChanged) {
        int response = showSaveDiscardDialog();
        if (response == 0) { // Save
            btnSaveActionPerformed(null); // Save changes
        } else if (response == 1) { // Discard
            isChanged = false; // Discard changes
        } else { // Cancel
            return; // Do nothing and return
        }
    }

    // PARAMETERS FOR SEARCH
    selectedDayOfMonth = String.format("%02d", Integer.valueOf(clickedButton.getText()));
    int monthNumber = monthOfYear.ordinal() + 1;
    selectedMonthOfYear = String.format("%02d", Integer.valueOf(monthNumber));
    selectedYear = year;
    System.out.println(selectedDayOfMonth + selectedMonthOfYear + selectedYear);

    // set the JTextField with the current selected date
    selectedDay = monthOfYear + " " + selectedDayOfMonth + ", " + year;
    jtfSelectedDay.setText(selectedDay);

    // Load new lstTodayPWSL content from XML
    String newFileName = System.getProperty("user.home") + "/Documents/MySIExperience/Configuration/days/"
            + selectedDayOfMonth + selectedMonthOfYear + selectedYear + ".xml";
    loadLstTodayPWSLFromXML(newFileName);

    deselectButtons();
    clickedButton.setSelected(true);
    clickedButton.setBackground(Color.green);
    selectedButton = clickedButton;
}

    private void initCalendar() {
    LocalDateTime now = LocalDateTime.now();
    dayOfMonth = now.getDayOfMonth();
    monthOfYear = MonthOfYear.values()[now.getMonthValue() - 1]; // Month values are 1-12, enum values are 0-11
    this.year = String.valueOf(now.getYear());
    lblMonthYear.setText(monthOfYear  + ", " + year);
    
    //Determining the first day of the month
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d, MMMM, yyyy", Locale.ENGLISH);
    dateString = 1 + ", " + monthOfYear + ", " + year;
    
    LocalDate date = LocalDate.parse(dateString, formatter);
    dayOfWeek = date.getDayOfWeek();
    System.out.println("The first day of " + monthOfYear + " is " + dayOfWeek);
    
    if (dayOfWeek == DayOfWeek.SUNDAY) {
        startCal = 1;
    } else if (dayOfWeek == DayOfWeek.MONDAY) {
        startCal = 2;
    } else if (dayOfWeek == DayOfWeek.TUESDAY) {
        startCal = 3;
    } else if (dayOfWeek == DayOfWeek.WEDNESDAY) {
        startCal = 4;
    } else if (dayOfWeek == DayOfWeek.THURSDAY) {
        startCal = 5;
    } else if (dayOfWeek == DayOfWeek.FRIDAY) {
        startCal = 6;
    } else if (dayOfWeek == DayOfWeek.SATURDAY) {
        startCal = 7;
    }
    
    // Update Calendar Buttons
    int day = 1;
    for (int i = 0; i < btnCal.length; i++) {
        if (i < startCal - 1) {
            btnCal[i].setText("");
            btnCal[i].setEnabled(false);
        } else {
            if (day <= LocalDate.of(Integer.parseInt(year), monthOfYear.ordinal() + 1, 1).lengthOfMonth()) {
                btnCal[i].setText(String.valueOf(day));
                btnCal[i].setEnabled(true);

                // Check if a PWSL exists for this day and set the background
                String fileName = String.format("%02d%02d%s.xml", day, monthOfYear.ordinal() + 1, year);
                File file = new File(System.getProperty("user.home") + "/Documents/MySIExperience/Configuration/days/" + fileName);
                if (file.exists()) {
                    btnCal[i].setBackground(Color.CYAN); // Set light-blue background
                } else {
                    btnCal[i].setBackground(Color.WHITE); // Reset to default background
                }

                day++;
            } else {
                btnCal[i].setText("");
                btnCal[i].setEnabled(false);
            }
        }
    }
}

    private void refreshCalendar() {
    lblMonthYear.setText(monthOfYear  + ", " + year);
    dateString = 1 + ", " + monthOfYear + ", " + year;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d, MMMM, yyyy", Locale.ENGLISH);
    LocalDate date = LocalDate.parse(dateString, formatter);
    dayOfWeek = date.getDayOfWeek();
    System.out.println("The first day of " + monthOfYear + " is " + dayOfWeek);
    
    if (dayOfWeek == DayOfWeek.SUNDAY) {
        startCal = 1;
    } else if (dayOfWeek == DayOfWeek.MONDAY) {
        startCal = 2;
    } else if (dayOfWeek == DayOfWeek.TUESDAY) {
        startCal = 3;
    } else if (dayOfWeek == DayOfWeek.WEDNESDAY) {
        startCal = 4;
    } else if (dayOfWeek == DayOfWeek.THURSDAY) {
        startCal = 5;
    } else if (dayOfWeek == DayOfWeek.FRIDAY) {
        startCal = 6;
    } else if (dayOfWeek == DayOfWeek.SATURDAY) {
        startCal = 7;
    }
    
    // Update Calendar Buttons
    int day = 1;
    for (int i = 0; i < btnCal.length; i++) {
        if (i < startCal - 1) {
            btnCal[i].setText("");
            btnCal[i].setEnabled(false);
        } else {
            if (day <= LocalDate.of(Integer.parseInt(year), monthOfYear.ordinal() + 1, 1).lengthOfMonth()) {
                btnCal[i].setText(String.valueOf(day));
                btnCal[i].setEnabled(true);
                btnCal[i].setBackground(Color.WHITE); // Reset to default background
                
                // Check if a PWSL exists for this day and set the background
                String fileName = String.format("%02d%02d%s.xml", day, monthOfYear.ordinal() + 1, year);
                File file = new File(System.getProperty("user.home") + "/Documents/MySIExperience/Configuration/days/" + fileName);
                if (file.exists()) {
                    btnCal[i].setBackground(Color.CYAN); // Set light-blue background
                }

                day++;
            } else {
                btnCal[i].setText("");
                btnCal[i].setEnabled(false);
            }
        }
    }
}

private void deselectButtons() {
    for (int i = 0; i < btnCal.length; i++) {
        if (btnCal[i].isEnabled()) {
            String dayText = btnCal[i].getText();
            if (!dayText.isEmpty()) {
                String fileName = String.format("%02d%02d%s.xml", Integer.parseInt(dayText), monthOfYear.ordinal() + 1, year);
                File file = new File(System.getProperty("user.home") + "/Documents/MySIExperience/Configuration/days/" + fileName);
                if (file.exists()) {
                    btnCal[i].setBackground(Color.CYAN); // Set light-blue background
                } else {
                    btnCal[i].setBackground(Color.WHITE); // Reset to default background
                }
            } else {
                btnCal[i].setBackground(Color.WHITE); // Reset to default background
            }
        }
    }
}

    private void btnCalArray() {
        btnCal[0] = btnCal1;
        btnCal[1] = btnCal2;
        btnCal[2] = btnCal3;
        btnCal[3] = btnCal4;
        btnCal[4] = btnCal5;
        btnCal[5] = btnCal6;
        btnCal[6] = btnCal7;
        btnCal[7] = btnCal8;
        btnCal[8] = btnCal9;
        btnCal[9] = btnCal10;
        btnCal[10] = btnCal11;
        btnCal[11] = btnCal12;
        btnCal[12] = btnCal13;
        btnCal[13] = btnCal14;
        btnCal[14] = btnCal15;
        btnCal[15] = btnCal16;
        btnCal[16] = btnCal17;
        btnCal[17] = btnCal18;
        btnCal[18] = btnCal19;
        btnCal[19] = btnCal20;
        btnCal[20] = btnCal21;
        btnCal[21] = btnCal22;
        btnCal[22] = btnCal23;
        btnCal[23] = btnCal24;
        btnCal[24] = btnCal25;
        btnCal[25] = btnCal26;
        btnCal[26] = btnCal27;
        btnCal[27] = btnCal28;
        btnCal[28] = btnCal29;
        btnCal[29] = btnCal30;
        btnCal[30] = btnCal31;
        btnCal[31] = btnCal32;
        btnCal[32] = btnCal33;
        btnCal[33] = btnCal34;
        btnCal[34] = btnCal35;
        btnCal[35] = btnCal36;
        btnCal[36] = btnCal37;
        btnCal[37] = btnCal38;
        btnCal[38] = btnCal39;
        btnCal[39] = btnCal40;
        btnCal[40] = btnCal41;
        btnCal[41] = btnCal42;
    }

    private MonthOfYear decrementMonth(MonthOfYear month) {
        int ordinal = month.ordinal();
        if (ordinal == 0) {
            ordinal = MonthOfYear.values().length - 1; // Wrap around to December if currently January
            int intYear = Integer.parseInt(year);
            intYear--;
            year = String.valueOf(intYear);
        } else {
            ordinal--;
        }
        return MonthOfYear.values()[ordinal];
    }

    private MonthOfYear incrementMonth(MonthOfYear month) {
        int ordinal = month.ordinal();
        if (ordinal == MonthOfYear.values().length - 1) { // If currently December
            ordinal = 0; // Wrap around to January
            int intYear = Integer.parseInt(year);
            intYear++;
            year = String.valueOf(intYear);
        } else {
            ordinal++;
        }
        return MonthOfYear.values()[ordinal];
    }

    private void populateLstAllPWSL() {
        DefaultListModel<String> listModel1 = (DefaultListModel<String>) lstAllPWSL.getModel();
        String userHome = System.getProperty("user.home");
        File folder = new File(userHome + "/Documents/MySIExperience/PWSL_Templates");

        File[] files = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".xml");
            }
        });

        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                if (fileName.endsWith(".xml")) {
                    fileName = fileName.substring(0, fileName.length() - 4); // Remove the ".xml" extension
                    listModel1.addElement(fileName);
                }
            }
        }
    }

private void checkOrCreateDaysFolder() {
    String userHome = System.getProperty("user.home");
    File daysFolder = new File(userHome + "/Documents/MySIExperience/Configuration/days");
    if (!daysFolder.exists()) {
        daysFolder.mkdirs();
    }
}

    private void saveLstTodayPWSLToXML(String fileName) {
    try {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // Root element
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("PWSLs");
        doc.appendChild(rootElement);

        // Add each PWSL to the XML
        DefaultListModel<String> listModel = (DefaultListModel<String>) lstTodayPWSL.getModel();
        for (int i = 0; i < listModel.getSize(); i++) {
            Element pwsl = doc.createElement("PWSL");
            pwsl.appendChild(doc.createTextNode(listModel.getElementAt(i)));
            rootElement.appendChild(pwsl);
        }

        // Write the content into XML file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fileName));

        transformer.transform(source, result);

        System.out.println("File saved: " + fileName);

    } catch (ParserConfigurationException | TransformerException e) {
        e.printStackTrace();
    }
}

private void loadLstTodayPWSLFromXML(String fileName) {
    try {
        File fXmlFile = new File(fileName);
        DefaultListModel<String> listModel = (DefaultListModel<String>) lstTodayPWSL.getModel();
        listModel.clear(); // Clear existing content

        if (!fXmlFile.exists()) {
            return; // If file doesn't exist, nothing to load
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("PWSL");
        for (int i = 0; i < nList.getLength(); i++) {
            Element element = (Element) nList.item(i);
            listModel.addElement(element.getTextContent());
        }

        lstTodayPWSL.setModel(listModel); // Ensure the JList model is updated

        System.out.println("File loaded: " + fileName);

    } catch (ParserConfigurationException | SAXException | IOException e) {
        e.printStackTrace();
    }
}
private int showSaveDiscardDialog() {
    String[] options = {"Save", "Discard", "Cancel"};
    return JOptionPane.showOptionDialog(this,
            "You have unsaved changes. Do you want to save or discard them?",
            "Unsaved Changes",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.WARNING_MESSAGE,
            null, options, options[0]);
}
public void setIsChanged(boolean isChanged) {
        this.isChanged = isChanged;
    }
    /**
     * Creates new form NewJFrame
     */

public SchedulePWSL() {
    initComponents();
    checkOrCreateDaysFolder(); // Check or create days folder
    btnCalArray();
    initCalendar();
    refreshCalendar();

    // Set models for the JLists
    lstAllPWSL.setModel(new DefaultListModel<>());
    lstTodayPWSL.setModel(new DefaultListModel<>());

    // Populate lstAllPWSL with XML file names
    populateLstAllPWSL();

    // Configure drag-and-drop
    lstAllPWSL.setDragEnabled(true);
    lstAllPWSL.setTransferHandler(new ListTransferHandler(this));
    lstTodayPWSL.setDragEnabled(true);
    lstTodayPWSL.setDropMode(javax.swing.DropMode.INSERT);
    lstTodayPWSL.setTransferHandler(new ListTransferHandler(this));

    // Track changes to lstTodayPWSL
    lstTodayPWSL.addListSelectionListener(e -> isChanged = true);
    
// Add window listener for closing event
addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        if (isChanged) {
            int response = showSaveDiscardDialog();
            if (response == 0) { // Save
                btnSaveActionPerformed(null); // Save changes
                dispose();
            } else if (response == 1) { // Discard
                dispose(); // Discard changes and close
            } else { // Cancel
                setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); // Do nothing
            }
        } else {
            dispose();
        }
    }
});
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCal = new javax.swing.JPanel();
        btnCal1 = new javax.swing.JButton();
        btnCal3 = new javax.swing.JButton();
        btnCal4 = new javax.swing.JButton();
        btnCal2 = new javax.swing.JButton();
        btnCal5 = new javax.swing.JButton();
        btnCal6 = new javax.swing.JButton();
        btnCal7 = new javax.swing.JButton();
        btnCal8 = new javax.swing.JButton();
        btnCal9 = new javax.swing.JButton();
        btnCal10 = new javax.swing.JButton();
        btnCal11 = new javax.swing.JButton();
        btnCal12 = new javax.swing.JButton();
        btnCal13 = new javax.swing.JButton();
        btnCal14 = new javax.swing.JButton();
        btnCal15 = new javax.swing.JButton();
        btnCal16 = new javax.swing.JButton();
        btnCal17 = new javax.swing.JButton();
        btnCal18 = new javax.swing.JButton();
        btnCal19 = new javax.swing.JButton();
        btnCal20 = new javax.swing.JButton();
        btnCal21 = new javax.swing.JButton();
        btnCal27 = new javax.swing.JButton();
        btnCal22 = new javax.swing.JButton();
        btnCal28 = new javax.swing.JButton();
        btnCal26 = new javax.swing.JButton();
        btnCal23 = new javax.swing.JButton();
        btnCal24 = new javax.swing.JButton();
        btnCal25 = new javax.swing.JButton();
        btnCal29 = new javax.swing.JButton();
        btnCal30 = new javax.swing.JButton();
        btnCal31 = new javax.swing.JButton();
        btnCal33 = new javax.swing.JButton();
        btnCal32 = new javax.swing.JButton();
        btnCal34 = new javax.swing.JButton();
        btnCal35 = new javax.swing.JButton();
        btnCal40 = new javax.swing.JButton();
        btnCal38 = new javax.swing.JButton();
        btnCal39 = new javax.swing.JButton();
        btnCal37 = new javax.swing.JButton();
        btnCal41 = new javax.swing.JButton();
        btnCal36 = new javax.swing.JButton();
        btnCal42 = new javax.swing.JButton();
        ptnNavMonthYear = new javax.swing.JPanel();
        btnYearBack = new javax.swing.JButton();
        btnMonthBack = new javax.swing.JButton();
        lblMonthYear = new javax.swing.JLabel();
        btnYearFwd = new javax.swing.JButton();
        btnMonthFwd = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jtbIcons = new javax.swing.JToolBar();
        tbbGap3 = new javax.swing.JButton();
        tbbAddPWSL = new javax.swing.JButton();
        tbbGap2 = new javax.swing.JButton();
        tbbMinusPWSL = new javax.swing.JButton();
        tbbGap1 = new javax.swing.JButton();
        tbbDuplicateDay = new javax.swing.JButton();
        tbbGap4 = new javax.swing.JButton();
        tbbSchedulePWSL = new javax.swing.JButton();
        btnClearAll = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jtfSelectedDay = new javax.swing.JTextField();
        scpToday = new javax.swing.JScrollPane();
        lstTodayPWSL = new javax.swing.JList<>();
        scpAll = new javax.swing.JScrollPane();
        lstAllPWSL = new javax.swing.JList<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        mnbSchedule = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mnuNew = new javax.swing.JMenu();
        mniNewCal = new javax.swing.JMenuItem();
        mniNewPWSL = new javax.swing.JMenuItem();
        mnuRun = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mniOpenCal = new javax.swing.JMenuItem();
        mniEditPWSL = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnuImport = new javax.swing.JMenu();
        mniImpCal = new javax.swing.JMenuItem();
        mniImpPWSL = new javax.swing.JMenuItem();
        mnuExport = new javax.swing.JMenu();
        mniExpCal = new javax.swing.JMenuItem();
        mniExpPWSL = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        mniSave = new javax.swing.JMenuItem();
        mniExit = new javax.swing.JMenuItem();
        mnuEdit = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PWSL Scheduler\n"); // NOI18N
        setBackground(new java.awt.Color(0, 102, 255));

        pnlCal.setBorder(new javax.swing.border.MatteBorder(null));

        btnCal1.setText("#");
        btnCal1.setToolTipText("Select day to view schedule in the right-pane");
        btnCal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal1ActionPerformed(evt);
            }
        });

        btnCal3.setText("#");
        btnCal3.setToolTipText("Select day to view schedule in the right-pane");
        btnCal3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal3ActionPerformed(evt);
            }
        });

        btnCal4.setText("#");
        btnCal4.setToolTipText("Select day to view schedule in the right-pane");
        btnCal4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal4ActionPerformed(evt);
            }
        });

        btnCal2.setText("#");
        btnCal2.setToolTipText("Select day to view schedule in the right-pane");
        btnCal2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal2ActionPerformed(evt);
            }
        });

        btnCal5.setText("#");
        btnCal5.setToolTipText("Select day to view schedule in the right-pane");
        btnCal5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal5ActionPerformed(evt);
            }
        });

        btnCal6.setText("#");
        btnCal6.setToolTipText("Select day to view schedule in the right-pane");
        btnCal6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal6ActionPerformed(evt);
            }
        });

        btnCal7.setText("#");
        btnCal7.setToolTipText("Select day to view schedule in the right-pane");
        btnCal7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal7ActionPerformed(evt);
            }
        });

        btnCal8.setText("#");
        btnCal8.setToolTipText("Select day to view schedule in the right-pane");
        btnCal8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal8ActionPerformed(evt);
            }
        });

        btnCal9.setText("#");
        btnCal9.setToolTipText("Select day to view schedule in the right-pane");
        btnCal9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal9ActionPerformed(evt);
            }
        });

        btnCal10.setText("#");
        btnCal10.setToolTipText("Select day to view schedule in the right-pane");
        btnCal10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal10ActionPerformed(evt);
            }
        });

        btnCal11.setText("#");
        btnCal11.setToolTipText("Select day to view schedule in the right-pane");
        btnCal11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal11ActionPerformed(evt);
            }
        });

        btnCal12.setText("#");
        btnCal12.setToolTipText("Select day to view schedule in the right-pane");
        btnCal12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal12ActionPerformed(evt);
            }
        });

        btnCal13.setText("#");
        btnCal13.setToolTipText("Select day to view schedule in the right-pane");
        btnCal13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal13ActionPerformed(evt);
            }
        });

        btnCal14.setText("#");
        btnCal14.setToolTipText("Select day to view schedule in the right-pane");
        btnCal14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal14ActionPerformed(evt);
            }
        });

        btnCal15.setText("#");
        btnCal15.setToolTipText("Select day to view schedule in the right-pane");
        btnCal15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal15ActionPerformed(evt);
            }
        });

        btnCal16.setText("#");
        btnCal16.setToolTipText("Select day to view schedule in the right-pane");
        btnCal16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal16ActionPerformed(evt);
            }
        });

        btnCal17.setText("#");
        btnCal17.setToolTipText("Select day to view schedule in the right-pane");
        btnCal17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal17ActionPerformed(evt);
            }
        });

        btnCal18.setText("#");
        btnCal18.setToolTipText("Select day to view schedule in the right-pane");
        btnCal18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal18ActionPerformed(evt);
            }
        });

        btnCal19.setText("#");
        btnCal19.setToolTipText("Select day to view schedule in the right-pane");
        btnCal19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal19ActionPerformed(evt);
            }
        });

        btnCal20.setText("#");
        btnCal20.setToolTipText("Select day to view schedule in the right-pane");
        btnCal20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal20ActionPerformed(evt);
            }
        });

        btnCal21.setText("#");
        btnCal21.setToolTipText("Select day to view schedule in the right-pane");
        btnCal21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal21ActionPerformed(evt);
            }
        });

        btnCal27.setText("#");
        btnCal27.setToolTipText("Select day to view schedule in the right-pane");
        btnCal27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal27ActionPerformed(evt);
            }
        });

        btnCal22.setText("#");
        btnCal22.setToolTipText("Select day to view schedule in the right-pane");
        btnCal22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal22ActionPerformed(evt);
            }
        });

        btnCal28.setText("#");
        btnCal28.setToolTipText("Select day to view schedule in the right-pane");
        btnCal28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal28ActionPerformed(evt);
            }
        });

        btnCal26.setText("#");
        btnCal26.setToolTipText("Select day to view schedule in the right-pane");
        btnCal26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal26ActionPerformed(evt);
            }
        });

        btnCal23.setText("#");
        btnCal23.setToolTipText("Select day to view schedule in the right-pane");
        btnCal23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal23ActionPerformed(evt);
            }
        });

        btnCal24.setText("#");
        btnCal24.setToolTipText("Select day to view schedule in the right-pane");
        btnCal24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal24ActionPerformed(evt);
            }
        });

        btnCal25.setText("#");
        btnCal25.setToolTipText("Select day to view schedule in the right-pane");
        btnCal25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal25ActionPerformed(evt);
            }
        });

        btnCal29.setText("#");
        btnCal29.setToolTipText("Select day to view schedule in the right-pane");
        btnCal29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal29ActionPerformed(evt);
            }
        });

        btnCal30.setText("#");
        btnCal30.setToolTipText("Select day to view schedule in the right-pane");
        btnCal30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal30ActionPerformed(evt);
            }
        });

        btnCal31.setText("#");
        btnCal31.setToolTipText("Select day to view schedule in the right-pane");
        btnCal31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal31ActionPerformed(evt);
            }
        });

        btnCal33.setText("#");
        btnCal33.setToolTipText("Select day to view schedule in the right-pane");
        btnCal33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal33ActionPerformed(evt);
            }
        });

        btnCal32.setText("#");
        btnCal32.setToolTipText("Select day to view schedule in the right-pane");
        btnCal32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal32ActionPerformed(evt);
            }
        });

        btnCal34.setText("#");
        btnCal34.setToolTipText("Select day to view schedule in the right-pane");
        btnCal34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal34ActionPerformed(evt);
            }
        });

        btnCal35.setText("#");
        btnCal35.setToolTipText("Select day to view schedule in the right-pane");
        btnCal35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal35ActionPerformed(evt);
            }
        });

        btnCal40.setText("#");
        btnCal40.setToolTipText("Select day to view schedule in the right-pane");
        btnCal40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal40ActionPerformed(evt);
            }
        });

        btnCal38.setText("#");
        btnCal38.setToolTipText("Select day to view schedule in the right-pane");
        btnCal38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal38ActionPerformed(evt);
            }
        });

        btnCal39.setText("#");
        btnCal39.setToolTipText("Select day to view schedule in the right-pane");
        btnCal39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal39ActionPerformed(evt);
            }
        });

        btnCal37.setText("#");
        btnCal37.setToolTipText("Select day to view schedule in the right-pane");
        btnCal37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal37ActionPerformed(evt);
            }
        });

        btnCal41.setText("#");
        btnCal41.setToolTipText("Select day to view schedule in the right-pane");
        btnCal41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal41ActionPerformed(evt);
            }
        });

        btnCal36.setText("#");
        btnCal36.setToolTipText("Select day to view schedule in the right-pane");
        btnCal36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal36ActionPerformed(evt);
            }
        });

        btnCal42.setText("#");
        btnCal42.setToolTipText("Select day to view schedule in the right-pane");
        btnCal42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCal42ActionPerformed(evt);
            }
        });

        ptnNavMonthYear.setBorder(new javax.swing.border.MatteBorder(null));

        btnYearBack.setText("<Year");
        btnYearBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnYearBackActionPerformed(evt);
            }
        });

        btnMonthBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mysiexperience/assets/20x20Size_SVG/Back2_PWSL.png"))); // NOI18N
        btnMonthBack.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/mysiexperience/assets/20x20Size_SVG/Back_PWSL.png"))); // NOI18N
        btnMonthBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMonthBackActionPerformed(evt);
            }
        });

        lblMonthYear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMonthYear.setText("Month, Year");

        btnYearFwd.setText("Year>");
        btnYearFwd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnYearFwdActionPerformed(evt);
            }
        });

        btnMonthFwd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mysiexperience/assets/20x20Size_SVG/Forward2_PWSL.png"))); // NOI18N
        btnMonthFwd.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/mysiexperience/assets/20x20Size_SVG/Forward_PWSL.png"))); // NOI18N
        btnMonthFwd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMonthFwdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ptnNavMonthYearLayout = new javax.swing.GroupLayout(ptnNavMonthYear);
        ptnNavMonthYear.setLayout(ptnNavMonthYearLayout);
        ptnNavMonthYearLayout.setHorizontalGroup(
            ptnNavMonthYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ptnNavMonthYearLayout.createSequentialGroup()
                .addGap(187, 187, 187)
                .addComponent(btnYearBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMonthBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMonthYear, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMonthFwd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnYearFwd)
                .addContainerGap(189, Short.MAX_VALUE))
        );
        ptnNavMonthYearLayout.setVerticalGroup(
            ptnNavMonthYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ptnNavMonthYearLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ptnNavMonthYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMonthYear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(ptnNavMonthYearLayout.createSequentialGroup()
                        .addGroup(ptnNavMonthYearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnMonthFwd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnMonthBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnYearFwd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnYearBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Sunday");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Monday");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Tuesday");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Wednesday");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Thursday");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Friday");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Saturday");

        javax.swing.GroupLayout pnlCalLayout = new javax.swing.GroupLayout(pnlCal);
        pnlCal.setLayout(pnlCalLayout);
        pnlCalLayout.setHorizontalGroup(
            pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnCal29, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal36, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCal16, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal23, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal37, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal30, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal9, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCal3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal10, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal17, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal24, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal31, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal38, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnCal32, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal39, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlCalLayout.createSequentialGroup()
                        .addGroup(pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCal12, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCal19, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCal26, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCal33, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCal40, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCal5, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCal6, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal13, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal20, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal27, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal34, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal41, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCal7, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal14, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal21, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal28, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal35, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCal42, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCalLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(ptnNavMonthYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlCalLayout.setVerticalGroup(
            pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ptnNavMonthYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(pnlCalLayout.createSequentialGroup()
                                    .addComponent(btnCal6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnCal12, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCal13, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnCal19, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCal20, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCal26, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnCal33, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCal34, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnCal40, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCal41, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(pnlCalLayout.createSequentialGroup()
                                    .addComponent(btnCal7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCal14, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCal21, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCal28, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCal35, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCal42, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlCalLayout.createSequentialGroup()
                                    .addComponent(btnCal27, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(120, 120, 120)))
                            .addGroup(pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(pnlCalLayout.createSequentialGroup()
                                    .addComponent(btnCal2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCal9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCal16, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCal23, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCal30, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCal37, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlCalLayout.createSequentialGroup()
                                    .addGroup(pnlCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnCal4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCal5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCal11, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCal18, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCal25, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCal32, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCal39, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(pnlCalLayout.createSequentialGroup()
                            .addComponent(btnCal1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnCal8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnCal15, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnCal22, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnCal29, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnCal36, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlCalLayout.createSequentialGroup()
                        .addComponent(btnCal3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCal10, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCal17, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCal24, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCal31, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCal38, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtbIcons.setBorder(null);
        jtbIcons.setRollover(true);
        jtbIcons.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        tbbGap3.setBorder(null);
        tbbGap3.setContentAreaFilled(false);
        tbbGap3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbbGap3.setEnabled(false);
        tbbGap3.setFocusable(false);
        tbbGap3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbbGap3.setMaximumSize(new java.awt.Dimension(5, 20));
        tbbGap3.setMinimumSize(new java.awt.Dimension(5, 20));
        tbbGap3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbbGap3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbbGap3ActionPerformed(evt);
            }
        });
        jtbIcons.add(tbbGap3);

        tbbAddPWSL.setBackground(getBackground());
        tbbAddPWSL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mysiexperience/assets/20x20Size_SVG/Add_PWSL.png"))); // NOI18N
        tbbAddPWSL.setToolTipText("Add PWSL");
        tbbAddPWSL.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tbbAddPWSL.setFocusable(false);
        tbbAddPWSL.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbbAddPWSL.setNextFocusableComponent(tbbMinusPWSL);
        tbbAddPWSL.setRolloverEnabled(false);
        tbbAddPWSL.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbbAddPWSL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbbAddPWSLActionPerformed(evt);
            }
        });
        jtbIcons.add(tbbAddPWSL);

        tbbGap2.setBorder(null);
        tbbGap2.setContentAreaFilled(false);
        tbbGap2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbbGap2.setEnabled(false);
        tbbGap2.setFocusable(false);
        tbbGap2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbbGap2.setMaximumSize(new java.awt.Dimension(5, 20));
        tbbGap2.setMinimumSize(new java.awt.Dimension(5, 20));
        tbbGap2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbbGap2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbbGap2ActionPerformed(evt);
            }
        });
        jtbIcons.add(tbbGap2);

        tbbMinusPWSL.setBackground(getBackground());
        tbbMinusPWSL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mysiexperience/assets/20x20Size_SVG/Minus_PWSL.png"))); // NOI18N
        tbbMinusPWSL.setToolTipText("Remove PWSL");
        tbbMinusPWSL.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tbbMinusPWSL.setFocusable(false);
        tbbMinusPWSL.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbbMinusPWSL.setNextFocusableComponent(tbbDuplicateDay);
        tbbMinusPWSL.setRolloverEnabled(false);
        tbbMinusPWSL.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbbMinusPWSL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbbMinusPWSLActionPerformed(evt);
            }
        });
        jtbIcons.add(tbbMinusPWSL);

        tbbGap1.setBorder(null);
        tbbGap1.setContentAreaFilled(false);
        tbbGap1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbbGap1.setEnabled(false);
        tbbGap1.setFocusable(false);
        tbbGap1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbbGap1.setMaximumSize(new java.awt.Dimension(5, 20));
        tbbGap1.setMinimumSize(new java.awt.Dimension(5, 20));
        tbbGap1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbbGap1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbbGap1ActionPerformed(evt);
            }
        });
        jtbIcons.add(tbbGap1);

        tbbDuplicateDay.setBackground(getBackground());
        tbbDuplicateDay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mysiexperience/assets/20x20Size_SVG/Copy_PWSL.png"))); // NOI18N
        tbbDuplicateDay.setToolTipText("Duplicate Day");
        tbbDuplicateDay.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tbbDuplicateDay.setFocusable(false);
        tbbDuplicateDay.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbbDuplicateDay.setNextFocusableComponent(tbbSchedulePWSL);
        tbbDuplicateDay.setRolloverEnabled(false);
        tbbDuplicateDay.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbbDuplicateDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbbDuplicateDayActionPerformed(evt);
            }
        });
        jtbIcons.add(tbbDuplicateDay);

        tbbGap4.setBorder(null);
        tbbGap4.setContentAreaFilled(false);
        tbbGap4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbbGap4.setEnabled(false);
        tbbGap4.setFocusable(false);
        tbbGap4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbbGap4.setMaximumSize(new java.awt.Dimension(5, 20));
        tbbGap4.setMinimumSize(new java.awt.Dimension(5, 20));
        tbbGap4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbbGap4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbbGap4ActionPerformed(evt);
            }
        });
        jtbIcons.add(tbbGap4);

        tbbSchedulePWSL.setBackground(getBackground());
        tbbSchedulePWSL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mysiexperience/assets/20x20Size_SVG/Cal_PWSL.png"))); // NOI18N
        tbbSchedulePWSL.setToolTipText("Schedule PWSL...");
        tbbSchedulePWSL.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tbbSchedulePWSL.setFocusable(false);
        tbbSchedulePWSL.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tbbSchedulePWSL.setRolloverEnabled(false);
        tbbSchedulePWSL.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbbSchedulePWSL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbbSchedulePWSLActionPerformed(evt);
            }
        });
        jtbIcons.add(tbbSchedulePWSL);

        btnClearAll.setText("Clear All...");
        btnClearAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearAllActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        jLabel1.setText(" Selected Day: ");

        jtfSelectedDay.setText("February 3, 2023");

        lstTodayPWSL.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lstTodayPWSL.setDragEnabled(true);
        lstTodayPWSL.setDropMode(javax.swing.DropMode.INSERT);
        scpToday.setViewportView(lstTodayPWSL);

        lstAllPWSL.setDoubleBuffered(true);
        lstAllPWSL.setDragEnabled(true);
        lstAllPWSL.setDropMode(javax.swing.DropMode.INSERT);
        scpAll.setViewportView(lstAllPWSL);
        lstAllPWSL.getAccessibleContext().setAccessibleName("");

        jLabel9.setText("All PWSLs:");

        jLabel10.setText("Today's PWSLs:");

        mnbSchedule.setName("PWSL Calendar"); // NOI18N

        mnuFile.setText("File");

        mnuNew.setText("New");

        mniNewCal.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mniNewCal.setText("New Calendar");
        mnuNew.add(mniNewCal);

        mniNewPWSL.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        mniNewPWSL.setText("New PWSL");
        mniNewPWSL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniNewPWSLActionPerformed(evt);
            }
        });
        mnuNew.add(mniNewPWSL);

        mnuFile.add(mnuNew);

        mnuRun.setText("Run Today's PWSL");
        mnuRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuRunActionPerformed(evt);
            }
        });
        mnuFile.add(mnuRun);
        mnuFile.add(jSeparator2);

        mniOpenCal.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mniOpenCal.setText("Open Calendar...");
        mnuFile.add(mniOpenCal);

        mniEditPWSL.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mniEditPWSL.setText("Edit PWSL...");
        mniEditPWSL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniEditPWSLActionPerformed(evt);
            }
        });
        mnuFile.add(mniEditPWSL);
        mnuFile.add(jSeparator3);

        mnuImport.setText("Import");

        mniImpCal.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mniImpCal.setText("Import Calendars");
        mnuImport.add(mniImpCal);

        mniImpPWSL.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        mniImpPWSL.setText("Import PWSLs");
        mnuImport.add(mniImpPWSL);

        mnuFile.add(mnuImport);

        mnuExport.setText("Export");

        mniExpCal.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mniExpCal.setText("Export Calendars");
        mnuExport.add(mniExpCal);

        mniExpPWSL.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        mniExpPWSL.setText("Export PWSLs");
        mnuExport.add(mniExpPWSL);

        mnuFile.add(mnuExport);
        mnuFile.add(jSeparator4);

        mniSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mniSave.setText("Save");
        mniSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSaveActionPerformed(evt);
            }
        });
        mnuFile.add(mniSave);

        mniExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mniExit.setText("Exit");
        mniExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniExitActionPerformed(evt);
            }
        });
        mnuFile.add(mniExit);

        mnbSchedule.add(mnuFile);

        mnuEdit.setText("Edit");
        mnbSchedule.add(mnuEdit);

        setJMenuBar(mnbSchedule);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlCal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(btnClear)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnClearAll)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnSave)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnClose))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtfSelectedDay))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(244, 244, 244))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(scpToday, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(scpAll, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jtbIcons, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jtbIcons, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtfSelectedDay))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scpToday, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scpAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnClear)
                            .addComponent(btnClearAll)
                            .addComponent(btnSave)
                            .addComponent(btnClose)))
                    .addComponent(pnlCal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnClearAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearAllActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClearAllActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
    if (selectedDayOfMonth != null && selectedMonthOfYear != null && selectedYear != null) {
        String fileName = System.getProperty("user.home") + "/Documents/MySIExperience/Configuration/days/"
                + selectedDayOfMonth + selectedMonthOfYear + selectedYear + ".xml";
        saveLstTodayPWSLToXML(fileName);
        isChanged = false; // Reset the flag after saving
    }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        exitApp();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void mniSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mniSaveActionPerformed

    private void tbbMinusPWSLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbbMinusPWSLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbbMinusPWSLActionPerformed

    private void tbbDuplicateDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbbDuplicateDayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbbDuplicateDayActionPerformed

    private void tbbSchedulePWSLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbbSchedulePWSLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbbSchedulePWSLActionPerformed

    private void tbbAddPWSLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbbAddPWSLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbbAddPWSLActionPerformed

    private void tbbGap1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbbGap1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbbGap1ActionPerformed

    private void tbbGap2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbbGap2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbbGap2ActionPerformed

    private void tbbGap3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbbGap3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbbGap3ActionPerformed

    private void mniExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniExitActionPerformed
        exitApp();
    }//GEN-LAST:event_mniExitActionPerformed

    private void btnMonthFwdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMonthFwdActionPerformed
        monthOfYear = incrementMonth(monthOfYear);
        refreshCalendar();
    }//GEN-LAST:event_btnMonthFwdActionPerformed

    private void btnYearFwdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnYearFwdActionPerformed
        int intYear = Integer.parseInt(year);
        intYear++;
        year = String.valueOf(intYear);
        refreshCalendar();
    }//GEN-LAST:event_btnYearFwdActionPerformed

    private void btnMonthBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMonthBackActionPerformed
        monthOfYear = decrementMonth(monthOfYear);
        refreshCalendar();

    }//GEN-LAST:event_btnMonthBackActionPerformed

    private void btnYearBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnYearBackActionPerformed
        int intYear = Integer.parseInt(year);
        intYear--;
        year = String.valueOf(intYear);
        refreshCalendar();
    }//GEN-LAST:event_btnYearBackActionPerformed

    private void btnCal42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal42ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal42ActionPerformed

    private void btnCal36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal36ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal36ActionPerformed

    private void btnCal41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal41ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal41ActionPerformed

    private void btnCal37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal37ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal37ActionPerformed

    private void btnCal39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal39ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal39ActionPerformed

    private void btnCal38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal38ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal38ActionPerformed

    private void btnCal40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal40ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal40ActionPerformed

    private void btnCal35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal35ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal35ActionPerformed

    private void btnCal34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal34ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal34ActionPerformed

    private void btnCal32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal32ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal32ActionPerformed

    private void btnCal33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal33ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal33ActionPerformed

    private void btnCal31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal31ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal31ActionPerformed

    private void btnCal30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal30ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal30ActionPerformed

    private void btnCal29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal29ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal29ActionPerformed

    private void btnCal25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal25ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal25ActionPerformed

    private void btnCal24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal24ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal24ActionPerformed

    private void btnCal23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal23ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal23ActionPerformed

    private void btnCal26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal26ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal26ActionPerformed

    private void btnCal28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal28ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal28ActionPerformed

    private void btnCal22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal22ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal22ActionPerformed

    private void btnCal27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal27ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal27ActionPerformed

    private void btnCal21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal21ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal21ActionPerformed

    private void btnCal20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal20ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal20ActionPerformed

    private void btnCal19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal19ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal19ActionPerformed

    private void btnCal18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal18ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal18ActionPerformed

    private void btnCal17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal17ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal17ActionPerformed

    private void btnCal16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal16ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal16ActionPerformed

    private void btnCal15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal15ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal15ActionPerformed

    private void btnCal14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal14ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal14ActionPerformed

    private void btnCal13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal13ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal13ActionPerformed

    private void btnCal12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal12ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal12ActionPerformed

    private void btnCal11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal11ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal11ActionPerformed

    private void btnCal10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal10ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal10ActionPerformed

    private void btnCal9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal9ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal9ActionPerformed

    private void btnCal8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal8ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal8ActionPerformed

    private void btnCal7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal7ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal7ActionPerformed

    private void btnCal6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal6ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal6ActionPerformed

    private void btnCal5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal5ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal5ActionPerformed

    private void btnCal2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal2ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal2ActionPerformed

    private void btnCal4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal4ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal4ActionPerformed

    private void btnCal3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal3ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);
    }//GEN-LAST:event_btnCal3ActionPerformed

    private void btnCal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCal1ActionPerformed
        JButton clickedButton = (JButton) evt.getSource();
        selectButton(clickedButton);

    }//GEN-LAST:event_btnCal1ActionPerformed

    private void tbbGap4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbbGap4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbbGap4ActionPerformed

    private void mniNewPWSLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniNewPWSLActionPerformed
        PWSL.main(null);
        
    }//GEN-LAST:event_mniNewPWSLActionPerformed

    private void mniEditPWSLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniEditPWSLActionPerformed
        PWSL pwslInstance = new PWSL ();
        pwslInstance.setVisible(true);
    }//GEN-LAST:event_mniEditPWSLActionPerformed

    private void mnuRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuRunActionPerformed
            DefaultListModel<String> listModel = (DefaultListModel<String>) lstTodayPWSL.getModel();
    
    // Iterate through the list and run each PWSL
    for (int i = 0; i < listModel.getSize(); i++) {
        String pwslName = listModel.getElementAt(i);
        
        // Assuming PWSL has a static method to run a specific instance by name
        // You need to implement this method in the PWSL class
               PWSL pwslInstance = new PWSL();
        pwslInstance.runPWSLByName(pwslName);
    }
    }//GEN-LAST:event_mnuRunActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SchedulePWSL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SchedulePWSL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SchedulePWSL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SchedulePWSL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SchedulePWSL().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCal1;
    private javax.swing.JButton btnCal10;
    private javax.swing.JButton btnCal11;
    private javax.swing.JButton btnCal12;
    private javax.swing.JButton btnCal13;
    private javax.swing.JButton btnCal14;
    private javax.swing.JButton btnCal15;
    private javax.swing.JButton btnCal16;
    private javax.swing.JButton btnCal17;
    private javax.swing.JButton btnCal18;
    private javax.swing.JButton btnCal19;
    private javax.swing.JButton btnCal2;
    private javax.swing.JButton btnCal20;
    private javax.swing.JButton btnCal21;
    private javax.swing.JButton btnCal22;
    private javax.swing.JButton btnCal23;
    private javax.swing.JButton btnCal24;
    private javax.swing.JButton btnCal25;
    private javax.swing.JButton btnCal26;
    private javax.swing.JButton btnCal27;
    private javax.swing.JButton btnCal28;
    private javax.swing.JButton btnCal29;
    private javax.swing.JButton btnCal3;
    private javax.swing.JButton btnCal30;
    private javax.swing.JButton btnCal31;
    private javax.swing.JButton btnCal32;
    private javax.swing.JButton btnCal33;
    private javax.swing.JButton btnCal34;
    private javax.swing.JButton btnCal35;
    private javax.swing.JButton btnCal36;
    private javax.swing.JButton btnCal37;
    private javax.swing.JButton btnCal38;
    private javax.swing.JButton btnCal39;
    private javax.swing.JButton btnCal4;
    private javax.swing.JButton btnCal40;
    private javax.swing.JButton btnCal41;
    private javax.swing.JButton btnCal42;
    private javax.swing.JButton btnCal5;
    private javax.swing.JButton btnCal6;
    private javax.swing.JButton btnCal7;
    private javax.swing.JButton btnCal8;
    private javax.swing.JButton btnCal9;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClearAll;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnMonthBack;
    private javax.swing.JButton btnMonthFwd;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnYearBack;
    private javax.swing.JButton btnYearFwd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JToolBar jtbIcons;
    private javax.swing.JTextField jtfSelectedDay;
    private javax.swing.JLabel lblMonthYear;
    private javax.swing.JList<String> lstAllPWSL;
    private javax.swing.JList<String> lstTodayPWSL;
    private javax.swing.JMenuBar mnbSchedule;
    private javax.swing.JMenuItem mniEditPWSL;
    private javax.swing.JMenuItem mniExit;
    private javax.swing.JMenuItem mniExpCal;
    private javax.swing.JMenuItem mniExpPWSL;
    private javax.swing.JMenuItem mniImpCal;
    private javax.swing.JMenuItem mniImpPWSL;
    private javax.swing.JMenuItem mniNewCal;
    private javax.swing.JMenuItem mniNewPWSL;
    private javax.swing.JMenuItem mniOpenCal;
    private javax.swing.JMenuItem mniSave;
    private javax.swing.JMenu mnuEdit;
    private javax.swing.JMenu mnuExport;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenu mnuImport;
    private javax.swing.JMenu mnuNew;
    private javax.swing.JMenuItem mnuRun;
    private javax.swing.JPanel pnlCal;
    private javax.swing.JPanel ptnNavMonthYear;
    private javax.swing.JScrollPane scpAll;
    private javax.swing.JScrollPane scpToday;
    private javax.swing.JButton tbbAddPWSL;
    private javax.swing.JButton tbbDuplicateDay;
    private javax.swing.JButton tbbGap1;
    private javax.swing.JButton tbbGap2;
    private javax.swing.JButton tbbGap3;
    private javax.swing.JButton tbbGap4;
    private javax.swing.JButton tbbMinusPWSL;
    private javax.swing.JButton tbbSchedulePWSL;
    // End of variables declaration//GEN-END:variables
}
