# Prova Final (Programação orientada a objetos)

Faça um programa em Java para manter um cadastro de produtos e suas
respectivas vendas. O aplicativo deve apresentar também alguns relatórios
de controle para o usuário. Algumas regras sobre o software:
- Um produto deve ter os seguintes atributos: código, nome, valor e
quantidade em estoque.
- Uma venda deve ter os seguintes atributos: data da venda, o produto
vendido e a quantidade vendida.
- As vendas são feitas sempre para um único produto.
- Para realizar a venda, sempre verifique se o produto a ser vendido existe
em estoque.
A estrutura mínima de menus/funcionalidades que o aplicativo deve ter é a
seguinte:

1 – Incluir produto<br>
2 – Consultar produto<br>
3 – Listagem de produtos<br>
4 – Vendas por período – detalhado<br>
5 – Realizar venda<br>

# Implementações 

```
- Produto:
   ┗ Adicionar produto   [✔️]
   ┗ Remover produto     [✔️]
   ┗ Editar produto      [✔️]
   ┗ Listagem de produto [✔️]
   
- Vendas:
   ┗ Iniciar venda         [✔️]
   ┗ Apagar venda          [✔️]
   ┗ Ver registro da venda [✔️]
   ┗ Listagem de vendas    [✔️]
     ┗ Listagem de vendas de todo período [✔️]
     ┗ Listagem de vendas entre 2 datas   [✔️]
     
- Relatórios:
   ┗ Relatório geral:
      ┗ 1. Todos os produtos listados   [✔️]
      ┗ 2. Todos as vendas feitas       [✔️]
      ┗ 3. Todos as mudanças no estoque [✔️]
     
- Interface:
   ┗ Lista de produtos     [✔️]
   ┗ Histórico de vendas   [✔️]
   ┗ Relatórios formatados [✔️]
     
- Outros:
   ┗ Amostra de produtos pré-feitos [✔️]
```

# Guia das classes:

| Atalho        | Função                                          |
|---------------|-------------------------------------------------|
| [Launcher.java](https://github.com/daviddev16/Prova-Final/blob/master/src/org/david/Launcher.java) | Classe aonde o programa é iniciado.             |
| [MainUI.java](https://github.com/daviddev16/Prova-Final/blob/master/src/org/david/view/MainUI.java)   | Classe da interface do usuário.                 |
| [model](https://github.com/daviddev16/Prova-Final/tree/master/src/org/david/model) | Pacote aonde contém a maior parte da implementação do sistema. |
| [controller](https://github.com/daviddev16/Prova-Final/tree/master/src/org/david/controller) | Pacote aonde contém os gerenciadores do sistema. |
| [view](https://github.com/daviddev16/Prova-Final/tree/master/src/org/david/view) | Pacote aonde contém a implementação da interface. |


# Link para download

[Link](https://github.com/daviddev16/Prova-Final/tree/master/builds) para baixar o software _(prova-final-1.1.jar)_

<h4>Obrigado pela atenção :)</h4>
