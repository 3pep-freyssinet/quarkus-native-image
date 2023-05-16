package com.foo;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;


import java.util.List;
import java.util.Base64;
//import org.apache.maven.util.Base64;
import java.io.IOException;

//import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

//import org.postgresql.Driver;

//import retrofit2.http.Headers;

//@ApplicationScoped
@Path("/invoice")


public class InvoiceEndpoint {

  //@PersistenceContext
  //@PersistenceContext(unitName = "MyPU")

  
  @Inject
  EntityManager em;

  //    curl http://localhost:8080/invoice
  @GET
  @Path("/")
  @Produces("text/plain")
  public Response grettings() {
    return Response.ok("Hello world from invoice").build();
  }

  //    curl http://localhost:8080/invoice/all   ----> [{"id":1,"elec_index":"1000","gas_index":"1001","date":"01-01-70"},{"id":2,"elec_index":"2000","gas_index":"2001","date":"01-01-70"},{"id":3,"elec_index":"3000","gas_index":"3001","date":"01-01-70"}]
  @GET
  @Path("/all")
  //@Produces(MediaType.APPLICATION_JSON)

  public Invoice[] all() {
    
	/*
	try
        {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException ex)
        {
			System.out.println("****************************************** ClassNotFoundException = "+ex);
        }

	
	InitialContext ctx_PrgSql = null;
        java.sql.Connection pcon = null;
        try
        {
            ctx_PrgSql = new InitialContext();
            DataSource ds_PrgSql = (DataSource) ctx_PrgSql.lookup("java:jboss/datasources/ExampleDS");
			
			
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("MyPU");
			em = factory.createEntityManager();
			
            System.out.println("****************************************** ds_PrgSql = "+ds_PrgSql+" factory = "+factory+"em = "+em);
			
			if (ds_PrgSql == null)
            {            System.out.println("****************************************** ds_PrgSql = "+ds_PrgSql);
            }
	    }catch (NamingException ex)
        {               System.out.println("****************************************** NamingException = "+ex);
        }
		//pcon = ds_PrgSql.getConnection();
		//System.out.println("****************************************** pcon = "+pcon);
	
	//DataSource ds = (DataSource) context.lookup("java:jboss/datasources/MyDS");
		*/
		
	//return em.createNamedQuery("Book.findAll", Book.class)
	//Query query = em.createQuery("SELECT e FROM Book e");
	//System.out.println("******************************************");
	//return null;
	//return query.getResultList()
    //   .toArray(new Book[0]);
	   //Query query = em.createNamedQuery("Book.findAll");
	   
	   //System.out.println("****************************************** em = "+em);
	   
	//return em.createNamedQuery("Book.findAll")
    //    .getResultList()
    //    .toArray(new Book[0]);
	
	//EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPU");
	
	//Metamodel metamodel = emf.getMetamodel();
	//EntityType entityType = metamodel.entity(Book.class);
    //String name = entityType.getName();
	//System.out.println("****************************************** metamodel = "+metamodel+" entityType = "+entityType+" name = "+name);
	
	//EntityType et = emf.getMetamodel().entity(Book.class);
    //String name = et.getName();
	//System.out.println("****************************************** name = "+name);
	
	//em = emf.createEntityManager();
	//System.out.println("****************************************** em = "+em);
	
		//Set<EntityType<?>> entityTypes = emf.getMetamodel().getEntities();
        //EntityType et = emf.getMetamodel().entity(Book);
        //et.getName();
	//System.out.println("****************************************** em = "+em);
	//em.getMetamodel().managedType(Book.class);
	//em.getMetamodel().managedType();
	
	Query query = em.createNamedQuery("Invoice.findAll");			//em.createQuery("SELECT e FROM Invoice e");	
	//System.out.println("****************************************** query = "+query);
	
	List<Invoice> list = query.getResultList();
    //System.out.println("****************************************** list = "+list+" size = "+list.size());
	
	//Invoice Invoice = (Invoice)list.get(0);
	//System.out.println("****************************************** Invoice = "+Invoice);
	
	//for (Invoice invoice : list) {
    //  System.out.println(invoice.getId());
    //}
	
    //****************************************************************************
	/*
	Either:

	Foo[] array = list.toArray(new Foo[0]);
	or:

	Foo[] array = new Foo[list.size()];
	list.toArray(array); // fill the array
	Note that this works only for arrays of reference types. For arrays of primitive types, use the traditional way:

	List<Integer> list = ...;
	int[] array = new int[list.size()];
	for(int i = 0; i < list.size(); i++) array[i] = list.get(i);
	Update:
	It is recommended now to use list.toArray(new Foo[0]);, not list.toArray(new Foo[list.size()]);.
	*/
	//****************************************************************************
	
	return list.toArray(new Invoice[0]);
	
  }
   
