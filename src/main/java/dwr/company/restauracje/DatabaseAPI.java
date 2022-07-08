package dwr.company.restauracje;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.*;
import java.util.logging.Level;

public class DatabaseAPI {
    //polaczenie z PSQL
     static EntityManagerFactory emfactory ;

    static EntityManager entitymanager;
    public DatabaseAPI(){
        org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.INFO);//OFF
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "jpa" );

        EntityManager entitymanager = emfactory.createEntityManager( );


    }

    // pierwsze query dla bazy poki co niedokonczone
    public String getAllUsers(){
        entitymanager.getTransaction().begin( );
        System.out.println("cos");
        Employee emp = entitymanager.find(Employee.class,1);
        return (emp.getId()+" "+emp.getName()+" "+emp.getLastname());
    }

}
