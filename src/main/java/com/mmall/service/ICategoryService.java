package com.mmall.service;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

public interface ICategoryService {

    ServerResponse<String> addCategory(String categoryName,Integer parentId);
    ServerResponse<List<Category>> selectCategoryChildrenByParentId(Integer id);

}
