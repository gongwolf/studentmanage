package edu.nmsu.nmamp.student.dao;


import java.util.List;
import edu.nmsu.nmamp.student.model.UserInfo;

public interface UserInfoDAO {
	public UserInfo findUserInfo(String userName);
    
    // [USER,AMIN,..]
    public List<String> getUserRoles(String userName);
     

}
