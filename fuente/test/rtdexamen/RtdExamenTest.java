/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtdexamen;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ASUS X450L
 */
public class RtdExamenTest {
    
    public RtdExamenTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testBuscarSinId() {
        System.out.println("buscar sin id");
        RtdExamen instance = new RtdExamen();
        
        instance.test = true;
        instance.finish = "2017-12-31";
        instance.start = "2017-01-01";
        
        instance.buscar();
        
        Boolean expNoId = true;
        Boolean expErrorUrl = false;
        Boolean expErrorOrdenFechas = false;
        Boolean realNoId = instance.noId;
        Boolean realErrorOrdenFechas = instance.errorOrdenFechas;
        Boolean realErrorUrl = instance.errorUrl;
        
        assertEquals(expNoId, realNoId);
        assertEquals(expErrorUrl, realErrorUrl);
        assertEquals(expErrorOrdenFechas, realErrorOrdenFechas);
    }
    

    @Test
    public void testBuscarSinFechaInicial() {
        
        //La fecha inicial por defecto es 2017-01-01
        System.out.println("buscar sin fecha inicial");
        RtdExamen instance = new RtdExamen();
        
        instance.test = true;
        instance.finish = "2017-12-31";
        instance.idConsulta = "4e25ce61-e6e2-457a-89f7-116404990967";
        
        instance.buscar();
        
        Boolean expNoId = false;
        Boolean expErrorUrl = false;
        Boolean expErrorOrdenFechas = false;
        int expNumFacturas = 1173;
        Boolean realNoId = instance.noId;
        Boolean realErrorOrdenFechas = instance.errorOrdenFechas;
        Boolean realErrorUrl = instance.errorUrl;
        int realNumFacturas = instance.consulta;
        
        
        assertEquals(expNoId, realNoId);
        assertEquals(expErrorUrl, realErrorUrl);
        assertEquals(expErrorOrdenFechas, realErrorOrdenFechas);
        assertEquals(expNumFacturas, realNumFacturas);
        
    }
    
    @Test
    public void testBuscarSinFechaFinal() {
        
        //La fecha inicial por defecto es 2017-01-01
        System.out.println("buscar sin fecha final");
        RtdExamen instance = new RtdExamen();
        
        instance.test = true;
        instance.start = "2017-01-01";
        instance.idConsulta = "4e25ce61-e6e2-457a-89f7-116404990967";
        
        instance.buscar();
        
        Boolean expNoId = false;
        Boolean expErrorUrl = false;
        Boolean expErrorOrdenFechas = false;
        int expNumFacturas = 1173;
        Boolean realNoId = instance.noId;
        Boolean realErrorOrdenFechas = instance.errorOrdenFechas;
        Boolean realErrorUrl = instance.errorUrl;
        int realNumFacturas = instance.consulta;
        
        
        assertEquals(expNoId, realNoId);
        assertEquals(expErrorUrl, realErrorUrl);
        assertEquals(expErrorOrdenFechas, realErrorOrdenFechas);
        assertEquals(expNumFacturas, realNumFacturas);
        
    }
    
    @Test
    public void testBuscarFechaFinMenorFechaInicio() {
        
        //La fecha inicial por defecto es 2017-01-01
        System.out.println("buscar fecha final menor que fecha inicial");
        RtdExamen instance = new RtdExamen();
        
        instance.test = true;
        instance.start = "2017-01-01";
        instance.finish = "2016-12-31";
        instance.idConsulta = "4e25ce61-e6e2-457a-89f7-116404990967";
        
        instance.buscar();
        
        Boolean expNoId = false;
        Boolean expErrorUrl = false;
        Boolean expErrorOrdenFechas = true;
        Boolean realNoId = instance.noId;
        Boolean realErrorOrdenFechas = instance.errorOrdenFechas;
        Boolean realErrorUrl = instance.errorUrl;
        
        
        assertEquals(expNoId, realNoId);
        assertEquals(expErrorUrl, realErrorUrl);
        assertEquals(expErrorOrdenFechas, realErrorOrdenFechas);
        
    }
    
    @Test
    public void testBuscarEnero() {
        
        //La fecha inicial por defecto es 2017-01-01
        System.out.println("buscar enero");
        RtdExamen instance = new RtdExamen();
        
        instance.test = true;
        instance.start = "2017-01-01";
        instance.finish = "2017-01-31";
        instance.idConsulta = "4e25ce61-e6e2-457a-89f7-116404990967";
        
        instance.buscar();
        
        Boolean expNoId = false;
        Boolean expErrorUrl = false;
        Boolean expErrorOrdenFechas = false;
        int expNumFacturas = 99;
        int expNumConsultas = 1;
        Boolean realNoId = instance.noId;
        Boolean realErrorOrdenFechas = instance.errorOrdenFechas;
        Boolean realErrorUrl = instance.errorUrl;
        int realNumFacturas = instance.consulta;
        int realNumConsultas = instance.numconsultas;
        
        
        assertEquals(expNoId, realNoId);
        assertEquals(expErrorUrl, realErrorUrl);
        assertEquals(expErrorOrdenFechas, realErrorOrdenFechas);
        assertEquals(expNumFacturas, realNumFacturas);
        assertEquals(expNumConsultas, realNumConsultas);
        
    }
    
    @Test
    public void testBuscarNoUrl() {
        
        //La fecha inicial por defecto es 2017-01-01
        System.out.println("buscar no url");
        RtdExamen instance = new RtdExamen();
        
        instance.test = true;
        instance.start = "2017-01-01";
        instance.finish = "2017-01-31";
        instance.idConsulta = "4e25ce61-e6e2-457a-89f7-116404990967";
        instance.url = "";
        
        instance.buscar();
        
        Boolean expNoId = false;
        Boolean expErrorUrl = true;
        Boolean expErrorOrdenFechas = false;
        Boolean realNoId = instance.noId;
        Boolean realErrorOrdenFechas = instance.errorOrdenFechas;
        Boolean realErrorUrl = instance.errorUrl;
        
        
        assertEquals(expNoId, realNoId);
        assertEquals(expErrorUrl, realErrorUrl);
        assertEquals(expErrorOrdenFechas, realErrorOrdenFechas);
        
    }
}
