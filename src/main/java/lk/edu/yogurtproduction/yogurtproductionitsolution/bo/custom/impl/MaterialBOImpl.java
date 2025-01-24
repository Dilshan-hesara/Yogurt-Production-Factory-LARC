package lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.impl;

import javafx.scene.paint.Material;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.MaterialBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.DAOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.MaterialDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.MatirialDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Matirial;
import lk.edu.yogurtproduction.yogurtproductionitsolution.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaterialBOImpl implements MaterialBO {

    MaterialDAO materialDAO = (MaterialDAO) DAOFactroy.getInstance().getDAO(DAOFactroy.DAOType.MATERIAL);

    public String getNextId() throws SQLException {

        return materialDAO.getNextId();

    }


    public boolean save(MatirialDto material) throws SQLException {

        return materialDAO.save(new Matirial(material.getMatId(),material.getMatName(),material.getMatQty(),material.getMatPrice()));

    }

    public ArrayList<MatirialDto> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<MatirialDto> matirialDtos = new ArrayList<>();
        ArrayList<Matirial> materials = materialDAO.getAll();

        for (Matirial material : materials) {
            matirialDtos.add(new MatirialDto(new Matirial(material.getMatId(),material.getMatName(),material.getMatQty(),material.getMatPrice())));

        }
        return matirialDtos;

    }

}
