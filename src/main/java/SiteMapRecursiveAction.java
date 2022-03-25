import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.RecursiveAction;

public class SiteMapRecursiveAction extends RecursiveAction {
    int start, end;
    String[] data;

    SiteMapRecursiveAction(String[] data, int start, int end) throws IOException {
        this.start = start;
        this.end = end;
        this.data = data;
    }

    @Override
    protected void compute() {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter("src/main/resources/siteMap.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if ((end - start) < Runtime.getRuntime().availableProcessors()) { // CPU cores count
            for (String link : data) {
                printWriter.print(link + "\n");
            }
        } else {
            int mid = (start + end) / 2;
            SiteMapRecursiveAction left = null;
            try {
                left = new SiteMapRecursiveAction(data, start, mid);
            } catch (IOException e) {
                e.printStackTrace();
            }
            SiteMapRecursiveAction right = null;
            try {
                right = new SiteMapRecursiveAction(data, mid, end);
            } catch (IOException e) {
                e.printStackTrace();
            }

            left.fork();
            left.join();
            right.fork();
            right.join();
        }
        printWriter.flush();
    }
}


