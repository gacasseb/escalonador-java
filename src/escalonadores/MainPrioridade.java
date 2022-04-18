package escalonadores;

import java.util.LinkedList;

public class MainPrioridade {
	public static LinkedList<Processo>execute(LinkedList<Processo> listaProcessos, int quantum) {
		EscalonadorPrioridade escalonador;
		int totalPrioridade = contaPrioridade(listaProcessos, listaProcessos.size());
		escalonador = new EscalonadorPrioridade(listaProcessos, listaProcessos.size(), totalPrioridade, quantum);
		printLista(listaProcessos, listaProcessos.size());
		escalonador.start();
		while(escalonador.isAlive())
			;
		listaProcessos = escalonador.getListaProcesso();
		printLista(listaProcessos, listaProcessos.size());
		return listaProcessos;
	}
	
	public static void printLista(LinkedList<Processo> listaProcessos, int totalProcessos) {
		Processo x;
		System.out.println("  ID         ArrivalTime        FinishTime          BurstTime                 TurnAround                 TempoRestante");
		for(int i = 0; i < totalProcessos; i++) {
			x = listaProcessos.get(i);
			System.out.printf("%3d        	%3d         	   %3d         	      %3d        		 %3d        		 %3d\n", 
					x.getIdProcesso(), x.getArrivalTime(), x.getFinishTime(), x.getBurstTime(), x.getTurnAround(), x.getTempoRestante());
		}
	}
	
	public static boolean repetidoPrioridade(int[] prioridade, int totalPrioridade, int x) {
		for(int i = 0; i < totalPrioridade; i++) 
			if(x == prioridade[i])
				return true;
		return false;
	}
	
	public static int contaPrioridade(LinkedList<Processo> listaProcesso, int totalProcesso) {
		int[] prioridade = new int[totalProcesso];
		int index = 0;
		for(int i = 0; i < totalProcesso; i++) 
			if(!repetidoPrioridade(prioridade, index, listaProcesso.get(i).getPrioridade())) 
				prioridade[index++] = listaProcesso.get(i).getPrioridade();
		return index;
	}
	
}
