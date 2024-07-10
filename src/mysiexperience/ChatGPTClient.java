package mysiexperience;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JTextArea;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ChatGPTClient {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/chat/completions";
    private static final String MODEL_NAME = "gpt-3.5-turbo-0125";
    private static MyConfiguration configuration;
    
    
    // Load configuration from XML file
    public static void loadConfiguration() {
        try {
            File configDirectory = new File(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "MySIExperience" + File.separator + "Configuration");
            File configFile = new File(configDirectory, "hazardconfiguration.xml");

            if (configFile.exists()) {
                // Use JAXB to unmarshal the configuration from XML
                JAXBContext context = JAXBContext.newInstance(MyConfiguration.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                configuration = (MyConfiguration) unmarshaller.unmarshal(configFile);
            } else {
                System.out.println("Configuration file not found.");
            }
        } catch (JAXBException | SecurityException | IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

    public static void askChatGPT(String input, JTextArea txaHazard) {
        // Load configuration before making API request
        loadConfiguration();

        try {
            // Construct the API request using the loaded configuration
            URL url = new URL(API_ENDPOINT);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

  connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + configuration.getApiKey());
            connection.setDoOutput(true);
int wordCountMax = configuration.getWordCount()*2;
            // Build the request body
            String requestBody = "{\"messages\": [{\"role\": \"system\", \"content\": \"You are a helpful assistant.\"}, {\"role\": \"user\", \"content\": \"" + input + "\"}], \"max_tokens\": " + wordCountMax + ", \"model\": \"" + configuration.getModel() + "\"}";
            try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                outputStream.writeBytes(requestBody);
                outputStream.flush();
            }

            // Get the API response
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Extract the assistant's response
                    String assistantResponse = extractAssistantResponse(response.toString());

                    // Update txaHazard with the assistant's response
                    txaHazard.setText(assistantResponse);
                }
            } else {
                // Handle HTTP error
                System.out.println("HTTP error: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to extract assistant's response content from the API response
private static String extractAssistantResponse(String apiResponse) {
    try {
        // Parse the JSON response
        JSONObject jsonResponse = new JSONObject(apiResponse);

        // Extract the content of the assistant's response from the "choices" array
        JSONArray choicesArray = jsonResponse.getJSONArray("choices");

        if (choicesArray.length() > 0) {
            JSONObject assistantChoice = choicesArray.getJSONObject(0);

            // Check if the "message" object exists
            if (assistantChoice.has("message")) {
                JSONObject messageObject = assistantChoice.getJSONObject("message");

                // Check if the "content" key exists
                if (messageObject.has("content")) {
                    String assistantResponse = messageObject.getString("content").trim();
                    return assistantResponse;
                }
            }
        }

        return "No valid 'content' found in the response.";
    } catch (Exception e) {
        e.printStackTrace();
        return "Error extracting response.";
    }
}
}