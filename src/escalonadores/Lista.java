package escalonadores;

public class Lista {
	private Integer numeroProcessos;
	private Integer prioridade;
	private Integer fila;
	
	public void setNumeroProcessosLista(Integer n) {
		this.numeroProcessos = n;
	}
	
	public void setPrioridadeLista(Integer p) {
		this.prioridade = p;
	}
	
	public void setFila(Integer n) {
		this.fila = n;
	}
	
	public Integer getNumeroProcessosLista() {
		return this.numeroProcessos;
	}
	
	public Integer getPrioridadeLista() {
		return this.prioridade;
	}
	
	public Integer getFila() {
		return this.fila;
	}
}
