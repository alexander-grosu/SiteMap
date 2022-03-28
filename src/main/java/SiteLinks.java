import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SiteLinks {
    public @NotNull List<String> getAllLinks(String url) {
        Set<String> finalSetOfLinks = new HashSet<>();
        List<String> finalListOfLinks = new ArrayList<>();
        Set<String> initialSetOfLinks = new HashSet<>();
        List<String> initialListOfLinks = new ArrayList<>();

        Document document = null;
        try {
            document = Jsoup.connect(url)
                    .data("query", "Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(5 * 1000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements links = document.select("[href]");
        for (Element element : links) {
            String str = element.attr("abs:href");
            if (str.contains("https://")) {
                initialSetOfLinks.add(str);
            }
        }
        initialListOfLinks.addAll(initialSetOfLinks);
        ArrayList<String> sortedLinks = (ArrayList<String>) initialListOfLinks.stream().sorted().collect(Collectors.toList());
        for (String originalLink : sortedLinks) {
            String link = "https://";
            String[] splitLink = originalLink.split("/");
            for (int i = 2; i < splitLink.length; i++) {
                String linkParts = splitLink[i] + "/";
                link += linkParts;
                finalSetOfLinks.add(link);
            }
        }
        List<String> listOfLinks = new ArrayList<>(finalSetOfLinks);
        Collections.sort(listOfLinks);
        for (String str : listOfLinks) {
            String[] linksParts = str.split("/");
            String t = "";
            for (int i = 0; i < linksParts.length; i++) {
                if (i >= 3) {
                    t += "\t\t";
                }
            }
            finalListOfLinks.add(t + str);
        }
        return finalListOfLinks;
    }
}
