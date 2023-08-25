package History.Crawlers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import History.Class.Wiki_TourismData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Wiki_TourismCrawler {
    private static final String url = "https://vi.wikipedia.org/wiki/Danh_s%C3%A1ch_Di_t%C3%ADch_qu%E1%BB%91c_gia_Vi%E1%BB%87t_Nam";
    private static final String outputFilePath = "History/output_files/Wiki_tourism.csv";

    public void scrape() {
        try {
            Document doc = Jsoup.connect(url).get();
            PrintWriter writer = new PrintWriter(
                    new BufferedWriter(new FileWriter(outputFilePath, StandardCharsets.UTF_8)));
            String space = ", ";
            writer.println("No." + "Tên" + space + "Địa điểm" + space + "Loại di tích" + space + "Năm công nhận" + space
                    + "Ghi chú");

            int count = 0;
            Elements tables = doc.select("table");

            List<Wiki_TourismData> List = new ArrayList<>();
            Wiki_TourismData data = new Wiki_TourismData();

            for (Element table : tables) {
                Elements rows = table.select("tr");
                for (Element row : rows) {
                    Elements cells = row.select("td, th");
                    if (cells.size() == 5) {
                        String name = cells.get(0).text();
                        if (name.equals("Di tích")) {
                            continue;
                        }
                        String location = cells.get(1).text();
                        location = location.replace(",", "-");
                        String type = cells.get(2).text();
                        String recogi_year = cells.get(3).text();
                        String note = cells.get(4).text();

                        note = cleaner(note);
                        if (recogi_year.isEmpty()) {
                            recogi_year = "N/A";
                        }
                        if (note.isEmpty()) {
                            note = "N/A";
                        }
                        data.setName(name);
                        data.setLocation(location);
                        data.setType(type);
                        data.setRecogi_year(recogi_year);
                        data.setNote(note);

                        List.add(data);
                        count++;

                    } else if (cells.size() == 4) {
                        String name_NB = cells.get(1).text();
                        if (name_NB.equals("Di tích")) {
                            continue;
                        }
                        String location_NB = cells.get(2).text();
                        String note_NB = cells.get(3).text();

                        String type_NB = "N/A";
                        String Recogi_year_NB = "N/A";
                        data.setName(name_NB);
                        data.setLocation(location_NB);
                        data.setType(type_NB);
                        data.setRecogi_year(Recogi_year_NB);
                        data.setNote(note_NB);

                        List.add(data);
                        count++;
                    }
                    writer.println((count) + ". " + (data.getName()) + space + (data.getLocation()) + space
                            + (data.getType()) + space + (data.getRecogi_year()) + space + (data.getNote()));
                }
            }

            writer.close();
            System.out.println("- Tourism sites: " + count);

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

        return data;
    }
}
