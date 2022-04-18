package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.awt.event.ActionEvent;

import escalonadores.Processo;
import escalonadores.MainPrioridade;
import escalonadores.MainRR;
import escalonadores.MainSJF;
import escalonadores.MainSRTN;

import javax.swing.JScrollPane;

public class Escalonador extends JPanel {
	private JTextField txtFieldTipoEscalonador;
	private JTextField txtNDeProcessos;
	private JTable tableEntrada;
	LinkedList<Processo> listaAtual;
	
	public LinkedList<Processo> getListaAtual(){
		return this.listaAtual;
	}

	/**
	 * Create the panel.
	 * @param panelTabelaResultados 
	 */
	public Escalonador(LinkedList<Processo> listaEscalonador, TabelaResultados tabelaResultados) {
		setLayout(null);
		this.listaAtual = listaEscalonador;
		String[] escalonadores = {"Shortest Job First", "Shortest Remaining Time Next", "Round-Robin", "Prioridade"};
		JComboBox cmbBoxEscalonadores = new JComboBox(escalonadores);
		cmbBoxEscalonadores.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cmbBoxEscalonadores.setBounds(108, 11, 316, 22);
		add(cmbBoxEscalonadores);
		
		txtFieldTipoEscalonador = new JTextField();
		txtFieldTipoEscalonador.setEditable(false);
		txtFieldTipoEscalonador.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtFieldTipoEscalonador.setText("Escalonador");
		txtFieldTipoEscalonador.setBounds(10, 11, 94, 22);
		add(txtFieldTipoEscalonador);
		txtFieldTipoEscalonador.setColumns(10);
		
		txtNDeProcessos = new JTextField();
		txtNDeProcessos.setEditable(false);
		txtNDeProcessos.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtNDeProcessos.setText("N\u00B0 de Processos");
		txtNDeProcessos.setBounds(10, 44, 94, 22);
		add(txtNDeProcessos);
		txtNDeProcessos.setColumns(10);
		
		JFormattedTextField nProcessosTxtField = new JFormattedTextField();

		nProcessosTxtField.setBounds(108, 44, 55, 22);
		add(nProcessosTxtField);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 77, 503, 178);
		add(scrollPane);
		
		DefaultTableModel model = new DefaultTableModel(); 
		JTable tableEntrada = new JTable(model); 
		scrollPane.setViewportView(tableEntrada);

		model.addColumn("ArrivalTime"); 
		model.addColumn("BurstTime"); 		
		
		nProcessosTxtField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nProcessos;
				nProcessos = Integer.parseInt(nProcessosTxtField.getText());
				listaAtual.clear();
				model.setRowCount(0);
				int index = cmbBoxEscalonadores.getSelectedIndex();
				switch(index) {
				case 0: // Processos SJF!!
					Processo.scanTempoAleatorio(listaAtual, nProcessos);
					break;
				case 1: // Processos SRTN!!
					Processo.scanTempoAleatorio(listaAtual, nProcessos);
					break;
				case 2: // Processos RR!!
					Processo.scanTempoAleatorio(listaAtual, nProcessos, 0);
					break;
				case 3: // Processos Prioridade!!
					Processo.scanTempoAleatorio(listaAtual, nProcessos, 0, 0);
					break;
				case 4: // Processos Multiplas Filas!!
					Processo.scanTempoAleatorio(listaAtual, nProcessos);
					break;
				}
				
				for(int i = 0; i < listaAtual.size(); i++) {
					model.addRow(new Object[]{listaAtual.get(i).getArrivalTime(), listaAtual.get(i).getBurstTime()});
				}
			}
		});
		
		tableEntrada.getModel().addTableModelListener(
				new TableModelListener() {
					public void tableChanged(TableModelEvent evt) 
				    {
						int linha = evt.getFirstRow();
						int coluna = evt.getColumn();
						if(linha >= 0 && coluna >= 0) {
							int valor = Integer.parseInt((String) tableEntrada.getModel().getValueAt(linha, coluna));
							if(coluna == 0) {
								listaAtual.get(linha).setArrivalTime(valor);
							} else {
								listaAtual.get(linha).setBurstTime(valor);
								listaAtual.get(linha).setTempoRestante(valor);
							}
						}
				    }
				}
		);
		
		
		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnIniciar.setBounds(424, 266, 89, 23);
		add(btnIniciar);
		
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LinkedList<Processo> novaLista = new LinkedList();
				int index = cmbBoxEscalonadores.getSelectedIndex();
				if(index == 0) {
					System.out.println("SJF");
					MainSJF mainsjf = new MainSJF();
					for ( int i = 0; i < listaEscalonador.size(); i++ ) {
						listaEscalonador.get(i).setEscalonador("SJF");
					}
					novaLista = mainsjf.execute(listaEscalonador);
					
				} else if(index == 1) {
					System.out.println("SRTN");					
					for ( int i = 0; i < listaEscalonador.size(); i++ ) {
						listaEscalonador.get(i).setEscalonador("SRTN");
						listaEscalonador.get(i).setTempoRestante(listaEscalonador.get(i).getBurstTime());
					}
					MainSRTN mainsrtn = new MainSRTN(listaEscalonador, listaEscalonador.size());
					novaLista = mainsrtn.execute(listaEscalonador);
				}
				else if(index == 2) {
					System.out.println("RR");
					int quantum = listaEscalonador.get(0).getQuantum();
					System.out.println("RR");
					for ( int i = 0; i < listaEscalonador.size(); i++ ) {
						listaEscalonador.get(i).setEscalonador("RR");
					}
					MainRR mainrr = new MainRR();
					novaLista = mainrr.execute(listaEscalonador, quantum);
				}
				else if(index == 3) {
					int quantum = listaEscalonador.get(0).getQuantum();
					System.out.println("Prioridade");
					for ( int i = 0; i < listaEscalonador.size(); i++ ) {
						listaEscalonador.get(i).setEscalonador("Prioridade");
					}
					MainPrioridade mainPrioridade = new MainPrioridade();
					novaLista = mainPrioridade.execute(listaEscalonador, quantum);
				}
				else if(index == 4) {
				}
				tabelaResultados.showResultados(novaLista);
			}
		});
	}
}


