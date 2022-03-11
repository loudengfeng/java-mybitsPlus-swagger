package com.example.demo.utils;

import com.example.demo.entity.Menu;

import java.util.ArrayList;
import java.util.List;

public class RankUtil {
  public static List<Menu> getChangeChildList(String id, List<Menu> menuList){
    List<Menu> childList = new ArrayList<>();
    for(Menu menu : menuList){
      if(String.valueOf(menu.getBelong()).equals(id)){
        childList.add(menu);
      }
    }
    for (Menu m : childList){
      m.setChildList(getChangeChildList(String.valueOf(m.getId()), menuList));
    }
    if(childList.size() == 0 ){
      return null;
    }
    return childList;
  }
}
