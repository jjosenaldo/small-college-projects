/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maratona2.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import maratona2.domain.Entity;
import maratona2.utils.DBUtils;

/**
 *
 * @author josenaldo
 */
public abstract class AbstractModel
{
    protected String sql_insert;
    protected String sql_find_all;
    protected String sql_update;
    protected String sql_delete;
    
    protected Connection connection;
    
    public AbstractModel(String sql_insert, String sql_find_all, String sql_update, String sql_delete)
    {
        this.sql_insert = sql_insert;
        this.sql_find_all = sql_find_all;
        this.sql_update = sql_update;
        this.sql_delete = sql_delete;
    }
    
    protected void setConnection() throws SQLException
    {
        if(this.connection == null || this.connection.isClosed())
            this.connection = DBUtils.getConnection();
    }
    
    protected PreparedStatement prepareStatement(String command) throws SQLException
    {
        this.setConnection();
        
        return this.connection.prepareStatement(command);
    }  
    
    protected Statement createStatement() throws SQLException
    {
        this.setConnection();
        
        return this.connection.createStatement();
    }
    
    public void insert(Entity entity) throws SQLException
    {
        PreparedStatement statement = this.prepareStatement(this.sql_insert);
        this.setPreparedStatementParams(statement, entity);
        statement.executeUpdate();
        
        this.connection.close();
    }
    
    public void delete(int id) throws SQLException
    {
        PreparedStatement statement = this.prepareStatement(this.sql_delete);
        statement.setInt(1, id);
        statement.executeUpdate();
        
        this.connection.close();
    }
    
    public List<Entity> selectAll() throws SQLException
    {
        List<Entity> resultList = new ArrayList<>();
        
        Statement statement = this.createStatement();
        try (ResultSet resultSet = statement.executeQuery(this.sql_find_all))
        {
            while(resultSet.next())
            {
                this.addEntityToList(resultSet, resultList);
            }
        }
        
        return resultList;
    }
    
    protected abstract void setPreparedStatementParams(PreparedStatement statement, Entity entity) throws SQLException;
    
    protected abstract void addEntityToList(ResultSet resultSet, List<Entity> list) throws SQLException;
}


