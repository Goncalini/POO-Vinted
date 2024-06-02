import exceptions.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Controlador implements Serializable{

    private Map<String,Utilizador> utilizadores; //coleçao de todos os utilizadores criados

    private Map<String,Artigo> artigos; // coleçao de todos os artigos

    private Map<String,Transportadora> transportadoras; // coleçao de todas as transportadoras

    private Map<String,Encomenda> encomendas; // coleçao de todas as encomendas criadas

    private double valorVintage; // valor ganho pela Vintage

    private Map<LocalDate,Estado> estados; // coleçao de todos os estados aka dias em que houve atividade no site

    private LocalDate diaAtual; // dia atual, que vai ser usado na criaçao dos estados

    public Controlador(){
        this.utilizadores = new HashMap<>();
        this.artigos = new HashMap<>();
        this.transportadoras = new HashMap<>();
        this.encomendas = new HashMap<>();
        this.valorVintage = 0;
        this.estados = new HashMap<>();
        this.diaAtual = LocalDate.now();
    }

    public Controlador(Map<String, Utilizador> utilizadores,Map<String, Artigo> artigos, Map<String, Transportadora> transportadoras, Map<String, Encomenda> encomendas, Map<LocalDate, Estado> estados, LocalDate diaAtual) {
        this.setUtilizadores(utilizadores);
        this.setArtigos(artigos);
        this.setTransportadoras(transportadoras);
        this.setEncomendas(encomendas);
        this.setValorVintage(0);
        this.setEstados(estados);
        this.setDiaAtual(diaAtual);
    }

    public Utilizador getUtilizadorEmail(String email) throws EmailNaoExistenteException{
        Utilizador u = null;
        for (Map.Entry<String, Utilizador> entry : this.utilizadores.entrySet()) {
            if((entry.getValue().getEmail()).equals(email)) {
                u = entry.getValue();
                break;
            }
        }
        if(u == null){
            throw new EmailNaoExistenteException("Este email de utilizador não existe!");
        }
        return u.clone();
    }

    public Artigo getArtigo(String ID) throws IdArtigoNaoExistenteException{
        if(!this.artigos.containsKey(ID)) throw new IdArtigoNaoExistenteException("Este artigo não existe!");
        return this.artigos.get(ID);
    }

    public void setValorVintage(double valorVintage) {
        this.valorVintage = valorVintage;
    }

    public Map<String, Encomenda> getEncomendas() {
        Map<String,Encomenda> newMap= new HashMap<>();
        for(Map.Entry<String,Encomenda> m : encomendas.entrySet())
            newMap.put(m.getKey(),m.getValue().clone());
        return newMap;
    }

    public Map<String, Transportadora> getTransportadoras() {
        Map<String,Transportadora> newMap = new HashMap<>();
        for(Map.Entry<String,Transportadora> m : transportadoras.entrySet())
            newMap.put(m.getKey(),m.getValue().clone());
        return newMap;
    }

    public Map<String, Artigo> getArtigos() {
        Map<String,Artigo> newMap= new HashMap<>();
        for(Map.Entry<String,Artigo> m : artigos.entrySet())
            newMap.put(m.getKey(),m.getValue().clone());
        return newMap;
    }

    public Map<String, Utilizador> getUtilizadores() {
        Map<String,Utilizador> newMap= new HashMap<>();
        for(Map.Entry<String,Utilizador> m : utilizadores.entrySet())
            newMap.put(m.getKey(),m.getValue().clone());
        return newMap;
    }

    public Map<LocalDate, Estado> getEstados() {
        Map<LocalDate, Estado> newMap= new HashMap<>();
        for(Map.Entry<LocalDate, Estado> m : estados.entrySet())
            newMap.put(LocalDate.from(m.getKey()),m.getValue().clone());
        return newMap;
    }

    public LocalDate getDiaAtual(){
        return LocalDate.from(this.diaAtual);
    }

    public void setEncomendas(Map<String, Encomenda> encomendas) {
        this.encomendas = new HashMap<>();
        for(String l : encomendas.keySet())
            this.encomendas.put(l,encomendas.get(l).clone());
    }

    public void setTransportadoras(Map<String, Transportadora> transportadoras) {
        this.transportadoras = new HashMap<>();
        for(String l : transportadoras.keySet())
            this.transportadoras.put(l,transportadoras.get(l).clone());
    }

    public void setArtigos(Map<String, Artigo> artigos) {
        this.artigos = new HashMap<>();
        for(String l : artigos.keySet())
            this.artigos.put(l,artigos.get(l).clone());
    }

    public void setUtilizadores(Map<String, Utilizador> utilizadores) {
        this.utilizadores = new HashMap<>();
        for(String l : utilizadores.keySet())
            this.utilizadores.put(l,utilizadores.get(l).clone());
    }

    public void setEstados(Map<LocalDate, Estado> estados) {
        this.estados = new HashMap<>();
        for(Map.Entry<LocalDate, Estado> m : estados.entrySet())
            this.estados.put(LocalDate.from(m.getKey()),m.getValue().clone());
    }

    public void setDiaAtual(LocalDate diaAtual){
        this.diaAtual = LocalDate.from(diaAtual);
    }

    //------------------------------------------------------------------------------------------------------------------

    public void criaUtilizador(String email, String nome, String morada, int numero_fiscal, Map<String,Artigo> produtos_vendidos, Map<String,Artigo> produtos_para_venda, Map<String,Artigo> produtos_adquiridos, Map<String,Artigo> carrinho,Map<String,Encomenda> comprasFeitas){
        Utilizador novo = new Utilizador(email, nome, morada, numero_fiscal, produtos_vendidos, produtos_para_venda, produtos_adquiridos, carrinho,comprasFeitas);
        novo.setCodigo(novo.criaCodigo());
        novo.setValorVendas(novo.valorVendas());
        this.utilizadores.put(novo.getCodigo(),novo.clone());
    }

    public void criaTransportadora(String nome, double margem_lucro,int formula){
        Transportadora novo = new Transportadora(nome, margem_lucro,formula);
        this.transportadoras.put(novo.getNome(),novo.clone());
    }

    public Encomenda criaEncomenda(String ID, Map<String,Artigo> lista_artigos){
        Encomenda novo = new Encomenda(ID, lista_artigos);
        novo.setDimensao(novo.dimensao());
        novo.setCustosDeExpedicao(novo.precoExpedicaoEncomenda(transportadoras));
        novo.setPrecoFinal(novo.valorFinalEncomenda(transportadoras));
        novo.setDataDeCriacao(this.diaAtual);
        this.encomendas.put(novo.getID(),novo.clone());
        return novo;
    }

    public void criaTshirt(String emailUser, String avaliacaoEstado, int num_donos, String descricao, String marca, String ID, double precoBase, String idTransportadora, String idVendedor,TShirt.Tamanho tamanho, TShirt.Padrao padrao){
        TShirt tshirt = new TShirt(avaliacaoEstado,num_donos,descricao,marca,ID,precoBase,idTransportadora,idVendedor,tamanho,padrao);
        tshirt.setCorrecaoPreco(tshirt.calculaPreco());
        this.artigos.put(tshirt.getID(),tshirt.clone());
        try {
            Utilizador user = this.getUtilizadorEmail(emailUser);
            user.adicionaArtigoParaVenda(tshirt.clone());
            Map<String,Utilizador> un = new HashMap<>();
            for(Map.Entry<String,Utilizador> unEntry : this.utilizadores.entrySet()){
                if(unEntry.getValue().getEmail().equals(emailUser)){
                    un.put(unEntry.getKey(),user.clone());
                } else {
                    un.put(unEntry.getKey(),unEntry.getValue().clone());
                }
            }
            this.utilizadores = un;
        }
        catch(EmailNaoExistenteException e){
            System.out.println("Este utilizador não existe!");
        }
    }

    public void criaSapatilhas(String emailUser,String avaliacaoEstado, int num_donos, String descricao, String marca, String ID, double precoBase, String idTransportadora, String idVendedor, int tamanho, boolean atacadores, String cor, LocalDate dataDeLancamento){
        Sapatilhas sapatilhas = new Sapatilhas(avaliacaoEstado, num_donos, descricao, marca, ID, precoBase, idTransportadora, idVendedor, tamanho, atacadores, cor, LocalDate.from(dataDeLancamento));
        sapatilhas.setCorrecaoPreco(sapatilhas.calculaPreco());
        sapatilhas.setIdade(this.diaAtual.getYear() - sapatilhas.getDataDeLancamento().getYear());
        this.artigos.put(sapatilhas.getID(),sapatilhas.clone());
        try {
            Utilizador user = this.getUtilizadorEmail(emailUser);
            user.adicionaArtigoParaVenda(sapatilhas.clone());
            Map<String,Utilizador> un = new HashMap<>();
            for(Map.Entry<String,Utilizador> unEntry : this.utilizadores.entrySet()){
                if(unEntry.getValue().getEmail().equals(emailUser)){
                    un.put(unEntry.getKey(),user.clone());
                } else {
                    un.put(unEntry.getKey(),unEntry.getValue().clone());
                }
            }
            this.utilizadores = un;
        }
        catch (EmailNaoExistenteException e){
            System.out.println("Este utilizador não existe!");
        }
    }

     public void criaSapatilhasPremium(String emailUser, String avaliacaoEstado, int num_donos, String descricao, String marca, String ID, double precoBase, String idTransportadora, String idVendedor, int tamanho, boolean atacadores, String cor, LocalDate dataDeLancamento, String autor){
         SapatilhasPremium sapatilhas = new SapatilhasPremium(avaliacaoEstado, num_donos, descricao, marca, ID, precoBase, idTransportadora, idVendedor, tamanho, atacadores, cor, dataDeLancamento,autor);
         sapatilhas.setCorrecaoPreco(sapatilhas.calculaPreco());
         sapatilhas.setIdade(this.diaAtual.getYear() - sapatilhas.getDataDeLancamento().getYear());
         this.artigos.put(sapatilhas.getID(),sapatilhas.clone());
         try {
             Utilizador user = this.getUtilizadorEmail(emailUser);
             user.adicionaArtigoParaVenda(sapatilhas.clone());
             Map<String,Utilizador> un = new HashMap<>();
            for(Map.Entry<String,Utilizador> unEntry : this.utilizadores.entrySet()){
                if(unEntry.getValue().getEmail().equals(emailUser)){
                    un.put(unEntry.getKey(),user.clone());
                } else {
                    un.put(unEntry.getKey(),unEntry.getValue().clone());
                }
            }
            this.utilizadores = un;
         }
         catch (EmailNaoExistenteException e){
             System.out.println("Este utilizador não existe!");
         }
     }

    public void criaMala(String emailUser,String avaliacaoEstado, int num_donos, String descricao, String marca, String ID, double precoBase, String idTransportadora, String idVendedor, int anoColecao, int tamanho, int material){
        Mala mala = new Mala(avaliacaoEstado, num_donos, descricao, marca, ID, precoBase, idTransportadora, idVendedor, anoColecao, tamanho, material);
        mala.setCorrecaoPreco(mala.calculaPreco());
        mala.setIdade(this.diaAtual.getYear() - mala.getAnoColecao());
        this.artigos.put(mala.getID(),mala.clone());
        try {
            Utilizador user = this.getUtilizadorEmail(emailUser);
            user.adicionaArtigoParaVenda(mala.clone());
            Map<String,Utilizador> un = new HashMap<>();
            for(Map.Entry<String,Utilizador> unEntry : this.utilizadores.entrySet()){
                if(unEntry.getValue().getEmail().equals(emailUser)){
                    un.put(unEntry.getKey(),user.clone());
                } else {
                    un.put(unEntry.getKey(),unEntry.getValue().clone());
                }
            }
            this.utilizadores = un;
        }
        catch (EmailNaoExistenteException e){
            System.out.println("Este utilizador não existe!");
        }
    }

    public void criaMalaPremium(String emailUser, String avaliacaoEstado, int num_donos, String descricao, String marca, String ID, double precoBase, String idTransportadora, String idVendedor, int anoColecao, int tamanho, int material, int valorizacao){
        MalaPremium mala = new MalaPremium(avaliacaoEstado, num_donos, descricao, marca, ID, precoBase, idTransportadora, idVendedor,anoColecao, tamanho, material,valorizacao);
        mala.setCorrecaoPreco(mala.calculaPreco());
        mala.setIdade(this.diaAtual.getYear() - mala.getAnoColecao());
        this.artigos.put(mala.getID(),mala.clone());
        try {
            Utilizador user = this.getUtilizadorEmail(emailUser);
            user.adicionaArtigoParaVenda(mala.clone());
            Map<String,Utilizador> un = new HashMap<>();
            for(Map.Entry<String,Utilizador> unEntry : this.utilizadores.entrySet()){
                if(unEntry.getValue().getEmail().equals(emailUser)){
                    un.put(unEntry.getKey(),user.clone());
                } else {
                    un.put(unEntry.getKey(),unEntry.getValue().clone());
                }
            }
            this.utilizadores = un;
        }
        catch (EmailNaoExistenteException e){
            System.out.println("Este utilizador não existe!");
        }
    }

    public void criaEstado(){
        Estado estadoNovo = new Estado(this.diaAtual);
        this.estados.put(this.diaAtual,estadoNovo.clone());
    }

    public void adicionaUtilizador(Utilizador u){
        this.utilizadores.put(u.getCodigo(),u.clone());
    }

    public void adicionaTransportadora(Transportadora t){
        this.transportadoras.put(t.getNome(),t.clone());
    }

    public void adicionaSapatilhas(Sapatilhas s){
        this.artigos.put(s.getID(),s.clone());
    }

    public double somaValorVintage(double valor){
        return this.valorVintage + valor;
    }

    //------------------------------------------------------------------------------------------------------------------

    public void mudarData (int dias) {
        this.diaAtual = this.diaAtual.plusDays(dias);
        criaEstado();
    }

    public Estado obterEstado () {
        return this.estados.get(this.diaAtual);
    }

    //------------------------------------------------------------------------------------------------------------------

    public void alteraEstadoEncomendas(Map<String,Encomenda> encomendas){
        Map<String,Encomenda> encomendasUpdated = new HashMap<>();
        for(Map.Entry<String,Encomenda> entry : encomendas.entrySet()){
            Encomenda encomenda = entry.getValue();
            encomenda.encomendaFinalizada(this.diaAtual);
            encomendasUpdated.put(encomenda.getID(),encomenda.clone());
        }
        this.setEncomendas(encomendasUpdated);
    }

    public Utilizador encomendaVendida(String ID_encomenda, String ID_Comprador){

        Utilizador comprador = this.utilizadores.get(ID_Comprador);
        Map<String,Artigo> listaArtigos = comprador.getCarrinho();
        Map<String,Encomenda> comprasFeitas = comprador.getComprasFeitas();
        Encomenda encomenda = criaEncomenda(ID_encomenda,listaArtigos);
        encomenda.setEstado(Encomenda.Estado.finalizada);
        encomenda.setDataDeCriacao(this.diaAtual);
        comprasFeitas.put(ID_encomenda,encomenda.clone());

        for(Artigo artigo : listaArtigos.values()){

            String ID_Vendedor = artigo.getIdVendedor();
            Utilizador vendedor = this.utilizadores.get(ID_Vendedor);

            Map<String,Artigo> produtosComprados = comprador.getProdutosAdquiridos();
            produtosComprados.put(artigo.getID(),artigo.clone());
            comprador.setProdutosAdquiridos(produtosComprados);

            Map<String,Artigo> produtosParaVenda = comprador.getProdutosParaVenda();
            produtosParaVenda.put(artigo.getID(),artigo.clone());
            comprador.setProdutosParaVenda(produtosParaVenda);

            //Update de produtos vendidos do vendedor
            Map<String,Artigo> produtosVendidos = vendedor.getProdutosVendidos();
            produtosVendidos.put(artigo.getID(),artigo.clone());
            vendedor.setProdutosVendidos(produtosVendidos);

            Map<String,Artigo> produtosParaVenda2 = vendedor.getProdutosParaVenda();
            produtosParaVenda2.remove(artigo.getID(),artigo);
            vendedor.setProdutosParaVenda(produtosParaVenda2);

            //Update do valor de vendas do vendedor
            double valor_venda = vendedor.getValorVendas();
            double valor_update = valor_venda + artigo.getCorrecaoPreco();
            vendedor.setValorVendas(valor_update);

            //Consequências da venda para a transportadora
            String ID = artigo.getTransportadora();
            Transportadora transportadora = this.transportadoras.get(ID);
            double lucro = transportadora.getTotalLucro();
            lucro += artigo.precoExpedicao(transportadora.getFormula(),transportadora.getMargemLucro(),transportadora.getImposto());
            transportadora.setTotalLucro(lucro);

            this.transportadoras.put(ID,transportadora.clone());

            artigo.setIdExVendedor(ID_Vendedor);
            artigo.setIdVendedor(ID_Comprador);

        }

        double ganhoVintage = encomenda.valorVintage();
        listaArtigos.clear();
        comprador.setCarrinho(listaArtigos);
        comprador.setComprasFeitas(comprasFeitas);
        this.setValorVintage(somaValorVintage(ganhoVintage));

        return comprador;
    }

    public List<String> getNomesTransportadoras(){
        return new ArrayList<>(this.transportadoras.keySet());
    }

    public List<String> getIdsArtigos(){
        return new ArrayList<>(this.artigos.keySet());
    }

    public void removeArtigo(String ID,Map<String,Artigo> lista) throws IdArtigoNaoExistenteException{
        if(!lista.containsKey(ID)) throw new IdArtigoNaoExistenteException("Artigo não existente!");
        lista.remove(ID);
    }

    public String verificaEmail(String email) throws EmailExistenteException{
        String mail = "";
        for (Map.Entry<String, Utilizador> entry : this.utilizadores.entrySet()) {
            if((entry.getValue().getEmail()).equals(email)) {
                mail = entry.getValue().getEmail();
                break;
            }
        }
        if(mail.equals(email)){
            throw new EmailExistenteException("Este email já existe!");
        }
        return email;
    }

    public String existeTransportadora(String nome) throws TransportadoraExistenteException{
        if(this.transportadoras.containsKey(nome)) throw new TransportadoraExistenteException("Esta transportadora já existe!");
        return nome;
    }

    public String naoExisteTransportadora(String nome) throws TransportadoraNaoExistenteException{
        if(!this.transportadoras.containsKey(nome)) throw new TransportadoraNaoExistenteException("Esta transportadora não existe!");
        return nome;
    }

    public String verificaArtigo(String ID) throws IdArtigoExistenteException{
        if(this.artigos.containsKey(ID)) throw new IdArtigoExistenteException("Este artigo já existe!");
        return ID;
    }

    public void updateUtilizador(Utilizador u){
        this.utilizadores.put(u.getCodigo(),u.clone());
    }

    //----------------------------------------Queries-------------------------------------------------------------------

    public Utilizador vendedorMaisFaturouSempre (){
        return this.utilizadores.entrySet()
            .stream()
            .max((entry1, entry2) -> entry1.getValue().getValorVendas() > entry2.getValue().getValorVendas() ? 1 : -1)
            .get()
            .getValue();
    }

    public Transportadora transportadoraComMaisFaturacao (){
        return this.transportadoras.entrySet()
            .stream()
            .max((entry1, entry2) -> entry1.getValue().getTotalLucro() > entry2.getValue().getTotalLucro() ? 1 : -1)
            .get()
            .getValue();
    }

    public void listarEncomendasDeVendedor(String utilizador) {
        for(Map.Entry<String,Encomenda> encomenda : this.encomendas.entrySet()){
            for(Map.Entry<String,Artigo> artigo : encomenda.getValue().getListaArtigos().entrySet()){
                if(artigo.getValue().getIdVendedor().equals(utilizador)){
                    System.out.println("Encomenda " + encomenda.getKey() + " com os artigos " + encomenda.getValue().getListaArtigos());
                    break;
                }
            }
        }
    }

    public double valorVintageTotalFuncionamento(){
        return valorVintage;
    }

}
