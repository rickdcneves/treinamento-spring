package org.kamicaze.domain.repository;

import org.kamicaze.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Clientes {
    @Autowired
    private EntityManager entityManager;
    @Transactional
    public Cliente salvar (Cliente cliente){
        entityManager.persist(cliente);
        return cliente;
    }

    @Transactional
    public void apagar (Integer id){
        Cliente cli = entityManager.find(Cliente.class,id);
        entityManager.remove(cli);
    }

    @Transactional
    public Cliente actualizar (Cliente cliente){
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarNome(String nome){
        String jpql = "select c from Cliente c where c.nome = :nome";
        TypedQuery <Cliente> query = entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome","%"+nome+"%");
        return query.getResultList();
    }
    @Transactional( readOnly = true)
    public List<Cliente> getAll(){
        return entityManager
                .createQuery("from Cliente",Cliente.class)
                .getResultList();
    }

}
