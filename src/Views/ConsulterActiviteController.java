/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Entities.Activite;
import Utils.ConnexionBD;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;



/**
 * FXML Controller class
 *
 * @author Dorra Kerrou
 */
public class ConsulterActiviteController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private TableView<Activite> tableAct;
    
    @FXML 
    private TableColumn<Activite, String> activite;
    @FXML
    private TableColumn<Activite, String> description;
    @FXML
    private TableColumn<Activite, Date> date;
    
    public ObservableList<Activite> data = FXCollections.observableArrayList();
    @FXML
    private Button btn;
    @FXML
    private AnchorPane root;
    @FXML
    private Button modifier_btn;
    @FXML
    private ComboBox<String> club_box;
    @FXML
    private Button det_btn;
    
       public ObservableList<String> nom = FXCollections.observableArrayList();
       
        HashMap<String, Integer> map;
    @FXML
    private Button Club_btn;
    @FXML
    private Button qr;
    @FXML
    private Button weather_btn;
    @FXML
    private TextField search_txt;
    
    
    private void Afficher(){
        

        
        data.clear();
        try{
            Connection con = (Connection) ConnexionBD.getInstance().getCnx();
            String res="SELECT typeact,detailles, date FROM `activite" ;
          
            Statement statement = con.createStatement();
          
            ResultSet rs =  statement.executeQuery(res);
            while(rs.next()){
                 Activite p = new Activite();
                 p.setTypeact(rs.getString("typeact"));
                 p.setDetailles(rs.getString("detailles"));
                 p.setDate(rs.getString("date"));
                
                data.add(p);
            }
        } catch (SQLException ex) {
             Logger.getLogger(ConsulterActiviteController.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        activite.setCellValueFactory(new PropertyValueFactory<Activite,String>("typeact"));
        description.setCellValueFactory(new PropertyValueFactory<Activite,String>("detailles"));
        date.setCellValueFactory(new PropertyValueFactory<Activite,Date>("date"));
        tableAct.setItems(data);
        
        
        
        
       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Afficher();
         map = new HashMap<>();  
        LoadData();
          
        for(int i=0; i<nom.size(); i++){ 
            club_box.setValue((String)nom.get(i));
           
        }
        club_box.setItems(nom);
    }    

    @FXML
    private void redirect(ActionEvent event) throws IOException {
        if(event.getSource() == btn){
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/Views/AjoutActivite.fxml"));
            root.getChildren().setAll(pane);
        }
    }

    @FXML
    private void redirect2(ActionEvent event) throws IOException {
        
          if(event.getSource() == modifier_btn){
            AnchorPane pane = FXMLLoader.load(getClass().getResource("ModifyActivite.fxml"));
            root.getChildren().setAll(pane);
    }
    
}

    @FXML
    private void Detail(ActionEvent event) {
        data.clear();
         String nom = club_box.getSelectionModel().getSelectedItem();
           int id = map.get(nom);  
        
        try{
            Connection con = (Connection) ConnexionBD.getInstance().getCnx();
            String res="SELECT typeact,detailles, date FROM Activite WHERE club_id="+id ;
          
            Statement statement = con.createStatement();
          
            ResultSet rs =  statement.executeQuery(res);
            while(rs.next()){
                 Activite p = new Activite();
                 p.setTypeact(rs.getString("typeact"));
                 p.setDetailles(rs.getString("detailles"));
                 p.setDate(rs.getString("date"));
                
                data.add(p);
            }
        } catch (SQLException ex) {
             Logger.getLogger(ConsulterActiviteController.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        activite.setCellValueFactory(new PropertyValueFactory<Activite,String>("typeact"));
        description.setCellValueFactory(new PropertyValueFactory<Activite,String>("detailles"));
        date.setCellValueFactory(new PropertyValueFactory<Activite,Date>("date"));
        tableAct.setItems(data);
        
    }
    
    private void LoadData(){
     
         try{
            
            String res="SELECT id,Name FROM Club" ;
          Connection con = (Connection) ConnexionBD.getInstance().getCnx();
            Statement statement = con.prepareStatement(res);
            ResultSet rs =  statement.executeQuery(res);
            while(rs.next()){
                map.put( rs.getString("Name"),rs.getInt("id"));
                nom.add(rs.getString("Name"));
              
                
                
            }
            
        } catch (SQLException ex) {
             System.err.println(ex);;
         }
         
    }

    @FXML
    private void TakeMeToClub(ActionEvent event) throws IOException {
        
          {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Club/ConsulterClub.fxml"));
            root.getChildren().setAll(pane);
    }
    }

    @FXML
    private void qrCode(ActionEvent event) throws SQLException {
        
          QrCodeController q=new QrCodeController();
       
       
         if (tableAct.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setContentText("Aucun element n'est selectionné");
                alert.showAndWait();
            }
            else{
        q.ini(tableAct.getSelectionModel().getSelectedItem());
    }
    }

    @FXML
    private void TakeMeToWeather(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Weather.fxml"));
            root.getChildren().setAll(pane);
        
    }

    @FXML
    private void Search(ActionEvent event) {
         data.clear();
         String nom = search_txt.getText();
           
        try{
            Connection con = (Connection) ConnexionBD.getInstance().getCnx();
            String res="Select * from Activite where typeact Like '%"+nom+"%' "   ;
          
            Statement statement = con.createStatement();
          
            ResultSet rs =  statement.executeQuery(res);
            while(rs.next()){
                 Activite p = new Activite();
                 p.setTypeact(rs.getString("typeact"));
                 p.setDetailles(rs.getString("detailles"));
                 p.setDate(rs.getString("date"));
                
                data.add(p);
            }
        } catch (SQLException ex) {
             Logger.getLogger(ConsulterActiviteController.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        activite.setCellValueFactory(new PropertyValueFactory<Activite,String>("typeact"));
        description.setCellValueFactory(new PropertyValueFactory<Activite,String>("detailles"));
        date.setCellValueFactory(new PropertyValueFactory<Activite,Date>("date"));
        tableAct.setItems(data);
        
        
        search_txt.clear();
         
    }


}