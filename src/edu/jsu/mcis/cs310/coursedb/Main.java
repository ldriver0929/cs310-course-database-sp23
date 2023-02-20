package edu.jsu.mcis.cs310.coursedb;

import com.github.cliftonlabs.json_simple.Jsoner;
import edu.jsu.mcis.cs310.coursedb.dao.*;

public class Main {

    public static void main(String[] args) {
        
        DAOFactory daoFactory = new DAOFactory("coursedb");
        //RegistrationDAO registrationDAO = new RegistrationDAO(daoFactory);
        //boolean success = registrationDAO.delete(1, 1);
        RegistrationDAO registrationDao = daoFactory.getRegistrationDAO();
        
        String json1 = "[{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"21098\"}]";
        
        registrationDao.delete(1, DAOUtility.TERMID_SP23);
        registrationDao.create(1, DAOUtility.TERMID_SP23, 21098);
        
        String json2 = registrationDao.list(1, DAOUtility.TERMID_SP23);
        
        System.out.println(json1);
        System.out.println(json2);
        
        if ( !daoFactory.isClosed() ) {
            System.out.println("Connected Successfully!");    
        }
        
        
        
    }
    
}