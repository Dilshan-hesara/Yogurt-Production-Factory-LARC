package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.SuperBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.InventroyDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InventroyBO extends SuperBO {

    public ArrayList<InventroyDto> getAll() throws SQLException, ClassNotFoundException;

    }
