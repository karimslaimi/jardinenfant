/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.Categorie;
import Utils.ConnexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jdk.nashorn.internal.runtime.Debug.id;

/**
 *
 * @author Emna
 */
public class CategorieService {
    
      private Connection cnx;
   
   public CategorieService()
   {
       cnx=ConnexionBD.getInstance().getCnx();
   }
   
    
    
    public void ajouter(Categorie c,int id){
      
          try{
             
            String req="Insert into Categorie(evenement_id,libelle) values ("+id+",'"+c.getLibelle()+"')";
            Statement statement=cnx.createStatement();
            statement.executeUpdate(req);
            System.out.println("Ajout réussi!");   
            
        }catch(SQLException ex){
            System.out.println(ex);
        }
    
    }

 /**   
   public void ModifierCategorie(int id_C,String libelle_C){
            
         try{
            Connection cnx = (Connection) ConnexionBD.getInstance().getCnx();

             String query="Update Categorie SET titre='"+libelle_C                 
             +"' WHERE id="+id_C;
           Statement st=cnx.createStatement();
             st=cnx.createStatement();
             st.executeUpdate(query);
             System.out.println("Libelle modifié!");
           
            
          }catch(SQLException e){
              
            System.out.println(e);
        }    
              
     }   */

            public void supprimerCategorie(int id)
   {
       try {

           String req="Delete from Categorie where id="+id;
           Statement st=cnx.createStatement();
           st.executeUpdate(req);
           System.out.println("Suppression réussie!");
       } catch (SQLException ex) {
           Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
     
          
     public List<Categorie> afficherEvenementCategorie(int id){
           List<Categorie> lc=new ArrayList<Categorie>();
           String req="Select * from Categorie where evenement_id="+id;
       try {
           Connection cnx = (Connection) ConnexionBD.getInstance().getCnx();

           Statement statement= cnx.createStatement();
           ResultSet rs=statement.executeQuery(req);
           while (rs.next())
           {Categorie c=new Categorie(rs.getString(2));
           c.setId(rs.getInt(1));
           lc.add(c);
           }
       } catch (SQLException ex) {
           Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
       }
       return lc;
   }       
    
    
    
    



}

