package org.opendatadaykalro.is.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class DatabaseServiceTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getConnection() throws SQLException, ClassNotFoundException {
        assert DatabaseService.getInstance().getConnection() !=null;
    }
}