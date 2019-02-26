/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iclonet.carteleriasegura.controller;

import com.iclonet.carteleriasegura.conversorpdf.BuildPPT;
import com.iclonet.carteleriasegura.conversorpdf.ConvertPPT;
import com.iclonet.carteleriasegura.entidades.Fleje;
import com.iclonet.carteleriasegura.ocr.ProcesarImagenOcr;
import com.iclonet.carteleriasegura.printer.CarteleraPrinter;
import com.iclonet.carteleriasegura.util.Ventanas;
import com.iclonet.carteleriasegura.webcam.ProcesarWebcam;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.print.PrintException;
import org.apache.poi.xslf.usermodel.XMLSlideShow;

/**
 * FXML Controller class
 *
 * @author Rodolfo Durante
 */
public class FlejeVentanaControlador extends CarteleriaControlador implements Initializable {

    static String INPUT_PPT_NAME = "template.pptx";
    static String OUTPUT_PPT_NAME = "output.pptx";
    static String OUTPUT_PNG_NAME = "output.png";
    public static String INPUT_JPG_SCAN_NAME = "test.png";
    public static String INPUT_JPG_SCAN_NAME_TYPE = "png";

    static int TEMPLATE_SLIDE_INDEX = 0;

    @FXML
    private Label lbl_sap_valor;

    @FXML
    private Label lbl_origen_valor;

    @FXML
    private Label lbl_modelo_valor;

    @FXML
    private Label lbl_precio_actual;

    @FXML
    private ImageView vistaprevia;

    @FXML
    private Label lbl_precio_antes;

    @FXML
    private Label lbl_fecha_valor;

            
    @FXML
    private Button btn_cerrar;

    @FXML
    private Button btn_nuevo;

    @FXML
    private Button btn_editar;
    /**
     * @FXML private Button btn_preliminar;
     */
    @FXML
    private Button btn_scan;

    @FXML
    private Button btn_imprimir;

    @FXML
    private Button btn_borrar;

    @FXML
    private TextArea txt_descripcion;

    @FXML
    private TableView<Fleje> tbl_flejes;

    @FXML
    private TableColumn<Fleje, String> clmn_producto;

    @FXML
    private TableColumn<Fleje, String> clmn_modelo;

    private ObservableList<Fleje> flejeData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btn_editar.setDisable(true);
        btn_borrar.setDisable(true);
        btn_imprimir.setDisable(true);
        txt_descripcion.setEditable(false);

        // Initialize the person table with the two columns.
        clmn_producto.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        clmn_modelo.setCellValueFactory(cellData -> cellData.getValue().modeloProperty());

