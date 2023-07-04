package org.kamicaze.domain.repository;

import org.kamicaze.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Clientes {
    private static String INSERT = "insert into cliente (nome) values (?)";
    private static String SELECT_ALL = "select * from cliente";
    private static String UPDATE = "UPDATE cliente SET nome = ? WHERE id = ?";
    private static String DELETE = "DELETE FROM cliente WHERE id = ?";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Cliente salvar (Cliente cliente){
        jdbcTemplate.update(INSERT,new Object[]{cliente.getNome()});
        return cliente;
    }

    public void apagar (Integer id){
        jdbcTemplate.update(DELETE, new Object[]{id});
    }

    public Cliente actualizar (Cliente cliente){
        jdbcTemplate.update(UPDATE, new Object[]{
                cliente.getNome(),
                cliente.getId()
        });
        return cliente;
    }

    public List<Cliente> buscarNome(String nome){
        return jdbcTemplate.query(
                SELECT_ALL.concat(" WHERE nome LIKE ? "),
                new Object[]{"%"+nome+"%"},
                mapperTodos()
        );
    }

    public List<Cliente> getAll(){
        return jdbcTemplate.query(SELECT_ALL, mapperTodos());
    }

    private static RowMapper<Cliente> mapperTodos() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Cliente(
                        resultSet.getInt("id"),
                        resultSet.getString("nome")
                );
            }
        };
    }


}
