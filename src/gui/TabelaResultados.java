package gui;

import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import escalonadores.Processo;


public class TabelaResultados extends JPanel {
	private JTable table;
		DefaultTableModel model = new DefaultTableModel(); 

	/**
	 * Create the panel.
	 */
	public TabelaResultados() {
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 508, 234);
		add(scrollPane);
		
		JTable tableTabela = new JTable(model); 
		scrollPane.setViewportView(tableTabela);

		model.addColumn("ID"); 
		model.addColumn("ArrivalTime"); 		
		model.addColumn("FinishTime"); 		
		model.addColumn("BurstTime"); 		
		model.addColumn("TurnAround"); 	
		
		JLabel lblNewLabel = new JLabel("Resultados:");
		lblNewLabel.setBounds(10, 11, 115, 20);
		add(lblNewLabel);

	}
	
	public void showResultados(LinkedList<Processo> processos) {
		model.setRowCount(0);
		for(int i = 0; i < processos.size(); i++) {
			model.addRow(new Object[]{processos.get(i).getIdProcesso(), processos.get(i).getArrivalTime(), processos.get(i).getFinishTime(), processos.get(i).getBurstTime(), processos.get(i).getTurnAround()});
		}
	}
	
}
