package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.model.Usuario.Usuario;
import com.ufcg.psoft.mercadofacil.model.Usuario.UsuarioEspecial;
import com.ufcg.psoft.mercadofacil.model.Usuario.UsuarioNormal;
import com.ufcg.psoft.mercadofacil.model.Usuario.UsuarioPremium;
import com.ufcg.psoft.mercadofacil.repositories.UsuarioRepository;
import com.ufcg.psoft.mercadofacil.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AdminController {
    // JPAS Repository
    @Autowired
    UsuarioRepository usuarioRepository;

    @RequestMapping(value = "usuario/{id}/especial", method = RequestMethod.PUT)
    public ResponseEntity<?> UsuarioParaEspecial(@PathVariable("id") long id) {

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (!optionalUsuario.isPresent()) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Usuario nao encontrado"),
                    HttpStatus.NOT_FOUND);
        }

        Usuario usuario = optionalUsuario.get();

        if (usuario.toString() == "Especial") {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Usuario ja eh especial"),
                    HttpStatus.CONFLICT);
        }

        Usuario usuarioEspecial = new UsuarioEspecial(id);

        // Informacoes para usuario especial
        usuarioEspecial.setCompras(usuario.getCompras());
        usuarioEspecial.setCarrinho(usuario.getCarrinho());

        usuarioRepository.delete(usuario);
        usuarioRepository.save(usuarioEspecial);

        return new ResponseEntity<Usuario>(usuarioEspecial, HttpStatus.OK);
    }

    @RequestMapping(value = "usuario/{id}/premium", method = RequestMethod.PUT)
    public ResponseEntity<?> UsuarioParaPremium(@PathVariable("id") long id) {

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (!optionalUsuario.isPresent()) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Usuario nao encontrado"),
                    HttpStatus.NOT_FOUND);
        }

        Usuario usuario = optionalUsuario.get();

        if (usuario.toString() == "Premium") {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Usuario ja eh premium"),
                    HttpStatus.CONFLICT);
        }

        Usuario usuarioPremium = new UsuarioPremium(id);

        // Informacoes para usuario premium
        usuarioPremium.setCompras(usuario.getCompras());
        usuarioPremium.setCarrinho(usuario.getCarrinho());

        usuarioRepository.delete(usuario);
        usuarioRepository.save(usuarioPremium);

        return new ResponseEntity<Usuario>(usuarioPremium, HttpStatus.OK);
    }

    @RequestMapping(value = "usuario/{id}/normal", method = RequestMethod.PUT)
    public ResponseEntity<?> UsuarioParaNormal(@PathVariable("id") long id) {

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (!optionalUsuario.isPresent()) {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Usuario nao encontrado"),
                    HttpStatus.NOT_FOUND);
        }

        Usuario usuario = optionalUsuario.get();

        if (usuario.toString() == "Normal") {
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Usuario ja eh normal"),
                    HttpStatus.CONFLICT);
        }

        Usuario usuarioNormal = new UsuarioNormal(id);

        // Informacoes para usuario normal
        usuarioNormal.setCompras(usuario.getCompras());
        usuarioNormal.setCarrinho(usuario.getCarrinho());

        usuarioRepository.delete(usuario);
        usuarioRepository.save(usuarioNormal);

        return new ResponseEntity<Usuario>(usuarioNormal, HttpStatus.OK);
    }



}
