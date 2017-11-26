package pccomparator;

import java.util.ArrayList;

import jade.core.Agent;
import jade.core.behaviours.*;


public class Agente extends Agent {
	private Item [] items=new Item [3];
	//Vector donde se guardara cada item de agente
	protected void setup() {
		
		ParallelBehaviour busqueda=new ParallelBehaviour(0);
		busqueda.addSubBehaviour(new Busqueda1("steelseries"));
		busqueda.addSubBehaviour(new Busqueda2("ozone"));
		busqueda.addSubBehaviour(new Busqueda3("mars"));
		addBehaviour(busqueda);
	}
	/*
	 * De momento para esta entrega vamos a funcionar solamente con la pagina PcComponentes
	 * En la siguiente entrega cada SubComportamiento será un 
	 */
	//De momento para esta entrega vamos a funcionar solamente con la pagina PcComponentes

	private class Busqueda1 extends OneShotBehaviour{
		private String nombreitem;
		private PcComponentesCrawler crawler;
		private ArrayList<Item> arr;
		
		public Busqueda1(String nombrei) {
			nombreitem=nombrei;
		}
		public void onStart() {
			crawler=new PcComponentesCrawler(nombreitem);
		}
		public void action() {
			arr=crawler.results();//debemos tratar el asunto de varios productos encontrados
			
			
		}
		
		
	}
private class Busqueda2 extends OneShotBehaviour{
	private String nombreitem;
	private PcComponentesCrawler crawler;
	private ArrayList<Item> arr;
	public Busqueda2(String nombreitem) {
		this.nombreitem=nombreitem;
	}
	public void onStart() {
		crawler=new PcComponentesCrawler(nombreitem);
	}
	
		public void action() {
			arr=crawler.results();//debemos tratar el asunto de varios productos encontrados
			
		}
		
		
	}
private class Busqueda3 extends OneShotBehaviour{
	private PcComponentesCrawler crawler;
	private String nombreitem;
	private ArrayList<Item> arr;
	public Busqueda3(String nombreitem) {
		this.nombreitem=nombreitem;
	}
	public void onStart() {
		crawler=new PcComponentesCrawler(nombreitem);
	}
	
	public void action() {
		arr=crawler.results();
		
		
	}
	
	
}
}
