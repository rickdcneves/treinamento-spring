package org.kamicaze.domain.repository;

import org.kamicaze.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface Clientes extends JpaRepository<Cliente,Integer> {
    List<Cliente> findByNomeLike(String nome);

    //ou
    @Query(value = "Select c from Cliente c where c.nome Like :nome")
    List<Cliente> encontrarNome(@Param("nome") String nome);
    boolean existsByNome(String nome);

    //busca de todos clientes com os seus pedidos
    @Query("select c from Cliente c left join fetch c.pedidos where c.id = :id")
    Cliente findClientFetchPedidos(@Param("id") Integer id);
}
