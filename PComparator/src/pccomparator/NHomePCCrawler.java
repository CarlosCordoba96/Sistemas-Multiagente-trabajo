package pccomparator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NHomePCCrawler {
	private Elements e;
	private Elements aux;
	private ArrayList<Item> items=new ArrayList<Item>();
	private static final String PATH="https://www.newhomepc.net/search?sort=&items=&q=";
	private static final String FINALPATH="&brand%5B%5D=&price=";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
	private String FinalPath;
	private static final String WEB="PcBox";
	
	public NHomePCCrawler(String item) {
		FinalPath=PATH+item+FINALPATH;
		crawler();
	}
	private void crawler() {
		org.jsoup.nodes.Document doc;
		 try {
       	 doc = Jsoup.connect(FinalPath).userAgent(USER_AGENT).get();
        } catch (IOException e) {
            System.out.println("The web NewHomePc cannot be reached");
            return;
        }
        System.out.println(FinalPath);
        e = doc.getElementsByClass("col-lg-4 col-md-4 col-sm-6");
       // System.out.println(e);
 
        int i=0;
        String nombre;
        double precio;
        boolean disponible;
        for(Element ele: e ){
       	 
       	 System.out.println(ele);
       	 i++;
       	 nombre=sacarnombre(ele);
       	 System.out.println(nombre);
       	 precio=sacarprecio(ele);
       	 System.out.println(precio);
       	 disponible=disponibilidad(ele);
       	 System.out.println(disponible);
       	 Item ite=new Item(nombre,precio,disponible,WEB);
       	 this.items.add(ite);        	 
        }
	}
	private static String sacarnombre(Element e){
		//System.out.println(e);
		String articulo=e.childNode(0).toString();
		final String patron = "title=\"[^\"]*\"";
		Pattern p1 = Pattern.compile(patron);
		final Matcher matcher = p1.matcher(articulo);
		int n = 0; //sólo para mostrar el número de elemento (opcional)
		String value=null;
		while (matcher.find()) {
		    value=matcher.group(0);
		}
		return value.substring(7,value.length()-1);
	}
	private static double sacarprecio(Element e){
		//System.out.println(e);
		String articulo=e.childNode(0).toString();

		final String patron = "[0-9]+([.][0-9]+)?";
		Pattern p1 = Pattern.compile(patron);
		final Matcher matcher = p1.matcher(articulo);
		int n = 0; //sólo para mostrar el número de elemento (opcional)
		String value=null;
		while (matcher.find()) {

		   value=matcher.group(0);
		}
		
		return Double.parseDouble(value);			
	}
	private static boolean disponibilidad(Element e) {
		boolean disponible=false;
		String articulo=e.childNode(0).toString();

		final String patron = "Agotado";
		Pattern p1 = Pattern.compile(patron);
		final Matcher matcher = p1.matcher(articulo);
		int n = 0; //sólo para mostrar el número de elemento (opcional)
		String value=null;
		while (matcher.find()) {
		   disponible=true;
		   break;
		}
	
		return disponible;
	}
	public ArrayList results(){
		return items;
	}
}
