import java.util.*;
import java.util.ArrayList;
//Movie class
public class Movie{

    //attributes of movie
    int id;
    String name;
    String year;
    ArrayList<String> genre = new ArrayList<String>();
    //category wise reviews(more can be added i.e , good to go have)
    Map<String,Integer> viewer_reviews = new HashMap<String,Integer>(); 
    int total_viewer_reviews=0;
    int viewer_reviews_sum=0;
    Map<String,Integer> critic_reviews = new HashMap<String,Integer>(); 
    int total_critic_reviews=0;
    int critic_reviews_sum=0;

    //constructor
    Movie(String name,String year,ArrayList<String> genre)
    {
        this.name=name;
        this.year=year;
        
        for(int i=0;i<genre.size();i++)
        {
            this.genre.add(genre.get(i));
        }

    }
}



