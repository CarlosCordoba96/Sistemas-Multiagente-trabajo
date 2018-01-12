package pccomparator;

import java.util.ArrayList;

import jade.core.Agent;
import jade.core.behaviours.*;


public class Agente extends Agent {
	private Item [] items=new Item [3];
	ThreadedBehaviourFactory tbf;
	Busqueda1 b1;
	Busqueda2 b2;
	Busqueda3 b3;
	//Vector donde se guardara cada item de agente
	protected void setup() {
		Object[] args = getArguments();
        if (args != null && args.length > 0) {
        	String producto=(String)args[0];
        	tbf = new ThreadedBehaviourFactory();
    		ParallelBehaviour busqueda=new ParallelBehaviour(0) {
    			public int onEnd() {
    				doDelete();
    				return 0;
    			}
    			
    		};
    		 b1=new Busqueda1(producto);
    		 b2=new Busqueda2(producto);
    		 b3=new Busqueda3(producto);
    		
    		busqueda.addSubBehaviour(tbf.wrap(b1));
    		busqueda.addSubBehaviour(tbf.wrap(b2));
    		busqueda.addSubBehaviour(tbf.wrap(b3));
    		addBehaviour(busqueda);
        }
            
		
	}
	protected void takeDown()
    {
    
	System.out.println("Taking down");
	/*Thread td1 = tbf.getThread(Comp1);
	td1.interrupt();
	Thread td2 = tbf.getThread(Comp2);
	td2.interrupt();*/
	super.takeDown();

        System.out.println("****Agente finalizado****");
    }
	private class Busqueda1 extends OneShotBehaviour{
		private String nombreitem;
		private PcComponentesCrawler crawler;
		private Item arr;
		
		public Busqueda1(String nombrei) {
			nombreitem=nombrei;
		}
		public void onStart() {
			crawler=new PcComponentesCrawler(nombreitem);
		}
		public void action() {
			arr=crawler.result();//debemos tratar el asunto de varios productos encontrados
			System.out.println(arr);
			
		}
		
		
	}
private class Busqueda2 extends OneShotBehaviour{
	private String nombreitem;
	private NHomePCCrawler crawler;
	private Item arr;
	public Busqueda2(String nombreitem) {
		this.nombreitem=nombreitem;
	}
	public void onStart() {
		crawler=new NHomePCCrawler(nombreitem);
	}
	
		public void action() {
			arr=crawler.result();//debemos tratar el asunto de varios productos encontrados
			System.out.println(arr);
		}
		
		
	}
private class Busqueda3 extends OneShotBehaviour{
	private AussarCrawler crawler;
	private String nombreitem;
	private Item arr;
	public Busqueda3(String nombreitem) {
		this.nombreitem=nombreitem;
	}
	public void onStart() {
		crawler=new AussarCrawler(nombreitem);
	}
	
	public void action() {
		arr=crawler.result();
		System.out.println(arr);
		
	}
	
	
}
}
