package cerberus.party;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * provides basic communication information
 */
public class Contact {

    public static Type arrayType = new TypeToken<ArrayList<Contact>>() {}.getType();

    private String name;
    private int mobile;
    private String email;

    /**
     * default constructor
     * @param name name of contact
     * @param mobile mobile no. of contact
     * @param email email of contact
     */
    public Contact(String name, int mobile, String email) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
