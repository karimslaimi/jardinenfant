/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Entities.AbonEnf;
import Entities.AdmEnf;
import IServices.EnfantService;
import Utils.ConnexionBD;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author FERID
 */
public class AdminEnfantController implements Initializable {

  
     
    @FXML
    private AnchorPane root;
    @FXML
    private TableView<AdmEnf> enfcons;
    @FXML
    private TableColumn<AdmEnf, String> nom;
    @FXML
    private TableColumn<AdmEnf, String> prenom;
    @FXML
    private TableColumn<AdmEnf, Date> date;
    @FXML
    private TableColumn<AdmEnf, String> sexe;
    @FXML
    private TableColumn<AdmEnf, String> parent;
    public ObservableList<AdmEnf> data = FXCollections.observableArrayList();
    @FXML
    private Button btn_supp;
    String id="";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try{
            Connection con = (Connection) ConnexionBD.getInstance().getCnx();
            String res="SELECT en.nom,en.prenom,en.datenaiss,en.sexe,en.id,ab.nom,ab.prenom FROM enfant en,parent AS ab WHERE en.parent_id=ab.id " ;
          
            Statement statement = con.createStatement();
          //
            ResultSet rs =  statement.executeQuery(res);
            while(rs.next()){
                 AdmEnf p = new AdmEnf();
                 p.setNom(rs.getString("nom"));
                 p.setPrenom(rs.getString("prenom"));
                  p.setDate(rs.getDate("datenaiss"));
                  p.setSexe(rs.getString("sexe"));
                  p.setJardin(rs.getString("ab.nom")+" "+rs.getString("ab.prenom"));
                  p.setId(rs.getInt("id"));
                  
            
               
                
                data.add(p);
            }
        } catch (SQLException ex) {
           
         }
        
        nom.setCellValueFactory(new PropertyValueFactory<AdmEnf,String>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<AdmEnf,String>("prenom"));
        date.setCellValueFactory(new PropertyValueFactory<AdmEnf,Date>("date"));
         sexe.setCellValueFactory(new PropertyValueFactory<AdmEnf,String>("sexe"));
         parent.setCellValueFactory(new PropertyValueFactory<AdmEnf,String>("jardin"));
        
       
        enfcons.setItems(data);
        
        enfcons.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                onEdit();
            }
        });
        
        
        
       
    }
    
     public void onEdit() {
        // check the table's selected item and get selected item
        if (enfcons.getSelectionModel().getSelectedItem() != null) {
            AdmEnf selectedOne = enfcons.getSelectionModel().getSelectedItem();
            id=Integer.toString(selectedOne.getId());
            
           
        }
    }
        

    @FXML
    private void supprimer(ActionEvent event) {
         EnfantService ens= new EnfantService();
        int mos=Integer.parseInt(id);
       int as= ens.supprimerAdm(mos);
       if (as>0){
          Alert ale= new Alert(Alert.AlertType.INFORMATION);
          ale.setTitle("INFORMATION");
          ale.setHeaderText("suppression faite !");
          ale.showAndWait();
          data.clear();
          try{
            Connection con = (Connection) ConnexionBD.getInstance().getCnx();
            String res="SELECT en.nom,en.prenom,en.datenaiss,en.sexe,en.id,ab.nom,ab.prenom FROM enfant en,parent AS ab WHERE en.parent_id=ab.id " ;
          
            Statement statement = con.createStatement();
          
            ResultSet rs =  statement.executeQuery(res);
            while(rs.next()){
                 AdmEnf p = new AdmEnf();
                 p.setNom(rs.getString("nom"));
                 p.setPrenom(rs.getString("prenom"));
                  p.setDate(rs.getDate("datenaiss"));
                  p.setSexe(rs.getString("sexe"));
                  p.setJardin(rs.getString("ab.nom")+" "+rs.getString("ab.prenom"));
                  p.setId(rs.getInt("id"));
                  
            
               
                
                data.add(p);
            }
        } catch (SQLException ex) {
           
         }
        
        nom.setCellValueFactory(new PropertyValueFactory<AdmEnf,String>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<AdmEnf,String>("prenom"));
        date.setCellValueFactory(new PropertyValueFactory<AdmEnf,Date>("date"));
         sexe.setCellValueFactory(new PropertyValueFactory<AdmEnf,String>("sexe"));
         parent.setCellValueFactory(new PropertyValueFactory<AdmEnf,String>("jardin"));
        
       
        enfcons.setItems(data);
          
      }
        
        
    }
    
    
    }    
    
