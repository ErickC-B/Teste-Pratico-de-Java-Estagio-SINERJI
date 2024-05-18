package testecódigo;

import java.util.*;

abstract class Funcionario {
    String nome;
    String cargo;
    int anoContratacao;
    int mesContratacao;
    double salarioBase;
    double beneficioAnual;

    Funcionario(String nome, String cargo, int anoContratacao, int mesContratacao, double salarioBase, double beneficioAnual) {
        this.nome = nome;
        this.cargo = cargo;
        this.anoContratacao = anoContratacao;
        this.mesContratacao = mesContratacao;
        this.salarioBase = salarioBase;
        this.beneficioAnual = beneficioAnual;
    }

    public double calcularSalario(int mes, int ano) {
        int anosDeServico = ano - this.anoContratacao;
        if (ano == this.anoContratacao && mes < this.mesContratacao) {
            anosDeServico = 0;
        }
        return this.salarioBase + (anosDeServico * this.beneficioAnual);
    }

    public abstract double calcularBeneficio(int mes, int ano);

    public double calcularTotalPago(int mes, int ano) {
        return calcularSalario(mes, ano) + calcularBeneficio(mes, ano);
    }

    public String getNome() {
        return nome;
    }
}

class Secretario extends Funcionario {
    private static final double PERCENTUAL_BENEFICIO = 0.20;

    Secretario(String nome, int anoContratacao, int mesContratacao) {
        super(nome, "Secretário", anoContratacao, mesContratacao, 7000, 1000);
    }

    @Override
    public double calcularBeneficio(int mes, int ano) {
        return calcularSalario(mes, ano) * PERCENTUAL_BENEFICIO;
    }
}

class Vendedor extends Funcionario {
    private static final double PERCENTUAL_COMISSAO = 0.30;
    private Map<String, Double> vendas;

    Vendedor(String nome, int anoContratacao, int mesContratacao, Map<String, Double> vendas) {
        super(nome, "Vendedor", anoContratacao, mesContratacao, 12000, 1800);
        this.vendas = vendas;
    }

    @Override
    public double calcularBeneficio(int mes, int ano) {
        String key = String.format("%02d/%04d", mes, ano);
        return vendas.getOrDefault(key, 0.0) * PERCENTUAL_COMISSAO;
    }

    public double getVendasMes(int mes, int ano) {
        String key = String.format("%02d/%04d", mes, ano);
        return vendas.getOrDefault(key, 0.0);
    }
}

class Gerente extends Funcionario {

    Gerente(String nome, int anoContratacao, int mesContratacao) {
        super(nome, "Gerente", anoContratacao, mesContratacao, 20000, 3000);
    }

    @Override
    public double calcularBeneficio(int mes, int ano) {
        return 0;
    }
}

public class CódigoTeste {
    
    public static double calcularTotalPago(List<Funcionario> funcionarios, int mes, int ano) {
        return funcionarios.stream().mapToDouble(f -> f.calcularTotalPago(mes, ano)).sum();
    }

    public static double calcularTotalSalarios(List<Funcionario> funcionarios, int mes, int ano) {
        return funcionarios.stream().mapToDouble(f -> f.calcularSalario(mes, ano)).sum();
    }

    public static double calcularTotalBeneficios(List<Funcionario> funcionarios, int mes, int ano) {
        return funcionarios.stream().mapToDouble(f -> f.calcularBeneficio(mes, ano)).sum();
    }

    public static Funcionario funcionarioComMaiorSalario(List<Funcionario> funcionarios, int mes, int ano) {
        return funcionarios.stream().max(Comparator.comparingDouble(f -> f.calcularTotalPago(mes, ano))).orElse(null);
    }

    public static Funcionario funcionarioComMaiorBeneficio(List<Funcionario> funcionarios, int mes, int ano) {
        return funcionarios.stream().filter(f -> f.calcularBeneficio(mes, ano) > 0).max(Comparator.comparingDouble(f -> f.calcularBeneficio(mes, ano))).orElse(null);
    }

    public static Vendedor vendedorComMaisVendas(List<Vendedor> vendedores, int mes, int ano) {
        return vendedores.stream().max(Comparator.comparingDouble(v -> v.getVendasMes(mes, ano))).orElse(null);
    }

    public static void main(String[] args) {
        Map<String, Double> vendasAna = new HashMap<>();
        vendasAna.put("12/2021", 5200.0);
        vendasAna.put("01/2022", 4000.0);
        vendasAna.put("02/2022", 4200.0);
        vendasAna.put("03/2022", 5850.0);
        vendasAna.put("04/2022", 7000.0);

        Map<String, Double> vendasJoao = new HashMap<>();
        vendasJoao.put("12/2021", 3400.0);
        vendasJoao.put("01/2022", 7700.0);
        vendasJoao.put("02/2022", 5000.0);
        vendasJoao.put("03/2022", 5900.0);
        vendasJoao.put("04/2022", 6500.0);

        List<Funcionario> funcionarios = Arrays.asList(
                new Secretario("Jorge Carvalho", 2018, 1),
                new Secretario("Maria Souza", 2015, 12),
                new Vendedor("Ana Silva", 2021, 12, vendasAna),
                new Vendedor("João Mendes", 2021, 12, vendasJoao),
                new Gerente("Juliana Alves", 2017, 7),
                new Gerente("Bento Albino", 2014, 3)
        );

        List<Funcionario> funcionariosComBeneficio = Arrays.asList(
                new Secretario("Jorge Carvalho", 2018, 1),
                new Secretario("Maria Souza", 2015, 12),
                new Vendedor("Ana Silva", 2021, 12, vendasAna),
                new Vendedor("João Mendes", 2021, 12, vendasJoao)
        );

        List<Vendedor> vendedores = Arrays.asList(
                new Vendedor("Ana Silva", 2021, 12, vendasAna),
                new Vendedor("João Mendes", 2021, 12, vendasJoao)
        );

        int mes = 2;
        int ano = 2022;

        System.out.println("Total pago no mês: " + calcularTotalPago(funcionarios, mes, ano));
        System.out.println("Total de salários no mês: " + calcularTotalSalarios(funcionarios, mes, ano));
        System.out.println("Total de benefícios no mês: " + calcularTotalBeneficios(funcionariosComBeneficio, mes, ano));

        Funcionario funcionarioMaiorSalario = funcionarioComMaiorSalario(funcionarios, mes, ano);
        System.out.println("Funcionário com maior salário: " + (funcionarioMaiorSalario != null ? funcionarioMaiorSalario.getNome() : "Nenhum"));

        Funcionario funcionarioMaiorBeneficio = funcionarioComMaiorBeneficio(funcionariosComBeneficio, mes, ano);
        System.out.println("Funcionário com maior benefício: " + (funcionarioMaiorBeneficio != null ? funcionarioMaiorBeneficio.getNome() : "Nenhum"));

        Vendedor vendedorMaisVendas = vendedorComMaisVendas(vendedores, mes, ano);
        System.out.println("Vendedor com mais vendas: " + (vendedorMaisVendas != null ? vendedorMaisVendas.getNome() : "Nenhum"));
    }
}

