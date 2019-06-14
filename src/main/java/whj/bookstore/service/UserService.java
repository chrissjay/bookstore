package whj.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whj.bookstore.dao.RoleMapper;
import whj.bookstore.dao.UserMapper;
import whj.bookstore.dao.UserRoleRMapper;
import whj.bookstore.model.Role;
import whj.bookstore.model.User;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleRMapper userRoleRMapper;

    public List<User> getUserInfos(Map<String, Object> paramMap) {
        return userMapper.selectByUserCode(paramMap.get("userCode").toString());
    }

    public List<Role> getRoleInfos(Map<String, Object> paramMap) {
        String string;
        string = paramMap.get("userCode").toString();
        String roleCode;
        roleCode = userRoleRMapper.selectByUserCode(string);
        return roleMapper.selectByRoleCode(roleCode);
    }

    public User getUser(int uid) {
        return userMapper.selectByPrimaryKey(uid);
    }

    public User getNowUser(String userCode) {
        return userMapper.selectOneByUserCode(userCode);
    }

    public void registerUser(String userCode, String userPwd, String userName, String userAddr, String userPhone) {
        User user = new User();
        user.setUserCode(userCode);
        user.setUserPwd(userPwd);
        user.setUserName(userName);
        user.setUserAddr(userAddr);
        user.setUserPhone(userPhone);
        userMapper.insert(user);
    }

}
