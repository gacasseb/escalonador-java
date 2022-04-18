package gui;

import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import escalonadores.FilaSRTN;
import escalonadores.Processo;

public class TabelaMultiplasFilas extends JPanel {
	private JTable tableResultadoMultiplas;
	DefaultTableModel modelMultiplas = new DefaultTableModel(); 


	public TabelaMultiplasFilas() {
		setLayout(null);
	
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 508, 234);
		add(scrollPane);
		
		JTable tableResultadoMultiplas = new JTable(modelMultiplas); 
		scrollPane.setViewportView(tableResultadoMultiplas);
	
		
		modelMultiplas.addColumn("Fila");
		modelMultiplas.addColumn("ID"); 
		modelMultiplas.addColumn("Prioridade"); 		
		modelMultiplas.addColumn("ArrivalTime"); 		
		modelMultiplas.addColumn("FinishTime"); 		
		modelMultiplas.addColumn("BurstTime"); 		
		modelMultiplas.addColumn("TurnAround"); 	
		
		JLabel lblNewLabel = new JLabel("Resultados:");
		lblNewLabel.setBounds(10, 11, 115, 20);
		add(lblNewLabel);

}

public void showResultados(LinkedList<FilaSRTN> filas) {
	modelMultiplas.setRowCount(0);
	for(int i = 0; i < filas.size(); i++) {
		for(int j = 0; j < filas.get(i).getNProcessos(); j++) {
			modelMultiplas.addRow(new Object[] {i, j, filas.get(i).getPrioridade(),  filas.get(i).getProcessos().get(j).getArrivalTime(),  filas.get(i).getProcessos().get(j).getFinishTime(),  filas.get(i).getProcessos().get(j).getBurstTime(),  filas.get(i).getProcessos().get(j).getTurnAround() });
		}
	}
}

}
