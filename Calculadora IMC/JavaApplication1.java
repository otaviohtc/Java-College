// JavaApplication1.java
import java.awt.*;
import javax.swing.*;

public class JavaApplication1 extends JFrame {
    private JTextField pesoField;
    private JTextField alturaField;
    private JLabel resultadoLabel;
    private JLabel classificacaoLabel;
    private JPanel colorPanel;

    public JavaApplication1() {
        super("Calculadora de IMC");
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout(10, 10));

        // Painel de título
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Calculadora de IMC"));
        add(topPanel, BorderLayout.NORTH);

        // Painel central
        JPanel centerPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        
        centerPanel.add(new JLabel("Peso (kg):"));
        pesoField = new JTextField();
        centerPanel.add(pesoField);
        
        centerPanel.add(new JLabel("Altura (m):"));
        alturaField = new JTextField();
        centerPanel.add(alturaField);
        
        centerPanel.add(new JLabel("IMC:"));
        resultadoLabel = new JLabel("");
        centerPanel.add(resultadoLabel);
        
        centerPanel.add(new JLabel("Classificação:"));
        classificacaoLabel = new JLabel("");
        centerPanel.add(classificacaoLabel);

        add(centerPanel, BorderLayout.CENTER);

        // Painel de cor
        colorPanel = new JPanel();
        colorPanel.setPreferredSize(new Dimension(100, 50));
        add(colorPanel, BorderLayout.EAST);

        // Painel de botões
        JPanel buttonPanel = new JPanel();
        
        JButton calcularBtn = new JButton("Calcular");
        calcularBtn.addActionListener(e -> calcularIMC());
        
        JButton limparBtn = new JButton("Limpar");
        limparBtn.addActionListener(e -> limparCampos());
        
        JButton sairBtn = new JButton("Sair");
        sairBtn.addActionListener(e -> System.exit(0));
        
        buttonPanel.add(calcularBtn);
        buttonPanel.add(limparBtn);
        buttonPanel.add(sairBtn);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void calcularIMC() {
        try {
            float peso = Float.parseFloat(pesoField.getText());
            float altura = Float.parseFloat(alturaField.getText());
            
            IMC calculadora = new IMC(peso, altura);
            float imc = calculadora.calculaImc();
            
            resultadoLabel.setText(String.format("%.2f", imc));
            classificacaoLabel.setText(calculadora.classifica());
            colorPanel.setBackground(calculadora.cor());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, insira valores numéricos válidos", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        pesoField.setText("");
        alturaField.setText("");
        resultadoLabel.setText("");
        classificacaoLabel.setText("");
        colorPanel.setBackground(Color.WHITE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JavaApplication1 app = new JavaApplication1();
            app.setVisible(true);
        });
    }
}