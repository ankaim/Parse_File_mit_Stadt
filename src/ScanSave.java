
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ScanSave {
    static Document doc;
    static FileWriter fileWriter;
    static String region;
    public static void main(String[] args) throws IOException, InterruptedException {
        fileWriter = new FileWriter(new File("listStad.txt"), true);
        for (int i = 100; i <10000; i++) {//23721
            try {
                doc = Jsoup.connect("http://pogodnik.com/" + i).get();
            } catch (Exception e) {
                continue;
            }
            Elements temp = doc.getElementsByAttributeValue("class", "hint");
            Elements ukr = doc.getElementsByAttributeValue("class", "mci-cont");
            String[] mas = temp.text().split(" ");
            String[] mas2 = ukr.text().split(" ");
            try {
                Element reg = ukr.get(0);
                region = reg.getElementsByTag("h4").text();
            }catch (Exception e){
                continue;
            }

            //if (!mas2[mas2.length - 1].equals("Украина")) {//Отфильтровываем города Украины
                int indFin = 0;
                for (int j = 0; j < mas.length; j++) {
                    if (mas[j].equals("Прогноз")) indFin = j;
                }
                fileWriter.write(i + " : ");
                for (int j = 7; j < indFin; j++) {
                    fileWriter.write(mas[j] + " ");
                }
                fileWriter.write(": "+region);
                fileWriter.write("\n");
                fileWriter.flush();
            //}
        }
        fileWriter.close();
    }
}
