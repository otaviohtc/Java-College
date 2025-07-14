public class IMC {
    private float peso;
    private float altura;

    public IMC(float peso, float altura) {
        this.peso = peso;
        this.altura = altura;
    }

    // Getters e Setters...
    public float getPeso() { return peso; }
    public void setPeso(float peso) { this.peso = peso; }
    public float getAltura() { return altura; }
    public void setAltura(float altura) { this.altura = altura; }

    public float calculaImc() {
        return peso / (altura * altura);
    }

    public String classifica() {
        float imc = calculaImc();
        if (imc < 18.5) return "Abaixo do peso";
        else if (imc < 25) return "Peso ideal";
        else if (imc < 30) return "Acima do peso";
        else if (imc < 40) return "Obesidade";
        else return "Obesidade severa";
    }

    public java.awt.Color cor() {
        float imc = calculaImc();
        if (imc < 18.5) return new java.awt.Color(255, 255, 102);
        else if (imc < 25) return new java.awt.Color(102, 255, 102);
        else if (imc < 30) return new java.awt.Color(255, 204, 102);
        else if (imc < 40) return new java.awt.Color(255, 153, 0);
        else return new java.awt.Color(255, 0, 0);
    }
}