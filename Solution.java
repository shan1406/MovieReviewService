import java.util.*;
import java.util.ArrayList;

public class Solution{
    //store list of users
    HashMap<String,User> userlist = new HashMap<String,User>();
    //stores list of movies
    HashMap<String,Movie> movielist = new HashMap<String,Movie>();
    //stores the movies genrewise
    HashMap<String,ArrayList<String>> genrewise = new HashMap<String,ArrayList<String>>();
    //stores movies yearwise
    HashMap<String,ArrayList<String>> yearwise = new HashMap<String,ArrayList<String>>();
    
    //function to add a movie
    void addMovie(String name,String year,ArrayList<String> genre)
    {
        //new movie object
        Movie m = new Movie(name,year,genre);

        //add to the movielist
        movielist.put(name,m);

        //push the movie in the yearwise map of arraylist
        if(yearwise.get(year)!=null)
            yearwise.get(year).add(name);
        else
            yearwise.put(year,new ArrayList<>(Arrays.asList(name)));

        //push the movie in the genrewise map of arraylist
        for(int i=0;i<genre.size();i++)
        {
            if(genrewise.get(genre.get(i))!=null)
                genrewise.get(genre.get(i)).add(name);
            else
                genrewise.put(genre.get(i),new ArrayList<>(Arrays.asList(name)));
        }

    }

    //function to add a user
    void addUser(String name)
    {
        User u = new User(name);
        userlist.put(name,u);
    }
    
    //function to add a review
    void add_review(String user,String movie,int rating)
    {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        String currentyear = String.valueOf(year);
        
        if(movielist.get(movie).viewer_reviews.get(user)!=null || movielist.get(movie).critic_reviews.get(user)!=null )
            System.out.println("Exception multiple reviews not allowed");
        else if(movielist.get(movie).year.compareTo(currentyear)>0)
            System.out.println("Exception movie yet to be released");
        else
        {
            //more conditions can be added based on number of categories like(viewer,critic,expert,admin)
            if(userlist.get(user).role=="viewer")
            {
                movielist.get(movie).viewer_reviews.put(user,rating);
                movielist.get(movie).viewer_reviews_sum+=rating;
                movielist.get(movie).total_viewer_reviews+=1;
                userlist.get(user).published++;
                if(userlist.get(user).published==3)
                    userlist.get(user).role="critic";
            }
            else
            {
                movielist.get(movie).critic_reviews.put(user,rating);
                movielist.get(movie).critic_reviews_sum+=2*rating;
                movielist.get(movie).total_critic_reviews+=1;
            }
        }
    }

    //function to List top n movies by total review score by ‘viewer’ in a particular year of release
    void top_n_by_year_viewerPreferred(int n,String year)
    {
        //push the movies in the given year into a arraylist
        ArrayList <Movie> list = 
                  new ArrayList <Movie> ();
        for(int i=0;i<yearwise.get(year).size();i++)
        {
            list.add(movielist.get(yearwise.get(year).get(i)));
        } 
  
        // Sort the list in decreasing order of viewer_reviews_sum
        Collections.sort(list, new Comparator<Movie>()            { 
            public int compare(Movie m1,Movie m2)  
            { 
                if (m1.viewer_reviews_sum<m2.viewer_reviews_sum)
                    return 1;
                else
                    return -1;
            } 
        }); 
        //print top n
        System.out.println("Top "+n+" movies by total review score by viewers in year "+year+":");
        for(int i=0;i<n;i++)
            System.out.println(list.get(i).name+" - "+list.get(i).viewer_reviews_sum+" ratings");

    }

    //function to List top n movies by total review score by ‘critics’ in a particular year of release
    void top_n_by_year_criticPreferred(int n,String year)
    {
        //push the movies in the given year into a arraylist
        ArrayList <Movie> list = 
                  new ArrayList <Movie> ();
        for(int i=0;i<yearwise.get(year).size();i++)
        {
            list.add(movielist.get(yearwise.get(year).get(i)));
        } 
        // Sort the list in decreasing order of critic_reviews_sum
        Collections.sort(list, new Comparator<Movie>()            { 
            public int compare(Movie m1,Movie m2)  
            { 
                if(m1.critic_reviews_sum<m2.critic_reviews_sum)
                    return 1;
                else
                    return -1;
            } 
        }); 
        //print top n
        System.out.println("Top "+n+" movies by total review score by critics in year "+year+":");
        for(int i=0;i<n;i++)
            System.out.println(list.get(i).name+" - "+list.get(i).critic_reviews_sum+" ratings");
    }

    //function to  List top n movies by total review score by ‘viewer’ in a particular genre
    void top_n_by_genre_viewerPreferred(int n,String genre)
    {
        //push the movies in the given genre into a arraylist
        ArrayList <Movie> list = 
                  new ArrayList <Movie> ();
        for(int i=0;i<genrewise.get(genre).size();i++)
        {
            list.add(movielist.get(genrewise.get(genre).get(i)));
        } 
  
        // Sort the list in decreasing order of viewer_reviews_sum
        Collections.sort(list, new Comparator<Movie>()            { 
            public int compare(Movie m1,Movie m2)  
            { 
                if(m1.viewer_reviews_sum<m2.viewer_reviews_sum)
                    return 1;
                else
                    return -1;
            } 
        }); 
        //print top n
        System.out.println("Top "+n+" movies by total review score by viewers in genre "+genre+":");
        for(int i=0;i<n;i++)
            System.out.println(list.get(i).name+" - "+list.get(i).viewer_reviews_sum+" ratings");

    }

