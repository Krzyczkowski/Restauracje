package dwr.company.restauracje;



import entity.Employee;
import org.json.simple.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;

public class databaseAPI {
    static EntityManagerFactory emf;
    static EntityManager em;
    public databaseAPI(){
        org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.INFO);//OFF
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();
    }
    private JSONObject prepareJSON(List<Employee> list){
        JSONObject jo = new JSONObject();
        for(Integer i=0; i<list.size();i++){
            jo.put(i.toString(),list.get(i).toJSON());
        }
        return jo;
    }
    // Employee
    public JSONObject getAllEmployee(){
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT emp FROM Employee emp");
        List<Employee> list = query.getResultList();
        em.getTransaction().commit();
        return (prepareJSON(list));
    }
    public void close(){
        em.close();
    }
    public JSONObject getEmployeeById(int id){
        em.getTransaction().begin();
        JSONObject jo = new JSONObject();
        //----- Zapytanie - lista pracowników
        Employee emp = em.find(Employee.class, id);
        em.getTransaction().commit();
        jo.put(id,emp.toJSON());
        return (jo);
    }
}
