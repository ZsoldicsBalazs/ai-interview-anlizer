package com.example.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class TranscriptionService {
    private final OpenAiAudioTranscriptionModel transcriptionModel;


    public String transcript(MultipartFile audioFile) throws IOException {

            // Convert MultipartFile to Resource
            InputStreamResource audioResource = new InputStreamResource(audioFile.getInputStream()) {

                @Override
                public String getFilename() {
                    return audioFile.getOriginalFilename();
                }
            };


            // Create transcription request
            AudioTranscriptionPrompt prompt = new AudioTranscriptionPrompt(audioResource);


            // Call OpenAI Whisper API for transcription
            AudioTranscriptionResponse response = transcriptionModel.call(prompt);

            // Extract the transcribed text
            String transcription = response.getResult().getOutput();
            return transcription;

    }

}
