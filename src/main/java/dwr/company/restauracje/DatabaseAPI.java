package dwr.company.restauracje;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.*;
public class DatabaseAPI {
    //polaczenie z PSQL
    private void connect(){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "jpa" );

        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction().begin( );

    }

    // pierwsze query dla bazy poki co niedokonczone
    private void getAllUsers(){
        String query = "select * from workers";
    }

}
