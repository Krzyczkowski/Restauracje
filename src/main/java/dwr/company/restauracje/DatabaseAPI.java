package dwr.company.restauracje;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.*;
public class DatabaseAPI {
    //polaczenie z PSQL
    EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "jpa" );

    EntityManager entitymanager = emfactory.createEntityManager( );
    private void connect(){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "jpa" );

        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction().begin( );

    }

    // pierwsze query dla bazy poki co niedokonczone
    private void getAllUsers(){
        Employee emp = entitymanager.find(Employee.class,1);
        System.out.println(emp.getId()+" "+emp.getName()+" "+emp.getLastname());
    }

}
