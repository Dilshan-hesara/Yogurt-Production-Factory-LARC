package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.SuperBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.ProdtionDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductionBO extends SuperBO {

    public String getNextId() throws SQLException ;

    public ArrayList<ProdtionDto> getAll() throws SQLException, ClassNotFoundException;

    public boolean save(ProdtionDto prodtionDto) throws SQLException ;


    public boolean saveProdt(ProdtionDto prodtionDto) throws SQLException;


    String getNextMatId() throws SQLException;

    ArrayList<String> getAllProdName() throws SQLException;

    String getNextInventroyId() throws SQLException;

    ArrayList<String> getAllAvItems() throws SQLException;
}
