/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mysiexperience;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author z004kptc
 */
public class MySIExperience {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create resource folder structure
        MySIExperienceFolder();
        BinFolder();
        ConfigurationFolder();
        PWSLTemplatesFolder();
        CompletedPWSLsFolder();
        ChromeWebdriverFolder();
        ChromeDriverWin64Folder();
        DaysFolder();

        // Call the main method of MySIGUI 
        MySIGUI.main(args);
    }

    public static void MySIExperienceFolder() {
        // Get the user's home directory
        String userHome = System.getProperty("user.home");
        // Define the directory path within the user's home directory
        Path path = Paths.get(userHome, "Documents", "MySIExperience");

        try {
            // Check if the directory exists
            if (!Files.exists(path)) {
                // Create the directory
                Files.createDirectories(path);
                System.out.println("Directory created successfully at: " + path.toString());
            }
        } catch (Exception e) {
            // Handle the exception
            System.err.println("Failed to create directory: " + e.getMessage());
        }
    }

    public static void BinFolder() {
        // Get the user's home directory
        String userHome = System.getProperty("user.home");
        // Define the directory path within the user's home directory
        Path path = Paths.get(userHome, "Documents", "MySIExperience", "bin");

        try {
            // Check if the directory exists
            if (!Files.exists(path)) {
                // Create the directory
                Files.createDirectories(path);
                System.out.println("Directory created successfully at: " + path.toString());
            }
        } catch (Exception e) {
            // Handle the exception
            System.err.println("Failed to create directory: " + e.getMessage());
        }
    }

    public static void CompletedPWSLsFolder() {
        // Get the user's home directory
        String userHome = System.getProperty("user.home");
        // Define the directory path within the user's home directory
        Path path = Paths.get(userHome, "Documents", "MySIExperience", "Completed_PWSLs");

        try {
            // Check if the directory exists
            if (!Files.exists(path)) {
                // Create the directory
                Files.createDirectories(path);
                System.out.println("Directory created successfully at: " + path.toString());
            }
        } catch (Exception e) {
            // Handle the exception
            System.err.println("Failed to create directory: " + e.getMessage());
        }
    }

    public static void ConfigurationFolder() {
        // Get the user's home directory
        String userHome = System.getProperty("user.home");
        // Define the directory path within the user's home directory
        Path path = Paths.get(userHome, "Documents", "MySIExperience", "Configuration");

        try {
            // Check if the directory exists
            if (!Files.exists(path)) {
                // Create the directory
                Files.createDirectories(path);
                System.out.println("Directory created successfully at: " + path.toString());
            }
        } catch (Exception e) {
            // Handle the exception
            System.err.println("Failed to create directory: " + e.getMessage());
        }
    }

    public static void PWSLTemplatesFolder() {
        // Get the user's home directory
        String userHome = System.getProperty("user.home");
        // Define the directory path within the user's home directory
        Path path = Paths.get(userHome, "Documents", "MySIExperience", "PWSL_Templates");

        try {
            // Check if the directory exists
            if (!Files.exists(path)) {
                // Create the directory
                Files.createDirectories(path);
                System.out.println("Directory created successfully at: " + path.toString());
            }
        } catch (Exception e) {
            // Handle the exception
            System.err.println("Failed to create directory: " + e.getMessage());
        }
    }
    public static void ChromeWebdriverFolder() {
        // Get the user's home directory
        String userHome = System.getProperty("user.home");
        // Define the directory path within the user's home directory
        Path path = Paths.get(userHome, "Documents", "MySIExperience", "Configuration", "Chrome Webdriver");

        try {
            // Check if the directory exists
            if (!Files.exists(path)) {
                // Create the directory
                Files.createDirectories(path);
                System.out.println("Directory created successfully at: " + path.toString());
            }
        } catch (Exception e) {
            // Handle the exception
            System.err.println("Failed to create directory: " + e.getMessage());
        }
    }
    public static void DaysFolder() {
        // Get the user's home directory
        String userHome = System.getProperty("user.home");
        // Define the directory path within the user's home directory
        Path path = Paths.get(userHome, "Documents", "MySIExperience", "Configuration", "days");

        try {
            // Check if the directory exists
            if (!Files.exists(path)) {
                // Create the directory
                Files.createDirectories(path);
                System.out.println("Directory created successfully at: " + path.toString());
            }
        } catch (Exception e) {
            // Handle the exception
            System.err.println("Failed to create directory: " + e.getMessage());
        }
    }
        public static void ChromeDriverWin64Folder() {
        // Get the user's home directory
        String userHome = System.getProperty("user.home");
        // Define the directory path within the user's home directory
        Path path = Paths.get(userHome, "Documents", "MySIExperience", "Configuration", "Chrome Webdriver", "chromedriver-win64");

        try {
            // Check if the directory exists
            if (!Files.exists(path)) {
                // Create the directory
                Files.createDirectories(path);
                System.out.println("Directory created successfully at: " + path.toString());
            }
        } catch (Exception e) {
            // Handle the exception
            System.err.println("Failed to create directory: " + e.getMessage());
        }
    }
}
