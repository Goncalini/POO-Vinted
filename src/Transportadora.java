import java.io.Serializable;

public class Transportadora implements Serializable {

    private String nome;
    private int formula;
    private double valorPequeno;
    private double valorMedio;
    private double valorGrande;
    private double imposto;
    private double margemLucro;
    private double totalLucro;

    public Transportadora(){
        this.nome = "";
        this.valorPequeno = 0;
        this.valorMedio = 0;
        this.valorGrande = 0;
        this.imposto = 0;
        this.margemLucro = 0;
        this.totalLucro = 0;
    }

    public Transportadora(String nome, double margemLucro,int formula) {
        this.nome = nome;
        this.formula = formula;
        this.valorPequeno = 1.5;
        this.valorMedio = 3.5;
        this.valorGrande = 5;
        this.imposto = 1.25;
        this.margemLucro = margemLucro;
    }

    public Transportadora(Transportadora transportadora){
        this.nome = transportadora.getNome();
        this.valorPequeno = transportadora.getValorPequeno();
        this.valorMedio = transportadora.getValorMedio();
        this.valorGrande = transportadora.getValorGrande();
        this.imposto = transportadora.getImposto();
        this.margemLucro = transportadora.getMargemLucro();
        this.totalLucro = transportadora.getTotalLucro();
    }

    public int getFormula() {
        return formula;
    }

    public void setFormula(int formula) {
        this.formula = formula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorPequeno() {
        return valorPequeno;
    }

    public void setValorPequeno(double valorPequeno) {
        this.valorPequeno = valorPequeno;
    }

    public double getValorMedio() {
        return valorMedio;
    }

    public void setValorMedio(double valorMedio) {
        this.valorMedio = valorMedio;
    }

    public double getValorGrande() {
        return valorGrande;
    }

    public void setValorGrande(double valorGrande) {
        this.valorGrande = valorGrande;
    }

    public double getImposto() {
        return imposto;
    }

    public void setImposto(double imposto) {
        this.imposto = imposto;
    }

    public double getMargemLucro() {
        return margemLucro;
    }

    public void setMargemLucro(double margemLucro) {
        this.margemLucro = margemLucro;
    }

    public double getTotalLucro() {
        return totalLucro;
    }

    public void setTotalLucro(double totalLucro) {
        this.totalLucro = totalLucro;
    }

    @Override
    public Transportadora clone(){
        return new Transportadora(this);
    }

}
