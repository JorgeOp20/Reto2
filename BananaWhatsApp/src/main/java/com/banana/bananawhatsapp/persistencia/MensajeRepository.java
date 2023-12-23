package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Setter;

import java.sql.*;
import java.util.List;

@Setter
public class MensajeRepository implements IMensajeRepository{

    private String db_url = null;
    @Override
    public Mensaje crear(Mensaje mensaje) throws SQLException {
        String sql = "INSERT INTO mensaje values (NULL,?,?,?,?)";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setString(1, mensaje.getCuerpo());
            stmt.setString(2, mensaje.getFecha().toString());
            stmt.setInt(3, mensaje.getRemitente().getId());
            stmt.setInt(4, mensaje.getDestinatario().getId());

            int rows = stmt.executeUpdate();

            ResultSet genKeys = stmt.getGeneratedKeys();
            if (genKeys.next()) {
                mensaje.setId(genKeys.getInt(1));
                mensaje.valido();
            } else {
                throw new SQLException("Mensaje creado erroneamente!!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }

        return mensaje;
    }

    @Override
    public List<Mensaje> obtener(Usuario usuario) throws SQLException {
        return null;
    }

    @Override
    public boolean borrarTodos(Usuario usuario) throws SQLException {
        return false;
    }
}
