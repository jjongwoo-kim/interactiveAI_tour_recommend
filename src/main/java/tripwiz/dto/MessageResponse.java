package tripwiz.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MessageResponse {
	private List<String> responses;

	public MessageResponse(List<String> responses) {
		this.responses = responses;
	}
}
