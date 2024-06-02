import java.io.Serializable;
import java.time.LocalDate;

public class Sapatilhas extends Artigo implements Serializable {

    private int tamanho;
    private boolean atacadores;
    private String cor;
    private LocalDate dataDeLancamento;
    private int idade;

    public Sapatilhas(){
        super();
        this.tamanho = 0;
        this.atacadores = false;
        this.cor = "";
        this.dataDeLancamento = LocalDate.now();
        this.idade = 0;
    }

    public Sapatilhas(String avaliacaoEstado, int numDonos, String descricao, String marca, String ID, double precoBase, String idTransportadora, String idVendedor, int tamanho, boolean atacadores, String cor, LocalDate dataDeLancamento) {
        super(avaliacaoEstado, numDonos, descricao, marca, ID, precoBase, idTransportadora,idVendedor);
        this.tamanho = tamanho;
        this.atacadores = atacadores;
        this.cor = cor;
        this.dataDeLancamento = LocalDate.from(dataDeLancamento);
    }

    public Sapatilhas(Sapatilhas sapatilhas) {
        super(sapatilhas.getAvaliacaoEstado(),sapatilhas.getNumDonos(),sapatilhas.getDescricao(),sapatilhas.getMarca(), sapatilhas.getID(),sapatilhas.getPrecoBase(),sapatilhas.getTransportadora(), sapatilhas.getIdVendedor());
        this.tamanho = sapatilhas.getTamanho();
        this.atacadores = sapatilhas.getAtacadores();
        this.cor = sapatilhas.getCor();
        this.dataDeLancamento = sapatilhas.getDataDeLancamento();
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getTamanho() {
        return tamanho;
    }

    public boolean getAtacadores() {
        return atacadores;
    }

    public String getCor() {
        return cor;
    }

    public LocalDate getDataDeLancamento() {
        return this.dataDeLancamento;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public void setAtacadores(boolean atacadores) {
        this.atacadores = atacadores;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setDataDeLancamento(LocalDate dataDeLancamento) {
        this.dataDeLancamento = dataDeLancamento;
    }

    public Sapatilhas clone(){
        return new Sapatilhas(this);
    }

    @Override
    public String toString() {
        return "Sapatilhas{" +
                super.toString() +
                "tamanho=" + tamanho +
                ", atacadores=" + atacadores +
                ", cor=" + cor +
                ", dataDeLancamento=" + dataDeLancamento +
                '}';
    }

    //------------------------------------------------------------------------------------------------------------------

    public double calculaPreco(){
        double precoAtual = this.getPrecoBase();
        int num_donos = this.getNumDonos();
        int idade = this.idade;
        Estado estado = this.getEstado();
        int Tamanho = this.tamanho;
        if(Tamanho > 45 && estado == Estado.Novo)
            precoAtual =- (precoAtual * (Tamanho/(45.*3.)));
        else if(estado == Estado.Usado && (idade >= 1))
            precoAtual =- precoAtual * (num_donos + idade)/25;
        return precoAtual;
    }

}
