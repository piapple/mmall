/**
 * Created by mac on 2019-08-26.
 **/

package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("iCategoryService")
public class ICategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 新增分类
     * @param categoryName
     * @param parentId
     * @return
     */
    @Override
    public ServerResponse<String> addCategory(String categoryName,Integer parentId){
        Category category =new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        int count = categoryMapper.insertSelective(category);
        if(count>0){
            return ServerResponse.createBySuccessMessage("新增成功");
        }
        return ServerResponse.createByErrorMessage("新增失败");


    }

    /**
     * 递归查询本节点的id及子节点的id
     * @param categoryId
     * @return
     */
    public ServerResponse<List<Category>> selectCategoryChildrenByParentId(Integer categoryId){
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet,categoryId);

        List<Category> categoryIdList = Lists.newArrayList();

        if(categoryId != null){
            for(Category categoryItem : categorySet){
                categoryIdList.add(categoryItem);
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }


    //递归算法,算出子节点
    private Set<Category> findChildCategory(Set<Category> categorySet ,Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null){
            categorySet.add(category);
        }
        //查找子节点,递归算法一定要有一个退出的条件
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for(Category categoryItem : categoryList){
            findChildCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }

}
