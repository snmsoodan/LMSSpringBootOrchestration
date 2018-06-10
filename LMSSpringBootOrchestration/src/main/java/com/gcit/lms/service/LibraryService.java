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

import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.LibraryBranch;

@RestController
public class LibraryService {
	

	@Autowired
	RestTemplate restTemplate;
	
	// BookCopy operations
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/bookCopy",method=RequestMethod.POST)
	public HttpHeaders saveBookCopy(@RequestBody BookCopies bookCopy, HttpServletResponse response) throws IOException
	{
		try {
			ResponseEntity<BookCopies> responseEntity = restTemplate.postForEntity("http://localhost:8083/bookCopy", bookCopy, BookCopies.class);
			response.setStatus(201);
			return responseEntity.getHeaders();
		} catch (RestClientException e) {
			response.sendError(400, "Invalid request caused by invalid body parameters.");
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/bookCopies",method=RequestMethod.GET)
	public BookCopies[] readBookCopiesById(@RequestParam("bookId") Integer bookId,@RequestParam("branchId") Integer branchId, HttpServletResponse response) throws IOException
	{
		try {
			ResponseEntity<BookCopies[]> responseEntity = restTemplate.getForEntity("http://localhost:8083/bookCopies?bookId="+bookId+"&branchId="+branchId , BookCopies[].class);
			BookCopies[] bookCopies = responseEntity.getBody();
			return bookCopies;
		} catch (RestClientException e) {
			
			response.sendError(404, "Invalid id, author does not exist in the database.");
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/bookCopies/{bookId}",method=RequestMethod.PUT)
	public HttpHeaders updateBookCopies(@PathVariable("bookId") Integer bookId,@RequestParam("branchId") Integer branchId,@RequestBody BookCopies bookCopy, @RequestHeader HttpHeaders headers, HttpServletResponse response) throws IOException
	{																				//admin updating noOfCopies
		try {
			HttpEntity<BookCopies> requestUpdate = new HttpEntity<>(bookCopy, headers);
			ResponseEntity<BookCopies> responseEntity = restTemplate.exchange("http://localhost:8083/bookCopies?bookId"+bookId+"&branchId="+branchId, HttpMethod.PUT, requestUpdate, BookCopies.class);
			return responseEntity.getHeaders();
		} catch (RestClientException e) {
			response.sendError(400, "Invalid request caused by invalid body parameters.");		
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/bookCopies/return",method=RequestMethod.PUT)
	public HttpHeaders updateBookCopies2(@RequestBody BookCopies bookCopy, @RequestHeader HttpHeaders headers, HttpServletResponse response) throws IOException  //when returning book
	{
		try {
			HttpEntity<BookCopies> requestUpdate = new HttpEntity<>(bookCopy, headers);
			ResponseEntity<BookCopies> responseEntity = restTemplate.exchange("http://localhost:8083/bookCopies/return", HttpMethod.PUT, requestUpdate, BookCopies.class);
			return responseEntity.getHeaders();
		} catch (RestClientException e) {
			response.sendError(400, "Invalid request caused by invalid body parameters.");		
			return null;
		}
	}
	
	
	//Library Branch Operations
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/libraryBranches/{branchId}",method=RequestMethod.PUT)
	public HttpHeaders updateLibraryBranch(@PathVariable("branchId") Integer branchId,@RequestBody LibraryBranch libraryBranch, @RequestHeader HttpHeaders headers, HttpServletResponse response) throws IOException 
	{
		try {
			HttpEntity<LibraryBranch> requestUpdate = new HttpEntity<>(libraryBranch, headers);
			ResponseEntity<LibraryBranch> responseEntity = restTemplate.exchange("http://localhost:8083/libraryBranches/"+branchId, HttpMethod.PUT, requestUpdate, LibraryBranch.class);
			return responseEntity.getHeaders();
		} catch (RestClientException e) {
			response.sendError(400, "Invalid request caused by invalid body parameters.");		
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/libraryBranches/{branchId}",method=RequestMethod.DELETE)
	public void deleteLibraryBranch(@PathVariable("branchId") Integer branchId, HttpServletResponse response) throws IOException
	{
		try {
			restTemplate.delete("http://localhost:8083/libraryBranches/" + branchId);
			response.setStatus(204);
		} catch (RestClientException e) {
			response.sendError(404, "Invalid id, author does not exist in database.");
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/libraryBranch",method=RequestMethod.POST)
	public HttpHeaders saveLibraryBranch(@RequestBody LibraryBranch libraryBranch, HttpServletResponse response) throws IOException 
	{
		try {
			ResponseEntity<LibraryBranch> responseEntity = restTemplate.postForEntity("http://localhost:8083/libraryBranch", libraryBranch, LibraryBranch.class);
			response.setStatus(201);
			return responseEntity.getHeaders();
		} catch (RestClientException e) {
			response.sendError(400, "Invalid request caused by invalid body parameters.");
			return null;
		}
	}
	
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/libraryBranches",method=RequestMethod.GET)
	public LibraryBranch[] readLibraryBranch(HttpServletResponse response) throws IOException 
	{
		try {
			ResponseEntity<LibraryBranch[]> responseEntity = restTemplate.getForEntity("http://localhost:8083/libraryBranches", LibraryBranch[].class);
			LibraryBranch[] libraryBranches = responseEntity.getBody();
			return libraryBranches;
		} catch (RestClientException e) {
			response.sendError(404, "Invalid URL");
			return null;
		}
	}
	
	@CrossOrigin
	@Transactional
	@RequestMapping(value="/lms/libraryBranches/{branchName}",method=RequestMethod.GET)
	public LibraryBranch[] readLibraryBranchesByName(@PathVariable("branchName") String branchName, HttpServletResponse response) throws IOException
	{
		try {
			ResponseEntity<LibraryBranch[]> responseEntity = restTemplate.getForEntity("http://localhost:8083/libraryBranches/" + branchName , LibraryBranch[].class);
			LibraryBranch[] libraryBranches = responseEntity.getBody();
			return libraryBranches;
		} catch (RestClientException e) {
			
			response.sendError(404, "Invalid id, author does not exist in the database.");
			return null;
		}
	}

}
