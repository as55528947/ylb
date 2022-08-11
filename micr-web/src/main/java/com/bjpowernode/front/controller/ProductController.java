package com.bjpowernode.front.controller;

import com.bjpowernode.api.model.BidInfo;
import com.bjpowernode.api.model.ProductInfo;
import com.bjpowernode.api.pojo.BidInfoProduct;
import com.bjpowernode.api.pojo.MultiProduct;
import com.bjpowernode.enums.RCode;
import com.bjpowernode.front.view.PageInfo;
import com.bjpowernode.front.view.RespResult;
import com.bjpowernode.util.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 动力节点乌兹
 * 2022-4-20
 */
@Api(tags = "理财产品功能")
@RestController
@RequestMapping("/v1")
public class ProductController extends BaseController {
    @ApiOperation(value = "首页三类产品列表",notes = "一个新手宝，三个优选，三个散标")
    @GetMapping("/product/index")
    public RespResult queryProductIndex(){
        RespResult result = RespResult.ok();
        MultiProduct multiProduct = productService.queryIndexPageProduct();
        result.setData(multiProduct);

        return result;
    }

    /*按产品类型分页查询*/
    @ApiOperation(value = "产品分页查询",notes = "按产品类型分页查询")
    @GetMapping("/product/list")
    public RespResult queryProductBytYPE(@RequestParam("ptype") Integer pType,
                                         @RequestParam(value = "pageNo",required = false,defaultValue = "1") Integer pageNo,
                                         @RequestParam(value = "pageSize",required = false,defaultValue = "9")Integer pageSize){
        RespResult result = RespResult.fail();
        if (pType != null &&(pType == 0||pType == 1||pType ==2)){
            pageNo = CommonUtil.defaultPageNo(pageNo);
            pageSize = CommonUtil.defaultPageSize(pageSize);
            //分页查询，记录总数
            Integer recordNums = productService.queryRecordNumsByType(pType);
            if (recordNums > 0){
                //产品集合
                List<ProductInfo> productInfos = productService.queryByTypeLimit(pType, pageNo, pageSize);

                //构建pageInfo
                PageInfo pageInfo = new PageInfo(pageNo, pageSize, recordNums);
                result = RespResult.ok();
                result.setPage(pageInfo);
                result.setList(productInfos);

            }
        }else {
            //请求参数有误
            result.setCode(RCode.REQUEST_PRODUCT_TYPE_ERR);
        }
        return result;
    }

    @ApiOperation(value = "产品详情", notes = "查询某个产品的详细信息和5条时间最近的投资记录")
    @GetMapping("/product/info")
    public RespResult queryProductDetail(Integer pid){
        RespResult result = RespResult.fail();
        if (pid != null && pid > 0){
            //查询该类产品详情
            ProductInfo productInfo = productService.queryProductByPid(pid);
            //如果该产品存在，继续查询产品投资记录
            if (productInfo != null){
                List<BidInfoProduct> bidInfoProducts = investService.queryBidListByProductId(pid, 1, 5);
                result = RespResult.ok();
                result.setData(productInfo);
                result.setList(bidInfoProducts);
            }else {
                result.setCode(RCode.PRODUCT_OFFLINE);
            }

        }
        return result;
    }

}
