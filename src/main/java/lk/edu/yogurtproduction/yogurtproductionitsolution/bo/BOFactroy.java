package lk.edu.yogurtproduction.yogurtproductionitsolution.bo;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.impl.*;

public class BOFactroy {

    private static BOFactroy boFactroy;
    private BOFactroy() {}
    public static BOFactroy getInstance() {
        return boFactroy == null ? new BOFactroy() : boFactroy;
    }

    public enum BOType{
        EMPLOYEE,SUPPLIER,RESIPE,MATERIAL,MATERIAL_USAGE ,INVENTROY,STOCK,CASHBOOK
    }

    public SuperBO getBO(BOType type) {
        switch (type) {
            case EMPLOYEE:
                return new EmployeeBOImpl();
                case SUPPLIER:
                    return new SupplierBOImpl();
                    case RESIPE:
                        return new ResipesBOImpl();

                        case MATERIAL:
                            return new MaterialBOImpl();
                            case MATERIAL_USAGE:
                                return new MaterialUsageBOImpl();
                                case INVENTROY:
                                    return new InventroyBOImpl();
                                    case STOCK:
                                        return new StockBOImpl();
                                        case CASHBOOK:
                                            return new CashBookBOImpl();
                    default:
                        return null;
        }
    }
}
