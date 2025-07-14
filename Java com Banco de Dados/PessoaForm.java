
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class PessoaForm extends JFrame {

    private final List<Pessoa> pessoas = new ArrayList<>();
    private int currentIndex = 0;
    private JLabel lblCodigo, lblNome, lblEmail, lblTelefone;
    private JTextField txtCodigo, txtNome, txtEmail, txtTelefone;
    private JButton btnAnterior, btnProximo;

    public PessoaForm(Connection connection) {
        super("Cadastro de Pessoas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(5, 2, 10, 10));

        carregarDados(connection);
        initComponents();
        exibirRegistroAtual();
    }

    private void carregarDados(Connection connection) {
        try {
            String sql = "SELECT * FROM Pessoa";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                int id = result.getInt("Código");
                String name = result.getString("Nome");
                String email = result.getString("Email");
                String fone = result.getString("Telefone");

                pessoas.add(new Pessoa(id, name, email, fone));
            }
        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        lblCodigo = new JLabel("Código:");
        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);

        lblNome = new JLabel("Nome:");
        txtNome = new JTextField();
        txtNome.setEditable(false);

        lblEmail = new JLabel("Email:");
        txtEmail = new JTextField();
        txtEmail.setEditable(false);

        lblTelefone = new JLabel("Telefone:");
        txtTelefone = new JTextField();
        txtTelefone.setEditable(false);

        btnAnterior = new JButton("Anterior");
        btnProximo = new JButton("Próximo");

        add(lblCodigo);
        add(txtCodigo);
        add(lblNome);
        add(txtNome);
        add(lblEmail);
        add(txtEmail);
        add(lblTelefone);
        add(txtTelefone);
        add(btnAnterior);
        add(btnProximo);

        btnAnterior.addActionListener((ActionEvent e) -> {
            if (currentIndex > 0) {
                currentIndex--;

                exibirRegistroAtual();
            } else {
                JOptionPane.showMessageDialog(PessoaForm.this,
                        "Este é o primeiro registro", "Informação",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnProximo.addActionListener((ActionEvent e) -> {
            if (currentIndex < pessoas.size() - 1) {
                currentIndex++;
                exibirRegistroAtual();
            } else {
                JOptionPane.showMessageDialog(PessoaForm.this,
                        "Este é o último registro", "Informação",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void exibirRegistroAtual() {
        if (pessoas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum registro encontrado", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Pessoa pessoa = pessoas.get(currentIndex);
        txtCodigo.setText(String.valueOf(pessoa.getCodigo()));
        txtNome.setText(pessoa.getNome());
        txtEmail.setText(pessoa.getEmail());
        txtTelefone.setText(pessoa.getTelefone());

        setTitle("Cadastro de Pessoas - Registro " + (currentIndex + 1)
                + " de " + pessoas.size());
    }

    private class Pessoa {

        private final int codigo;
        private final String nome;
        private final String email;
        private final String telefone;

        public Pessoa(int codigo, String nome, String email, String telefone) {
            this.codigo = codigo;
            this.nome = nome;
            this.email = email;
            this.telefone = telefone;
        }

        public int getCodigo() {
            return codigo;
        }

        public String getNome() {
            return nome;
        }

        public String getEmail() {
            return email;
        }

        public String getTelefone() {
            return telefone;
        }
    }
}
