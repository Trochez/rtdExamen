/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtdexamen;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Juan Diego Tróchez Montoya
 */
public class RtdExamen extends Application implements Initializable{
    //Clase principal desde la que se ejecuta la aplicación
    
    //Los elementos con la etiqueta @FXML corresponden a elementos de la interfaz 
    //gráfica, el elemento pane es el contenedor principal
    
    @FXML
    Pane pane;
    
    @FXML
    RadioButton total;
    
    @FXML
    RadioButton fechas;
    
    @FXML
    ToggleGroup group;
    
    @FXML
    TextField id;
    
    @FXML
    DatePicker fechaInicial;
    
    @FXML
    DatePicker fechaFinal;
    
    @FXML
    Label resultado;
    
    @FXML
    Label resultadoConsulta;
        
    private Scene scene;
    
    private Alert alert;
    
    //Objeto mediante el que se consultan el número de facturas
    private consultaFactura cf = new consultaFactura();
    
    //Se define la base del url de consulta
    String url = "http://34.209.24.195/facturas?";
    
    //Se definie el formato fecha que requiere el servicio web
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    //Variables fecha
    String start = "";
    String finish = "";
    
    //Variable id
    String idConsulta = "";
    
    //Variables resultado
    int consulta = 0,numconsultas = 0;
    
    //variable para guardar tamaño inicial de ventana emergente
    double alertsize = 0;
    
    //Variables de testing (TDD)
    Boolean noId=false,errorOrdenFechas=false,errorUrl=false;
    
    //Bandera de testiong (Desactiva funciones gráficas para hacer testing)
    Boolean test = false;
    
    //Clase para la construcción de interfaz gráfica
    @Override
    public void start(Stage primaryStage) {
        
        try {
            
            //Se carga la interfaz gráfica desde el archivo llamada gui0fxml
            pane = FXMLLoader.load(getClass().getResource("fxml/gui.fxml"));
            
        } catch (IOException ex) {
            
            System.err.println("Error cargando GUI: "+ex);
            
        }
        
        Group root = new Group();
        
        scene = new Scene(root,600,380);
        root
                .getChildren()
                .add(
                        pane
                );
        
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }
    
    //el metodo initialize pertenece a la implementación Initializable de la clase principal 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        //Inicializa ventana emergente
        alert = new Alert(AlertType.INFORMATION);
        
        //Guarda tamaño inicial
        alertsize = alert.getDialogPane().getMinHeight();
        
        //Inicialmente los campos de selección de fecha estan desactivados
        desactiveDate();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    //Metodo para desplegar ventana emergente de instrucciones rápidas
    public void openInstruction(){
        
        alert.getDialogPane().setMinHeight(300);
        alert.setTitle("Intrucciones Rapidas");
        alert.setHeaderText("Consulta de número de facturas");
        alert.setContentText("Esta aplicación permita realizar dos tipos de consultas: \n\n"
                + " 1) Consultar las facturas totales: seleccione 2017, digite un id y presione buscar.\n\n"
                + "2) consultar facturas entre fechas: seleccione rango de fechas, digite un id, seleccione fecha inicial y fecha final"
                + "en los campos con estos nombres y presione buscar\n");
        
        alert.showAndWait();
        
    }
    
