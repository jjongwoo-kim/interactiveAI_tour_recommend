package tripwiz.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tripwiz.domain.Weathers;
import tripwiz.service.WeathersService;

import java.util.List;

@RestController
public class WeathersController {
	private final WeathersService weathersService;

	public WeathersController(WeathersService weathersService) {
		this.weathersService = weathersService;
	}

//	@GetMapping("/")
//	public String weathers(Model model) throws Exception {
//		List<Weathers> weathersList = weathersService.getWeathersData();
//		model.addAttribute("weathers", weathersList);
//		return "weathers";
//	}

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/")
	public List<Weathers> weathers() throws Exception {
		return weathersService.getWeathersData();
	}

}
