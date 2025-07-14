
public abstract class Carro {

    protected String placa;
    protected int anoFabricacao;
    protected String modelo;
    protected boolean disponivel;
    protected String vendedor;

    public Carro(String placa, int anoFabricacao, String modelo) {
        this.placa = placa;
        this.anoFabricacao = anoFabricacao;
        this.modelo = modelo;
        this.disponivel = true;
        this.vendedor = null;
    }

    public boolean Disponivel() {
        return disponivel;
    }

    public void ImprimeDados() {
        System.out.println("Placa: " + placa);
        System.out.println("Ano de Fabricação: " + anoFabricacao);
        System.out.println("Modelo: " + modelo);
        System.out.println("Disponivel: " + (disponivel ? "Sim" : "Não"));
        if (!disponivel) {
            System.out.println("Vendido por: " + vendedor);
        }
    }

    public abstract boolean Oferta(double valor);

    public boolean VenderCarro(String vendedor, double valorVenda) {
        if (!Oferta(valorVenda) || !disponivel) {
            return false;
        }
        this.disponivel = false;
        this.vendedor = vendedor;
        return true;
    }
}
