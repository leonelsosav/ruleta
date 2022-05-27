package com.ibm.academia.ruletaapirest.controllers;

import com.ibm.academia.ruletaapirest.models.entities.Apuesta;
import com.ibm.academia.ruletaapirest.models.entities.Ruleta;
import com.ibm.academia.ruletaapirest.services.RuletaDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequestMapping("/ruleta")
public class RuletaController {

    @Autowired
    private RuletaDAO ruletaDao;

    @PostMapping(value = "/crearRuleta")
    public ResponseEntity<?> save(@Valid @RequestBody Ruleta ruleta, BindingResult result) {
        Map<String, Object> validaciones = new HashMap<String, Object>();
        if (result.hasErrors()) {
            List<String> listaErrores = result.getFieldErrors()
                    .stream()
                    .map(errores -> "Campo: '" + errores.getField() + "' " + errores.getDefaultMessage())
                    .collect(Collectors.toList());
            validaciones.put("Lista Errores", listaErrores);
            return new ResponseEntity<Map<String, Object>>(validaciones, HttpStatus.BAD_REQUEST);
        }
        Ruleta ruletaGuardada = ruletaDao.save(ruleta);

        return new ResponseEntity<Long>(ruletaGuardada.getId(), HttpStatus.CREATED);
    }

    @PutMapping("/abrirRuleta/{idRuleta}")
    public String openRuleta(@RequestBody Ruleta ruleta, @PathVariable("idRuleta") Long id)
    {
        Ruleta ruletaRegresada = ruletaDao.openRuleta(ruleta,id);
        return ruletaRegresada.getEstaAbierta() ? "Ruleta Abierta" : "Ruleta Cerrada";
    }

    @PostMapping("/apostar/{idRuleta}")
    public Apuesta saveApuesta(@PathVariable("idRuleta") Long id, @Valid @RequestBody  Apuesta apuesta )
    {
        return ruletaDao.saveApuesta(apuesta, id);
    }

    @GetMapping("/apuestas/{idRuleta}")
    public ResponseEntity<?> apuestasPorRuleta(@PathVariable ("idRuleta") Long id)
    {
        Ruleta ruletaACerrar = ruletaDao.findById(id);
        ruletaDao.closeRuleta(ruletaACerrar, id);
        List<Apuesta> apuestas = (List<Apuesta>) ruletaDao.apuestasPorRuleta(id);
        return new ResponseEntity<List<Apuesta>>(apuestas,HttpStatus.OK);
    }

    @GetMapping("/ruletas")
    public List<Ruleta> fetchRuletasList()
    {
        return ruletaDao.listaRuletas();
    }

}
