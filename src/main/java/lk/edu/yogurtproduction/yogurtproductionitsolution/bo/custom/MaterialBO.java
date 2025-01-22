package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.SuperBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.MatirialDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MaterialBO extends SuperBO {

    public String getNextId() throws SQLException ;

    public boolean save(MatirialDto matirialDto) throws SQLException ;

    public ArrayList<MatirialDto> getAll() throws SQLException, ClassNotFoundException;
}