    //function to  List top n movies by total review score by ‘critics’ in a particular genre
    void top_n_by_genre_criticPreferred(int n,String genre)
    {
        //push the movies in the given genre into a arraylist
        ArrayList <Movie> list = 
                  new ArrayList <Movie> ();
        for(int i=0;i<genrewise.get(genre).size();i++)
        {
            list.add(movielist.get(genrewise.get(genre).get(i)));
        } 

        // Sort the list in decreasing order of critic_reviews_sum
        Collections.sort(list, new Comparator<Movie>()            { 
            public int compare(Movie m1,Movie m2)  
            { 
                if(m1.critic_reviews_sum<m2.critic_reviews_sum)
                    return 1;
                else
                    return -1;
            } 
        }); 
        //print top n
        System.out.println("Top "+n+" movies by total review score by critics in genre "+genre+":");
        for(int i=0;i<n;i++)
            System.out.println(list.get(i).name+" - "+list.get(i).critic_reviews_sum+" ratings");
    }

    //function to find Average review score in a particular year of release
    void average_review_year(String year)
    {
        int sum=0;
        int count=0;
        //find the sum of the all ratings and total number of ratings 
        for(int i=0;i<yearwise.get(year).size();i++)
        {
            sum+=movielist.get(yearwise.get(year).get(i)).viewer_reviews_sum+movielist.get(yearwise.get(year).get(i)).critic_reviews_sum;
            count+=movielist.get(yearwise.get(year).get(i)).total_viewer_reviews+movielist.get(yearwise.get(year).get(i)).total_critic_reviews;
        }
        if(count>0)
            System.out.printf("%.2f",1.0*sum/count);
        System.out.println();
    }

    //function to find Average review score in a particular genre
    void average_review_genre(String genre)
    {
        int sum=0;
        int count=0;
        //find the sum of the all ratings and total number of ratings 
        for(int i=0;i<genrewise.get(genre).size();i++)
        {
            sum+=movielist.get(genrewise.get(genre).get(i)).viewer_reviews_sum+movielist.get(genrewise.get(genre).get(i)).critic_reviews_sum;
            count+=movielist.get(genrewise.get(genre).get(i)).total_viewer_reviews+movielist.get(genrewise.get(genre).get(i)).total_critic_reviews;
        }
        if(count>0)
            System.out.printf("%.2f",1.0*sum/count);
        System.out.println();
    }

    //function to find Average review score for a particular movie
    void average_review_movie(String movie)
    {
        int sum=movielist.get(movie).viewer_reviews_sum+movielist.get(movie).critic_reviews_sum;
        int count=movielist.get(movie).total_viewer_reviews+movielist.get(movie).total_critic_reviews;
        if(count>0)
            System.out.printf("%.2f",1.0*sum/count);
        System.out.println();
    }

    //main function
    public static void main(String args[])
    {
        Solution object=new Solution();
        
        //Requirement 1(a)- Adding movies
        object.addMovie("Don","2006",new ArrayList<>(Arrays.asList("Action","Comedy")));
        object.addMovie("Tiger","2008",new ArrayList<>(Arrays.asList("Drama")));
        object.addMovie("Padmaavat","2006",new ArrayList<>(Arrays.asList("Comedy")));
        object.addMovie("Lunchbox","2022",new ArrayList<>(Arrays.asList("Drama")));
        object.addMovie("Guru","2006",new ArrayList<>(Arrays.asList("Drama")));
        object.addMovie("Metro","2006",new ArrayList<>(Arrays.asList("Romance")));
        
        //Requirement 1(b)- Adding users
        object.addUser("SRK");
        object.addUser("Salman");
        object.addUser("Deepika");
        
        //Requirement 2 Adding reviews
        object.add_review("SRK","Don",2);
        object.add_review("SRK","Padmaavat",8);
        object.add_review("Salman","Don",5);
        object.add_review("Deepika","Don",9);
        object.add_review("Deepika","Guru",6);
        object.add_review("SRK","Don",10);
        object.add_review("Deepika","Lunchbox",5);
        object.add_review("SRK","Tiger",5);
        object.add_review("SRK","Metro",7);
        
        //Requirement 3 -  List top n movies by total review score by ‘viewer’ in a particular year of release
        object.top_n_by_year_viewerPreferred(1,"2006");
        
        //Requirement 4 -  List top n movies by total review score by ‘viewer’ in a particular genre 
        object.top_n_by_genre_viewerPreferred(1,"Drama");
        
        //Requirement 5 -  List top n movies by total review score by ‘critics’ in a particular year of release
        object.top_n_by_year_criticPreferred(1,"2006");
        
        //Requirement 6 -  List top n movies by total review score by ‘critics’ in a particular genre
        object.top_n_by_genre_criticPreferred(1,"Drama");
        
        //Requirement 7 - Average review score in a particular year of release
        object.average_review_year("2006");
        
        //Requirement 8 - Average review score in a particular genre
        object.average_review_genre("Drama");
        
        //Requirement 9 - Average review score for a particular movie
        object.average_review_movie("Don");

    }

}



























