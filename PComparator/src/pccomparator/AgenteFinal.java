package pccomparator;
import java.util.LinkedList;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class AgenteFinal extends Agent {


	public void setup() {
		System.out.println("Nuevo comportamiento bb");
		addBehaviour(new fbehaviour());
		
	}
	public void TakeDown() {
		
	}
	
	public class anunciador extends OneShotBehaviour{
		private LinkedList <Item> list;
		public anunciador(LinkedList <Item> list) {
			this.list=list;
		}
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
		private LinkedList <Item> list=new LinkedList<Item>();
		MessageTemplate plantilla;;
		int espera;
		int recibidos;
		boolean end=false;
		
		public fbehaviour() {
			espera=3;
			recibidos=0;
		}
		 
		 public void onStart() {
			 System.out.println("Empezamos");
			 plantilla=null;
			 AID emisor = new AID();
	         emisor.setLocalName("crawler");
	         MessageTemplate filtroEmisor = MessageTemplate.MatchSender(emisor);
	         MessageTemplate filtroInform = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
	         plantilla = MessageTemplate.and(filtroInform,filtroEmisor);
		 }
		public void action() {
			
			
			if(recibidos==0) {
				ACLMessage mensaje=blockingReceive(plantilla);
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
					 }else {
						 System.out.println("Esperamos a ver si llega alguno más...");
						 espera--;
						 block(5000);
					 }
					 
				 }
			}
			
			
			 
			
		}

		
		public boolean done() {
			
			return (recibidos==3)||end;
		}
		public int onEnd() {
			addBehaviour(new anunciador(list));
			return 0;
		}
		
		
	}
}
