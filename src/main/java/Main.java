import java.io.IOException;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

class Main {
    public static void main(String[] args) {
        try {
            SiteLinks siteLinks = new SiteLinks();
            List<String> list = siteLinks.getAllLinks("https://4kwallpapers.com/cars/"); // get list of links from url
            String[] strings = list.toArray(String[]::new);  // list to array

            ForkJoinPool forkJoinPool = new ForkJoinPool();
            SiteMapRecursiveAction siteMap = new SiteMapRecursiveAction(strings, 0, strings.length);
            forkJoinPool.invoke(siteMap);

            System.out.println("Active threads: " + forkJoinPool.getActiveThreadCount());
            System.out.println("Pool size: " + forkJoinPool.getPoolSize());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}