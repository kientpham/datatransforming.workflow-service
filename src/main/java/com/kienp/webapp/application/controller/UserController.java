package com.kienp.webapp.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kienp.webapp.application.connector.user.UserEditInteractor;
import com.kienp.webapp.application.connector.user.UserListInteractor;
import com.kienp.webapp.application.model.datatable.PaginationCriteria;
import com.kienp.webapp.application.model.datatable.TablePage;
import com.kienp.webapp.application.model.user.GroupListJoinUser;
import com.kienp.webapp.application.model.user.UserDataTable;
import com.kienp.webapp.application.model.user.UserEdit;
import com.kienp.webapp.application.model.user.UserSearch;


@RestController
public class UserController {

	@Autowired
	private UserListInteractor userInteractor;
	
	@Autowired
	private UserEditInteractor userEditInteractor;

	@RequestMapping(value = "/user_paginated", method = RequestMethod.POST)
	@ResponseBody
	public TablePage listUsers(@RequestBody PaginationCriteria paginationCriteria) {		
		return userInteractor.getUsersListPaging(paginationCriteria);
	}

	@RequestMapping(value = "/getUserDataTable", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<UserDataTable> getUserTable(@RequestBody UserSearch user) {
		return userInteractor.getUserList(user);
	}

	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	public ResponseEntity<UserEdit> getUser(@RequestParam(value = "id") Integer id) {
		// return userService.getUserForEdit(id);
		ResponseEntity<UserEdit> entity = new ResponseEntity<UserEdit>(userEditInteractor.getUserForEdit(id), HttpStatus.OK);
		return entity;
	}

	@RequestMapping(value = "/getUserSearchForm", method = RequestMethod.GET)
	public ResponseEntity<UserSearch> getUserSearchForm(@RequestParam(value = "id") Integer id) {
		ResponseEntity<UserSearch> entity = new ResponseEntity<UserSearch>(new UserSearch(), HttpStatus.OK);
		return entity;
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ResponseEntity<String> addAccount(@RequestBody(required = true) UserEdit userEdit) {
		return new ResponseEntity<String>(userEditInteractor.saveUser(userEdit), HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteUsers", method = RequestMethod.POST)
	public String deleteUsers(@RequestBody(required = true) List<Integer> ids) {
		return userInteractor.deleteUsers(ids);
		// return "The accounts have been deleted successfully!";
	}

	@RequestMapping(value = "/getGroupUserList", method = RequestMethod.POST)
	public List<GroupListJoinUser> getGroupListJoinUser(@RequestBody(required = true) int id) {
		return userEditInteractor.getGroupListJoinUser(id);
	}

}
