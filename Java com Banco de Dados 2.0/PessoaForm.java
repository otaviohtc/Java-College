
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PessoaForm extends JFrame {

    private JLabel lblCodigo, lblNome, lblEmail, lblTelefone;
    private JTextField txtCodigo, txtNome, txtEmail, txtTelefone;
    private JButton btnIncluir, btnAlterar, btnExcluir, btnConsultar;
    private JTable tabela;
    private DefaultTableModel tableModel;
    private Connection connection;
    private int idSelecionado = -1;

    public PessoaForm(Connection connection) {
        super("Cadastro de Pessoa");
        this.connection = connection;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new BorderLayout(10, 10));
        JPanel panelCampos = new JPanel(new GridLayout(4, 2, 10, 10));
        lblCodigo = new JLabel("ID:");
        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        lblNome = new JLabel("NOME:");
        txtNome = new JTextField();
        lblEmail = new JLabel("E-MAIL:");
        txtEmail = new JTextField();
        lblTelefone = new JLabel("TELEFONE:");
        txtTelefone = new JTextField();

        panelCampos.add(lblCodigo);
        panelCampos.add(txtCodigo);
        panelCampos.add(lblNome);
        panelCampos.add(txtNome);
        panelCampos.add(lblEmail);
        panelCampos.add(txtEmail);
        panelCampos.add(lblTelefone);
        panelCampos.add(txtTelefone);
        String[] colunas = {"ID", "Nome", "E-mail", "Telefone"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabela);
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnIncluir = new JButton("INCLUIR");
        btnAlterar = new JButton("ALTERAR");
        btnExcluir = new JButton("EXCLUIR");
        btnConsultar = new JButton("CONSULTAR");
        panelBotoes.add(btnIncluir);
        panelBotoes.add(btnAlterar);
        panelBotoes.add(btnExcluir);
        panelBotoes.add(btnConsultar);
        add(panelCampos, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
        configurarListeners();
        consultarRegistros();
    }

    private void configurarListeners() {
        btnIncluir.addActionListener((ActionEvent e) -> {

            if (validarCampos()) {
                incluirRegistro();
            }
        });
        btnAlterar.addActionListener((ActionEvent e) -> {
            if (idSelecionado == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um registro para alterar", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else if (validarCampos()) {
                alterarRegistro();
            }
        });
        btnExcluir.addActionListener((ActionEvent e) -> {
            if (idSelecionado == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um registro para excluir", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                excluirRegistro();
            }
        });
        btnConsultar.addActionListener((ActionEvent e) -> {
            consultarRegistros();
        });
        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linhaSelecionada = tabela.getSelectedRow();
                if (linhaSelecionada >= 0) {
                    idSelecionado
                            = Integer.parseInt(tabela.getValueAt(linhaSelecionada, 0).toString());
                    txtCodigo.setText(String.valueOf(idSelecionado));
                    txtNome.setText(tabela.getValueAt(linhaSelecionada,
                            1).toString());
                    txtEmail.setText(tabela.getValueAt(linhaSelecionada, 2).toString());
                    txtTelefone.setText(tabela.getValueAt(linhaSelecionada, 3).toString());

                }
            }
        });
    }

    private boolean validarCampos() {
        if (txtNome.getText().isEmpty() || txtEmail.getText().isEmpty()
                || txtTelefone.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void incluirRegistro() {
        try {
            String sql = "INSERT INTO Pessoa (Nome, Email, Telefone) VALUES( ?,  ?,  ?)";
            PreparedStatement stmt = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, txtNome.getText());
            stmt.setString(2, txtEmail.getText());
            stmt.setString(3, txtTelefone.getText());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idSelecionado = generatedKeys.getInt(1);
                        txtCodigo.setText(String.valueOf(idSelecionado));
                        JOptionPane.showMessageDialog(this, "Registro incluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        consultarRegistros();
                    }
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao incluir registro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterarRegistro() {
        try {
            String sql = "UPDATE Pessoa SET Nome = ?, Email = ?, Telefone =  ? WHERE  Código =  ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, txtNome.getText());
            stmt.setString(2, txtEmail.getText());
            stmt.setString(3, txtTelefone.getText());
            stmt.setInt(4, idSelecionado);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Registro alterado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                consultarRegistros();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar registro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirRegistro() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir este registro?",
                "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM Pessoa WHERE Código = ?";
                PreparedStatement stmt
                        = connection.prepareStatement(sql);
                stmt.setInt(1, idSelecionado);

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Registro excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparCampos();
                    consultarRegistros();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir registro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void consultarRegistros() {
        try {
            tableModel.setRowCount(0);
            String sql = "SELECT * FROM Pessoa";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("Código");
                String nome = result.getString("Nome");
                String email = result.getString("Email");
                String telefone = result.getString("Telefone");
                tableModel.addRow(new Object[]{id, nome, email,
                    telefone});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao consultar registros: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        idSelecionado = -1;
        txtCodigo.setText("");
        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
    }
}
