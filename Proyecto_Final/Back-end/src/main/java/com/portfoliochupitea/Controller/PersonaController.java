package com.portfoliochupitea.Controller;

import com.portfoliochupitea.Dto.dtoPersona;
import com.portfoliochupitea.Entity.Persona;
import com.portfoliochupitea.Security.Controller.Mensaje;
import com.portfoliochupitea.Service.ImpPersonaService;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/personas")
@CrossOrigin(origins = {"https://frontendportafoliochupitea.web.app","http://localhost:4200"})
public class PersonaController {
    @Autowired
    ImpPersonaService personaService;
    
    @GetMapping("/lista")
    public ResponseEntity<List<Persona>> list(){
        List<Persona> list = personaService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    
    @GetMapping("/detail/{id}")
    public ResponseEntity<Persona> getById(@PathVariable("id") int id){
        if(!personaService.existsById(id)){
            return new ResponseEntity(new Mensaje("No existe el id"),HttpStatus.BAD_REQUEST);
        }
        Persona persona = personaService.getOne(id).get();
        return new ResponseEntity(persona, HttpStatus.OK);
    }
    
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> delete(@PathVariable("id") int id){
//        if(!personaService.existsById(id)){
//            return new ResponseEntity(new Mensaje("No existe el id"),HttpStatus.NOT_FOUND);
//        }
//        personaService.delete(id);
//        return new ResponseEntity(new Mensaje("Educacion elimanda"),HttpStatus.OK);
//    }
    
//    @PostMapping("/create")
//    public ResponseEntity<?> create(@RequestBody dtoEducacion dtoeducacion){
//        if(StringUtils.isBlank(dtoeducacion.getNombreE())){
//            return new ResponseEntity(new Mensaje("El nombre es obligatorio"),HttpStatus.BAD_REQUEST);
//        }
//        if(sEducacion.existsByNombreE(dtoeducacion.getNombreE())){
//            return new ResponseEntity(new Mensaje("Ese nombre ya existe"),HttpStatus.BAD_REQUEST);
//        }
//        
//        Educacion educacion = new Educacion(
//                dtoeducacion.getNombreE(), dtoeducacion.getDescripcionE()
//        );
//        sEducacion.save(educacion);
//        return new ResponseEntity(new Mensaje("Educacion creada"),HttpStatus.OK);
//    }
//    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody dtoPersona dtopersona){
        if(!personaService.existsById(id)){
            return new ResponseEntity(new Mensaje("No existe el id"),HttpStatus.NOT_FOUND);
        }
        if(personaService.existsByNombre(dtopersona.getNombre()) && personaService.getByNombre(dtopersona.getNombre()).get().getId() != id){
            return new ResponseEntity(new Mensaje("Ese nombre ya existe"),HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(dtopersona.getNombre())){
            return new ResponseEntity(new Mensaje("El campo no puede estar vacío"),HttpStatus.BAD_REQUEST);
        }
        
        Persona persona = personaService.getOne(id).get();
        
        persona.setNombre(dtopersona.getNombre());
        persona.setApellido(dtopersona.getApellido());
        persona.setSubtitulo(dtopersona.getSubtitulo());
        persona.setDescripcion(dtopersona.getDescripcion());
        persona.setImg(dtopersona.getImg());
        persona.setTelefono(dtopersona.getTelefono());
        persona.setUbicacion(dtopersona.getUbicacion());
        
        personaService.save(persona);
        
        return new ResponseEntity(new Mensaje("Persona actualizada"),HttpStatus.OK);
    }

}