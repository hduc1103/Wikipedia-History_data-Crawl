package History.Crawlers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import History.Class.Wiki_KingData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Wiki_KingCrawler {
    private static final String url = "https://vi.wikipedia.org/wiki/Vua_Vi%E1%BB%87t_Nam";
    private static final String outputFilePath = "History/output_files/Wiki_king.csv";

    public void scrape() {
        try {
            Document doc = Jsoup.connect(url).get();
            PrintWriter writer = new PrintWriter(
                    new BufferedWriter(new FileWriter(outputFilePath, StandardCharsets.UTF_8)));

            String space = ", ";
            writer.println("No. " + "Tên" + space + "Niên hiệu" + space + "Subnames" + space + "Năm trị vì" + space
                    + "Tiền nhiệm" + space + "Kế nhiệm" + space + "Sinh" + space + "Mất" + space + "Thời kỳ" + space
                    + "An táng" + space + "Thân phụ" + space + "Thân mẫu");

            int count = 0;
            Elements tables = doc.select("table");

            for (Element table : tables) {
                Elements rows = table.select("tr");
                String subnames = "";
                for (Element row : rows) {
                    Elements cells = row.select("td, th");

                    if (cells.size() == 10) {
                        String name = cells.get(1).text();
                        String mieuHieu = cells.get(2).text();
                        String thuyHieu = cells.get(3).text();
                        String nienHieu = cells.get(4).text();
                        String tenHuy = cells.get(5).text();
                        String theThu = cells.get(6).text();
                        String namTriVi = cells.get(7).html();
                        String ngang = cells.get(8).text();
                        String end = cells.get(9).html();

                        name = cleaner(name);
                        mieuHieu = cleaner(mieuHieu);
                        thuyHieu = cleaner(thuyHieu);
                        nienHieu = cleaner(nienHieu);
                        tenHuy = cleaner(tenHuy);
                        theThu = cleaner(theThu);
                        namTriVi = namTriVi.replaceAll("<sup[^?]*", "");
                        namTriVi = namTriVi.concat(ngang);
                        end = end.replaceAll("<sup[^?]*", "");
                        namTriVi = namTriVi.concat(end);
                        namTriVi = namTriVi.replaceAll("<a[^?]*", "");
                        namTriVi = cleaner(namTriVi);

                        if (mieuHieu.equals("không có")) {
                            mieuHieu = mieuHieu.replace("không có", "");
                        }
                        if (mieuHieu.equals("không rõ")) {
                            mieuHieu = mieuHieu.replace("không rõ", "");
                        }
                        if (tenHuy.equals("không có")) {
                            tenHuy = tenHuy.replace("không có", "");
                        }
                        if (tenHuy.equals("không rõ")) {
                            tenHuy = tenHuy.replace("không rõ", "");
                        }
                        if (theThu.equals("không có")) {
                            theThu = theThu.replace("không có", "");
                        }
                        if (theThu.equals("không rõ")) {
                            theThu = theThu.replace("không rõ", "");
                        }
                        if (nienHieu.equals("không có")) {
                            nienHieu = nienHieu.replace("không có", "N/A");
                        }
                        if (nienHieu.equals("không rõ")) {
                            nienHieu = nienHieu.replace("không rõ", "N/A");
                        }
                        subnames = mieuHieu + " & " + thuyHieu + " & " + tenHuy + " & " + theThu;
                        subnames = replaceCommas(subnames);
                        subnames = remove_space(subnames);
                        if (subnames.contains(" () ")) {
                            subnames.replace(" ()", " ");
                        }
                        String kingUrl = cells.get(1).select("a").attr("href");
                        String kingDataUrl = "https://vi.wikipedia.org" + kingUrl;
                        Document kingDoc = Jsoup.connect(kingDataUrl).get();
                        Elements kingInfoTables = kingDoc.select("table.infobox");

                        String description = "";
                        Elements pTags = kingDoc.select("div[class=mw-parser-output] > p");
                        if (pTags.size() > 0) {
                            description = pTags.get(0).text();
                        }

                        String tienNhiem = "";
                        String keNhiem = "";
                        String sinh = "";
                        String mat = "";
                        String thoiKy = "";
                        String anTang = "";
                        String thanPhu = "";
                        String thanMau = "";

                        for (Element kingInfoTable : kingInfoTables) {
                            Elements rowsInfo = kingInfoTable.select("tr");

                            for (Element rowInfo : rowsInfo) {
                                Element header = rowInfo.selectFirst("th");

                                if (header != null) {
                                    String headerText = cleaner(header.text());

                                    switch (headerText) {
                                        case "Tiền nhiệm":
                                            tienNhiem = cleaner(rowInfo.select("td").text());
                                            break;
                                        case "Kế nhiệm":
                                            keNhiem = cleaner(rowInfo.select("td").text());
                                            break;
                                        case "Sinh":
                                            sinh = cleaner(rowInfo.select("td").text());
                                            break;
                                        case "Mất":
                                            mat = cleaner(rowInfo.select("td").text());
                                            break;
                                        case "Thời kỳ":
                                        case "Triều đại":
                                            thoiKy = cleaner(rowInfo.select("td").text());
                                            break;
                                        case "An táng":
                                            anTang = cleaner(rowInfo.select("td").text());
                                            break;
                                        case "Thân phụ":
                                            thanPhu = cleaner(rowInfo.select("td").text());
                                            break;
                                        case "Thân mẫu":
                                            thanMau = cleaner(rowInfo.select("td").text());
                                            break;
                                    }
                                }
                            }
                        }
                        if (tienNhiem.isEmpty()) {
                            tienNhiem = "Không có";
                        }
                        if (keNhiem.isEmpty()) {
                            keNhiem = "Không có";
                        }
                        if (sinh.isEmpty()) {
                            sinh = "Không có";
                        }
                        if (mat.isEmpty()) {
                            mat = "Không có";
                        }
                        if (thoiKy.isEmpty()) {
                            thoiKy = "Không có";
                        }
                        if (anTang.isEmpty()) {
                            anTang = "Không có";
                        }
                        if (thanMau.isEmpty()) {
                            tienNhiem = "Không có";
                        }
                        if (thanPhu.isEmpty()) {
                            tienNhiem = "Không có";
                        }
                        Wiki_KingData data = new Wiki_KingData(name, nienHieu, subnames, namTriVi, description,
                                tienNhiem, keNhiem, sinh, mat, thoiKy, anTang, thanPhu, thanMau);
                        count++;
                        String output = count + ", " + data.getName() + ", " + data.getNienHieu() + ", "
                                + data.getSubnames() + ", " + data.getNamTriVi() + ", " + data.getTienNhiem() + ", "
                                + data.getKeNhiem() + ", " + data.getSinh() + ", " + data.getMat() + ", "
                                + data.getThoiKy() + ", " + data.getAnTang() + ", " + data.getThanPhu() + ", "
                                + data.getThanMau();

                        writer.println(output);
                    }
                }
            }

            writer.close();
            System.out.println("- Kings: " + count);
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

    private String replaceCommas(String sample) {
        sample = sample.replace(",", "&").replace(";", "&");
        sample = sample.replace("&  &", "&");

        if (sample.startsWith("&")) {
            sample = sample.substring(1);
        }
        if (sample.startsWith(" &")) {
            sample = sample.substring(2);
        }
        if (sample.endsWith("&")) {
            sample = sample.substring(0, sample.length() - 1);
        }
        if (sample.endsWith("& ")) {
            sample = sample.substring(0, sample.length() - 2);
        }
        return sample;
    }

    private String remove_space(String sample) {
        if (sample.startsWith(" ")) {
            sample = sample.substring(1);
        }
        if (sample.endsWith(".")) {
            sample = sample.substring(0, sample.length() - 1);
        }
        return sample;
    }

}