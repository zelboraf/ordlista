package com.ellara.ordlista.repository;

import com.ellara.ordlista.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

    @Query(value = "SELECT * FROM dictionary WHERE replace(swedish, '|', '') LIKE ?1%", nativeQuery = true)
    List<Dictionary> findAllSwedishStartingWith(String swedishWord);

    @Query(value = "SELECT * FROM dictionary WHERE polish LIKE ?1%", nativeQuery = true)
    List<Dictionary> findAllPolishStartingWith(String searchString);

    @Query(value = "SELECT * FROM dictionary WHERE replace(swedish, '|', '') LIKE %?1%", nativeQuery = true)
    List<Dictionary> findAllSwedishContaining(String swedishWord);

    @Query(value = "SELECT * FROM dictionary WHERE polish LIKE %?1%", nativeQuery = true)
    List<Dictionary> findAllPolishContaining(String searchString);

    @Query(value = "SELECT COUNT(*) FROM dictionary", nativeQuery = true)
    String countAll();
}