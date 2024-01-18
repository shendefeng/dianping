package com.hmdp.service.impl;

import cn.hutool.json.JSONUtil;
import com.hmdp.dto.Result;
import com.hmdp.entity.ShopType;
import com.hmdp.mapper.ShopTypeMapper;
import com.hmdp.service.IShopTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hmdp.utils.RedisConstants.CACHE_SHOP_TYPE_KEY;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result queryList() {
        // 这里Key是CACHE_SHOP_TYPE_KEY, value是list类型,
        //先从Redis中查，这里的常量值是固定前缀 + 店铺id
        List<String> shopTypeList =
                stringRedisTemplate.opsForList().range(CACHE_SHOP_TYPE_KEY, 0, -1);
        //如果有，直接返回
        if (shopTypeList != null && !shopTypeList.isEmpty()){
            // 查询肯定和下面的加入是对应的, 下面是用leftPushAll和这里使用0索引
            return Result.success(JSONUtil.toList(shopTypeList.get(0),ShopType.class));
        }
        //没有先去数据库查询
        List<ShopType> shopTypes = query().orderByAsc("sort").list();
        //如果数据库没有则报错
        if (shopTypes == null || shopTypes.isEmpty()){
            return Result.error("店铺类型不存在！！");
        }
        //有则写入redis
        String shopTypesToString = JSONUtil.toJsonStr(shopTypes);
        stringRedisTemplate.opsForList().leftPushAll(CACHE_SHOP_TYPE_KEY, shopTypesToString);
        //返回
        return Result.success(shopTypes);
    }
}
