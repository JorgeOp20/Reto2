package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

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
        return null;
    }

    @Override
    public Usuario obtenerPosiblesDesinatarios(Usuario usuario, int max) throws UsuarioException {
        return null;
    }
}