  //used in new
  @GET
  @Path("/{id}") //the 'id' of the record.The curl query is like : curl http://localhost:8080/invoice/99, where '99' is the id of the record.
  //@Produces(MediaType.APPLICATION_JSON)
  public Invoice getId(@PathParam("id") String id){
	//in 'findId' id is an integer not string
	int id_ = Integer.parseInt(id);
	return (Invoice)em.createNamedQuery("Invoice.findId") //em.createNamedQuery("Invoice.findId", Invoice.class)
		.setParameter("id", id_)
        .getResultList()
        .get(0);
  }

  
  /*
			
		curl http://localhost:8080/invoice/id/1
		ou
		curl http://localhost:8080/invoice/date/01-01-70
  */
  
  //get id or date 
  @GET
  @Path("/{param0}/{param1}")   //par exemple : /id/99 ou /date/01-01-70
  //@Produces(MediaType.APPLICATION_JSON)
  public Invoice getInvoice(@PathParam("param0") String param0, @PathParam("param1") String param1){
    if(param0.equals("id"))
		return (Invoice)em.createNamedQuery("Invoice.findId") //em.createNamedQuery("Invoice.findId", Invoice.class)
        .setParameter("id", Integer.parseInt(param1))
        .getResultList()
        .get(0);		//one element in the list for isbn
	if(param0.equals("date"))
		return (Invoice)em.createNamedQuery("Invoice.findDate")
		.setParameter("date", param1)
        .getResultList()
        .get(0);		//one element in the list for author
		
	return null; // no id or date found
  }
  
