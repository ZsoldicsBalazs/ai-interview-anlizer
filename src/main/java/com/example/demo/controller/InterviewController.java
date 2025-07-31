package com.example.demo.controller;

import com.example.demo.CustomPrompt;
import com.example.demo.model.InterviewSubmissionDTO;
import com.example.demo.model.QuestionAnswer;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class InterviewController {

    private final ChatClient chatClient;

    public InterviewController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
    @PostMapping("/ai")
    Map<String,String> completion(@RequestBody CustomPrompt prompt){
        return Map.of("completion",
                chatClient.prompt()
                        .user(prompt.getMessage())
                        .call()
                        .content());
    }


    @PostMapping("/evaluate")
    public ResponseEntity<String> evaluateCandidate(@RequestBody InterviewSubmissionDTO submission) {
        StringBuilder prompt = new StringBuilder("Evaluează următoarele răspunsuri la întrebări de interviu:\n");
        for (QuestionAnswer qa : submission.responses()) {
            prompt.append("Întrebare: ").append(qa.question())
                    .append("\nRăspuns: ").append(qa.answer()).append("\n\n");
        }
        prompt.append("Analizeaza fiecare intrebare cu raspuns si oferă un scor general (1-10) și un mic feedback general fara a da feedback la fiecare intrebare. "+
                "Returnează răspunsul în formatul exact: 'Scor: X\nFeedback: ...'");

        String evaluation = chatClient.prompt()
                .user(prompt.toString())
                .system("You are an expert java interviewer.")
                .call()
                .content();
        String score = "N/A";
        String feedback = "Feedback indisponibil";
        try {
            String[] lines = evaluation.split("\n");
            for (String line : lines) {
                if (line.startsWith("Scor:")) {
                    score = line.replace("Scor:", "").trim();
                } else if (line.startsWith("Feedback:")) {
                    feedback = line.replace("Feedback:", "").trim();
                }
            }
        } catch (Exception e) {
            // În caz de eroare, păstrează valorile implicite
        }

        // Formatare răspuns final
        String formattedResponse = String.format("Nota: %s\nFeedback: %s", score, feedback);
        return ResponseEntity.ok(formattedResponse);
    }
}
