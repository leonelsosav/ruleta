package com.ibm.academia.ruletaapirest.services;

import java.util.List;

import javax.transaction.Transactional;

import com.ibm.academia.ruletaapirest.exceptions.BadRequestException;
import com.ibm.academia.ruletaapirest.exceptions.NotFoundException;
import com.ibm.academia.ruletaapirest.models.entities.Apuesta;
import com.ibm.academia.ruletaapirest.models.entities.Ruleta;
import com.ibm.academia.ruletaapirest.repositories.ApuestaRepository;
import com.ibm.academia.ruletaapirest.repositories.RuletaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuletaDAOImpl implements RuletaDAO{

    @Autowired(required = true)
    RuletaRepository ruletaRepository;

    @Autowired
    ApuestaRepository apuestaRepository;

    @Override
    @Transactional
    public Ruleta save(Ruleta entidad) {
        return ruletaRepository.save(entidad);
    }

    @Override
    public Ruleta openRuleta(Ruleta ruleta, Long id) {
        Ruleta ruletaToOpen = ruletaRepository.findById(id).orElseThrow(()-> new NotFoundException("La ruleta con ID %d no existe", id) );
        if(ruletaToOpen.getEstaAbierta())
        {
            throw new BadRequestException("La ruleta ya se encuentra abierta");
        }
        ruletaToOpen.setEstaAbierta(true);
        return ruletaRepository.save(ruletaToOpen);
    }

    @Override
    @Transactional
    public Apuesta saveApuesta(Apuesta entidad, Long id) {
        Ruleta ruletaToSaveApuesta = ruletaRepository.findById(id).orElseThrow(()-> new NotFoundException("La ruleta con ID %d no existe", id) );

        if(entidad.getValorApuesta() == null || entidad.getValorApuesta() < 0)
        {
            throw new BadRequestException("El valor de la apuesta debe ser mayor a 0");
        }
        if(!ruletaToSaveApuesta.getEstaAbierta())
        {
            throw new BadRequestException("La ruleta está cerrada");
        }

        entidad.setRuleta(ruletaToSaveApuesta);
        return apuestaRepository.save(entidad);
    }

    @Override
    @Transactional
    public Iterable<Apuesta> apuestasPorRuleta(Long ruletaId) {
        return apuestaRepository.apuestasPorRuleta(ruletaId);
    }

    @Override
    public List<Ruleta> listaRuletas() {
        return (List<Ruleta>) ruletaRepository.findAll();
    }

    @Override
    public Ruleta closeRuleta(Ruleta ruleta, Long id) {
        Ruleta ruletaToClose = ruletaRepository.findById(id).orElseThrow(()-> new NotFoundException("La ruleta con ID %d no existe", id) );
        if(!ruletaToClose.getEstaAbierta())
        {
            throw new BadRequestException("La ruleta ya se encuentra cerrada");
        }
        ruletaToClose.setEstaAbierta(false);
        return ruletaRepository.save(ruletaToClose);
    }

    @Override
    public Ruleta findById(Long id) {
        return ruletaRepository.findById(id).orElseThrow(()-> new NotFoundException("La ruleta con ID %d no existe", id) );
    }
    
}
