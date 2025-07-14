
public class CarroProprio extends Carro {

    private double valorCompra;
    private String dataCompra;

    public CarroProprio(String placa, int anoFabricacao, String modelo, double valorCompra, String dataCompra) {
        super(placa, anoFabricacao, modelo);
        this.valorCompra = valorCompra;
        this.dataCompra = dataCompra;
    }

    @Override
    public boolean Oferta(double valor) {
        return valor >= valorCompra * 1.1;
    }
}
