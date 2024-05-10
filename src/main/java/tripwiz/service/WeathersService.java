package tripwiz.service;

import jakarta.annotation.PostConstruct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import tripwiz.domain.Weathers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeathersService {
	private String location = "서울" + "날씨";
	private String Weathers_URL = "https://search.naver.com/search.naver?sm=mtb_pcv&where=nexearch&query=" + location;

	@PostConstruct
	public List<Weathers> getWeathersData() throws IOException {
		List<Weathers> weathersList = new ArrayList<>();
		Document document = Jsoup.connect(Weathers_URL).get();

		Elements contents = document.select(".week_list");
		for(Element content : contents) {
			Weathers weathers = Weathers.builder()
					.date(content.select(".date").text())
					.weather(content.select(".wt_icon .blind").text())
					.low_temperature(content.select(".lowest").text())
					.high_temperature(content.select(".highest").text())
					.build();
			weathersList.add(weathers);
			System.out.println(weathersList);
		}
		return weathersList;
	}
}
