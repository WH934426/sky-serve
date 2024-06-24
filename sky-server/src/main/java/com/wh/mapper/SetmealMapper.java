package com.wh.mapper;

import com.github.pagehelper.Page;
import com.wh.annotation.AutoFill;
import com.wh.dto.SetmealPageQueryDTO;
import com.wh.entity.SetmealEntity;
import com.wh.enumeration.OperationType;
import com.wh.vo.DishItemVO;
import com.wh.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 套餐接口
 */
@Mapper
public interface SetmealMapper {

    /**
     * 添加套餐
     *
     * @param setmeal 套餐实体
     */
    @AutoFill(OperationType.INSERT)
    void addSetmeal(SetmealEntity setmeal);

    /**
     * 分页查询套餐
     *
     * @param setmealPageQueryDTO 分页查询条件
     * @return 分页结果
     */
    Page<SetmealVO> querySetmealByPage(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据id查询套餐
     *
     * @param id id
     * @return 套餐实体
     */
    @Select("select * from setmeal where id=#{id}")
    SetmealEntity getSetmealById(Long id);

    /**
     * 根据id删除套餐
     *
     * @param id id
     */
    @Delete("delete from setmeal where id=#{id}")
    void deleteSetmealById(Long id);

    /**
     * 根据id修改套餐
     *
     * @param setmeal 套餐
     */
    @AutoFill(OperationType.UPDATE)
    void updateSetmealById(SetmealEntity setmeal);

    /**
     * 根据分类id查询套餐数量
     *
     * @param categoryId 分类id
     * @return 套餐数量
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId} ")
    Integer countByCategoryId(Long categoryId);

    /**
     * 根据条件动态查询套餐
     *
     * @param setmeal 套餐
     * @return 套餐列表
     */
    List<SetmealEntity> getSetmealList(SetmealEntity setmeal);

    /**
     * 根据套餐id查询菜品选项
     *
     * @param setmealId 套餐id
     * @return 菜品选项列表
     */
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);
}
