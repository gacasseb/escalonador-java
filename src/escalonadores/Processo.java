package escalonadores;

import java.util.LinkedList;

import java.util.Random;

public class Processo extends Thread{
	private int id;
	private int arrivalTime;
	private int burstTime;
	private int finishTime;
	private int turnAround;
	private int tempoAtual;
	private int tempoRestante;
	private int tempoInicio;
	private int quantum;
	private int prioridade;
	private String escalonador;
	
	Processo(int id, int arrivalTime, int burstTime, int tempoRestante, int quantum, int prioridade){
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.tempoRestante = tempoRestante;
		this.finishTime = 0;
		this.turnAround = 0;
		this.tempoAtual = 0;
		this.quantum = quantum;
		this.prioridade = prioridade;
		this.tempoInicio = 0;
	}
	
	Processo(int id, int arrivalTime, int burstTime){
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;		
		this.tempoRestante = 0;
		this.finishTime = 0;
		this.turnAround = 0;
		this.tempoAtual = 0;
		this.quantum = 0;
		this.prioridade = 0;
		this.tempoInicio = 0;
	}
	
	Processo(int id, int arrivalTime, int burstTime, int tempoRestante, int quantum){
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.tempoRestante = tempoRestante;
		this.finishTime = 0;
		this.turnAround = 0;
		this.tempoAtual = 0;
		this.tempoInicio = 0;
		this.quantum = quantum;
	}
	
	public void run() {
		if ( escalonador == "SJF" ) {			
			this.tempoAtual += burstTime;
			this.finishTime = tempoAtual;
			this.turnAround = finishTime - arrivalTime;
			
		} else if ( escalonador == "SRTN" ) {
			tempoRestante--;
			if ( tempoRestante == 0 ) {
				finishTime = tempoAtual;
				turnAround = finishTime - tempoInicio;
			}
		}
	}
	
	public int getIdProcesso() {
		return this.id;
	}
	
	public int getBurstTime() {
		return this.burstTime;
	}
	
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	
	public int getFinishTime() {
		return this.finishTime;
	}
	
	public int getTurnAround() {
		return this.turnAround;
	}
	
	public int getTempoRestante() {
		return this.tempoRestante;
	}
	
	public int getPrioridade() {
		return this.prioridade;
	}
	
	public int getTempoInicio() {
		return this.tempoInicio;
	}
	
	public String getEscalonador() {
		return this.escalonador;
	}
	
	public void setTurnAround(int turnAround) {
		this.turnAround = turnAround;
	}
	
	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}
	
	public void setTempoAtual(int tempoAtual) {
		this.tempoAtual = tempoAtual;
	}
	
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}
	
	public void setEscalonador(String escalonador) {
		this.escalonador = escalonador;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setTempoRestante(int tempoRestante) {
		this.tempoRestante = tempoRestante;
	}

	public void setTempoInicio(int tempoInicio) {
		this.tempoInicio = tempoInicio;
	}
	
	public boolean isComplete() {

		if ( tempoRestante == 0 ) {
			return true;
		}
		
		return false;
	}
	
	public static void  scanTempoAleatorio(LinkedList<Processo> listaProcessos, int totalProcessos) {
		int arrivalTime, burstTime;
		Random rand = new Random();
		for(int i = 0; i < totalProcessos; i++) {
			arrivalTime = rand.nextInt(101);
			burstTime = rand.nextInt(50);
			burstTime++; // para nao pegar resultado 0!!
			Processo x = new Processo(i+1, arrivalTime, burstTime);
			listaProcessos.add(x);
		}
	}
}