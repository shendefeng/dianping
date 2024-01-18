package com.hmdp.utils;

import com.hmdp.dto.UserDTO;

/**
 * 使用了 ThreadLocal 来存储和管理UserDTO信息
 */
public class UserHolder {
    private static final ThreadLocal<UserDTO> tl = new ThreadLocal<>();

    public static void saveUser(UserDTO user){
        tl.set(user);
    }

    public static UserDTO getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
