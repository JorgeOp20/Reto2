package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Setter
//@Service
public class ServicioUsuarios implements IServicioUsuarios{
    //@Autowired
    private IUsuarioRepository repoUsuario;
    @Override
    public Usuario crearUsuario(Usuario usuario) throws UsuarioException {
        try {
            usuario.valido();
            repoUsuario.crear(usuario);
        } catch (SQLException e) {
          e.printStackTrace();
          throw new UsuarioException("Error en la creación: " + e.getMessage());
        }
        return usuario;
    }

    @Override
    public boolean borrarUsuario(Usuario usuario) throws UsuarioException {
        try {
            repoUsuario.borrar(usuario);
        } catch (SQLException e) {
          e.printStackTrace();
          throw new UsuarioException("Error en borrar usuario: " +e.getMessage());
        }
        return true;
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) throws UsuarioException {

        Usuario usuUpdate;

        try {
            usuario.valido();
            usuUpdate = repoUsuario.actualizar(usuario);
        } catch (SQLException e) {
            throw new UsuarioException(e.getMessage());
        }

        return usuUpdate;
    }

    @Override
    public Set <Usuario> obtenerPosiblesDesinatarios(Usuario usuario, int max) throws UsuarioException {

        Set <Usuario> listaUsuario = new HashSet<>();

        try {
           listaUsuario = repoUsuario.obtenerPosiblesDestinatarios(usuario.getId(), max);

        } catch (SQLException e) {
            throw new UsuarioException(e.getMessage());
        }
        return listaUsuario;
    }
}
