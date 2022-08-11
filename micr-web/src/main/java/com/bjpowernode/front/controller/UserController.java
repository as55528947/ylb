package com.bjpowernode.front.controller;

import com.bjpowernode.api.model.RechargeRecord;
import com.bjpowernode.api.model.User;
import com.bjpowernode.api.pojo.UserAccountInfo;
import com.bjpowernode.api.pojo.UserCenterBidPage;
import com.bjpowernode.enums.RCode;
import com.bjpowernode.front.service.RealnameServiceImpl;
import com.bjpowernode.front.service.SmsService;
import com.bjpowernode.front.view.RespResult;
import com.bjpowernode.front.view.ResultView;
import com.bjpowernode.front.vo.RealnameVo;
import com.bjpowernode.util.CommonUtil;
import com.bjpowernode.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动力节点乌兹
 * 2022-4-28
 */
@Api(tags = "用户功能")
@RestController
@RequestMapping("/v1/user")

public class UserController extends BaseController {


    @Resource(name = "SmsCodeRegisterImpl")
    private SmsService registerSmsService;

    @Resource(name = "SmsCodeLoginImpl")
    private SmsService loginSmsService;

    @Resource
    private RealnameServiceImpl realnameService;


    @Resource
    private JwtUtil jwtUtil;

    @ApiOperation(value = "查询手机是否注册过", notes = "在注册功能中，判断手机号是否可以注册")
    @ApiImplicitParam(name = "phone", value = "手机号")
    @GetMapping("/phone/exists")
    public RespResult phoneExists(@RequestParam("phone") String phone) {
        RespResult result = RespResult.fail();
        User user = new User();
        //检查请求参数是否符合要求
        if (CommonUtil.checkPhone(phone)) {
            user = userService.queryByPhone(phone);
            if (user == null) {
                //该手机号在用户表没有用户记录，可以注册

                result = RespResult.ok();

                //把手机号存到redis。然后检查手机号是否存在，可以查redis
            } else {
                result.setCode(RCode.PHONE_EXISTS);
            }
        } else {
            result.setCode(RCode.PHONE_FORMAT_ERR);
        }

        return result;
    }

    @ApiOperation(value = "手机号注册用户")
    @ApiImplicitParam(name = "phone", value = "手机号")
    @PostMapping("/register")
    public RespResult userRegister(@RequestParam String phone,
                                   @RequestParam String pword,
                                   @RequestParam String scode) {
        RespResult result = RespResult.fail();
        //检查参数
        if (CommonUtil.checkPhone(phone)) {

            //短信验证码由前端Md5加密为32位
            if (pword != null && pword.length() == 32) {
                //检查短信验证码
                if (registerSmsService.checkSmsCode(phone, scode)) {
                    //可以注册
                    int registerResult = userService.userRegister(phone, pword);
                    if (registerResult == 1) {
                        result = RespResult.ok();
                    } else if (registerResult == 2) {
                        result.setCode(RCode.PHONE_EXISTS);
                    } else {
                        result.setCode(RCode.REQUEST_PARAM_ERR);
                    }
                } else {
                    //短信验证码无效
                    result.setCode(RCode.SMS_CODE_INVALID);
                }
            } else {
                result.setCode(RCode.REQUEST_PARAM_ERR);
            }
        } else {
            //手机号格式不正确
            result.setCode(RCode.PHONE_FORMAT_ERR);
        }
        return result;
    }

    @ApiOperation(value = "用户登录-获取访问token")
    @PostMapping("/login")
    public RespResult userLogin(@RequestParam String phone,
                                @RequestParam String pword,
                                @RequestParam String scode) throws Exception {
        RespResult result = RespResult.fail();
        //验证手机号  密码数据是否有效
        if (CommonUtil.checkPhone(phone) && (pword != null && pword.length() == 32)) {
            //查询redis中该phone手机号的验证码是否存在
            if (loginSmsService.checkSmsCode(phone, scode)) {
                //验证码存在  登录
                User user = userService.userLogin(phone, pword);
                if (user != null) {
                    //登陆成功,生成token
                    Map<String, Object> data = new HashMap<>();
                    data.put("id", user.getId());
                    String jwtToken = jwtUtil.createJwt(data, 120);

                    result = RespResult.ok();
                    result.setAccessToken(jwtToken);

                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("uid", user.getId());
                    userInfo.put("phone", user.getPhone());
                    userInfo.put("name", user.getName());
                    result.setData(userInfo);
                } else {
                    result.setCode(RCode.PHONE_LOGIN_PASSWORD_INVALID);
                }
            } else {

            }
        } else {
            result.setCode(RCode.REQUEST_PARAM_ERR);
        }


        return result;
    }

