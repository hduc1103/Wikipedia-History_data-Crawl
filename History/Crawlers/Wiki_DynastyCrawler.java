package History.Crawlers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import History.Class.Wiki_DynastyData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Wiki_DynastyCrawler {
    private static final String url = "https://en.wikipedia.org/wiki/List_of_Vietnamese_dynasties#Timeline_of_dynasties_in_Vietnamese_history";
    private static final String outputFilePath = "History/output_files/Wiki_dynasty.csv";

    public void scrape() {
        try {
            Document doc = Jsoup.connect(url).get();
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFilePath, StandardCharsets.UTF_8)));

            String space = ", ";
            writer.println("No. " + "Tên" + space + "Bắt đầu" + space + "Kết thúc");


            int count = 0;

            Element table = doc.select("table.wikitable").first();
            Elements rows = table.select("tr");

            for (int i = 1; i < rows.size(); i++) {
                Element row = rows.get(i);
                Elements columns = row.select("td");

                if (columns.size() >= 4) {
                    Element dynastyElement = columns.get(0);
                    String dynastyName = "";

                    if (dynastyElement.select("i[lang=vi]").size() > 0) {
                        Element vietnameseNameElement = dynastyElement.selectFirst("i[lang=vi]");
                        dynastyName = vietnameseNameElement.text();
                    } else {
                        dynastyName = dynastyElement.text();
                    }
                    String startTime = columns.get(2).text();
                    String endTime = columns.get(3).text();
                    dynastyName = cleaner(dynastyName);
                    endTime = cleaner(endTime);
                    count++;
                    Wiki_DynastyData data = new Wiki_DynastyData();
                    data.setName(dynastyName);
                    data.setStart_time(startTime);
                    data.setEnd_time(endTime);

                    writer.println((count) + space + (data.getName()) + space + (data.getStart_time()) + space + (data.getEnd_time()));
                }
            }

            writer.close();
            System.out.println("- Dynasties: " + count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   private static String cleaner(String sample) {
        String data = sample;
        int index = data.indexOf("[");

        while (index != -1) {
            int close = data.indexOf("]", index);

            if (close != -1) {
                String tmp = data.substring(index, close + 1);
                data = data.replace(tmp, "");
                index = data.indexOf("[", index);
            } else {
                break;
            }
        }
        data = data.replaceAll("[\\p{InCJKUnifiedIdeographs}]+", "");
        data = data.replace("/", "");
        return data.trim();
    }
} 