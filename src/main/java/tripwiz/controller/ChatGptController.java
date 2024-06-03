package tripwiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tripwiz.dto.MessageRequest;
import tripwiz.dto.MessageResponse;
import tripwiz.service.ChatGptService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/message")
public class ChatGptController {

	@Autowired
	private ChatGptService chatGptService;

	@PostMapping
	public ResponseEntity<MessageResponse> getChatResponse(@RequestBody MessageRequest request) throws Exception {
		List<String> aiResponses = chatGptService.getAIResponses(request.getMessage());
		MessageResponse responseDTO = new MessageResponse(aiResponses);
		return ResponseEntity.ok(responseDTO);
	}
}
