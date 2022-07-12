package dwr.company.restauracje;



import entity.Employee;
import entity.Logins;
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
        Query query = em.createQuery("SELECT emp FROM Employee emp where emp.id = ?").setParameter(1, id);
        List<Employee> list = query.getResultList();
        em.getTransaction().commit();
        return (prepareJSON(list));
    }
    public JSONObject getEmployeeByName(String name){
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT emp FROM Employee emp where emp.name like ?").setParameter(1, name + "%");
        List<Employee> list = query.getResultList();
        em.getTransaction().commit();
        return (prepareJSON(list));
    }
    public void insertEmployee (Employee e){
        em.getTransaction().begin();
        em.merge(e);
        em.getTransaction().commit();
    }
        public void deleteEmployee (Integer id){
        em.getTransaction().begin();
        //deleteLogin(id);
        em.remove(em.find(Employee.class, id));
        em.getTransaction().commit();
    }

    public void updateEmployee(Employee e){
        em.getTransaction().begin();
        Employee emp = em.find(Employee.class,e.getId());
        emp.setId(e.getId());
        emp.setName(e.getName());
        emp.setLastname(e.getLastname());
        em.getTransaction().commit();
    }
    public JSONObject getAllEmployeesFullInfo()
    {
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT log.login,log.password,log.idrestaurant,emp.name,emp.lastname FROM Logins log  join Employee emp on log.id = emp.id");
        List<Object[]> list = query.getResultList();
        for (Object[] result : list) {
            System.out.println(result[0].toString() + " " + result[1].toString()+" " + result[2]+" " + result[3]);
        }

        em.getTransaction().commit();
        return new JSONObject();
    }
}








//        em.createNativeQuery("INSERT INTO employee (name, lastname) VALUES (?,?)")
//        .setParameter(1, e.getName())
//        .setParameter(2, e.getLastname())
//        .executeUpdate();