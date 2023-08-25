package History.Crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import History.Class.Wiki_EventData;

public class Wiki_EventCrawler {
    private static final String url = "https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam";
    private static final String outputFilePath = "History/output_files/Wiki_event.csv";

    public void scrape() {
        try {
            Document doc = Jsoup.connect(url).get();
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFilePath, StandardCharsets.UTF_8)));

            String space = ", ";
            writer.println("No." + "Tên" + space + "Thời điểm" + space + "Mô tả");

            int count = 0;
            Elements itemsElements = doc.select("div[class=mw-parser-output]");
            List<Wiki_EventData> List = new ArrayList<>();

            for (Element element : itemsElements) {
                Elements e1 = element.getElementsByTag("p");
                for (Element element2 : e1) {
                    if (element2.nextElementSibling() != null) {
                        if (element2.text().equals("Niên biểu lịch sử Việt Nam")) {
                            continue;
                        } else if (element2.text().equals("Xem thêm")) {
                            continue;
                        }
                        Wiki_EventData data = new Wiki_EventData();
                        if (element2.nextElementSibling().tagName().equals("dl")) {

                            String name = element2.text() + " " + element2.nextElementSibling().text();
                            String time = element2.getElementsByTag("b").text() + " "
                                    + element2.nextElementSibling().getElementsByTag("b").text();

                            if (!time.isEmpty()) {
                                if (name.contains(time)) {
                                    name = name.replace(time, "").trim();
                                }
                            }
                            data.setName(name);
                            data.setTime(time);

                            // event.setDynastyName();
                            String hrefString = element2.nextElementSibling().getElementsByTag("a").attr("href");
                            try {
                                Document sub_doc = Jsoup.connect("https://vi.wikipedia.org/" + hrefString).get();
                                Elements output = sub_doc.select("div[class=mw-parser-output]");
                                data.setDescription(output.select("p").get(0).text());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            String name = element2.text();
                            String time = element2.getElementsByTag("b").text();
                            if (!time.isEmpty()) {
                                if (name.contains(time)) {
                                    name = name.replace(time, "").trim();
                                }
                            }
                            data.setName(name);
                            data.setTime(time);
                            // event.setDynastyName();
                            if (!element2.getElementsByTag("a").attr("class").equals("new")) {
                                String hrefString = element2.getElementsByTag("a").attr("href");
                                try {
                                    Document sub_doc = Jsoup.connect("https://vi.wikipedia.org/" + hrefString).get();
                                    Elements output = sub_doc.select("div[class=mw-parser-output]");
                                    data.setDescription(output.select("p").get(0).text());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        if (!data.getName().equals("Xem thêm") || !data.getTime().equals("Xem thêm")) {
                            count++;
                            List.add(data);
                        } else if (data.getName().length() >= 10) {
                            continue;
                        }

                    }
                }
            }
            int cnt = 0;
            for (Wiki_EventData data : List) {
                if (data.getTime().equals("Niên biểu lịch sử Việt Nam")) {
                    continue;
                }
                cnt++;
                writer.println((cnt) + space + (data.getName()) + space + (data.getTime()) + space + (data.getDescription()));

            }

            writer.close();

            System.out.println("- Events: " + count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}