package gui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import escalonadores.FilaSRTN;
import escalonadores.Processo;
import escalonadores.Lista;
import gui.TabelaMultiplasFilas;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.awt.event.ActionEvent;

public class MultiplasFilas extends JPanel {
	private JTextField textFieldLista;
	private JTextField textFieldNLista;
	private JTable tableFilas;

	public int nFilas;
	private JTable tableProcessos;
	private JTable table;
	private LinkedList<Lista> listaAux = new LinkedList<>();
	/**
	 * Create the panel.
	 */
	public MultiplasFilas(LinkedList<FilaSRTN> listaMultiplas, TabelaMultiplasFilas tabelaMultiplas) {
		setLayout(null);
		
		textFieldLista = new JTextField();
		textFieldLista.setEditable(false);
		textFieldLista.setText("N\u00B0 de Filas");
		textFieldLista.setBounds(10, 11, 86, 20);
		add(textFieldLista);
		textFieldLista.setColumns(10);
		
		textFieldNLista = new JTextField();
		textFieldNLista.setBounds(106, 11, 65, 20);
		add(textFieldNLista);
		textFieldNLista.setColumns(10);
		
		JScrollPane scrollPaneFila = new JScrollPane();
		scrollPaneFila.setBounds(10, 34, 508, 81);
		add(scrollPaneFila);
		
		DefaultTableModel modelFilas = new DefaultTableModel(); 
		
		modelFilas.addColumn("Fila"); 
		modelFilas.addColumn("N Processos");
		modelFilas.addColumn("Prioridade");
		
		JTable tableFilas = new JTable(modelFilas); 
		scrollPaneFila.setViewportView(tableFilas);
		
		textFieldNLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nFilas;
				Lista l;
				FilaSRTN list = new FilaSRTN();
				modelFilas.setRowCount(0);
				nFilas = Integer.parseInt(textFieldNLista.getText());
				for(int i = 0; i < nFilas; i++) {
					l = new Lista();
					modelFilas.addRow(new Object[]{i, 0, 0});
					l.setFila(i);
					l.setNumeroProcessosLista(0);
					l.setPrioridadeLista(0);
					listaAux.add(l);
					list.setNProcessos(0);
					list.setPrioridade(0);
					listaMultiplas.add(list);
				}
			}
		});
		
		tableFilas.getModel().addTableModelListener(
				new TableModelListener() {
					public void tableChanged(TableModelEvent evt) 
				    {
						int linha = evt.getFirstRow();
						int coluna = evt.getColumn();
						if(linha >= 0 && coluna > 0) {
							int valor = Integer.parseInt((String) tableFilas.getModel().getValueAt(linha, coluna));
							if(coluna == 1) {
								listaAux.get(linha).setNumeroProcessosLista(valor);
							}
							else{
								listaAux.get(linha).setPrioridadeLista(valor);
							}	
						}
				    }
				}
		);
		
		JScrollPane scrollPaneProcessos = new JScrollPane();
		scrollPaneProcessos.setBounds(10, 148, 508, 113);
		add(scrollPaneProcessos);
		
		DefaultTableModel modelProcesso = new DefaultTableModel();
		
		modelProcesso.addColumn("Fila");
		modelProcesso.addColumn("Prioridade");
		modelProcesso.addColumn("ID");
		modelProcesso.addColumn("ArrivalTime");	
		modelProcesso.addColumn("BurstTime");
		
		JTable tableProcesso = new JTable(modelProcesso);
		scrollPaneProcessos.setViewportView(tableProcesso);
		
		JLabel lblTempos = new JLabel("Alterar tempos:");
		lblTempos.setBounds(10, 126, 89, 14);
		add(lblTempos);
		
		JButton btnGerarTempos = new JButton("Gerar Tempos");
		btnGerarTempos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelProcesso.setRowCount(0);
				LinkedList<Processo> aux;
				FilaSRTN elemento;
				for(int i = 0; i < listaAux.size(); i++) {
					elemento = new FilaSRTN();
					aux = new LinkedList<>();
					elemento.setPrioridade(listaAux.get(i).getPrioridadeLista());
					elemento.setNProcessos(listaAux.get(i).getNumeroProcessosLista());
					Processo.scanTempoAleatorio(aux, elemento.getNProcessos());
					elemento.setProcessos(aux);
					listaMultiplas.set(i, elemento);
					for(int j = 0;  j < listaAux.get(i).getNumeroProcessosLista(); j++) {
						modelProcesso.addRow(new Object[]{i, elemento.getPrioridade(), j,listaMultiplas.get(i).getProcessos().get(j).getArrivalTime(), listaMultiplas.get(i).getProcessos().get(j).getBurstTime()});						
					}
				}
			}
		});
		btnGerarTempos.setBounds(397, 122, 121, 23);
		add(btnGerarTempos);
				
		
		tableProcesso.getModel().addTableModelListener(
				new TableModelListener() {
					public void tableChanged(TableModelEvent evt) 
				    {
						int linha = evt.getFirstRow();
						int coluna = evt.getColumn();
						if(linha >= 0 && coluna > 2) {
							int valor = Integer.parseInt((String) tableProcesso.getModel().getValueAt(linha, coluna));
							Integer pos = (Integer) modelProcesso.getValueAt(linha, 0);
							Integer processo = (Integer) modelProcesso.getValueAt(linha, 2);
							if(coluna == 3) {
								listaMultiplas.get(pos).getProcessos().get(processo).setArrivalTime(valor);
							} else if(coluna == 4){
								listaMultiplas.get(pos).getProcessos().get(processo).setBurstTime(valor);;
							}
						}
				    }
				}
		);
		
		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//chamarSRTN
				tabelaMultiplas.showResultados(listaMultiplas);
			}
		});
		btnIniciar.setBounds(429, 266, 89, 23);
		add(btnIniciar);
	}
}
