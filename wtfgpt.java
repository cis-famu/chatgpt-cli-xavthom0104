package com.example.wtfgpt;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class WTFGPT {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a string to search for: ");
        String searchString = scanner.nextLine();

        ObjectMapper objectMapper = new ObjectMapper();
        ChatGptRequest chatGptRequest = new ChatGptRequest("text-davinci");

        String input = """
            {
                "model": "text-davinci-001",
                "prompt": "%s",
                "temperature": 1,
                "max_tokens": 100
            }
        """.formatted(searchString);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/completions"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Authorization", "Bearer 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")
                .POST(HttpRequest.BodyPublishers.ofString(input))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ChatGptResponse chatGptResponse = objectMapper.readValue(response.body(), ChatGptResponse.class);
            String answer = chatGptResponse.getChoices()[chatGptResponse.getChoices().length-1].getText();
            if (!answer.isEmpty()) {
                System.out.println(answer.replace("\n", "").trim());
            } else {
                System.out.println(response.statusCode());
                System.out.println(response.body());
            }
        }
    }
}
