package lk.edu.yogurtproduction.yogurtproductionitsolution.bo;

import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.impl.EmployeeBOImpl;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.impl.SupplierBOImpl;

public class BOFactroy {

    private static BOFactroy boFactroy;
    private BOFactroy() {}
    public static BOFactroy getInstance() {
        return boFactroy == null ? new BOFactroy() : boFactroy;
    }

    public enum BOType{
        EMPLOYEE,SUPPLIER
    }

    public SuperBO getBO(BOType type) {
        switch (type) {
            case EMPLOYEE:
                return new EmployeeBOImpl();
                case SUPPLIER:
                    return new SupplierBOImpl();
                    default:
                        return null;
        }
    }
}
