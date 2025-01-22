package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.impl;

import javafx.scene.paint.Material;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.MaterialBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.DAOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.MaterialDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.MatirialDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaterialBOImpl implements MaterialBO {

    MaterialDAO materialDAO = (MaterialDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.MATERIAL);

    public String getNextId() throws SQLException {

        return materialDAO.getNextId();

    }

    public boolean save(MatirialDto matirialDto) throws SQLException {

        return materialDAO.save(matirialDto);

    }

    public ArrayList<MatirialDto> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<MatirialDto> matirialDtos = new ArrayList<>();
        ArrayList<MatirialDto> materials = materialDAO.getAll();

        for (MatirialDto material : materials) {
            matirialDtos.add(material);

        }
        return matirialDtos;

    }

}
