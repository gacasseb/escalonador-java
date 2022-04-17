
package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import escalonadores.FilaSRTN;
import escalonadores.Processo;

import javax.swing.JTabbedPane;

import gui.MultiplasFilas;

public class JanelaPrincipal extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaPrincipal frame = new JanelaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JanelaPrincipal() {
		LinkedList<Processo> listaProcessos = new LinkedList();
		LinkedList<FilaSRTN> listaMultiplas = new LinkedList();
		setTitle("Escalonador de Processos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 555, 374);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		TabelaResultados panelTabelaResultados = new TabelaResultados();
		TabelaMultiplasFilas panelTabelaMultiplas = new TabelaMultiplasFilas();
		Escalonador escalonador = new Escalonador(listaProcessos, panelTabelaResultados);
		
		tabbedPane.addTab("Escalonador", escalonador);
		listaProcessos = escalonador.getListaAtual();
		MultiplasFilas panelMultiplas = new MultiplasFilas(listaMultiplas, panelTabelaMultiplas);
		tabbedPane.addTab("MúltiplasFilas", panelMultiplas);
		tabbedPane.addTab("Tabela", panelTabelaResultados);
		tabbedPane.addTab("TabelaMúltiplasFilas", panelTabelaMultiplas);
		Graficos panelGraficos = new Graficos();
		tabbedPane.add("Gráficos", panelGraficos);
		
		
		
		contentPane.add(tabbedPane, BorderLayout.CENTER);
	}

}
