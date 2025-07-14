// JavaApplication2.java

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

public class JavaApplication2 {

    public static void main(String[] args) {
        Connection vCon = new banco().getCon();
        if (vCon != null) {
            try {
                System.out.println("O nome do BD é: "
                        + vCon.getCatalog());
                SwingUtilities.invokeLater(() -> {
                    PessoaForm form = new PessoaForm(vCon);
                    form.setVisible(true);
                });
            } catch (SQLException ex) {
                Logger.getLogger(JavaApplication2.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        } else {
            System.err.println("Falha ao conectar ao banco de dados");
        }
    }
}