    //Metodo de consulta de facturas
    public void buscar(){
        
        //Reinicio banderas de testing
        noId=false;
        errorOrdenFechas=false;
        errorUrl = false;
        
        if(!test){
            //Notificación de espera
            alert.getDialogPane().setMinHeight(alertsize);
            alert.setTitle("Espere");
            alert.setHeaderText("Consulta de resultados");
            alert.setContentText("Presione aceptar y espere que termine la consulta");
            alert.showAndWait();
        }
        
        
        //Define el url base en el objeto consulta y verifica que no esté vació
        if(!cf.setUrl(url)){
            
            errorUrl = true;
            
            if(!test){
                alert.getDialogPane().setMinHeight(alertsize);
                alert.setTitle("Error URL");
                alert.setHeaderText("Dirección vacía");
                alert.setContentText("La dirección url está vacía");
                alert.showAndWait();
            }
            
            return;
        }
        
        if(!test){
            //Se obtiene el id digitado
            idConsulta = id.getText();
            
            //Verificación de que los campos fechas no esten vacío y transformación de
            //los mismos en cadenas de texto en el formato definido al inicio de la clase
            if(fechaInicial.getValue()!=null)start = dateFormat.format(Date.from(fechaInicial.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            else start = "";
            if(fechaFinal.getValue()!=null)finish = dateFormat.format(Date.from(fechaFinal.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            else finish = "";
            
        }
            
        //Verifica si se digito fecha inicial, de lo contrario asigna el 1 de enero del 2017 como fecha inical
        if(start.isEmpty()){
            start = "2017-01-01";
            
            //si fecha final está vacía la define como el último día del 2017
            if(finish.isEmpty())finish = "2017-12-31";
        }
        
        
        //Verifica si la fecha final está vacía, si es asi asigna la fecha actual como final
        if(finish.isEmpty()){
            Date date =  new Date();
            finish = dateFormat.format(date);
        }
        
        //verifica si el ID de busqueda está vacío, si es asi informa este hecho y aborta el proceso
        if(idConsulta.isEmpty()){
            noId = true;
            if(!test){
                alert.getDialogPane().setMinHeight(alertsize);
                alert.setTitle("ID faltante");
                alert.setHeaderText("Falta de parámetros");
                alert.setContentText("Digite un ID para realizar la consulta");
                alert.showAndWait();
            }
            
            return;
        }
                
        //Se define el id de busqueda en el objeto consulta
        cf.setId(idConsulta);
        
        Date initial = null;
        Date end = null;
        
        try {
            //Convierte las fechas en objetos Date
            initial = dateFormat.parse(start);
            end = dateFormat.parse(finish);
        } catch (ParseException ex) {
            System.err.println("Error obteniendo fechas digitadas "+ ex);
        }
        
        //Verifica que la fecha inicial no sea después de la final, si es asi lo informa y aborta la consulta
        if(initial.after(end)){
            errorOrdenFechas = true;
            
            if(!test){
                alert.getDialogPane().setMinHeight(alertsize);
                alert.setTitle("Error en fechas");
                alert.setHeaderText("Fechas incorrectas");
                alert.setContentText("La fecha final debe ser mayor a la inicial");
                alert.showAndWait();
            }
            
            return;
        }
        
        //Se crean objeto calendarpara operación entre fehcas
        Calendar calstart = Calendar.getInstance();
        Calendar calfinish = Calendar.getInstance();
        calstart.setTime(initial);
        calfinish.setTime(end);
       
        //Se obtiene la diferencia de meses entre la fecha inicial y final.
        //Se dividen las busquedas a una por mes
        int months = 0;
        int years = calfinish.get(Calendar.YEAR)-calstart.get(Calendar.YEAR);
        months = years*12-calstart.get(Calendar.MONTH)+calfinish.get(Calendar.MONTH);
        
        String result="";
        
        //variables que obtienen el número de facturas y el número de consultas respectivamente
        int cuenta = 0,consultas = 0;
        
        //bandera de consulta correcta
        boolean notNumeric = false;
        
        //intervalo de días en caso de que se encuentren más de 100 facturas
        int days = 15;
       
        int watchdog = 0;//variable para evitar bucle infinito
        
        if(months == 0){
            
            //Si las dos fechas son del mismo mes obtiene la diferencia en días
            days = calfinish.get(Calendar.DATE)-calstart.get(Calendar.DATE);
            
            int daysDiference = days;
            
            //bucle para consulta de uno o menos de un mes
            while(true){
                
                //define fecha inicial en objeto consulta
                cf.setFechaInicial(dateFormat.format(calstart.getTime()));
                            
                //define fecha final en objeto consulta
                cf.setFechaFinal(dateFormat.format(calfinish.getTime()));
                
                //obtiene resultado de cosnulta
                result = cf.consultar();
                
                //Aumenta contador de consultas
                 consultas++;
                
                //incrementa contador para vigilar bucles infinitos
                watchdog++;

               try{
                   //Si es númerico, suma el resultado a la variable de cuentas de facturas
                   cuenta += Integer.parseInt(result);
                   
                   //verifica si el resultado anterior fue numérico
                   if(notNumeric){
                       
                       //adiciona la diferencia de días a la fecha final
                       calfinish.add(Calendar.DATE, days);
                       
                       //adiciona la diferencia del ciclo anterior más uno a la fecha inicial
                       calstart.add(Calendar.DATE, daysDiference+1);
                       
                       //Obtiene nueva diferencia de días
                       days = calfinish.get(Calendar.DATE)-calstart.get(Calendar.DATE);
                       
                       //reinicia bandera de resultado numérico
                       notNumeric = false;
                   }
                   else{
                       //termina consulta
                       break;
                   }
               }
               
               //Excepción si el resultado no es numérico (más de 100 facturas)
               catch(Exception e){
                   //divide la diferencia de días en dos (divide la consulta en dos)
                   daysDiference /=2;
                   
                   //Control de operación
                   System.err.println("Error consultando facturas "+e);
                   
                   //Activa bandera de resultado no numérico
                   notNumeric = true;
                   
                   //resta la mitad de la diferencia a la variable de fecha final
                   calfinish.add(Calendar.DATE, -daysDiference);
                   
                   //recalcula diferencia de días en fechas de consulta
                   days -= daysDiference; 
               }
               
               //Si la variable de bucle infinito es mayor a 100 aborta operación y notifica
               if(watchdog>100){
                    if(!test){
                        alert.getDialogPane().setMinHeight(alertsize);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error calculando facturas");
                        alert.setContentText("El número de conexiónes supera 100 intentos");
                        alert.showAndWait();
                    }
                    
                    return;
               }
            }
           
        }
        
        else{
            
            //Variable auxiliar de fecha
            Calendar calaux = Calendar.getInstance();;
            
            //bucle de más de un mes
            while(true){
                
                 //define fecha inicial en objeto consulta
                cf.setFechaInicial(dateFormat.format(calstart.getTime()));
                
                //guarda fecha inicial en variable auxiliar
                calaux.setTime(calstart.getTime());
                
                //Si el resultado anterior no fue numérico adiciona la diferencia en días a la fecha inicial
                //(redefine rango de consulta)
                if(notNumeric){
                    calstart.add(Calendar.DATE, days);
                }
                
                //Si el resultado anterior fue numérico ubica la fecha final en el último día del mes
                else{
                    calstart.add(Calendar.MONTH, 1);
                    calstart.set(Calendar.DATE, 1);
                    calstart.add(Calendar.DATE, -1);
                }
                
                //define fecha final, normalmente es último día del mes de la fecha inicial
                cf.setFechaFinal(dateFormat.format(calstart.getTime()));
                
                //obtiene resultado de consulta
                result = cf.consultar();
                
                //Aumenta contador de consultas
                consultas++;
                
                //incrementa contador para vigilar bucles infinitos
                watchdog++;
                
                 try{
                    //Si es númerico, suma el resultado a la variable de cuentas de facturas
                    cuenta += Integer.parseInt(result);
                    
                    //verifica si el resultado anterior fue numérico
                    if(notNumeric){
                        //reinicia bandera de resultado no numérico
                        notNumeric = false;
                        
                        //adiciona un día a la fecha inicial
                        calstart.add(Calendar.DATE, 1);
                    }
                    
                    //si el resultado anterior fue numérico ubica la fehca inicial en el primer día del siguiente mes
                    else{
                        calstart.add(Calendar.MONTH, 1);
                        calstart.set(Calendar.DATE, 1);
                    }
                    
                    //Si la fecha inicial es mayor a la final termina loop de consulta 
                    if(calstart.after(calfinish) || calstart.equals(calfinish)){
                        break;
                    }

                }
               
               //Si el resultado no fue numérico, activa bandera de no numérico y 
               //reinicia valor de fecha inicial al valor anterior con la variable auxiliar
               catch(Exception e){
                   notNumeric = true;
                   calstart.setTime(calaux.getTime());
               }
                 
                 //Si la variable de bucle infinito es mayor a 400 aborta operación y notifica
               if(watchdog>400){
                    if(!test){
                        alert.getDialogPane().setMinHeight(alertsize);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error calculando facturas");
                        alert.setContentText("El número de conexiónes supera 100 intentos");
                        alert.showAndWait();
                    }
                    
                    return;
               }
               
            }
            
        }
        
        //Asigna resultado en variables resultado
        consulta = cuenta;
        numconsultas = consultas;
        
        if(!test){
            //Plasma los resultados en la interfaz gráfica
            resultado.setText(String.valueOf(cuenta));
            resultadoConsulta.setText(String.valueOf(consultas));
        }
        
    }
    
    //función para exportar resultados en un rchivo de texto
    public void guardarResultado(){ 
        
        //Obtiene resultados e ID
        String numeroFacturas = resultado.getText(),
                idCliente = id.getText(),
                numeroConsultas = resultadoConsulta.getText();
        
        //Si el número de facturas o el número de consultas o el id están vacíos notifica y aborta operación
        if(numeroFacturas.isEmpty() || idCliente.isEmpty() || numeroConsultas.isEmpty()){
            alert.getDialogPane().setMinHeight(alertsize);
            alert.setTitle("Consulta vacía");
            alert.setHeaderText("Exportar resultado");
            alert.setContentText("Debe realizar una consulta antes de exportar un resultado");
            alert.showAndWait();
            return;
        }
        
        //Abre explorador de archivos para seleccionar ruta donde exporta
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        
        //filtro de archivos formato txt
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT","*.txt"));
        
        File file = fileChooser.showSaveDialog(stage);
        
        //Se elimina extensión .txt que se coloca por defecto en linux pero no en windows
        //confirmando que la extensión sea .txt
        file = new File(file.getAbsolutePath().replace(".txt", "")+".txt");
        
        //Define cadena de texto a exportar
        String text = "ID: "+idCliente+
                "\r\nNúmero de Facturas: "+numeroFacturas+
                "\r\nNúmero de consultas: "+numeroConsultas;
        
        //imprime resultado en archivo 
        try (PrintWriter out = new PrintWriter(file.getAbsolutePath())) {
            out.println(text);
        } catch (FileNotFoundException ex) {
            //Control de excepciones
            System.err.println("Error exportando archivo de texto "+ex);
        }
    }
    
    //Metodo para cerrar aplicación
    public void cerrar(){
        System.exit(0);
        
    }
    
    //Metodo para abrir pdf de desarrollador
    public void desarrollador(){
        
        //verifica si el OS tiene escritorio
        if (Desktop.isDesktopSupported()) {
            //crea objeto File con pdf de desarrollador
            File myFile = new File("pdf/hoja-de-vida.pdf");
            
            //Abre PDF en un hilo para evitar bloqueo de aplicación
            new Thread(() -> {
                try {
                    //abre archivo
                    Desktop.getDesktop().open(myFile);
                } catch (IOException e) {
                    //Control de excepciones
                    System.err.println("Error abriendo pdf "+e);
                }
            }).start();
            
        }
    }
    
    //Metodo para activar la entrada de fechas personalizadas
    public void activeDate(){
        fechaFinal.setDisable(false);
        fechaInicial.setDisable(false);
    }
    
    //Metodo para desactivar y limpiar fechas personalizadas
    public void desactiveDate(){
        fechaFinal.setDisable(true);
        fechaInicial.setDisable(true);
        fechaInicial.setValue(null);
        fechaFinal.setValue(null);
    }
    
}
