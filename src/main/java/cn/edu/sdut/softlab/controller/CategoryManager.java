package cn.edu.sdut.softlab.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import cn.edu.sdut.softlab.model.Category;
import cn.edu.sdut.softlab.service.CategoryFacade;

@Named("categoryManager")
@RequestScoped
public class CategoryManager {

	@Inject
	CategoryFacade userService;
	
	@Inject
	EntityManager em;

	@Inject
	private UserTransaction utx;
	
	
	private Category category = new Category();
	
	public List<Category> getCategoryName() throws NotSupportedException, SystemException {
		
		utx.begin();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery();
	    cq.select(cq.from(category.getClass()));
	    
	    //Query query = (Query) em.createQuery(cq);
	    
	    List categoryName = em.createQuery(cq).getResultList();
	    
	    for(int i = 0;i < categoryName.size();i ++) {
	    	System.out.println( "new println--------------:" + categoryName.toString());
	    }
	    //System.out.println("Single Result : " + em.createQuery(cq).getSingleResult().toString());
	    
	    //System.out.println("print String" + em.createQuery(cq).getResultList().toString());
	    
	    //List<Category> categoryName = new List()<>;
	    return em.createQuery(cq).getResultList();
	}
}
