/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mysiexperience;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ChromeDriverUpdater {
         String chromeDriverUrl = "https://googlechromelabs.github.io/chrome-for-testing/last-known-good-versions-with-downloads.json";
        Path configurationFolderPath = Paths.get(System.getProperty("user.home"), "Documents", "MySIExperience", "Configuration");
        String configurationFolderPathString = configurationFolderPath.toString();
        String downloadDir = configurationFolderPathString + "\\Chrome Webdriver";
        String jsonDir = configurationFolderPathString + "\\ChromeDriver_v.json";  
        String versionNew = "version";
        String platform = "";
        String chromedriverUrl = null;

                
    public static void main(String[] args) {
    ChromeDriverUpdater updater = new ChromeDriverUpdater();
    updater.fetchChromeJson();
    updater.checkChromeJson();
    String downloadedZipPath = updater.downloadChromeDriver();  
    // downloadedZipPath = ''UserHome''\Documents\MySIExperience\Configuration\Chrome Webdriver\chromedriver.zip


        try {
            updater.unzip(downloadedZipPath, updater.downloadDir);  // Correct usage
            System.out.println("Unzipping completed successfully.");
        } catch (IOException e) {
            System.err.println("Failed to unzip the file: " + e.getMessage());
        }

}
    //Method to download the JSON file. 
        public void fetchChromeJson() {
        try {
            // Make HTTP request to fetch JSON file
            URL url = new URL(chromeDriverUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read response
            InputStream inputStream = connection.getInputStream();

            // Save JSON file
            FileOutputStream outputStream = new FileOutputStream(jsonDir);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();

            System.out.println("JSON file downloaded successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
        //Method to check the json file for version
        public void checkChromeJson() {
        try {
            // Parse the downloaded JSON file and extract the version
            JSONParser parser = new JSONParser();
            FileReader fileReader = new FileReader(jsonDir);
            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);
            JSONObject channelsChannel = (JSONObject) jsonObject.get("channels");
            JSONObject stableChannel = (JSONObject) channelsChannel.get("Stable");
            versionNew = (String) stableChannel.get("version");
            System.out.println("The new Chrome Driver version = " + versionNew);
            JSONObject downloadsChannel = (JSONObject) stableChannel.get("downloads");
            JSONArray chromedriverDownloads = (JSONArray) downloadsChannel.get("chromedriver");
            
            systemOSDefine();
            //Win64
            if ("win64".equals(platform)){

            for (Object download : chromedriverDownloads) {
                JSONObject chromedriver = (JSONObject) download;
                String jplatform = (String) chromedriver.get("platform");
                if (jplatform.equals("win64")) {
                    chromedriverUrl = (String) chromedriver.get("url");
                    break;}
                }} 
            
            //Win32
            if ("win32".equals(platform)){

            for (Object download : chromedriverDownloads) {
                JSONObject chromedriver = (JSONObject) download;
                String jplatform = (String) chromedriver.get("platform");
                if (jplatform.equals("win32")) {
                    chromedriverUrl = (String) chromedriver.get("url");
                    break;}
                }}
                        
            //linux64
            if ("linux64".equals(platform)){

            for (Object download : chromedriverDownloads) {
                JSONObject chromedriver = (JSONObject) download;
                String jplatform = (String) chromedriver.get("platform");
                if (jplatform.equals("linux64")) {
                    chromedriverUrl = (String) chromedriver.get("url");
                    break;}
                }} 
            
            //mac-arm64
            if ("mac-arm64".equals(platform)){

            for (Object download : chromedriverDownloads) {
                JSONObject chromedriver = (JSONObject) download;
                String jplatform = (String) chromedriver.get("platform");
                if (jplatform.equals("mac-arm64")) {
                    chromedriverUrl = (String) chromedriver.get("url");
                    break;}
                }}
            
            //mac-64
            if ("mac-x64".equals(platform)){

            for (Object download : chromedriverDownloads) {
                JSONObject chromedriver = (JSONObject) download;
                String jplatform = (String) chromedriver.get("platform");
                if (jplatform.equals("mac-x64")) {
                    chromedriverUrl = (String) chromedriver.get("url");
                    break;}
                }}
            
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    // Method to unzip a zip file
    private void unzip(String zipFilePath, String destDir) throws IOException {
        // zipFilePath = ''UserHome''\Documents\MySIExperience\Configuration\Chrome Webdriver\chromedriver.zip
        // destDir     = ''UserHome''\Documents\MySIExperience\Configuration\Chrome Webdriver
        File destDirectory = new File(destDir);
        if (!destDirectory.exists()) {
            destDirectory.mkdir();
        }
        File tempDir = new File(destDir, "temp");
        tempDir.mkdir();
        
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry = zipIn.getNextEntry();
            while (entry != null) {
                String filePath = destDir + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    extractFile(zipIn, filePath);
                } else {
                    //Make the directory
                    new File(filePath).mkdir();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
        //move files and cleanup
        moveAndCleanup(tempDir, new File(destDir));
        FileUtils.deleteDirectory(tempDir); // Delete the temporary directory        
    }
// Helper method to move files from the temporary directory to the final destination
    private void moveAndCleanup(File sourceDir, File destDir) throws IOException {
        File[] files = sourceDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Process directories (expected to be 'chromedriver-win64')
                    moveAndCleanup(file, destDir); // Recursively move contents
                } else {
                    // Move files directly
                    Files.move(file.toPath(), new File(destDir, file.getName()).toPath());
                }
            }
        }
    }    
        // Method to extract a file from zip
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        File destFile = new File(filePath);
        File parentDir = destFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile))) {
            byte[] bytesIn = new byte[4096];
            int read;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }
    
    //Method to define the PC OS
    public void systemOSDefine() {
        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();
        

        if (osName.contains("linux")) {
            platform = "linux64";
        } else if (osName.contains("mac")) {
            if (osArch.contains("arm")) {
                platform = "mac-arm64";
            } else {
                platform = "mac-x64";
            }
        } else if (osName.contains("windows")) {
            platform = osArch.contains("64") ? "win64" : "win32";
        }

        System.out.println("Platform: " + platform);
    }

    private String downloadChromeDriver() {
    String zipFilePath = downloadDir + File.separator + "chromedriver.zip";  // This is the path where the zip file will be saved.
    
    try {
        URL url = new URL(chromedriverUrl);  // Convert the string URL to a URL object.
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();  // Open a connection to the URL.
        connection.setRequestMethod("GET");  // Set the request method to GET.

        // Create an input stream to read the data from the URL connection.
        InputStream inputStream = connection.getInputStream();

        // Ensure the directory where the file will be saved exists.
        File outputFile = new File(zipFilePath);
        outputFile.getParentFile().mkdirs();

        // Create an output stream to write the data to the file.
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();  // Close the FileOutputStream.
        inputStream.close();  // Close the InputStream.

        System.out.println("ChromeDriver downloaded successfully.");
        return zipFilePath;  // Return the path to the downloaded zip file.
    } catch (IOException e) {
        e.printStackTrace();
        return null;  // Return null if there was an error.
    }}}