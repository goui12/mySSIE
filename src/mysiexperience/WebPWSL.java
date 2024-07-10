/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mysiexperience;

import java.io.File;
import java.time.Duration;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import javax.swing.SwingUtilities;
import org.openqa.selenium.TimeoutException;

public class WebPWSL {

    private static String mUser;
    private static String mPass;
    private static WebDriver driver;

    public static void main(PWSLData formData) {
        new Thread(() -> displayAbortDialog()).start();

        new Thread(() -> {

            String userHome = System.getProperty("user.home");
            System.setProperty("webdriver.chrome.driver", userHome + "\\Documents\\MySIExperience\\Configuration\\Chrome Webdriver\\chromedriver-win64\\chromedriver.exe");
            String userProfile = userHome + "\\AppData\\Local\\Google\\Chrome\\User Data";
            ChromeOptions options = new ChromeOptions();
            options.addArguments("user-data-dir=" + userProfile);
            options.addArguments("profile-directory=Default"); 

            driver = new ChromeDriver(options);
            
            driver.manage().deleteAllCookies();
            System.out.println("All cookies deleted.");

            try {
                loadCredentialsFromXML();
                driver.get("https://forms.office.com/pages/responsepage.aspx?id=zTuuOHmV1E-t2rQuFJXVWrqlf-kYGPpGmQwEjPzuQMxUOVkxNzZYSVpPTjlTMElQWVZKNUxVWU8xMS4u");
                 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); //wait up to 10 seconds
                 WebElement progressBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("single-progress-bar")));
                if (progressBar.getText().contains("Page 1 of 3")) {
                pageOneInput(driver, formData);
            } else{
                userInput(driver, mUser);
                Thread.sleep(3000);
                pageOneInput(driver, formData);
                Thread.sleep(2000); //2 seconds\
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (driver != null) {
                    driver.quit();
                }
            }
        }).start();
    }

    private static void userInput(WebDriver driver, String mUser) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); //wait up to 20 seconds

    try {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("loginfmt")));
        element.sendKeys(mUser);
        WebElement nextButton = driver.findElement(By.cssSelector("input[type='submit']"));
        nextButton.click();
    } catch (TimeoutException e) {
        System.out.println("The element was not found within the timeout period.");}
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    // Method to load credentials from XML

    private static void loadCredentialsFromXML() {
        try {
            // Create a JAXBContext for the MyConfiguration class
            JAXBContext jaxbContext = JAXBContext.newInstance(MyConfiguration.class);

            // Create an Unmarshaller
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // Unmarshal the XML file into an instance of MyConfiguration
            File file = new File("C:\\Users\\z004kptc\\Documents\\MySIExperience\\Configuration\\hazardconfiguration.xml");
            MyConfiguration configuration = (MyConfiguration) jaxbUnmarshaller.unmarshal(file);

            // Retrieve the mUser and mPass values
            mUser = configuration.getMUser();
            mPass = configuration.getMPass();
            // Do something with the mUser and mPass values
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static void pageOneInput(WebDriver driver, PWSLData formData) throws InterruptedException {

        String repeatValueAsString = (formData.getTxfRepeatValue() != null) ? formData.getTxfRepeatValue() : "1";
        int numberOfRepetitions = Integer.parseInt(repeatValueAsString);
        String txaHazardValue = formData.getTxaHazardValue();
        String txfCustValue = formData.getTxfCustValue();
        String txfEmpValue = formData.getTxfEmpValue();
        String txfEquipValue = formData.getTxfEquipValue();
        String txfTaskValue = formData.getTxfTaskValue();
        boolean rdOnSiteYesValue = formData.isRdOnSiteYesValue();
        boolean rdOnSiteNoValue = formData.isRdOnSiteNoValue();
        boolean cbConfinedValue = formData.isCbConfinedValue();
        boolean cbCorrectionsValue = formData.isCbCorrectionsValue();
        boolean cbCutValue = formData.isCbCutValue();
        boolean cbDrivingValue = formData.isCbDrivingValue();
        boolean cbDustValue = formData.isCbDustValue();
        boolean cbErgonomicsValue = formData.isCbErgonomicsValue();
        boolean cbElectricalValue = formData.isCbElectricalValue();
        boolean cbEyeStrainValue = formData.isCbEyeStrainValue();
        boolean cbLiftsValue = formData.isCbLiftsValue();
        boolean cbLaddersValue = formData.isCbLaddersValue();
        boolean cbRoofsValue = formData.isCbRoofsValue();
        boolean cbFlyingValue = formData.isCbFlyingValue();
        boolean cbMetalValue = formData.isCbMetalValue();
        boolean cbHandValue = formData.isCbHandValue();
        boolean cbHousekeepingValue = formData.isCbHousekeepingValue();
        boolean cbInfectionValue = formData.isCbInfectionValue();
        boolean cbLightingValue = formData.isCbLightingValue();
        boolean cbHeavyValue = formData.isCbHeavyValue();
        boolean cbRepeatValue = formData.isCbRepeatValue();
        boolean cbNoiseValue = formData.isCbNoiseValue();
        boolean cbNewSiteValue = formData.isCbNewSiteValue();
        boolean cbPowerToolsValue = formData.isCbPowerToolsValue();
        boolean cbSlipValue = formData.isCbSlipValue();
        boolean cbTrenchValue = formData.isCbTrenchValue();
        boolean cbHotValue = formData.isCbHotValue();
        boolean cbColdValue = formData.isCbColdValue();
        boolean cbStormValue = formData.isCbStormValue();
        boolean cbInsectsValue = formData.isCbInsectsValue();
        boolean cbAloneValue = formData.isCbAloneValue();
        boolean cbTripValue = formData.isCbTripValue();
        String txfToolsValue = formData.getTxfToolsValue();
        String txfNeedsValue = formData.getTxfNeedsValue();
        String txfUniqueValue = formData.getTxfUniqueValue();
        String txfRepeatValue = formData.getTxfRepeatValue();
        boolean rbPPENoValue = formData.isRbPPENoValue();
        boolean rbPPEYesValue = formData.isRbPPEYesValue();
        boolean rbSafetyNoValue = formData.isRbSafetyNoValue();
        boolean rbSafetyYesValue = formData.isRbSafetyYesValue();
        boolean cbExposedValue = formData.isCbExposedValue();
        boolean cbSiemensLadderValue = formData.isCbSiemensLadderValue();
        boolean cbHotWorkValue = formData.isCbHotWorkValue();
        boolean cbInLiftValue = formData.isCbInLiftValue();
        boolean cbConfinedSpaceValue = formData.isCbConfinedSpaceValue();
        boolean cbNoneValue = formData.isCbNoneValue();
        boolean cbFranticValue = formData.isCbFranticValue();
        boolean cbEasyValue = formData.isCbEasyValue();
        boolean cbFasterValue = formData.isCbFasterValue();
        boolean cbNormalValue = formData.isCbNormalValue();
        boolean cbSlowValue = formData.isCbSlowValue();
        WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(5)); //wait up to 5 seconds
        WebDriverWait wait10 = new WebDriverWait(driver, Duration.ofSeconds(10)); //wait up to 10 seconds
        WebDriverWait wait60 = new WebDriverWait(driver, Duration.ofSeconds(60)); //wait up to 60 seconds    

        for (int i = 0; i < numberOfRepetitions; i++) {
            try {
                // Wait until the element with id "single-progress-bar" is visible
                System.out.println("hi");
                WebElement progressBar = wait60.until(ExpectedConditions.visibilityOfElementLocated(By.id("single-progress-bar")));
                System.out.println("hi");
                // Once the element is visible, find and click on the "Next" button
                if (progressBar.getText().contains("Page 1 of 3")) {

                    WebElement nextButton1 = driver.findElement(By.cssSelector("button[data-automation-id='nextButton']"));
                    nextButton1.click();
                } else {
                    System.out.println("Page progress bar not found or not on the expected page.");
                }
            } catch (Exception e) {
                // Handle any exceptions
                e.printStackTrace();

            }
            // 1. "employee ID"
            WebElement questionItem = driver.findElement(By.xpath("//div[contains(@data-automation-id, 'questionItem') and contains(., 'employee ID')]"));
            WebElement inputField = questionItem.findElement(By.xpath(".//input[@data-automation-id='textInput']"));
            inputField.sendKeys(txfEmpValue);

            // 2. "Job Name"
            questionItem = driver.findElement(By.xpath("//div[contains(@data-automation-id, 'questionItem') and contains(., 'Job Name')]"));
            inputField = questionItem.findElement(By.xpath(".//input[@data-automation-id='textInput']"));
            inputField.sendKeys(txfCustValue);

            // 3. "offsite Boolean" NOTE THAT VARIABLE IS CALLED ONSITE BUT STILL REPRESENTS THE OFFSITE BOOLEAN EQUALITY 
            questionItem = driver.findElement(By.xpath("//div[contains(@data-automation-id, 'questionItem') and contains(., 'offsite')]"));
            /*if offsite = false, or if onsite = true */
            if (!rdOnSiteYesValue) {
                WebElement noOption = questionItem.findElement(By.xpath(".//span[text()='No']/preceding-sibling::span/input"));
                noOption.click();
            } else {
                if (!rdOnSiteNoValue) {
                    WebElement yesOption = questionItem.findElement(By.xpath(".//span[text()='Yes']/preceding-sibling::span/input"));
                    yesOption.click();
                } else {
                    System.out.println("ERROR PARSING BOOLEAN ONSITE/OFFSITE");
                }
            }
            WebElement progressBar = wait5.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@data-automation-id, 'questionItem') and contains(., 'What Equipment')]")));
            // 4. "Equipment"
            questionItem = driver.findElement(By.xpath("//div[contains(@data-automation-id, 'questionItem') and contains(., 'What Equipment')]"));
            inputField = questionItem.findElement(By.xpath(".//input[@data-automation-id='textInput']"));
            inputField.sendKeys(txfEquipValue);

            // 5. "Task"
            questionItem = driver.findElement(By.xpath("//div[contains(@data-automation-id, 'questionItem') and contains(., 'What Task')]"));
            inputField = questionItem.findElement(By.xpath(".//input[@data-automation-id='textInput']"));
            inputField.sendKeys(txfTaskValue);

            // 6. "Hazards"
            //questionItem = driver.findElement(By.xpath("//div[contains(@data-automation-id, 'questionItem') and contains(., 'What hazards')]"));
            if (cbConfinedValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Confined Space']"));
                checkbox.click();
            }

            if (cbCorrectionsValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Corrections Facility Hazards']"));
                checkbox.click();
            }

            if (cbCutValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Cut Hazards']"));
                checkbox.click();
            }

            if (cbDrivingValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Driving']"));
                checkbox.click();
            }

            if (cbDustValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Dust']"));
                checkbox.click();
            }

            if (cbElectricalValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Electrical']"));
                checkbox.click();
            }

            if (cbErgonomicsValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Ergonomics']"));
                checkbox.click();
            }

            if (cbEyeStrainValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Eye Strain']"));
                checkbox.click();
            }

            if (cbLiftsValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Falls Lifts']"));
                checkbox.click();
            }

            if (cbLaddersValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Falls Ladders']"));
                checkbox.click();
            }

            if (cbRoofsValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Falls Rooftops']"));
                checkbox.click();
            }

            if (cbFlyingValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Flying Particles']"));
                checkbox.click();
            }

            if (cbMetalValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Hot Work Metal Cutting Grinding Welding']"));
                checkbox.click();
            }

            if (cbHandValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Hand Tools']"));
                checkbox.click();
            }

            if (cbHousekeepingValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Housekeeping']"));
                checkbox.click();
            }

            if (cbInfectionValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Infectious Disease Potential']"));
                checkbox.click();
            }

            if (cbLightingValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Lighting']"));
                checkbox.click();
            }

            if (cbHeavyValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Lifting Carrying Heavy Objects']"));
                checkbox.click();
            }

            if (cbRepeatValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Lifting Carrying Repetitive Motion']"));
                checkbox.click();
            }

            if (cbNoiseValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Loud Noise']"));
                checkbox.click();
            }

            if (cbNewSiteValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='New Site']"));
                checkbox.click();
            }

            if (cbPowerToolsValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Power Tools']"));
                checkbox.click();
            }

            if (cbSlipValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Slip Hazards']"));
                checkbox.click();
            }

            if (cbTrenchValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Trenching Excavation']"));
                checkbox.click();
            }

            if (cbTripValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Trip Hazards']"));
                checkbox.click();
            }

            if (cbHotValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Weather Hot']"));
                checkbox.click();
            }

            if (cbColdValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Weather Cold']"));
                checkbox.click();
            }

            if (cbStormValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Weather Storm Potential']"));
                checkbox.click();
            }

            if (cbInsectsValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Wildlife/Insects']"));
                checkbox.click();
            }

            if (cbAloneValue) {
                WebElement checkbox = driver.findElement(By.cssSelector("input[value='Working Alone']"));
                checkbox.click();

            }

            //7. Hazard Mitigation
            WebElement textArea = driver.findElement(By.xpath("//span[contains(text(), 'Describe what is being done to address these hazards')]/ancestor::div[@data-automation-id='questionItem']//textarea[@data-automation-id='textInput']"));

            textArea.sendKeys(txaHazardValue);

            //8. What tools and/or equipment are required to perform the work
            WebElement textTools = driver.findElement(By.xpath("//span[contains(text(), 'What tools and/or equipment are required to perform the work')]/ancestor::div[@data-automation-id='questionItem']//textarea[@data-automation-id='textInput']"));

            textTools.sendKeys(txfToolsValue);

            //9. Is there anything you don't have that could make the job easier?
            WebElement textAnything = driver.findElement(By.xpath("//span[contains(text(), 'Is there anything you')]/ancestor::div[@data-automation-id='questionItem']//textarea[@data-automation-id='textInput']"));

