package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.SuperBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.CashBookDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.MatirialDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.SuplierDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CashBookBO extends SuperBO {


    public int getAllPayAmount() throws SQLException ;
    public Boolean saveResept(CashBookDto cashBookDto) throws SQLException ;


    public String getNextId() throws SQLException ;

    public ArrayList<CashBookDto> getAll() throws SQLException, ClassNotFoundException ;

    public boolean save(CashBookDto cashBookDto) throws SQLException ;

    ArrayList<String> getAllSupIds() throws SQLException;

    SuplierDto findByID(String selectID) throws SQLException;

    ArrayList<String> getAllItemIds() throws SQLException;

    MatirialDto findById(String selectID) throws SQLException;
}
