package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashSet;
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
        String sql = "UPDATE usuario set activo=?, email=?, nombre=? WHERE id=?";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            usuario.valido();

            stmt.setInt(1, usuario.isActivo() ? 1 : 0);
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getNombre());
            stmt.setInt(4, usuario.getId());


            int rows = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return usuario;
    }

    @Override
    public boolean borrar(Usuario usuario) throws SQLException {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(db_url);
            conn.setAutoCommit(false);

            String sql ="DELETE FROM mensaje WHERE from_user = ? OR to_user = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, usuario.getId());
            stmt.setInt(2, usuario.getId());

            int rows = stmt.executeUpdate();
            System.out.println(rows);

            if (rows <= 0) {
                throw new SQLException();
            }

            stmt.close();

            sql = "DELETE FROM usuario WHERE id=?";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, usuario.getId());

            rows = stmt.executeUpdate();
            System.out.println(rows);

            if (rows <= 0) {
                throw new SQLException();
            }

            stmt.close();

            System.out.println("Transaccion exitosa!!");
            conn.commit();

        } catch (Exception e) {
            System.out.println("Transaccion rollback!!");
            conn.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (conn != null) conn.close();
        }

        return true;
    }


    @Override
    public Set<Usuario> obtenerPosiblesDestinatarios(Integer id, Integer max) throws SQLException {

        Set<Usuario> listaDestinatarios = new HashSet<>();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(db_url);

            //VALIDAMOS USUARIO DE ENTRADA
            String sql = "SELECT * FROM usuario WHERE id = ?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                boolean activo = rs.getBoolean("activo");
                if (!activo) {
                    throw new SQLException("Usuario inactivo");
                }
            } else {
                throw new SQLException("No existe usuario entrada");
            }
            pstm.close();

            // OBTENEMOS LOS DESTINATARIOS POSIBLES
            sql = "SELECT * FROM usuario WHERE id <> ?";

            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, id);

            rs = pstm.executeQuery();

            int filas = 0;

            while (rs.next() && filas < max) {
                filas += 1;

                listaDestinatarios.add(new Usuario(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getDate("alta").toLocalDate(),
                        rs.getBoolean("activo")));
            }


        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return listaDestinatarios;
    }
}
