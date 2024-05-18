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
		Elements contents = document.select(".week_list"); // 주간 예보파트 크롤링

		for(Element content : contents) {
			Weathers weathers = Weathers.builder()
					.date(content.select(".date").text()) // 날씨 정보
					.weather(content.select(".wt_icon .blind").text()) // 날씨 정보
					.low_temperature(content.select(".lowest").text()) // 최저 온도
					.high_temperature(content.select(".highest").text()) // 최고 온도
					.build();
			weathersList.add(weathers);
			//System.out.println(weathersList);
		}
		return weathersList;
	}
}
