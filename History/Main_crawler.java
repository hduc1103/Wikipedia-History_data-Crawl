package History;

import History.Crawlers.*;

public class Main_crawler {
    public static void main(String[] args) {
        System.out.println("Source: Wikipedia");
        System.out.println("Total: ");

        Wiki_KingCrawler King_crawler = new Wiki_KingCrawler();
        King_crawler.scrape();

        Wiki_DynastyCrawler Dynasty_crawler = new Wiki_DynastyCrawler();
        Dynasty_crawler.scrape();

        Wiki_EventCrawler Event_crawler = new Wiki_EventCrawler();
        Event_crawler.scrape();

        Wiki_FestivalCrawler Festival_crawler = new Wiki_FestivalCrawler();
        Festival_crawler.scrape();

        Wiki_TourismCrawler Tourism_crawler = new Wiki_TourismCrawler();
        Tourism_crawler.scrape();

        System.out.println("Finished!");
    }
}
