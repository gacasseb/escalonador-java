package escalonadores;

import java.util.LinkedList;

public class EscalonadorSRTN extends Thread {

    private int totalProcesso;
    private LinkedList<Processo> listaProcesso;
    private int tempo;

    EscalonadorSRTN(LinkedList<Processo> listaProcesso, int totalProcesso)
    {
        this.totalProcesso = totalProcesso;
        this.listaProcesso = listaProcesso;
        this.tempo = 0;
    }
    
    public int getTempo()
    {
    	return this.tempo;
    }

    public LinkedList<Processo> getListaProcesso()
    {
		return this.listaProcesso;
	}
    
    public void setTempo(int tempo)
    {
    	this.tempo = tempo;
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

    public void run()
    {
        int processosCompletos = 0;

        System.out.println("Começou o escalonador SRTN.");

        while (processosCompletos != totalProcesso)
        {
            // Verificar o último processo
            int indexProcesso = getLastProcess(tempo);

            // Encontrou um processo a ser executado
            if ( indexProcesso >= 0 ) {
                Processo processo;
                processo = listaProcesso.get(indexProcesso);
                // Início do processo
                if ( processo.getBurstTime() == processo.getTempoRestante() ) {
                    processo.setTempoInicio(tempo);
                }
                processo.setTempoAtual(tempo);
                processo.start();
                // Espera o processo acabar
                while (processo.isAlive());
                // Processo já finalizou
                if ( processo.isComplete() ) {
                    processosCompletos++;
                }

                Processo novoProcesso;
                novoProcesso = new Processo(processo.getIdProcesso(), processo.getArrivalTime(), processo.getBurstTime());
                novoProcesso.setTempoRestante(processo.getTempoRestante());
                novoProcesso.setTempoAtual(tempo);
                novoProcesso.setFinishTime(processo.getFinishTime() + 1);
                novoProcesso.setTempoInicio(processo.getTempoInicio());
                novoProcesso.setTurnAround(processo.getTurnAround() + 1);
                novoProcesso.setEscalonador(processo.getEscalonador());

                listaProcesso.set(indexProcesso, novoProcesso);
            }

            tempo++;
        }
        
        System.out.println("Finalizou o escalonador SRTN.");
    }
    }
