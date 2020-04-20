/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.Jardin;

import Entities.Jardin;
import IServices.CrudJardinEnfantImpl;
import Utils.ConnexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class RechercheJardinEnfantController2 implements Initializable {
    ObservableList<Jardin> list = FXCollections.observableArrayList();

    @FXML
    private TextField recherche;
    @FXML
    private TableView<Jardin> jardin;
    @FXML
    private MenuButton fwith;
    @FXML
    private MenuItem bnom;
    @FXML
    private MenuItem badresse;
    @FXML
    private MenuItem btarif;
    @FXML
    private MenuItem btel;
    @FXML
    private MenuItem betat;
    @FXML
    private MenuItem edit;
    @FXML
    private MenuItem delet;
     @FXML
    private TableColumn<Jardin, String> id;
    @FXML
    private TableColumn<Jardin, Double> tarif;
    @FXML
    private TableColumn<Jardin, String> description;
    @FXML
    private TableColumn<Jardin, String> adresse;
    @FXML
    private TableColumn<Jardin, String> numtel;
    @FXML
    private TableColumn<Jardin, String> etat;
    @FXML
    private TableColumn<Jardin, String> colnom;
     Connection connection=null;
    
    public RechercheJardinEnfantController2() {
        connection= ConnexionBD.getInstance().getCnx();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        initCol();
        try {
            loadData();
            // TODO
        } catch (SQLException ex) {
            Logger.getLogger(RechercheJardinEnfantController2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
  private void loadData() throws SQLException {
        List<Jardin> prov = new ArrayList<>();
        list.clear();
        CrudJardinEnfantImpl esi =new CrudJardinEnfantImpl();
        prov=(List<Jardin>) esi.findAll();

        list.addAll(prov);
        jardin.setItems(list);
        
    }
   private Stage getStage() {
        return (Stage) jardin.getScene().getWindow();
    }

    ///pour faire l'inisialisation des attribue 
    private void initCol() {
         id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tarif.setCellValueFactory(new PropertyValueFactory<>("Tarif"));
        description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("Adresse"));
        numtel.setCellValueFactory(new PropertyValueFactory<>("numTel"));
        etat.setCellValueFactory(new PropertyValueFactory<>("Etat"));
        colnom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        
        
        
        
    }
    @FXML
    private void fnNom(ActionEvent event) throws SQLException {
         List<Jardin> prov = new ArrayList<>();
        list.clear();
        CrudJardinEnfantImpl esi =new CrudJardinEnfantImpl();
        prov=(List<Jardin>) esi.findByNom(recherche.getText());
       list.addAll(prov);
        jardin.setItems(list);
        
    }

    @FXML
    private void fnAdresse(ActionEvent event) throws SQLException {
          List<Jardin> prov = new ArrayList<>();
        list.clear();
        CrudJardinEnfantImpl esi =new CrudJardinEnfantImpl();
        prov=(List<Jardin>) esi.findByAdresse(recherche.getText());
       list.addAll(prov);
        jardin.setItems(list);
    }

    @FXML
    private void fnTarif(ActionEvent event) throws SQLException {
          List<Jardin> prov = new ArrayList<>();
        list.clear();
        CrudJardinEnfantImpl esi =new CrudJardinEnfantImpl();
        prov=(List<Jardin>) esi.findByTarif(Double.parseDouble(recherche.getText()));
       list.addAll(prov);
        jardin.setItems(list);
    }

    @FXML
    private void fnNum(ActionEvent event) throws SQLException {
          List<Jardin> prov = new ArrayList<>();
        list.clear();
        CrudJardinEnfantImpl esi =new CrudJardinEnfantImpl();
        prov=(List<Jardin>) esi.findByNum(recherche.getText());
        list.addAll(prov);
        jardin.setItems(list);
        
        
    }

    @FXML
    private void fnEtat(ActionEvent event) throws SQLException {
        
          List<Jardin> prov = new ArrayList<>();
        list.clear();
        CrudJardinEnfantImpl esi =new CrudJardinEnfantImpl();
        prov=(List<Jardin>) esi.findByEtat(recherche.getText());
        list.addAll(prov);
        jardin.setItems(list);
    }

    @FXML
    private void handlerEdit(ActionEvent event) throws SQLException, IOException {
        
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        CrudJardinEnfantImpl esi =new CrudJardinEnfantImpl();
         Jardin selectedForEdit = jardin.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
                        alert.setTitle("error");
                        alert.setContentText("No Space selected ,Please select a Space for edit" );
                        alert.show();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/view/jardin/EditJardin.fxml"));
            Parent parent = loader.load();
            EditJardinController controller = (EditJardinController) loader.getController();
            controller.inflateUI(selectedForEdit);
           esi.delet(selectedForEdit);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("edit Space");
            stage.setScene(new Scene(parent));
            stage.show();

           
        } catch (IOException ex) {
            ex.printStackTrace();
    }
    }
    
 

    @FXML
    private void handlerDelet(ActionEvent event) throws SQLException {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        CrudJardinEnfantImpl esi =new CrudJardinEnfantImpl();
        Jardin selectedForDeletion = jardin.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
                        alert.setTitle("error");
                        alert.setContentText("No Jardin selected ,Please select a Space for edit" );
                        alert.show();
            return;
        }
        else if (selectedForDeletion != null)
            {
        alert.setTitle("Deleting jardin");
        alert.setContentText("Are you sure want to delete the Space " + selectedForDeletion.getName() + " ?");
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            int result = esi.delet(selectedForDeletion);
            System.out.println(selectedForDeletion.getId());
            if (result!=0) 
             {
                        alert.setTitle("success");
                        alert.setContentText("Space deleted "+ selectedForDeletion.getName() + " was deleted successfully.");
                        alert.show();
                        list.remove(selectedForDeletion);
            }
            else
            {
                        alert.setTitle("echec");
                        alert.setContentText("Failed "+selectedForDeletion.getName() +  " could not be deleted");
                        alert.show();
            }
        }
}
        else 
        {
            alert.setTitle("Cancelled");
                        alert.setContentText("Deletion cancelled "+ selectedForDeletion.getName() + "Deletion process cancelled");
                        alert.show();
        }
            }
    
    
    

    
}