// Send keys to the textarea element
            textAnything.sendKeys(txfNeedsValue);

            //10. Is there anything unique about doing this task today versus other times 
            //    you have done this same or similar work. If yes, please describe below, otherwise write No or NA
            WebElement textUnique = driver.findElement(By.xpath("//span[contains(text(), 'Is there anything unique')]/ancestor::div[@data-automation-id='questionItem']//textarea[@data-automation-id='textInput']"));

// Send keys to the textarea element
            textUnique.sendKeys(txfUniqueValue);

            // 11. Are you wearing required PPE (minimum PPE to perform work is hard hat, safety glasses, safety boots)?
            questionItem = driver.findElement(By.xpath("//div[contains(@data-automation-id, 'questionItem') and contains(., 'required PPE')]"));

            if (!rbPPEYesValue) {
                WebElement noOption = questionItem.findElement(By.xpath(".//span[text()='No']/preceding-sibling::span/input"));
                noOption.click();
            } else {
                if (!rbPPENoValue) {
                    WebElement yesOption = questionItem.findElement(By.xpath(".//span[text()='Yes']/preceding-sibling::span/input"));
                    yesOption.click();
                } else {
                    System.out.println("ERROR PARSING BOOLEAN PPE");
                }
            }
            try {
                // Sleep for 3 seconds
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }

            // 12. Is there a project specific safety plan and have you reviewed it? If you don't know, please select 'No'.
            questionItem = driver.findElement(By.xpath("//div[contains(@data-automation-id, 'questionItem') and contains(., 'project specific safety plan')]"));

            if (!rbSafetyYesValue) {
                WebElement noOption = questionItem.findElement(By.xpath(".//span[text()='No']/preceding-sibling::span/input"));
                noOption.click();
            } else {
                if (!rbSafetyNoValue) {
                    WebElement yesOption = questionItem.findElement(By.xpath(".//span[text()='Yes']/preceding-sibling::span/input"));
                    yesOption.click();
                } else {
                    System.out.println("ERROR PARSING BOOLEAN PPE");
                }
            }
            try {
                // Sleep for 3 seconds
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }

            WebElement nextButton = driver.findElement(By.cssSelector("button[data-automation-id='nextButton']"));
            nextButton.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Wait up to 10 seconds
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@data-automation-id, 'questionItem') and contains(., 'Do any of the following apply to your work today? Check all that apply')]")));

            WebElement checkbox1 = driver.findElement(By.xpath("//span[contains(text(),'Working within 3 feet of exposed and energized 50v or more')]/preceding-sibling::span/input"));
            WebElement checkbox2 = driver.findElement(By.xpath("//span[contains(text(),'Working in a lift')]/preceding-sibling::span/input"));
            WebElement checkbox3 = driver.findElement(By.xpath("//span[contains(text(),'Using a non-Siemens Portable ladder')]/preceding-sibling::span/input"));
            WebElement checkbox4 = driver.findElement(By.xpath("//span[contains(text(),'Confined Space Entry')]/preceding-sibling::span/input"));
            WebElement checkbox5 = driver.findElement(By.xpath("//span[contains(text(),'Hot work, which includes grinding/cutting metal producing sparks')]/preceding-sibling::span/input"));
            WebElement checkbox6 = driver.findElement(By.xpath("//span[contains(text(),'None of these apply to my work today')]/preceding-sibling::span/input"));
            if (cbExposedValue) {
                checkbox1.click();
            }
            if (cbSiemensLadderValue) {
                checkbox3.click();
            }
            if (cbHotWorkValue) {
                checkbox5.click();
            }
            if (cbInLiftValue) {
                checkbox2.click();
            }
            if (cbConfinedSpaceValue) {
                checkbox4.click();
            }
            if (cbNoneValue) {
                checkbox6.click();
            }

            //14. How intense has the pace at work been lately?
            WebElement checkbox11 = driver.findElement(By.xpath("//span[contains(text(),'Frantic - very imminent deadlines, rushing a lot to get things done, long hours, etc')]/preceding-sibling::span/input"));
            WebElement checkbox12 = driver.findElement(By.xpath("//span[contains(text(),'Faster than normal - some longer hours, deadlines are looming but not on top of us, etc')]/preceding-sibling::span/input"));
            WebElement checkbox13 = driver.findElement(By.xpath("//span[contains(text(),'Normal Ops - Normal speed')]/preceding-sibling::span/input"));
            WebElement checkbox14 = driver.findElement(By.xpath("//span[contains(text(),'Easy going pace - I have plenty of time to finish my work with little to no pressure from anyone to get it done faster')]/preceding-sibling::span/input"));
            WebElement checkbox15 = driver.findElement(By.xpath("//span[contains(text(),'Slow – there is no rush at all, I may be soon or I am currently  waiting for others before I can really move forward with my tasks')]/preceding-sibling::span/input"));

            if (cbFranticValue) {
                checkbox11.click();
            }
            if (cbEasyValue) {
                checkbox12.click();
            }
            if (cbFasterValue) {
                checkbox13.click();
            }
            if (cbNormalValue) {
                checkbox14.click();
            }
            if (cbSlowValue) {
                checkbox15.click();
            }

            WebElement submitButton = driver.findElement(By.cssSelector("button[data-automation-id='submitButton']"));

            // Click the button
            submitButton.click();

            wait10.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[data-automation-id='submitAnother']")));
            if (i <= numberOfRepetitions) {
                WebElement element = driver.findElement(By.cssSelector("span[data-automation-id='submitAnother']"));
                element.click();
            }
            int countI = i + 1; //it'll say completed 1 if I don't do this
            System.out.println("Completed: " + countI + " PWSLs.");
        }
    }

    private static void displayAbortDialog() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AbortPWSL abortDialog = new AbortPWSL(null, true); // Pass the appropriate arguments to the constructor
                abortDialog.setVisible(true);
            }
        });
    }

    public static void stopWebDriver() {
        if (driver != null) {
            // Quit the WebDriver instance
            driver.quit();
            driver = null; // Set the driver reference to null to indicate it's stopped
        }
    }
}
