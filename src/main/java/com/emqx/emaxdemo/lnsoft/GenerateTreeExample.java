package com.emqx.emaxdemo.lnsoft;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.utils
 * @date: 2020/12/1 23:06
 **/
public class GenerateTreeExample {
    public List<Menu> list2Tree(List<Menu> list, Integer pId) {
        List<Menu> tree = new ArrayList<>();
        Iterator<Menu> it = list.iterator();
        while (it.hasNext()) {
            Menu m = it.next();
            if (m.parentId == pId) {
                tree.add(m);
                // 已添加的元素删除掉
                it.remove();
            }
        }
        // 寻找子元素
        tree.forEach(n -> n.children = list2Tree(list, n.id));
        return tree;
    }

    public static void main(String[] args) {
        GenerateTreeExample e =new GenerateTreeExample();
        List<Menu> list = e.buildList();
        Menu root = new Menu();
        root.id = 0;
        root.menuName = "根目录";
        root.children = e.list2Tree(list, root.id);
        System.out.println(JSON.toJSONString(root));
    }

    public List<Menu> buildList() {
        List<Menu> list = new ArrayList<>();
        Menu m = new Menu();
        m.id =1;
        m.menuName = "系统管理";
        m.parentId =0;
        list.add(m);
        m = new Menu();
        m.id =2;
        m.menuName = "用户管理";
        m.parentId = 0;
        list.add(m);
        m = new Menu();
        m.id = 3;
        m.menuName = "角色管理";
        m.parentId = 0;
        list.add(m);
        m = new Menu();
        m.id = 4;
        m.menuName = "菜单管理";
        m.parentId = 0;
        list.add(m);

        m = new Menu();
        m.id = 5;
        m.menuName = "菜单管理1";
        m.parentId = 4;
        list.add(m);

        return list;
    }


    @Data
    static class Menu {
        private Integer id;

        /**
         * 父节点Id
         */
        private Integer parentId;

        /**
         * 菜单名
         */
        private String menuName;

        List<Menu> children;
    }
}
