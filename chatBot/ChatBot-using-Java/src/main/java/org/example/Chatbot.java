package org.example;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class Chatbot {

    private final JSONArray intents;

    // Constructor
    public Chatbot(String intentsPath) throws IOException, JSONException {
        String jsonString = Files.readString(Path.of(intentsPath));
        JSONObject jsonObject = new JSONObject(jsonString);       // Read root object
        this.intents = jsonObject.getJSONArray("intents");        // Get the "intents" array
    }

    // Method to get response based on user message
    public String getResponse(String message) {
        String response = "";
        boolean foundMatch = false;

        try {
            for (int i = 0; i < intents.length(); i++) {
                JSONObject intent = intents.getJSONObject(i);

                if (intent.has("patterns")) {
                    JSONArray patterns = intent.getJSONArray("patterns");

                    for (int j = 0; j < patterns.length(); j++) {
                        String pattern = patterns.getString(j);

                        // Case-insensitive match anywhere in the message
                        if (message.toLowerCase().contains(pattern.toLowerCase())) {
                            JSONArray responses = intent.getJSONArray("responses");
                            response = responses.getString(new Random().nextInt(responses.length()));
                            foundMatch = true;
                            break;
                        }
                    }
                }

                if (foundMatch) break;
            }
        } catch (JSONException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
        }

        if (!foundMatch) {
            // Fallback: suggest a Google search
            try {
                String encodedQuery = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
                response = "I'm sorry, I didn't understand that. You can try searching here:\nhttps://www.google.com/search?q=" + encodedQuery;
            } catch (Exception e) {
                System.err.println("Error encoding query string: " + e.getMessage());
            }
        }

        return response;
    }
}
