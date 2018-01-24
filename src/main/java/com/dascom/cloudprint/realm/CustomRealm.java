package com.dascom.cloudprint.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.dascom.cloudprint.dao.CollectionUsersDao;
import com.dascom.cloudprint.entity.auth.CollectionUsers;
import com.dascom.cloudprint.service.CollectionUsersService;


public class CustomRealm extends AuthorizingRealm {
	
	private CollectionUsersDao collectionUsersDao;
	
	// 设置realm的名称
	@Override
	public void setName(String name) {
		super.setName("customRealm");
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		CollectionUsers  user=(CollectionUsers) principals.getPrimaryPrincipal();
		if(user==null) return null;
		//获取该所拥有的权限
		List<String> permissions= new ArrayList<String>();
		//角色类别
		if(user.getRole()!=null&&user.getRole().getCategory()!=null&&!"".equals(user.getRole().getCategory())){
			String[] list=user.getRole().getCategory().split(",");
			for (String string : list) {
				permissions.add(string);
			}
		}
		//角色详细
		if(user.getRole()!=null&&user.getRole().getRole()!=null&&!"".equals(user.getRole().getRole())){
			String[] list=user.getRole().getRole().split(",");
			for (String string : list) {
				permissions.add(string);
			}
		}
		// simpleAuthorizationInfo.setRoles(user.getRolesName()); 
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addStringPermissions(permissions);
		return simpleAuthorizationInfo;
	}

	//realm的认证方法，从数据库查询用户信息
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {	
		// token是用户输入的用户名和密码 
		// 第一步从token中取出用户名
		String username = (String) token.getPrincipal();

		// 第二步：根据用户输入的userCode从数据库查询
		CollectionUsers user=null;
		try {
			user=collectionUsersDao.findAllByName(username);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		if(user==null) return null;
		// 从数据库查询到密码
		String password = user.getPassword();
		//将activeUser设置simpleAuthenticationInfo
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,password,this.getName());
		return simpleAuthenticationInfo;
	}

	public CollectionUsersDao getCollectionUsersDao() {
		return collectionUsersDao;
	}

	public void setCollectionUsersDao(CollectionUsersDao collectionUsersDao) {
		this.collectionUsersDao = collectionUsersDao;
	}

}
