package escalonadores;

import java.util.LinkedList;

public class FilaSRTN extends Fila implements Runnable {

    private int prioridade;
    private int totalProcessos;
    private LinkedList<Processo> processos;

    // public FilaSRTN(LinkedList processos, int prioridade, int totalProcessos)
    // {
    //     this.prioridade = prioridade;
    //     this.processos = processos;
    //     this.totalProcessos = totalProcessos;
    // }

    public FilaSRTN()
    {
        LinkedList<Processo> listaProcessos = new LinkedList<>();
        this.processos = listaProcessos;
        // this.prioridade = prioridade;
    }

    public int getPrioridade()
    {
        return prioridade;
    }

    public LinkedList<Processo> getProcessos()
    {
        return this.processos;
    }
    
    public int getNProcessos() {
    	return this.totalProcessos;
    }

    public void setPrioridade(int prioridade)
    {
        this.prioridade = prioridade;
    }

    public void setNProcessos(int n) {
    	this.totalProcessos = n;
    }
    
    public void add(LinkedList<Processo> listaProcesso)
    {
        this.processos = listaProcesso;
    }

    public void set(int index, Processo processo)
    {
        this.processos.add(processo);
    }

    public void setProcessos(LinkedList<Processo> processos)
    {
        this.processos = processos;
    }

    public void run()
    {
        EscalonadorSRTN escalonador;
        escalonador = new EscalonadorSRTN(processos, totalProcessos);
		// printLista(processos, totalProcessos);
		escalonador.start();
		while(escalonador.isAlive());
		processos = escalonador.getListaProcesso();
		// printLista(listaProcessos, n);
    }
}