package com.foo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import javax.xml.bind.annotation.XmlRootElement;

@Entity
//@Table(name = "REST_DB_ACCESS")
@Table(name = "invoice")
@org.hibernate.annotations.NamedQueries({
    @org.hibernate.annotations.NamedQuery(name = "Invoice.findAll",  query = "SELECT e FROM Invoice e"),
    @org.hibernate.annotations.NamedQuery(name = "Invoice.findDate", query = "SELECT e FROM Invoice e WHERE date = :date"),
	 @org.hibernate.annotations.NamedQuery(name = "Invoice.findId",  query = "SELECT e FROM Invoice e WHERE id = :id")
	
	/*
	@org.hibernate.annotations.NamedQuery(name = "Invoice.findAuthor", query = "SELECT e FROM Invoice e WHERE author = :author"),
	@org.hibernate.annotations.NamedQuery(name = "Invoice.findId", query = "SELECT e FROM Invoice e WHERE id = :id"),
	@org.hibernate.annotations.NamedQuery(name = "Invoice.removeIsbn", query = "DELETE FROM Invoice e WHERE isbn = :isbn"),
	@org.hibernate.annotations.NamedQuery(name = "Invoice.removeAuthor", query = "DELETE FROM Invoice e WHERE author = :author"),
	@org.hibernate.annotations.NamedQuery(name = "Invoice.removeId", query = "DELETE FROM Invoice e WHERE e.id = :id")
	*/

})

@XmlRootElement
public class Invoice {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO) 	//marche avec windows pgsql et serial
  //marche aussi dans container à condition que la table soit vide. Il y a création de : 'Hibernate: create sequence hibernate_sequence start 1 increment 1'
  
  //@GeneratedValue(strategy = GenerationType.IDENTITY)	// marche dans container à condition que la table soit vide. Il n' y a pas cette ligne : 
  // 'Hibernate: create sequence hibernate_sequence start 1 increment 1' comme dans '@SequenceGenerator('.
  
  //@SequenceGenerator(name = "my_seq", sequenceName = "hibernate_sequence", allocationSize = 1, initialValue = 1)	
  //@GeneratedValue(generator = "my_seq")
  //marche avec container car dans le mode 'dev' obtenu avec la commande 'quarkus dev' la table 'invoice' est generée avec 'hibernate_sequence' qu'il faut mettre ci-dessus dans '@SequenceGenerator'
  /*
  Hibernate:

    drop table if exists invoice cascade
Hibernate:

    drop sequence if exists hibernate_sequence
Hibernate: create sequence hibernate_sequence start 1 increment 1
Hibernate:

    create table invoice (
       id int4 not null,
        date varchar(8),
        elec_index varchar(6),
        gas_index varchar(6),
        primary key (id)
    )
  */
  //Faire attention à la valeur de 'start' s'il y a des valeurs insérées, en mode 'dev', dans la table 'invoice 'avec 'import.sql' dans 
  //'#quarkus.hibernate-orm.sql-load-script=import.sql' qui se trouve dans 'application.properties'. En tnir compte dans l'argument 'initialValue' de '@SequenceGenerator'.
  //Si on ne veut pas d'insertion, supprimmer le fichier 'import.sql' et retirer la ligne 'quarkus.hibernate-orm.sql-load-script=import.sql' dans 'application.properties'.
  // ou
  // garder la ligne 'quarkus.hibernate-orm.sql-load-script=import.sql' avec un fichier 'import.sql' vide.
  
  /*
  The JPA provider (e.g. Hibernate) will use the sequence value as a base, and mutiply it with the allocationSize to get the actual ID it will insert. 
  So if next seq value is 11 and allocationSize is 20, the next ID generated will be 220. Usually, you just want your IDs to follow exactly the sequence value, 
  so set allocationSize = the INCREMENT BY of the sequence.
  
  For example, if we define the sequence in database with a INCREMENT BY value of 20, set the allocationsize in SequenceGenerator also to 20. 
  In this case the JPA will not make a call to database until it reaches the next 20 mark while it increments each value by 1 internally. 
  This saves database calls to get the next sequence number each time. The side effect of this is - Whenever the application is redeployed or the server is restarted in between, 
  it'll call database to get the next batch and you'll see jumps in the sequence values. Also we need to make sure the database definition and the application setting to 
  be in-sync which may not be possible all the time as both of them are managed by different groups and you can quickly lose control of. 
  If database value is less than the allocationsize, you'll see PrimaryKey constraint errors due to duplicate values of Id. If the database value is higher than the
  allocationsize, you'll see jumps in the values of Id.
  If the database sequence INCREMENT BY is set to 1 (which is what DBAs generally do), set the allocationSize as also 1 so that they are in-sync but the JPA calls database 
  to get next sequence number each time.

 If you don't want the call to database each time, use GenerationType.IDENTITY strategy and have the @Id value set by database trigger. 
 With GenerationType.IDENTITY as soon as we call em.persist the object is saved to DB and a value to id is assigned to the returned object so we don't have to do a em.merge 
 or em.flush. (This may be JPA provider specific..Not sure)
 
 
 To use an AUTO_INCREMENT or IDENTITY column, you need to use the GenerationType.IDENTITY strategy on the @GeneratedValue annotation.
 
 */
  
  
  private int id;

  @Column(length = 6)
  private String elec_index;
  
  @Column(length = 6)
  private String gas_index;
  
  @Column(length = 8)
  private String date;
  
  //Default constructor
  public Invoice(){}
 
  
  public int getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }

  public String getElec_index() {
    return elec_index;
  }

  public void setElec_index(String elec_index) {
    this.elec_index = elec_index;
  }
  
  public String getGas_index() {
    return gas_index;
  }

  public void setGas_index(String gas_index) {
    this.gas_index = gas_index;
  }
	
	public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
