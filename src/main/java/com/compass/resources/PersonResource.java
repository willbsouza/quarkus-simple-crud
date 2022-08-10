package com.compass.resources;

import com.compass.domain.Customer;
import com.compass.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

@Path("/start")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@Slf4j
public class PersonResource {

    @Inject
    private EntityManager em;


    @GET
    public Response findAll(){
        return Response.ok(em.createQuery("select p from Person p", Customer.class).getResultList()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id){
        Customer customer = em.find(Customer.class, id);
        if (customer != null){
            return Response.ok(customer).build();
        } else {
            throw new ObjectNotFoundException("Person ID: " + id + " not found.");
        }
    }

    @POST
    public Response create(Customer customer){
        em.persist(customer);
        URI uri = UriBuilder.fromUri("http://localhost:8080").build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateById(@PathParam("id") Long id, Customer customer){
        Customer customerUpdate = em.find(Customer.class, id);
        if(customerUpdate != null){
            customerUpdate.setName(customer.getName());
            return Response.ok(customerUpdate).build();
        } else {
            throw new ObjectNotFoundException("Person ID: " + id + " not found.");
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteById(@PathParam("id") Long id){
        Customer customer = em.find(Customer.class, id);
        if(customer != null){
            return Response.noContent().build();
        } else {
            throw new ObjectNotFoundException("Person ID: " + id + " not found.");
        }
    }
}