package lk.edu.yogurtproduction.yogurtproductionitsolution.dao;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.EmployeeDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.QueryDAOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.SupplierDAOImpl;

public class DAOFactroy {

    private static DAOFactroy daoFactroy;
    private DAOFactroy() {}
    public static DAOFactroy getInstance() {
       return daoFactroy == null ? daoFactroy = new DAOFactroy() : daoFactroy;
    }

    public enum DAOType {
        EMPLOYEE,SUPPLIER,QUERY    }

    public SuperDAO  getDAO(DAOType type) {
        switch (type) {
            case EMPLOYEE:
                return new EmployeeDAOImpl();
                case SUPPLIER:
                    return new SupplierDAOImpl();
                    case QUERY:
                        return  new QueryDAOImpl();
                    default:
                        return null;
        }
    }

}
