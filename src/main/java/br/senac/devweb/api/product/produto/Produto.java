package br.senac.devweb.api.product.produto;

import lombok.*;
import br.senac.devweb.api.product.categoria.Categoria;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity(name = "PRODUTO")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Nome é obrigatório")
    @Size(min = 1, max = 100, message = "Nome deve conter entre 1 e 100 caracteres")
    @Column(name = "NOME")
    private String nome;

    @NotNull(message = "Descrição é obrigatória")
    @Size(min = 1, max = 250, message = "Descrição deve conter entre 1 e 250 caracteres")
    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "COMPLEMENTO")
    private String complemento;

    @NotNull(message = "Valor é obrigatório")
    @Column(name = "VAlOR")
    private Double valor;

    @NotNull(message = "Unidade de Medida é obrigatória")
    @Column(name = "UNIDADE DE MEDIDA")
    private UnidadeMedida unidadeMedida;

    @NotNull(message = "Quantidade é obrigatória")
    @Column(name = "QTDE")
    private Double qtde;

    @NotNull(message = "Fabricante é obrigatório")
    @Size(min = 1, max = 255, message = "Fabricante deve ter entre 1 e 255 caracteres")
    @Column(name = "FABRICANTE")
    private String fabricante;

    @NotNull(message = "Fornecedor é obrigatório")
    @Size(min = 1, max = 250, message = "Fornecedor é obrigatória")
    @Column(name = "FORNECEDOR")
    private String fornecedor;

    @NotNull(message = "Status é obrigatório")
    @Column(name = "STATUS")
    private Status status;

    @NotNull(message = "A categoria é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Categoria.class)
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;

    public enum UnidadeMedida{
        UN,
        KG,
        ML,
        TN,
        MT,
        MT2,
        MT3,
        LT
    }

    public enum Status{
        INATIVO,
        ATIVO
    }
}