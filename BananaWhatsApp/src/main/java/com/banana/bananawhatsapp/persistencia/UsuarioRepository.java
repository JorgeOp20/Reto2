package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Set;

//@Repository
@Setter
public class UsuarioRepository implements IUsuarioRepository {

    //@Value("${db_url}")
    private String db_url = null;

    @Override
    public Usuario crear(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario values (NULL,?,?,?,?)";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            //stmt.setBoolean(1, usuario.isActivo());
            usuario.valido();

            stmt.setInt(1, usuario.isActivo() ? 1 : 0);
            stmt.setString(2, usuario.getAlta().toString());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getNombre());

            int rows = stmt.executeUpdate();

            ResultSet genKeys = stmt.getGeneratedKeys();
            if (genKeys.next()) {
                usuario.setId(genKeys.getInt(1));

            } else {
                throw new SQLException("Usuario creado erroneamente!!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }

        return usuario;
    }

    @Override
    public Usuario actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario set activo=?, alta=?, email=?, nombre=? WHERE id=?";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, usuario.isActivo() ? 1 : 0);
            stmt.setString(2, usuario.getAlta().toString());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getNombre());
            stmt.setInt(5, usuario.getId());

            int rows = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return usuario;
    }

    @Override
    public boolean borrar(Usuario usuario) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id=?";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, usuario.getId());

            int rows = stmt.executeUpdate();
            System.out.println(rows);

            if(rows<=0){
                throw new SQLException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return true;
    }


    @Override
    public Set<Usuario> obtenerPosiblesDestinatarios(Integer id, Integer max) throws SQLException {
        return null;
    }
}
