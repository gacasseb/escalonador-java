package escalonadores;

import java.util.LinkedList;

public class MainSJF {
	public static LinkedList<Processo>execute(LinkedList<Processo> listaProcessos) {
		EscalonadorSJF escalonador;
		escalonador = new EscalonadorSJF(listaProcessos, listaProcessos.size());
		printLista(listaProcessos, listaProcessos.size());
		escalonador.start();
		while(escalonador.isAlive()) ;
		listaProcessos = escalonador.getListaCPU();
		printLista(listaProcessos, listaProcessos.size());
		return listaProcessos;
	}
	
	public static void printLista(LinkedList<Processo> listaProcessos, int totalProcessos) {
		Processo x;
		System.out.println("  ID         ArrivalTime        FinishTime          BurstTime                 TurnAround");
		for(int i = 0; i < totalProcessos; i++) {
			x = listaProcessos.get(i);
			System.out.printf("%3d        	%3d         	   %3d         	      %3d        		 %3d\n", 
					x.getIdProcesso(), x.getArrivalTime(), x.getFinishTime(), x.getBurstTime(), x.getTurnAround());
		}
	}
}
