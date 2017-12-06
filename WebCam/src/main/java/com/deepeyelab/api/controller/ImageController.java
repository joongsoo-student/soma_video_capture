package com.deepeyelab.api.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deepeyelab.storage.StorageManager;

/**
 * 
 * @author Daniel
 */
@RestController
public class ImageController {
	
	@RequestMapping(value="/api/image", method=RequestMethod.GET)
	public ResponseEntity<List<String>> imageInfo() {
		return new ResponseEntity<>(StorageManager.fileList("odin/acro"), HttpStatus.OK);
	}
	
}