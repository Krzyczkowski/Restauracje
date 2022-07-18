package dwr.company.restauracje;

import entity.*;
import org.json.simple.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class DatabaseAPI {
    static EntityManagerFactory emf;
    static EntityManager em;
    String restaurant;
    public DatabaseAPI(){
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
        Query query = em.createQuery("SELECT emp FROM Employee emp where emp.id = ?1").setParameter(1, id);
        List<Employee> list = query.getResultList();
        em.getTransaction().commit();
        return (prepareJSON(list));
    }
    public JSONObject getEmployeeByName(String name){
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT emp FROM Employee emp where emp.name like ?1").setParameter(1, name + "%");
        List<Employee> list = query.getResultList();
        em.getTransaction().commit();
        return (prepareJSON(list));
    }
    public JSONObject getAllEmployeesFullInfo(String name){
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT emp FROM Employee emp where emp.name like ?1 or emp.lastname like ?1 ").setParameter(1, "%"+name + "%");
        List<Employee> list = query.getResultList();
        System.out.println(list.size());
        List<Logins> list1 = new ArrayList<>();
        for (int i =0; i<list.size();i++)
        {
            query = em.createQuery("select log from Logins log where log.id = ?1").setParameter(1,list.get(i).getId());
            Logins log =(Logins) query.getSingleResult();
            em.merge(log);
            list1.add(log);
        }
        em.getTransaction().commit();
        JSONObject jo = new JSONObject();
        for(Integer i=0; i<list.size();i++){
            jo.put(i.toString(),list1.get(i).toJSON());
        }
        return jo;
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

    public void updateEmployee(Logins e){
        em.getTransaction().begin();
        em.merge(e);
        em.getTransaction().commit();
    }
    public JSONObject getAllEmployeesFullInfo()
    {
        JSONObject jo = new JSONObject();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT log FROM Logins log");
        List<Logins> list = query.getResultList();

        em.getTransaction().commit();
        for(Integer i=0; i<list.size();i++){
            jo.put(i.toString(),list.get(i).toJSON());
        }
        return jo;
    }

//    public int getDatebaseIdByName(String name){
//        em.getTransaction().begin();
//        Query query = em.createQuery("SELECT rest FROM Restaurants rest where rest.name like ?1").setParameter(1, name);
//        List<Restaurants> list = query.getResultList();
//        em.getTransaction().commit();
//        if (list.size()==1)
//            return list.get(0).getId();
//        return 0;
//    }
    public JSONObject getAllRestaurants(){
        JSONObject jo = new JSONObject();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT res FROM Restaurants res");
        List<Restaurants> list = query.getResultList();
        em.getTransaction().commit();
        for(Integer i=0; i<list.size();i++){
            jo.put(i.toString(),list.get(i).toJSON());
        }
        return jo;
    }
    public Logins authorization(String userName, String password,String restaurant){
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT log FROM Logins log where log.login = ?1 and log.password = ?2 and log.restaurantname = ?3").setParameter(1, userName).setParameter(2,password).setParameter(3,restaurant);
        List<Logins> list = query.getResultList();
        em.getTransaction().commit();
        if(list.size()==1) {
            this.restaurant=list.get(0).getRestaurantname();
            return list.get(0);
        }

        Logins bad_login = new Logins();
        bad_login.setId(-1);
        return bad_login;
    }
//    public Integer getIdRestaurantByName(String name){
//        em.getTransaction().begin();
//        Query query = em.createQuery("SELECT rest FROM Restaurants rest where rest.name like ?1").setParameter(1, name);
//        List<Restaurants> list = query.getResultList();
//        em.getTransaction().commit();
//        if (list.size()==1)
//            return list.get(0).getId();
//        return 0;
//    }

    public JSONObject getProducts() {
        JSONObject jo = new JSONObject();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT prod FROM Products prod where prod.restaurant=?1").setParameter(1,restaurant);
        List<Products> list = query.getResultList();
        em.getTransaction().commit();
        for(Integer i=0; i<list.size();i++){
            jo.put(i.toString(),list.get(i).toJSON());
        }
        return jo;
    }

    public JSONObject getProducts(String name,String category) {
        JSONObject jo = new JSONObject();
        em.getTransaction().begin();
        Query query;
        if (category.equals(""))
        {
            query = em.createQuery("SELECT prod FROM Products prod where prod.name like ?1 and prod.restaurant=?2 ").setParameter(1,name+"%").setParameter(2,restaurant);
        }
        else
            query = em.createQuery("SELECT prod FROM Products prod where prod.name like ?1 and prod.category = ?2 and prod.restaurant=?3 ").setParameter(1,name+"%").setParameter(2,category).setParameter(3,restaurant);
        List<Products> list = query.getResultList();
        em.getTransaction().commit();
        for(Integer i=0; i<list.size();i++){
            jo.put(i.toString(),list.get(i).toJSON());
        }
        return jo;
    }


    public JSONObject getCategories() {
        JSONObject jo = new JSONObject();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT cat FROM Categories cat where cat.restaurant=?1").setParameter(1,restaurant);
        List<Categories> list = query.getResultList();
        em.getTransaction().commit();
        for(Integer i=0; i<list.size();i++){
            jo.put(i.toString(),list.get(i).toJSON());
        }
        return jo;
        //
    }

    public JSONObject getStorage() {
        JSONObject jo = new JSONObject();
        em.getTransaction().begin();
        Query query = em.createQuery( "SELECT st FROM Storage st where st.restaurant = ?1 ").setParameter(1,restaurant);
        List<Storage> list = query.getResultList();
        em.getTransaction().commit();
        System.out.println(list.size());
        for(Integer i=0; i<list.size();i++){
            jo.put(i.toString(),list.get(i).toJSON());
        }
        return jo;
    }
    public void insertStorageItem(Storage s) {
        System.out.println("id w db:"+s.getId());
        em.getTransaction().begin();
        em.merge(s);
        em.getTransaction().commit();

    }

    public void updateStorageAmount(Storage st) {
        em.getTransaction().begin();
        Storage s = em.find(Storage.class,st.getId());
        s.setAmount(st.getAmount());
        em.merge(s);
        em.getTransaction().commit();
    }

    public void deleteItem(Storage st) {
        em.getTransaction().begin();
        Storage s = em.find(Storage.class, st.getId());
        em.remove(s);
        em.getTransaction().commit();
    }

    public String getProductName(Integer id) {
        em.getTransaction().begin();
        Products s = em.find(Products.class, id);
        em.getTransaction().commit();
        return s.getName();
    }


}








//        em.createNativeQuery("INSERT INTO employee (name, lastname) VALUES (?,?)")
//        .setParameter(1, e.getName())
//        .setParameter(2, e.getLastname())
//        .executeUpdate();