package tripwiz.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tripwiz.dto.Weathers;
import tripwiz.service.WeathersService;

import java.util.List;

@RestController
public class WeathersController { // 날씨 정보를 주는 컨트롤러
	private final WeathersService weathersService;

	public WeathersController(WeathersService weathersService) {
		this.weathersService = weathersService;
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping("/save")
	public ResponseEntity<String> setLocation(@RequestBody String tripLocation) {   // 클라이언트에서 지역 변수 받기
		weathersService.setLocation(tripLocation);
		return ResponseEntity.ok("Trip location set successfully");
	}
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/save")
	public List<Weathers> weathers() throws Exception { // 서버에서 클라이언트로 크롤링한 값 보내기
		return weathersService.getWeathersData();
	}
}
