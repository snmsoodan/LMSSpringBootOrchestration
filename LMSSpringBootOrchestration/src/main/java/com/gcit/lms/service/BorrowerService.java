package com.gcit.lms.service;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;

@RestController
public class BorrowerService {
	
	@Autowired
	RestTemplate restTemplate;
	
	//BookLoan Operations
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/bookLoan",method=RequestMethod.POST)
	public HttpHeaders  saveBookLoan(@RequestBody BookLoans bookLoan, HttpServletResponse response) throws IOException
	{
		try {
			ResponseEntity<BookLoans> responseEntity = restTemplate.postForEntity("http://localhost:8082/bookLoan", bookLoan, BookLoans.class);
			response.setStatus(201);
			return responseEntity.getHeaders();
		} catch (RestClientException e) {
			response.sendError(400, "Invalid request caused by invalid body parameters.");
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/bookLoans",method=RequestMethod.PUT)
	public HttpHeaders  returnBookLoan(@RequestParam("bookId") Integer bookId,@RequestParam("branchId") Integer branchId,@RequestParam("cardNo") Integer cardNo,@RequestBody BookLoans bookLoan, @RequestHeader HttpHeaders headers, HttpServletResponse response) throws IOException //this method updates dateIn 
	{
		try {
			HttpEntity<BookLoans> requestUpdate = new HttpEntity<>(bookLoan, headers);
			ResponseEntity<BookLoans> responseEntity = restTemplate.exchange("http://localhost:8082/bookLoans?bookId="+bookId+"&branchId="+branchId+"&cardNo="+cardNo , HttpMethod.PUT, requestUpdate, BookLoans.class);
			return responseEntity.getHeaders();
		} catch (RestClientException e) {
			response.sendError(400, "Invalid request caused by invalid body parameters.");		
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/bookLoans/dueDate",method=RequestMethod.PUT)
	public HttpHeaders changeDueDate(@RequestParam("bookId") Integer bookId,@RequestParam("branchId") Integer branchId,@RequestParam("cardNo") Integer cardNo,@RequestBody BookLoans bookloan, @RequestHeader HttpHeaders headers, HttpServletResponse response) throws IOException
	{	
		
		try {
			HttpEntity<BookLoans> requestUpdate = new HttpEntity<>(bookloan, headers);
			ResponseEntity<BookLoans> responseEntity = restTemplate.exchange("http://localhost:8082/bookLoans/dueDate?bookId="+bookId+"&branchId="+branchId+"&cardNo="+cardNo , HttpMethod.PUT, requestUpdate, BookLoans.class);
			return responseEntity.getHeaders();
		} catch (RestClientException e) {
			response.sendError(400, "Invalid request caused by invalid body parameters.");		
			return null;
		}
	}
	
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/bookLoans/user/{cardNo}",method=RequestMethod.GET)
	public BookLoans[] ReadBookLoansByUserBranch(@PathVariable("cardNo") Integer cardNo, HttpServletResponse response) throws IOException 
	{
		try {
			ResponseEntity<BookLoans[]> responseEntity = restTemplate.getForEntity("http://localhost:8082/bookLoans/user/" + cardNo , BookLoans[].class);
			BookLoans[] bookloans = responseEntity.getBody();
			return bookloans;
		} catch (RestClientException e) {
			
			response.sendError(404, "Invalid id, author does not exist in the database.");
			return null;
		}
	}
	
	
	
	//BookCopies
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/bookCopies",method=RequestMethod.PUT)
	public HttpHeaders loanBookCopies(@RequestParam("bookId") Integer bookId,@RequestParam("branchId") Integer branchId,@RequestBody BookCopies bookCopy, @RequestHeader HttpHeaders headers, HttpServletResponse response) throws IOException  //this method updates noOfCopies
	{
		try {
			HttpEntity<BookCopies> requestUpdate = new HttpEntity<>(bookCopy, headers);
			ResponseEntity<BookCopies> responseEntity = restTemplate.exchange("http://localhost:8082/bookCopies?bookId="+bookId+"&branchId="+branchId, HttpMethod.PUT, requestUpdate, BookCopies.class);
			return responseEntity.getHeaders();
		} catch (RestClientException e) {
			response.sendError(400, "Invalid request caused by invalid body parameters.");		
			return null;
		}
	}
	
	
	//Books
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/books/{bookId}",method=RequestMethod.GET)
	public Book[] ReadBookByBookID(@PathVariable("bookId") Integer bookId, HttpServletResponse response) throws IOException   //for return book
	{
		try {
			ResponseEntity<Book[]> responseEntity = restTemplate.getForEntity("http://localhost:8082/books/" + bookId , Book[].class);
			Book[] books = responseEntity.getBody();
			return books;
		} catch (RestClientException e) {
			
			response.sendError(404, "Invalid id, author does not exist in the database.");
			return null;
		}
	}
	
	
	
	//Borrower
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/borrowers",method=RequestMethod.GET)
	public Borrower[] readBorrower(HttpServletResponse response) throws IOException 
	{
		try {
			ResponseEntity<Borrower[]> responseEntity = restTemplate.getForEntity("http://localhost:8082/borrowers", Borrower[].class);
			Borrower[] borrowers = responseEntity.getBody();
			return borrowers;
		} catch (RestClientException e) {
			response.sendError(404, "Invalid URL");
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/borrowers/name/{name}",method=RequestMethod.GET)
	public Borrower[] readBorrowerByName(@PathVariable("name") String name, HttpServletResponse response) throws IOException
	{
		try {
			ResponseEntity<Borrower[]> responseEntity = restTemplate.getForEntity("http://localhost:8082/borrowers/name/" + name , Borrower[].class);
			Borrower[] borrowers = responseEntity.getBody();
			return borrowers;
		} catch (RestClientException e) {
			
			response.sendError(404, "Invalid id, author does not exist in the database.");
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/borrowers/{cardNo}",method=RequestMethod.GET)
	public Borrower[] readBorrowerById(@PathVariable("cardNo") Integer cardNo, HttpServletResponse response) throws IOException
	{
		try {
			ResponseEntity<Borrower[]> responseEntity = restTemplate.getForEntity("http://localhost:8082/borrowers/" + cardNo , Borrower[].class);
			Borrower[] borrowers = responseEntity.getBody();
			return borrowers;
		} catch (RestClientException e) {
			
			response.sendError(404, "Invalid id, author does not exist in the database.");
			return null;
		}
	}
	
	
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/borrowers/{cardNo}",method=RequestMethod.PUT)
	public HttpHeaders  updateBorrower(@PathVariable("cardNo") Integer cardNo,@RequestBody Borrower borrower, @RequestHeader HttpHeaders headers, HttpServletResponse response) throws IOException
	{
		try {
			HttpEntity<Borrower> requestUpdate = new HttpEntity<>(borrower, headers);
			ResponseEntity<Borrower> responseEntity = restTemplate.exchange("http://localhost:8082/borrowers/" + cardNo, HttpMethod.PUT, requestUpdate, Borrower.class);
			return responseEntity.getHeaders();
		} catch (RestClientException e) {
			response.sendError(400, "Invalid request caused by invalid body parameters.");		
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/borrowers/{cardNo}",method=RequestMethod.DELETE)
	public void deleteBorrower(@PathVariable("cardNo") Integer cardNo, HttpServletResponse response) throws IOException
	{
		try {
			restTemplate.delete("http://localhost:8082/borrowers/" + cardNo);
			response.setStatus(204);
		} catch (RestClientException e) {
			response.sendError(404, "Invalid id, author does not exist in database.");
		}
	}
	
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/borrower",method=RequestMethod.POST)
	public HttpHeaders  saveBorrower(@RequestBody Borrower borrower, HttpServletResponse response) throws IOException 
	{
		try {
			ResponseEntity<Borrower> responseEntity = restTemplate.postForEntity("http://localhost:8082/borrower", borrower, Borrower.class);
			response.setStatus(201);
			return responseEntity.getHeaders();
		} catch (RestClientException e) {
			response.sendError(400, "Invalid request caused by invalid body parameters.");
			return null;
		}
	}

}
