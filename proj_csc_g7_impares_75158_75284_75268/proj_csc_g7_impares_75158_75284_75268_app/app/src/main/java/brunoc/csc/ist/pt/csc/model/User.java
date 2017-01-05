package brunoc.csc.ist.pt.csc.model;

/**
 * Created by Bruno on 30/12/2016.
 */

public class User {
    String name;
    MyLocation location;
    public User(String name, MyLocation location) {
        this.name = name;
        this.location = location;
    }
    public String getName(){return name;}
    public void setLocation(MyLocation location){this.location = location;}
    public MyLocation getLocation(){return location;}
}
