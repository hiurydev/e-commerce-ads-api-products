package br.senac.devweb.api.product.produto;

import br.senac.devweb.api.product.categoria.Categoria;
import br.senac.devweb.api.product.categoria.CategoriaRepresentation;
import br.senac.devweb.api.product.categoria.CategoriaService;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/produto")
@AllArgsConstructor //
public class ProdutoController {

    private ProdutoService produtoService;
    private CategoriaService categoriaService;


    @PostMapping("/")
    public ResponseEntity<ProdutoRepresentation.Detalhes> createProduto(
            @Valid @RequestBody ProdutoRepresentation.createOrUpdateProduto createOrUpdateProduto){

        Categoria categoria = this.categoriaService.getCategoria(createOrUpdateProduto.getCategoria());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProdutoRepresentation
                        .Detalhes.from(produtoService.salvar(createOrUpdateProduto, categoria)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoRepresentation.Detalhes> atualizaProduto(
            @PathVariable("id") Long id, @Valid @RequestBody ProdutoRepresentation.createOrUpdateProduto createOrUpdateProduto, Categoria categoria){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ProdutoRepresentation.Detalhes.from(this.produtoService.atualizar(id, createOrUpdateProduto, categoria)));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ProdutoRepresentation.Lista>> BuscarTodos(){

        BooleanExpression filter = QProduto.produto.status.eq(Produto.Status.ATIVO);
        return ResponseEntity.ok(ProdutoRepresentation.Lista.from(this.produtoService.buscarTodos(filter)));
    }

    @GetMapping("/{id}")
    public ResponseEntity <ProdutoRepresentation.Detalhes> buscarUm(@PathVariable("id") Long id){
        return ResponseEntity.ok(ProdutoRepresentation.Detalhes.from(this.produtoService.buscarUm(id)));
    }

    public ResponseEntity deleteProduto(@PathVariable("id") Long id){
        this.produtoService.deletaProduto(id);
        System.out.println("deletado teste");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}