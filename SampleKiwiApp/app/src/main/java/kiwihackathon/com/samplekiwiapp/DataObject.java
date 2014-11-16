package kiwihackathon.com.samplekiwiapp;

import com.ibm.mobile.services.data.IBMData;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

/**
 * Created by Ming on 15/11/2014.
 */
@IBMDataObjectSpecialization("DataObject")
public class DataObject extends IBMDataObject {
    public static final String CLASS_NAME = "DataObject";
    private static final String NAME = "name";
    private  static  final  String AGE = "age";
    private static final String SEX ="sex";
    private static final String HEIGHT="height";
    private static final String WEIGHT = "weight";

    /**
     * Gets the name of the Item.
     * @return String itemName
     */
    public String getName() {
        return (String) getObject(NAME);
    }
    public String getAge() {
        return (String) getObject(AGE);
    }
    public String getSex() {
        return (String) getObject(SEX);
    }
    public String getWeight() {
        return (String) getObject(WEIGHT);
    }
    public String getHeight() {
        return (String) getObject(HEIGHT);
    }


    /**
     * Sets the name of a list item, as well as calls setCreationTime().
     * @param String itemName
     */
    public void setName(String itemName) {
        setObject(NAME, (itemName != null) ? itemName : "");
    }
    public void setAge(String itemName) {
        setObject(AGE, (itemName != null) ? itemName : "");
    }
    public void setSex(String itemName) {
        setObject(SEX, (itemName != null) ? itemName : "");
    }
    public void setWEIGHT(String itemName) {
        setObject(WEIGHT, (itemName != null) ? itemName : "");
    }
    public void setHeight(String itemName) {
        setObject(HEIGHT, (itemName != null) ? itemName : "");
    }


    /**
     * When calling toString() for an item, we'd really only want the name.
     * @return String theItemName
     */
    public String toString() {

        return getName()+","+getAge()+","+getHeight()+","+getSex()+","+getWeight();
    }
}
