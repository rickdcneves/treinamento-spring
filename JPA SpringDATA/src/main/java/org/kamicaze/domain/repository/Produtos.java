package org.kamicaze.domain.repository;

import org.kamicaze.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto,Integer> {

}
