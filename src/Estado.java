import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Estado implements Serializable{

    private Set<String> utilizadores; // coleçao de utilizadores num dia
    private Set<String> encomendas; // coleçao de encomendas num dia
    private LocalDate diaAtual; // dia do estado

    public Estado() {
        this.utilizadores = new HashSet<>();
        this.encomendas = new HashSet<>();
        this.diaAtual = LocalDate.now();
    }

    public Estado(LocalDate diaAtual) {
        this();
        this.setDiaAtual(diaAtual);
    }

    public Estado(Estado e){
        this.setUtilizadores(e.getUtilizadores());
        this.setEncomendas(e.getEncomendas());
        this.setDiaAtual(e.getDiaAtual());
    }

    public void setUtilizadores(Set<String> utilizadores) {
        this.utilizadores = new HashSet<>();
        for(String s : utilizadores){
            this.utilizadores.add(s);
        }
    }

    public void setEncomendas(Set<String> encomendas) {
        this.encomendas = new HashSet<>();
        for(String s : encomendas){
            this.encomendas.add(s);
        }
    }

    public void setDiaAtual(LocalDate diaAtual) {
        this.diaAtual = LocalDate.from(diaAtual);
    }

    public Set<String> getUtilizadores() {
        Set<String> rUtilizadores = new HashSet<>();
        for(String s : this.utilizadores){
            rUtilizadores.add(s);
        }
        return rUtilizadores;
    }

    public Set<String> getEncomendas() {
        Set<String> rEncomendas = new HashSet<>();
        for(String s : this.encomendas){
            rEncomendas.add(s);
        }
        return rEncomendas;
    }

    public LocalDate getDiaAtual() {
        return LocalDate.from(diaAtual);
    }

    public String toString() {
        return "Estado [utilizadores=" + utilizadores + ", encomendas=" + encomendas + ", diaAtual=" + diaAtual + "]";
    }

    public Estado clone(){
        return new Estado(this);
    }

}
