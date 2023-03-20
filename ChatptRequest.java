package famu.edu;

public record ChatptRequest(String model, String prompt, int temperature, int max_tokens) {
}
