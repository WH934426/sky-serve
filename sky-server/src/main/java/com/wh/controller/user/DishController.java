package com.wh.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wh.constant.StatusConstant;
import com.wh.entity.DishEntity;
import com.wh.result.Result;
import com.wh.service.DishService;
import com.wh.vo.DishVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * C端菜品浏览控制器
 */
@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
public class DishController {
    @Resource
    private DishService dishService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId 分类id
     * @return 菜品列表数据
     */
    @GetMapping("/list")
    public Result<List<DishVO>> getDishByCategoryId(Long categoryId) {

        // 构造redis中的key
        String key = "dish_" + categoryId;

        // 尝试从Redis获取并反序列化
        try {
            String value = (String) redisTemplate.opsForValue().get(key);
            if (value != null) {
                // 初始化ObjectMapper实例
                ObjectMapper objectMapper = new ObjectMapper();
                List<DishVO> dishVOList = objectMapper.readValue(value, new TypeReference<>() {
                });
                return Result.success(dishVOList);
            }
        } catch (IOException e) {
            log.error("Error occurred while deserializing from Redis: ", e);
        }

        // 如果Redis中没有数据，则从数据库查询
        DishEntity dish = new DishEntity();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);
        List<DishVO> dishVOList = dishService.listWithFlavor(dish);

        // 序列化并存回Redis
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(dishVOList);
            redisTemplate.opsForValue().set(key, json);
        } catch (JsonProcessingException e) {
            log.error("Error occurred while serializing to Redis: ", e);
        }

        return Result.success(dishVOList);
    }
}
