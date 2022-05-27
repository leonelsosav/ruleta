package com.ibm.academia.ruletaapirest.repositories;

import org.springframework.stereotype.Repository;

import com.ibm.academia.ruletaapirest.models.entities.Apuesta;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


@Repository
public interface ApuestaRepository extends CrudRepository<Apuesta, Long> {

    @Query("select a from Apuesta a join fetch a.ruleta r where r.id = ?1")
    public Iterable<Apuesta> apuestasPorRuleta(Long ruletaId);
}
