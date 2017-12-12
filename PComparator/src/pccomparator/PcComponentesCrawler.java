package pccomparator;
import java.io.IOException;

import javax.swing.text.Document;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class PcComponentesCrawler {
	private Elements e;
	private ArrayList<Item> items=new ArrayList<Item>();
	private static final String PATH="https://www.pccomponentes.com/buscar/?query=";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
	private String FinalPath;
	private static final String WEB="PcComponentes";
	public PcComponentesCrawler(String item) {
		FinalPath=PATH+item;	
		crawler();

	}
	
	private void crawler() {
		org.jsoup.nodes.Document doc;
		 try {
        	 doc = Jsoup.connect(FinalPath).userAgent(USER_AGENT).get();
         } catch (IOException e) {
             System.out.println("The web PcComponentes cannot be reached");
             return;
         }
         
         e = doc.getElementsByClass("tarjeta-articulo__elementos-basicos");
        // System.out.println(e);
         int i=0;
         String nombre;
         double precio;
         boolean disponibilidad;
         for(Element ele: e ){
        	 
        	 //System.out.println(ele);
        	 i++;
        	 nombre=sacarnombre(ele);
        	 precio=sacarprecio(ele);
        	 disponibilidad=sacardisponibilidad(ele);
        	 System.out.println("Nombre: "+ nombre+" precio: "+precio+" disponibilidad: "+disponibilidad);
        	 Item ite=new Item(nombre,precio,disponibilidad,WEB);
        	 this.items.add(ite);        	 
         }
	}
	static String sacarnombre(Element e){
		//System.out.println(e);
		String articulo=e.childNode(1).toString();
		final String patron = "data-name=\"[^\"]*\"";
		Pattern p1 = Pattern.compile(patron);
		final Matcher matcher = p1.matcher(articulo);
		int n = 0; //sólo para mostrar el número de elemento (opcional)
		String value=null;
		while (matcher.find()) {
		    value=matcher.group(0);
		}
		return value.substring(11,value.length()-1);
	}
	
	static double sacarprecio(Element e){
		//System.out.println(e);
		String articulo=e.childNode(1).toString();

		final String patron = "data-price=\"[0-9]+([.][0-9]+)?\"";
		Pattern p1 = Pattern.compile(patron);
		final Matcher matcher = p1.matcher(articulo);
		int n = 0; //sólo para mostrar el número de elemento (opcional)
		String value=null;
		while (matcher.find()) {

		   value=matcher.group(0);
		}
		
		return Double.parseDouble(value.substring(12,value.length()-1));			
	}
	static boolean sacardisponibilidad(Element e) {
		//System.out.println(e);
		boolean disponibilidad=false;
		String articulo=e.childNode(3).toString();
		//System.out.println(articulo);
		final String patron = "ma�ana";
		Pattern p1 = Pattern.compile(patron);
		final Matcher matcher = p1.matcher(articulo);
		int n = 0; //s�lo para mostrar el n�mero de elemento (opcional)
		String value=null;
		while (matcher.find()) {

		   value=matcher.group(0);
			 disponibilidad = true;
		}
		return disponibilidad;
	}
	
	public ArrayList results(){
		return items;
	}

	
	
	
	
}
