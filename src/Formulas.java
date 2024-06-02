public class Formulas {
// classe das formulas usadas no calculo do preço de expediçao das transportadoras
    public static double formula1(double precoBase, double margemLucro, double imposto){
        return precoBase * margemLucro * (1 + imposto) * 0.9;
    }

    public static double formula2(double precoBase, double margemLucro, double imposto){
        return precoBase * (margemLucro + imposto);
    }

    public static double formula3(double precoBase, double margemLucro, double imposto){
        return (precoBase/imposto) * margemLucro;
    }

}
