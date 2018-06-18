package com.gcit.lms.service;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
public class AdminService  {

	
	
	@Autowired
	RestTemplate restTemplate;
	
	//author operations
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/author", method=RequestMethod.POST)
	public HttpHeaders saveAuthor(@RequestBody Author author, HttpServletResponse response) throws IOException
	{
		try {
//			System.out.println(author.getAuthorName());
			ResponseEntity<Author> responseEntity = restTemplate.postForEntity("http://54.185.38.228:8081/author/", author, Author.class);
			response.setStatus(201);
			return responseEntity.getHeaders();
		} catch (RestClientException e) {
			response.sendError(400, "Invalid request caused by invalid body parameters.");
			return null;
		}
	}
	
	

	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/authors",method=RequestMethod.GET)
	public Author[] readAuthor(HttpServletResponse response) throws SQLException, IOException
	{
		try {
			ResponseEntity<Author[]> responseEntity= restTemplate.getForEntity("http://54.185.38.228:8081/authors", Author[].class);
			Author[] authors = responseEntity.getBody();
			return authors;
		} catch (RestClientException e) {
			response.sendError(404, "Invalid URL from server");
			return null;
		}
		
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/authors/{authorName}",method=RequestMethod.GET)
	public Author[] readAuthorsByName(@PathVariable("authorName") String authorName, HttpServletResponse response) throws IOException
	{
		try {
			ResponseEntity<Author[]> responseEntity = restTemplate.getForEntity("http://54.185.38.228:8081/authors/" + authorName , Author[].class);
			Author[] author = responseEntity.getBody();
			return author;
		} catch (RestClientException e) {
			
			response.sendError(404, "Invalid id, author does not exist in the database.");
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/authors/{authorId}",method=RequestMethod.PUT)
	public HttpHeaders updateAuthor(@PathVariable("authorId") Integer authorId,@RequestBody Author author, @RequestHeader HttpHeaders headers, HttpServletResponse response) throws IOException
	{
		System.out.println("outside "+authorId);
		try {
			System.out.println(authorId);
			HttpEntity<Author> requestUpdate = new HttpEntity<>(author, headers);
			ResponseEntity<Author> responseEntity = restTemplate.exchange("http://54.185.38.228:8081/authors/" + authorId, HttpMethod.PUT, requestUpdate, Author.class);
			return responseEntity.getHeaders();
		} catch (RestClientException e) {
			response.sendError(400, "Invalid request caused by invalid body parameters.");		
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/authors/{authorId}",method=RequestMethod.DELETE)
	public void deleteAuthor(@PathVariable("authorId") Integer authorId, HttpServletResponse response) throws IOException
	{
		try {
			restTemplate.delete("http://54.185.38.228:8081/authors/" + authorId);
			response.setStatus(204);
		} catch (RestClientException e) {
			response.sendError(404, "Invalid id, author does not exist in database.");
		}
	}
	
		
	//genre operations
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/genre", method=RequestMethod.POST)
	public HttpHeaders saveGenre(@RequestBody Genre genre, HttpServletResponse response) throws  IOException
	{
		try {
			ResponseEntity<Genre> responseEntity = restTemplate.postForEntity("http://54.185.38.228:8081/genre", genre, Genre.class);
			response.setStatus(201);
			return responseEntity.getHeaders();
		} catch (RestClientException e) {
			response.sendError(400, "Invalid request caused by invalid body parameters.");
			return null;
		}
	}
	

	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/genres",method=RequestMethod.GET)
	public Genre[] readGenre(HttpServletResponse response) throws SQLException,IOException
	{
		try {
			ResponseEntity<Genre[]> responseEntity= restTemplate.getForEntity("http://54.185.38.228:8081/genres", Genre[].class);
			Genre[] genres = responseEntity.getBody();
			return genres;
		} catch (RestClientException e) {
			response.sendError(404, "Invalid URL");
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/genres/{genre_name}",method=RequestMethod.GET)
	public Genre[] readGenresByName(@PathVariable("genre_name") String genre_name, HttpServletResponse response) throws IOException
	{
		try {
			ResponseEntity<Genre[]> responseEntity = restTemplate.getForEntity("http://54.185.38.228:8081/genres/" + genre_name , Genre[].class);
			Genre[] genres = responseEntity.getBody();
			return genres;
		} catch (RestClientException e) {
			
			response.sendError(404, "Invalid id, author does not exist in the database.");
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/genres/{genre_Id}",method=RequestMethod.PUT)
	public HttpHeaders  updateGenre(@PathVariable("genre_Id") Integer genre_Id,@RequestBody Genre genre, @RequestHeader HttpHeaders headers, HttpServletResponse response) throws IOException 
	{
		try {
			HttpEntity<Genre> requestUpdate = new HttpEntity<>(genre, headers);
			ResponseEntity<Genre> responseEntity = restTemplate.exchange("http://54.185.38.228:8081/genres/" + genre_Id, HttpMethod.PUT, requestUpdate, Genre.class);
			return responseEntity.getHeaders();
		} catch (RestClientException e) {
			response.sendError(400, "Invalid request caused by invalid body parameters.");		
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/genres/{genre_Id}",method=RequestMethod.DELETE)
	public void deleteGenre(@PathVariable("genre_Id") Integer genre_Id, HttpServletResponse response) throws IOException
	{
		try {
			System.out.println(genre_Id);
			restTemplate.delete("http://54.185.38.228:8081/genres/" + genre_Id);
			response.setStatus(204);
		} catch (RestClientException e) {
			response.sendError(404, "Invalid id, author does not exist in database.");
		}
	}
	
	
	//Book Operations
	
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/book",method=RequestMethod.POST)
	public HttpHeaders saveBook(@RequestBody Book book, HttpServletResponse response) throws  IOException
	{
		try {
			ResponseEntity<Book> responseEntity = restTemplate.postForEntity("http://54.185.38.228:8081/book", book, Book.class);
			response.setStatus(201);
			return responseEntity.getHeaders();
		} catch (RestClientException e) {
			response.sendError(400, "Invalid request caused by invalid body parameters.");
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/books",method=RequestMethod.GET)
	public Book[] readBook(HttpServletResponse response) throws IOException
	{
		try {
			ResponseEntity<Book[]> responseEntity = restTemplate.getForEntity("http://54.185.38.228:8081/books", Book[].class);
			Book[] books = responseEntity.getBody();
			return books;
		} catch (RestClientException e) {
			response.sendError(404, "Invalid URL");
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/books/title/{title}",method=RequestMethod.GET)
	public Book[] readBookByName(@PathVariable("title") String title, HttpServletResponse response) throws IOException
	{
		try {
			System.out.println(title);
			ResponseEntity<Book[]> responseEntity = restTemplate.getForEntity("http://54.185.38.228:8081/books/title/" + title , Book[].class);
			Book[] books = responseEntity.getBody();
			return books;
		} catch (RestClientException e) {
			
			response.sendError(404, "Invalid name, author does not exist in the database.");
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/books/{bookId}",method=RequestMethod.PUT)
	public HttpHeaders updateBook(@PathVariable("bookId") Integer bookId,@RequestBody Book book, @RequestHeader HttpHeaders headers, HttpServletResponse response) throws IOException
	{
		try {
			HttpEntity<Book> requestUpdate = new HttpEntity<>(book, headers);
			ResponseEntity<Book> responseEntity = restTemplate.exchange("http://54.185.38.228:8081/books/" + bookId, HttpMethod.PUT, requestUpdate, Book.class);
			return responseEntity.getHeaders();
		} catch (RestClientException e) {
			response.sendError(400, "Invalid request caused by invalid body parameters.");		
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/books/{bookId}",method=RequestMethod.DELETE)
	public void deleteBook(@PathVariable("bookId") Integer bookId, HttpServletResponse response) throws IOException
	{
		try {
			restTemplate.delete("http://54.185.38.228:8081/books/" + bookId);
			response.setStatus(204);
		} catch (RestClientException e) {
			response.sendError(404, "Invalid id, author does not exist in database.");
		}
	}
	
	
	
	//publisher operations

	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/publisher",method=RequestMethod.POST)
	public HttpHeaders savePublisher(@RequestBody Publisher publisher, HttpServletResponse response) throws IOException 
	{
		try {
			ResponseEntity<Publisher> responseEntity = restTemplate.postForEntity("http://54.185.38.228:8081/publisher", publisher, Publisher.class);
			response.setStatus(201);
			return responseEntity.getHeaders();
		} catch (RestClientException e) {
			response.sendError(400, "Invalid request caused by invalid body parameters.");
			return null;
		}
	}
	

	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/publishers",method=RequestMethod.GET)
	public Publisher[] readPublisher(HttpServletResponse response) throws IOException
	{
		try {
			ResponseEntity<Publisher[]> responseEntity = restTemplate.getForEntity("http://54.185.38.228:8081/publishers", Publisher[].class);
			Publisher[] publishers = responseEntity.getBody();
			return publishers;
		} catch (RestClientException e) {
			response.sendError(404, "Invalid URL");
			return null;
		}
	}
	
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/publishers/{publisherName}",method=RequestMethod.GET)
	public Publisher[] readPublisherByNme(@PathVariable("publisherName") String publisherName, HttpServletResponse response) throws IOException
	{
		try {
			ResponseEntity<Publisher[]> responseEntity = restTemplate.getForEntity("http://54.185.38.228:8081/publishers/" + publisherName , Publisher[].class);
			Publisher[] publishers = responseEntity.getBody();
			return publishers;
		} catch (RestClientException e) {
			
			response.sendError(404, "Invalid name, author does not exist in the database.");
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/publishers/{publisherId}",method=RequestMethod.PUT)
	public HttpHeaders updatePublisher(@PathVariable("publisherId") Integer publisherId,@RequestBody Publisher publisher, @RequestHeader HttpHeaders headers, HttpServletResponse response) throws IOException
	{
		try {
			HttpEntity<Publisher> requestUpdate = new HttpEntity<>(publisher, headers);
			ResponseEntity<Publisher> responseEntity = restTemplate.exchange("http://54.185.38.228:8081/publishers/" + publisherId, HttpMethod.PUT, requestUpdate, Publisher.class);
			return responseEntity.getHeaders();
		} catch (RestClientException e) {
			response.sendError(400, "Invalid request caused by invalid body parameters.");		
			return null;
		}
	}
	

	
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/publishers/{publisherId}",method=RequestMethod.DELETE)
	public void deletePublisher(@PathVariable("publisherId") Integer publisherId, HttpServletResponse response) throws IOException
	{
		try {
			restTemplate.delete("http://54.185.38.228:8081/publishers/" + publisherId);
			response.setStatus(204);
		} catch (RestClientException e) {
			response.sendError(404, "Invalid id, author does not exist in database.");
		}
	}
	

	
	
	//BookLoans Operations

	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/bookLoans",method=RequestMethod.GET)
	public BookLoans[] readAllBookLoans(HttpServletResponse response) throws IOException
	{
		try {
			ResponseEntity<BookLoans[]> responseEntity = restTemplate.getForEntity("http://54.185.38.228:8081/bookLoans", BookLoans[].class);
			BookLoans[] bookLoans = responseEntity.getBody();
			return bookLoans;
		} catch (RestClientException e) {
			response.sendError(404, "Invalid URL");
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/bookLoans/{cardNo}",method=RequestMethod.GET)
	public BookLoans[] readBookLoansByUserId(@PathVariable("cardNo") Integer cardNo, HttpServletResponse response) throws IOException
	{
		try {
			ResponseEntity<BookLoans[]> responseEntity = restTemplate.getForEntity("http://54.185.38.228:8081/bookLoans/" + cardNo , BookLoans[].class);
			BookLoans[] bookLoans = responseEntity.getBody();
			return bookLoans;
		} catch (RestClientException e) {
			
			response.sendError(404, "Invalid id, author does not exist in the database.");
			return null;
		}
	}
	
	
	

	
}
