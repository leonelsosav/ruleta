package com.ibm.academia.ruletaapirest.repositories;

import com.ibm.academia.ruletaapirest.models.entities.Ruleta;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuletaRepository extends CrudRepository<Ruleta, Long>{
    
}
