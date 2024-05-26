package tripwiz.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import tripwiz.dto.Weathers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeathersService { // 날씨 정보를 크롤링 해오는 서비스


	private String location;
	private String Weathers_URL;

	public void setLocation(String tripLocation) {  // 크롤링 주소 설정
		this.location = tripLocation.replaceAll("\"", "") + "날씨";
		this.Weathers_URL = "https://search.naver.com/search.naver?sm=mtb_pcv&where=nexearch&query=" + location;
	}

	public List<Weathers> getWeathersData() throws IOException {
		List<Weathers> weathersList = new ArrayList<>();
		Document document = Jsoup.connect(Weathers_URL).get(); // 크롤링
		Elements weekList = document.select(".week_list"); // 주간 예보파트 크롤링

		for (Element week : weekList) {
			Elements dates = week.select(".date");
			Elements weatherDescriptions = week.select(".wt_icon .blind");
			Elements lowTemperatures = week.select(".lowest");
			Elements highTemperatures = week.select(".highest");

			int daysCount = dates.size();

			for (int i = 0; i < daysCount; i++) {
				Weathers weather = Weathers.builder()
						.date(dates.get(i).text()) // 날짜 정보
						.weather(weatherDescriptions.get(i).text()) // 날씨 정보
						.low_temperature(lowTemperatures.get(i).text()) // 최저 온도
						.high_temperature(highTemperatures.get(i).text()) // 최고 온도
						.build();
				weathersList.add(weather);
			}
		}
		return weathersList;
	}
}
