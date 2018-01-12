package pccomparator;
import java.util.LinkedList;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class AgenteFinal extends Agent {

	private LinkedList <Item> list=new LinkedList<Item>();
	public void Setup() {
		
	}
	public void TakeDown() {
		
	}
	
	public class anunciador extends OneShotBehaviour{

		@Override
		public void action() {
			Item selected=null;
			double min=Double.MAX_VALUE;
			for (Item i: list) {
				if(min > i.getPrecio()) {
					min=i.getPrecio();
					selected=i;
				}
			}
			System.out.println("EL ITEM MÁS BARATO ES: "+selected);
		}
	
		
	}
	public class fbehaviour extends Behaviour{

		MessageTemplate plantilla = null;
		int espera;
		int recibidos;
		boolean end=false;
		
		public fbehaviour() {
			espera=3;
			recibidos=0;
		}
		 
		 public void onStart() {
			 AID emisor = new AID();
	         emisor.setLocalName("crawler");
	         MessageTemplate filtroEmisor = MessageTemplate.MatchSender(emisor);
	         MessageTemplate filtroInform = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
	         plantilla = MessageTemplate.and(filtroInform,filtroEmisor);
		 }
		public void action() {
			
			 ACLMessage mensaje=receive(plantilla);
			 if(mensaje!=null) {
				 recibidos++;
				 espera=3;
				 Item recieved=null;
				 try {
				recieved=(Item)mensaje.getContentObject();
				list.add(recieved);
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 
				 
			 }else {
				 if(espera==0) {
					end=true;
				 }
				 System.out.println("Esperamos a ver si llega alguno más...");
				 espera--;
				 block(5000);
			 }
			 
			
		}

		
		public boolean done() {
			
			return (recibidos==3)||end;
		}
		public int onEnd() {
			
			return 0;
		}
		
		
	}
}
