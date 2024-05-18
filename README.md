
# Teste de Java Orientado a Objetos

Olá! Este pequeno projeto é apenas uma resposta ao teste para a empresa SINERJI para concorrer a vaga de estágio em desenvolvimento com Java.

# Explicando um pouco do código

## Classes de Funcionários:

**Funcionario**: Classe abstrata base para todos os tipos de funcionários. Contém métodos para calcular salário, benefícios e total pago.

**Secretario**: Classe que estende Funcionario e implementa o método para calcular benefícios específicos para secretários.

**Vendedor**: Classe que estende Funcionario e implementa o método para calcular benefícios baseados em comissões de vendas.

**Gerente**: Classe que estende Funcionario e implementa o método para gerentes, que não recebem benefícios adicionais.
## Métodos da Empresa

**calcularTotalPago**: Calcula o total pago (salário + benefícios) a todos os funcionários.

**calcularTotalSalarios**: Calcula o total pago somente em salários.

**calcularTotalBeneficios**: Calcula o total pago somente em benefícios.

**funcionarioComMaiorSalario**: Retorna o funcionário com o maior salário total (incluindo benefícios).

**funcionarioComMaiorBeneficio**: Retorna o funcionário com o maior valor de benefícios.

**vendedorComMaisVendas**: Retorna o vendedor com o maior valor em vendas no mês.

**Main**: Cria uma lista de funcionários, vendedores e os registros de vendas, e chama os métodos para realizar os cálculos necessários.