        // Listener para detectar el cambio de seleccion
        tbl_flejes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> cargarFlejeSeleccionado(newValue));

        cargarFlejeSeleccionado(null);

        tbl_flejes.setItems(flejeData);

    }

    private void cargarFlejeSeleccionado(Fleje a) {
        if (a != null) {
            this.txt_descripcion.setText(a.getDescripcion().toUpperCase());
            this.lbl_sap_valor.setText(String.valueOf(a.getSap()));
            this.lbl_origen_valor.setText(a.getOrigen());
            this.lbl_modelo_valor.setText(a.getModelo());
            this.lbl_precio_actual.setText(String.valueOf(a.getPrecioActual()));
            this.lbl_precio_antes.setText(String.valueOf(a.getPrecioAntes()));           
            this.lbl_fecha_valor.setText(a.getFechaActual());
            
            buildPPT(a);

        } else {

            this.lbl_sap_valor.setText("");
            this.lbl_origen_valor.setText("");
            this.lbl_modelo_valor.setText("");
            this.lbl_precio_actual.setText("");
            this.lbl_precio_antes.setText("");
            this.txt_descripcion.setText("");
            this.lbl_fecha_valor.setText("");
        }
    }

    @FXML
    private void imprimirFleje(ActionEvent event) {

        CarteleraPrinter carteleraPrinter = new CarteleraPrinter();
        try {
            carteleraPrinter.imprimirPNG(OUTPUT_PNG_NAME);
        } catch (PrintException ex) {
            informarError("No se ha podido imprimir");
            Logger.getLogger(FlejeVentanaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Hago los reemplazos en base al template de entrada Genero una ppt de
     * output nueva con los reemplazos /Convierto la ppt creada en png
     */
    private void buildPPT(Fleje a) {

        /**
         * Hago los reemplazos
         *
         */
        BuildPPT buildPPT = new BuildPPT();
        XMLSlideShow ppt = buildPPT.openPpt(INPUT_PPT_NAME);
        /**
         * En éste método además de hacer los reemplazos cargo los valores
         * leídos del fleje
         */
        buildPPT.setEngineData(ppt, a);

        ppt.removeSlide(TEMPLATE_SLIDE_INDEX); // remove the template slide
        buildPPT.savePpt(ppt, OUTPUT_PPT_NAME);

        /**
         * Convierto en PNG
         */
        ConvertPPT convertPPT = new ConvertPPT();
        try {
            convertPPT.convertirAPNG(OUTPUT_PPT_NAME, OUTPUT_PNG_NAME);
        } catch (IOException ex) {
            informarError("se ha producido un error al convertir PPT a imagen");
            Logger.getLogger(FlejeVentanaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        /**
         * Una vez creada la imagen seteo en el frame
         */
        File file = new File(OUTPUT_PNG_NAME);
        System.out.println("en la imagen" + file.toURI().toString());
        Image image = new Image(file.toURI().toString());
        vistaprevia.setImage(image);
        // vistaprevia = new ImageView(image);
    }

    @FXML
    private void flejeNuevo(ActionEvent event) {
        Stage stage = (Stage) btn_cerrar.getScene().getWindow();
        Fleje flejeNuevo = new Fleje();
        super.mainApp.mostrarVentanaModal(Ventanas.FlejesEdicion, stage, flejeNuevo);
        if (flejeNuevo.getOrigen() != null) {
            flejeData.add(flejeNuevo);
            tbl_flejes.getSelectionModel().select(flejeNuevo);
            habilitarBotonesPantalla();

        }
    }

    private void habilitarBotonesPantalla() {
        btn_editar.setDisable(false);
        btn_borrar.setDisable(false);
        //   btn_preliminar.setDisable(false);
        btn_imprimir.setDisable(false);
    }

    @FXML
    private void editarFleje(ActionEvent event) {
        Stage stage = (Stage) btn_cerrar.getScene().getWindow();
        super.mainApp.mostrarVentanaModal(Ventanas.FlejesEdicion, stage, tbl_flejes.getSelectionModel().getSelectedItem());
        cargarFlejeSeleccionado(tbl_flejes.getSelectionModel().getSelectedItem());
    }

    /**
     * Proceso el dato del OCR y cargo la pantalla con los datos cargados para
     * ello a)tomo foto con la webcam,b)roto la imagen,c)leo el texto de la
     * imagen      *
     * @param event
     */
    @FXML
    private void scanFleje(ActionEvent event) {

        /**
         * Fleje flejeNuevo = new Fleje(); flejeNuevo.setDescripcion("Sillon
         * caño aplicable textileno"); flejeNuevo.setModelo("OFERTA");
         * flejeNuevo.setOrigen("CHINA"); flejeNuevo.setPrecioActual(120.5);
         * flejeNuevo.setPrecioAntes(50.6); flejeNuevo.setSap(5656);
         *
         * if (flejeNuevo.getOrigen() != null) { flejeData.add(flejeNuevo);
         * tbl_flejes.getSelectionModel().select(flejeNuevo); }
         */
        /**
         * Tomo la foto y la roto
         */
        ProcesarWebcam pw = new ProcesarWebcam();
        pw.procesarWebcam(INPUT_JPG_SCAN_NAME_TYPE, OUTPUT_PNG_NAME, 180);

        /**
         * Una vez leida y rotada la imagen procedo procesar el texto de la
         * imagen para generar un nuevo fleje con los datos leidos
         */
        ProcesarImagenOcr procesarImagenOcr = new ProcesarImagenOcr();
        Fleje flejeNuevo = procesarImagenOcr.procesarImagen(OUTPUT_PNG_NAME);

        Stage stage = (Stage) btn_cerrar.getScene().getWindow();
        // Fleje flejeNuevo = new Fleje();
        super.mainApp.mostrarVentanaModal(Ventanas.FlejesEdicion, stage, flejeNuevo);
        if (flejeNuevo.getOrigen() != null) {
            flejeData.add(flejeNuevo);
            tbl_flejes.getSelectionModel().select(flejeNuevo);
            habilitarBotonesPantalla();
        }

    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) btn_cerrar.getScene().getWindow();
        stage.close();
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void eliminarFleje() {
        int selectedIndex = tbl_flejes.getSelectionModel().getSelectedIndex();
        System.out.println(selectedIndex);
        tbl_flejes.getItems().remove(selectedIndex);
        vistaprevia.setImage(null);        
        tbl_flejes.getSelectionModel().focus(selectedIndex);
        Fleje fleje=tbl_flejes.getSelectionModel().getSelectedItem();
        cargarFlejeSeleccionado(fleje);
    }

    private void informarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Se ha producido un error.");
        alert.setContentText(mensaje);
        alert.setTitle("Information Dialog");
        alert.showAndWait();

    }

}