    @ApiOperation(value = "实名认证", notes = "提供手机号、姓名、身份证号   认证姓名和身份证是否真实")
    @PostMapping("/realname")
    public RespResult userRealname(@RequestBody RealnameVo realnameVo) {
        RespResult result = RespResult.fail();
        result.setCode(RCode.REQUEST_PARAM_ERR);
        //验证请求参数
        if (CommonUtil.checkPhone(realnameVo.getPhone())) {
            if (StringUtils.isNotBlank(realnameVo.getName()) && StringUtils.isNotBlank(realnameVo.getIdCard())) {
                //判断用户已经做过实名认证
                User user = userService.queryByPhone(realnameVo.getPhone());
                if (user != null) {
                    if (StringUtils.isNotBlank(user.getName())) {
                        result.setCode(RCode.REALNAME_RETRY);
                    } else {

                        //此处应有短信验证码校验   重复功能省略

                        //不含空数据   调用第三方接口,判断认证结果
                        boolean realnameResult = realnameService.handleRealname(realnameVo.getPhone(), realnameVo.getName(), realnameVo.getIdCard());
                        //User userInfo = userService.queryByPhone(realnameVo.getPhone());
                        if (realnameResult == true) {
                           // result.setData(userInfo);
                            result = RespResult.ok();
                        } else {
                            result.setCode(RCode.REALNAME_FAIL);
                        }
                    }

                }
            }
        }

        return result;
    }

    @ApiOperation(value = "用户中心")
    @GetMapping("/userCenter")
    public RespResult userCenter(@RequestHeader(required = false,value = "uid") Integer uid,
                                 @RequestParam(required = false,defaultValue = "1") Integer pageNo,
                                 @RequestParam(required = false,defaultValue = "6")  Integer pageSize){
        RespResult result = RespResult.fail();
        if (uid != null && uid > 0){
            UserAccountInfo userAccountInfo = userService.queryUserAllInfo(uid);
            if (userAccountInfo != null){
                result = RespResult.ok();
                //该map存放用户基本信息
                Map<String,Object> data = new HashMap<>();
                //该map存放用户最近投资  最近充值  最近收益的List
                Map<String,List> page = new HashMap<>();
                data.put("name", userAccountInfo.getName());
                data.put("phone", userAccountInfo.getPhone());
                data.put("money", userAccountInfo.getAvailableMoney());
                data.put("HeaderUrl", userAccountInfo.getHeaderImage());
                if (userAccountInfo.getLastLoginTime() != null){
                    data.put("loginTime", DateFormatUtils.format(userAccountInfo.getLastLoginTime(), "yyyy-MM-dd HH:mm:ss"));
                }else {
                    data.put("loginTime","");
                }
                result.setData(data);
                //用户中心  充值记录
                List<RechargeRecord> records = rechargeService.queryByUid(uid, pageNo, pageSize);
                page.put("rechargeList",toView(records));

                //用户中心 投资记录
                int newPNo = 0;
                int newPSize = 0;
                int offset = 0;
                newPNo = CommonUtil.defaultPageNo(pageNo);
                newPSize = CommonUtil.defaultPageSize(pageSize);
                //根据页数计算数据偏移量
                offset = (pageNo - 1) * newPSize;
                List<UserCenterBidPage> bidInfoList = investService.queryPageByUid(uid,offset,newPSize);
                page.put("bidInfoList", bidInfoList);
                result.setMap(page);
            }
        }

        return result;
    }

    private List<ResultView> toView(List<RechargeRecord> src){
        List<ResultView> target = new ArrayList<>();
        src.forEach(record ->{
            target.add(new ResultView(record));
        });
        return target;
    }



























}
