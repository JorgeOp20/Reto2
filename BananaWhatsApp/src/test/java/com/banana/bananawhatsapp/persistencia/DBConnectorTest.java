package com.banana.bananawhatsapp.persistencia;
import com.banana.bananawhatsapp.config.SpringConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class DBConnectorTest {

    @Autowired
    DBConnector dbc;

    @Test
    void connect_isOK() throws SQLException, IOException {
        dbc.connect();
        assertTrue(true);

    }

}