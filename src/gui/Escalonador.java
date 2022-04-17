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
				Processo.scanTempoAleatorio(listaAtual, nProcessos);
				
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
						if(linha >= 0 && coluna > 0) {
							int valor = Integer.parseInt((String) tableEntrada.getModel().getValueAt(linha, coluna));
							if(coluna == 0) {
								listaAtual.get(linha).setArrivalTime(valor);
							} else {
								listaAtual.get(linha).setBurstTime(valor);
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
						listaEscalonador.get(i).setEscalonador("SJF");
					}
					MainSRTN mainsrtn = new MainSRTN(listaEscalonador, listaEscalonador.size());
					novaLista = mainsrtn.execute(listaEscalonador);
				}
				else if(index == 2) {
					System.out.println("RR");
					//chamar RR
				}
				else if(index == 3) {
					System.out.println("Prioridade");
					//chamar Prioridade
				}
				else if(index == 4) {
					System.out.println("Multiplas filas");
					//chamar Multiplas Filas
				}
				tabelaResultados.showResultados(novaLista);
			}
		});
	}
}


