import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupExample {
    public static void main(String[] args) {
        String html = "<html><body><div id='content'>Hello, world!</div></body></html>";
        Document doc = Jsoup.parse(html);

        Element contentDiv = doc.select("#content").first();
        String text = contentDiv.text();
        System.out.println("Content: " + text);

        Element body = doc.body();
        Elements children = body.children();
        for (Element child : children) {
            System.out.println("Child tag: " + child.tagName());
        }

    }
}
