package br.senac.devweb.api.product.produto;

import br.senac.devweb.api.product.categoria.Categoria;
import br.senac.devweb.api.product.categoria.CategoriaRepresentation;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

public interface ProdutoRepresentation {

    @Data
    @Getter
    @Setter
    class createOrUpdateProduto {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotNull(message = "Nome é obrigatório")
        @Size(min = 1, max = 100, message = "Nome deve conter entre 1 e 100 caracteres")
        private String nome;

        @NotNull(message = "Descrição é obrigatória")
        @Size(min = 1, max = 250, message = "Descrição deve conter entre 1 e 250 caracteres")
        private String descricao;

        private String complemento;

        @NotNull(message = "Valor é obrigatório")
        @NotEmpty(message = "Não pode ser vazio")
        private Double valor;

        @NotNull(message = "Unidade de Medida é obrigatória")
        @NotEmpty(message = "Não pode ser vazio")
        private Produto.UnidadeMedida unidadeMedida;

        @NotNull(message = "Quantidade é obrigatória")
        private Double qtde;

        @NotNull(message = "Fabricante é obrigatório")
        @Size(min = 1, max = 255, message = "Fabricante deve ter entre 1 e 255 caracteres")
        private String fabricante;

        @NotNull(message = "Fornecedor é obrigatório")
        @Size(min = 1, max = 250, message = "Fornecedor é obrigatória")
        private String fornecedor;

        @NotNull(message = "A categoria é obrigatória")
        private Long categoria;
    }

    @Data
    @Getter
    @Setter
    @Builder
    public class Detalhes {

        private Long id;
        private String nome;
        private String descricao;
        private String complemento;
        private Double valor;
        private Produto.UnidadeMedida unidadeMedida;
        private Double qtde;
        private String fabricante;
        private String fornecedor;
        private CategoriaRepresentation.Detail categoria;


        public static Detalhes from(Produto produto){
            return Detalhes.builder()
                    .id(produto.getId())
                    .nome(produto.getNome())
                    .descricao(produto.getDescricao())
                    .complemento(produto.getComplemento())
                    .valor(produto.getValor())
                    .qtde(produto.getQtde())
                    .unidadeMedida(produto.getUnidadeMedida())
                    .fornecedor(produto.getFornecedor())
                    .fabricante(produto.getFabricante())
                    .categoria(CategoriaRepresentation.Detail.from(produto.getCategoria()))
                    .build();
        }
    }


    @Data
    @Setter
    @Getter
    @Builder
    public class Lista {
        private Long id;
        private String nome;
        private String descricao;
        private String complemento;
        private Double valor;
        private Produto.UnidadeMedida unidadeMedida;
        private Double qtde;
        private String fabricante;
        private String fornecedor;
        private CategoriaRepresentation.Detail categoria;

        private static Lista from(Produto produto){
            return Lista.builder()
                    .id(produto.getId())
                    .nome(produto.getNome())
                    .valor(produto.getValor())
                    .qtde(produto.getQtde())
                    .categoria(CategoriaRepresentation.Detail.from(produto.getCategoria()))
                    .build();
        }

        public static List<Lista> from(List<Produto> produtos){
            return produtos.stream()
                    .map(Lista::from)
                    .collect(Collectors.toList());
        }

    }
}
