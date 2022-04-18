package escalonadores;

import java.util.LinkedList;

public class MainRR {
	public static LinkedList<Processo>execute(LinkedList<Processo> listaProcessos, int quantum) {
		EscalonadorRR escalonador;
		escalonador = new EscalonadorRR(listaProcessos, listaProcessos.size(), quantum);
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
}
