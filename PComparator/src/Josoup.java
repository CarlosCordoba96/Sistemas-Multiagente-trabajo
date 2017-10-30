import java.io.IOException;

import javax.swing.text.Document;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Josoup {
	  public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 org.jsoup.nodes.Document doc;
		 String link="https://www.pccomponentes.com/buscar/?query=steelseries";
         try {
        	 doc = Jsoup.connect(link).userAgent(USER_AGENT).get();
         } catch (IOException e) {
             System.out.println("The web " + link + " cannot be reached");
             return;
         }
         
         Elements e = doc.getElementsByClass("tarjeta-articulo__elementos-basicos");
        // System.out.println(e);
         int i=0;
         for(Element ele: e ){
        	 System.out.println("ELEMENTO "+i);
        	 System.out.println();
        	// System.out.println(ele);
        	 i++;
        	 System.out.println();
        	 sacarnombre(ele);
        	 sacarprecio(ele);
         }
         for (Element result : doc.select("col-xs-6 col-sm-4 col-md-4 col-lg-4")){
        	 
             final String title = result.text();
             final String url = result.attr("href");

             //Now do something with the results (maybe something more useful than just printing to console)

             System.out.println(title + " -> " + url);
         }
         
        
	}
	static void sacarnombre(Element e){
		//System.out.println(e);
		String articulo=e.childNode(1).toString();
		System.out.println(articulo);
		final String patron = "data-name=\"[^\"]*\"";
		Pattern p1 = Pattern.compile(patron);
		final Matcher matcher = p1.matcher(articulo);
		int n = 0; //sólo para mostrar el número de elemento (opcional)

		while (matcher.find()) {
		    System.out.println(matcher.group(0));
		}
	}
	static void sacarprecio(Element e){
		//System.out.println(e);
		String articulo=e.childNode(1).toString();

		final String patron = "data-price=\"[0-9]+([.][0-9]+)?\"";
		Pattern p1 = Pattern.compile(patron);
		final Matcher matcher = p1.matcher(articulo);
		int n = 0; //sólo para mostrar el número de elemento (opcional)

		while (matcher.find()) {
		    System.out.println(matcher.group(0));
		}
	}
}
