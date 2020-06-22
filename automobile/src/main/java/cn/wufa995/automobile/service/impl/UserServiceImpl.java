/**
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
 */
package cn.wufa995.automobile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wufa995.web.repository.BaseRepository;
import cn.wufa995.web.service.impl.BaseServiceImpl;
import cn.wufa995.automobile.repository.UserRepository;
import cn.wufa995.automobile.service.UserService;
import cn.wufa995.automobile.entity.User;

/**
 * @description:
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
 * @version: V1.0
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public BaseRepository getRepository() {
        return userRepository;
    }
}