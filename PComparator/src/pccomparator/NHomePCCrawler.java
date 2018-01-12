package pccomparator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NHomePCCrawler{
	private Elements e;
	private Elements aux;
	private ArrayList<Item> items=new ArrayList<Item>();
	private static final String PATH="https://www.newhomepc.net/search?sort=&items=&q=";
	private static final String FINALPATH="&brand%5B%5D=&price=";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
	private String FinalPath;
	private static final String WEB="NewHomePc";
	
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
        e = doc.getElementsByClass("col-lg-4 col-md-4 col-sm-6");//Dónde se encuentra cada producto

     
        String nombre;
        double precio;
        boolean disponible;
        for(Element ele: e ){
       	 nombre=sacarnombre(ele);
       	 precio=sacarprecio(ele);
       	 disponible=disponibilidad(ele);
       	 Item ite=new Item(nombre,precio,disponible,WEB);
       	 this.items.add(ite);        	 
        }
	}
	private static String sacarnombre(Element e){
		String articulo=e.childNode(0).toString();
		final String patron = "title=\"[^\"]*\"";
		Pattern p1 = Pattern.compile(patron);
		final Matcher matcher = p1.matcher(articulo);
		String value=null;
		while (matcher.find()) {
		    value=matcher.group(0);
		}
		return value.substring(7,value.length()-1);
	}
	
	private static double sacarprecio(Element e){
		String articulo=e.childNode(0).toString();
		final String patron = "[0-9]+([.][0-9]+)?";
		Pattern p1 = Pattern.compile(patron);
		final Matcher matcher = p1.matcher(articulo);
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
		while (matcher.find()) {
		   disponible=true;
		   break;
		}
	
		return disponible;
	}
	public Item result() {
		Item i=null;
		double price=Double.MAX_VALUE;
		for (Item e:items) {
			if(price>e.getPrecio()) {
				i=e;
				price=i.getPrecio();
			}
		}
		return i;
	}
}
