import exceptions.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Programa implements Serializable{

    private Controlador controller; // controlador do programa aka o que vai conter tudo (tipo uma base de dados)

    private Utilizador user; // user que esta logged in

    private Map<String,Artigo> artigosParaCompra; //artigos que o user que esta logado pode comprar

    private final Scanner scan;

    public static String letra = "A";
    public static long contador = 1;

    public String criaCodigo(){
        String novoCodigo = letra + contador;
        contador++;
        return novoCodigo;
    }

    public Programa() {
        this.controller = new Controlador();
        this.scan = new Scanner(System.in);
        this.artigosParaCompra = new HashMap<>();
    }

    public void guardaControlador(String filename) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
        oos.writeObject(this.controller);
        oos.flush();
        oos.close();
    }

    public static Controlador carregaControlador(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
        Controlador f = (Controlador) ois.readObject();
        ois.close();
        return f;
    }

    public void carregaController(String filename) throws IOException, ClassNotFoundException {
        Controlador controller = carregaControlador(filename);
        this.setController(controller);
    }

    public Controlador getController() {
        return controller;
    }

    public void setController(Controlador controller) {
        this.controller = controller;
    }

    public Map<String,Artigo> getArtigosParaCompra(){
        Map<String,Artigo> newmap = new HashMap<String,Artigo>();
        for(Map.Entry<String,Artigo> m : artigosParaCompra.entrySet())
            newmap.put(m.getKey(), m.getValue().clone());
        return newmap;
    }

    public void setArtigosParaCompra(Map<String,Artigo> artigosParaCompra){
        this.artigosParaCompra = new HashMap<>();
        for(String l : artigosParaCompra.keySet()){
            this.artigosParaCompra.put(l,artigosParaCompra.get(l).clone());
        }
    }

    public int formulaTransportadora(){
        String[] opcoesFormula = {"Preço de Expedição = Preço Base * Margem de Lucro * (1 + Imposto) * 0.9","Preço de Expedição = Preço de Base * Margem de Lucro + Imposto)","Preço de Expedição = (Preço de Base/Imposto) * Margem de Lucro"};
        System.out.println("Que fórmula deseja escolher?");
        Menu menuFormulas = new Menu(opcoesFormula);
        menuFormulas.executa();
        return menuFormulas.getOpcao();
    }

    public void criacaoTransportadora(){
        try {
            System.out.println("Insira o nome da transportadora");
            System.out.println("Nomes de transportadoras existentes no sistema: " + this.controller.getNomesTransportadoras());
            String nome = this.controller.existeTransportadora(scan.nextLine());
            System.out.println("Insira a margem de lucro da transportadora:");
            double margemLucro = Double.parseDouble(scan.nextLine());
            int formula = formulaTransportadora();
            this.controller.criaTransportadora(nome, margemLucro,formula);
        }
        catch(TransportadoraExistenteException e){
            System.out.println("Esta transportadora já existe!");
        }
    }

    public void criacaoSapatilhas(){
        System.out.println("Insira o número de donos:");
        int numeroDeDonos = Integer.parseInt(this.scan.nextLine());
        String avaliacaoEstado = "";
        if(numeroDeDonos != 0){
            System.out.println("Insira a avaliação de estado das sapatilhas:");
            avaliacaoEstado = scan.nextLine();
        }
        System.out.println("Insira a descrição das sapatilhas:");
        String descricaoArtigo = scan.nextLine();
        System.out.println("Insira a marca das sapatilhas:");
        String marca = scan.nextLine();
        try {
            System.out.println("Insira o ID das sapatilhas:");
            System.out.println("Ids de artigos existentes no sistema: " + this.controller.getIdsArtigos());
            String IdArtigo = this.controller.verificaArtigo(scan.nextLine());
            System.out.println("Insira o preço base das sapatilhas:");
            double precoBase = Double.parseDouble(scan.nextLine());
            try {
                System.out.println("Insira o nome da transportadora que deseja para a entrega das sapatilhas:");
                System.out.println("Nomes de transportadoras existentes no sistema: " + this.controller.getNomesTransportadoras());
                String nomeTransportadora = this.controller.naoExisteTransportadora(scan.nextLine());
                System.out.println("Insira o tamanho das sapatilhas:");
                int tamanhoSapatilhas = Integer.parseInt(scan.nextLine());
                boolean atacadoresSapatilhas = atacadoresSapatilhas();
                System.out.println("Insira a cor das sapatilhas:");
                String corSapatilhas = scan.nextLine();
                System.out.println("Insira a data de lançamento:");
                String dataLancamento = scan.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                LocalDate data = LocalDate.parse(dataLancamento, formatter);
                boolean tipoArtigo = tipoArtigo();
                if (tipoArtigo) {
                    this.controller.criaSapatilhas(user.getEmail(), avaliacaoEstado, numeroDeDonos, descricaoArtigo, marca, IdArtigo, precoBase, nomeTransportadora, this.user.getCodigo(), tamanhoSapatilhas, atacadoresSapatilhas, corSapatilhas, data);
                } else {
                    System.out.println("Insira o nome do autor das sapatilhas:");
                    String nome = scan.nextLine();
                    this.controller.criaSapatilhasPremium(user.getEmail(), avaliacaoEstado, numeroDeDonos, descricaoArtigo, marca, IdArtigo, precoBase, nomeTransportadora, this.user.getCodigo(), tamanhoSapatilhas, atacadoresSapatilhas, corSapatilhas, data, nome);
                }
                System.out.println("Artigo criado!");
            } catch(TransportadoraNaoExistenteException e){
                System.out.println("Esta transportadora não existe!");
            }
        }
        catch(IdArtigoExistenteException e){
            System.out.println("Este ID já está ocupado!");
        }
    }

    public void criacaoMala() {
        System.out.println("Insira o número de donos:");
        int numeroDeDonos = Integer.parseInt(this.scan.nextLine());
        String avaliacaoEstado = "";
        if (numeroDeDonos != 0) {
            System.out.println("Insira a avaliação de estado da mala:");
            avaliacaoEstado = scan.nextLine();
        }
        System.out.println("Insira a descrição da mala:");
        String descricaoArtigo = scan.nextLine();
        System.out.println("Insira a marca da mala:");
        String marca = scan.nextLine();
        try {
            System.out.println("Insira o ID da mala:");
            System.out.println("Ids de artigos existentes no sistema: " + this.controller.getIdsArtigos());
            String IdArtigo = this.controller.verificaArtigo(scan.nextLine());
            System.out.println("Insira o preço base da mala:");
            double precoBase = Double.parseDouble(scan.nextLine());
            try {
                System.out.println("Insira o nome da transportadora que deseja para a entrega da mala:");
                System.out.println("Nomes de transportadoras existentes no sistema: " + this.controller.getNomesTransportadoras());
                String nomeTransportadora = this.controller.naoExisteTransportadora(scan.nextLine());
                System.out.println("Insira o ano de coleção da mala:");
                int anoMala = Integer.parseInt(scan.nextLine());
                System.out.println("Insira o tamanho da mala:");
                int tamanhoMala = Integer.parseInt(scan.nextLine());
                System.out.println("Insira a qualidade do material da mala:");
                int qualidadeMala = Integer.parseInt(scan.nextLine());
                boolean tipoArtigo = tipoArtigo();
                if (tipoArtigo) {
                    this.controller.criaMala(user.getEmail(), avaliacaoEstado, numeroDeDonos, descricaoArtigo, marca, IdArtigo, precoBase, nomeTransportadora, this.user.getCodigo(), anoMala, tamanhoMala, qualidadeMala);
                } else {
                    System.out.println("Insira a valorização da mala:");
                    int valorizacao = Integer.parseInt(scan.nextLine());
                    this.controller.criaMalaPremium(user.getEmail(), avaliacaoEstado, numeroDeDonos, descricaoArtigo, marca, IdArtigo, precoBase, nomeTransportadora, this.user.getCodigo(), anoMala, tamanhoMala, qualidadeMala, valorizacao);
                }
                System.out.println("Artigo criado!");
            } catch(TransportadoraNaoExistenteException e){
                System.out.println("Esta transportadora não existe!");
            }
        }
        catch (IdArtigoExistenteException e) {
            System.out.println("Este ID já está ocupado!");
        }
    }

    public void criacaoTshirt() {
        System.out.println("Insira o número de donos:");
        int numeroDeDonos = Integer.parseInt(this.scan.nextLine());
        String avaliacaoEstado = "";
        if (numeroDeDonos != 0) {
            System.out.println("Insira a avaliação de estado da Tshirt:");
            avaliacaoEstado = scan.nextLine();
        }
        System.out.println("Insira a descrição da Tshirt:");
        String descricaoArtigo = scan.nextLine();
        System.out.println("Insira a marca da Tshirt:");
        String marca = scan.nextLine();
        try {
            System.out.println("Insira o ID da Tshirt:");
            System.out.println("Ids de artigos existentes no sistema: " + this.controller.getIdsArtigos());
            String IdArtigo = this.controller.verificaArtigo(scan.nextLine());
            System.out.println("Insira o preço base da Tshirt:");
            double precoBase = Double.parseDouble(scan.nextLine());
            try {
                System.out.println("Insira o nome da transportadora que deseja para a entrega da Tshirt:");
                System.out.println("Nomes de transportadoras existentes no sistema: " + this.controller.getNomesTransportadoras());
                String nomeTransportadora = this.controller.naoExisteTransportadora(scan.nextLine());
                TShirt.Padrao padraoTshirt = padraoTshirt();
                TShirt.Tamanho tamanhoTshirt = tamanhoTshirt();
                this.controller.criaTshirt(user.getEmail(), avaliacaoEstado, numeroDeDonos, descricaoArtigo, marca, IdArtigo, precoBase, nomeTransportadora, this.user.getCodigo(), tamanhoTshirt, padraoTshirt);
                this.user = this.controller.getUtilizadores().get(user.getCodigo());
                System.out.println("Artigo criado!");
            } catch (TransportadoraNaoExistenteException e) {
                System.out.println("Esta transportadora não existe!");
            }
        }
        catch (IdArtigoExistenteException e) {
            System.out.println("Este ID já está ocupado!");
        }
    }

    public boolean atacadoresSapatilhas(){
        String[] opcoesAtacadores = {"Sim","Não"};
        System.out.println("As sapatilhas têm atacadores?");
        Menu menuAtacadores = new Menu(opcoesAtacadores);
        menuAtacadores.executa();
        return menuAtacadores.getOpcao() == 1;
    }

    public boolean tipoArtigo(){
        String[] opcoesMala = {"Normal","Premium"};
        System.out.println("Que tipo de Artigo deseja?");
        Menu menuTipo = new Menu(opcoesMala);
        menuTipo.executa();
        return menuTipo.getOpcao() == 1;
    }

    public void removeEncomenda(){
        try {
            System.out.println("Insira o ID da encomenda que deseja devolver: \n");
            Map<String, Encomenda> lista = this.user.getComprasFeitas();
            for (Map.Entry<String, Encomenda> entry : lista.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue().toString());
            }
            String IdEncomenda = this.user.verificaEncomenda(scan.nextLine());
            try {
                devolveEncomenda(IdEncomenda);
            } catch(DevolucaoImpossivelException e){
                System.out.println("A devolução da encomenda não é possível!");
            }
        } catch (IdEncomendaNaoExistenteException e) {
            System.out.println("Id de encomenda inserido não existe!");;
        }
    }

    public void devolveEncomenda(String IdEncomenda) throws DevolucaoImpossivelException{
        Map<String,Encomenda> comprasFeitas = this.user.getComprasFeitas();
        Map<String,Artigo> produtosAdquiridos = this.user.getProdutosAdquiridos();
        Encomenda compra = comprasFeitas.get(IdEncomenda);
        Map<String,Artigo> artigosCompra = compra.getListaArtigos();
        LocalDate dataAtual = this.controller.getDiaAtual();
        if(compra.idadeEncomenda(dataAtual) < 2){
            comprasFeitas.remove(IdEncomenda);
            this.user.setComprasFeitas(comprasFeitas);
            this.artigosParaCompra.putAll(artigosCompra);
            for(Map.Entry<String,Artigo> entry : artigosCompra.entrySet()){
                produtosAdquiridos.remove(entry.getKey());
                Artigo artigo = entry.getValue();
                String ID_Vendedor = artigo.getIdVendedor();
                String ID_ExVendedor = artigo.getIdExVendedor();
                artigo.setIdExVendedor(ID_Vendedor);
                artigo.setIdVendedor(ID_ExVendedor);
            }
            this.user.setProdutosAdquiridos(produtosAdquiridos);
        }
        else throw new DevolucaoImpossivelException("A devolução da encomenda não é possível!");
    }

    public TShirt.Padrao padraoTshirt(){
        String[] opcoesPadrao = {"Liso","Riscas","Palmeiras"};
        System.out.println("Que padrão deseja para a Tshirt?");
        TShirt.Padrao padrao = null;
        Menu menuPadrao = new Menu(opcoesPadrao);
        menuPadrao.executa();
        switch(menuPadrao.getOpcao()){
            case(1) -> {
                padrao = TShirt.Padrao.liso;
            }
            case(2) -> {
                padrao = TShirt.Padrao.riscas;
            }
            case(3) -> {
                padrao = TShirt.Padrao.palmeiras;
            }
        }
        return padrao;
    }

    public TShirt.Tamanho tamanhoTshirt(){
        String[] opcoesTamanho = {"S","M","L","XL"};
        System.out.println("Que tamanho deseja para a Tshirt?");
        TShirt.Tamanho tamanho = null;
        Menu menuTamanho = new Menu(opcoesTamanho);
        menuTamanho.executa();
        switch(menuTamanho.getOpcao()){
            case(1) -> {
                tamanho = TShirt.Tamanho.S;
            }
            case(2) -> {
                tamanho = TShirt.Tamanho.M;
            }
            case(3) -> {
                tamanho = TShirt.Tamanho.L;
            }
            case(4) -> {
                tamanho = TShirt.Tamanho.XL;
            }
        }
        return tamanho;
    }

    public void criaArtigo(){
        String[] opcoesCompra = {"TShirt","Mala","Sapatilhas","Voltar"};
        System.out.println("Que artigo deseja criar?");
        Menu menuArtigo = new Menu(opcoesCompra);
        do{
            menuArtigo.executa();
            switch(menuArtigo.getOpcao()){
                case(1) -> {
                    criacaoTshirt();
                    break;
                }
                case(2) -> {
                    criacaoMala();
                    break;
                }
                case(3) -> {
                    criacaoSapatilhas();
                    break;
                }
            }
        } while(menuArtigo.getOpcao() != 4);
    }

    public void criaUtilizador(){
        Map<String,Artigo> produtosVendidos = new HashMap<>();
        Map<String,Artigo> produtosParaVenda = new HashMap<>();
        Map<String,Artigo> produtosAdquiridos = new HashMap<>();
        Map<String,Artigo> carrinho = new HashMap<>();
        Map<String,Encomenda> comprasFeitas = new HashMap<>();
        try {
            System.out.println("Insira o email do utilizador:");
            String email = this.controller.verificaEmail(this.scan.nextLine());
            System.out.println("Insira o nome do utilizador:");
            String nome = scan.nextLine();
            System.out.println("Insira a morada do utilizador:");
            String morada = scan.nextLine();
            System.out.println("Insira o número fiscal do utilizador:");
            int NIF = Integer.parseInt(scan.nextLine());
            this.controller.criaUtilizador(email, nome, morada, NIF, produtosVendidos, produtosParaVenda, produtosAdquiridos, carrinho,comprasFeitas);
        }
        catch(EmailExistenteException e){
            System.out.println("Este email já existe!");
        }
    }

    public void mudaTempo(){
        System.out.println("Quantos dia deseja avançar?");
        int numDias = Integer.parseInt(scan.nextLine());
        this.controller.mudarData(numDias);
        this.controller.alteraEstadoEncomendas(this.controller.getEncomendas());
    }

    public void gestaoUtilizador(){
        String[] opcoes = {"Criar Artigo","Adicionar Artigo ao Carrinho","Remover Artigo ao Carrinho","Carrinho","Artigos vendidos","Artigos comprados","Artigos para venda","Devolver Encomenda","Estatísticas","Faturas","Mudar tempo","Logout"};
        Menu menuUser = new Menu(opcoes);
        filtraArtigosParaCompra(this.user.getCodigo());
        do{
            System.out.println("***MENU DE UTILIZADOR***\n");
            System.out.println("Bem-vindo, " + this.user.getNome() + "!\n");
            menuUser.executa();
            switch (menuUser.getOpcao()){
                case(1) -> criaArtigo();
                case(2) -> compraArtigo();
                case(3) -> removeArtigoEncomenda();
                case(4) -> Carrinho();
                case(5) -> artigosVendidos();
                case(6) -> artigosComprados();
                case(7) -> artigosParaVenda();
                case(8) -> removeEncomenda();
                case(9) -> estatisticasPrograma();
                case(10) -> faturas();
                case(11) -> mudaTempo();
            }
        } while(menuUser.getOpcao() != 12);
    }

    public void estatisticasPrograma(){
        String[] opcoes = {"Vendedor que mais facturou num período ou desde sempre","Transportador com maior volume de facturação","Listagem de encomendas emitidas por um vendedor","Ordenação dos maiores compradores/vendedores do sistema durante um período de tempo","Dinheiro ganho pela Vintage no seu funcionamento","Voltar"};
        System.out.println("MENU Estatísticas\n");
        Menu menuEstatisticas = new Menu(opcoes);
        do{
            menuEstatisticas.executa();
            switch(menuEstatisticas.getOpcao()){
                case(1) -> {query1();}
                case(2) -> {query2();}
                case(3) -> {query3();}
                case(4) -> {}
                case(5) -> {query5();}
            }
        } while(menuEstatisticas.getOpcao() != 6);
    }

    public boolean tipoQuery1(){
        String[] opcoesCompra = {"Desde sempre","Num período"};
        System.out.println("Em que intervalo de tempo deseja verificar?");
        Menu menuArtigo = new Menu(opcoesCompra);
        menuArtigo.executa();
        return menuArtigo.getOpcao() == 1;
    }

    public void query1(){
        boolean opcao = tipoQuery1();
        if(opcao) System.out.println(this.controller.vendedorMaisFaturouSempre());
       // else
    }

    public void query2(){
        Transportadora t = this.controller.transportadoraComMaisFaturacao();
        if(t == null) System.out.println("Não existem transportadoras!");
        else {
            System.out.println(t.getNome());
            System.out.println(t.getTotalLucro());
        }
    }

    public void query3(){
        System.out.println("Insira o email do utilizador:");
        String email = scan.nextLine();
        try {
            Utilizador user = this.controller.getUtilizadorEmail(email);
            this.controller.listarEncomendasDeVendedor(user.getCodigo());
        }
        catch (EmailNaoExistenteException e){
            System.out.println("Este email de utilizador não existe!");
        }
    }

    public double query5(){
        return this.controller.valorVintageTotalFuncionamento();
    }

    public void Carrinho() {
        String[] opcoes = {"Finalizar compra", "Voltar"};
        System.out.println("O seu carrinho:\n");
        Menu menuCarrinho = new Menu(opcoes);
        String codigo = criaCodigo();
        Map<String, Artigo> lista = this.user.getCarrinho();
        Map<String,Encomenda> comprasFeitas = this.user.getComprasFeitas();
        for (Map.Entry<String, Artigo> entry : lista.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
        }
        do{
            menuCarrinho.executa();
            if(menuCarrinho.getOpcao() == 1){
                this.controller.updateUtilizador(this.user);
                Utilizador user = this.controller.encomendaVendida(codigo,this.user.getCodigo());
                Map<String,Encomenda> encomendasUser = user.getComprasFeitas();
                Encomenda encomenda = encomendasUser.get(codigo);
                Map<String,Artigo> artigosUser = user.getProdutosAdquiridos();
                artigosUser.putAll(lista);
                this.user.setProdutosAdquiridos(artigosUser);
                Map<String,Artigo> artigosVendaUser = user.getProdutosParaVenda();
                for(Map.Entry<String,Artigo> avu : lista.entrySet()){
                    artigosVendaUser.put(avu.getKey(),avu.getValue().clone());
                }
                this.user.setProdutosParaVenda(artigosVendaUser);
                lista.clear();
                this.user.setCarrinho(lista);
                comprasFeitas.put(codigo,encomenda.clone());
                this.user.setComprasFeitas(comprasFeitas);
                this.controller.updateUtilizador(this.user);
            }
        } while(menuCarrinho.getOpcao() != 2);
    }

    public void faturas(){
        String[] opcoes = {"Voltar"};
        System.out.println("As suas faturas: \n");
        Menu menuFaturas = new Menu(opcoes);
        Map<String,Encomenda> encomendasUtilizador = this.user.getComprasFeitas();
        Map<String,Transportadora> transportadoras = this.controller.getTransportadoras();
        for (Map.Entry<String, Encomenda> entry : encomendasUtilizador.entrySet()) {
            entry.getValue().faturaEncomenda(transportadoras);
            System.out.println("\n----------\n");
        }
        do {
            menuFaturas.executa();
        }while(menuFaturas.getOpcao() != 1);
    }

    public void removeArtigoEncomenda(){
        System.out.println("Insira o ID do artigo que deseja remover do carrinho:");
        Map<String, Artigo> lista = this.user.getCarrinho();
        for (Map.Entry<String, Artigo> entry : lista.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
        }
        String IdArtigo = scan.nextLine();
        try{
            this.controller.removeArtigo(IdArtigo,lista);
            Artigo artigo = this.controller.getArtigo(IdArtigo);
            this.user.setCarrinho(lista);
            this.artigosParaCompra.put(IdArtigo,artigo.clone());
        }
        catch(IdArtigoNaoExistenteException e){
            System.out.println("Artigo não existente!");
        }
    }

    public void artigosVendidos(){
        String[] opcoes = {"Voltar"};
        System.out.println("Os seus artigos vendidos: \n");
        Menu menuArtigosVendidos = new Menu(opcoes);
        Map<String,Artigo> artigosVendidosUtilizador = this.user.getProdutosVendidos();
        for (Map.Entry<String, Artigo> entry : artigosVendidosUtilizador.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        do {
            menuArtigosVendidos.executa();
        }while(menuArtigosVendidos.getOpcao() != 1);
    }

    public void artigosParaVenda(){
        String[] opcoes = {"Voltar"};
        System.out.println("Os seus artigos para venda: \n");
        Menu menuArtigosParaVenda = new Menu(opcoes);
        Map<String,Artigo> artigosParaVendaUtilizador = this.user.getProdutosParaVenda();
        for (Map.Entry<String, Artigo> entry : artigosParaVendaUtilizador.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        do {
            menuArtigosParaVenda.executa();
        }while(menuArtigosParaVenda.getOpcao() != 1);
    }

    public void artigosComprados(){
        String[] opcoes = {"Voltar"};
        System.out.println("Os seus artigos comprados: \n");
        Menu menuArtigosComprados = new Menu(opcoes);
        Map<String,Artigo> artigosUtilizador = this.user.getProdutosAdquiridos();
        for (Map.Entry<String, Artigo> entry : artigosUtilizador.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        do {
            menuArtigosComprados.executa();
        }while(menuArtigosComprados.getOpcao() != 1);
    }

    public void compraArtigo(){
        System.out.println("Insira o ID do artigo que deseja adicionar ao carrinho:");
        for (Map.Entry<String, Artigo> entry : this.artigosParaCompra.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        String IdArtigo = scan.nextLine();
        try {
            Artigo artigo = this.controller.getArtigo(IdArtigo);
            Map<String,Artigo> newCarrinho = this.user.getCarrinho();
            newCarrinho.put(IdArtigo,artigo.clone());
            this.artigosParaCompra.remove(IdArtigo);
            this.user.setCarrinho(newCarrinho);
            this.controller.updateUtilizador(this.user);
        }
        catch(IdArtigoNaoExistenteException e){
            System.out.println("Este artigo não existe!");
        }
    }

    public void filtraArtigosParaCompra(String codigoUser){
        Map<String,Artigo> listaController = new HashMap<>();
        for (Map.Entry<String, Artigo> entry : this.controller.getArtigos().entrySet()) {
            if(!(entry.getValue().getIdVendedor()).equals(codigoUser))
                listaController.put(entry.getKey(),entry.getValue().clone());
        }
        for (Map.Entry<String, Artigo> entry : this.user.getProdutosAdquiridos().entrySet()) {
            if(!(entry.getValue().getIdVendedor()).equals(codigoUser))
                listaController.remove(entry.getKey());
        }
        this.setArtigosParaCompra(listaController);
    }

    public void login(){
        System.out.println("Insira o seu email:");
        String email = scan.nextLine();
        try {
            this.user = this.controller.getUtilizadorEmail(email);
            gestaoUtilizador();
        }
        catch (EmailNaoExistenteException e){
            System.out.println("Este email de utilizador não existe!");
        }
    }

    public void run() throws IOException, ClassNotFoundException {
        System.out.println("*** MENU PRINCIPAL ***");
        String[] opcoesMenu = {"Login","Regista Utilizador","Cria Transportadora","Guarda Estado do programa","Carrega Estado do programa","Todos os utilizadores","Todas as transportadoras","Sair"};
        Menu menuPrincipal = new Menu(opcoesMenu);
        do{
            menuPrincipal.executa();
            switch (menuPrincipal.getOpcao()){
                case(1) -> login();
                case(2) -> criaUtilizador();
                case(3) -> criacaoTransportadora();
                case(4) -> guardaControlador("./log.txt");
                case(5) -> carregaController("./log.txt");
                case(6) -> printaUtilizadores();
                case(7) -> printaTransportadoras();
            }
        } while(menuPrincipal.getOpcao() != 8);
    }

    public void printaUtilizadores() {
        String[] opcoes = {"Voltar"};
        System.out.println("Os utilizadores do sistema : \n");
        Menu menuArtigosComprados = new Menu(opcoes);
        for (Map.Entry<String, Utilizador> entry : this.controller.getUtilizadores().entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
            do {
                menuArtigosComprados.executa();
            } while (menuArtigosComprados.getOpcao() != 1);
        }


    public void printaTransportadoras(){
        String[] opcoes = {"Voltar"};
        System.out.println("As transportadoras do sistema : \n");
        Menu menuArtigosComprados = new Menu(opcoes);
        for(Map.Entry<String,Transportadora> entry : this.controller.getTransportadoras().entrySet()){
            System.out.println(entry.getKey() + " : " + entry.getValue().toString());
        }
        do{
            menuArtigosComprados.executa();
        } while(menuArtigosComprados.getOpcao() != 1);
    }

    public static void main(String[] args){
        try {
            new Programa().run();
        }
        catch (IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

}

