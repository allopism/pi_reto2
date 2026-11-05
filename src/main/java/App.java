import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

public class App {
    public static void main(String[] args) {
        //Creamos las variables que vamos a necesitar.
        File fichero = new File("src/main/resources/calendario.txt");
        PrintWriter escritor;
        BufferedReader buffer;
        String url = "https://www.motogp.com/es/calendar/2027?view=list";

        try {
            //Preparamos para escribir el fichero
            escritor = new PrintWriter(fichero);

            //Conectamos y extraemos los datos de la web. Los escribimos en el fichero y cerramos el PrintWriter.
            if (getStatusConnectionCode(url) == 200) {
                Document doc = getHtmlDocument(url);
                Elements gps = doc.select("div[data-widget='structured-data-event/structured-data-event']"); //Recuperamos la información guardando todos los divs con el atributo "data-widget='structured-data-event/structured-data-event'

                //Empezamos a escribir el archivo. Escribimos una línea introductoria y el bucle añadirá una línea por cada GP.
                //Dado que la fuente está en inglés, escribiremos todo el documento en inglés.
                escritor.println("----- MotoGP 2027 CALENDAR -----\n");
                int contador = 0;
                for (Element gp : gps) {
                    if (!gp.attr("data-structured-kind").equals("TEST")) { //No guardamos eventos catalogados como tests, solo carreras.
                        contador++;
                        String ronda = "ROUND " + contador;
                        String nombre = gp.attr("data-structured-title").trim();
                        String ciudad = gp.attr("data-structured-city");
                        String circuito = (ciudad.isEmpty()) ? gp.attr("data-structured-location") : gp.attr("data-structured-location") + ", " + ciudad;
                        String fechas = "From " + gp.attr("data-structured-start-date").substring(0,10) + " to " + gp.attr("data-structured-end-date").substring(0,10);

                        escritor.println("\u25CF " + ronda + ": " + nombre + " (" + circuito + ") | " + fechas);
                    }
                }
                escritor.close();
            }

            //Leemos el archivo txt y lo mostramos por consola.
            buffer = new BufferedReader(new FileReader(fichero));
            String linea;
            while ((linea = buffer.readLine()) != null) {
                System.out.println(linea);
            }
            buffer.close();

        } catch (IOException e) {e.printStackTrace();}
    }

    //Comprueba que se puede realizar correctamente la conexión.
    public static int getStatusConnectionCode(String url) {

        Connection.Response response = null;

        try {
            response = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/153.0.0.0 Safari/537.36").timeout(100000).ignoreHttpErrors(true).execute();
        } catch (IOException ex) {
            System.out.println("Excepción al obtener el Status Code: " + ex.getMessage());
        }
        return response.statusCode();
    }

    //Guarda el documento HTML de la URL que le pasamos.
    public static Document getHtmlDocument(String url) {

        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/153.0.0.0 Safari/537.36").timeout(100000).get();
        } catch (IOException ex) {
            System.out.println("Excepción al obtener el HTML de la página" + ex.getMessage());
        }
        return doc;
    }
}
