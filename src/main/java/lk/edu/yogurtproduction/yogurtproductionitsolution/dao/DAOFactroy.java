package lk.edu.yogurtproduction.yogurtproductionitsolution.dao;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom.impl.*;

public class DAOFactroy {

    private static DAOFactroy daoFactroy;
    private DAOFactroy() {}
    public static DAOFactroy getInstance() {
       return daoFactroy == null ? daoFactroy = new DAOFactroy() : daoFactroy;
    }

    public enum DAOType {
        EMPLOYEE,SUPPLIER,QUERY,RESIPE,MATERIAL,MATERIAL_USAGE,STOCK,INVENTROY ,CASHBOOK,PACKING,PRODTION   }

    public SuperDAO  getDAO(DAOType type) {
        switch (type) {
            case EMPLOYEE:
                return new EmployeeDAOImpl();
                case SUPPLIER:
                    return new SupplierDAOImpl();
                    case QUERY:
                        return  new QueryDAOImpl();
                        case RESIPE:
                            return new ResipesDAOImpl();
                            case MATERIAL:
                                return new MaterialDAOImpl();
                                case MATERIAL_USAGE:
                                    return new MaterialUsageDAOImpl();
            case STOCK:
                return new StockDAOImpl();
                case INVENTROY:
                    return new InventroyDAOImpl();

                    case CASHBOOK:
                        return new CashBookDAOImpl();

                        case PACKING:
                            return new PackingDAOImpl();
                    default:
                        return null;
        }
    }

}
