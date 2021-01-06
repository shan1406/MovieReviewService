import java.util.*;

//User class
public class User{
    
    //attributes of user
    int id;
    String name;
    String role;
    int published;

    //constructor
    User(String name)
    {
        this.name=name;
        this.role="viewer";
        this.published=0;
    }
}

