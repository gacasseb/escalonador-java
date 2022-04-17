package escalonadores;

import java.util.List;
import java.util.Vector;

public abstract class Fila {
    
    private  int prioridade;
    private boolean containsProcess;
    
    List<Processo> processos = new Vector<>();
    
    public void addProcesso(Processo processo){
        processos.add(processo);
    }
    public Processo removeProcesso(Processo processo){
        Processo temp = processos.get(processos.size());
        processos.remove(temp);
        return temp;
    }
    
    public List<Processo> getFila(Processo processo) {
        return processos;
    }
    
    public boolean isEmpty(){
        return processos.isEmpty();
    }
    
    public boolean getContainsProcess(){
        if (this.containsProcess){
            return true;
        } else {
            return false;
        }
    }
    
    public void setPrioridade(int prioridade){
        this.prioridade = prioridade;
    }
    
    public int getPrioridade(){
        return prioridade;
    }
}