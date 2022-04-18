package escalonadores;

import java.util.LinkedList;

public class MainFilas {
	public MainFilas() {
		
	}
	
	private static LinkedList<FilaSRTN> sortFilaPrioridade(LinkedList<FilaSRTN> listaFilaSRTN) {
		// int aux;
		// LinkedList<Processo> aux;

		for (int i = listaFilaSRTN.size() -1; i > 0; i--) {
			for(int j = 0; j < i; j++) {
				if ( listaFilaSRTN.get(j).getPrioridade() < listaFilaSRTN.get(j+1).getPrioridade() ) {
					FilaSRTN aux;
					aux = listaFilaSRTN.get(j);
					listaFilaSRTN.set(j, listaFilaSRTN.get(j+1));
					listaFilaSRTN.set(j+1, aux);
					// aux = listaFilaSRTN.get(j).getProcessos();
					// listaFilaSRTN.get(j).setProcessos(listaFilaSRTN.get(j+1).getProcessos());
					// listaFilaSRTN.get(j+1).setProcessos(aux);
				}
			}
		}

		return listaFilaSRTN;
	}

	public static LinkedList<FilaSRTN> execute(LinkedList<FilaSRTN> listaFilaSRTN) {

		listaFilaSRTN = sortFilaPrioridade(listaFilaSRTN);
		LinkedList<FilaSRTN> novaLista = new LinkedList<FilaSRTN>();
		int tempoAtual = 0;

		// Inicia o escalonamento
		for ( int i = 0; i < listaFilaSRTN.size(); i++ ) {
			EscalonadorSRTN escalonador;
			FilaSRTN fila;
			fila = listaFilaSRTN.get(i);
			System.out.println("Lista com prioridade: " + fila.getPrioridade());
			escalonador = new EscalonadorSRTN(fila.getProcessos(), fila.getProcessos().size());
			escalonador.setTempo(tempoAtual);
			escalonador.start();
			while(escalonador.isAlive());
			tempoAtual = escalonador.getTempo();
			fila.setProcessos(escalonador.getListaProcesso());
			novaLista.add(fila);
		}
		
		return novaLista;
	}
}
