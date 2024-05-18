package tripwiz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {
	private String response;

	public MessageResponse(String response) {
		this.response = response;
	}
}
