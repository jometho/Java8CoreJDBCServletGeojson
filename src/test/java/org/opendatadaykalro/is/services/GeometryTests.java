package org.opendatadaykalro.is.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.postgis.LinearRing;

import java.util.logging.Logger;

import java.sql.SQLException;

public class GeometryTests {

    private final static Logger logger = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    private static final String mlng_str = "MULTILINESTRING ((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0))";

    private static final String mplg_str = "MULTIPOLYGON (((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)),((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)))";

    private static final String plg_str = "POLYGON ((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0))";

    private static final String lng_str = "LINESTRING  (10 10 20,20 20 20, 50 50 50, 34 34 34)";

    private static final String ptg_str = "POINT(10 10 20)";

    private static final String lr_str = "(10 10 20,34 34 34, 23 19 23 , 10 10 11)";

    @Test
    public void testLinearRing() throws SQLException {
        logger.info("void testLinearRing()");
        logger.info(lr_str);
        LinearRing lr = new LinearRing(lr_str);
        logger.info(lr.getValue());
    }

}
