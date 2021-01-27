package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.bean.Filme;
import model.dao.FilmeDAO;

import javax.swing.JButton;

public class JFListarFilmes extends JFrame {

	private JPanel contentPane;
	private JTable jtFilme;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFListarFilmes frame = new JFListarFilmes();
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
	public JFListarFilmes() {
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				readJTable();
			}
			public void windowLostFocus (WindowEvent e) {
				
			}
		});
		
		
		setTitle("Listar Filmes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 567, 391);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Listar Filmes");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 11, 179, 27);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 49, 449, 245);
		contentPane.add(scrollPane);
		
		jtFilme = new JTable();
		jtFilme.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"idFilme", "T\u00EDtulo", "Categoria", "Tempo"
			}
		));
		scrollPane.setViewportView(jtFilme);
		
		JButton btnCadastrar = new JButton("Cadastrar Filme");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFCadastrarFilme cf = new JFCadastrarFilme();
				cf.setVisible(true);
			}
		});
		btnCadastrar.setBounds(10, 315, 141, 23);
		contentPane.add(btnCadastrar);
		
		JButton btnAlterar = new JButton("Alterar Filme");
			btnAlterar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if(jtFilme.getSelectedRow()!= -1) {
						JFAtualizarFilme af = new JFAtualizarFilme(
								(int)jtFilme.getValueAt(jtFilme.getSelectedRow(), 0));
						af.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Selecione um filme!");
					}
					readJTable();
				}
			});
			
		btnAlterar.setBounds(161, 315, 107, 23);
		contentPane.add(btnAlterar);
		
		JButton btnExcluir = new JButton("Excluir Filme");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(jtFilme.getSelectedRow() != -1) {
					
					int opcao = JOptionPane.showConfirmDialog(null, "Deseja excluir o filme selecionado?"
							,"Exclusão",JOptionPane.YES_NO_OPTION);
					if (opcao == 0) {
						FilmeDAO dao = new FilmeDAO();
						Filme f = new Filme();
						f.setIdFilme((int) jtFilme.getValueAt(jtFilme.getSelectedRow(), 0));
						dao.delete(f);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um filme!");
				}
				readJTable();
			}
		});
		btnExcluir.setBounds(278, 315, 116, 23);
		contentPane.add(btnExcluir);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(404, 315, 89, 23);
		contentPane.add(btnCancelar);
	
		readJTable();
	}
	
	public void readJTable() {
		DefaultTableModel modelo = (DefaultTableModel) jtFilme.getModel();
		modelo.setNumRows(0);
		FilmeDAO fdao = new FilmeDAO();
		for(Filme f : fdao.read()) {
			modelo.addRow(new Object[] {
				f.getIdFilme(),
				f.getTitulo(),
				f.getCategoria(),
				f.getTempo()
			});
		}
	}
	
	
}
