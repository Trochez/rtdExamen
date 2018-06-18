/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtdexamen;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ASUS X450L
 */
public class consultaFacturaTest {
    
    public consultaFacturaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testSetFechaInicial() {
        System.out.println("setFechaInicial");
        String fechaInicial = "2017-01-01";
        consultaFactura instance = new consultaFactura();
        boolean expResult = true;
        boolean result = instance.setFechaInicial(fechaInicial);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSetFechaInicialVacia() {
        System.out.println("setFechaInicial");
        String fechaInicial = "";
        consultaFactura instance = new consultaFactura();
        boolean expResult = false;
        boolean result = instance.setFechaInicial(fechaInicial);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSetFechaInicialMalFormato() {
        System.out.println("setFechaInicial");
        String fechaInicial = "aaa";
        consultaFactura instance = new consultaFactura();
        boolean expResult = false;
        boolean result = instance.setFechaInicial(fechaInicial);
        assertEquals(expResult, result);
    }


    @Test
    public void testSetFechaFinal() {
        System.out.println("setFechaFinal");
        String fechaFinal = "2017-02-01";
        consultaFactura instance = new consultaFactura();
        boolean expResult = true;
        boolean result = instance.setFechaFinal(fechaFinal);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSetFechaFinalVacia() {
        System.out.println("setFechaInicial");
        String fechaFinal = "";
        consultaFactura instance = new consultaFactura();
        boolean expResult = false;
        boolean result = instance.setFechaFinal(fechaFinal);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSetFechaFinalMalFormato() {
        System.out.println("setFechaInicial");
        String fechaFinal = "aaa";
        consultaFactura instance = new consultaFactura();
        boolean expResult = false;
        boolean result = instance.setFechaFinal(fechaFinal);
        assertEquals(expResult, result);
    }


    @Test
    public void testSetId() {
        System.out.println("setId");
        String id = "1a2b3c4d5e";
        consultaFactura instance = new consultaFactura();
        boolean expResult = true;
        boolean result = instance.setId(id);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSetIdVacio() {
        System.out.println("setFechaInicial");
        String id = "";
        consultaFactura instance = new consultaFactura();
        boolean expResult = false;
        boolean result = instance.setId(id);
        assertEquals(expResult, result);
    }


    @Test
    public void testSetUrl() {
        System.out.println("setUrl");
        String url = "www.resuelvetudeuda.co";
        consultaFactura instance = new consultaFactura();
        boolean expResult = true;
        boolean result = instance.setUrl(url);
        assertEquals(expResult, result);
    }
    
    public void testSetUrlVacio() {
        System.out.println("setFechaInicial");
        String url = "";
        consultaFactura instance = new consultaFactura();
        boolean expResult = false;
        boolean result = instance.setUrl(url);
        assertEquals(expResult, result);
    }


    @Test
    public void testConsultar() {
        System.out.println("consultar");
        consultaFactura instance = new consultaFactura();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        instance.setFechaInicial("2017-01-01");
        instance.setFechaFinal("2017-01-31");
        instance.url = "http://34.209.24.195/facturas?";
        instance.id = "4e25ce61-e6e2-457a-89f7-116404990967";
        String expResult = "99";
        String result = instance.consultar();
        assertEquals(expResult, result);
    }
    
}
