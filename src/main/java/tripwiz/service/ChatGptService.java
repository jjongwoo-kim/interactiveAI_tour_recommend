package tripwiz.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatGptService {

	@Value("${API-KEY.openai-key}")
	private String apiKey;
	private final List<String> fineTurningModels = List.of("ft:gpt-3.5-turbo-0125:personal::9NegxU8J", "ft:gpt-3.5-turbo-0125:personal::9MTRsIcq", "ft:gpt-3.5-turbo-0125:personal::9MS62PjM", "ft:gpt-3.5-turbo-0125:personal::9KZwXqgn", "ft:gpt-3.5-turbo-0125:personal::9KNTWyTg", "ft:gpt-3.5-turbo-0125:personal::9KNQKxPX");
	private static final String apiEndpoint = "https://api.openai.com/v1/chat/completions";

	public List<String> getAIResponses(String prompt) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(apiKey);

		List<String> responses = new ArrayList<>();

		for (String model : fineTurningModels) {
			Map<String, Object> requestBody = new HashMap<>();
			requestBody.put("model", model);
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
					responses.add(extractResponseMessage(responseBody));
				} catch (Exception e) {
					e.printStackTrace();
					responses.add("응답 파싱 중 에러");
				}
			} else {
				responses.add("API 호출 중 에러: " + response.getStatusCode());
			}
		}
		return responses;
	}

	private String extractResponseMessage(String responseBody) throws Exception {
		JSONObject responseJson = new JSONObject(responseBody);
		JSONObject choice = responseJson.getJSONArray("choices").getJSONObject(0);
		JSONObject message = choice.getJSONObject("message");
		return message.getString("content");
	}
}
