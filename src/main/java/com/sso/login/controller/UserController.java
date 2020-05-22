package com.sso.login.controller;

import com.sso.login.encryption.ThreeDES;
import com.sso.login.entity.Permission;
import com.sso.login.entity.RolePermission;
import com.sso.login.entity.User;
import com.sso.login.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "用户信息接口")
@Controller
public class UserController {

    @Resource
    UserService userService;

//    @ControllerLog(description = "获取用户信息")
    @ApiOperation(value = "获取用户信息")
    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUserInfoById(@PathVariable String username) {
        Map<String, Object> map = new HashMap<>(16);
        User user = userService.getUserInfoByName(username);
        map.put("username", user.getUsername());
        map.put("roleId", user.getRoleId());

        RolePermission rolePermission = userService.getPermIdByRoleId(user.getRoleId());
        Permission permission = userService.getPermDetailByPermId(rolePermission.getPermId());
        map.put("permDetail", permission.getPermDetail());

        return map;
    }

    @ApiOperation(value = "拦截后返回登录页的接口")
    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
    public Object toLogin() {
        Map<String, Object> map = new HashMap<>(16);
        map.put("code", 200);
        map.put("msg", "未登录");
        return "/login";
    }

    @ApiOperation(value = "登录测试页面的接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestParam(value = "name") String name,
                        @RequestParam(value = "password")String password, Model model) {
        //  获取Subject
        Subject subject = SecurityUtils.getSubject();
        //  封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        //  执行登录方法
        try {
            subject.login(token);
            return "redirect:/index";
        } catch (UnknownAccountException e) {
            //  登录失败：用户名不存在
            model.addAttribute("msg", "用户名不存在");
            return "/login";
        } catch (IncorrectCredentialsException e) {
            //  登录失败：密码错误
            model.addAttribute("msg", "密码错误");
            return "/login";
        }
    }

    @ApiOperation("登录成功")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public Object index() {
        Map<String, Object> map = new HashMap<>(16);
        map.put("code", 100);
        map.put("msg", "您登录成功");
        return map;
    }

    @ApiOperation(value = "未授权提示")
    @RequestMapping(value = "/noAuth", method = RequestMethod.GET)
    @ResponseBody
    public Object noAuth() {
        Map<String, Object> map = new HashMap<>(16);
        map.put("code", 200);
        map.put("msg", "您没有该授权");
        return map;
    }

    @ApiOperation("根据用户名为密码加密")
    @RequestMapping(value = "/encrypt/{username}", method = RequestMethod.GET)
    @ResponseBody
    public Object updatePasswordWithEncryption(@PathVariable String username) throws Exception {

        //唯一密钥，即key
        final String key = "cf410f84904a44cc8a7f48fc4134e8f9";

//        ThreeDES threeDES = new ThreeDES();
        //加密
//        String str_encrypt = threeDES.encryptThreeDESECB(URLEncoder.encode(str, "UTF-8"), key);
        //解密
//        String str_dencrypt = threeDES.decryptThreeDESECB(str, key);

        ThreeDES threeDES = new ThreeDES();

        User user = userService.getUserInfoByName(username);
        String password = user.getPassword();
        //  密码加密
        password = threeDES.encryptThreeDESECB(URLEncoder.encode(password, "UTF-8"), key);

        int i = userService.updatePasswordWithEncryption(username, password);
        return i == 1? "加密成功":"加密失败";
    }


}

