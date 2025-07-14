
public class Main {

    public static void main(String[] args) {
        CarroProprio carro1 = new CarroProprio("ADC1234", 2018, "Toyota Corolla", 55000, "10/05/2022");
        carro1.ImprimeDados();

        CarroConsignado carro2 = new CarroConsignado("XT2547B", 2018, "Honda Civic", "Gold Silver", 60000);
        carro2.ImprimeDados();

        System.out.println("\nTentando vender carro proprio por 55000...");
        if (carro1.VenderCarro("Carlos Souza", 55000)) {
            System.out.println("Venda realizada com sucesso!");
        } else {
            System.out.println("Venda não realizada.");
        }
        carro1.ImprimeDados();

        System.out.println("\nTentando vender carro consignado por 62000...");
        if (carro2.VenderCarro("Ana Fortira", 62000)) {
            System.out.println("Venda realizada com sucesso!");
        } else {
            System.out.println("Venda não realizada.");
        }
        carro2.ImprimeDados();
    }
}
