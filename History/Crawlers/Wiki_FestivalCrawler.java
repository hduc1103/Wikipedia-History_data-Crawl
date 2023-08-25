package History.Crawlers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import History.Class.Wiki_FestivalData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Wiki_FestivalCrawler {
    private static final String url = "https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam";
    private static final String outputFilePath = "History/output_files/Wiki_festival.csv";

    public void scrape() {
        try {
            Document doc = Jsoup.connect(url).get();
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFilePath, StandardCharsets.UTF_8)));

            String space = ", ";

            
            writer.println("No." +  "Name" + space + "Time" + space + "Place" + space + "Character" + space + "First Held");

            int count = 0;
            Elements rows = doc.select("table.prettytable > tbody > tr");

            List<Wiki_FestivalData> List = new ArrayList<>();

            for (Element row : rows) {
                Elements cells = row.select("td");
                if (cells.size() >= 5) {
                    String name = row.select("td:nth-of-type(3)").text();
                    String time = row.select("td:nth-of-type(1)").text();
                    String place = row.select("td:nth-of-type(2)").text();
                    String character = row.select("td:nth-of-type(5)").text();
                    String firstHeld = row.select("td:nth-of-type(2)").text();

                    // In case can't find data
                    if (name.isEmpty()) {
                        name = null;
                    }
                    if (time.isEmpty()) {
                        time = null;
                    }
                    if (place.isEmpty()) {
                        place = null;
                    }
                    if (character.isEmpty()) {
                        character = null;
                    }
                    if (firstHeld.isEmpty()) {
                        firstHeld = null;
                    }

                    Wiki_FestivalData data = new Wiki_FestivalData(name, time, place, character, firstHeld);

                    data.setName(name);
                    data.setTime(time);
                    data.setPlace(place);
                    data.setCharacter(character);
                    data.setFirstheld(firstHeld);
                    List.add(data);
                    count++;

                    writer.println((count) + space + (data.getName() != null ? data.getName() : "N/A") + space + (data.getTime() != null ? data.getTime() : "N/A") + space + (data.getPlace() != null ? data.getPlace() : "N/A") + space + (data.getCharacter() != null ? data.getCharacter() : "N/A") + space + (data.getFirstheld() != null ? data.getFirstheld() : "N/A"));
                }
            }

            writer.close();
            System.out.println("- Festivals: " + count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
