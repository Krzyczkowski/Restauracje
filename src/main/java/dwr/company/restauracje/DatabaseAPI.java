package dwr.company.restauracje;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;

public class DatabaseAPI {
    //polaczenie z PSQL
     static EntityManagerFactory emfactory ;

    static EntityManager em;
    public DatabaseAPI(){
        org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.INFO);//OFF
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "jpa" );

        EntityManager em = emfactory.createEntityManager( );


    }

    // pierwsze query dla bazy poki co niedokonczone
    public String getAllUsers(){
        em.getTransaction().begin( );
        System.out.println("cos");
        Query query = em.createQuery("from Employee", Employee.class);
        List<Employee> list = query.getResultList();

        for (Employee e : list) {
            System.out.println("Empno: " + e.getId() + " Employee: " + e.getName() + " sal: " + e.getLastname());
        }
        return ("git");
    }

}
