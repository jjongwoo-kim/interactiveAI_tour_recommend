package tripwiz.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatGptService {

	@Value("${API-KEY.openai-key}")
	private String apiKey;
	private static final String apiEndpoint = "https://api.openai.com/v1/chat/completions";

	public String getAIResponse(String prompt) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(apiKey);

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("model", "gpt-3.5-turbo");
		requestBody.put("messages", new Object[]{
				new HashMap<String, String>() {{
					put("role", "user");
					put("content", prompt);
				}}
		});
		requestBody.put("temperature", 0.8);
		requestBody.put("max_tokens", 1024);
		requestBody.put("top_p", 1);
		requestBody.put("frequency_penalty", 0.5);
		requestBody.put("presence_penalty", 0.5);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(apiEndpoint, request, String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			String responseBody = response.getBody();
			try {
				return extractResponseMessage(responseBody);
			} catch (Exception e) {
				e.printStackTrace();
				return "응답 파싱 중 에러";
			}
		} else {
			return "API 호출 중 에러: " + response.getStatusCode();
		}
	}

	private String extractResponseMessage(String responseBody) throws Exception {
		JSONObject responseJson = new JSONObject(responseBody);
		JSONObject choice = responseJson.getJSONArray("choices").getJSONObject(0);
		JSONObject message = choice.getJSONObject("message");
		return message.getString("content");
	}
}
