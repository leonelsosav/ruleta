package com.ibm.academia.ruletaapirest.services;

import java.util.List;

import com.ibm.academia.ruletaapirest.models.entities.Apuesta;
import com.ibm.academia.ruletaapirest.models.entities.Ruleta;

public interface RuletaDAO {

    public Ruleta save(Ruleta entidad);

    public Ruleta openRuleta(Ruleta ruleta, Long id);

    public Ruleta closeRuleta(Ruleta ruleta, Long id);

    public Ruleta findById(Long id);

    public Apuesta saveApuesta(Apuesta entidad, Long id);

    public Iterable<Apuesta> apuestasPorRuleta(Long ruletaId);

    List<Ruleta> listaRuletas();
    
}
