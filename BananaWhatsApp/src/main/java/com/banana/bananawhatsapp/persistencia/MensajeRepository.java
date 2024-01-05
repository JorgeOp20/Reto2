package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Setter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Setter
public class MensajeRepository implements IMensajeRepository {

    private String db_url = null;

    @Override
    public Mensaje crear(Mensaje mensaje) throws SQLException {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(db_url);
            conn.setAutoCommit(false);

            // VALIDAMOS EL USUARIO REMITENTE
            String sql = "SELECT * FROM usuario WHERE id = ?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, mensaje.getRemitente().getId());

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                boolean activo = rs.getBoolean("activo");
                if (!activo) {
                    throw new UsuarioException("Usuario remitente inactivo");
                }
            } else {
                throw new UsuarioException("No existe usuario remitente");
            }
            pstm.close();

            // VALIDAMOS EL USUARIO DESTINATARIO
            sql = "SELECT * FROM usuario WHERE id = ?";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, mensaje.getDestinatario().getId());

            rs = pstm.executeQuery();

            if (rs.next()) {
                boolean activo = rs.getBoolean("activo");
                if (!activo) {
                    throw new UsuarioException("Usuario destinatario inactivo");
                }
            } else {
                throw new UsuarioException("No existe usuario destinatario");
            }
            pstm.close();

            //UNA VEZ VALIDADOS LOS USUARIOS, INSERTAMOS EL MENSAJE
            sql = "INSERT INTO mensaje values (NULL,?,?,?,?)";
            pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, mensaje.getCuerpo());
            pstm.setString(2, mensaje.getFecha().toString());
            pstm.setInt(3, mensaje.getRemitente().getId());
            pstm.setInt(4, mensaje.getDestinatario().getId());

            mensaje.valido();

            int rows = pstm.executeUpdate();

            ResultSet genKeys = pstm.getGeneratedKeys();
            if (genKeys.next()) {
                mensaje.setId(genKeys.getInt(1));
                mensaje.valido();
            } else {
                throw new SQLException("Mensaje creado erroneamente!!!");
            }

            pstm.close();

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


        return mensaje;
    }


    @Override
    public List<Mensaje> obtener(Usuario usuario) throws SQLException {

        List <Mensaje> listMensa = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(db_url);
          //  conn.setAutoCommit(false);

            // VALIDAMOS EL USUARIO ENTRADA
            String sql = "SELECT * FROM usuario WHERE id = ?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, usuario.getId());

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                boolean activo = rs.getBoolean("activo");
                if (!activo) {
                    throw new UsuarioException("Usuario inactivo");
                }
            } else {
                throw new UsuarioException("No existe usuario entrada");
            }
            pstm.close();

            // VALIDAMOS EL USUARIO DESTINATARIO
            sql = "SELECT * FROM mensaje WHERE from_user = ? OR to_user = ?";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, usuario.getId());
            pstm.setInt( 2, usuario.getId());

            rs = pstm.executeQuery();

            while (rs.next()) {
                Usuario usuRemi = new Usuario();
                usuRemi.setId(rs.getInt("from_user"));

                Usuario usuDest = new Usuario();
                usuDest.setId(rs.getInt("to_user"));

                listMensa.add(new Mensaje(rs.getInt("id"), usuRemi, usuDest,
                        rs.getString("cuerpo"), rs.getDate("fecha").toLocalDate()));
            }

            pstm.close();

            //System.out.println("Transaccion exitosa!!");
           // conn.commit();

        } catch (Exception e) {
           // System.out.println("Transaccion rollback!!");
           // conn.rollback();
            e.printStackTrace();
            System.out.println("Ha habido un error: " + e.getMessage());
            throw e;
        } finally {
            if (conn != null) conn.close();
        }

        return listMensa;

    }

    @Override
    public boolean borrarTodos(Usuario remitente, Usuario destinatario) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(db_url);

            // VALIDAMOS USUARIO REMITENTE ENTRADA
            String sql = "SELECT * FROM usuario WHERE id = ?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, remitente.getId());

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                boolean activo = rs.getBoolean("activo");
                if (!activo) {
                    throw new UsuarioException("Usuario remitente inactivo");
                }
            } else {
                throw new UsuarioException("No existe usuario remitente de entrada");
            }
            pstm.close();

            // VALIDAMOS USUARIO DESTINATARIO ENTRADA
            sql = "SELECT * FROM usuario WHERE id = ?";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, remitente.getId());

            rs = pstm.executeQuery();

            if (rs.next()) {
                boolean activo = rs.getBoolean("activo");
                if (!activo) {
                    throw new UsuarioException("Usuario destinatario inactivo");
                }
            } else {
                throw new UsuarioException("No existe usuario destinatario de entrada");
            }
            pstm.close();

            // BORRAMOS MENSAJES ENTRE LOS DOS USUARIOS
            sql = "DELETE FROM mensaje WHERE from_user IN (?,?) AND to_user IN (?,?)";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, remitente.getId());
            pstm.setInt( 2, destinatario.getId());
            pstm.setInt(3, remitente.getId());
            pstm.setInt( 4, destinatario.getId());


            //rs = pstm.executeQuery();
            int rows = pstm.executeUpdate();

            if (rows <= 0) {
                throw new SQLException();
            }

            pstm.close();


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ha habido un error: " + e.getMessage());
            throw e;
        } finally {
            if (conn != null) conn.close();
        }

        return true;
    }
}
