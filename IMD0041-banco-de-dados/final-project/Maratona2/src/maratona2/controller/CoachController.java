/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maratona2.controller;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import maratona2.domain.Coach;
import maratona2.model.CoachModel;

/**
 * FXML Controller class
 *
 * @author josenaldo
 */
public class CoachController extends AbstractDataController {
    private final CoachModel coachModel; 
    
    @FXML
    private TextField txtName;
    
    public CoachController()
    {
        this.coachModel = new CoachModel("INSERT INTO Coach (name) VALUES (?)");
        this.selected = null;
    }
        
    @FXML
    private void handleBtnSaveAction(ActionEvent event) {
        if(this.selected == null)
        {
            try
            {
                if(!txtName.getText().trim().isEmpty())
                    this.coachModel.insert(new Coach(txtName.getText()));
            }
            
            catch (SQLException ex)
            {
                Logger.getLogger(CoachController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            this.clearFields();
        }
        
        else
        {
        //    this.coachModel.update(this.selected);
            this.selected = null;
            this.clearFields();
        }
            
    }
    
    @FXML
    private void handleBtnDeleteAction(ActionEvent event)
    {
        if(this.selected != null)
        {
         //   this.coachModel.delete(this.selected);
            this.selected = null;
        }
    }
    
    @Override
    protected void clearFields()
    {
        txtName.setText("");
    }
}
