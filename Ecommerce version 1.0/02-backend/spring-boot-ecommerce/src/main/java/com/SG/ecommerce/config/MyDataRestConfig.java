package com.SG.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import com.SG.ecommerce.entity.Product;
import com.SG.ecommerce.entity.ProductCategory;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer{

	//@ToDo: our rest api disclose the entity id
	private EntityManager entityManager;
	
	
	//#################Autowire JPA entity manager########
	@Autowired
	public MyDataRestConfig(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	
	
	@Override
	    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

	        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};

	        // disable HTTP methods for Product: PUT, POST, DELETE and PATCH
	        config.getExposureConfiguration()
	                .forDomainType(Product.class)
	                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
	                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));

	        // disable HTTP methods for ProductCategory: PUT, POST, DELETE and PATCH
	        config.getExposureConfiguration()
	                .forDomainType(ProductCategory.class)
	                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
	                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
	        
	        
	        //call an internal helper method to expose the id's
	        
	        exposeIds(config);
	 }
	
	
	private void exposeIds(RepositoryRestConfiguration config) {
		
		//expose entity ids
		
		
		//get a list of all entity classes from the entity manager
		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
		
		//create an array List of those entity types
		List<Class> entityClasses = new ArrayList<>();
		for(EntityType tempEntityType: entities) {
			entityClasses.add(tempEntityType.getJavaType());
		}
		
		//expose the entity id for the array of entity/domain types
		Class[] domainTypes = entityClasses.toArray(new Class[0]);
		config.exposeIdsFor(domainTypes); //this method expose id's exposeIdsFor
		
		
	}
	
}
