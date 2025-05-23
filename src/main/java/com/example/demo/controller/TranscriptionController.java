package com.example.demo.controller;

import com.example.demo.service.ChatService;
import com.example.demo.service.TranscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/transcribe")
@RequiredArgsConstructor
public class TranscriptionController {

    private final OpenAiAudioTranscriptionModel transcriptionModel;
    private final ChatService chatService;
    private final TranscriptionService  transcriptionService;


    @PostMapping
    public ResponseEntity<String> transcribeAudio(@RequestParam("audio") MultipartFile audioFile) {

        try {
            String transcription = transcriptionService.transcript(audioFile);
            String formattedTranscription = chatService.formatWithAI(transcription);
            return ResponseEntity.ok(formattedTranscription);
        } catch (IOException e){
            return ResponseEntity.status(500).body("Error processing audio file: " + e.getMessage());
        }

    }



}