  /*  
  
	  curl -H "Content-Type: application/json" -d "{\"elec_index\":\"1000\",\"gas_index\":\"2000\", \"date\":\"01-01-70\"}" -X POST http://localhost:8080/invoice/new
  */
  
  
  @POST
  @Path("/new")
  //@Consumes(MediaType.APPLICATION_JSON)
  //@Produces(MediaType.APPLICATION_JSON)
  @Transactional
  public Invoice post(Invoice invoice){
    System.out.println("********************************************************************************************************************");
	System.out.println("*******invoice = " + invoice);
	System.out.println("*******new, invoice, if = " + invoice.getId() + " elec_index = " + invoice.getElec_index() + " gas_index= " + invoice.getGas_index() + " date = " +invoice.getDate() );
	System.out.println("********************************************************************************************************************");
	
	//Invoice invoice_ = new Invoice(invoice.getId(), invoice.getElec_index(), invoice.getGas_index(), invoice.getDate());
	//Invoice invoice_ = new Invoice(invoice.getElec_index(), invoice.getGas_index(), invoice.getDate());
	Invoice invoice_ = new Invoice();
	invoice_.setElec_index(invoice.getElec_index());
	invoice_.setGas_index(invoice.getGas_index());
	invoice_.setDate(invoice.getDate());
	
	em.persist(invoice_);	//method persist() return void.
	
	//get the id of the persisted object.
	int id = invoice_.getId();
	
	String id_ = String.valueOf(id);//get the id from the perisist object.
	System.out.println("****************************************** new, invoice, id after persist = " + id_);
    return getId(id_); //invoice_; //; //call the above method 'getId' with argument from the persist.
  }
   
	
	/*
  @DELETE //pour voir l'effet de delete avec curl, remplacer '@DELETE' par '@GET'
  //@GET //@DELETE
  @Path("/delete/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional
  public Response DeleteId(@PathParam("id") int id){
    //Query query  = em.createNamedQuery("Book.findId", Book.class);
	//Query query_ = em.createNamedQuery("Book.findId");
	//System.out.println("****************************************** delete, query = " + query + " query_ = " + query_);
	
	Book book = em.createNamedQuery("Book.findId", Book.class).setParameter("id", id).getResultList().get(0);
	//System.out.println("****************************************** delete, number = " + number);
	if(book != null)em.remove(book);
	return Response.ok("delete Id " + id + " success").build();
	
	//autre methode
	//Bulk queries like update and delete cannot be used in TypedQuery. TypedQuery est le 2ème argument de createNamedQuery. donc utiliser createNamedQuery avec un seul argument.
	//int number = em.createNamedQuery("Book.removeId").setParameter("id", id).executeUpdate();  //Number of entities deleted 
    //System.out.println("****************************************** delete, number = " + number);
	//if(number != 1) return Response.ok("delete entities " + number + " failure").build();   
	//return Response.ok("delete entity " + number + " success").build();
  }
  */
  
  
  //  curl -H "Content-Type: application/json" -d "{\"elec_index\":\"9999\",\"gas_index\":\"9999\", \"id\":\"92\"}" -X PUT http://localhost:8080/invoice/update
  @PUT
  @Path("/update/")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional
  public Response updateId(Invoice invoice){
    System.out.println("****************************************** update sent data, elec_index = " + invoice.getElec_index() + " gas_index = " + invoice.getGas_index() + " date = " + invoice.getDate() );
	 
	 String id_ = String.valueOf(invoice.getId());
	if(null == id_)return Response.ok("Invoice Id was not set on request").build();
	
	Query query = em.createNamedQuery("Invoice.findId");
	
	//save the values sent in the POST
	String id0          = String.valueOf(invoice.getId());
	String elec_index0  = invoice.getElec_index();
	String gas_index0   = invoice.getGas_index();
	//String date0      = invoice.getDate();
	
	
	Query query_        =  query.setParameter("id", Integer.parseInt(id0));
	System.out.println("****************************************** update sent, id = " + id0 + " elec_index0 = " + elec_index0 + " gas_index0 = " + gas_index0 + " query = " + query_);
	
	List<Invoice> list = query_.getResultList();
	invoice            = (Invoice)list.get(0);
	
	if(invoice == null)return Response.ok("invoice with id = " + id0 + " not found").build();;
	
	//get the current or old values to update.
	String elec_index1  = invoice.getElec_index();
	String gas_index1   = invoice.getGas_index();
	String date1        = invoice.getDate();
	
	System.out.println("****************************************** update data to update, current invoice = " + invoice + " id = " + id0 + " elec_index = " + elec_index1 + " gas_index = " + gas_index1 + " date = " + date1);
	
	//update
	invoice.setElec_index(elec_index0);
	invoice.setGas_index(gas_index0);
	
	//get the final values updated.
	String elec_index2  = invoice.getElec_index();
	String gas_index2   = invoice.getGas_index();
	String date2        = invoice.getDate();
	
	System.out.println("****************************************** update, data updated = " + invoice + " id = " + id0 + " elec_index = " + elec_index2 + " gas_index = " + gas_index2 + " date = " + date2);
	
	em.merge(invoice);
	return Response.ok("update Id = " + id0 + " elec_index = " + elec_index2 + " gas_index = " + gas_index2 + " date = " + date2).build();
  }
  
  
  //  curl -H "Content-Type: application/json" -d "{\"id\":\"92\"}" -X DELETE http://localhost:8080/invoice/delete
  
  @DELETE
  @Path("/delete")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional
    
    public Response delete(Invoice invoice) {
       String id_ = String.valueOf(invoice.getId());
		if(id_ == null)return Response.ok("Invoice Id was not set on request").build();;
	
		Query query   = em.createNamedQuery("Invoice.findId");
		Query query_  =  query.setParameter("id", Integer.parseInt(id_));
	
		List<Invoice> list = query_.getResultList();
		invoice            = (Invoice)list.get(0);
	
		if(invoice == null)return Response.ok("invoice with id = " + id_ + " not found").build();;
		
        em.remove(invoice);
        return Response.ok("Invoice with Id = " + id_ + " successfully deleted").build();
    }
	
  
  /* old comment
  @GET
  @Path("/author")
  @Produces(MediaType.APPLICATION_JSON)
	public Response getListOfBooks(@HeaderParam("Accept-Language") String acceptLanguage,
            @HeaderParam("User-Agent") String userAgent, @HeaderParam("Name") String name){
		return Response.status(200)
                  .entity("User-Agent :- " + userAgent + " \nAccept-Language :- " + acceptLanguage + " \nName :- " + name)
                      .build();
	}
	*/
 
