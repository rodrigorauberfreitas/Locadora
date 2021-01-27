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

import model.bean.Cliente;
import model.bean.Filme;
import model.dao.ClienteDAO;
import model.dao.FilmeDAO;

import javax.swing.JButton;

public class JFListarClientes extends JFrame {

	private JPanel contentPane;
	private JTable jtCliente;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFListarClientes frame = new JFListarClientes();
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
	public JFListarClientes() {
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				readJTable();
			}
			public void windowLostFocus (WindowEvent e) {
				
			}
		});
		setTitle("Listar Clientes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 586, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Listar Clientes");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 11, 163, 20);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 42, 460, 203);
		contentPane.add(scrollPane);
		
		jtCliente = new JTable();
		jtCliente.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"idCliente", "Nome", "Idade", "Estado Civil"
			}
		));
		scrollPane.setViewportView(jtCliente);
		
		JButton btnCadastrar = new JButton("Cadastrar Cliente");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFCadastrarCliente cc = new JFCadastrarCliente();
				cc.setVisible(true);
			}
		});
		btnCadastrar.setBounds(10, 256, 163, 23);
		contentPane.add(btnCadastrar);
		
		JButton btnAlterar = new JButton("Alterar Cliente");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(jtCliente.getSelectedRow()!= -1) {
					JFAtualizarCliente ac = new JFAtualizarCliente(
							(int)jtCliente.getValueAt(jtCliente.getSelectedRow(), 0));
					ac.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "Selecione um cliente!");
				}
				readJTable();
			}
		});
		
		btnAlterar.setBounds(183, 256, 115, 23);
		contentPane.add(btnAlterar);
		
		JButton btnExcluir = new JButton("Excluir Cliente");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(jtCliente.getSelectedRow() != -1) {
					
					int opcao = JOptionPane.showConfirmDialog(null, "Deseja excluir o Cliente selecionado?"
							,"Exclusão",JOptionPane.YES_NO_OPTION);
					if (opcao == 0) {
						ClienteDAO dao = new ClienteDAO();
						Cliente c = new Cliente();
						c.setIdCliente((int) jtCliente.getValueAt(jtCliente.getSelectedRow(), 0));
						dao.delete(c);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um filme!");
				}
				readJTable();
			}
		});
		btnExcluir.setBounds(308, 256, 116, 23);
		contentPane.add(btnExcluir);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(434, 256, 89, 23);
		contentPane.add(btnCancelar);
		
		readJTable();
	}
	
	public void readJTable() {
		DefaultTableModel modelo = (DefaultTableModel) jtCliente.getModel();
		modelo.setNumRows(0);
		ClienteDAO cdao = new ClienteDAO();
		for(Cliente c : cdao.read()) {
			modelo.addRow(new Object[] {
				c.getIdCliente(),
				c.getNome(),
				c.getIdade(),
				c.getEstadocivil()
			});
		}
	}
	
	
	
}
