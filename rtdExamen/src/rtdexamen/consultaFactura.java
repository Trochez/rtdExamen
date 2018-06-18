/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtdexamen;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Juan Diego Tróchez Montoya
 */
public class consultaFactura {
    
    //variables de objeto de consulta
    private String url = "";
    private Date fechaInicial = null;
    private Date fechaFinal = null;
    private String id = "";
    
    //Formato fecha de consulta
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private final String USER_AGENT = "Mozilla/5.0";
   
    //Metodo para definir fecha inicial de objeto consulta
    public boolean  setFechaInicial(String fechaInicial){
        
        //Verifica que la fecha introducida no este vacía
        if(fechaInicial.isEmpty())return false;
        else{
            try {
                //Define fecha inicial con valor recibido
                this.fechaInicial = df.parse(fechaInicial);
                 return true;
            } catch (ParseException ex) {
                //Control de excepciones
                System.err.println("Error en formato de fecha inicial "+ ex);
                return false;
            }
           
        }
    }
    
     //Metodo para definir fecha final de objeto consulta
    public boolean setFechaFinal(String fechaFinal){
        
        //Verifica que la fecha introducida no este vacía
        if(fechaFinal.isEmpty())return false;
        else{
            try {
                 //Define fecha final con valor recibido
                this.fechaFinal = df.parse(fechaFinal);
                 return true;
            } catch (ParseException ex) {
                //Control de excepciones
                System.err.println("Error en formato de fecha final "+ ex);
                return false;
            }
           
        }
    }
    
     //Metodo para definir ID de objeto consulta
    public boolean setId(String id){
        //Verifica que el id introducido no este vacía
        if(id.isEmpty())return false;
        else{
            //Define id con valor recibido
            this.id = id;
            return true;
        }
    }
    
    //Metodo para definir URL de objeto consulta
    public boolean setUrl(String url){
         //Verifica que el url introducido no este vacía
        if(url.isEmpty())return false;
        else{
            //Define url con valor recibido
            this.url = url;
            return true;
        }
    }
    
    //Metodo para consultar en el servicio web con las variables especificadas
    public String consultar(){
        
         URL obj = null;
         HttpURLConnection con = null;
        
         //Define url de consulta con parámetros
        String urlparameters = url+"id="+
                id+"&start="+
                df.format(fechaInicial)+"&finish="+
                df.format(fechaFinal);
                                
        try {
            
            //Crea objeto url
            obj = new URL(urlparameters);
            
        } catch (MalformedURLException ex) {
            //Control de excepciones
            System.err.println("Error creando url "+ ex);
            
        }
        
        try {
            //Se conecta a url especificado
            con = (HttpURLConnection) obj.openConnection();
        } catch (IOException ex) {
            //Control de excepciones
            System.err.println("Error abriendo conexión "+ ex);
        }

        //Adiciona header
        con.setRequestProperty("User-Agent", USER_AGENT);
        
        //Variable de código de respuesta
        int responseCode = 0;
        
        try {
            //Obtiene código de respuesta
            responseCode = con.getResponseCode();
        } catch (IOException ex) {
            //Control de excepciones
            System.err.println("Error obteniendo respuesta "+ex);
        }
        
        //Variable para obtener respuesta de servicio
        BufferedReader in = null;
        try {
            //Obtiene respuesta
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
        } catch (IOException ex) {
             //Control de excepciones
            System.err.println("Error obteniendo respuesta"+ ex);
        }
        
        //Variable para obtener respuesta parcialmente
        String inputLine;
        
        //Variable en la que se plasma la respuesta final
        StringBuffer response = new StringBuffer();

        try {
            //Lee cada línea de la respuesta
            while ((inputLine = in.readLine()) != null) {
                //Concatena cada linea en variable respuesta final
                response.append(inputLine);
            }
        } catch (IOException ex) {
           //Control de excepciones
           System.err.println("Error leyendo respuesta"+ ex);
        }
        
        try {
            //Cierra conexión
            in.close();
        } catch (IOException ex) {
            //Control de excepciones
           System.err.println("Error cerrando conexión"+ ex);
        }
        
        //Retorna respuesta
        return response.toString();
        
    }
    
}
