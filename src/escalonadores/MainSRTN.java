package escalonadores;

import java.util.LinkedList;

public class MainSRTN {
	
    private int totalProcesso;
    private LinkedList<Processo> listaProcesso;
	
    public MainSRTN(LinkedList<Processo> listaProcesso, int totalProcesso)
    {
        this.totalProcesso = totalProcesso;
        this.listaProcesso = listaProcesso;
    }
	
	 public LinkedList<Processo> getListaProcesso()
	 {
		 return this.listaProcesso;
	 }
	
	public int getLastProcess(int tempoAtual)
    {
        // Procura pelo processo que vai ser finalizado primeiro
        // (Buscar pelo processo com menor tempo de trabalho restante)
        int index = -1, menorTempo = -1;
        
        for ( int i = 0; i < this.listaProcesso.size(); i++ ) {

            Processo processo;
            processo = this.listaProcesso.get(i);
            int tempoRestante = processo.getTempoRestante();

            // Procura pelo processo que já pode ser iniciado, não está completo e tem o menor tempo restante
            if ( tempoAtual >= processo.getArrivalTime() && !processo.isComplete() && (tempoRestante < menorTempo || menorTempo == -1) ) {
                menorTempo = tempoRestante;
                index = i;
            }
        }

        return index;
    }
	
	public static LinkedList<Processo>execute(LinkedList<Processo> listaProcessos) {
		EscalonadorSJF escalonador;
		
		escalonador = new EscalonadorSJF(listaProcessos, listaProcessos.size());
		escalonador.start();
		while(escalonador.isAlive()) ;
		listaProcessos = escalonador.getListaCPU();
		
		return listaProcessos;
	}
}