 /*
  @POST
  @Path("/authorization/{message}")
  //@Path("/authorization/titi/{message}")
  //@Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  //@Produces("text/plain")
    public Response valideAutorization(@HeaderParam("Authorization") String authorization, @PathParam("message") String message){
		//public Response valideAutorization(@PathParam("message") String message){
		//return Response.ok("/autorization/titi/ et POST message = " + message + " authorization reçu = " + authorization).build();
		//return Response.status(Response.Status.OK).entity(message).build();
		
		System.out.println("authorization = " + authorization);//authorization = Basic YXplcnR5OnF3ZXJ0eQ==
		
		String identifier[] = authorization.split(" ");
		
		System.out.println("identifier[0] = " + identifier[0].trim() + " identifier[1] = " + identifier[1].trim());
		
        System.out.println("size = " + em.createNamedQuery("Book.findAuthor").setParameter("author", identifier[0].trim()).getResultList().size());
		
		if( em.createNamedQuery("Book.findAuthor").setParameter("author", identifier[0].trim()).getResultList().size()!= 1)return Response.status(Response.Status.EXPECTATION_FAILED).entity("identifier error").build();
		
		Book b = (Book)em.createNamedQuery("Book.findAuthor").setParameter("author", identifier[0].trim()).getResultList().get(0);	
		
		System.out.println("**************** book = " + b);
		
		if(b.equals(null))return Response.status(Response.Status.EXPECTATION_FAILED).entity("identifier error").build();
		
		System.out.println("**************** author = "+b.getAuthor()+" isbn = "+b.getIsbn());
		
		if(identifier[1].trim().equals(b.getIsbn())) return Response.status(Response.Status.OK).entity("success").build();
		
		//if(identifier[0].trim().equals("titi") && identifier[1].trim().equals("toto"))return Response.status(Response.Status.OK).entity("success").build();
			
		return Response.status(Response.Status.EXPECTATION_FAILED).entity("pwd error").build();
		//String json = //convert entity to json
		//return Response.ok(json, MediaType.APPLICATION_JSON).build();	
	}
	*/
	
	/*
   @POST
   @Transactional
    @Path("/upload/{id}")  //Your Path or URL to call this service
    
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes("multipart/form-data")

    public void upload(@MultipartForm MyEntity entity, @PathParam("id") int id) throws IOException {
	
		System.out.println("******************************** entity = "+entity+" id = "+id+" name = "+entity.name+" bytes = "+entity.filedata+" length = "+ entity.filedata.length);
		byte[] filedata = entity.getFiledata();
		System.out.println("******************************** filedata = "+filedata);
		
	    System.out.println("*********************************** entity manager upload image = "+em);
		
		em.persist(entity);
		
		System.out.println("*********************************** entity manager upload id = "+entity.getId());
		//return Response.ok("MultipartForm!************").build();
	
	//JsonObject jsonFile = Json.createObjectBuilder()
                //.add("length", entity.getFile().length)
                //.add("file", entity.getData().toString())
                //.build();
 
        //return Response.ok(jsonFile).build();
    }
	*/
	
	/*
	@GET
	@Path("/imagesall")
	@Produces(MediaType.APPLICATION_JSON)
	
	public MyEntity[] imagesAll() {
    
	Query query = em.createNamedQuery("MyEntity.findAll");			//em.createQuery("SELECT e FROM MyEntity e");	
	//System.out.println("****************************************** query = "+query);
	
	List<MyEntity> list = query.getResultList();
    
	return list.toArray(new MyEntity[0]);
	
  }
  */
  
  /*
  @GET
	@Path("/imageid/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public MyEntity getImageId(@PathParam("id") int id){
    return (MyEntity)em.createNamedQuery("MyEntity.findId") 
		.setParameter("id", id)
        .getResultList()
        .get(0);
  }
  */
  
  /*
  @GET
	@Path("/imagebyteid/{id}")
	//@Produces(MediaType.APPLICATION_JSON)
	//@Produces("application/octet-stream") // ouverture fenêtre téléchargement
	@Produces("application/html")    //fenêtre téléchargement
	
	public String getImageByteId(@PathParam("id") int id){
		byte[] bytes = getImageId(id)
		              .getFiledata(); 
		
		
		String base64String = Base64.getEncoder().encodeToString(bytes);
		System.out.println("****************************************** base64String = "+base64String);
		return base64String;
		//byte[] backToBytes = Base64.decode(base64String);
		
    }
	*/
}
