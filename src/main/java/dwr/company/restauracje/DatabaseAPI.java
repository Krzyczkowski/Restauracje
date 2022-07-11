package dwr.company.restauracje;



import entity.Employee;
import org.json.simple.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;

public class DatabaseAPI {
    static EntityManagerFactory emf;
    static EntityManager em;
    public DatabaseAPI(){
        org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.INFO);//OFF
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();


    }

    // pierwsze query dla bazy poki co niedokonczone
    public JSONObject getAllUsers(){
        em.getTransaction().begin();
        JSONObject jo = new JSONObject();

        //----- Zapytanie - lista pracowników
        Query query = em.createQuery("SELECT emp FROM Employee emp");
        List<Employee> list = query.getResultList();

        for(Integer i=0; i<list.size();i++){
            jo.put(i.toString(),list.get(i).toJSON());
        }


        em.getTransaction().commit();
        return (jo);
    }
    public void close(){
        em.close();
    }

}
