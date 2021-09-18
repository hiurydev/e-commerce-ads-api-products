package br.senac.devweb.api.product.categoria;

import lombok.AllArgsConstructor;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Service;
import com.querydsl.core.types.dsl.BooleanExpression;
import br.senac.devweb.api.product.exceptions.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriaService {


    private CategoriaRepository categoriaRepository;

    public Categoria salvar(CategoriaRepresentation.CreateOrUpdateCategoria createCategoria) {

        return this.categoriaRepository.save(Categoria.builder()
                .descricao(createCategoria.getDescricao())
                .status(Categoria.Status.ATIVO)
                .build());
    }

    public List<Categoria> getAllCategoria(Predicate filter) {
        return this.categoriaRepository.findAll(filter);
    }

    public void deleteCategoria(Long id) {
        Categoria categoria = this.getCategoria(id);
        categoria.setStatus(Categoria.Status.INATIVO);

        this.categoriaRepository.save(categoria);
    }

    public Categoria getCategoria(Long id) {
        BooleanExpression filter =
                QCategoria.categoria.id.eq(id)
                        .and(QCategoria.categoria.status.eq(Categoria.Status.ATIVO));

        return this.categoriaRepository.findOne(filter)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada."));
    }

    //TODO
//    public Categoria update(Long id, CategoriaRepresentation.CreateOrUpdateCategoria createOrUpdateCategoria) {
//
//    }
}
