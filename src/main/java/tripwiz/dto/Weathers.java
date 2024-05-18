package tripwiz.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Weathers {
	private String date;
	private String low_temperature;
	private String high_temperature;
	private String weather;
}
