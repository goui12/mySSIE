/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mysiexperience;

/**
 *
 * @author z004kptc
 */
import java.io.Serializable;
import javax.crypto.SecretKey;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MyConfiguration implements Serializable {
    private String apiKey;
    private String model; // Encrypted model
    private String endPoint;
    private int wordCount;
    private String mUser;
    private String mPass;

    // Constructors, getters, and setters

    public MyConfiguration() {
        // Default constructor (required for JAXB)
    }

    public MyConfiguration(String apiKey, String model, String endPoint, int wordCount, String mUser, String mPass) {
        this.apiKey = apiKey;
        this.endPoint = endPoint;
        this.wordCount = wordCount;
        this.model = model;
        this.mUser = mUser;
        this.mPass = mPass;
    }

    // Getters and setters

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String encryptedModel) {
        this.model = encryptedModel;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }
    public String getMUser() {
        return mUser;
    }
    
    public void setMUser(String mUser) {
        this.mUser = mUser;
    }
    
        public String getMPass() {
        return mPass;
    }
    
    public void setMPass(String mPass) {
        this.mPass = mPass;
    }
}