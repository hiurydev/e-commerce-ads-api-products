package br.senac.devweb.api.product.produto;

import br.senac.devweb.api.product.categoria.Categoria;
import br.senac.devweb.api.product.categoria.CategoriaRepresentation;
import br.senac.devweb.api.product.categoria.CategoriaService;
import br.senac.devweb.api.product.categoria.QCategoria;
import br.senac.devweb.api.product.util.Paginacao;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/produto")
@AllArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;

    @PostMapping("/")
    public ResponseEntity<ProdutoRepresentation.Detalhes> cadastrarProduto(
            @Valid @RequestBody ProdutoRepresentation.CreateOrUpdate createOrUpdate) {

        Categoria categoria = this.categoriaService.getCategoria(createOrUpdate.getCategoria());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProdutoRepresentation
                        .Detalhes.from(this.produtoService.salvar(createOrUpdate, categoria)));

    }

    @GetMapping("/")
    public ResponseEntity<Paginacao> getAll(
            @QuerydslPredicate(root = Produto.class) Predicate filtroProduto,
            @RequestParam(name = "paginaSelecionada", defaultValue = "0") Integer paginaSelecionada,
            @RequestParam(name = "tamanhoPagina", defaultValue = "2") Integer tamanhoPagina) {

        BooleanExpression filter = Objects.isNull(filtroProduto) ?
                QProduto.produto.status.eq(Produto.Status.ATIVO) :
                QProduto.produto.status.eq(Produto.Status.ATIVO).and(filtroProduto);

        Pageable pageRequest = PageRequest.of(paginaSelecionada, tamanhoPagina);

        Page<Produto> produtoList = this.produtoRepository.findAll(filter, pageRequest);

        Paginacao paginacao = Paginacao.builder()
                .conteudo(ProdutoRepresentation.Lista
                        .from(produtoList.getContent()))
                .paginaSelecionada(paginaSelecionada)
                .tamanhoPagina(tamanhoPagina)
                .build();

        return ResponseEntity.ok(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoRepresentation.Detalhes> getOneProduto(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ProdutoRepresentation.Detalhes.from(this.produtoService.getProduto(id)));
    }

    @PutMapping ("/{id}")
    public ResponseEntity<ProdutoRepresentation.Detalhes> atualizarProduto(
            @PathVariable("id") Long id,
            @Valid @RequestBody ProdutoRepresentation.CreateOrUpdate createOrUpdate) {

        Categoria categoria = this.categoriaService.getCategoria(createOrUpdate.getCategoria());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ProdutoRepresentation
                        .Detalhes.from(this.produtoService.atualizar(id, createOrUpdate, categoria)));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProdutoRepresentation.Detalhes> deletarProduto(@PathVariable("id") Long id) {
        this.produtoService.deletar(id);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

}