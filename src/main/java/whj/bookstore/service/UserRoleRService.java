package whj.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whj.bookstore.dao.UserRoleRMapper;
import whj.bookstore.model.UserRoleR;

@Service
public class UserRoleRService {

    @Autowired
    UserRoleRMapper userRoleRMapper;

    public void registerUserRoleR(String userCode) {
        UserRoleR userRoleR = new UserRoleR();
        userRoleR.setUserCode(userCode);
        userRoleR.setRoleCode("guest");
        userRoleRMapper.insert(userRoleR);
    }

}
