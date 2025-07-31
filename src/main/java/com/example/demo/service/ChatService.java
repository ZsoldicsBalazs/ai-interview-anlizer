package com.example.demo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private ChatClient chatClient;

    public String formatWithAI(String transcription) {
        if (transcription == null || transcription.trim().isEmpty()) {
            return "";
        }

        // Define formatting instructions
        String systemPrompt = """
            -You are a medical assistant specializing in drafting radiology reports. Format the following transcribed text by:
            - Adding appropriate punctuation (periods, commas, etc.).
            - Every row should be in a new line.
            - Capitalizing the first letter of each sentence.
            - Breaking the text into lines for readability (one sentence per line).
            - Fixing any grammatical errors.
            -You receive a voice transcription that may include edit commands such as 'sterge acest rand' and you delete the last sentence.
            -Your task is to process the transcription by applying the voice commands,
            and generate the complete clinical report using the corresponding template. Do not include the voice commands in the final report.";
            Return only the formatted text without additional commentary and edit commands.
            """;

        // Call the AI model to format the text
        return chatClient.prompt()
                .system(systemPrompt)
                .user(transcription)
                .call()
                .content();
    }
}
