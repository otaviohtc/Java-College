
public class CarroConsignado extends Carro {

    private String nomeProprietario;
    private double valorDesejado;

    public CarroConsignado(String placa, int anoFabricacao, String modelo, String nomeProprietario, double valorDesejado) {
        super(placa, anoFabricacao, modelo);
        this.nomeProprietario = nomeProprietario;
        this.valorDesejado = valorDesejado;
    }

    @Override
    public boolean Oferta(double valor) {
        return valor >= valorDesejado * 1.05;
    }
}
