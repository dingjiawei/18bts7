package model;

/**
 * Created by Administrator on 2017/11/26.
 */

public class ModelCarNearby {

    public String strCarNumber; //车牌号
    public String strCarOwner;  //车主
    public String strCarStatus; //车状态
    public String    iCarScanedTime;//当前车被扫描的次数

    public ModelCarNearby( String carNumber,String carOwner,String carStatus,String crScanedTime ){
        strCarNumber = carNumber;
        strCarOwner = carOwner;
        strCarStatus = carStatus;
        iCarScanedTime = crScanedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModelCarNearby that = (ModelCarNearby) o;

        return strCarNumber != null ? strCarNumber.equals(that.strCarNumber) : that.strCarNumber == null;
    }

    @Override
    public int hashCode() {
        return strCarNumber != null ? strCarNumber.hashCode() : 0;
    }
}
