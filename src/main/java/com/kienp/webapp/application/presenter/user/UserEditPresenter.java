package com.kienp.webapp.application.presenter.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kienp.webapp.application.model.group.GroupJoinOtherList;
import com.kienp.webapp.application.model.user.UserEdit;
import com.kienp.webapp.userservice.dto.entity.Group;
import com.kienp.webapp.userservice.dto.entity.User;
import com.kienp.webapp.userservice.service.GroupService;
import com.kienp.webapp.userservice.service.UserService;

@Component
public class UserEditPresenter{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;	
	
	
	public UserEdit getUserForEdit(Integer id) {	
		return new UserEdit(userService.getUserById(id));				
	}
	
	public String saveUser(UserEdit user) {
		User newUser= userService.saveUser(this.getUserEntity(user));
		return String.format("The user %s have been saved succesfully!", newUser.getUserName());
	}
	
	public List<GroupJoinOtherList> getGroupListJoinUser(Integer userId) {
		List<GroupJoinOtherList> returnList=new ArrayList<GroupJoinOtherList>();		
		for (Group group: groupService.getAllGroup()) {
			GroupJoinOtherList groupJoinUser=new GroupJoinOtherList();
			groupJoinUser.setGroupId(group.getId());
			groupJoinUser.setGroupName(group.getName());
			List<Group>userGroupAll= userService.getUserById(userId).getGroups();
			if (userGroupAll!=null) {
				for (Group userGroup:userGroupAll) {
					if (group.getId()==userGroup.getId()) {
						groupJoinUser.setIsChecked("checked");
						break;
					}
				}
			}
			returnList.add(groupJoinUser);
		}		
		return returnList;
	}	
	
	private User getUserEntity(UserEdit eUser) {
		User user=new User();
		user.setId(eUser.getId());
		user.setUserName(eUser.getUsername());
		user.setPassword(eUser.getPassword());
		user.setFirstName(eUser.getFirstName());
		user.setLastName(eUser.getLastName());
		user.setEmail(eUser.getEmail());
		user.setUserType(eUser.getUserType());
		user.setStatus(eUser.getUserStatus());
		user.setAddress(eUser.getAddress());
		user.setGroups(groupService.getGroupListByIds(eUser.getTblUserGroup()));
		return user;
	} 
	
}
